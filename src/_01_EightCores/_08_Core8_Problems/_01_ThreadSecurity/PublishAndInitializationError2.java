package _01_EightCores._08_Core8_Problems._01_ThreadSecurity;

/*
 * 什么是逸出:
 * 逸出指的是一个对象被发布到了它不该被发布的地方, 如:
 * 1. 方法返回一个private对象(private本意是不让外部访问);
 * 2. 还未完成初始化(构造函数没有完全执行完毕)就把对象提供给外界, 比如:
 *    a). 在构造函数中未初始化完毕就this赋值
 *    b). 隐式逸出: 注册监听事件
 *    c). 构造函数中运行线程
 *
 * 这里演示逸出方式二中的在构造函数中未初始化完毕就this赋值的情况;
 */

import java.util.concurrent.TimeUnit;

class Point {
    private final Integer x, y;

    public Point(int x, int y) throws InterruptedException {
        this.x = x;
        PublishAndInitializationError2.point = this; // 在Point对象还没有初始化完成之前就把它赋值给了别人
        TimeUnit.SECONDS.sleep(2); // 除此之外, 它可能还做了其他工作, 这里用休眠代替
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

class PointInitializer extends Thread {
    @Override
    public void run() {
        try {
            new Point(1, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class PublishAndInitializationError2 {
    static Point point;

    public static void main(String[] args) throws InterruptedException {
        new PointInitializer().start();
        TimeUnit.SECONDS.sleep(1);
        // TimeUnit.SECONDS.sleep(3);
        if (point != null) {
            /*
             * 由于在主线程中只休眠了1秒就去使用point, 而在Point对象的构造函数中, 完成x、y的初始化需要更久(初始化y值比较迟),
             * 所以这里打印出的point并不完整(没有y值的信息); 如果上面休眠时间长一些, 这里就会打印出完整的point;
             * 但是, 问题不是出在主线程等待的时间不够长, 因为我们不能要求用户根据我们的设计而做出改变;
             * 问题是出在Point对象的构造函数中过早地发布了对象;
             */
            System.out.println(point);
        }
    }
}
