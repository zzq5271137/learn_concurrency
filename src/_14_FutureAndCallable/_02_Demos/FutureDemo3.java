package _14_FutureAndCallable._02_Demos;

/*
 * 此处演示批量提交任务时, 用Future的List来批量获取结果;
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class FutureDemo3 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(service.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    TimeUnit.SECONDS.sleep(3);
                    return new Random().nextInt();
                }
            }));
        }
        for (Future<Integer> f : futures) {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }
}
