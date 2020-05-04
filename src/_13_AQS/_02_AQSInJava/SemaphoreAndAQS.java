package _13_AQS._02_AQSInJava;

/*
 * Semaphore(以及各种线程协作类)和AQS(AbstractQueuedSynchronizer类)的关系:
 * Semaphore有一个内部类, 叫做Sync, 这个类继承了AbstractQueuedSynchronizer类;
 * 不仅是Semaphore, CountDownLatch、ReentrantLock等线程协作类, 也都是这个结构;
 */

public class SemaphoreAndAQS {
    public static void main(String[] args) {
    }
}
