package _11_ConcurrentCollections._01_Intro;

/*
 * 演示Hashtable(可以把它理解为线程安全的HashMap), 主要看Hashtable的源码;
 * 和Vector类一样, Hashtable里面好多方法, 都是直接使用synchronized修饰的, 也就是以当前Hashtable对象作为锁,
 * 在高并发的场景下, 这会导致性能很低;
 */

import java.util.Hashtable;

public class HashtableDemo {
    public static void main(String[] args) {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("testKey", "testValue");
        System.out.println(hashtable.get("testKey"));
    }
}
