package _08_Atomic._01_Intro;

/*
 * 什么是原子类, 有什么用:
 * 原子操作是指一组操作是不可分割、不可中断的, 即便是在多线程的情况下也可以保证;
 * 在Java中, java.util.concurrent.atomic包中的类都是具有原子特性的类;
 * 原子类的作用和锁类似, 都是为了保证并发情况下的线程安全; 不过, 原子类相比于锁, 有一定的优势:
 * 1. 粒度更细
 *    原自变量可以把竞争范围缩小到变量级别, 这是我们可以获得的最细粒度的情况了, 通常, 锁的粒度都要大于原子变量的粒度;
 * 2. 效率更高
 *    通常, 使用原子类的效率会比使用锁的效率更高, 除了高度竞争的情况;
 *
 * 6类原子类纵览:
 * 1. Atomic*基本类型原子类
 *    a). AtomicInteger: 整型原子类
 *    b). AtomicLong: 长整型原子类
 *    c). AtomicBoolean: 布尔型原子类
 * 2. Atomic*Array数组类型原子类(数组里的每个元素都可以保证原子性)
 *    a). AtomicIntegerArray: 整型数组原子类
 *    b). AtomicLongArray: 长整型数组原子类
 *    c). AtomicReferenceArray: 引用类型数组原子类
 * 3. Atomic*Reference引用类型原子类
 *    a). AtomicReference: 引用类型原子类
 *    b). AtomicStampedReference: 引用类型原子类的升级, 带时间戳, 可以解决ABA问题
 *    c). AtomicMarkableReference
 * 4. Atomic*FieldUpdater升级原子类
 *    a). AtomicIntegerFieldUpdater: 原子更新整形字段的更新器
 *    b). AtomicLongFieldUpdater: 原子更新长整形字段的更新器
 *    c). AtomicReferenceFieldUpdater: 原子更新引用类型字段的更新器
 * 5. Adder累加器
 *    a). LongAdder
 *    b). DoubleAdder
 * 6. Accumulator累加器
 *    a). LongAccumulator
 *    b). DoubleAccumulator
 */

public class Intro {
    public static void main(String[] args) {
    }
}
