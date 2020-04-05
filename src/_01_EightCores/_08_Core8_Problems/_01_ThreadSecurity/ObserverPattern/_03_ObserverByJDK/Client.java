package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._03_ObserverByJDK;

public class Client {
    public static void main(String[] args) {
        ConcreteWeatherSubject weatherSubject = new ConcreteWeatherSubject();
        ConcreteObserver observer = new ConcreteObserver("毛毛的大脚");
        weatherSubject.addObserver(observer);
        weatherSubject.setWeatherContent("明天天气晴朗");
    }
}
