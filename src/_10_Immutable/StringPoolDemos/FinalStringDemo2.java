package _10_Immutable.StringPoolDemos;

public class FinalStringDemo2 {
    private static String getName() {
        return "zzq";
    }

    public static void main(String[] args) {
        String a = "zzq2";

        final String b = getName();
        String d = "zzq";

        String c = b + 2;
        String e = d + 2;

        System.out.println(b == d);
        System.out.println(a == c);
        System.out.println(a == e);
    }
}
