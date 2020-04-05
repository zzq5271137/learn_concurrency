package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._03_ObserverByJDK;

import java.util.Observable;
import java.util.Observer;

public class ConcreteObserver implements Observer {
    private String observerName;

    public ConcreteObserver(String observerName) {
        this.observerName = observerName;
    }

    @Override
    public void update(Observable o, Object arg) {
        // 推模型的方式
        System.out.println("[推模型]" + observerName + "收到了消息, 目标推送过来的是: " + arg);

        // 拉模型的方式
        System.out.println("[拉模型]" + observerName + "收到了消息, 主动从目标对象拉取数据: " + ((ConcreteWeatherSubject) o).getWeatherContent());
    }
}
