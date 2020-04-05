package _01_EightCores._04_Core4_LifeCycle;

/*
 * 在Java中, 线程有6个状态:
 * NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
 *
 * 此处演示NEW, RUNNABLE, TERMINATED
 */

public class NewRunnableTerminated implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new NewRunnableTerminated());

        /*
         * 线程状态: NEW
         * 新创建的线程还没有start()时, 处于NEW状态;
         */
        System.out.println("Thread state(NEW): " + thread.getState());

        thread.start();

        /*
         * 线程状态: RUNNABLE
         * 刚启动的线程, 此时可能该线程还没有真正的在运行(可能正在等待分配资源),
         * 此时线程处于RUNNABLE状态;
         */
        System.out.println("Thread state(RUNNABLE, 刚启动): " + thread.getState());

        Thread.sleep(1);

        /*
         * 线程状态: RUNNABLE
         * 当主线程短暂休眠后, 再去打印thread的状态, 这时thread一定是在运行中的,
         * 这里打印的依然是RUNABLE;
         * 也就是说, 在Java中, 线程状态RUNNABLE表示线程处于可运行状态, 也表示线程正在运行状态;
         */
        System.out.println("Thread state(RUNNABLE, 运行中): " + thread.getState());

        thread.join();

        /*
         * 线程状态: TERMINATED
         * 执行完成的线程(从run()方法中返回), 处于TERMINATED状态;
         */
        System.out.println("Thread state(TERMINATED): " + thread.getState());
    }
}
