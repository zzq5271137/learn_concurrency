package _02_JavaMemoryModel._05_SingletonPattern;

/*
 * 单例模式: 懒汉式(同步方法, 线程安全) [不推荐使用]
 */

public class SingletonType4 {
    private static SingletonType4 instance;

    private SingletonType4() {
    }

    /*
     * 这种方式不推荐使用是因为效率太低了;
     * 在实际场景中, 一个工具类或者统计类是会被很多线程同时使用的, 如果是以这种写法,
     * 那么那些线程就无法并行的拿到工具类的实例;
     */
    public synchronized static SingletonType4 getInstance() {
        if (instance == null) {
            instance = new SingletonType4();
        }
        return instance;
    }
}
