package _11_ConcurrentCollections._03_ConcurrentHashMap._01_MapIntro;

/*
 * Map接口即其实现类简介:
 * Map是一个顶层接口, 它有很多实现类:
 * 1. HashMap
 *    HashMap是直接对Map接口的实现; HashMap会根据键的hashcode进行存储, 由于可以直接算出hashcode的值,
 *    可以直接定位到所需要找的位置, 所以HashMap的访问速度是非常快的; HashMap是线程不安全的,
 *    所以如果有多个线程同时对一个HashMap进行操作, 可能会导致数据不一致(线程安全问题);
 * 2. Hashtable
 *    Hashtable也是直接对Map接口的实现; Hashtable是一个历史遗留类, 它的很多功能都和HashMap一样,
 *    但Hashtable是线程安全的, 任何一个时刻只能有一个线程对它进行操作(因为它的方法是直接使用synchronized修饰的),
 *    但这也导致了它的并发性很低, 远不如ConcurrentHashMap;
 * 3. LinkedHashMap
 *    LinkedHashMap继承了HashMap; LinkedHashMap保存了记录的插入顺序, 在遍历的时候,
 *    我们遍历的顺序和插入的顺序是一致的;
 * 4. TreeMap
 *    TreeMap实现的是一个叫SortedMap的接口(这个接口继承自Map接口), 它具有排序的功能, 可以根据键去排序;
 * Map接口常用方法, 详见MapDemo.java
 *
 * 为什么HashMap是线程不安全的:
 * HashMap本身并不是设计为多线程访问的, 所以在并发的场景使用HashMap会有各种问题, 比如:
 * 1. 同时put()碰撞导致数据丢失;
 * 2. 同时put()扩容导致数据丢失;
 * 3. 死循环造成的CPU利用率100%(主要存在于Java7中);
 *
 * 想保证线程安全, 为什么不用Collections.synchronizedMap():
 * 之前分析过了, 因为它是在方法内使用同步代码块来控制并发访问的, 同步代码块使用的都是同一把锁, 所以导致并发性很低;
 */

public class MapIntro {
    public static void main(String[] args) {
    }
}
