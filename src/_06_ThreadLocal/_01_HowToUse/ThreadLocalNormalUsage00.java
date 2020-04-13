package _06_ThreadLocal._01_HowToUse;

/*
 * 演示两个线程分别打印日期;
 *
 * 时间格式简单说明:
 * y = 年 (yy或yyyy)
 * M = 月 (MM)
 * d = 月中的天 (dd)
 * h = 小时, 0~12 (hh)
 * H = 小时, 0~23 (HH)
 * m = 分 (mm)
 * s = 秒 (ss)
 * S = 毫秒 (SSS)
 * z = 时区文本 (例如, 太平洋标准时间)
 * Z = 时区, 时间偏移量 (例如-0800)
 * 以下是一些模式示例, 其中包含每个格式如何格式化或期望解析日期的示例:
 * yyyy-MM-dd (2009-12-31)
 * dd-MM-yyyy (31-12-2009)
 * yyyy-MM-dd HH:mm:ss (2009-12-31 23:59:59)
 * HH:mm:ss.SSS (23:59:59.999)
 * yyyy-MM-dd HH:mm:ss.SSS (2009-12-31 23:59:59.999)
 * yyyy-MM-dd HH:mm:ss.SSS Z (2009-12-31 23:59:59.999 +0100)
 *
 * 具体日期格式可以参考:
 * http://tutorials.jenkov.com/java-internationalization/simpledateformat.html
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalNormalUsage00 {
    public void date(int seconds) {
        // Date的这个构造方法传入的是毫秒, 从1970.01.01 00:00:00 GMT开始计算, 算出一个日期然后返回
        Date date = new Date(1000 * seconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z Z");

        // 使用System.identityHashCode()获取对象的真实地址
        System.out.println(Thread.currentThread().getName() + ": "
                + System.identityHashCode(sdf) + ", " + sdf.format(date));
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new ThreadLocalNormalUsage00().date(10);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new ThreadLocalNormalUsage00().date(104707);
            }
        }).start();
    }
}
