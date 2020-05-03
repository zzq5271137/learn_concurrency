package _11_ConcurrentCollections._03_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._01_BinarySearchTree;

import java.util.ArrayList;
import java.util.Random;

public class BinarySearchTreeTest {
    private static void testTraverse() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums) {
            bst.add(num);
        }
        // System.out.println(bst);

        System.out.println("前序遍历(递归):");
        bst.preOrder();
        System.out.println("\n前序遍历(非递归):");
        bst.noneRecursivePreOrder();
        System.out.println("\n中序遍历(递归):");
        bst.inOrder();
        System.out.println("\n中序遍历(非递归):");
        bst.noneRecursiveInOrder();
        System.out.println("\n后序遍历(递归):");
        bst.afterOrder();
        System.out.println("\n层序遍历:");
        bst.levelOrder();
        System.out.println();
    }

    private static void testRemoveMin() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++)
            bst.add(random.nextInt(10000));
        ArrayList<Integer> nums = new ArrayList<>();
        while (!bst.isEmpty())
            nums.add(bst.removeMin());
        System.out.println(nums);
        for (int i = 1; i < nums.size(); i++)
            if (nums.get(i - 1) > nums.get(i))
                throw new IllegalArgumentException("Error.");
        System.out.println("removeMin test completed.");
    }

    private static void testRemove() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        int[] nums = {20, 18, 25, 10, 19, 23, 27, 26, 30};
        for (int num : nums) {
            bst.add(num);
        }
        System.out.println("删除前:");
        bst.inOrder();
        System.out.println("\n删除25后:");
        bst.remove(25);
        bst.inOrder();
        System.out.println();
    }

    public static void main(String[] args) {
        // testTraverse();
        // testRemoveMin();
        testRemove();
    }
}
