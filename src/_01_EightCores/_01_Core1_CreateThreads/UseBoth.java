package _01_EightCores._01_Core1_CreateThreads;

/*
 * 同时使用两种方式实现线程:
 * 继承Thread类的方式中重写的run()方法会覆盖传入的Runnable的run()方法
 */

public class UseBoth {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我来自Runnable");
            }
        }) {
            @Override
            public void run() {
                System.out.println("我来自Thread");
            }
        }.start();
    }
}
