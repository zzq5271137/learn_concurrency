package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass.Examples;

/*
 * 参考: https://blog.csdn.net/worldchinalee/article/details/83790790
 *
 * 讨论为什么在使用wait()方法时, 需要使用while循环来判断条件是否满足，而不是if;
 * 以下例子使用生产者消费者模式, 很好地说明了问题;
 * 其实就是, 使用while循环是为了防止多个生产者或消费者彼此之间互相notify()造成混乱的情况, 所以需要while循环的二次检查;
 * 因为当线程被唤醒之后, 会从当初休眠的地方(即wait()那行语句)继续往后执行代码; 如果使用的是if, 那么线程被唤醒后,
 * 会继续往后执行, 而不会重新进行if条件判断; 如果一个线程被唤醒后, 它并不应该往下执行, 比如下面例子中的消费者2号被消费者1号唤醒,
 * 但是此时data数组长度为0, 它不应该消费, 但是它依然往下执行, 所以造成了数组越界异常;
 * 所以, 我们需要使用while循环包裹住wait(), 使其在被唤醒后再一次进行条件检查, 如果满足, 再往下执行;
 */

class SynStack {
    private char[] data = new char[6];
    private int cnt = 0;  // 表示数组有效元素的个数

    public synchronized void push(char ch) throws InterruptedException {
        if (cnt >= data.length) {
            System.out.println("生产线程" + Thread.currentThread().getName() + "准备休眠");
            this.wait();
            System.out.println("生产线程" + Thread.currentThread().getName() + "休眠结束了");
        }
        data[cnt++] = ch;
        System.out.printf("生产线程%s正在生产第%d个产品, 该产品是: %c\n", Thread.currentThread().getName(), cnt, ch);
        this.notify();
    }

    public synchronized void pop() throws InterruptedException {
        if (cnt <= 0) {
            System.out.println("消费线程" + Thread.currentThread().getName() + "准备休眠");
            this.wait();
            System.out.println("消费线程" + Thread.currentThread().getName() + "休眠结束了");
        }
        char ch = data[(cnt--) - 1];
        System.out.printf("消费线程%s正在消费第%d个产品, 该产品是: %c\n", Thread.currentThread().getName(), cnt + 1, ch);
        this.notify();
    }
}

class Producer2 implements Runnable {
    private SynStack ss;

    public Producer2(SynStack ss) {
        this.ss = ss;
    }

    @Override
    public void run() {
        char ch;
        for (int i = 0; i < 10; i++) {
            ch = (char) ('a' + i);
            try {
                ss.push(ch);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer2 implements Runnable {
    private SynStack ss;

    public Consumer2(SynStack ss) {
        this.ss = ss;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                ss.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class WhyWaitNeedWhile {
    public static void main(String[] args) {
        SynStack ss = new SynStack();
        Producer2 producer = new Producer2(ss);
        Consumer2 consumer = new Consumer2(ss);

        new Thread(producer, "Producer").start();
        new Thread(consumer, "Consumer-1").start();
        new Thread(consumer, "Consumer-2").start();
    }
}
