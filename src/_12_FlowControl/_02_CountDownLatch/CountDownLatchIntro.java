package _12_FlowControl._02_CountDownLatch;

/*
 * CountDownLatch(倒计时门栓):
 * 类比到现实场景中, 例如拼团购物、游乐园的过山车等, 等待人满才发货/开车;
 * CountDownLatch会进行倒数, 在倒数结束之前, 线程一直处于等待状态, 直到倒计时结束了, 线程才能继续工作;
 * 同一个CountDownLatch对象是不能够重复使用的(倒数不能重置);
 *
 * CountDownLatch相关方法:
 * 1. CountDownLatch(int count):
 *    仅有的一个构造函数, 参数count为需要倒数的数值;
 * 2. await():
 *    调用await()方法的线程会被挂起, 线程会等待直到count值为0才继续执行;
 * 3. countDown():
 *    将count值减1, 减到0时, 等待的线程会被唤醒;
 *    调用countDown()方法的线程并不会进行等待, 只有调用await()方法的线程才会等待;
 *
 * CountDownLatch的典型用法:
 * 1. 一个线程等待多个线程都执行完毕, 再继续自己的工作;
 *    详见CountDownLatchDemo1.java
 * 2. 多个线程等待某一个线程的信号, 同时开始执行;
 *    详见CountDownLatchDemo2.java
 * 3. 既有一等多, 也有多等一的场景;
 *    详见CountDownLatchDemo3.java
 *
 * CountDownLatch的注意点:
 * 同一个CountDownLatch对象是不能够重复使用的(倒数不能重置); 如果需要重新计数, 必须重新创建新的CountDownLatch实例,
 * 或者可以考虑使用CyclicBarrier;
 */

public class CountDownLatchIntro {
    public static void main(String[] args) {
    }
}
