package _05_ThreadPool._01_ThreadPoolIntro;

/*
 * 线程池的重要性:
 * 首先, 软件中的"池"可以理解为计划经济, 即, 资源是有限的, 但是任务可能是非常多的,
 * 这样就需要一个"池"来合理地利用这些资源去完成全部的任务;
 * 比如对于线程来说, 创建和销毁线程都需要很大开销, 如果每个任务都重建一个线程, 消耗太大;
 * 而且每一个Java线程都对应到操作系统中的线程, 而操作系统的线程数量是有上限的, 但任务的数量是不受我们控制的;
 * 所以可以利用线程池去复用线程, 而不需要为每个任务创建线程, 这样就能有效地使用有限的线程数去完成很多的任务;
 * 总的来说, 我们为什么需要线程池:
 * 1. 反复创建线程开销大
 * 2. 过多的线程会占用太多内存
 * 3. 系统的线程数量有上限, 但是任务数量是不受我们控制的
 *
 * 使用线程池的好处:
 * 1. 加快响应速度, 提升用户体验
 * 2. 更加合理地统筹CPU和内存资源
 * 3. 统一管理资源与任务
 *
 * 适合使用线程池的场合:
 * 1. 服务器接受大量请求时, 使用线程池技术是非常合适的, 它可以大大减少线程的创建和销毁次数, 提高服务器的工作效率
 * 2. 实际上, 在实际开发中, 如果需要创建5个以上的线程, 那么就可以使用线程池来管理
 */

public class ThreadPoolIntro {
    public static void main(String[] args) {
    }
}
