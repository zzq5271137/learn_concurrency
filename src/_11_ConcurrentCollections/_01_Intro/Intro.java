package _11_ConcurrentCollections._01_Intro;

/*
 * 线程安全的并发容器:
 * 1. ConcurrentHashMap: 线程安全的HashMap
 * 2. CopyOnWriteArrayList: 线程安全的List
 * 3. 并发队列
 *    a). 阻塞队列
 *        顶层接口是BlockingQueue, 表示阻塞队列, 非常适合用于作为数据共享的通道; 它有许多实现类,
 *        如ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue、SynchronousQueue、
 *        DelayedQueue、TransferQueue等;
 *    b). 非阻塞队列
 *        ConcurrentLinkedQueue, 高效的非阻塞并发队列, 使用链表实现; 可以看做是一个线程安全的LinkedList;
 * 4. ConcurrentSkipListMap和ConcurrentSkipListSet
 *    ConcurrentSkipListMap是一个Map, 使用跳表的数据结构进行快速查找;
 *
 * 集合类的历史, 古老和过时的同步容器:
 * 1. 阶段一: Vector和Hashtable
 *    可以把Vector理解为线程安全的ArrayList, 把Hashtable理解为线程安全的HashMap;
 *    他们是早期JDK提供的工具, 他们的目标也是提供线程安全的集合类; 不过随着JDK的发展,
 *    他们已经不能满足当今的需求了, 比如说, 他们的性能不够好, 而且他们对复合操作支持的不够好,
 *    比如说在多个线程并发地修改容器中的内容的情况下, 他们可能会抛出异常; 当然, 他们最大的缺点是性能不好;
 *    因为他们里面的好多方法, 都是直接使用synchronized修饰的, 在高并发的场景下, 这会导致性能很低;
 *    详见VectorDemo.java和HashtableDemo.java
 * 2. 阶段二: ArrayList和HashMap的升级版(即所谓同步容器)
 *    我们都知道, ArrayList和HashMap是线程不安全的; 但是, 他们有升级版;
 *    即, 可以用Collections.synchronizedList(new ArrayList<E>())和Collections.synchronizedMap(new HashMap<K,V>()),
 *    使之变成线程安全的, 这些就是所谓的同步容器; 他们里面的方法虽然不像Vector和Hashtable一样直接使用synchronized修饰,
 *    他们使用的是同步代码块的形式去实现线程安全的; 但是这些方法的同步代码块中使用的都是同一把锁, 所以他们的性能依然很低;
 *    详见SynListDemo.java和SynMapDemo.java
 * 3. 阶段三: ConcurrentHashMap和CopyOnWriteArrayList
 *    这俩就是用来取代同步容器的(即Collections.synchronizedList和Collections.synchronizedMap);
 *    因为在绝大多数的情况下, 这两个的性能都比同步容器要好; 唯一例外的是, 对于list来说, 如果一个list经常被修改(经常被写入),
 *    那么Collections.synchronizedList的性能要优于CopyOnWriteArrayList, 因为CopyOnWriteArrayList适用于读多写少的场景;
 *    但是对于ConcurrentHashMap来说, 它几乎没有例外的在所有场景的性能都优于Collections.synchronizedMap;
 */

public class Intro {
    public static void main(String[] args) {
    }
}
