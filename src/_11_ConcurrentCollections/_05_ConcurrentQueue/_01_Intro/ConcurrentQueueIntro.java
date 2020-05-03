package _11_ConcurrentCollections._05_ConcurrentQueue._01_Intro;

/*
 * 为什么要使用并发队列:
 * 使用队列可以在线程间传递数据, 比如在生产者消费者模式中使用的队列; 在多线程场景下, 就要求队列是线程安全的,
 * 这样, 我们在使用队列时就不用考虑同步问题;
 *
 * 并发队列包括:
 * a). 阻塞队列
 *     顶层接口是BlockingQueue, 表示阻塞队列, 非常适合用于作为数据共享的通道; 它有许多实现类,
 *     如ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue、SynchronousQueue、
 *     DelayedQueue、TransferQueue等;
 * b). 非阻塞队列
 *     ConcurrentLinkedQueue, 高效的非阻塞并发队列, 使用链表实现; 可以看做是一个线程安全的LinkedList;
 */

public class ConcurrentQueueIntro {
    public static void main(String[] args) {
    }
}
