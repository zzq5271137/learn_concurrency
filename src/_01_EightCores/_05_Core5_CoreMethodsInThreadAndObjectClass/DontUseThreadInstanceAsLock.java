package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * 参考: https://www.cnblogs.com/aigeileshei/p/10728739.html
 *      https://segmentfault.com/q/1010000016744022?utm_source=tag-newest
 *      https://blog.csdn.net/nmyangym/article/details/7850882#commentBox
 *
 * 不要使用Thread的实例对象作为锁:
 * 具体来说, 如果一个类继承了Thread类, 并且需要实际运行它(即需要创建一个它的实例, 以下简称myThread, 然后需要执行myThread.start()),
 * 就不要把myThread用作锁, 因为在myThread的run()方法执行结束后, 会自动调用自身的notifyAll()方法(即this.notifyAll()),
 * 也就是说, 在myThread的run()方法执行结束后, 会自动地去唤醒正在等待myThread这把锁的所有线程(这里的等待是指执行过myThread.wait()),
 * 这显然会打乱我们的线程执行的设计规划, 会得到我们不想要的结果;
 *
 * 但如果一个类是implements Runnable, 然后再把它的实例(以下简称myRunnable)作为new Thread()的参数传进去, 并start(),
 * 那么myRunnable是可以作为锁来用的, 因为, 是在Thread实例对象的run()方法中会自动执行notifyAll(), 也就是说,
 * 这个线程的run()方法最后唤醒的是等待Thread实例这把锁的线程, 而不是myRunnable这把锁, 所以没问题;
 *
 * 总结一句话就是, Thread类的实例对象在run()方法的最后会自动执行this.notifyAll(),
 * 所以我们不要把Thread类(和我们自己写的继承自Thread类的类)的实例对象用作锁;
 *
 * 在下面的例子中, Calculator类继承自Thread类, 我们创建了一个它的实例对象, 即calculator, 然后start()了它,
 * 在该类中, run()方法内的同步代码块和getFinalResult()方法中的同步代码块用的是同一个锁(this, 也就是calculator这个对象),
 * 在main方法中, 主线程MainThread执行getFinalResult()方法, 由于finished = false, 所以进入循环,
 * 然后执行wait(), 即等待其他的持有calculator这把锁的线程的唤醒(在这个例子中, 就是在等待run()方法中的同步代码块的唤醒);
 * 但是, 我们可以看到, 在run()方法中, 并没有写notify()或者notifyAll()的代码, 而getFinalResult()依旧执行完成并输出了结果,
 * 这就印证了上面的说法, 即Thread类的实例对象在run()方法执行结束后会自动执行this.notifyAll();
 */

import java.util.concurrent.TimeUnit;

class Calculator extends Thread {
    private int result = 0;
    private boolean finished = false;

    @Override
    public void run() {
        /*
         * 为了保证让主线程执行getFinalResult()时先进入同步代码块并进入while循环执行wait(),
         * 这里先让run()方法休眠一小会儿之后再进入同步代码块去做计算;
         */
        try {
            TimeUnit.MICROSECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            try {
                System.out.println(Thread.currentThread().getName() + "开始计算");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i <= 10; i++) {
                result += i;
                System.out.println(Thread.currentThread().getName() + "做了一次计算, result = " + result);
            }
            finished = true;
        }
    }

    public int getFinalResult() {
        synchronized (this) {
            while (!finished) {
                try {
                    System.out.println(Thread.currentThread().getName() + "尝试获得结果, 但尚未计算完成, 进入wait()");
                    TimeUnit.SECONDS.sleep(3);
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("已计算完成, " + Thread.currentThread().getName() + "被唤醒");
            return result;
        }
    }
}

public class DontUseThreadInstanceAsLock {
    public static void main(String[] args) {
        Thread.currentThread().setName("MainThread");
        Calculator calculator = new Calculator();
        calculator.setName("CalculatorThread");
        calculator.start();
        System.out.println("最终结果: " + calculator.getFinalResult());
    }
}
