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
    }
}
