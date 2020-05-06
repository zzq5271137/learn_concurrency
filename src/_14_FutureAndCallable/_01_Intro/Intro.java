package _14_FutureAndCallable._01_Intro;

/*
 * Runnable接口的缺陷:
 * 1. 它没有返回值
 *    其实我们去用子线程执行任务, 很多时候我们希望能有个返回值, 以便我们能够通过返回值去做不同的工作;
 * 2. 它不能抛出Checked Exception
 *    所有的异常需要在run()方法内进行catch和处理, 在run()方法中无法抛出Checked Exception;
 *    详见RunnableCantThrowCheckedException.java
 * 为什么有这些缺陷呢? 因为在Runnable接口中, run()方法的定义就是这样的, 即返回值为void, 也没有抛出异常;
 * 那么Runnable为什么要设计成这样呢? 因为啊, 即便是run()方法定义成有返回值或者抛出了异常,
 * 但是run()方法是放进Thread或者线程池中去执行的, 不论是Thread还是线程池都不是我们编写的,
 * 所以我们也无法处理返回值和抛出的异常; 所以干脆, run()方法被设计成无返回值和无法抛出异常;
 * 那么针对这些缺陷, 有没有什么补救措施呢? 那就是Callable接口;
 *
 * Callable接口:
 * 1. 它类似于Runnable, 是被其他线程执行的任务;
 * 2. 它定义了call()方法, 需要我们去实现, 就像是我们实现Runnable接口的run()方法一样;
 * 3. call()方法既有返回值, 又可以抛出异常, 其定义为:
 *    V call() throws Exception;
 *
 * Future:
 * Future的核心思想是, 一个方法的计算可能会非常耗时, 在计算的过程中, 我们没有必要一直等待该方法执行完成并返回;
 * 那么, 我们就可以把这个方法去交给一个子线程去执行, 在子线程执行期间, 我们可以去做其他事;
 * 当我们想要获取子线程的执行结果的时候, 我们可以利用Future去获取子线程的相关运行结果, 包括返回值、异常等等;
 *
 * Callable和Future, 以及线程池之间的关系:
 * 上面提到, 我们可以利用Future去获取子线程的相关运行结果, 包括返回值、异常等等, 所以, 我们的子线程就要使用Callable,
 * 而不是Runnable, 因为Runnable既没有返回值也无法抛出异常;
 * 我们可以用Future.get()来获取Callable接口的call()方法返回的执行结果, 还可以通过Future.isDone()来判断任务是否已经执行完了;
 * Future还提供了取消任务、限时获取任务的结果等功能; 在call()方法还未执行完成时, 调用Future.get()的线程会被阻塞,
 * 直到call()方法执行完成并返回了结果后, 调用Future.get()的线程才会被唤醒并得到该结果(即重新切换成RUNNABLE状态);
 * 所以, Future实际上是一个存储器, 它存储了Callable的结果, 而这个任务的执行时间是无法确定的,
 * 因为这完全取决于call()方法的执行情况; 所以, Callable和Future是一个相互配合的关系;
 * 线程池提交任务有两个方法, 一个是execute(), 一个是submit(); execute()方法接收的参数是一个Runnable,
 * 它无返回值; 而submit()方法, 它的参数可以是一个Runnable, 也可以是一个Callable, 并且它可以返回一个Future;
 * 所以, 如果我们使用线程池, 我们可以使用submit()方法来提交任务并获得一个Future, 以便做后续操作;
 *
 * Future的主要方法:
 * 1. get()/get(long timeout, Timeunit unit):
 *    获取Callable的运行结果(返回值); get()方法的行为取决于Callable任务的状态, 只有以下5种情况:
 *    a). 任务已正常完成:
 *        get()方法会立刻返回结果;
 *    b). 任务尚未完成(任务还没开始或进行中):
 *        get()方法将阻塞并直到任务完成;
 *    c). 任务执行过称中抛出Exception(可能会是各种异常):
 *        get()方法会抛出ExecutionException; 不论call()方法执行时抛出的异常类型是什么,
 *        get()方法抛出的异常类型都是ExecutionException;
 *    d). 任务被取消:
 *        get()方法会抛出CancellationException;
 *    e). 任务超时:
 *        get()方法有一个重载方法, 需要传入一个延迟时间和时间单位, 如果时间到了还没有获得结果,
 *        get()就会抛出TimeoutException; 超时的需求是很常见的, 使用get(long timeout, Timeunit unit)时,
 *        如果call()在指定时间内完成了任务, 那么就会正常获取到返回值; 而如果在指定时间内没有计算出结果,
 *        那么就会抛出TimeoutException; 如果任务超时(我们接收到了TimeoutException异常), 我们需要对超时进行处理,
 *        也就是调用cancel()方法取消任务;
 * 2. cancel(boolean mayInterruptIfRunning):
 *    取消任务的执行; mayInterruptIfRunning代表是否中断正在执行的任务;
 * 3. isDone():
 *    判断任务是否执行完毕了; 这里的执行完毕不单单代表成功执行, 比如任务抛出异常执行失败了, 或者任务被中断了,
 *    都属于执行完毕了;
 * 4. isCancelled():
 *    判断任务是否被取消了;
 */

public class Intro {
    public static void main(String[] args) {
    }
}
