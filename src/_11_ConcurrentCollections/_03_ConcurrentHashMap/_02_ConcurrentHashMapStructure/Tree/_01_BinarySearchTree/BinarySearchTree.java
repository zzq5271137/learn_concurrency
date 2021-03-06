package _11_ConcurrentCollections._03_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._01_BinarySearchTree;

/*
 * 二分搜索树的实现
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinarySearchTree<E extends Comparable<E>> {

    private class Node {
        public E e;
        public Node left, right;

        public Node(E e) {
            this.e = e;
            left = right = null;
        }
    }

    private Node root;
    private int size;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 向二分搜索树中添加新的元素e
     */
    public void add(E e) {
        root = add(root, e);
    }

    /**
     * 递归算法, 向以node为根的二分搜索树中添加新的元素e, 返回插入新节点后新的二分搜索树的根
     */
    private Node add(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }

        if (e.compareTo(node.e) < 0)
            node.left = add(node.left, e);
        else if (e.compareTo(node.e) > 0)
            node.right = add(node.right, e);
        return node;
    }

    /**
     * 查询二分搜索树中是否包含元素e
     */
    public boolean contains(E e) {
        return contains(root, e);
    }

    /**
     * 递归算法, 查询以node为根的二分搜索树中是否包含元素e
     */
    private boolean contains(Node node, E e) {
        if (node == null)
            return false;
        if (e.compareTo(node.e) == 0)
            return true;

        if (e.compareTo(node.e) < 0)
            return contains(node.left, e);
        else
            return contains(node.right, e);
    }

    /**
     * 二分搜索树的前序遍历, 深度优先
     */
    public void preOrder() {
        preOrder(root);
    }

    /**
     * 递归算法, 前序遍历以node为根的二分搜索树
     */
    private void preOrder(Node node) {
        if (node == null)
            return;

        System.out.print(node.e + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 前序遍历的非递归写法(借助栈)
     */
    public void noneRecursivePreOrder() {
        if (root == null)
            return;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            System.out.print(current.e + " ");
            if (current.right != null)
                stack.push(current.right);
            if (current.left != null)
                stack.push(current.left);
        }
    }

    /**
     * 二分搜索树的中序遍历, 深度优先, 中序遍历的结果正是二分搜索树中元素正序排序后的结果
     */
    public void inOrder() {
        inOrder(root);
    }

    /**
     * 递归算法, 中序遍历以node为根的二分搜索树
     */
    private void inOrder(Node node) {
        if (node == null)
            return;

        inOrder(node.left);
        System.out.print(node.e + " ");
        inOrder(node.right);
    }

    /**
     * 中序遍历的非递归写法(借助栈)
     */
    public void noneRecursiveInOrder() {
        if (root == null)
            return;
        Stack<Node> stack = new Stack<>();
        Node current = root;
        while (!stack.isEmpty() || current != null) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            Node pick = stack.pop();
            System.out.print(pick.e + " ");
            if (pick.right != null)
                current = pick.right;
        }
    }

    /**
     * 二分搜索树的后序遍历, 深度优先
     */
    public void afterOrder() {
        afterOrder(root);
    }

    /**
     * 递归算法, 后序遍历以node为根的二分搜索树
     */
    private void afterOrder(Node node) {
        if (node == null)
            return;

        afterOrder(node.left);
        afterOrder(node.right);
        System.out.print(node.e + " ");
    }

    /**
     * 二分搜索树的层序遍历, 非递归算法, 借助队列, 广度优先
     */
    public void levelOrder() {
        if (root == null)
            return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node current = queue.remove();
            System.out.print(current.e + " ");
            if (current.left != null)
                queue.add(current.left);
            if (current.right != null)
                queue.add(current.right);
        }
    }

    /**
     * 寻找二分搜索树中的最小元素
     */
    public E minimum() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty.");
        return minimum(root).e;
    }

    /**
     * 递归算法, 寻找以node为根的二分搜索树中的最小元素
     */
    private Node minimum(Node node) {
        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    /**
     * 寻找二分搜索树中的最大元素
     */
    public E maximum() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty.");
        return maximum(root).e;
    }

    /**
     * 递归算法, 寻找以node为根的二分搜索树中的最大元素
     */
    private Node maximum(Node node) {
        if (node.right == null)
            return node;
        return maximum(node.right);
    }

    /**
     * 从二分搜索树中删除最小值所在的节点, 并返回最小值
     */
    public E removeMin() {
        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    /**
     * 递归算法, 删除掉以node为根的二分搜索树中的最小节点, 返回删除节点后新的二分搜索树的根
     */
    private Node removeMin(Node node) {
        if (node.left == null) {
            size--;
            return node.right;
        }

        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 从二分搜索树中删除最大值所在的节点, 并返回最大值
     */
    public E removeMax() {
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    /**
     * 递归算法, 删除掉以node为根的二分搜索树中的最大节点, 返回删除节点后新的二分搜索树的根
     */
    private Node removeMax(Node node) {
        if (node.right == null) {
            size--;
            return node.left;
        }

        node.right = removeMax(node.right);
        return node;
    }

    /**
     * 从二分搜索树中删除值为e的节点
     */
    public void remove(E e) {
        root = remove(root, e);
    }

    /**
     * 递归算法, 删除掉以node为根的二分搜索树中值为e的节点, 返回删除节点后新的二分搜索树的根
     */
    private Node remove(Node node, E e) {
        if (node == null)
            return null;

        if (e.compareTo(node.e) < 0) {
            node.left = remove(node.left, e);
            return node;
        }
        if (e.compareTo(node.e) > 0) {
            node.right = remove(node.right, e);
            return node;
        }

        /*
         * 当前节点即为待删除的节点的情况, 即e.compareTo(node.e) == 0
         */
        // 待删除的节点的左子树为空的情况
        if (node.left == null) {
            size--;
            return node.right;
        }

        // 待删除的节点的右子树为空的情况
        if (node.right == null) {
            size--;
            return node.left;
        }

        /*
         * 待删除的节点的左右子树都不为空的情况;
         * 找到比待删除的节点大的最小节点, 即待删除的节点的右子树的最小节点,
         * 用这个节点顶替待删除节点的位置;
         */
        Node successor = minimum(node.right);
        successor.right = removeMin(node.right);
        successor.left = node.left;
        return successor;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    /**
     * 递归算法, 生成以node为根节点、深度为depth的描述二叉搜索树的字符串, 前序遍历的方式
     */
    private void generateBSTString(Node node, int depth, StringBuilder res) {
        if (node == null) {
            res.append(generateDepthString(depth)).append("null").append("\n");
            return;
        }

        res.append(generateDepthString(depth)).append(node.e).append("\n");
        generateBSTString(node.left, depth + 1, res);
        generateBSTString(node.right, depth + 1, res);
    }

    private String generateDepthString(int depth) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            res.append("--");
        }
        return res.toString();
    }
}
