package _01_EightCores._06_Core6_ThreadAttributes;

/*
 * name: 作用是让用户或程序员在开发、调试或运行过程中, 更容易区分每个不同的线程、定位问题等;
 * ID是给JVM用的, Name是给人用的;
 */

public class ThreadName {
    public static void main(String[] args) {
        Thread thread = new Thread("MyThread-1");
        System.out.println(thread.getName());
        thread.setName("MyThread-changed");
        System.out.println(thread.getName());
    }
}
