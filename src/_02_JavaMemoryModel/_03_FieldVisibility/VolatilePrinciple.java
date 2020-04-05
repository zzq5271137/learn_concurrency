package _02_JavaMemoryModel._03_FieldVisibility;

/*
 * 参考: https://www.infoq.cn/article/java-memory-model-4/
 *
 * volatile的特性:
 * 当我们声明共享变量为volatile后, 对这个变量的读/写将会很特别,
 * 理解volatile特性的一个好方法是: 把对volatile变量的单个读/写, 看成是使用同一个监视器锁对这些单个读/写操作做了同步;
 * 下方VolatileFeatureDemo1是对一个使用volatile声明的64位long类型变量的单个读、写和复合读/写操作;
 * 假设有多个线程分别调用VolatileFeatureDemo1里的3个方法, VolatileFeatureDemo1所写的代码的语义等价于VolatileFeatureDemo2的写法;
 * 如两份代码所示, 对一个volatile变量的单个读/写操作, 与对一个普通变量的读/写操作使用同一个监视器锁来同步, 它们之间的执行效果相同;
 * 监视器锁的happens-before规则保证释放监视器和获取监视器的两个线程之间的内存可见性, 这意味着对一个volatile变量的读,
 * 总是能看到(任意线程)对这个volatile变量最后的写入; 监视器锁的语义决定了临界区代码的执行具有原子性,
 * 这意味着即使是64位的long型和double型变量, 只要它是volatile变量, 对该变量的读写就将具有原子性;
 * 如果是多个volatile操作或类似于volatile++这种复合操作, 这些操作整体上不具有原子性;
 * 简而言之, volatile变量自身具有下列特性:
 * 1). 可见性: 对一个volatile变量的读, 总是能看到(任意线程)对这个volatile变量最后的写入;
 * 2). 原子性: 对任意单个volatile变量的读/写具有原子性, 但类似于volatile++这种复合操作不具有原子性;
 *
 * volatile写-读建立的happens-before关系:
 * 上面讲的是volatile变量自身的特性, 对程序员来说, volatile对线程的内存可见性的影响比volatile自身的特性更为重要;
 * 从JSR-133开始, volatile变量的写-读可以实现线程之间的通信; 什么叫实现线程之间的通信呢?
 * 从内存语义的角度来说, volatile与监视器锁有相同的效果:
 * 1). volatile写和监视器的释放有相同的内存语义;
 * 2). volatile读与监视器的获取有相同的内存语义;
 * 在下面VolatileDemo的代码中, 假设线程A执行writer()方法之后, 线程B执行reader()方法, 根据happens-before原则,
 * 这个过程可以建立的happens-before关系可以有如下几类:
 * 1). 根据程序次序规则, 1 happens before 2; 3 happens before 4;
 * 2). 据volatile规则, 2 happens before 3;
 * 3). 根据传递性规则, 1 happens before 4;
 * 这是什么意思呢? 我们可以看到第3条关系, 是由前两条推导出来的, 这完全符合happens-before原则; 根据第3条关系,
 * 回到VolatileDemo代码中, 我们可以看到, 1 happens before 4, 也就是说, a = 1这条写入是保证对int i = a可见的;
 * 也就是说, 当写入一个volatile变量时, 在这条写入语句之前所有的写入都被其他线程可见(也就是都被flush到了主存);
 * 具体来说就是, 这里线程A写一个volatile变量后, 线程B读同一个volatile变量, 线程A在写volatile变量之前所有可见的共享变量,
 * 在线程B读同一个volatile变量后, 都将立即变得对B线程可见(注意这里的"都"字);
 *
 * volatile写-读的内存语义:
 * 1. volatile写的内存语义如下:
 *    当写一个volatile变量时, JMM会把该线程对应的本地内存中的共享变量刷新到主内存;
 * 2. volatile读的内存语义如下:
 *    当读一个volatile变量时, JMM会把该线程对应的本地内存置为无效, 强制线程接下来从主内存中读取共享变量;
 * 如果我们把volatile写和volatile读这两个步骤综合起来看的话, 在读线程B读一个volatile变量后,
 * 写线程A在写这个volatile变量之前所有可见的共享变量的值都将立即变得对读线程B可见;
 * 下面对volatile写和volatile读的内存语义做个总结:
 * 1). 线程A写一个volatile变量, 实质上是线程A向接下来将要读这个volatile变量的某个线程发出了(其对共享变量所做修改的)消息;
 * 2). 线程B读一个volatile变量, 实质上是线程B接收了之前某个线程发出的(在写这个volatile变量之前对共享变量所做修改的)消息;
 * 3). 线程A写一个volatile变量, 随后线程B读这个volatile变量, 这个过程实质上是线程A通过主内存向线程B发送消息;
 * 这就是为什么上面说"volatile变量的写-读可以实现线程之间的通信";
 * 那么JMM又是怎么实现volatile的内存语义的呢?
 *
 * JMM如何实现volatile写/读的内存语义:
 * 前文我们提到过重排序分为编译器重排序和处理器重排序; 为了实现volatile内存语义, JMM会分别限制这两种类型的重排序类型;
 * JMM针对编译器制定的volatile重排序规则为:
 * 1). 当第二个操作是volatile写时, 不管第一个操作是什么, 都不能重排序;
 *     这个规则确保volatile写之前的操作不会被编译器重排序到volatile写之后;
 * 2). 当第一个操作是volatile读时, 不管第二个操作是什么, 都不能重排序;
 *     这个规则确保volatile读之后的操作不会被编译器重排序到volatile读之前;
 * 3). 当第一个操作是volatile写, 第二个操作是volatile读时, 不能重排序;
 * 为了实现volatile的内存语义, 编译器在生成字节码时, 会在指令序列中插入内存屏障来禁止特定类型的处理器重排序;
 * 对于编译器来说, 发现一个最优布置来最小化插入屏障的总数几乎不可能, 为此, JMM采取保守策略;
 * 下面是基于保守策略的JMM内存屏障插入策略:
 * 1). 在每个volatile写操作的前面插入一个StoreStore屏障;
 *     解释: 禁止volatile写之前的普通写和volatile写重排序, 也保证了其前面的所有普通写操作已经对任意处理器可见了,
 *          这是因为StoreStore屏障将保障上面所有的普通写在volatile写之前刷新到主内存;
 * 2). 在每个volatile写操作的后面插入一个StoreLoad屏障;
 *     解释: 这个屏障的作用是避免volatile写与后面可能有的volatile读/写操作重排序;
 *          因为编译器常常无法准确判断在一个volatile写的后面, 是否需要插入一个StoreLoad屏障(比如,
 *          一个volatile写之后方法立即return); 为了保证能正确实现volatile的内存语义, JMM在这里采取了保守策略:
 *          在每个volatile写的后面或在每个volatile读的前面插入一个StoreLoad屏障; 从整体执行效率的角度考虑,
 *          JMM选择了在每个volatile写的后面插入一个StoreLoad屏障; 因为volatile写-读内存语义的常见使用模式是:
 *          一个写线程写volatile变量, 多个读线程读同一个volatile变量; 当读线程的数量大大超过写线程时,
 *          选择在volatile写之后插入StoreLoad屏障将带来可观的执行效率的提升;
 *          从这里我们可以看到JMM在实现上的一个特点: 首先确保正确性, 然后再去追求执行效率;
 * 3). 在每个volatile读操作的后面插入一个LoadLoad屏障;
 *     解释: LoadLoad屏障用来禁止处理器把volatile读与之后的普通读重排序, 并确保刷新了本地内存的内容(即从主存中读数据);
 * 4). 在每个volatile读操作的后面插入一个LoadStore屏障;
 *     解释: LoadStore屏障用来禁止处理器把volatile读与之后的普通写重排序, 并确保刷新了本地内存的内容(即从主存中读数据);
 * 上述内存屏障插入策略非常保守, 但它可以保证在任意处理器平台, 任意的程序中都能得到正确的volatile内存语义;
 */

class VolatileDemo {
    int a = 0;
    volatile boolean flag = false;

    public void writer() {
        a = 1;                // 1
        flag = true;          // 2
    }

    public void reader() {
        if (flag) {           // 3
            int i = a;        // 4
        }
    }
}

class VolatileFeatureDemo1 {
    volatile long num = 0L;  // 使用volatile声明64位的long类型变量

    public void set(long num) {
        this.num = num;  // 单个的volatile变量的写
    }

    public void getAndIncrement() {
        num++;  // 复合(多个)的volatile变量的读/写
    }

    public long get() {
        return num;  // 单个的volatile变量的读
    }
}

class VolatileFeatureDemo2 {
    long num = 0L;  // 64位的long类型普通变量

    // 对单个的普通变量的写用一个监视器锁同步
    public synchronized void set(long num) {
        this.num = num;
    }

    public void getAndIncrement() {  // 普通方法, 未使用监视器锁同步
        long temp = get();  // 调用已同步的读方法
        temp += 1L;  // 普通写操作
        set(temp);  // 调用已同步的写方法
    }

    // 对单个的普通变量的读用一个监视器锁同步
    public synchronized long get() {
        return num;
    }
}

public class VolatilePrinciple {
    public static void main(String[] args) {
    }
}
