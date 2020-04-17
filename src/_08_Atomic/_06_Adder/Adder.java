package _08_Atomic._06_Adder;

/*
 * Adder累加器
 * a). LongAdder
 * b). DoubleAdder
 *
 * Adder累加器:
 * 是Java8引入的; 高并发下LongAdder比AtomicLong效率高, 不过本质是空间换时间; 竞争激烈的时候,
 * LongAdder把不同线程对应到不同的Cell上进行修改, 降低了冲突的概率, 是多段锁的理念,
 * 提高了并发性; 详见AtomicLongDrawbackDemo.java和LongAdderDemo.java的运行结果对比;
 */

public class Adder {
    public static void main(String[] args) {
    }
}
