package _01_EightCores._07_Core7_UncaughtException;

/*
 * 定义自己的全局未捕获异常处理器;
 */

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Logger logger;
    private String name;

    public MyUncaughtExceptionHandler(String name) {
        logger = Logger.getAnonymousLogger();
        this.name = name;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(name + "捕获到" + t.getName() + "发生异常" + e);
        logger.log(Level.WARNING, t.getName() + "线程发生异常, 线程终止", e);
    }
}
