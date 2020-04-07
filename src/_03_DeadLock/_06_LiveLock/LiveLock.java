package _03_DeadLock._06_LiveLock;

/*
 * 活锁(LiveLock)也是活跃性问题的一种;
 *
 * 什么是活锁:
 * 活锁指的是任务或者执行者没有被阻塞, 还在一直执行, 但是由于某些条件没有满足, 导致一直重复尝试—失败—尝试—失败的过程;
 * 对于哲学家就餐的例子, 死锁的情况是, 每个哲学家都拿着左手的餐叉, 永远都在等待右手的餐叉(仅仅是在等, 没有做其他事情,
 * 也就是进入阻塞状态); 而活锁的情况是, 哲学家们在完全相同的时刻进入餐厅, 并同时拿起左手餐叉, 再等5分钟, 又同时拿起左手餐叉,
 * 然后循环(因为所有哲学家的行为都是相同且写死的, 所以不可能会打破这个循环, 他们会一直重复拿起-等待-拿起-等待这个过程, 这就是活锁);
 * 解决以上哲学家就餐的活锁的问题, 可以使用引入随机性, 即让每个哲学家等待的时间不同, 这样就可以打破循环;
 * 在实际的计算机问题中, 缺乏餐叉可以类比为缺乏共享资源;
 * 总结来说, 活锁具有两个特点:
 * 1. 线程没有阻塞, 线程始终在运行;
 * 2. 线程虽然在运行, 但是程序却得不到进展, 因为线程始终在做同样的事情;
 * 而活锁有时候比死锁更可怕, 首先活锁比死锁更难发现; 其次死锁会让线程进入阻塞状态, 进入阻塞状态的线程是不消耗CPU资源的,
 * 而发生活锁的线程是消耗CPU资源的, 因为他们始终在运行;
 *
 * 在下面的例子中, 夫妻二人共同使用同一个勺子吃饭, 两人在自己吃饭前, 会先看对方是不是饿, 如果对方是饿的, 就让对方先吃;
 * 由于两人的策略一模一样, 即发现对方饿就立即把勺子给对方让对方先吃, 所以当两人都是饿的时候, 就会发生活锁;
 * 解决活锁的有效策略是引入随机性; 所以在下面的例子中, 如果发现对方也是饿的, 不是百分百地去马上给对方勺子让对方先吃,
 * 而是有几率地让对方先吃, 这样就解决了问题;
 */

import java.util.Random;

class Spoon {
    private Diner owner;

    public Spoon(Diner owner) {
        this.owner = owner;
    }

    public Diner getOwner() {
        return owner;
    }

    public void setOwner(Diner owner) {
        this.owner = owner;
    }

    public synchronized void use() {
        System.out.printf("%s: 正在吃饭！", owner.getName());
    }
}

class Diner {
    private String name;
    private volatile boolean isHungry;

    public String getName() {
        return name;
    }

    public Diner(String name) {
        this.name = name;
        isHungry = true;
    }

    public void eat(Spoon spoon, Diner spouse) throws InterruptedException {
        while (isHungry) {
            if (spoon.getOwner() != this) {
                Thread.sleep(1);
                continue;
            }
            if (spouse.isHungry && new Random().nextInt(10) < 9) {  // 引入随机性, 解决活锁问题
                System.out.println(name + ": 让" + spouse.name + "先吃");
                spoon.setOwner(spouse);
                continue;
            }
            spoon.use();
            isHungry = false;
            System.out.println(name + ": 已经吃完了");
            spoon.setOwner(spouse);
        }
    }
}

public class LiveLock {
    public static void main(String[] args) {
        Diner husband = new Diner("丈夫");
        Diner wife = new Diner("妻子");
        Spoon spoon = new Spoon(husband);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    husband.eat(spoon, wife);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wife.eat(spoon, husband);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
