package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._06_Theory;

/*
 * 反编译(反汇编)来观察synchronized底层原理及monitor具体指令;
 * 步骤:
 * 1. 将java文件编译成字节码(.class文件): javac SynchronizedDecompilation.java
 * 2. 将字节码反汇编: javap -verbose SynchronizedDecompilation.class
 */

public class SynchronizedDecompilation {
    final private Object MY_LOCK = new Object();

    public void func() {
        synchronized (MY_LOCK) {
        }
    }
}
