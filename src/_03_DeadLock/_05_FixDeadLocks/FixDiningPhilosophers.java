package _03_DeadLock._05_FixDeadLocks;

/*
 * 使用避免策略(改变一个哲学家拿叉子的顺序), 解决哲学家问题的死锁;
 */

class FixPhilosopher implements Runnable {
    private Object leftFork;
    private Object rightFork;

    public FixPhilosopher(Object leftFork, Object rightFork) {
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

public class FixDiningPhilosophers {
    public static void main(String[] args) {
        FixPhilosopher[] philosophers = new FixPhilosopher[5];
        Object[] forks = new Object[philosophers.length];
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }
        for (int i = 0; i < philosophers.length; i++) {
            Object rightFork = forks[i];
            Object leftFork = forks[(i + 1) % philosophers.length];

            if (i == philosophers.length - 1) {
                // "与众不同"的哲学家
                philosophers[i] = new FixPhilosopher(rightFork, leftFork);
            } else {
                philosophers[i] = new FixPhilosopher(leftFork, rightFork);
            }

            new Thread(philosophers[i], "Philosopher_" + (i + 1)).start();
        }
    }
}
