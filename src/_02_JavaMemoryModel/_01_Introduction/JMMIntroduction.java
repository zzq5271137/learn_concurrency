package _02_JavaMemoryModel._01_Introduction;

/*
 * 参考: https://www.jianshu.com/p/e0e80d004a86
 *      https://www.jianshu.com/p/8a58d8335270
 *
 * 什么是Java内存模型(Java Memory Model, JMM):
 * 1. JMM是一组规范, 需要要求各个JVM虚拟机的实现来遵守JMM规范, 以便于开发者可以利用这些规范, 更方便地开发多线程程序;
 *    如果没有这样的一个JMM内存模型来规范, 那么很有可能同一份代码经过了不同JVM的不同规则的重排序之后,
 *    导致其在不同的虚拟机上运行的结果不一样, 这是很大的问题;
 * 2. 同时, JMM是各种并发编程的关键字和工具类的原理, 比如volatile、synchronized、Lock等的原理都是JMM;
 *    如果没有JMM, 那就需要我们自己指定什么时候用内存栅栏等, 那是相当麻烦的, 幸好有了JMM,
 *    让我们只需要用同步工具和关键字就可以开发并发程序;
 *
 * JMM主要包含3个方面的内容:
 * 1. 重排序
 * 2. 可见性
 * 3. 原子性
 */

public class JMMIntroduction {
    public static void main(String[] args) {
    }
}
