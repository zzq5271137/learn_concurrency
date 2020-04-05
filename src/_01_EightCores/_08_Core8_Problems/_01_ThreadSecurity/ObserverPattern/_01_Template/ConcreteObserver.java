package _01_EightCores._08_Core8_Problems._01_ThreadSecurity.ObserverPattern._01_Template;

/*
 * 具体的观察者对象类, 实现观察者接口中的更新方法, 使自身的状态和目标的状态保持一致;
 */

public class ConcreteObserver implements Observer {
    // 观察者的状态
    private String observerState;

    @Override
    public void update(Subject subject) {
        this.observerState = ((ConcreteSubject) subject).getSubjectState();
    }
}
