package _02_JavaMemoryModel._05_SingletonPattern;

/*
 * 单例模式: 懒汉式(枚举) [推荐使用]
 */

public enum SingletonType8 {
    INSTANCE;

    // 代表这个类向外提供的一些功能
    public void whatever() {
        System.out.println("正在处理...");
    }

    public static void main(String[] args) {
        SingletonType8.INSTANCE.whatever();  // 调用
    }
}
