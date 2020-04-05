package _01_EightCores._03_Core3_StopThreads;

/*
 * run()方法无法向上抛出异常, 只能在run()方法内部try/catch;
 * 因为我们只是在对Runnable接口中的run()方法进行重写, 而Runnable接口中的run()方法没有定义抛出异常;
 */

public class CantThrowsExceptionInRun {
    public static void func() throws Exception {
        throw new Exception();
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    func();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
