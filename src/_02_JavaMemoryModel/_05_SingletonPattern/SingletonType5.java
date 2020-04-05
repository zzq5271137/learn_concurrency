package _02_JavaMemoryModel._05_SingletonPattern;

/*
 * 单例模式: 懒汉式(同步代码块, 线程不安全) [不可用]
 */

public class SingletonType5 {
    private static SingletonType5 instance;

    private SingletonType5() {
    }

    /*
     * 这种写法其实是线程不安全的, 如果两个线程同时执行if判断并同时进入if语句, 他们依然会分别创建实例;
     */
    public static SingletonType5 getInstance() {
        if (instance == null) {
            synchronized (SingletonType5.class) {
                instance = new SingletonType5();
            }
        }
        return instance;
    }
}
