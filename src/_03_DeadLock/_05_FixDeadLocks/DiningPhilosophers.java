package _03_DeadLock._05_FixDeadLocks;

/*
 * 哲学家就餐问题:
 * 1. 哲学家会思考, 思考完后会吃饭, 每个哲学家左右手边各有一个叉子
 * 1. 哲学家想要吃饭, 必须先拿起左手的叉子, 再拿起右手的叉子, 左右手的叉子都拿到后才能吃饭
 * 2. 如果叉子被别人占用了, 那就等别人用完
 * 4. 吃完后, 会依次放下两个叉子(先右后左), 放下后的叉子可以被其他临近的哲学家使用
 *
 * 哲学家就餐问题中死锁的发生:
 * 假设此时哲学家们同时想吃饭了, 他们同时拿起自己左手的叉子, 那么此时, 他们会永远都在等右手的叉子;
 */

class Philosopher implements Runnable {
    private Object leftFork;
    private Object rightFork;

    public Philosopher(Object leftFork, Object rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
            while (true) {
                doAction("thinking");
                synchronized (leftFork) {
                    doAction("picked up left fork");
                    synchronized (rightFork) {
                        doAction("picked up right fork, start eating");
                        doAction("put down right fork");
                    }
                    doAction("put down left fork");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doAction(String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep((long) (Math.random() * 10));
    }
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        Object[] forks = new Object[philosophers.length];
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }
        for (int i = 0; i < philosophers.length; i++) {
            Object rightFork = forks[i];
            Object leftFork = forks[(i + 1) % philosophers.length];
            philosophers[i] = new Philosopher(leftFork, rightFork);
            new Thread(philosophers[i], "Philosopher_" + (i + 1)).start();
        }
    }
}
