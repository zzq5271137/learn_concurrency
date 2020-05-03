package _11_ConcurrentCollections._03_ConcurrentHashMap._02_ConcurrentHashMapStructure;

/*
 * 演示HashMap无法在迭代时进行修改;
 */

import java.util.HashMap;
import java.util.Iterator;

public class FastFailInHashMap {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        System.out.println("迭代前: " + map);

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.equals("k2")) {
                /*
                 * 如果在迭代过程中对map进行修改, 会报ConcurrentModificationException异常;
                 * 这其实是一种fast-fail机制(快速失败), 这是一种容错机制(通过modCount去实现, 详见源码);
                 * 因为我们都知道, HashMap并不是并发安全的, 所以, 当它发现在自己迭代(读)过程中,
                 * 有修改(写)操作时, 它会报出ConcurrentModificationException异常,
                 * 以提醒开发者, 这里有并发安全问题; 它自己并没有去解决并发安全问题, 只是去告知开发者,
                 * 因为它本身就不是设计为并发安全的; 不仅是HashMap, 很多其他非并发安全的容器都有这种机制,
                 * 比如ArrayList, 详见CopyOnWriteArrayListDemo1.java
                 */
                // map.remove("k2");

                /*
                 * 解决这个异常的办法, 可以使用iterator的remove()方法, 详见源码;
                 */
                iterator.remove();
            }
        }
        System.out.println("迭代后: " + map);
    }
}
