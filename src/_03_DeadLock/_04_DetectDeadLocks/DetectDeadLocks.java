package _03_DeadLock._04_DetectDeadLocks;

/*
 * 如何定位死锁:
 * 1. 使用jstack工具
 *    a). 使用"jvisualvm"命令或者"jps"命令查看你想要检查的正在运行的Java程序的pid;
 *    b). 使用"jstack pid"命令打印出运行程序的线程栈信息(命令中的pid为上一步所查到的pid);
 *    c). 由jstack打印出的线程栈信息, 可以看到发生了多少个死锁, 以及死锁的具体信息;
 * 2. 使用ThreadMXBean在Java代码中定位死锁
 *    详见ThreadMXBeanDetection.java
 */

public class DetectDeadLocks {
    public static void main(String[] args) {
    }
}
