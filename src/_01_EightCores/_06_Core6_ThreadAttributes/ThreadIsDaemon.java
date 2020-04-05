package _01_EightCores._06_Core6_ThreadAttributes;

/*
 * daemon: 表明该线程是否是守护线程;
 * 对于守护线程, 通常而言, 守护线程都是由JVM自动启动的;
 * 守护线程与用户线程的区别在于, 守护线程不影响JVM的停止, 而用户线程可以;
 * 即, 当所有用户线程已经执行完毕, 守护线程就会跟随JVM一起停止;
 * 守护线程这个属性的值会继承自父线程, 即创建线程A的线程是守护线程, 那么线程A也是守护线程, 反之亦然;
 * 在开发中, 我们不应该将一个自己的线程设置为守护线程, 原因是守护线程它不能阻止JVM的停止;
 * 即, 当其他用户线程停止时, 这个自己的守护线程会随着JVM一起停止, 这是很危险的;
 */

public class ThreadIsDaemon {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println(thread.isDaemon());
        thread.setDaemon(true);
        System.out.println(thread.isDaemon());
    }
}
