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
 * 1. lock.newCondition()
 *    创建Condition实例的方法, 需要一个Lock锁的实例去调用, 创建后, 该Condition实例就绑定到了该Lock锁实例上,
 *    当有线程调用await()方法时, 要求其必须先持有该Condition实例绑定的Lock锁, 调用后, 会释放Lock锁, 并进入阻塞状态;
 *    当有线程调用signal()/signalAll()方法时, 也要求其必须先持有该Condition实例绑定的Lock锁;
 * 2. await()
 *    等待条件的线程会调用此方法, 释放Lock锁, 并进入阻塞状态;
 * 3. signal()/signalAll()
 *    执行条件的线程在条件达成时会调用此方法, 唤醒等待该条件的线程;
 *    signal()是公平的, 它会唤醒等待时间最长的那个线程; signalAll()会唤醒所有线程;
 *
 * Condition的注意点:
 * 1. 实际上, 如果说使用Lock来代替synchronized, 那么Condition就是用来代替相对应的object.wait()/notify()的,
 *    所以在用法和性质上, 几乎都一样;
 * 2. 类似于调用wait()方法会自动释放monitor锁, 调用await()方法会自动释放持有的Lock锁;
 * 3. 类似于调用wait()方法时, 线程必须先持有相应的monitor锁, Condition是绑定在一个Lock锁上的,
 *    调用await()方法时, 线程必须先持有相应的Lock锁, 否则会抛出异常;
 *    signal()/signalAll()同理;
 * 4. 相比于wait()/notify(), Condition的优势在于, 一个Lock锁可以有多个Condition(通过lock.newCondition()创建),
 *    这样, 就可以更灵活地控制线程执行的流程;
 *
 * 实例详见ConditionDemo1.java和ConditionDemo2.java
 */

public class ConditionIntro {
    public static void main(String[] args) {
    }
}
