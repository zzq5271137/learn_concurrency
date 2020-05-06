package _15_SimpleProject._05_Computable4;

/*
 * 解决计算过程中抛出异常的情况;
 */

import java.util.Map;
import java.util.concurrent.*;

public class MyCache implements Computable<String, Integer> {

    private final Map<String, Future<Integer>> cache = new ConcurrentHashMap<>();
    private final Computable<String, Integer> c;

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

                /*
                 * 需要从cache中删除对应的FutureTask, 因为Future的状态是不能重置的,
                 * 如果某一次计算出错而不从cache中清理掉它, 之后想要获取相同结果的线程从cache中取到的FutureTask就都是它,
                 * 那么他们调用get()方法就始终会抛出ExecutionException异常, 导致死循环;
                 * 而且, 不仅是在ExecutionException异常的情况需要清理掉cache中的FutureTask,
                 * 在其他异常的情况中, 也需要清理掉;
                 * 这就是缓存污染的问题;
                 */
                cache.remove(arg);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache(new ExpensiveFunction());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + ": key为13, 第一次获取结果, 结果为:"
                            + myCache.compute("13"));
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
                            + myCache.compute("13"));
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
                            + myCache.compute("14"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
