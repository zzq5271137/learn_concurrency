package _10_Immutable.StringPoolDemos;

public class StringPoolDemo5 {
    public static void main(String[] args) {
        String s1 = new String("abc");
        String s2 = "abc";
        String s3 = new String("abc");
        System.out.println(s1 == s2.intern());
        System.out.println(s1.intern() == s2);
        System.out.println(s1 == s3.intern());
        System.out.println(s2 == s3.intern());
    }
}
