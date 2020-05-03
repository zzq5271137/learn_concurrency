package _11_ConcurrentCollections._01_Intro;

/*
 * 演示Vector(可以把它理解为线程安全的ArrayList, 可以当做ArrayList来使用), 主要看Vector的源码;
 * 和Hashtable类一样, Vector类里面好多方法, 都是直接使用synchronized修饰的, 也就是以当前Vector对象作为锁,
 * 在高并发的场景下, 这会导致性能很低;
 */

import java.util.Vector;

public class VectorDemo {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("test");
        System.out.println(vector.get(0));
    }
}
