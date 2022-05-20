package _13_AQS._02_AQSPrinciple;

/*
 * AQS最核心的三要素:
 * 1. state
 *    state的具体含义, 会根据实现类的不同而不同, 比如在Semaphore中, 它表示"剩余的许可证的数量",
 *    而在CountDownLatch中, 它表示"还需要倒数的数量"; state是volatile修饰的, 会被并发地修改,
 *    所以所有修改state的方法都需要保证线程安全, 比如getState()、setState()以及compareAndSetState()操作,
 *    来读取和更新这个状态; 这些方法依赖于J.U.C.atomic包和Unsafe的支持;
 *    在ReentrantLock中, state用来表示"锁"的占有情况, 包括可重入计数(类似monitor锁的重入计数),
 *    即, 当没有线程占用锁时, state为0; 当一个线程第一次请求这把锁而此时state为0时, 线程成功获取锁, 并把state置为1,
 *    并且把当前持有这把锁的线程赋值为这个线程; 如果该线程重入了(通过判断该线程与当前持有这把锁的线程是否相同), 那就累加1,
 *    如果该线程退出, 那就递减1; 如果线程退出, state减1之后, state变为0, 则线程释放该锁, 并将当前持有这把锁的线程置为null;
 *    如果线程第一次请求锁而此时state不为0(通过判断该线程与当前持有这把锁的线程是否相同), 则阻塞;
 * 2. 控制线程抢锁和配合的FIFO队列
 *    这个队列用来存放"等待的线程", AQS就是"排队管理器", 当多个线程争用同一把锁时, 必须有排队机制,
 *    将那些没能拿到锁的线程串在一起; 当锁被释放时, 锁管理器就会从队列中挑选一个合适的线程来占有这个刚刚被释放的锁;
 *    AQS会维护一个等待的线程队列, 把线程都放到这个队列里; 这个队列是一个双向链表的形式;
 *    这个队列在锁的公平与非公平的概念中起到重要作用;
 * 3. 期望协作工具类去实现的获取/释放等重要方法
 *    这里的获取和释放的方法, 是那些利用AQS的线程协作类里面最重要的方法, 是有线程协作类自己实现的, 并且含义各不相同;
 *    这些待实现的方法主要包括:
 *    a). 获取的方法
 *        获取操作会依赖于state变量, 经常会阻塞;
 *        每个线程协作类中都有获取的方法, 比如:
 *        在Lock中, 就是lock()、tryLock()等方法, 表示获取锁;
 *        在Semaphore中, 就是acquire()、tryAcquire()等方法, 表示获取许可证;
 *        在CountDownLatch中, 就是await()等方法;
 *        这些方法都用到了重写的获取锁的方法; 如果线程协作类是一个独占锁的逻辑, 就重写tryAcquire()方法,
 *        如果线程协作类是一个共享锁的逻辑, 就重写tryAcquireShared(int acquires)方法;
 *        线程协作类需要根据自己的具体逻辑去重写这些方法; 线程协作类的获取的方法依赖于这个重写的方法;
 *    b). 释放的方法
 *        释放操作不会阻塞线程;
 *        每个线程协作类中都有释放的方法, 比如:
 *        在Lock中, 就是unlock()方法;
 *        在Semaphore中, 就是release()方法;
 *        在CountDownLatch中, 就是countDown()方法;
 *        这些方法都用到了重写的释放锁的方法; 如果线程协作类是一个独占锁的逻辑, 就重写tryRelease()方法,
 *        如果线程协作类是一个共享锁的逻辑, 就重写tryReleaseShared(int releases)方法;
 *        线程协作类需要根据自己的具体逻辑去重写这些方法; 线程协作类的释放的方法依赖于这个重写的方法;
 *
 * AQS的用法(比如我们利用AQS自己实现一个线程协作类):
 * 1. 写一个类, 想好协作逻辑, 实现获取/释放方法(需要用到下面第3步的重写的获取锁和释放锁的方法);
 * 2. 内部写一个Sync类(名字也可以自己取)继承自AbstractQueuedSynchronizer类;
 * 3. 根据是否独占, 来重写获取锁和释放锁的方法; 如果独占, 就重写tryAcquire()和tryRelease()方法;
 *    如果共享, 就重写tryAcquireShared(int acquires)和tryReleaseShared(int releases)方法;
 *    在之前写的获取/释放方法中调用AQS的tryAcquire()/tryRelease()或者shared方法;
 * 实例详见OneShotLatch.java
 *
 * AQS在CountDownLatch中的应用总结:
 * CountDownLatch实际上是一个共享锁的逻辑, 所以它在Sync内重写的方法是tryAcquireShared(int acquires)方法,
 * 和tryReleaseShared(int releases)方法;
 * 在CountDownLatch中, state表示"还需要倒数的数量";
 * 1. 当线程调用CountDownLatch的await()方法时, 便会尝试获取"共享锁", 不过一开始是获取不到锁的,
 *    因为还未倒数到0, 于是线程就进入等待队列中, 并进入阻塞状态;
 * 2. "共享锁"可获取到的条件, 就是"锁计数器"的值为0;
 * 3. "锁计数器"的初始值为count, 每当一个线程调用该CountDownLatch对象的countDown()方法时,
 *    就将"锁计数器"的值减1;
 * 4. 当某一次调用countDown()方法时"锁计数器"的值减到0, 就会唤醒等待队列中的所有线程;
 *
 * AQS在Semaphore中的应用总结:
 * Semaphore也是一个共享锁的逻辑, 所以它在Sync内重写的方法是tryAcquireShared(int acquires)方法,
 * 和tryReleaseShared(int releases)方法;
 * 在Semaphore中, state表示"剩余的许可证的数量";
 *
 * 资料：
 * 1. https://www.bilibili.com/video/BV19J411Q7R5?p=10
 * 2. https://www.jianshu.com/p/cb326d6233d5
 */

public class AQSPrinciple {
    public static void main(String[] args) {
    }
}
