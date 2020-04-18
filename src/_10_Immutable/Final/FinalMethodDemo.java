package _10_Immutable.Final;

/*
 * 演示final修饰方法, 表示该方法不能被重写; 但是final不能修饰构造方法;
 *
 * 引申: static方法也不能被重写
 */

class SuperClass {
    public void normalMethod() {
        System.out.println("SuperClass normalMethod");
    }

    public final void finalMethod() {
        System.out.println("SuperClass finalMethod");
    }

    public static void staticMethod() {
        System.out.println("SuperClass staticMethod");
    }
}

class SubClass extends SuperClass {
    // 构造方法不能修饰为final
    // public final SubClass() {}

    @Override
    public void normalMethod() {
        System.out.println("Subclass normalMethod");
    }

    // 父类中的final方法不能被重写
    // @Override
    // public final void finalMethod() {}

    // 父类中的static方法也不能被重写, 因为静态方法是属于类的, 不属于对象;
    // 但是可以在子类中写一个和父类中方法签名相同的静态方法;
    // @Override
    public static void staticMethod() {
        System.out.println("SubClass staticMethod");
    }
}

public class FinalMethodDemo {
    public static void main(String[] args) {
        SuperClass instance = new SubClass();

        /*
         * 首先静态方法不建议使用实例去调用;
         * 其次, 如果你使用实例去调用, 那么在多态的写法下(声明为父类, 赋值为子类实例),
         * 声明的是哪个类, 就调用哪个类的静态方法;
         * 因为, 在编译成字节码时, 这种实例调用静态方法的语句, 会被换成类名调用静态方法,
         * 而具体是哪个类名, 就是根据你这个变量声明时候使用的类;
         */
        instance.staticMethod();
    }
}
