package _10_Immutable.StringPoolDemos;

public class StringPoolDemo2_1 {
    public static void main(String[] args) {
        String s1 = new String("1");
        String s1Inter = s1.intern();
        String s2 = "1";
        System.out.println(s1 == s2);
        System.out.println(s1Inter == s2);

        String s3 = new String("1") + new String("1");
        String s3Inter = s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);
        System.out.println(s3Inter == s4);
    }
}
