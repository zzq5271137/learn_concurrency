package _07_Lock._04_TypesOfLock.InterruptableLock;

/*
 * 可中断锁与不可中断锁:
 * 可中断与不可中断指的是, 在等待锁的过程中是否可以响应中断(是否可以响应interrupt());
 * 具体来说, 如果某一线程A正在持有锁, 另一线程B正在等待获取该锁, 可能由于等待时间过长, 我们不想让B等待了,
 * 想让它先处理其他任务, 我们就可以中断它等锁的过程, 这种就是可中断锁;
 * 在Java中, synchronized就是不可中断锁, 而Lock是可中断锁, 因为tryLock(time)和lockInterruptibly()都能响应中断;
 */

public class InterruptableLock {
    public static void main(String[] args) {
    }
}
