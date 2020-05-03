package _11_ConcurrentCollections._03_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._03_AVLTree;

public class AVLTreeTest {
    private static void testAdd() {
        int[] nums = {20, 17, 24, 10, 22};
        AVLTree<Integer, Integer> tree = new AVLTree<>();
        for (int num : nums) {
            tree.add(num, 1);
        }
        System.out.println(tree.inOrder());
        System.out.println("是否是二叉搜索树: " + tree.isBST());
        System.out.println("是否是平衡树: " + tree.isBalanced());
        System.out.println("============================");
        System.out.println("插入9后:");
        tree.add(9, 1);
        System.out.println(tree.inOrder());
        System.out.println("是否是二叉搜索树: " + tree.isBST());
        System.out.println("是否是平衡树: " + tree.isBalanced());
    }

    private static void testRemove() {
        int[] nums = {20, 17, 24, 10, 22, 27, 21, 26, 30, 31};
        AVLTree<Integer, Integer> tree = new AVLTree<>();
        for (int num : nums) {
            tree.add(num, 1);
        }
        System.out.println(tree.inOrder());
        System.out.println("是否是二叉搜索树: " + tree.isBST());
        System.out.println("是否是平衡树: " + tree.isBalanced());
        System.out.println("============================");
        System.out.println("删除24后:");
        tree.remove(24);
        System.out.println(tree.inOrder());
        System.out.println("是否是二叉搜索树: " + tree.isBST());
        System.out.println("是否是平衡树: " + tree.isBalanced());
    }

    public static void main(String[] args) {
        // testAdd();
        testRemove();
    }
}
