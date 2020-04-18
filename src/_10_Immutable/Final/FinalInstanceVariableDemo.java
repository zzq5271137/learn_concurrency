package _10_Immutable.Final;

/*
 * 演示final修饰的非静态属性的赋值时机;
 *
 * 3种赋值时机:
 * 1). 在声明变量的等号右边直接赋值
 * 2). 在构造函数中赋值
 * 3). 在非static代码块中赋值
 * 其实这3种时机本质上是一样的, 因为, 对于第1种和第2种来说, 在声明变量的等号右边直接赋值,
 * 在运行时实际上是放进构造函数中去运行的, 即运行构造函数时(创建一个对象时),
 * 会把对属性的直接赋值的语句放到构造函数中的最前面, 然后执行构造函数; 对于第3种和第2种来说,
 * 非static代码块在运行时也是放在构造函数中运行的, 即在运行构造函数时(创建一个对象时),
 * 会把非static代码块放到构造函数中的最前面, 然后执行构造函数;
 * 对于一个final修饰的非静态属性, 这3种赋值时机的使用规则是, 必须选择一个, 而且只能选择一个;
 */

public class FinalInstanceVariableDemo {
    private final int num = 10;

    {
        // num = 10;
    }

    public FinalInstanceVariableDemo(int num) {
        // this.num = num;
    }
}
