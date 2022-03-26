package _09_CAS;

/*
 * atomic包下的很多类具有原子性的原理, 就是使用了CAS;
 *
 * 以AtomicInteger为例, AtomicInteger是如何使用CAS并保证线程安全的:
 * 1. AtomicInteger会加载一个Unsafe工具, 这个工具可以用来直接操作内存数据, 使用Unsafe来实现底层操作;
 *    Unsafe类是CAS的核心类, Java无法直接访问底层操作系统, 而是通过本地(native)方法来访问,
 *    所以, JDK提供了一个Unsafe类, 它提供了硬件级别的原子操作, 比如这里的CAS操作;
 *    Unsafe最终调用的都是native的方法, 用来执行CAS指令;
 *    追到底层, 最终执行的cpu指令是"lock cmpxchg", 其中, "lock"前缀, 表示在执行"cmpxchg"指令时, 使用"总线锁"或者"缓存锁"的方式, 保证"cmpxchg"指令的原子性;
 * 2. 在Unsafe的方法中, 使用CAS指令(native方法)加上自旋操作(CAS加自旋, 其实就是乐观锁的重试策略的实现),
 *    实现了AtomicInteger的原子性; 自旋操作的目的是, 如果此次CAS更新失败, 就重新获取, 然后再次更新, 直到更新成功;
 * 3. 在AtomicInteger中, 用volatile修饰value字段, 保证可见性;
 * 详见AtomicInteger源码;
 */

public class CASInAtomicPackage {
    public static void main(String[] args) {
    }
}
