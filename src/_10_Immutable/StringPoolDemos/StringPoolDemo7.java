package _10_Immutable.StringPoolDemos;

public class StringPoolDemo7 {
    public static void main(String[] args) {
        String a = new String("123") + new String("456");
        // String b= new String("123456");
        System.out.println(a.intern() == a);
        // 输出: true; 放开注释输出: false
    }
}
