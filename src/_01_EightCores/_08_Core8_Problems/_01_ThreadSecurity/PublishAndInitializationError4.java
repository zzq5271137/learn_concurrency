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
 * 这里演示逸出方式二中的构造函数中运行线程的情况;
 */

import java.util.HashMap;
import java.util.Map;

public class PublishAndInitializationError4 {
    private Map<String, String> states;

    public PublishAndInitializationError4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                states = new HashMap<>();
                states.put("1", "周一");
                states.put("2", "周二");
                states.put("3", "周三");
                states.put("4", "周四");
                states.put("5", "周五");
                states.put("6", "周六");
                states.put("7", "周日");
            }
        }).start();
    }

    public Map<String, String> getStates() {
        return states;
    }

    public static void main(String[] args) throws InterruptedException {
        PublishAndInitializationError4 instance = new PublishAndInitializationError4();

        /*
         * 由于在构造函数中, states的创建和初始化是交给了一个子线程去做的(这是需要时间的),
         * 而构造函数本身在执行完子线程start()方法后就会执行完成,
         * 所以如果在这里直接访问states的值, 可能会造成空指针异常,
         * 因为子线程可能还没有完成states的初始化;
         * 如果在这里稍微等待一会儿, 可能就没这个问题了;
         * 但还是那句话, 问题不是出在主线程等待的时间不够长, 因为我们不能要求用户根据我们的设计而做出改变;
         */
        // TimeUnit.SECONDS.sleep(1);
        System.out.println(instance.getStates().get("1"));
    }
}
