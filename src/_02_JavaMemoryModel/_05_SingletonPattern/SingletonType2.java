package _02_JavaMemoryModel._05_SingletonPattern;

/*
 * 单例模式: 饿汉式(静态代码块) [可用]
 */

public class SingletonType2 {
    private final static SingletonType2 INSTANCE;

    static {
        INSTANCE = new SingletonType2();
    }

    private SingletonType2() {
    }

    public static SingletonType2 getInstance() {
        return INSTANCE;
    }
}
