package _15_SimpleProject._01_SimpleCache;

/*
 * 自己实现一个缓存;
 * 最简单的形式: HashMap;
 */

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SimpleCache {

    // HashMap是线程不安全的
    private final HashMap<String, Integer> cache = new HashMap<>();

    /**
     * 从缓存中取数据;
     * 这里明显是线程不安全的, 需要改进;
     * 如果此方法加上synchronized, 就是线程安全的了;
     * 但是如果加上synchronized, 会导致此方法性能很差, 因为多个线程无法同时访问;
     */
    public Integer compute(String userId) throws InterruptedException {
        Integer res = cache.get(userId);
        // 先检查缓存中有没有保存过该userId的计算结果
        if (res == null) {
            // 如果缓存中找不到, 那么需要现在计算一下结果, 并且保存到缓存中
            res = doCompute(userId);
            cache.put(userId, res);
        }
        return res;
    }

    /**
     * 模拟数据的计算;
     * 在真实应用场景中, 可能是从数据库中取的, 也可能是从服务器端取的;
     */
    private Integer doCompute(String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return new Integer(userId);
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleCache myCache = new SimpleCache();
        System.out.println("第一次获取结果(很慢), 结果为:" + myCache.compute("13"));
        System.out.println("第二次获取结果(很快), 结果为:" + myCache.compute("13"));
    }
}
