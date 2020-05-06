package _14_FutureAndCallable._02_Demos;

/*
 * 我们可以使用线程池的submit()方法返回Future对象;
 * 首先, 我们要给线程池提交我们的任务, 提交时线程池会立刻返回一个空的Future容器; 一旦线程的任务执行完毕,
 * 也就是当我们可以获取结果的时候, 线程池便会把该结果填入到之前给我们的那个Future中去(而不是创建一个新的Future),
 * 此时我们便可以从该Future中获得任务的执行结果;
 *
 * 此处演示submit()返回Future的基本用法, 以及get()的基本用法;
 */

import java.util.Random;
import java.util.concurrent.*;

public class FutureDemo1 {

    static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Integer> future = service.submit(new CallableTask());
        try {
            System.out.println(future.get());  // 会阻塞
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
