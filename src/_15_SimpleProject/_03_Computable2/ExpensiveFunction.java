package _15_SimpleProject._03_Computable2;

/*
 * 耗时计算的实现类, 本身不具备缓存的能力, 而且缓存也不应该由此类来考虑, 计算应与缓存分开;
 */

import java.util.concurrent.TimeUnit;

public class ExpensiveFunction implements Computable<String, Integer> {

    /**
     * 模拟数据的计算;
     * 在真实应用场景中, 可能是从数据库中取的, 也可能是从服务器端取的;
     */
    @Override
    public Integer compute(String arg) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "正在计算...");
        TimeUnit.SECONDS.sleep(3);
        return new Integer(arg);
    }

}
