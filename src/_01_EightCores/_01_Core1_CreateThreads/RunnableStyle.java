package _01_EightCores._01_Core1_CreateThreads;

/*
 * 实现多线程方式一: 实现Runnable接口的方式实现线程
 */

public class RunnableStyle implements Runnable {
    @Override
    public void run() {
        System.out.println("实现Runnable接口的方式实现线程");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableStyle());
        thread.start();
    }
}
