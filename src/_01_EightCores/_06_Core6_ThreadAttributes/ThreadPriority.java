package _01_EightCores._06_Core6_ThreadAttributes;

/*
 * priority: 线程的优先级这个属性的目的是告诉线程调度器, 用户希望哪些线程相对多运行、哪些少运行;
 * 在Java程序中, 线程优先级一共有1~10级:
 * 1. Thread.MIN_PRIORITY: 表示1级
 * 2. Thread.NORM_PRIORITY: 表示5级
 * 3. Thread.MAX_PRIORITY: 表示10级
 * 创建一个线程默认情况下优先级为5(main为5); 这个优先级的值会继承自父线程(即创建它的线程);
 *
 * 我们的程序设计不应该依赖于优先级的使用, 因为:
 * 1. 不同的操作系统对优先级的设定是不一样的, 比如Java语言中是10级, Windows是7级, Linux是忽略优先级等等;
 * 2. 优先级会被操作系统改变, 比如在Windows中有一个被称为优先级推进器的功能, 当它发现某一个线程特别努力想执行的时候,
 *    它就会越过优先级的设置, 优先为这个线程分配时间; 所谓的优先级高的优先执行, 是在大量执行次数中才能体现出来的;
 *    而且, 如果我们把某一个线程的优先级设置的过低, 那么很有可能发生一种情况, 就是只要还有其他任务在, 操作系统就不会调度它,
 *    会导致这个线程被饿死(饥饿, 即很长时间不被运行);
 * 所以, 在实际开发中, 我们没有必要针对优先级做自己的修改, 默认的就好;
 */

public class ThreadPriority {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println(thread.getPriority());
        thread.setPriority(Thread.MIN_PRIORITY);
        System.out.println(thread.getPriority());
        thread.setPriority(Thread.NORM_PRIORITY);
        System.out.println(thread.getPriority());
        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println(thread.getPriority());
    }
}
