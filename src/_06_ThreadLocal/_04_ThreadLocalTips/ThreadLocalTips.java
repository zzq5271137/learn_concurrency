package _06_ThreadLocal._04_ThreadLocalTips;

/*
 * 什么是内存泄漏:
 * 某个对象不再有用(或者说不再可以被获取), 但是这个对象占用的内存却不能被回收;
 * 如果程序中有很多内存泄漏的情况, 那么最终可能导致OOM错误(OutOfMemoryError);
 *
 * ThreadLocal可能会有内存泄漏的风险; 因为在ThreadLocalMap这个类中的内部类Entry中,
 * 使用了弱引用(WeakReference)去关联key(也就是ThreadLocal对象), 但使用了强引用(StrongReference)去关联value;
 * 弱引用的特点是, 当一个对象只被弱引用关联时, 这个对象随时可能被垃圾回收器回收;
 * 正常情况下, 当线程终止, 保存在线程对象中的threadLocals(ThreadLocalMap对象)就会被回收,
 * 那自然threadLocals中的每一个Entry(包括key和value)也会被回收, 这是没有问题的(也就是说,
 * 当线程不会持续太长时间, 是不会造成value所指向的对象的内存泄漏的, 即使key采用的是弱引用);
 * 但是, 当线程不终止(比如线程需要保持很久, 如线程池的情况), 那么value所指向的对象就不能被回收, 因为有以下调用链:
 *     Thread -> ThreadLocalMap对象(threadLocals) -> Entry -> value
 * 但是此时Entry里的key可能已经被回收了(因为它采用的是弱引用), 所以value我们永远也拿不到, 这就造成了内存泄漏;
 * 也就是说, 当线程没有终止, 而且该线程中没有任何地方在用这个ThreadLocal对象(key)时, 这个ThreadLocal对象就可能会被回收;
 * 但是, 因为value所指向的对象(也就是我们存储进ThreadLocal的对象)还有一个强引用在关联着它(也就是这个value变量),
 * 所以value所指向的对象不会被回收, 但这个value所指向的对象永远也拿不到了; 这就是value所指向的对象的内存泄漏的情况;
 * 当有很多这种情况出现时(比如我们在线程中使用了很多ThradLocal去存储), 最终可能导致OOM;
 *
 * JDK其实已经考虑到了这个问题, 所以在set()、remove()、rehash()、resize()等方法中会扫描key为null的Entry,
 * 并把对应的value设置为null(打开value所指向的对象的强引用), 这样, value所指向的对象就可以被回收了;
 * 但是如果一个ThreadLocal不再被使用, 那么实际上set()、remove()、rehash()、resize()这些方法也不会被调用,
 * 所以依然有value内存泄漏的可能;
 */

public class ThreadLocalTips {
    public static void main(String[] args) {
    }
}
