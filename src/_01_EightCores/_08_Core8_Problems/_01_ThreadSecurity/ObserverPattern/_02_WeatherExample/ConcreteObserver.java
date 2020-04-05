package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._02_WeatherExample;

public class ConcreteObserver implements Observer {
    private String observerName;
    private String weatherContent;
    private String remindThing;

    public ConcreteObserver(String observerName, String remindThing) {
        this.observerName = observerName;
        this.remindThing = remindThing;
    }

    @Override
    public void update(WeatherSubject subject) {
        weatherContent = ((ConcreteWeatherSubject) subject).getWeatherContent();
        System.out.println(observerName + "收到了" + weatherContent + ", 提醒: " + remindThing);
    }
}
