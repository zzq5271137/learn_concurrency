package _11_ConcurrentCollections._04_CopyOnWriteArrayList;

/*
 * 对比两个CopyOnWriteArrayList的迭代器, 演示迭代的数据可能是过期的;
 */

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo2 {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        System.out.println("初始化后: " + list);
        System.out.println("创建迭代器1");
        Iterator<Integer> iterator1 = list.iterator();
        System.out.println("添加元素\"4\"");
        list.add(4);
        System.out.println("添加元素\"4\"后: " + list);
        System.out.println("创建迭代器2");
        Iterator<Integer> iterator2 = list.iterator();

        System.out.print("迭代器1的迭代内容: ");
        iterator1.forEachRemaining(System.out::print);
        System.out.print("\n迭代器2的迭代内容: ");
        iterator2.forEachRemaining(System.out::print);
        System.out.println();
    }
}
