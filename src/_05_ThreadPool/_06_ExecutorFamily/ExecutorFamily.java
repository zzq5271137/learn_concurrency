package _05_ThreadPool._06_ExecutorFamily;

/*
 * Executor家族的继承/实现关系如下(->表示继承自或实现):
 * ThreadPoolExecutor -> AbstractExecutorService -> ExecutorService -> Executor, 即:
 * 1. ThreadPoolExecutor类继承自AbstractExecutorService类;
 * 2. AbstractExecutorService类实现了ExecutorService接口;
 * 3. ExecutorService接口继承自Executor接口;
 * 4. 而Executors是一个工具类, 是帮助我们快速创建一些线程池用的;
 *
 * 线程池是如何实现线程的复用的:
 * 我们都知道, 一个线程(Thread)只能被启动一次(只能start()一次), 那线程池是如何实现线程的复用呢?
 * 线程池实现线程复用的核心是使用相同的线程去循环执行不同的任务(循环执行不同task的run()方法而已);
 * 线程池对线程做了包装(ThreadPoolExecutor类中的Worker内部类), Worker只需要启动一次, 不是重复启动,
 * 而在Worker的run()方法中, 使用循环不断地检测有没有新的任务交给该Worker, 如果有的话就去执行该任务(执行task.run());
 * 详见ThreadPoolExecutor源码;
 */

public class ExecutorFamily {
    public static void main(String[] args) {
    }
}
