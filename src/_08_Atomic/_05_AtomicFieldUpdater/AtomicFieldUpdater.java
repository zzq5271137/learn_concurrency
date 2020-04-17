package _08_Atomic._05_AtomicFieldUpdater;

/*
 * Atomic*FieldUpdater升级原子类
 * a). AtomicIntegerFieldUpdater: 原子更新整形字段的更新器
 * b). AtomicLongFieldUpdater: 原子更新长整形字段的更新器
 * c). AtomicReferenceFieldUpdater: 原子更新引用类型字段的更新器
 *
 * 以AtomicIntegerFieldUpdater为例, 它可以对普通变量进行升级, 使其具有原子性;
 *
 * AtomicFieldUpdater注意点:
 * 1. 属性的可见性(你的fieldUpdater必须能看到该类的属性, 即属性至少不能是private的);
 * 2. 属性不能是static的;
 */

public class AtomicFieldUpdater {
    public static void main(String[] args) {
    }
}
