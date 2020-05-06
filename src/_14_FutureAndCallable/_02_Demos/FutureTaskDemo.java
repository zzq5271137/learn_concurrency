package _14_FutureAndCallable._02_Demos;

/*
 * FutureTask类:
 * 我们可以使用FutureTask来获取Future和任务的执行结果; FutureTask是一种包装器,
 * 它可以把Callable转化成Future和Runnable, FutureTask同时实现了二者的接口;
 * (FutureTask类实现的是RunnableFuture接口, 而RunnableFuture接口继承自Runnable接口和Future接口,
 * 所以FutureTask即可以作为Runnable被线程执行, 又可以作为Future以得到Callable的返回值);
 *
 * FutureTask的使用:
 * 把Callable实例当做参数, 生成FutureTask对象, 然后把这个对象当做一个Runnable对象,
 * 用线程池或Thread去执行里面的任务, 最后通过同一个FutureTask对象获取执行的结果;
 *
 * 此处演示FutureTask的基本使用;
 */

import java.util.Random;
import java.util.concurrent.*;

public class FutureTaskDemo {

    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程正在执行任务...");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("子线程执行完成, 返回结果");
            return new Random().nextInt(10);
        }
    }

    private static void runTaskUsingThread() {
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<>(task);
        new Thread(futureTask).start();
        try {
            System.out.println("FutureTask获取到子线程的运行结果: " + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void runTaskUsingThreadPool() {
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<>(task);
        ExecutorService service = Executors.newCachedThreadPool();

        /*
         * 以这种方式执行submit(), 就不需要submit()返回的Future了,
         * 因为我们可以通过过传入的FutureTask对象来获取任务的执行结果;
         */
        service.submit(futureTask);

        try {
            System.out.println("FutureTask获取到子线程的运行结果: " + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    public static void main(String[] args) {
        System.out.println("通过Thread运行任务: ");
        runTaskUsingThread();
        System.out.println("==========================");
        System.out.println("通过线程池运行任务: ");
        runTaskUsingThreadPool();
    }
}
