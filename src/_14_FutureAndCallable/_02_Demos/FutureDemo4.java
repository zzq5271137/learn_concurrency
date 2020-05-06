package _14_FutureAndCallable._02_Demos;

/*
 * 此处演示执行任务过程中抛出了异常, 以及isDone()方法的演示;
 * 并不是任务一产生异常, ExecutionException异常就会被抛出, 而是当我们调用get()方法时才会抛出ExecutionException异常;
 * 我们用一个for循环进行倒计时, 来演示抛出ExecutionException异常的时机;
 * ps. ExecutionException的异常信息内会包含任务抛出的异常的完整堆栈信息;
 */

import java.util.concurrent.*;

public class FutureDemo4 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                throw new IllegalArgumentException("Callable抛出异常");
            }
        });
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(i + "...");
                Thread.sleep(1000);
            }
            System.out.println("任务是否结束了: " + future.isDone());
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InterruptedException异常");
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("ExecutionException异常");
        }
        service.shutdown();
    }
}
