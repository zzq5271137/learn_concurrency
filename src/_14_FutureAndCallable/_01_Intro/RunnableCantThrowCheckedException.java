package _14_FutureAndCallable._01_Intro;

/*
 * 演示在run()方法中无法抛出Checked Exception;
 */

public class RunnableCantThrowCheckedException {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            // throw new Exception();  // 无法在run()方法中抛出异常
        };
    }
}
