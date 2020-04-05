package _01_EightCores._03_Core3_StopThreads.WrongWays;

/*
 * 错误的停止线程的方式: 使用stop()方法停止线程;
 * 使用这种方式, 会导致线程运行一半突然停止, 该线程就没法完成基本单位的操作(事务的原子性), 会产生脏数据;
 * 比如, 连队发弹药的例子, 当突然停止, 会造成有的连队多领取、而有的连队少领取;
 *
 * 使用suspend()方法和resume()方法停止线程也是错误的;
 */

public class StopThreadUsingStop implements Runnable {
    @Override
    public void run() {
        // 模拟连队发弹药, 一共5个连队, 每个连队15人, 以连队为单位发放弹药, 叫到号的士兵前去领取
        for (int i = 0; i < 5; i++) {
            System.out.println("连队" + (i + 1) + "开始领取弹药: ");
            for (int j = 0; j < 15; j++) {
                System.out.println("士兵" + (j + 1) + "领取");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("连队" + (i + 1) + "领取完毕");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadUsingStop());
        thread.start();
        Thread.sleep(1000);
        thread.stop();
    }
}
