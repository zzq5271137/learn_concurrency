package _13_AQS._01_Intro;

/*
 * 为什么需要AQS:
 * AQS是AbstractQueuedSynchronizer的缩写, 在Java中, 它是一个类;
 * 我们的锁(Lock)和协作类都有个共同特点, 那就是, 他们都像"闸门", 即每次只允许一定数量的线程通过;
 * 比如, Lock一次允许一个线程通过, Semaphore一次允许多个线程通过; 在这些类中, "请求通过闸门"都有相应的方法,
 * 比如Lock中的lock()方法, Semaphore中的acquire()方法; 当线程不能通过"闸门"时, 都会进入阻塞状态;
 * Lock和Semaphore还有更多相似点, 比如他们都允许尝试(Lock中的tryLock()和Semaphore中的tryAcquire()),
 * 他们也都允许在一定的时间内不停的尝试(Lock中带参数的tryLock()和Semaphore中带参数的tryAcquire()), 等等;
 * 事实上, 不仅是Lock和Semaphore, 包括CountDownLatch、ReentrantReadWriteLock等,
 * 都有这样类似的"协作"(或者叫"同步")功能; 其实, 他们底层都用了一个共同的基类, 即AbstractQueuedSynchronizer类,
 * 这个类就是AQS思想在Java中的实现; 这就是AQS;
 */

public class Intro {
    public static void main(String[] args) {
    }
}
