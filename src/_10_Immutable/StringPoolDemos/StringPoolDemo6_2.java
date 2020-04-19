package _10_Immutable.StringPoolDemos;

public class StringPoolDemo6_2 {
    public static void main(String[] args) {
        String str0 = "SEUCalvin";
        String str1 = new String("SEU") + new String("Calvin");
        System.out.println(str1.intern() == str1);
        System.out.println(str1 == "SEUCalvin");
    }
}
