package _09_CAS;

/*
 * 演示Unsafe的使用
 */

import java.lang.reflect.Field;

public class UnsafeDemo {
    private int count;

    private static sun.misc.Unsafe UNSAFE;
    private static long COUNT_OFFSET;

    static {
        try {
            Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");  // 使用反射获取一个Unsafe对象（走后门）
            field.setAccessible(true);
            UNSAFE = (sun.misc.Unsafe) field.get(null);
            COUNT_OFFSET = UNSAFE.objectFieldOffset(UnsafeDemo.class.getDeclaredField("count"));  // 通过Unsafe，获取类属性的偏移量（CAS操作时需要用到）
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void notUseUnsafe() throws InterruptedException {
        UnsafeDemo instance = new UnsafeDemo();

        Runnable r = () -> {
            for (int i = 0; i < 10000; i++) {
                instance.count++;  // 直接++
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("not use Unsafe: " + instance.count);
    }

    private static void useUnsafe() throws InterruptedException {
        UnsafeDemo instance = new UnsafeDemo();

        Runnable r = () -> {
            for (int i = 0; i < 10000; i++) {
                int cur;
                do {
                    cur = UNSAFE.getIntVolatile(instance, COUNT_OFFSET);  // 读取当前内存中最新的值
                } while (!UNSAFE.compareAndSwapInt(instance, COUNT_OFFSET, cur, cur + 1));  // 使用Unsafe提供的CAS直接操作共享内存，确保原子性
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("use Unsafe: " + instance.count);
    }

    public static void main(String[] args) throws InterruptedException {
        notUseUnsafe();
        useUnsafe();
    }
}
