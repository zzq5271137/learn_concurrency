package _05_ThreadPool._02_CreateThreadPool;

/*
 * 线程池构造方法的参数:
 * 1. corePoolSize: int
 *    核心线程数;
 *    corePoolSize表示线程池的基本大小, 即在没有任务需要执行的时候线程池的大小, 并且只有在工作队列满了的情况下,
 *    才会创建超出这个数量的线程; 这里需要注意的是, 在刚刚创建ThreadPoolExecutor的时候, 线程并不会立即启动,
 *    而是要等到有任务提交时才会启动(即线程池在完成初始化后, 默认情况下, 线程池中并没有任何线程, 线程池会等待有任务到来时,
 *    再创建新线程去执行任务), 除非调用了prestartCoreThread/prestartAllCoreThreads事先启动核心线程;
 *    如果接受的任务数量大于corePoolSize的大小, 线程池就会把多于corePoolSize的那部分任务放到工作队列中,
 *    等待正在执行的线程执行完毕, 再从工作队列中去取任务进行执行; 但如果当前工作队列已经满了,
 *    线程池就会突破corePoolSize的大小, 去创建新的线程(当然, 总线程数不会超过maxPoolSize);
 * 2. maxPoolSize/maximumPoolSize: int
 *    最大线程数;
 *    maximumPoolSize表示线程池中允许的最大线程数, 线程池中的当前线程数目不会超过该值; 如果队列中任务已满,
 *    并且当前线程个数小于maximumPoolSize, 那么会创建新的线程来执行任务; 这里值得一提的是largestPoolSize,
 *    该变量记录了线程池在整个生命周期中曾经出现的最大线程个数; 为什么说是曾经呢? 因为线程池创建之后,
 *    可以调用setMaximumPoolSize()改变运行的最大线程的数目;
 *    额外说明: 这个参数在不同地方的具体参数名可能不一样; 在ThreadPoolExecutor类中, 参数名是maximumPoolSize;
 *            不过在org.springframework.scheduling.concurrent包的ThreadPoolExecutorFactoryBean类等其他类中,
 *            也有使用maxPoolSize作为参数名的情况; 我们可以直接理解为maxPoolSize和maximumPoolSize是相同的就可以了;
 * 3. keepAliveTime: long
 *    保持存活时间;
 *    如果线程池当前的线程数多于corePoolSize, 那么如果有些线程处于空闲状态且空闲时间超过keepAliveTime,
 *    它们就会被终止(即线程被回收, 以免造成不必要的资源消耗); 默认情况下, 不会将线程的数量回收至少于corePoolSize,
 *    也就是只会回收多于corePoolSize的那部分线程; 但如果设置了allowCoreThreadTimeout为true, 那么,
 *    线程池就会回收所有空闲时间超过keepAliveTime的线程;
 * 4. workQueue: BlockingQueue
 *    任务存储队列/工作队列;
 *    在线程数等于或大于corePoolSize的情况下, 新进来的任务会被优先放进工作队列中, 当然前提是队列没有满;
 *    当某一线程执行完当前任务时, 会从队列头取任务去执行;
 *    有3种队列类型:
 *    1). 直接交接: SynchronousQueue
 *        在任务不是特别多的业务中, 如果我们只是把任务通过队列做简单的中转, 然后直接交到线程去处理,
 *        就可以使用SynchronousQueue; SynchronousQueue是没有容量的, 也就是说, 它是存不下任务的;
 *        所以, 如果要使用这种队列, 我们可能要把maxPoolSize设置得大一下, 因为我们没有队列作为缓冲了;
 *    2). 无界队列: LinkedBlockingQueue
 *        LinkedBlockingQueue的容量可以看成是无限的, 它不会被装满; 所以, 如果使用这种队列,
 *        线程池中的线程数永远不会超过corePoolSize; 使用这种队列有风险, 因为如果处理速度跟不上任务提交的速度,
 *        那么队列里的任务就会越来越多, 可能会造成内存浪费或者OOM异常;
 *    3). 有界队列: ArrayBlokcingQueue
 *        ArrayBlokcingQueue可以设置队列大小;
 * 5. threadFactory: ThreadFactory
 *    当线程池需要新的线程的时候, 会使用传进来的threadFactory来生成新的线程;
 *    新线程都是由传进来的threadFactory创建的, 如果不传threadFactory参数, 默认使用Executors.defaultThreadFactory();
 *    使用Executors.defaultThreadFactory()创建出来的线程都在同一个线程组, 拥有同样的NORMAL_PRIORITY优先级,
 *    并且都不是守护线程; 如果自己指定threadFactory, 那么就可以改变线程名、线程组、优先级、是否是守护线程等等;
 *    通常情况, 我们使用默认的线程工厂就好;
 * 6. handler: RejectedExecutionHandler
 *    当线程池无法接受你所提交的任务时的拒绝策略;
 *
 * 线程池添加(新建)线程的规则:
 * 1. 如果线程数小于corePoolSize, 即使其他工作线程处于空闲状态, 也会创建一个新线程来运行新任务;
 * 2. 如果线程数等于(或大于)corePoolSize, 但少于maximumPoolSize, 且队列未满, 则将任务放入队列;
 * 3. 如果线程数大于corePoolSize, 但少于maximumPoolSize, 且队列已经满了, 则创建一个新线程来运行任务(新线程从队列头取任务);
 * 4. 如果线程数等于(或大于)maxPoolSize, 且队列已经满了, 则拒绝该任务;
 *
 * 线程池增减线程的特点:
 * 1. 通过设置corePoolSize和maximumPoolSize相同, 就可以创建一个固定大小的线程池;
 * 2. 线程池始终希望保持较少的线程数, 只有在负载变的很大时(队列满了的情况)才增加新线程;
 * 3. 通过设置maximumPoolSize为很大的值, 例如Integer.MAX_VALUE, 可以允许线程池容纳任意数量的并发任务;
 * 4. 只有在队列填满时才会创建多于corePoolSize的线程, 所以如果你传入的是无界队列(例如LinkedBlockingQueue),
 *    那么线程数就永远都不会超过corePoolSize;
 *
 * 线程池应该手动创建还是自动创建:
 * 手动创建更好, 因为这样可以让我们更加明确线程池的运行规则, 避免资源耗尽的风险;
 * 自动创建线程池(即使用JDK封装好的构造函数, 比如Executors.newFixedThreadPool())可能会带来很多问题;
 * 自动创建的线程池有:
 * 1. FixedThreadPool
 *    corePoolSize和maximumPoolSize相同的线程池, 不回收线程(keepAliveTime为0),
 *    使用无界队列(LinkedBlockingQueue);
 *    详见FixedThreadDemo.java和FixedThreadPoolOOMDemo.java
 * 2. SingleThreadExecutor
 *    只有1个线程的线程池, corePoolSize和maximumPoolSize都是1, 不回收线程(keepAliveTime为0),
 *    使用无界队列(LinkedBlockingQueue);
 *    详见SingleThreadExecutorDemo.java
 * 3. CachedThreadPool
 *    可缓存的线程池, 因为它设置了keepAliveTime(默认是60秒), 所以会把闲置的线程给回收回来,
 *    是无界线程池, 因为它的maximumPoolSize值为Integer.MAX_VALUE,
 *    使用直接交接队列(SynchronousQueue), 即提交的任务直接交给线程执行;
 *    详见CachedThreadPoolDemo.java
 */

public class CreateThreadPool {
    public static void main(String[] args) {
    }
}
