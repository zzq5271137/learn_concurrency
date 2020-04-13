package _06_ThreadLocal._05_NullPointerExeceptionInThreadLocal;

/*
 * 演示ThreadLocal发生空指针异常(NullPointerException)的情况;
 */

public class ThreadLocalNPE {
    private static ThreadLocal<Long> longLocal = new ThreadLocal<>();

    public static void set() {
        longLocal.set(Thread.currentThread().getId());
    }

    /*
     * 这里这个方法的返回值类型是一个基本类型long, 而我们ThreadLocal中存储的是一个包装类型Long,
     * 当还没执行set()而直接执行get()去取的话, 会报NPE空指针异常(如下面主函数中的例子);
     * 因为, 执行longLocal.get()返回的是一个包装类型Long的对象, 而我们的get()方法需要返回基本类型long,
     * 所以这里做了一个自动拆箱的操作, 即把Long类型转成基本类型, 需要调用Long对象的longValue()方法;
     * 而因为这里没有执行set(), 也没有重写initialValue(), 所以调用longLocal.get()返回的是一个null,
     * null.longValue()当然会报NPE;
     */
    public static long get() {
        return longLocal.get();
    }

    public static void main(String[] args) {
        System.out.println(get());
    }
}
