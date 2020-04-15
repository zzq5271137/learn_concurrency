package _07_Lock._04_TypesOfLock.ShareExclusiveLock;

/*
 * 在锁降级成功后, 也就是持有写锁的同时申请并成功获得了读锁之后, 此时直接释放写锁, 但是不释放读锁,
 * 这样就可以提高锁的利用效率;
 *
 * 下面这段代码演示了在更新缓存的时候, 如何利用锁的降级功能;
 * 在这段代码中有一个读写锁, 最重要的就是中间的processCachedData()方法, 在这个方法中, 会首先获取到读锁,
 * 也就是rwl.readLock().lock(), 用它去判断当前的缓存是否有效, 如果有效那么就直接跳过整个if语句,
 * 如果缓存已经失效, 代表我们需要更新这个缓存了; 由于我们需要更新缓存, 所以之前获得到的读锁是不够用的,
 * 我们需要获取写锁; 在获取写锁之前, 我们首先要释放读锁, 然后利用rwl.writeLock().lock()来获取到写锁,
 * 然后是经典的try/finally结构, 在try语句中, 我们首先再次判断缓存是否有效, 因为在刚才释放读锁和获取写锁的间隙中,
 * 可能有其他线程抢先修改了数据, 所以在此我们需要进行二次判断; 如果我们发现缓存是失效的, 就用new Object()这样的方式来示意,
 * 获取到了新的数据内容, 并且把缓存的标记位设置为true, 意为缓存现在有效了; 由于我们后续希望打印出data的值,
 * 所以不能在此处释放掉所有的锁; 我们的选择是在不释放写锁的情况下直接获取读锁, 也就是rwl.readLock().lock()这行语句所做的事,
 * 然后, 在持有读锁的情况下释放写锁, 最后, 在最下面的try中把data打印出来, 再在finally里把读锁释放掉;
 *
 * 这就是一个非常典型的利用锁的降级功能的例子; 有人可能会问, 为什么要这么麻烦进行降级呢? 我从最开始就持有最高等级的写锁不就行了吗?
 * 这样谁都没有办法来影响到我自己的工作, 永远是线程安全的;
 * 那么为什么需要锁的降级呢? 如果我们在刚才的方法中一直使用写锁, 最后才释放写锁的话, 虽然确实是线程安全的,
 * 但是也是没有必要的, 因为我们只有一处修改数据的代码, 即, data = new Object(), 后面我们对于data仅仅是读取;
 * 如果一直使用写锁的话, 就不能让多个线程同时来读取了, 一直持有写锁是浪费资源的, 降低了整体的运行效率;
 * 所以, 在这种场景下, 使用锁的降级是很好的办法, 可以提高整体性能;
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

class CachedData {
    Object data;
    volatile boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    void processCachedData() {
        // 最开始是读
        rwl.readLock().lock();
        if (!cacheValid) {
            // 发现缓存失效, 那么就需要写入了, 所以现在需要获取写锁, 由于锁不支持升级, 所以在获取写锁前, 需要先释放读锁
            rwl.readLock().unlock();
            // 获取到写锁
            rwl.writeLock().lock();
            try {
                // 这里需要再次判断数据的有效性, 因为在我们释放读锁和获取写锁的空隙之内, 可能有其他线程修改了数据
                if (!cacheValid) {
                    data = new Object();
                    cacheValid = true;
                }
                // 在不释放写锁的情况下, 直接获取读锁, 这就是读写锁的降级
                rwl.readLock().lock();
            } finally {
                // 释放了写锁, 但是依然持有读锁, 这样一来, 就可以多个线程同时读取了, 提高了整体的执行效率
                rwl.writeLock().unlock();
            }
        }

        try {
            System.out.println(data);
        } finally {
            // 最后释放读锁
            rwl.readLock().unlock();
        }
    }
}

public class DowngradeDemo {
    public static void main(String[] args) {
    }
}
