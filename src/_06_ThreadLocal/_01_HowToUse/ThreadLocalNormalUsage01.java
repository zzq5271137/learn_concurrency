package _06_ThreadLocal._01_HowToUse;

/*
 * 演示多个线程分别打印日期(使用for循环创建线程);
 *
 * 在这个例子中, 由于总线程数并不多, 所以使用for循环去创建线程也不是不可以;
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalNormalUsage01 {
    public void date(int seconds) {
        // Date的这个构造方法传入的是毫秒, 从1970.01.01 00:00:00 GMT开始计算, 算出一个日期然后返回
        Date date = new Date(1000 * seconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z Z");

        // 使用System.identityHashCode()获取对象的真实地址
        System.out.println(Thread.currentThread().getName() + ": "
                + System.identityHashCode(sdf) + ", " + sdf.format(date));
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new ThreadLocalNormalUsage01().date(finalI);
                }
            }).start();
            Thread.sleep(100);
        }
    }
}
