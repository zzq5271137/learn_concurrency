package _08_Atomic._02_AtomicInteger;

/*
 * Atomic*基本类型原子类
 * a). AtomicInteger: 整型原子类
 * b). AtomicLong: 长整型原子类
 * c). AtomicBoolean: 布尔型原子类
 * 以AtomicInteger为例, 它实际上是对Integer或int进行的封装, 封装好后就具备了原子的访问和更新操作;
 * 它的原理是CAS;
 *
 * AtomicInteger常用方法:
 * 1. int get():
 *    获取当前的值返回
 * 2. int getAndSet(int newValue):
 *    获取当前的值返回, 并将其设置为newValue
 * 3. int getAndIncrement():
 *    获取当前的值返回, 并自增:
 * 4. int getAndDecrement():
 *    获取当前的值返回, 并自减
 * 5. int getAndAdd(int delta):
 *    获取当前的值返回, 并将其加上delta
 * 6. boolean compareAndSet(int expect, int update):
 *    如果当前的值等于expect, 则将其设置为update, 返回是否成功
 */

public class AtomicInteger {
    public static void main(String[] args) {
    }
}
