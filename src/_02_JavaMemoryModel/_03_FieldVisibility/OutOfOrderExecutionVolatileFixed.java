package _02_JavaMemoryModel._03_FieldVisibility;

/*
 * 演示volatile关键字可以禁止重排序的效果;
 *
 * 在没有使用volatile修饰时, 下面的代码会有4种运行结果:
 * 1. x = 0, y = 1
 *    四行代码执行顺序为: a = 1; x = b; b = 1; y = a;
 * 2. x = 1, y = 0
 *    四行代码执行顺序为: b = 1; y = a; a = 1; x = b;
 * 3. x = 1, y = 1
 *    四行代码执行顺序为: b = 1; a = 1; x = b; y = a;
 * 4. x = 0, y = 0
 *    这是代码被重排序后的执行结果, 代码执行顺序可能是: y = a; a = 1; x = b; b = 1;
 *
 * 当使用volatile关键字声明a、b、x、y之后, 就不会出现重排序了, 上面的第4种情况就绝对不会再出现了;
 */

import java.util.concurrent.CountDownLatch;

public class OutOfOrderExecutionVolatileFixed {
    private volatile static int x = 0, y = 0;
    private volatile static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;

            CountDownLatch latch = new CountDownLatch(3);

            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.countDown();
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a = 1;
                    x = b;
                }
            }, "One");
            Thread two = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.countDown();
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    b = 1;
                    y = a;
                }
            }, "Two");
            one.start();
            two.start();
            latch.countDown();
            one.join();
            two.join();

            System.out.println("已执行" + i + "次, [x = " + x + ", y = " + y + "]");
            if (x == 0 && y == 0) {
                break;
            }
        }
    }
}
