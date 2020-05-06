package _15_SimpleProject._02_Computable1;

/*
 * 计算的接口, 用来代表耗时计算, 每个计算器都要实现这个接口, 计算应与缓存分开;
 */

public interface Computable<A, V> {

    V compute(A arg) throws Exception;

}
