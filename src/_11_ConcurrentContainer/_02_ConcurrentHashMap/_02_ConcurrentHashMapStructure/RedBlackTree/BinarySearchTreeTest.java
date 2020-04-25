package _11_ConcurrentContainer._02_ConcurrentHashMap._02_ConcurrentHashMapStructure.RedBlackTree;

public class BinarySearchTreeTest {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums) {
            bst.add(num);
        }
        System.out.println("前序遍历:");
        bst.preOrder();
        System.out.println("\n前序遍历(非递归):");
        bst.noneRecursivePreOrder();
        System.out.println("\n中序遍历:");
        bst.inOrder();
        System.out.println("\n后序遍历:");
        bst.afterOrder();
        System.out.println();
        // System.out.println(bst);
    }
}
