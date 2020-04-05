package _02_JavaMemoryModel._03_FieldVisibility;

/*
 * 参考: https://blog.csdn.net/breakout_alex/article/details/94379895
 *      https://www.infoq.cn/article/java-memory-model-1/
 *
 * 什么是内存屏障:
 * 内存屏障(Memory Barrier)是一个CPU指令; 基本上, 它是这样一条指令:
 * 1. 确保一些特定操作执行的顺序;
 * 2. 影响一些数据的可见性(可能是某些指令执行后的结果);
 * 我们都知道, 编译器和CPU可以在保证输出结果一样的情况下对指令重排序, 使性能得到优化;
 * 插入一个内存屏障, 相当于告诉CPU和编译器, 先于这个内存屏障命令的语句必须先执行, 后于这个命令的语句必须后执行;
 * 内存屏障另一个作用是强制更新一次缓存(本地内存或主内存), 例如:
 * 1. 一个写屏障会把这个屏障前写入的数据从本地内存刷新到主内存, 这样任何试图读取该数据的其他线程将得到最新值;
 * 2. 一个读屏障会把该线程的本地内存无效化, 然后强制其从主内存读取数据, 也就是刷新本地内存的内容;
 *
 * Java内存屏障的主要类型:
 * 不同硬件实现内存屏障的方式不同, JMM屏蔽了这种底层硬件平台的差异, 有JVM来为不同的平台生成相应的机器码;
 * Java内存屏障主要有Load和Store两大类:
 * 1. 对于Load Barrier(读屏障)来说, 在读指令前插入读屏障, 可以让本地缓存中的数据失效, 重新从主存加载数据;
 * 2. 对于Store Barrier(写屏障)来说, 在写指令后插入写屏障, 可以让写入本地内存的最新数据flush会主存;
 *
 * 对于Load和Store, 在实际使用中, 又分为以下4种:
 * 1. LoadLoad屏障
 *    序列: Load1; LoadLoadBarrier; Load2;
 *    解释: 确保Load1的数据装载, 之前于Load2及所有后续Load指令的装载;
 * 2. StoreStore屏障
 *    序列: Store1; StoreStoreBarrier; Store2;
 *    解释: 确保Store1要存储的数据对其他处理器可见(指刷新到主存), 之前于Store2及所有后续Store指令的存储;
 * 3. LoadStore屏障
 *    序列: Load1; LoadStoreBarrier; Store2;
 *    解释: 确保Load1的数据装载, 之前于Store2及所有后续的Store指令flush到内存;
 * 4. StoreLoad屏障
 *    序列: Store1; StoreLoadBarrier; Load2;
 *    解释: 确保Store1要存储的数据对其他处理器变得可见(指刷新到主存), 之前于Load2及所有后续Load指令的装载;
 *         StoreLoad屏障会使该屏障之前的所有内存访问指令(Load和Store指令)完成之后, 才执行该屏障之后的内存访问指令;
 */

public class MemoryBarrier {
    public static void main(String[] args) {
    }
}
