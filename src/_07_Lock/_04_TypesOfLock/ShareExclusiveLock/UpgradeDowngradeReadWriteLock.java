package _07_Lock._04_TypesOfLock.ShareExclusiveLock;

/*
 * 读写锁的升降级:
 * 读锁和写锁其实不是平等的, 我们能看出来写锁拥有更大的权力; 读写锁的升降级指的是:
 * 1. 降级:
 *    当一个线程持有写锁, 在不释放写锁的情况下直接去获取读锁;
 * 2. 升级:
 *    当一个线程持有读锁, 在不释放读锁的情况下直接去获取写锁;
 * ReentrantReadWriteLock(Lock的实现类)允许降级, 不允许升级;
 *
 * 此处演示ReentrantReadWriteLock允许降级, 不允许升级, 依然是电影票那个场景;
 */

public class UpgradeDowngradeReadWriteLock {
    public static void main(String[] args) {
    }
}
