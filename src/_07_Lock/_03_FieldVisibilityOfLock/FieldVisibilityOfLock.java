package _07_Lock._03_FieldVisibilityOfLock;

/*
 * 对于可见性(Field Visibility)而言, Lock锁是能够保证可见性的; 因为happens-before规则中的监视器锁规则对Lock锁同样适用,
 * 即: 一个unlock操作happens—before于后面(时间上的先后顺序)对同一个锁的lock操作;
 *     即, 当线程A在同步代码块内执行完成并释放了锁, 然后线程B进入相同同步代码块并获得了锁,
 *     线程A解锁之前的写操作都对线程B可见;
 * 更深一步地说, Lock锁的加锁和解锁与synchronized的Monitor锁的加锁和解锁具有相同的内存语义, 即:
 * 1. 释放锁的内存语义如下:
 *    当线程释放锁时, JMM会把该线程对应的本地内存中的共享变量刷新到主内存中;
 * 2. 获取锁的内存语义如下:
 *    当线程获取锁时, JMM会把该线程对应的本地内存置为无效, 从而使得被锁保护的临界区代码必须要从主内存中去读取共享变量;
 */

public class FieldVisibilityOfLock {
    public static void main(String[] args) {
    }
}
