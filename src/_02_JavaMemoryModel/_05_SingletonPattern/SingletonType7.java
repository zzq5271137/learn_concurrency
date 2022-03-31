package _02_JavaMemoryModel._05_SingletonPattern;

/*
 * 单例模式: 懒汉式(静态内部类) [可用]
 */

public class SingletonType7 {
    private SingletonType7() {
    }

    private static class InstanceHolder {
        private static final SingletonType7 INSTANCE = new SingletonType7();
    }

    public static SingletonType7 getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
