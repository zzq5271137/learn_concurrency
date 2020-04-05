package _01_EightCores._01_Core1_CreateThreads.WrongWays;

/*
 * 使用匿名内部类的方式实现线程
 */

public class AnonymousInnerClassStyle {
    public static void main(String[] args) {
        // 方式一
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }.start();

        // 方式二
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
}
