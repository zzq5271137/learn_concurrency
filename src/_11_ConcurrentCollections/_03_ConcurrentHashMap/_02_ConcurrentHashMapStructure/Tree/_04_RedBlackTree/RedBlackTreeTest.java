package _11_ConcurrentCollections._03_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._04_RedBlackTree;

import _11_ConcurrentCollections._03_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._02_SetAndMapUsingBST.BSTMap;
import _11_ConcurrentCollections._03_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._03_AVLTree.AVLTree;

import java.util.ArrayList;
import java.util.Random;

public class RedBlackTreeTest {
    public static void main(String[] args) {
        int n = 20000000;
        Random random = new Random();
        ArrayList<Integer> testData = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            testData.add(random.nextInt(Integer.MAX_VALUE));
        }

        // 二分搜索树插入耗时
        long startTime = System.nanoTime();
        BSTMap<Integer, Integer> bst = new BSTMap<>();
        for (Integer x : testData) {
            bst.add(x, 1);
        }
        long endTime = System.nanoTime();
        double time = (endTime - startTime) / 1000000000.0;
        System.out.println("BST: " + time + "s");

        // AVL树插入耗时
        startTime = System.nanoTime();
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        for (Integer x : testData) {
            avlTree.add(x, 1);
        }
        endTime = System.nanoTime();
        time = (endTime - startTime) / 1000000000.0;
        System.out.println("AVL Tree: " + time + "s");

        // 红黑树插入耗时
        startTime = System.nanoTime();
        RedBlackTree<Integer, Integer> rbTree = new RedBlackTree<>();
        for (Integer x : testData) {
            rbTree.add(x, 1);
        }
        endTime = System.nanoTime();
        time = (endTime - startTime) / 1000000000.0;
        System.out.println("RedBlack Tree: " + time + "s");
    }
}
