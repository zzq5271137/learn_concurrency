package _10_Immutable.StringPoolDemos;

public class FinalStringDemo1 {
    public static void main(String[] args) {
        String a = "zzq2";

        final String b = "zzq";
        String d = "zzq";

        String c = b + 2;
        String e = d + 2;

        System.out.println(b == d);
        System.out.println(a == c);
        System.out.println(a == e);

        /*
         * 打印结果为true, true, false; 解释:
         * 当final变量是基本数据类型以及String类型时, 如果在编译期间能知道它的确切值, 也就是提前知道了b的值,
         * 所以编译器会把它当做编译期常量使用; 也就是说在用到该final变量的地方, 相当于直接访问的这个常量,
         * 不需要在运行时确定, 和C语言中的宏替换有点像; 因此在上面的一段代码中, 由于变量b被final修饰,
         * 且b是被直接赋值的(即b直接指向常量池中的字符串), 因此b会被当做编译期常量,
         * 所以在使用到b的地方会直接将变量b替换为它的值; 而对于变量a和d的访问/使用却需要在运行时通过链接指向常量池;
         * 变量c是b + 2得到的, 由于b是一个常量, 所以在使用b的时候直接相当于使用b的原始值(zzq)进行计算,
         * 所以c生成的也是一个常量, 而a所指向的也是常量池中的, a和c都是zzq2,
         * 由于Java中常量池中只生成唯一的一个zzq2字符串, 所以a和c是==的(即指向的地址相同);
         * 但是d的情况有所不同, d是指向常量池中的zzq，但由于d不是final修饰,
         * 也就是说编译器在使用d的时候不会提前知道d的值, 所以在计算e的时候也需要在运行时才能确定,
         * 所以这种计算会在堆上生成值为zzq2的对象(使用StringBuilder的append()方法), 所以最终e指向的是堆上的,
         * 所以a和e不相等;
         */
    }
}
