package _02_JavaMemoryModel._02_OutOfOrderExecution;

/*
 * 参考: https://www.jianshu.com/p/e0e80d004a86
 *      https://www.jianshu.com/p/8a58d8335270
 *
 * 此处演示重排序(Out Of Order Execution)的现象;
 *
 * 由于重排序不是百分之百每次发生的, 所以这里我们需要做多次重复, 直到某个条件达成才停止;
 * 下面两个子线程中4行代码的执行顺序决定了最终x和y的结果, 乍一看, 一共有3中情况:
 * 1. x = 0, y = 1
 *    四行代码执行顺序为: a = 1; x = b; b = 1; y = a;
 * 2. x = 1, y = 0
 *    四行代码执行顺序为: b = 1; y = a; a = 1; x = b;
 * 3. x = 1, y = 1
 *    四行代码执行顺序为: b = 1; a = 1; x = b; y = a;
 * 因为我们这么分析的出发点是, 在单个线程内的两行代码的执行顺序是不会变的, 也就是线程1的a = 1会在x = b前执行, 线程2的b = 1会在y = a前执行;
 * 但实际上, 下面的代码还有第4种结果, 就是x = 0, y = 0;
 * 得到这种结果的代码执行顺序可能是: y = a; a = 1; x = b; b = 1, 这里就发生了重排序, 即把线程2的两行语句执行顺序颠倒了;
 * 再来定义一下什么是重排序, 在线程2内部的两行代码的实际执行顺序和代码在Java文件中的顺序不一致,
 * 即代码指令并不是严格按照代码语句顺序执行的, 他们的顺序被改变了, 这就是重排序;
 *
 * 重排序的好处, 是可以提高处理速度, 重排序可以对代码进行指令优化;
 * 重排序的3中情况:
 * 1. 编译器优化:
 *    编译器优化是指编译器(包括JVM、JIT编译器等)出于优化的目的, 在编译的过程中会进行一定程度的重排序,
 *    导致生成的机器指令和之前的字节码的顺序不一致;
 *    例如在下面这个例子中:
 *        a = 3;
 *        b = 2;
 *        a = a + 1;
 *    编译器很可能把指令优化成(当然这不是指令, 只是为了演示方便, 使用Java代码演示编译器优化后指令的执行顺序):
 *        a = 3;
 *        a = a + 1;
 *        b = 2;
 *    即, 由于对a操作的两行代码和对b操作的一行代码之间没有数据依赖关系, 所以可以把对a操作的两行代码放到一起执行,
 *    所以, 优化后的指令只需要load一次a和写入一次a即可, 而优化前需要load两次和写入两次, 这样就提高了效率;
 *    而在下面代码的例子中, 编译器将y = a和b = 1这两行语句换了顺序(或者也可能是线程1的两行语句换了顺序, 同理),
 *    因为这两行语句之间没有数据依赖关系, 所以最后会得到x = 0, y = 0这种结果;
 * 2. 指令重排序:
 *    指令重排序或者叫指令级并行的重排序, 现代处理器(CPU)采用了指令级并行技术(Instruction-LevelParallelism, ILP)
 *    来将多条指令重叠执行, 如果不存在数据依赖性, CPU可以改变语句对应机器指令的执行顺序;
 *    指令重排序是指CPU的优化行为, 和编译器优化很类似; 它是通过乱序执行的技术, 来提高执行的效率;
 *    所以就算编译器不发生重排序, 当编译器生成的指令真正拿到CPU内执行的时候, CPU也可能对指令进行重排序,
 *    所以我们在开发中一定要考虑到重排序带来的后果;
 * 3. 内存系统的"重排序":
 *    由于处理器使用缓存和读/写缓冲区, 这使得加载(load)和存储(save)操作看上去好像是在乱序执行;
 *    内存系统内不存在重排序, 但是内存会带来看上去和重排序一样的效果, 所以这里的"重排序"打了双引号;
 *    由于内存有缓存的存在, 在JMM里表现为主存和本地内存, 由于主存和本地内存的不一致, 会使得程序表现出乱序的行为;
 *    在下面代码的例子中, 假设没有编译器的重排序和CPU的指令重排序, 但如果发生了主存和本地内存不一致,
 *    也可能导致同样的情况: 假设线程1先执行完毕, 线程1修改了a的值, 但是修改后并没有写回主存,
 *    所以线程2是看不到刚才线程1对a的修改的, 所以线程2看到的a还是等于0, 所以最后运行的结果是x = 0, y = 0;
 *    当然, 同理, 也可能是线程2先执行, 然后线程2对b做的修改没有写回主存, 导致线程1执行时看到的b的值还是0;
 */

import java.util.concurrent.CountDownLatch;

public class OutOfOrderExecution {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;

            // 为了让程序运行结果是上面提到的情况3, 这里用CountDownLatch作为辅助, 使两个线程同时开始
            CountDownLatch latch = new CountDownLatch(3);

            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.countDown();
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a = 1;
                    x = b;
                }
            }, "One");
            Thread two = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.countDown();
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    b = 1;
                    y = a;
                }
            }, "Two");
            one.start();
            two.start();
            latch.countDown();
            one.join();
            two.join();

            System.out.println("已执行" + i + "次, [x = " + x + ", y = " + y + "]");
            if (x == 0 && y == 0) {
                break;
            }
        }
    }
}
