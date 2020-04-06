package _03_DeadLock._02_Demos;

/*
 * 模拟双人同时转账的死锁的例子;
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

public class TransferMoney implements Runnable {
    int flag;
    static Account accountA = new Account("account_A", 500);
    static Account accountB = new Account("account_B", 500);

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
        synchronized (from) {
            // 如果加上这句代码的执行, 就会形成死锁
            Thread.sleep(10);

            synchronized (to) {
                if (from.balance - amount < 0) {
                    throw new TransferMoneyException(from.accountName + "余额不足" + amount + ", 转账失败");
                }
                from.balance -= amount;
                to.balance += amount;
                System.out.println(from.accountName + "成功向" + to.accountName + "转账" + amount + ", 转账后: " + from + ", " + to);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("转账前: " + accountA + ", " + accountB);
        TransferMoney r1 = new TransferMoney();
        TransferMoney r2 = new TransferMoney();
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
