package _11_ConcurrentCollections._04_CopyOnWriteArrayList;

/*
 * CopyOnWriteArrayListDrawbacks的缺点:
 * 不同于ConcurrentHashMap几乎完美取代了Collections.synchronizedMap, CopyOnWriteArrayList是有一些缺点的:
 * 1. 数据一致性问题:
 *    CopyOnWrite容器只能保证数据的最终一致性, 但不能保证数据的实时一致性;
 *    所以, 如果你希望写入的数据能够马上对其他人可见, 请不要使用CopyOnWrite容器;
 * 2. 内存占用问题:
 *    因为CopyOnWrite容器的写操作是复制机制的, 所以在进行写操作的时候, 这个CopyOnWrite需要同时占用多份内存;
 */

public class CopyOnWriteArrayListDrawbacks {
    public static void main(String[] args) {
    }
}
