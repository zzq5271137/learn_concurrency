package _11_ConcurrentContainer._02_ConcurrentHashMap._02_ConcurrentHashMapStructure.RedBlackTree;

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
     * 递归算法, 向以node为根的二分搜索树中添加新的元素e, 返回插入新节点后二分搜索树的根
     */
    private Node add(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }

        if (e.compareTo(node.e) < 0) {
            node.left = add(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            node.right = add(node.right, e);
        }
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
