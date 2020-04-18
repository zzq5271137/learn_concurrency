package _10_Immutable.Final;

/*
 * final关键字的作用:
 * 1. 修饰变量:
 *    表示变量的值不能被修改; 如果变量是一个对象, 那么表示对象的引用不能被修改, 但是对象自身的内容(属性)依然可以被修改;
 *    一旦变量被声明为final之后, 该变量只能被赋值一次, 且一旦被赋值, 就不能再被改变;
 *    包含3种场景, 这些场景的区别在于final变量的赋值时机:
 *    a). final instance variable, 即类中的final属性
 *        这种场景有3种赋值时机:
 *        1). 在声明变量的等号右边直接赋值
 *        2). 在构造函数中赋值
 *        3). 在类的初始化代码块(非static代码块)中赋值
 *        其实这3种时机本质上是一样的, 因为, 对于第1种和第2种来说, 在声明变量的等号右边直接赋值,
 *        在运行时实际上是放进构造函数中去运行的, 即运行构造函数时(创建一个对象时),
 *        会把对属性的直接赋值的语句放到构造函数中的最前面, 然后执行构造函数; 对于第3种和第2种来说,
 *        非static代码块在运行时也是放在构造函数中运行的, 即在运行构造函数时(创建一个对象时),
 *        会把非static代码块放到构造函数中的最前面, 然后执行构造函数;
 *        对于一个final修饰的非静态属性, 这3种赋值时机的使用规则是, 必须选择一个, 而且只能选择一个;
 *        详见FinalInstanceVariableDemo.java
 *    b). final static variable, 即类中的final static属性
 *        这种场景有2种赋值时机:
 *        1). 在声明变量的等号右边直接赋值
 *        2). 在类的static初始化代码块(static代码块)中赋值
 *        这两种赋值时机本质上是一样的, 因为, 对于static修饰的属性, 如果在声明它时在等号右边直接赋值,
 *        那么在加载该类时, 该赋值语句实际上是放进static代码块中运行的;
 *        对于一个final修饰的静态属性, 这2种赋值时机的使用规则是, 必须选择一个, 而且只能选择一个;
 *        详见FinalStaticVariableDemo.java
 *    c). final local variable, 即方法中的final本地变量
 *        这种场景下, 并不规定赋值时机, 只要求在使用前必须赋值, 就和普通的本地变量一样,
 *        只不过final的本地变量只能被赋值一次;
 *        详见FinalLocalVariableDemo.java
 * 2. 修饰方法:
 *    表示方法不能被重写; 但是final不能修饰构造方法;
 *    详见FinalMethodDemo.java
 * 3. 修饰类:
 *    表示类不能被继承; 典型的String类就是final修饰的;
 *    详见FinalClassDemo.java
 * 使用final的一大原因就是为了实现线程安全, 因为它天生是线程安全的, 不需要额外的同步开销;
 */

public class FinalIntro {
    public static void main(String[] args) {
    }
}
