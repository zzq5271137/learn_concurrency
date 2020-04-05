package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._01_Template;

/*
 * 具体的目标对象类, 负责持有业务中的具体目标对象的状态, 并负责在状态改变时通知注册的观察者对象;
 */

public class ConcreteSubject extends Subject {
    // 具体目标对象的状态
    private String subjectState;

    public String getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(String state) {
        this.subjectState = state;
        this.notifyObservers(); // 自身状态改变, 所以要通知注册的观察者对象
    }
}
