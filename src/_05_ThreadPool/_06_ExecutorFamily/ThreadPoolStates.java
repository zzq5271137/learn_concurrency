package _05_ThreadPool._06_ExecutorFamily;

/*
 * 线程池的状态:
 * 1. RUNNING
 *    接受新任务并处理排队任务;
 * 2. SHUTDOWN
 *    不接受新任务, 但处理排队任务;
 * 3. STOP
 *    不接受新任务, 也不处理排队任务, 并中断正在进行的任务;
 * 4. TIDYING
 *    中文意为整洁, 理解了中文就容易理解这个状态了;
 *    当所有任务都已终止, workerCount为0时, 线程池会转到TIDYING状态, 并将运行terminated()钩子方法;
 * 5. TERMINATED
 *    terminated()运行完成;
 */

public class ThreadPoolStates {
    public static void main(String[] args) {
    }
}
