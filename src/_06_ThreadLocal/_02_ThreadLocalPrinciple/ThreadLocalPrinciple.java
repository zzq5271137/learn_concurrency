package _06_ThreadLocal._02_ThreadLocalPrinciple;

/*
 * Thread、ThreadLocal、ThreadLocalMap三者之间的关系:
 * 每个Thread对象中都持有一个类型为ThreadLocalMap的成员变量, 这个ThreadLocalMap对象, 是一个封装好的Map集合(key-value),
 * 它以ThreadLocal对象作为key进行存储, 存储的内容就是我们使用ThreadLocal进行存储的内容;
 * 这样, 一个线程就可以拥有多个使用ThreadLocal存储的对象(比如在实际场景中, 我们可能有多个工具类或多个需要线程私有的全局对象);
 */

public class ThreadLocalPrinciple {
    public static void main(String[] args) {
    }
}
