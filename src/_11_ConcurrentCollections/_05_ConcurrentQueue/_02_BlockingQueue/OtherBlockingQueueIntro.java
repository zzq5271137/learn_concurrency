package _11_ConcurrentCollections._05_ConcurrentQueue._02_BlockingQueue;

/*
 * LinkedBlockingQueue:
 * 1. 底层使用链表去存放数据(Node);
 * 2. 是无界队列, 容量为Integer.MAX_VALUE(在真实使用场景下普遍达不到这个值, 因为内存不够, 所以称为无界队列);
 *    由于是无界的, 所以put()是不会阻塞的;
 * 3. 使用两把锁(takeLock和putLock, 都是ReentrantLock, 分析put()方法和take()方法源码);
 *
 * PriorityBlockingQueue:
 * 1. 底层使用数组去存放数据;
 * 2. 是无界队列, 它无界的原理是当容量不够时会进行扩容;
 *    由于是无界的, 所以put()是不会阻塞的;
 * 3. 支持优先级, 不是先进先出, 使用compareTo()决定顺序;
 * 4. 使用一把锁(ReentrantLock);
 *
 * SynchronousQueue:
 * 容量为0, 它是不存储数据的; 需要注意的是, 它的容量是0而不是1(有些资料会说它容量为1, 这是错的),
 * 因为SynchronousQueue不持有元素, 它所做的就是直接传递(direct handoff);
 * SynchronousQueue没有peek()等方法, 因为peek()的含义是取出头结点, 但是SynchronousQueue容量为0,
 * 所以连头结点都没有, 也就没有peek()方法; 同理, 也没有iterator相关方法;
 * SynchronousQueue是一个极好的用来直接传递的并发数据结构;
 * Executors.newCachedThreadPool()使用的线程池正是SynchronousQueue;
 *
 * DelayQueue:
 * 1. 延迟队列, 根据延迟时间排序(放入其中的元素必须实现Delayed接口, 规定排序规则);
 * 2. 是无界队列; 由于是无界的, 所以put()是不会阻塞的;
 */

public class OtherBlockingQueueIntro {
    public static void main(String[] args) {
    }
}
