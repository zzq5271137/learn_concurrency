package _07_Lock._04_TypesOfLock.OptimisticPessimisticLock;

/*
 * 为什么会诞生乐观锁:
 * 乐观锁又叫非互斥同步锁, 悲观锁又叫互斥同步锁;
 * 为什么会诞生非互斥同步锁呢? 因为互斥同步锁有一些劣势:
 * 1. 阻塞和唤醒带来的性能劣势
 *    因为当一个线程获得了某一资源的互斥同步锁时, 这个资源就是独占的, 其他线程如果想获得资源就必须等待;
 *    这就会带来性能问题, 性能问题主要发生在线程的阻塞和唤醒阶段, 里面包括一系列的操作,
 *    比如用户态和核心态的切换、上下文切换、检查是否有被阻塞线程需要被唤醒等等, 会消耗大量的系统资源, 带来性能损耗;
 * 2. 悲观锁可能使线程进入永久阻塞
 *    如果持有锁的线程因为某些原因始终不释放锁, 比如遇到了无限循环、死锁等活跃性问题, 那么等待该线程释放锁的那几个悲催的线程,
 *    就会进入永久阻塞, 将永远得不到执行;
 * 3. 优先级反转
 *    如果正在持有锁的线程优先级比较低, 反而正在等待锁而进入阻塞的线程优先级比较高, 这会导致优先级反转;
 *    因为我们设置了优先级, 想要优先级高的线程多运行, 优先级低的线程少运行; 可一旦优先级低的线程拿到锁之后,
 *    它不释放锁或者释放的比较慢, 那么即便其他线程优先级高, 也得不到执行; 所以导致优先级高的线程实际看起来比较低,
 *    而优先级低的线程却始终在执行;
 *
 * 什么是乐观锁与悲观锁:
 * 乐观锁和悲观锁是从是否锁住资源的角度来分的; 乐观锁和悲观锁是一种思想; 乐观锁认为, 事情总会很顺利地进行,
 * 所以不锁住资源, 等出问题了再来解决; 而悲观锁认为, 出错是一种常态, 所以要锁住资源避免出错;
 * 具体来说:
 * 1. 悲观锁: 认为如果我不锁住这个资源, 别人就会来抢, 就会造成数据结果错误, 所以悲观锁为了确保结果的正确性,
 *           会在每次获取并修改数据时, 把数据锁住, 让别人无法访问该数据, 这样就可以确保数据内容万无一失;
 *           在Java中, 典型的悲观锁的例子是synchronized和Lock相关的类;
 *           在数据库中, select ... for update, 就是悲观锁;
 * 2. 乐观锁: 认为自己在处理操作的时候不会有其他线程来干扰, 所以并不会锁住被操作的对象, 在实际更新资源的时候,
 *           再去对比在我修改的期间数据有没有被其他人改过, 如果没有被改过, 就说明真的是只有我自己在操作,
 *           那我就正常去修改数据; 如果数据和我一开始拿到的不一样了, 就说明其他人在这段时间内改过数据,
 *           那我就不能继续刚才的更新数据过程了, 我会选择放弃、报错、重试等策略;
 *           乐观锁一般都是利用CAS算法来实现的; 在Java中, 典型的乐观锁的例子是原子类和并发容器等;
 *           其他乐观锁的例子如, Git的往远端仓库push代码就是乐观锁的行为;
 *           又例如在数据库中, 使用version字段就是乐观锁:
 *           1). select * from table;
 *               先查出version, 比如说查出来的version为1;
 *           2). update set num = 2, version = version + 1 where version = 1 and id = 5;
 *               在更新时的where条件里加上原version的值, 并且set里让version加1,
 *               如果更新时查不到version为1的那条记录, 说明之前查出的那条记录在我操作期间被其他人更新了, 这里就会更新失败;
 *
 * 悲观锁与乐观锁的开销对比:
 * 悲观锁的原始开销要高于乐观锁, 但是悲观锁的特点是一劳永逸, 即开销是固定的, 因为开销一共就是加锁、解锁、上下文切换等的固定开销,
 * 不会有多余开销, 就算那个持有锁的线程持有的时间再长, 等待锁的线程也不会去做多于的操作;
 * 相反, 对于乐观锁来说, 虽然乐观锁一开始的开销比悲观锁小, 但是如果自旋时间很长或者不停重试, 那么消耗的资源也会越来越多;
 *
 * 悲观锁与乐观锁各自的使用场景:
 * 1. 悲观锁
 *    适合于并发写很多的情况, 适用于临界区持锁时间比较长的情况, 悲观锁可以避免大量的无用自旋等消耗,
 *    典型的情况如, 临界区有I/O操作、临界区代码复杂或者循环量大、临界区竞争非常多(并发量高)等场景;
 * 2. 乐观锁
 *    适合于并发写入少, 大部分是读取的场景, 不加锁能让读取性能大幅提高;
 */

import java.util.concurrent.atomic.AtomicInteger;

public class OptimisticPessimisticLock {
    int a;

    // 悲观锁的例子
    public synchronized void testMethod() {
        a++;
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();  // 乐观锁的例子
    }
}
