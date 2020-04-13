package _06_ThreadLocal._02_ThreadLocalPrinciple;

/*
 * Thread、ThreadLocal、ThreadLocalMap三者之间的关系:
 * 每个Thread对象中都持有一个类型为ThreadLocalMap的成员变量(叫做threadLocals), 这个ThreadLocalMap对象,
 * 是一个封装好的Map集合(key-value), 它以ThreadLocal对象作为key进行存储, 存储的内容就是我们使用ThreadLocal进行存储的内容;
 * 由于threadLocals是对象的成员变量, 所以是每个Thread对象(线程)独有的; 也就是说, 即使key所指向的对象可能是很多线程共有的,
 * (比如我们在使用ThreadLocal时, 经常把ThreadLocal对象设置为静态的), 但我们从ThreadLocal中取出我们存储的对象(get()方法),
 * 实际上是从Thread对象的threadLocals中取的(get()放法中的getMap()方法), 所以即使key是共有的, 但value是线程独占的;
 * 这就是我们所说的, 存入ThreadLocal的对象, 是每一个线程各有一个;
 * 而且, 一个线程可以拥有多个使用ThreadLocal存储的对象(比如在实际场景中, 我们可能有多个工具类或多个需要线程私有的全局对象),
 * 因为threadLocals是一个集合, 里面有多个Entry(key-value, key为ThreadLocal对象, value为我们存入的对象);
 */

public class ThreadLocalPrinciple {
    public static void main(String[] args) {
    }
}
