package _11_ConcurrentCollections._04_CopyOnWriteArrayList;

/*
 * 演示CopyOnWriteArrayList可以在迭代的过程中修改内容, 但是ArrayList不行(会报ConcurrentModificationException异常);
 * 原因在于, ArrayList并不是并发安全的, 所以它提供了一种fast-fail(快速失败)的机制, 以提醒开发者,
 * 这里出现了并发安全问题, 它自己并没有去解决并发安全问题, 只是去告知开发者, 因为它本身就不是设计为并发安全的,
 * 很多非并发安全的容器都有这种fast-fail机制, 比如HashMap, 详见FastFailInHashMap.java;
 * 而CopyOnWriteArrayList是并发安全的, 所以不需要这种fast-fail机制, 它会在内部去进行同步;
 */

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo1 {
    public static void main(String[] args) {
        // ArrayList<String> list = new ArrayList<>();
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        System.out.println("迭代前: " + list);

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String content = iterator.next();
            System.out.println("list: " + list + ", current: " + content);

            /*
             * 从运行结果我们可以看到, 我们在"2"时删除了"5", 又在"3"时添加了"3 found",
             * 但是这个iterator迭代器依然遍历出了"5", 但是没有遍历出"3 found";
             * 这其实就是CopyOnWriteArrayList的特性;
             * "CopyOnWrite"的含义就是, 当进行写操作时, 不是直接操作原list, 而是复制一份出来,
             * 对这个复制出来的list进行写操作, 然后在合适的时机将原list重新指向这个新的list;
             * 即创建新副本, 读写分离; 这就是为什么CopyOnWriteArrayList支持读写不互斥的原理,
             * 因为这里的写操作实际上是在一个新的地方进行写的, 并不影响原list的读;
             * 所以, 这里的iterator迭代器依然遍历出了"5", 但是没有遍历出"3 found",
             * 因为这里迭代器使用的依然是旧的list(这也意味着, 对于CopyOnWrite的容器来说,
             * 迭代的时候的数据可能是过期的); 详见CopyOnWriteArrayListDemo2.java
             */
            if (content.equals("2")) {
                list.remove("5");
            }
            if (content.equals("3")) {
                list.add("3 found");
            }
        }
        System.out.println("迭代后: " + list);
    }
}
