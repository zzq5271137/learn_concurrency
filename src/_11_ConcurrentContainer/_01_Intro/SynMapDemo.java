package _11_ConcurrentContainer._01_Intro;

/*
 * 演示Collections.synchronizedMap(new HashMap<K,V>()), 它是一个线程安全的HashMap;
 * 通过观察源码, 我们发现, 和Collections.synchronizedList(new ArrayList<E>())一样,
 * 这个类里面的方法虽然不像Vector和Hashtable一样直接使用synchronized修饰,
 * 它使用的是同步代码块的形式去实现线程安全的; 但是这些方法的同步代码块中使用的都是同一把锁, 所以它的性能依然很低;
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynMapDemo {
    public static void main(String[] args) {
        Map<String, String> synMap = Collections.synchronizedMap(new HashMap<>());
        synMap.put("testKey", "testValue");
        System.out.println(synMap.get("testKey"));
    }
}
