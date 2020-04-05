package _02_JavaMemoryModel._03_FieldVisibility;

/*
 * 我们都知道, 对单个的volatile变量的读/写具有原子性, 对volatile变量的单个读/写,
 * 等价于使用同一个监视器锁对这些单个读/写操作做了同步;
 * 具体来说, volatile关键字适用于以下场合:
 * 1. boolean flag
 *    如果一个共享变量自始至终只会被各个线程进行单次的读或写, 例如一个boolean类型的标记位变量,
 *    那么就可以用volatile来代替synchronized或代替原子变量, 因为单个的对volatile变量的读/写具有原子性,
 *    volatile又可以保证可见性, 所以就足以保证线程安全(而且volatile是一种轻量级的同步, 它不会产生线程的上下文切换,
 *    比synchronized效率更高);
 * 2. volatile变量作为刷新之前变量的触发器
 *
 * 这里演示volatile适用场合二;
 * 在这个例子中, writer()方法中, 先对a、b、c进行赋值, 然后再将flag置为true(意为已经对共享变量做了修改),
 * 由于flag是用volatile声明的, 所以保证了a = 1、b = 2、c = 3这些赋值(写入操作)对其他线程的可见性,
 * 这是由volatile的内存语义所保证的(volatile的内存语义详见VolatilePrinciple.java);
 * 具体来说, 当线程A先执行writer()方法, 然后线程B执行reader()方法, 那么线程B在执行a、b、c的打印(读取操作)时,
 * 一定能看到a已经被赋值为1、b已经被赋值为2、c已经被赋值为3; 这就是volatile变量作为触发器的用法;
 */

public class VolatileUsefulDemo2 {
    int a = 0;
    int b = 0;
    int c = 0;
    volatile boolean flag = false;

    public void writer() {
        a = 1;
        b = 2;
        c = 3;
        flag = true;
    }

    public void reader() {
        if (flag) {
            System.out.println("a = " + a + ", b = " + b + ", c = " + c);
        } else {
            System.out.println("a尚未被修改");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileUsefulDemo2 instance = new VolatileUsefulDemo2();
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.writer();
            }
        }, "ThreadA").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.reader();
            }
        }, "ThreadB").start();
    }
}
