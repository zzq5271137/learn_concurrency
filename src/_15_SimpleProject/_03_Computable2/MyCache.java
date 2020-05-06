package _15_SimpleProject._03_Computable2;

/*
 * 将HashMap替换为ConcurrentHashMap;
 * 但是现在的代码依然有问题, 那就是当一个线程计算完成之前, 如果有另一个线程想要计算相同值, 会导致不必要的重复计算;
 * 因为ConcurrentHashMap只是解决了不同线程同时读写的问题, 真正的计算是发生在put()方法之前的c.compute(arg)方法的,
 * 就上面那种情况, 第二个进来的线程已经计算完成了, 只是在put()方法时会导致put()覆盖或失败, 但是之前的计算已经消耗了资源;
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyCache implements Computable<String, Integer> {

    private final Map<String, Integer> cache = new ConcurrentHashMap<>();
    private final Computable<String, Integer> c;

    public MyCache(Computable<String, Integer> c) {
        this.c = c;
    }

    @Override
    public Integer compute(String arg) throws Exception {
        Integer res = cache.get(arg);
        if (res == null) {
            res = c.compute(arg);
            cache.put(arg, res);
        }
        return res;
    }

    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache(new ExpensiveFunction());

        /*
         * 通过将HashMap替换为ConcurrentHashMap, 我们发现, 这三个线程几乎是同时取得运算结果;
         */
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
