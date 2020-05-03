package _11_ConcurrentCollections._03_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._02_SetAndMapUsingBST;

/*
 * 映射的接口
 */

public interface Map<K, V> {

    void add(K key, V value);

    V remove(K key);

    boolean contains(K key);

    V get(K key);

    void set(K key, V newvalue);

    int size();

    boolean isEmpty();
}
