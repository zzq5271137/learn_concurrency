package _04_ConcurrentToolsIntro;

/*
 * 并发工具类总体分类:
 * 1. 以线程安全为目的的并发工具
 * 2. 以管理线程、提高效率为目的的并发工具
 * 3. 以线程协作为目的的并发工具
 *
 * 以线程安全为目的的并发工具, 可以有两种角度来看这类工具:
 * 1. 从底层原理来分类
 *    1). 互斥同步
 *        a). 使用各种互斥同步的锁
 *            如synchronized、ReentrantLock、ReadWriteLock等
 *        b). 使用同步的工具类
 *            如Collections.synchronizedList(new ArrayList<>())、Vector等
 *    2). 非互斥同步
 *        atomic包, 原子类:
 *        a). Atomic*基本类型原子类
 *            i). AtomicInteger: 整型原子类
 *            ii). AtomicLong: 长整型原子类
 *            iii). AtomicBoolean: 布尔型原子类
 *        b). Atomic*Array数组类型原子类(数组里的每个元素都可以保证原子性)
 *            i). AtomicIntegerArray: 整型数组原子类
 *            ii). AtomicLongArray: 长整型数组原子类
 *            iii). AtomicReferenceArray: 引用类型数组原子类
 *        c). Atomic*Reference引用类型原子类
 *            i). AtomicReference: 引用类型原子类
 *            ii). AtomicStampedReference: 引用类型原子类的升级, 带时间戳, 可以解决ABA问题
 *            iii). AtomicMarkableReference
 *        d). Atomic*FieldUpdater升级原子类
 *            i). AtomicIntegerFieldUpdater: 原子更新整形字段的更新器
 *            ii). AtomicLongFieldUpdater: 原子更新长整形字段的更新器
 *        e). Adder加法器
 *            i). LongAdder
 *            ii). DoubleAdder
 *        f). Accumulator累加器
 *            i). LongAccumulator
 *            ii). DoubleAccumulator
 *    3). 结合互斥同步和非互斥同步
 *        线程安全的并发容器:
 *        a). ConcurrentHashMap
 *        b). CopyOnWriteArrayList
 *        c). 并发队列
 *            i). 阻塞队列
 *                如ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue、SynchronousQueue、
 *                DelayedQueue、TransferQueue等
 *            ii). 非阻塞队列
 *                 ConcurrentLinkedQueue
 *        d). ConcurrentSkipListMap和ConcurrentSkipListSet
 *    4). 无同步方案、不可变
 *        a). final关键字
 *        b). 线程封闭
 *            i). ThreadLocal
 *            ii). 栈封闭
 * 2. 从使用者的角度来分类
 *    1). 避免共享变量
 *        线程封闭:
 *        a). ThreadLocal
 *        b). 栈封闭
 *    2). 共享变量, 但是加以限制和处理
 *        a). 互斥同步
 *            i). 使用各种互斥同步的锁, 如synchronized、Lock接口的相关类等
 *            ii). 使用同步的工具类, 如Collections.synchronizedList(new ArrayList<E>())、Vector等
 *        b). final关键字
 *    3). 直接使用成熟工具类, 不需要自己去处理共享变量
 *        a). 线程安全的并发容器(详见上方)
 *        b). atomic包，原子类(详见上方)
 *
 * 以管理线程、提高效率为目的的并发工具:
 * 1. 线程池相关类
 *    1). Executor
 *    2). Executors
 *    3). ExecutorService
 *    4). 常见线程池
 *        FixedThreadPool、CachedThreadPool、ScheduledThreadPool、SingleThreadExecutor、ForkJoinPool等
 * 2. 能获取子线程的运行结果的相关类
 *    1). Callable
 *    2). Future
 *    3). FutureTask
 *    4). CompletableFuture
 *
 * 以线程协作为目的的并发工具:
 * 1. CountDownLatch
 * 2. CyclicBarrier
 * 3. Semaphore
 * 4. Condition
 * 5. Exchanger
 * 6. Phaser
 */

public class ConcurrentToolsIntro {
    public static void main(String[] args) {

    }
}
