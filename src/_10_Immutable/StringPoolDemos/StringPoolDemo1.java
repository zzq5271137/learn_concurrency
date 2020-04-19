package _10_Immutable.StringPoolDemos;

public class StringPoolDemo1 {
    public static void main(String[] args) {
        String str1 = "ABCD";
        String str2 = "A" + "B" + "C" + "D";
        String str3 = "AB" + "CD";
        String str4 = new String("ABCD");
        String temp = "AB";
        String str5 = temp + "CD";
        final String temp2 = "AB";
        String str6 = temp2 + "CD";
        System.out.println(str1 == str2);
        System.out.println(str1 == str3);
        System.out.println(str1 == str4);
        System.out.println(str1 == str5);
        System.out.println(str1 == str6);
    }
}
