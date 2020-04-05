package _01_EightCores._04_Core4_LifeCycle;

/*
 * 在Java中, 线程有6个状态:
 * NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
 *
 * 此处演示BLOCKED, WAITING, TIMED_WAITING
 */

public class BlockedWaitingTimedWaiting implements Runnable {

    @Override
    public void run() {
        try {
            func();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void func() throws InterruptedException {
        Thread.sleep(1000);
        wait();
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new BlockedWaitingTimedWaiting();
        Thread thread1 = new Thread(runnable);
        thread1.start();
        Thread thread2 = new Thread(runnable);
        thread2.start();
        Thread.sleep(5);

        /*
         * 线程状态: TIMED_WAITING
         * 此时thread1正在执行Thread.sleep(1000)方法, 所以是TIMED_WAITING状态;
         */
        System.out.println("Thread state(TIMED_WAITING, thread1): " + thread1.getState());

        /*
         * 线程状态: BLOCKED
         * 在Java中, BLOCKED状态只是针对于synchronized关键字的;
         * 当一个线程进入了synchronized代码块或方法, 而此时这个锁被其他线程所占有,
         * 这时, 这个线程就进入了BLOCKED状态;
         * 由于此时thread1正在执行Thread.sleep(1000)而没有进入wait()方法(执行wait()方法就会释放掉锁),
         * 所以thread1还没有释放掉锁, 所以thread2处于BLOCKED状态;
         */
        System.out.println("Thread state(BLOCKED, thread2): " + thread2.getState());

        Thread.sleep(1300);

        /*
         * 线程状态: WAITING
         * thread1执行wait()方法, 所以进入WAITING状态;
         * 进入WAITING状态后, 线程就会释放掉锁;
         */
        System.out.println("Thread state(WAITING, thread1): " + thread1.getState());

        Thread.sleep(1300);

        /*
         * 线程状态: WAITING
         * 由于thread1执行wait()进入了WAITING状态, 所以他会把锁释放掉, 然后thread2得以获得锁然后进入同步方法;
         * 又由于上面一行代码让主线程等待了足够的时间, 得以让thread2执行完Thread.sleep(100)然后也进入wait()方法,
         * 所以此时打印thread2的状态, 应该为WAITING;
         */
        System.out.println("Thread state(WAITING, thread2): " + thread2.getState());
    }
}
