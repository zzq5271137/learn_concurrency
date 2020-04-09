package _05_ThreadPool._04_RejectTasks;

/*
 * 拒绝任务的时机:
 * 1. 当Executor关闭时(不一定完全关闭, 调用过shutdown()方法就会开始拒绝新任务), 提交新任务会被拒绝;
 * 2. 当Executor使用的是有界边界和有界队列且线程数达到maximumPoolSize、队列也已经满了时,
 *    会拒绝新任务;
 *
 * 有4种拒绝策略:
 * 1. AbortPolicy
 *    直接抛出RejectedExecutionException异常;
 * 2. DiscardPolicy
 *    会默默地把新提交的任务丢弃, 提交任务的线程不会得到通知;
 * 3. DiscardOldestPolicy
 *    会把队列中存在时间最久的任务丢弃, 以便腾出空间给新提交的那个任务;
 * 4. CallerRunsPolicy
 *    会将新提交的任务交给提交任务的线程去执行(你行你上);
 *    这种策略有两点好处, 一个是这种策略不会像上面3种一样会丢弃任务, 在这种策略下提交的任务总会得到执行;
 *    第二点是这种策略其实是一种负反馈, 它会让提交任务的速度降低下来, 因为提交任务的线程可能需要花费时间去自己执行任务;
 */

public class RejectTasks {
    public static void main(String[] args) {
    }
}
