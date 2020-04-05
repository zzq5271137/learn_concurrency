package _01_EightCores._01_Core1_CreateThreads.WrongWays;

/*
 * 使用Lambda表达式的方式实现线程
 */

public class LambdaStyle {
    public static void main(String[] args) {
        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
    }
}
