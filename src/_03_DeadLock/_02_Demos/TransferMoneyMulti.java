package _03_DeadLock._02_Demos;

/*
 * 模拟多人同时转账, 依然会有死锁发生的可能;
 */

import java.util.Random;

public class TransferMoneyMulti {
    private static final int ACCOUNT_NUMS = 500;
    private static final int DEFAULT_BALANCE = 1000;
    private static final int TRANSFER_ITERATIONS = 1000000;
    private static final int THREADS_NUM = 20;

    public static void main(String[] args) {
        Random random = new Random();
        Account[] accounts = new Account[ACCOUNT_NUMS];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account("account_" + i, DEFAULT_BALANCE);
        }
        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < TRANSFER_ITERATIONS; i++) {
                    int fromAcct = random.nextInt(ACCOUNT_NUMS);
                    int toAcct = random.nextInt(ACCOUNT_NUMS);
                    int amount = random.nextInt(DEFAULT_BALANCE);
                    try {
                        TransferMoney.transferMoney(accounts[fromAcct], accounts[toAcct], amount);
                    } catch (TransferMoneyException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (int i = 0; i < THREADS_NUM; i++) {
            new TransferThread().start();
        }
    }
}
