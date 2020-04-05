package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._02_WeatherExample;

import java.util.ArrayList;
import java.util.List;

public class WeatherSubject {
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
