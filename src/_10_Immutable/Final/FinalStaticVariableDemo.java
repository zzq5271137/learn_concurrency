package _10_Immutable.Final;

/*
 * 演示final修饰的静态(static)属性的赋值时机;
 *
 * 2种赋值时机:
 * 1). 在声明变量的等号右边直接赋值
 * 2). 在类的static初始化代码块(static代码块)中赋值
 * 这两种赋值时机本质上是一样的, 因为, 对于static修饰的属性, 如果在声明它时在等号右边直接赋值,
 * 那么在加载该类时, 该赋值语句实际上是放进static代码块中运行的;
 * 对于一个final修饰的静态属性, 这2种赋值时机的使用规则是, 必须选择一个, 而且只能选择一个;
 */

public class FinalStaticVariableDemo {
    private final static int num = 10;

    static {
        // num = 10;
    }
}
