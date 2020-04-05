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
 * 这里演示的是使用工厂模式解决逸出方式二, 即解决还未完成初始化就把对象提供给外界的问题;
 * (逸出方式一的解决办法是使用返回对象副本而不是对象本身的方式, 详见PublishAndInitializationError1.java)
 */

import java.util.concurrent.TimeUnit;

class MySource2 {
    private EventListener2 listener;

    void registListener(EventListener2 listener) {
        this.listener = listener;
    }

    void eventCome(Event2 e) {
        if (listener != null) {
            listener.onEvent(e);
        } else {
            System.out.println("\nlistener还未初始化完毕");
        }
    }
}

interface EventListener2 {
    void onEvent(Event2 e);
}

interface Event2 {
}

public class PublishAndInitializationErrorFixed {
    Integer count;
    private EventListener2 listener;

    private PublishAndInitializationErrorFixed(MySource2 source) {
        listener = new EventListener2() {
            @Override
            public void onEvent(Event2 e) {
                System.out.println("\n我得到的数字是: " + count);
            }
        };
        // 这里只是模拟这个构造函数会做一些其他的操作
        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    /*
     * 对外暴露的工厂方法, 是外部创建实例对象的唯一入口(构造方法已做私有化);
     * 在本方法内, 就可以保证构造函数完成它所需要完成的所用操作, 然后再注册监听器,
     * 这样就能避免还未初始化完成就把对象发布到外面;
     */
    public static PublishAndInitializationErrorFixed getInstance(MySource2 source) {
        PublishAndInitializationErrorFixed instance = new PublishAndInitializationErrorFixed(source);
        source.registListener(instance.listener);
        return instance;
    }

    public static void main(String[] args) {
        MySource2 source = new MySource2();

        /*
         * 本例解决的是PublishAndInitializationError3.java中的问题,
         * 使用了工厂方法统一地给外界创建对象, 在工厂方法中, 当初始化工作完成再将listener注册给source,
         * 所以, 当主函数(用户代码)是下面的写法, 即先启动了子线程而后做初始化工作,
         * 控制台打印出的是"listener还未初始化完毕", 而不是"我得到的数字是: null", 这是符合要求的,
         * 因为, 还未初始化完成的listener(或者说count)不应该发布到外界(不应该注册给source),
         * 所以source中的listener在此时理所应当是null, 这样就解决了这个问题,
         * 详见PublishAndInitializationError3.java
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                source.eventCome(new Event2() {
                });
            }
        }).start();
        getInstance(source);
    }
}
