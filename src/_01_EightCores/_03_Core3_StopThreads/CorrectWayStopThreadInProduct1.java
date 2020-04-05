package _01_EightCores._03_Core3_StopThreads;

/*
 * 最佳实践1: 传递中断
 *          即: 当run()方法内需要调用其他方法(自己的)时, 在那个方法内,
 *              优先选择在方法签名中抛出这个异常(InterruptedException);
 *
 * 因为如果我们这样做, 那么就会强制要求在run()方法中try/catch;
 * 而且, 如果这样做, 异常也必须在run()方法中try/catch, run()方法无法再向上抛出异常(详见CantThrowsExceptionInRun.java);
 * 这样就避免了异常被漏掉或吞掉的情况;
 * 也就是说, 最佳实践为, 在最外层(也就是run()方法中)去响应中断;
 */

public class CorrectWayStopThreadInProduct1 implements Runnable {
    @Override
    public void run() {
        try {
            int num = 0;
            while (num < 100) {
                System.out.println("go");
                myMethod();
                num++;
            }
        } catch (InterruptedException e) {
            /*
             * 由于myMethod()方法做出了正确逻辑(把异常往外抛出),
             * 所以我们可以在这里做出更多的正确的操作来响应中断请求,
             * 比如说保存日志、中断程序等等;
             */
            System.out.println("保存日志");
            e.printStackTrace();
        }
        System.out.println("done");
    }

    private void myMethod() throws InterruptedException {
        Thread.sleep(3000);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new CorrectWayStopThreadInProduct1());
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}
