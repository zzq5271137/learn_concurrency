package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._02_WeatherExample;

public class ConcreteWeatherSubject extends WeatherSubject {
    private String weatherContent;

    public String getWeatherContent() {
        return weatherContent;
    }

    public void setWeatherContent(String weatherContent) {
        this.weatherContent = weatherContent;
        this.notifyObservers();
    }
}
