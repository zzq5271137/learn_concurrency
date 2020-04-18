package _10_Immutable;

/*
 * 什么是不变性(Immutable):
 * 如果对象在被创建后, 状态就不能被改变, 那么它就具有不变性(即不可变对象);
 * 不可变对象的要求: 所有属性都由final修饰, 表示这个对象真真正正的不可被修改, 增加字段也不行;
 * 比如, 一个Person对象, person, 它的age和name都不能再变, 那么person就是个不可变对象;
 * 详见ImmutableDemo.java
 *
 * 不变性与线程安全:
 * 具有不变性的对象一定是线程安全的(好像是废话?), 我们不需要对其采取任何额外的安全措施, 也能保证线程安全;
 * "不可变"是并发安全版图中一个重要的模块, 也是一个很重要的策略;
 */

public class Intro {
    public static void main(String[] args) {
    }
}
