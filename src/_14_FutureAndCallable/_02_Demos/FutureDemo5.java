package _14_FutureAndCallable._02_Demos;

/*
 * 此处演示任务超时, 需要注意, 我们需要对超时进行处理, 也就是调用cancel(boolean mayInterruptIfRunning)方法取消任务;
 * 演示cancel(boolean mayInterruptIfRunning)方法传入true和false的区别;
 * mayInterruptIfRunning代表是否中断正在执行的任务;
 *
 * cancel()方法取消任务的情况总结:
 * 1. 如果这个任务还没有开始执行, 那么这种情况最简单, 任务会被正常取消, 未来也不会执行, cancel()方法返回true;
 * 2. 如果任务已经完成, 或者已取消, 那么cancel()方法会执行失败, 返回false;
 * 3. 如果这个任务已经开始执行但尚未执行完毕, 那么cancel()方法会根据我们传入的mayInterruptIfRunning的值,
 *    来决定是否中断任务, cancel()方法返回true;
 */

import java.util.concurrent.*;

public class FutureDemo5 {

    // 广告类
    static class AD {
        String name;

        public AD(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "AD{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private static ExecutorService service = Executors.newFixedThreadPool(10);

    static class FetchADTask implements Callable<AD> {
        @Override
        public AD call() throws Exception {
            try {
                TimeUnit.SECONDS.sleep(7);
            } catch (InterruptedException e) {
                System.err.println("任务中: 任务sleep()期间被中断了, 返回默认广告");

                /*
                 * 其实这里返回的默认广告并没有用, 因为在下面的printAD()方法中,
                 * 我们在捕获到TimeoutException异常时, 会自己创建一个超时时的默认广告,
                 * 因为既然任务已经超时, 我们就对任务不抱什么希望了;
                 */
                return new AD("任务中: 任务被中断时的默认广告");
            }
            return new AD("BiliBili, 干杯！");
        }
    }

    public static void printAD() {
        Future<AD> future = service.submit(new FetchADTask());
        AD ad;
        try {
            // ad = future.get(10, TimeUnit.SECONDS);  // 时间足够
            ad = future.get(2, TimeUnit.SECONDS);  // 时间不够
        } catch (InterruptedException e) {
            ad = new AD("get()中: get()被中断时的默认广告");
        } catch (ExecutionException e) {
            ad = new AD("get()中: 任务运行异常时的默认广告");
        } catch (TimeoutException e) {
            ad = new AD("get()中: 任务超时时的默认广告");
            System.out.println("get()中: 任务超时, 未获取到广告, 自动创建任务超时时的默认广告");

            /*
             * cancel()方法传入true和false的区别在于:
             * 1. 传入true, 会中断任务的执行(向任务发送interrupt()请求);
             * 2. 传入false, 不会中断任务的执行, 任务会继续正常地执行结束, 但是任务返回的内容我们就获取不到了;
             * 这就是为什么, 传入false时, 运行结果没有打印"任务中: 任务sleep()期间被中断了, 返回默认广告",
             * 而传入true时, 打印了这句话, 因为传入false时, 根本就没有中断任务, 所以任务没有走到catch分支,
             * 而传入true时, 任务走到了catch分支;
             */
            // boolean cancelled = future.cancel(false);
            boolean cancelled = future.cancel(true);
            System.out.println("任务是否取消成功: " + cancelled);
        }
        service.shutdown();
        System.out.println(ad);
    }

    public static void main(String[] args) {
        printAD();
    }
}
