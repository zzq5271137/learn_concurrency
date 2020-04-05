package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._03_ObserverByJDK;

import java.util.Observable;

public class ConcreteWeatherSubject extends Observable {
    private String weatherContent;

    public String getWeatherContent() {
        return weatherContent;
    }

    public void setWeatherContent(String weatherContent) {
        this.weatherContent = weatherContent;

        /*
         * 调用Observable中的通知方法通知所有观察者对象;
         * 调用前必须先调用setChanged()方法将changed标志位置为true;
         */
        this.setChanged();
        this.notifyObservers(weatherContent); // 这里是推模型的方式
        // this.notifyObservers(); // 这里是拉模型的方式
    }
}
