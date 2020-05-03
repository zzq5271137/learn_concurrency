package _11_ConcurrentCollections._01_Intro;

/*
 * 演示Collections.synchronizedList(new ArrayList<E>()), 它是一个线程安全的ArrayList;
 * 通过观察源码, 我们发现, 和Collections.synchronizedMap(new HashMap<K,V>())一样,
 * 这个类里面的方法虽然不像Vector和Hashtable一样直接使用synchronized修饰,
 * 它使用的是同步代码块的形式去实现线程安全的; 但是这些方法的同步代码块中使用的都是同一把锁, 所以它的性能依然很低;
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynListDemo {
    public static void main(String[] args) {
        List<String> synList = Collections.synchronizedList(new ArrayList<>());
        synList.add("test");
        System.out.println(synList.get(0));
    }
}
