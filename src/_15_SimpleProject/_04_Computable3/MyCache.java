package _15_SimpleProject._04_Computable3;

/*
 * 使用Future和Callable解决重复计算的问题;
 */

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MyCache implements Computable<String, Integer> {

    private final Map<String, Future<Integer>> cache = new ConcurrentHashMap<>();
    private final Computable<String, Integer> c;

    public MyCache(Computable<String, Integer> c) {
        this.c = c;
    }

    @Override
    public Integer compute(String arg) throws Exception {
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
        return res.get();
    }

    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache(new ExpensiveFunction());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("key为13, 第一次获取结果, 结果为:" + myCache.compute("13"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("key为13, 第二次获取结果, 结果为:" + myCache.compute("13"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("key为14, 第一次获取结果, 结果为:" + myCache.compute("14"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
