package _15_SimpleProject._06_Computable5;

/*
 * 出于安全性考虑, 缓存需要设置有效期, 到期自动失效;
 */

import java.util.Map;
import java.util.concurrent.*;

public class MyCache implements Computable<String, Integer> {

    private final Map<String, Future<Integer>> cache = new ConcurrentHashMap<>();
    private final Computable<String, Integer> c;
    private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    public MyCache(Computable<String, Integer> c) {
        this.c = c;
    }

    @Override
    public Integer compute(String arg) throws InterruptedException, CancellationException {
        while (true) {
            Future<Integer> res = cache.get(arg);
            if (res == null) {
                Callable<Integer> callable = new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return c.compute(arg);
                    }
                };
                FutureTask<Integer> futureTask = new FutureTask<>(callable);
                res = cache.putIfAbsent(arg, futureTask);
                if (res == null) {
                    res = futureTask;
                    futureTask.run();
                }
            }
            try {
                return res.get();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + ": 获取结果的过程被中断了");
                cache.remove(arg);
                throw e;
            } catch (CancellationException e) {
                System.out.println(Thread.currentThread().getName() + ": 计算任务被取消了");
                cache.remove(arg);
                throw e;
            } catch (ExecutionException e) {
                System.out.println(Thread.currentThread().getName() + ": 计算过程出错, 重试中...");
                cache.remove(arg);
            }
        }
    }

    public Integer compute(String arg, long expire, TimeUnit unit) throws InterruptedException {
        if (expire <= 0)
            throw new IllegalArgumentException("超时时间必须为整数！");
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                clear(arg);
            }
        }, expire, unit);
        return compute(arg);
    }

    public synchronized void clear(String arg) {
        Future<Integer> future = cache.get(arg);
        if (future != null) {
            if (!future.isDone()) {
                System.out.println(arg + ": 已过期, 取消Future任务");
                future.cancel(true);
            }
            System.out.println(arg + ": 过期时间到, 清除该缓存");
            cache.remove(arg);
        }
    }

    /**
     * 我们需要对缓存的过期时间做随机化, 否则如果缓存同时过期, 那么所有请求都同时拿不到缓存,
     * 会导致打爆CPU和数据库, 造成缓存雪崩、缓存击穿等高并发下的缓存问题;
     */
    public Integer computeRandomExpire(String arg) throws InterruptedException {
        long randomEepire = (long) (Math.random() * 10 + 4);
        return compute(arg, randomEepire, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache(new ExpensiveFunction());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + ": key为13, 第一次获取结果, 结果为:"
                            + myCache.computeRandomExpire("13"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + ": key为13, 第二次获取结果, 结果为:"
                            + myCache.computeRandomExpire("13"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + ": key为14, 第一次获取结果, 结果为:"
                            + myCache.computeRandomExpire("14"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(20);
        System.out.println("检查过期缓存是否清除(是否重新计算): ");
        System.out.println("重新获取\"13\": " + myCache.compute("13"));
        executor.shutdown();
    }
}
