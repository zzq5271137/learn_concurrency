package _02_JavaMemoryModel._05_SingletonPattern;

/*
 * 单例模式: 懒汉式(双重检查) [推荐使用]
 */

public class SingletonType6 {
    /*
     * 参考: https://coding.imooc.com/learn/questiondetail/142039.html
     *
     * 需要用volatile去声明instance是因为, 创建对象这一条语句并不具备原子性,
     * "instance = new SingletonType6();"这条语句在JVM中实际上对应3个步骤:
     * 1. 执行new关键字:
     *    给对象分配内存;
     * 2. 执行SingletonType6()构造方法:
     *    调用SingletonType6()构造方法来初始化成员变量;
     * 3. 执行instance变量的赋值:
     *    将该对象的引用赋值给instance变量(将instance指向分配的内存空间), 执行完这一步, instance就非null了;
     *
     * 但是在JVM的即时编译器中存在指令重排序的优化, 也就是说上面的第2步和第3步的顺序是不能保证的,
     * 最终的执行顺序可能是1-2-3或者1-3-2; 如果是后者, 则在3执行完毕、2未执行之前, 如果CPU时钟切出当前线程, 切到线程B,
     * 此时线程B刚刚进来第一重检查, 看到的instance已经是非null了(但其实并没有初始化, 里面的属性值可能是null/false/0,
     * 例如下面例子中, 在这种情况, 线程B将看到a = 0, b = 0, c = 0, 而该对象真正需要的是a = 1, b = 2, c = 3),
     * 所以线程B会直接返回instance, 然后使用, 然后顺理成章地报错或者是看到了非预期的值(因为这个instance对象的属性并没有初始化);
     * 所以我们要使用volatile关键字来禁止重排序;
     */
    private volatile static SingletonType6 instance;

    private int a;
    private int b;
    private int c;

    private SingletonType6() {
        // 假设初始化该类的实例对象时需要初始化好几个属性
        a = 1;
        b = 2;
        c = 3;
    }

    /*
     * 使用双重检查的作用是为了防止这种情况, 当多个线程同时执行到第一重检查时, 由于此处代码并不是同步的,
     * 他们可以同时进行第一重检查的判断, 又由于现在instance是null, 所以他们的第一重检查都判断成功,
     * 他们都会进入if分支; 如果没有第二重检查, 这些同时进入的线程都会去创建instance的实例,
     * 虽然创建实例的语句有synchronized做同步, 但也仅仅是让他们不会在同一时刻创建, 而是串行地创建;
     *
     * 双重检查优点: 线程安全, 延迟加载, 效率较高;
     */
    public static SingletonType6 getInstance() {
        if (instance == null) {
            synchronized (SingletonType6.class) {
                if (instance == null) {
                    instance = new SingletonType6();
                }
            }
        }
        return instance;
    }
}
