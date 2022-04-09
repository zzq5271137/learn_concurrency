package _06_ThreadLocal._03_ReferencesTypes;

/*
 * 参考: https://blog.csdn.net/qq_39192827/article/details/85611873
 *      https://www.cnblogs.com/wjh123/p/11142176.html
 *      https://www.bilibili.com/video/BV12f4y127bZ?spm_id_from=333.337.search-card.all.click
 *
 * 虚引用(Phantom Reference):
 * 虚引用和前面的软引用、弱引用不同, 它并不影响对象的生命周期; 在java中用java.lang.ref.PhantomReference类表示;
 * 如果一个对象与虚引用关联, 则跟没有引用与之关联一样, 在任何时候都可能被垃圾回收器回收; 虚引用主要用来跟踪对象被垃圾回收的活动;
 * 无法通过虚引用访问对象的任何属性或函数, 仅仅是提供了一种确保对象被finalize以后, 做某些事情的机制;
 * 虚引用必须和引用队列(ReferenceQueue)关联使用, 当垃圾回收器准备回收一个对象时, 如果发现它还有虚引用,
 * 就会在回收对象的内存之前, 把这个虚引用加入到与之关联的引用队列中; 程序可以通过判断引用队列中是否已经加入了虚引用,
 * 来了解被引用的对象是否将要被垃圾回收; 如果程序发现某个虚引用已经被加入到引用队列,
 * 那么就可以在所引用的对象的内存被回收之前采取必要的行动;
 * 应用场景: 可用来跟踪对象被垃圾回收器回收的活动, 当一个虚引用关联的对象被垃圾收集器回收之前会收到一条系统通知;
 * 虚引用可以用于管理"直接内存"（direct memory）。
 */

import java.lang.ref.ReferenceQueue;
import java.lang.ref.PhantomReference;
import java.util.concurrent.TimeUnit;

public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> pr = new PhantomReference<>(obj, referenceQueue);

        System.out.println(obj);
        System.out.println(pr.get());
        System.out.println(referenceQueue.poll());

        obj = null;
        System.gc();  // 通知JVM的gc进行垃圾回收
        TimeUnit.SECONDS.sleep(1);
        System.out.println("after gc:");

        System.out.println(obj);
        System.out.println(pr.get());
        System.out.println(referenceQueue.poll());
    }
}
