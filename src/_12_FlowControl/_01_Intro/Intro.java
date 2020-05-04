package _12_FlowControl._01_Intro;

/*
 * 什么是控制并发流程:
 * 控制并发流程的工具类, 作用就是帮助开发人员更容易地协调线程之间的合作, 让线程之间相互配合, 来满足业务需求;
 * 例如, 让线程A等待线程B执行完毕后再执行, 或者更复杂的情况;
 *
 * 有哪些控制并发流程的工具类:
 * 1. Semaphore
 *    信号量, 可以通过控制"许可证"的数量, 来保证线程之间的配合;
 *    线程只有拿到"许可证"后才能继续运行; 相比于其他的同步器, 更加灵活;
 * 2. CyclicBarrier
 *    线程会等待, 直到等待线程的数量达到了事先规定的数目; 一旦达到触发条件, 就可以进行下一步的动作;
 *    适用于线程之间相互等待处理结果就绪的场景;
 * 3. Phaser
 *    和CyclicBarrier类似, 但是计数可变; 是Java1.7加入的;
 * 4. CountDownLatch
 *    和CyclicBarrier类似, 数量递减到0时, 触发动作;
 *    同一个CountDownLatch对象是不能够重复使用的(倒数不能重置);
 * 5. Exchanger
 *    让两个线程在合适时交换对象;
 *    适用场景: 当两个线程工作在同一个类的不同实例上时, 用于交换数据;
 * 6. Condition
 *    可以控制线程的"等待"和"唤醒"; 是object.wait()的升级版;
 */

public class Intro {
    public static void main(String[] args) {
    }
}
