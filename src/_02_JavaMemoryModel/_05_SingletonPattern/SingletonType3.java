package _02_JavaMemoryModel._05_SingletonPattern;

/*
 * 单例模式: 懒汉式(线程不安全) [不可用]
 */

public class SingletonType3 {
    private static SingletonType3 instance;

    private SingletonType3() {
    }

    public static SingletonType3 getInstance() {
        if (instance == null) {
            instance = new SingletonType3();
        }
        return instance;
    }
}
