package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * 同wait()一样, sleep()也具有响应中断的能力(监听线程的中断标记位), 当它响应interrupt()中断请求时, 会做两件事情:
 * 1. 会抛出InterruptedException;
 * 2. 会重置线程的中断标记位(清除中断状态);
 * (其实只要是响应InterruptedException异常的都会清除中断状态);
 *
 * wait()与sleep()的异同:
 * 相同点:
 * 1. 都会使线程进入阻塞状态;
 * 2. 都可以响应interrupt()中断;
 * 不同点:
 * 1. wait()方法必须在同步代码块/方法中执行, sleep()不需要;
 * 2. wait()、notify()、notifyAll()方法是属于Object类的, sleep()、yield()方法是属于Thread类的;
 * 3. wait()方法会释放monitor锁, sleep()方法不会;
 * 4. sleep()方法经过指定时间的休眠后会主动退出阻塞状态, 而没有指定时间的wait()方法需要被其他线程唤醒;
 *
 * 此处展示sleep()用法, 有两种写法:
 * 1. 使用Thread.sleep();
 * 2. 使用TimeUnit.SECONDS.sleep(), 这种写法更优雅, 推荐;
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SleepInterrupted implements Runnable {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void run() {
        int i = 0;
        /*
         * 由于InterruptedException是在while里面被catch, 所以它会继续执行下面的代码, 也就是继续循环;
         * 又由于InterruptedException被响应, 所以中断状态被重置了, 所以这里isInterrupted()判断返回的是false,
         * 所以线程不会停止;
         */
        while (i < 10 && !Thread.currentThread().isInterrupted()) {
            System.out.println(sdf.format(new Date()));
            try {
                TimeUnit.SECONDS.sleep(1); // sleep()第二种写法
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "被中断了！");
                e.printStackTrace();
            }
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new SleepInterrupted());
        thread.start();
        Thread.sleep(4000); // sleep()第一种写法
        thread.interrupt();
    }
}
