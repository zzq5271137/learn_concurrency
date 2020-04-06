package _03_DeadLock._05_FixDeadLocks;

/*
 * 使用避免策略修复死锁(在代码中避免以相反的顺序获取锁的情况);
 */

class Account {
    String accountName;
    int balance;

    public Account(String accountName, int balance) {
        this.accountName = accountName;
        this.balance = balance;
        System.out.println("创建账户: " + this);
    }

    @Override
    public String toString() {
        return accountName + "(" + balance + ")";
    }
}

class TransferMoneyException extends Exception {
    public TransferMoneyException() {
        super();
    }

    public TransferMoneyException(String msg) {
        super(msg);
    }
}

public class FixTransferMoney implements Runnable {
    int flag;
    static Account accountA = new Account("account_A", 500);
    static Account accountB = new Account("account_B", 500);
    static final Object HELPER_LOCK = new Object();

    @Override
    public void run() {
        if (flag == 0) {
            try {
                transferMoney(accountA, accountB, 200);
            } catch (TransferMoneyException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (flag == 1) {
            try {
                transferMoney(accountB, accountA, 100);
            } catch (TransferMoneyException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void transferMoney(Account from, Account to, int amount) throws TransferMoneyException, InterruptedException {
        class Helper {
            public void transfer() throws TransferMoneyException {
                if (from.balance - amount < 0) {
                    throw new TransferMoneyException(from.accountName + "余额不足" + amount + ", 转账失败");
                }
                from.balance -= amount;
                to.balance += amount;
                System.out.println(from.accountName + "成功向" + to.accountName + "转账" + amount + ", 转账后: " + from + ", " + to);
            }
        }

        // 利用锁对象的hash值来帮助我们决定获取锁的顺序
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        // 根据锁对象的hash值, 从小往大获取
        if (fromHash < toHash) {
            synchronized (from) {
                Thread.sleep(10);
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        }
        if (fromHash > toHash) {
            synchronized (to) {
                Thread.sleep(10);
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        }
        /*
         * 如果两个锁对象的hash值一样(虽然概率很低), 那就在外面再套一层格外的锁, 想要执行内层的代码,
         * 就必须要先获取这把最外层的锁, 所以永远也不会有相反的获取锁的顺序这种情况发生;
         * 总之, 避免策略的思路就是要在代码中避免以相反的顺序获取锁的情况;
         *
         * 在真实开发中, 我们也可以使用对象在数据库中的主键来作为获取锁的顺序的判断条件,
         * 因为主键是唯一的, 不会出现重复, 所以也不会用到这种在最外层套一个额外锁的方法;
         */
        if (fromHash == toHash) {
            synchronized (HELPER_LOCK) {
                synchronized (from) {
                    Thread.sleep(10);
                    synchronized (to) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("转账前: " + accountA + ", " + accountB);
        FixTransferMoney r1 = new FixTransferMoney();
        FixTransferMoney r2 = new FixTransferMoney();
        r1.flag = 0;
        r2.flag = 1;
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("最终转账后: " + accountA + ", " + accountB);
    }
}
