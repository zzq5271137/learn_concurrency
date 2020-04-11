package _06_ThreadLocal._03_ReferencesTypes;

/*
 * 参考: https://blog.csdn.net/qq_39192827/article/details/85611873
 *      https://www.cnblogs.com/wjh123/p/11142176.html
 *
 * 在JDK1.2以前的版本中, 当一个对象不被任何变量引用, 那么程序就无法再使用这个对象; 也就是说, 只有对象处于可触及状态,
 * 程序才能使用它; 这就像在商店购买了某样物品后, 如果有用就一直保留它, 否则就把它扔到垃圾箱, 由清洁工人收走;
 * 一般说来, 如果物品已经被扔到垃圾箱, 想再把它捡回来使用就不可能了; 但有时候情况并不这么简单,
 * 可能会遇到可有可无的"鸡肋"物品; 这种物品现在已经无用了, 保留它会占空间, 但是立刻扔掉它也不划算,
 * 因为也许将来还会派用场; 对于这样的可有可无的物品, 如果家里空间足够, 就先把它保留在家里, 如果家里空间不够,
 * 即使把家里所有的垃圾清除, 还是无法容纳那些必不可少的生活用品, 那么再扔掉这些可有可无的物品;
 * 在Java中, 虽然不需要程序员手动去管理对象的生命周期, 但是如果希望某些对象具备一定的生命周期的话
 * (比如内存不足时JVM就会自动回收某些对象从而避免OutOfMemory的错误)就需要用到软引用和弱引用了;
 * 从Java SE2开始, 就提供了四种类型的引用: 强引用、软引用、弱引用和虚引用;
 * Java中提供这四种引用类型主要有两个目的:
 * 第一是可以让程序员通过代码的方式决定某些对象的生命周期;
 * 第二是有利于JVM进行垃圾回收;
 *
 * Java中的4种引用:
 * 1. 强引用(Strong Reference)
 *    详见StrongReferenceDemo.java
 * 2. 软引用(Soft Reference)
 *    详见SoftReferenceDemo.java
 * 3. 弱引用(Weak Reference)
 *    详见WeakReferenceDemo.java
 * 4. 虚引用(Phantom Reference)
 *    详见PhantomReferenceDemo.java
 */

public class ReferencesTypes {
    public static void main(String[] args) {
    }
}
