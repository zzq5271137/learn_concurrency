package _09_CAS;

/*
 * 什么是CAS:
 * CAS运用的场合一定是并发的; CAS是Compare-And-Swap的缩写, 即比较并替换, 它是一种思想/算法, 用来实现线程安全的算法;
 * 同时, Compare-And-Swap也是一种CPU指令, 这一条指令就能完成比较并替换的组合操作, 不会被打断(即具有原子性);
 * CAS主要被用在了并发编程领域, 实现那些不能被打断的交换操作; CAS的主要思路是: 我认为V的值应该是A, 如果是的话,
 * 我就把它改成B, 如果不是A(说明被别人修改过了), 那我就不修改了, 避免多人同时修改导致出错;
 * 其中, 这个"我认为V的值应该是A"的这个A, 可能是我之前读取出来的, 读取出来之后我在此基础上进行相关操作与计算,
 * 当我计算完成之后想写回去的时候, 我会先判断此时的值还是不是A, 如果是的话, 我就继续我的写入, 如果不是A,
 * 说明在我计算过程中有其他人修改过它的值了, 那我就不修改了; 其实这就是乐观锁的思想;
 *
 * CAS的具体操作:
 * 首先, CAS主要包含3个操作数:
 * 1. 内存值V, 即当前内存中的真实值;
 * 2. 预期值A, 即我认为的当前内存中的值;
 * 3. 要修改的值B, 即我想修改的值;
 * 首先, 会读取出现在的V值(即内存值V), 然后比较预期值A与内存值V, 当且仅当预期值A和内存值V相同时, 才将内存值修改为B,
 * 否则就不修改(宣告这一次CAS操作失败); 最后会返回内存值V(不管修改与否, 返回的都是当初读出来的那个内存值V);
 * CAS实际上最终是要利用CPU的特殊指令的, 首先这条指令由CPU保证了它的原子性, 其次, 一条指令就可以做好几件事情,
 * 即, 这一条指令可以去先读取当前值, 然后比较, 再去更新;
 *
 * CAS的应用场景:
 * 1. 乐观锁
 * 2. 并发容器
 * 3. 原子类
 *    详见CASInAtomicPackage.java
 */

public class Intro {
    public static void main(String[] args) {
    }
}
