package _11_ConcurrentContainer._02_ConcurrentHashMap._02_ConcurrentHashMapStructure;

/*
 * ConcurrentHashMap的结构:
 * ConcurrentHashMap在Java1.7和Java8中的结构是有区别的:
 * 1. 在Java1.7中:
 *    ConcurrentHashMap内部有一个个的segment(Segment的数组, 默认大小是16), 每个segment里面,
 *    都是一个类似于HashMap的结构, 即拉链法创建的数据结构, 即将hash值不同的元素存成数组, 数组内的每一个元素,
 *    是一个链表(即如果hash值相同, 则他们是以链表的形式串起来的);
 *    总结ConcurrentHashMap在Java1.7中的结构:
 *    a). 最外层是多个segment, 即一个Segment的数组(默认大小是16), 每个segment的底层数据结构与HashMap类似,
 *        仍然是数组和链表组成的拉链法;
 *    b). 每个segment使用各自独立的ReentrantLock锁(Segment内部类本身就是继承自ReentrantLock),
 *        每个segment之间互不影响, 所以提高了并发效率;
 *    c). ConcurrentHashMap默认是有16个segment, 所以可以支持最多16个线程的并发写(因为操作分别分步在不同的segment上),
 *        这个默认值可以在初始化的时候设置为其他值, 但是一旦初始化完成之后, segment是不能扩容的;
 * 2. 在Java8中:
 *    在Java8中, ConcurrentHashMap几乎被完全重写了, 代码量从1.7的1000多行变为了6000多行;
 *    首先第一个设计上的区别就是它不再采用segment的方式, 而是采用node; 另外它保证线程安全的方式是利用CAS加上synchronized;
 *    具体来说, ConcurrentHashMap在Java8中的结构和Java8中的HashMap类似, 即数组+链表+红黑树;
 *    在ConcurrentHashMap中通过一个Node<K,V>[]数组来保存添加到map中的键值对, 而在同一个数组位置,
 *    是通过链表和红黑树的形式来保存的;
 *
 * 详见: https://www.bilibili.com/video/BV16J411L7uE?from=search&seid=9801827668276256293
 *      https://www.bilibili.com/video/bv1x741117jq/
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapStructure {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("k1", "v1");
        System.out.println(map.get("k1"));
    }
}
