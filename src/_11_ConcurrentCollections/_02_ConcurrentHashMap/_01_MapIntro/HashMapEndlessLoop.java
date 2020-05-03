package _11_ConcurrentCollections._02_ConcurrentHashMap._01_MapIntro;

/*
 * 演示在Java1.7下, HashMap在多线程下造成死循环并导致CPU利用率100%的情况;
 * 核心原因在于, HashMap在多线程下同时扩容的时候, 可能会造成循环链表, 导致CPU利用率100%;
 * 详细分析: https://coolshell.cn/articles/9606.html
 */

import java.util.HashMap;

public class HashMapEndlessLoop {
    private static HashMap<Integer, String> map = new HashMap<>(2, 1.5f);

    public static void main(String[] args) {
        map.put(5, "C");
        map.put(7, "B");
        map.put(3, "A");
        new Thread(() -> {
            map.put(15, "D");
            System.out.println(map);
        }, "Thread-1").start();
        new Thread(() -> {
            map.put(1, "E");
            System.out.println(map);
        }, "Thread-2").start();
    }
}
