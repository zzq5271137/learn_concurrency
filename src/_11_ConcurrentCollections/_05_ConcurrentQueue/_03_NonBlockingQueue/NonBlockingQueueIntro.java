package _11_ConcurrentCollections._05_ConcurrentQueue._03_NonBlockingQueue;

/*
 * 非阻塞并发队列:
 * J.U.C中的非阻塞并发队列只有ConcurrentLinkedQueue这一种; 顾名思义, 它是使用链表作为底层数据结构的;
 * 它使用CAS和自旋(非阻塞算法)来实现线程安全, 适合用在对性能要求较高的并发场景; 它不具备阻塞功能;
 * (注意看源码中offer()方法的CAS的使用)
 */

public class NonBlockingQueueIntro {
    public static void main(String[] args) {
    }
}
