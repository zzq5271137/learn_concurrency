package _12_FlowControl._04_Condition;

/*
 * Condition(条件对象):
 * Condition是一个接口, 又称为条件对象; Condition的主要作用是, 当线程1需要等待某个条件的时候,
 * 它就会去执行condition.await()方法, 一旦执行了这个方法, 线程就会进入阻塞状态; 这时, 通常会有另外一个线程,
 * 假设是线程2, 去执行对应的条件, 直到这个条件达成的时候, 线程2就会去执行condition.signal()方法,
 * 这时JVM就会从被阻塞的线程里找, 找到那些等待该condition的线程, 并唤醒他们, 相当于线程1收到条件满足的信号,
 * 线程1的状态就会变成RUNNABLE状态, 继续执行; Condition实例是绑定在锁上面的;
 *
 * Condition的主要方法:
 * 1. await()
 *    等待条件的线程会调用此方法, 进入阻塞状态;
 * 2. signal()/signalAll()
 *    执行条件的线程在条件达成时会调用此方法, 唤醒等待该条件的线程;
 *    signal()是公平的, 它会唤醒等待时间最长的那个线程; signalAll()会唤醒所有线程;
 *
 * 实例详见ConditionDemo1.java和ConditionDemo2.java
 */

public class ConditionIntro {
    public static void main(String[] args) {
    }
}
