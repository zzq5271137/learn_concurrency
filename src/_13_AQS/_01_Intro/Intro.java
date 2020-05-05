package _13_AQS._01_Intro;

/*
 * 为什么需要AQS:
 * AQS是AbstractQueuedSynchronizer的缩写, 在Java中, 它是一个类;
 * 我们的各种线程协作类(Lock锁、Semaphore、CountDownLatch等等)都有个共同特点, 那就是, 他们都像"闸门",
 * 即每次只允许一定数量的线程通过; 比如, Lock一次允许一个线程通过, Semaphore一次允许多个线程通过;
 * 在Lock和Semaphore中, "请求通过闸门"都有相应的方法, 比如Lock中的lock()方法, Semaphore中的acquire()方法;
 * 当线程不能通过"闸门"时, 都会进入阻塞状态; Lock和Semaphore还有更多相似点,
 * 比如他们都允许尝试(Lock中的tryLock()和Semaphore中的tryAcquire()),
 * 他们也都允许在一定的时间内不停的尝试(Lock中带参数的tryLock()和Semaphore中带参数的tryAcquire()), 等等;
 * 事实上, 不仅是Lock和Semaphore, 包括CountDownLatch、ReentrantReadWriteLock等,
 * 都有这样类似的"协作"(或者叫"同步")功能; 其实, 他们底层都用了一个共同的基类, 即AbstractQueuedSynchronizer类,
 * 这就是AQS; 就是因为以上提到的各种线程协作类都有共同点(很多工作都是类似的), 所以如果能提取出一个工具类,
 * 那么他们就可以直接使用, 对于这些线程协作类而言, 就可以屏蔽很多细节, 只关注于他们自己的"业务逻辑"就可以了;
 *
 * Semaphore(以及各种线程协作类)和AQS(AbstractQueuedSynchronizer类)的关系:
 * Semaphore有一个内部类, 叫做Sync, 这个类继承了AbstractQueuedSynchronizer类;
 * 不仅是Semaphore, CountDownLatch、ReentrantLock等线程协作类, 也都是这个结构;
 *
 * AQS的作用:
 * AQS是一个用于构建锁、同步器、协作工具类的工具类(框架); 有了AQS之后, 更多的协作工具类都可以很方便地被写出来;
 * 一句话总结: 有了AQS, 构建线程协作类就容易多了;
 * 使用了AQS的类有: ReentrantLock、ReentrantReadWriteLock、Semaphore、CountDownLatch、ThreadPoolExecutor等;
 */

public class Intro {
    public static void main(String[] args) {
    }
}
