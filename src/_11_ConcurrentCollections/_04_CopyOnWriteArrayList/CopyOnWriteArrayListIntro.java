package _11_ConcurrentCollections._04_CopyOnWriteArrayList;

/*
 * CopyOnWriteArrayList诞生记:
 * 1. CopyOnWriteArrayList是用来代替Vector和Collections.synchronizedList的,
 *    原因就和ConcurrentHashMap代替Collections.synchronizedMap一样,
 *    因为Vector和Collections.synchronizedList的锁的粒度太大, 并发效率相比较低,
 *    并且迭代时无法编辑(因为被锁住了);
 * 2. Copy-On-Write并发容器还包括CopyOnWriteArraySet, 用来代替同步Set;
 *
 * CopyOnWriteArrayList适用场景:
 * 1. 需要读操作尽可能地快, 而写操作即使慢一些也没有太大关系;
 * 2. 读操作的数量远远大于写操作的数量;
 *
 * CopyOnWriteArrayList的读写规则:
 * 回顾读写锁的读写规则:
 * 1. 读锁和写锁是互斥的, 即读锁和写锁不能同时存在;
 * 2. 读锁可以由多个线程同时持有, 写锁只能由一个线程持有;
 * 3. 获取读锁后, 只能读, 获取写锁后, 既能读又能写;
 * 即读读共享, 其他都互斥(读写互斥, 写读互斥, 写写互斥);
 * CopyOnWriteArrayList的读写规则:
 * 读取是完全不用加锁的, 并且写入也不会阻塞读取操作, 就是说即便有一个线程在写, 其他线程依然可以读,
 * 只有写入与写入之间需要进行同步等待; 即只有写写互斥, 其他都不互斥;
 * 详见CopyOnWriteArrayListDemo1.java
 */

public class CopyOnWriteArrayListIntro {
    public static void main(String[] args) {
    }
}
