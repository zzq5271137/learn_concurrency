package _01_EightCores._06_Core6_ThreadAttributes;

/*
 * tid(Thread ID): 表示线程的编号, 每个线程都有自己的ID, 用于唯一标识不同的线程, 线程ID从1开始(main);
 * 线程ID是不能够修改的, 是由JVM分配的, main就是第一个线程(ID为1), 之后创建的线程的ID是自增的;
 * 当JVM运行起来之后, 我们自己创建的线程的ID早已不是2, 因为JVM运行起来, 会创建好多线程(比如GC相关);
 * 这个线程ID, 并不能用作数据库的主键, 因为在每次JVM启动时, 对于一个线程来说这个ID是会变的;
 */

public class ThreadID {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread thread = new Thread();
        System.out.println(mainThread.getName() + ", ID = " + mainThread.getId());
        System.out.println(thread.getName() + ", ID = " + thread.getId());
    }
}
