package _01_EightCores._08_Core8_Problems._01_ThreadSecurity;

/*
 * 线程安全问题之对象的发布和初始化问题;
 *
 * 什么是发布:
 * 发布指的是让一个对象能够在超过本类的范围去使用, 比如声明对象时使用public, 或者让一个方法能够return这个对象;
 *
 * 什么是逸出:
 * 逸出指的是一个对象被发布到了它不该被发布的地方, 如:
 * 1. 方法返回一个private对象(private本意是不让外部访问);
 * 2. 还未完成初始化(构造函数没有完全执行完毕)就把对象提供给外界, 比如:
 *    a). 在构造函数中未初始化完毕就this赋值
 *    b). 隐式逸出: 注册监听事件
 *    c). 构造函数中运行线程
 *
 * 这里演示逸出方式一, 即方法返回一个private对象的逸出;
 */

import java.util.HashMap;
import java.util.Map;

public class PublishAndInitializationError1 {
    private Map<String, String> states;

    public PublishAndInitializationError1() {
        states = new HashMap<>();

        // 设计的初衷是只能在构造函数内对states进行赋值
        states.put("1", "周一");
        states.put("2", "周二");
        states.put("3", "周三");
        states.put("4", "周四");
        states.put("5", "周五");
        states.put("6", "周六");
        states.put("7", "周日");
    }

    /*
     * 这个名为states的map是private的, 本意是只能让外界访问其中的某一个值, 而不是让外界获得整个map的引用,
     * 但是在这里, 整个map被发布出去了, 外界就能够拿到整个map的引用, 从而可以修改或删除里面的值,
     * 这是违背设计初衷的, 这就是一种逸出;
     */
    public Map<String, String> getStates() {
        return states;
    }

    /*
     * 可以使用返回副本而不是返回真身的方式解决这个问题;
     */
    public Map<String, String> getStatesImproved() {
        return new HashMap<>(states);
    }

    public static void main(String[] args) {
        PublishAndInitializationError1 instance = new PublishAndInitializationError1();

        // 使用getStates()方法得到的是states的真身
        System.out.println(instance.getStates().get("1"));
        instance.getStates().remove("1"); // 由于states逸出了, 我们能够修改或删除里面的值, 而这是我们所不希望的
        System.out.println(instance.getStates().get("1"));

        // 使用getStatesImproved()方法返回的是states的一个副本, 而不是states本身, 所以对其修改不会影响states
        System.out.println(instance.getStatesImproved().get("2"));
        instance.getStatesImproved().remove("2");
        System.out.println(instance.getStatesImproved().get("2"));
    }
}
