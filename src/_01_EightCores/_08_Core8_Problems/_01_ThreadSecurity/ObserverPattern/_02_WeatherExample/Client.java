package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._02_WeatherExample;

public class Client {
    public static void main(String[] args) {
        // 创建目标
        ConcreteWeatherSubject weatherSubject = new ConcreteWeatherSubject();

        // 创建观察者
        Observer observer1 = new ConcreteObserver("毛毛的大脚", "记得早起晨跑");
        Observer observer2 = new ConcreteObserver("深海的星星", "记得去购物");

        // 注册观察者
        weatherSubject.attach(observer1);
        weatherSubject.attach(observer2);

        // 目标发布天气
        weatherSubject.setWeatherContent("明天天气晴朗");
    }
}
