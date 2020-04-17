package _08_Atomic._05_AtomicFieldUpdater;

/*
 * 演示AtomicIntegerFieldUpdater的基本用法;
 */

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

class Candidate {
    volatile int score;
}

public class AtomicIntegerFieldUpdaterDemo implements Runnable {
    static Candidate tom;
    static Candidate peter;

    // 利用了反射
    static final AtomicIntegerFieldUpdater<Candidate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            peter.score++;
            scoreUpdater.getAndIncrement(tom);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        tom = new Candidate();
        peter = new Candidate();
        AtomicIntegerFieldUpdaterDemo instance = new AtomicIntegerFieldUpdaterDemo();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("tom的分数(经过升级): " + tom.score);
        System.out.println("peter的分数(未经过升级): " + peter.score);
    }
}
