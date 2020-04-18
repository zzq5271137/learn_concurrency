package _10_Immutable.Final;

/*
 * 演示final修饰的本地变量;
 *
 * 这种场景下, 并不规定赋值时机, 只要求在使用前必须赋值, 就和普通的本地变量一样,
 * 只不过final的本地变量只能被赋值一次;
 */

public class FinalLocalVariableDemo {
    public void func() {
        final int num;
        System.out.println("do something...");
        num = 10;
        System.out.println("num = " + num);
        // num = 20;  // final修饰的变量只能被赋值一次
    }

    public static void main(String[] args) {
        new FinalLocalVariableDemo().func();
    }
}
