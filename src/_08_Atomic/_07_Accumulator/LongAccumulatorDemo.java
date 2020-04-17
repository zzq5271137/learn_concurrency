package _08_Atomic._07_Accumulator;

/*
 * 演示LongAccumulator累加器的基本使用;
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

public class LongAccumulatorDemo {
    public static void main(String[] args) {
        /*
         * 第一个参数是一个LongBinaryOperator(函数式接口), 表示希望做什么运算,
         * 这也是Accumulator累加器更灵活的地方, 可以用方法引用来使用已定义好的一些运算;
         * 第二个参数表示初始值;
         */
        // LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 100);
        // LongAccumulator accumulator = new LongAccumulator(Long::sum, 100);
        // LongAccumulator accumulator = new LongAccumulator((x, y) -> x > y ? x : y, 100);
        LongAccumulator accumulator = new LongAccumulator(Math::max, 100);

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        IntStream.range(1, 10).forEach(n -> executorService.submit(() -> accumulator.accumulate(n)));
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        System.out.println(accumulator.getThenReset());
    }
}
