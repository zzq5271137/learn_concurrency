package _06_ThreadLocal._03_ReferencesTypes;

/*
 * 参考: https://blog.csdn.net/qq_39192827/article/details/85611873
 *      https://www.cnblogs.com/wjh123/p/11142176.html
 *      https://www.bilibili.com/video/BV12f4y127bZ?spm_id_from=333.337.search-card.all.click
 *
 * 强引用(Strong Reference):
 * 我们使用的大部分引用实际上都是强引用, 这是使用最普遍的引用; 比如下面这段代码中的object和str都是强引用:
 *     Object object = new Object();
 *     String str = "StrongReference";
 * 如果一个对象具有强引用, 那就类似于必不可少的物品, 不会被垃圾回收器回收; 当内存空间不足,
 * Java虚拟机宁愿抛出OutOfMemoryError错误, 使程序异常终止, 也不回收这种对象;
 *
 * 例如下方代码的例子, 当运行至Object[] objArr = new Object[Integer.MAX_VALUE]时, 如果内存不足,
 * JVM会抛出OOM错误也不会回收object指向的对象; 不过要注意的是, 当method1运行完之后, object和objArr都已经不存在了,
 * 所以它们指向的对象都会被JVM回收;
 *
 * 强引用的中断:
 * 如果想中断强引用和某个对象之间的关联, 可以显示地将引用赋值为null, 这样一来的话, JVM在合适的时间就会回收该对象;
 * 比如ArraryList类的clear()方法中就是通过将引用赋值为null来实现清理工作的;
 * 在ArrayList类中定义了一个私有的变量elementData数组, 在调用方法清空数组时可以看到为每个数组内容赋值为null;
 * 不同于elementData = null, 强引用仍然存在, 避免在后续调用add()等方法添加元素时进行重新的内存分配;
 * 使用如clear()方法中释放内存的方法对数组中存放的引用类型特别适用, 这样就可以及时释放内存;
 */

public class StrongReferenceDemo {
    public void method1() {
        Object object = new Object();
        Object[] objArr = new Object[Integer.MAX_VALUE];
    }

    public static void main(String[] args) {
        new StrongReferenceDemo().method1();
    }
}
