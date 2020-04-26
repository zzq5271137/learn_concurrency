package _11_ConcurrentContainer._02_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._02_SetAndMapUsingBST;

/*
 * 使用二分搜索树实现集合
 */

import _11_ConcurrentContainer._02_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._01_BinarySearchTree.BinarySearchTree;

public class BSTSet<E extends Comparable<E>> implements Set<E> {

    private BinarySearchTree<E> bst;

    public BSTSet() {
        bst = new BinarySearchTree<>();
    }

    @Override
    public void add(E e) {
        bst.add(e);
    }

    @Override
    public void remove(E e) {
        bst.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return bst.contains(e);
    }

    @Override
    public int size() {
        return bst.size();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}
