package _01_EightCores._01_Core1_CreateThreads.WrongWays;

/*
 * 使用定时器的方式实现线程
 */

import java.util.Timer;
import java.util.TimerTask;

public class TimerStyle {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }, 1000, 1000);
    }
}
