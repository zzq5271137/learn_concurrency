package _01_EightCores._02_Core2_StartThreads;

/*
 * 演示不能执行两次start()方法
 */

public class CantStartTwice {
    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        thread.start();
    }
}
