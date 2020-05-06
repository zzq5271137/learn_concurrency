package _15_SimpleProject._02_Computable1;

/*
 * 用装饰者模式, 给计算器自动添加缓存的功能;
 * 将compute()方式使用synchronized修饰, 以保证线程安全;
 */

import java.util.HashMap;
import java.util.Map;

public class MyCache implements Computable<String, Integer> {

    private final Map<String, Integer> cache = new HashMap<>();
    private final Computable<String, Integer> c;

    public MyCache(Computable<String, Integer> c) {
        this.c = c;
    }

    /**
     * 这里先使用synchronized, 以保证线程安全; 之后再去迭代升级;
     */
    @Override
    public synchronized Integer compute(String arg) throws Exception {
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
         * 这个例子就可以看到使用synchronized的缺点;
         * 第二次取"13"时, 实际上"13"是已经缓存了的, 但是它并没有很快取到, 因为它要等待取"14"的那个线程计算完成;
         * 原因就在于compute()方法使用了synchronized修饰, 导致这个方法只能同时由一个线程访问;
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
