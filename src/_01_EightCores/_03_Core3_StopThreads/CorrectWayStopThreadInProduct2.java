package _01_EightCores._03_Core3_StopThreads;

/*
 * 最佳实践2: 恢复中断
 *          即: 当run()方法内需要调用其他方法(自己的)时, 在那个方法内,
 *              如果无法向外抛出InterruptedException异常(可能由于业务要求或者其他原因)而必须try/catch,
 *              我们需要在catch子句中使用Thread.currentThread().interrupt()来恢复设置中断状态,
 *              以便于在后续的执行中, 依然能够检测到刚才发生了中断(使用Thread.currentThread().isInterrupted());
 */

public class CorrectWayStopThreadInProduct2 implements Runnable {
    @Override
    public void run() {
        int num = 0;
        while (num < 100) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted");
                break;
            }
            System.out.println("go");
            myMethod();
            num++;
        }
        System.out.println("done");
    }

    private void myMethod() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // 恢复中断
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new CorrectWayStopThreadInProduct2());
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}
