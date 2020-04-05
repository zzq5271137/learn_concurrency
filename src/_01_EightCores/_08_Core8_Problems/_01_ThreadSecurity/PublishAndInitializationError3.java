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
 * 这里演示逸出方式二中的注册监听事件(隐式逸出)的情况;
 * 这里使用观察者模式来演示;
 */

import java.util.concurrent.TimeUnit;

class MySource {
    private EventListener listener;

    void registListener(EventListener listener) {
        this.listener = listener;
    }

    void eventCome(Event e) {
        if (listener != null) {
            listener.onEvent(e);
        } else {
            System.out.println("\nlistener还未初始化完毕");
        }
    }
}

interface EventListener {
    void onEvent(Event e);
}

interface Event {
}

public class PublishAndInitializationError3 {
    Integer count;

    public PublishAndInitializationError3(MySource source) {
        source.registListener(new EventListener() {
            @Override
            public void onEvent(Event e) {
                System.out.println("\n我得到的数字是: " + count);
            }
        });
        // 这里只是模拟这个构造函数会做一些其他的操作
        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    public static void main(String[] args) {
        MySource source = new MySource();

        /*
         * 在本例中, 要演示的是还未完成初始化(构造函数没有完全执行完毕)就把对象发布给外界的情况中的注册监听器的情况,
         * 这里指的发布给外界的对象其实是指监听器对象(listener);
         * 在下面创建并启动的线程中, 直接调用了source的eventCome()方法, 而在eventCome()方法中,
         * 直接调用了listener的onEvent()方法(因为这时初始化工作已经做了一部分, listener已经注册给了source,
         * 所以listener不为null, 所以走if分支而不是else分支), 而在onEvent()方法中(定义在构造函数的匿名内部类中),
         * 又直接引用了count, 由于count此时还没有初始化完成, 其实也可以说listener还没有初始化完成,
         * 就直接把listener注册给了source, 又由于创建PublishAndInitializationError3对象发生在启动该线程的下面,
         * 所以在onEvent()中打印出的count值为null;
         * 如果把创建PublishAndInitializationError3对象(即把所有初始化工作的执行)放在启动线程之前, 就不会有问题;
         * 但是还是那句话, 问题不是出在先执行启动线程还是先执行相应的初始化工作, 因为不能要求用户根据我们的设计而做出改变,
         * 问题是出在listener过早地发布出去了; 如果用户代码真的是如下面主函数的写法(即先启动了子线程而后做初始化工作),
         * 那么理想情况就是会打印出"listener还未初始化完毕"(详见eventCome()的代码), 因为此时listener(或者说count)还未初始化完成,
         * 就不能将listener注册给source, 所以在eventCome()中, 应该走else分支(即判断出listener为null),
         * 从而打印出"listener还未初始化完毕";
         *
         * 本情况的解决办法是使用工厂方法统一地去给外部创建对象, 在工厂方法中, 要安排好初始化工作与注册监听器的顺序,
         * 详见PublishAndInitializationErrorFixed.java
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                source.eventCome(new Event() {
                });
            }
        }).start();
        new PublishAndInitializationError3(source);
    }
}
