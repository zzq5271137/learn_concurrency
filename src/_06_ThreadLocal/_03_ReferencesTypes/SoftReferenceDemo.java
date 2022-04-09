package _06_ThreadLocal._03_ReferencesTypes;

/*
 * 参考: https://blog.csdn.net/qq_39192827/article/details/85611873
 *      https://www.cnblogs.com/wjh123/p/11142176.html
 *      https://www.bilibili.com/video/BV12f4y127bZ?spm_id_from=333.337.search-card.all.click
 *
 * 软引用(Soft Reference):
 * 软引用是用来描述一些有用但并不是必需的对象, 在Java中用java.lang.ref.SoftReference类来表示;
 * 对于软引用关联着的对象, 只有在内存不足的时候JVM才会回收该对象, 即JVM会确保在抛出OutOfMemoryError之前,
 * 清理软引用指向的对象; 因此, 这一点可以很好地用来解决OOM的问题, 并且这个特性很适合用来实现缓存: 比如网页缓存、图片缓存等;
 * 软引用可以和一个引用队列(ReferenceQueue)联合使用, 如果软引用所引用的对象被垃圾回收器回收,
 * Java虚拟机就会把这个软引用加入到与之关联的引用队列中; 后续, 我们可以调用ReferenceQueue的poll()方法,
 * 来检查是否有它所关心的对象被回收; 如果队列为空, 将返回一个null, 否则该方法返回队列中前面的一个Reference对象;
 * 软引用在实际中有重要的应用, 例如浏览器的后退按钮, 这个后退时显示的网页内容可以重新进行请求或者从缓存中取出:
 * (1). 如果一个网页在浏览结束时就进行内容的回收, 则按后退查看前面浏览过的页面时, 需要重新构建;
 * (2). 如果将浏览过的网页存储到内存中会造成内存的大量浪费, 甚至会造成内存溢出, 这时候就可以使用软引用;
 * 应用场景: 软引用通常用来实现内存敏感的缓存; 如果还有空闲内存, 就可以暂时保留缓存, 当内存不足时清理掉,
 *         这样就保证了使用缓存的同时, 不会耗尽内存;
 */

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

public class SoftReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        SoftReference<Object> sr = new SoftReference<>(obj, referenceQueue);

        System.out.println(obj);
        System.out.println(sr.get());
        System.out.println(referenceQueue.poll());

        obj = null;
        System.gc();  // 通知JVM的gc进行垃圾回收
        TimeUnit.SECONDS.sleep(1);
        System.out.println("after gc:");

        System.out.println(obj);
        System.out.println(sr.get());
        System.out.println(referenceQueue.poll());
    }
}
