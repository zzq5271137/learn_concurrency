package _11_ConcurrentContainer._02_ConcurrentHashMap._02_ConcurrentHashMapStructure;

/*
 * ConcurrentHashMap的结构:
 * ConcurrentHashMap在Java1.7和Java8中的结构是有区别的:
 * 1. 在Java1.7中:
 *    ConcurrentHashMap内部有一个个的Segment(Segment的数组, 默认大小是16), 每个Segment里面,
 *    都是一个类似于HashMap的结构(老的1.7的HashMap的结构), 即拉链法的数据结构,
 *    即hash值不同的元素(HashEntry)存储在数组的不同位置上, hash值相同的元素存储在数组的相同位置上,
 *    在数组的每个位置上, HashEntry(他们的hash值相同)以链表的形式串起来(即如果HashEntry的hash值相同,
 *    这些HashEntry会通过next属性一个一个地串起来形成链表, 然后数组中的每个位置指向每个链表的头结点);
 *    总结ConcurrentHashMap在Java1.7中的结构:
 *    a). 最外层是多个Segment, 即一个Segment的数组(默认大小是16), 每个Segment的底层数据结构与HashMap类似,
 *        仍然是数组和链表组成的拉链法, 即每个Segment里有一个HashEntry<K,V>数组, HashEntry有一个属性是next,
 *        它可以指向另一个HashEntry, 所以, hash值相同或者通过hash值算出的数组下标值相同的HashEntry就会一个一个的链起来;
 *    b). 通过UNSAFE的CAS操作加while循环(自旋)去创建Segment数组中的每个Segment, 保证了多线程情况下的线程安全;
 *    c). 每个Segment使用各自独立的ReentrantLock锁(Segment内部类本身就是继承自ReentrantLock),
 *        每个Segment之间互不影响, 所以提高了并发效率;
 *    d). ConcurrentHashMap默认是有16个Segment, 所以可以支持最多16个线程的并发写(因为操作分别分布在不同的Segment上),
 *        这个默认值可以在初始化的时候设置为其他值, 但是一旦初始化完成之后, Segment是不能扩容的;
 *        但是每个Segment内部的HashEntry数组的大小, 随着插入的值越来越多, 会根据当前HashEntry数组的阈值(threshold),
 *        进行扩容(扩容成现在数组长度的2倍, 因为HashEntry数组的长度, 包括外层的Segment数组的长度, 都要求是2的幂次方数,
 *        这是为了将值更好地散列而设计的, 详见源码);
 * 2. 在Java8中:
 *    在Java8中, ConcurrentHashMap几乎被完全重写了, 代码量从1.7的1000多行变为了6000多行;
 *    首先第一个设计上的区别就是它不再采用Segment的方式, 而是采用Node; 另外它保证线程安全的方式是利用CAS和synchronized;
 *    具体来说, ConcurrentHashMap在Java8中的结构和Java8中的HashMap类似, 即数组+链表+红黑树;
 *    在ConcurrentHashMap中通过一个Node<K,V>[]数组来保存添加到map中的键值对, 而在同一个数组位置,
 *    是通过链表和红黑树的形式来保存的; 然后再利用CAS和synchronized来保证并发安全;
 *
 * 详见: https://www.bilibili.com/video/bv1x741117jq/
 */

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapStructure {
    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("k1", "v1");
        System.out.println(hashMap.get("k1"));
        hashMap.remove("k1", "v1");

        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("k1", "v1");
        System.out.println(concurrentHashMap.get("k1"));
        System.out.println(concurrentHashMap.size());
        concurrentHashMap.remove("k1", "v1");
    }
}
