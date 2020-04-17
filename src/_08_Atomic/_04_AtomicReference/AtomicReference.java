package _08_Atomic._04_AtomicReference;

/*
 * Atomic*Reference引用类型原子类
 * a). AtomicReference: 引用类型原子类
 * b). AtomicStampedReference: 引用类型原子类的升级, 带时间戳, 可以解决ABA问题
 * c). AtomicMarkableReference
 *
 * AtomicReference类的作用:
 * 和AtomicInteger并没有本质区别, AtomicInteger可以让一个整数保持原子性,
 * 而AtomicReference可以让一个对象保持原子性, 当然, AtomicReference的功能显然比AtomicInteger强,
 * 因为一个对象里可以包含很多属性; 用法和AtomicInteger类似;
 */

public class AtomicReference {
    public static void main(String[] args) {
    }
}
