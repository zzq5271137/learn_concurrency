package _01_EightCores._02_Core2_StartThreads;

/*
 * 对比start()和run()这两种启动线程的方式:
 * 1. 直接调用run()方法, 实际上是由当前线程(比如说main主线程)来执行run()里面的内容的,
 *    并没有启动一个新线程去执行;
 * 2. 由start()方法启动线程, 是由当前线程(比如说main主线程)告知JVM虚拟机,
 *    让其在空闲时启动一个新线程去执行run()里面的内容;
 */

public class StartAndRunMethod {
    public static void main(String[] args) {
        // run()方法启动线程(使用Lambda表达式创建线程)
        Runnable runnable = () -> {
            System.out.println("run(): " + Thread.currentThread().getName());
        };
        runnable.run();

        // start()方法启动线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("start(): " + Thread.currentThread().getName());
            }
        }).start();
    }
}
