package _11_ConcurrentCollections._02_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._02_SetAndMapUsingBST;

/*
 * 集合的接口
 */

public interface Set<E> {

    void add(E e);

    void remove(E e);

    boolean contains(E e);

    int size();

    boolean isEmpty();
}
