package _11_ConcurrentCollections._02_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._03_AVLTree;

/*
 * AVL树的实现, 平衡的定义为, 每一个节点左右子树的高度差不超过1;
 */

import java.util.ArrayList;

public class AVLTree<K extends Comparable<K>, V> {

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private int height;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
            height = 1;
        }
    }

    private Node root;
    private int size;

    public AVLTree() {
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
     * 返回以node为根的树的高度
     */
    private int getHeight(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    /**
     * 计算并返回以node为根的树的平衡因子
     */
    private int getBalanceFactor(Node node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * 检查树是否满足二分搜索树的基本性质;
     * 利用二分搜索树的一个性质, 即中序遍历二分搜索树时, 遍历出来的值是升序的;
     */
    public boolean isBST() {
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0)
                return false;
        }
        return true;
    }

    /**
     * 检查树是否满足平衡条件, 即, 每一个节点左右子树的高度差不超过1
     */
    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node node) {
        if (node == null)
            return true;
        if (Math.abs(getBalanceFactor(node)) > 1)
            return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }

    /*
     * 右旋转:
     * 对y节点进行右旋转操作, 返回旋转后新的根节点x;
     *       y                         x
     *      / \                      /   \
     *     x  T4      右旋转(y)      z     y
     *    / \       ------------>  / \   / \
     *   z  T3                    T1 T2 T3 T4
     *  / \
     * T1  T2
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T3 = x.right;
        x.right = y;
        y.left = T3;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    /*
     * 左旋转:
     * 对y节点进行左旋转, 返回旋转后新的根节点x;
     *    y                         x
     *   / \                      /   \
     * T1  x        左旋转(y)     y     z
     *    / \     ----------->  / \   / \
     *   T2  z                 T1 T2 T3 T4
     *      / \
     *    T3  T4
     */
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;
        x.left = y;
        y.right = T2;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    /**
     * 使用四种旋转操作, 重新使以node为根的树平衡
     */
    private Node reBalance(Node node) {
        // 计算平衡因子, 如果平衡因子绝对值大于1, 则当前的树不再满足平衡二叉树的条件, 需要做自平衡操作(旋转)
        int nodeBalanceFactor = getBalanceFactor(node);
        int leftBalanceFactor = getBalanceFactor(node.left);
        int rightBalanceFactor = getBalanceFactor(node.right);

        /*
         * 旋转的四种情况:
         * 1. LL(右旋转)
         *    在node的左子树的左子树上插入节点而破坏平衡
         * 2. RR(左旋转)
         *    在node的右子树的右子树上插入节点而破坏平衡
         * 3. LR(先左旋后右旋)
         *    在node的左子树的右子树上插入节点而破坏平衡
         * 4. RL(先右旋后左旋)
         *    在node的右子树的左子树上插入节点而破坏平衡
         */
        if (nodeBalanceFactor > 1 && leftBalanceFactor >= 0) {           // LL
            node = rightRotate(node);
        } else if (nodeBalanceFactor < -1 && rightBalanceFactor <= 0) {  // RR
            node = leftRotate(node);
        } else if (nodeBalanceFactor > 1 && leftBalanceFactor < 0) {     // LR
            node.left = leftRotate(node.left);
            node = rightRotate(node);
        } else if (nodeBalanceFactor < -1 && rightBalanceFactor > 0) {   // RL
            node.right = rightRotate(node.right);
            node = leftRotate(node);
        }

        return node;
    }

    public ArrayList<K> inOrder() {
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        return keys;
    }

    private void inOrder(Node node, ArrayList<K> keys) {
        if (node == null)
            return;
        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    public void add(K key, V value) {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }

        if (key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if (key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else
            node.value = value;

        // 更新height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        return reBalance(node);
    }

    private Node getNode(Node node, K key) {
        if (node == null)
            return null;
        if (key.compareTo(node.key) == 0)
            return node;

        if (key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else
            return getNode(node.right, key);
    }

    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newvalue) {
        Node node = getNode(root, key);
        if (node == null)
            throw new IllegalArgumentException(key + " doesn't exist.");
        node.value = newvalue;
    }

    public V remove(K key) {
        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key) {
        if (node == null)
            return null;

        Node retNode;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else {
            if (node.left == null) {
                size--;
                retNode = node.right;
            } else if (node.right == null) {
                size--;
                retNode = node.left;
            } else {
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;
                retNode = successor;
            }
        }

        if (retNode == null)
            return null;

        // 更新height
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        return reBalance(retNode);
    }

    private Node minimum(Node node) {
        if (node.left == null)
            return node;
        return minimum(node.left);
    }
}
