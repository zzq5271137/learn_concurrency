package _02_JavaMemoryModel._05_SingletonPattern;

/*
 * 单例模式: 饿汉式(静态常量) [可用]
 */

public class SingletonType1 {
    private final static SingletonType1 INSTANCE = new SingletonType1();

    private SingletonType1() {
    }

    public static SingletonType1 getInstance() {
        return INSTANCE;
    }
}
