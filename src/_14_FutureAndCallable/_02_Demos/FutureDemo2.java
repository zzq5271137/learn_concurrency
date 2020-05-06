package _14_FutureAndCallable._02_Demos;

/*
 * 此处演示Callable的lambda表达式的形式;
 */

import java.util.Random;
import java.util.concurrent.*;

public class FutureDemo2 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Integer> future = service.submit(() -> {
            Thread.sleep(3000);
            return new Random().nextInt();
        });
        try {
            System.out.println(future.get());  // 会阻塞
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
