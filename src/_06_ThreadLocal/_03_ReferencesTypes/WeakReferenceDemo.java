package _06_ThreadLocal._03_ReferencesTypes;

/*
 * 参考: https://blog.csdn.net/qq_39192827/article/details/85611873
 *      https://www.cnblogs.com/wjh123/p/11142176.html
 *      https://www.bilibili.com/video/BV12f4y127bZ?spm_id_from=333.337.search-card.all.click
 *
 * 弱引用(Weak Reference):
 * 弱引用也是用来描述非必需对象的, 当JVM进行垃圾回收时, 无论内存是否充足, 都会回收被弱引用关联的对象;
 * 在java中, 用java.lang.ref.WeakReference类来表示;
 * 弱引用与软引用的区别在于: 只具有弱引用的对象拥有更短暂的生命周期; 在垃圾回收器线程扫描它所管辖的内存区域的过程中,
 * 一旦发现了只具有弱引用的对象, 不管当前内存空间足够与否, 都会回收它的内存; 不过, 由于垃圾回收器是一个优先级很低的线程,
 * 因此不一定会很快发现那些只具有弱引用的对象; 总结来说就是, 被软引用关联的对象只有在内存不足时才会被回收,
 * 而被弱引用关联的对象在JVM进行垃圾回收时总会被回收; 弱引用可以和一个引用队列(ReferenceQueue)联合使用,
 * 如果弱引用所引用的对象被垃圾回收, Java虚拟机就会把这个弱引用加入到与之关联的引用队列;
 * 应用场景: 弱应用同样可用于内存敏感的缓存;
 */

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class WeakReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> wr = new WeakReference<>(obj, referenceQueue);

        System.out.println(obj);
        System.out.println(wr.get());
        System.out.println(referenceQueue.poll());

        obj = null;
        System.gc();  // 通知JVM的gc进行垃圾回收
        TimeUnit.SECONDS.sleep(1);
        System.out.println("after gc:");

        System.out.println(obj);
        System.out.println(wr.get());
        System.out.println(referenceQueue.poll());
    }
}
