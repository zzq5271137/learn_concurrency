package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._01_Template;

/*
 * 被观察的目标对象类, 它知道观察它的所有观察者, 并向外提供注册和删除观察者的接口,
 * 并向子类(具体的目标对象)提供通知所有注册的观察者的方法;
 */

import java.util.ArrayList;
import java.util.List;

public class Subject {
    // 用来保存注册的观察者对象
    private List<Observer> observers = new ArrayList<>();

    // 注册观察者
    public void attach(Observer observer) {
        observers.add(observer);
    }

    // 删除观察者
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    // 当自身状态改变时, 通知所有注册的观察者对象
    protected void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
