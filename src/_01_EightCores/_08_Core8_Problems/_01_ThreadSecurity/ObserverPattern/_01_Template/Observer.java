package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._01_Template;

/*
 * 观察者的接口定义, 定义一个更新的接口给那些在目标发生改变的时候被通知的对象;
 */

public interface Observer {
    void update(Subject subject);
}
