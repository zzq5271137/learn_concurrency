package _06_ThreadLocal._01_HowToUse;

/*
 * ThreadLocal的两大使用场景:
 * 1. 每个线程需要一个独享的对象, 这个对象通常是工具类, 典型需要使用的类有SimpleDateFormat和Random;
 *    每个Thread内有自己的实例副本, 不共享;
 *    详见: ThreadLocalNormalUsage00.java
 *         ThreadLocalNormalUsage01.java
 *         ThreadLocalNormalUsage02.java
 *         ThreadLocalNormalUsage03.java
 *         ThreadLocalNormalUsage04.java
 *         ThreadLocalNormalUsage05.java
 * 2. 每个线程内需要保存全局的变量(例如在拦截器中获取用户信息或请求信息), 可以让不同的方法直接使用, 避免传递参数的麻烦;
 *    详见: ThreadLocalNormalUsage06.java
 *
 * 总结ThreadLocal的作用:
 * 1. 可以让某个需要用到的对象在线程之间隔离, 每个线程都有自己独立的对象;
 * 2. 可以让这个对象在整个线程运行期间做全局保存, 使其在任何方法中都可以轻松地获取到该对象;
 *
 * 总结ThreadLocal的好处:
 * 1. 保证线程安全
 *    因为存入ThreadLocal的对象, 是每一个线程都有一个, 所以不存在对象的共享, 也就不存在线程安全问题;
 * 2. 不需要加锁, 提高执行效率
 *    例如ThreadLocalNormalUsage05.java中的例子, 由于每个线程都有一个自己的SimpleDateFormat对象,
 *    所以date()方法就不用加锁;
 * 3. 高效地使用内存、节省开销
 *    例如ThreadLocalNormalUsage05.java中的例子, 只需要创建10个SimpleDateFormat对象, 而不是1000个;
 * 4. 避免了传参的繁琐
 *    例如ThreadLocalNormalUsage06.java中的例子, Service2、Service3只需要从ThreadLocal中取出User,
 *    而不需要将User层层向下传递; 这会使得代码的耦合度降低;
 */

public class UsageScenarios {
    public static void main(String[] args) {
    }
}
