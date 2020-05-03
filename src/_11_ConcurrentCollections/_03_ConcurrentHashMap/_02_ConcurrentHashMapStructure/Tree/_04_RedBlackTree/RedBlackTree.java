package _11_ConcurrentCollections._03_ConcurrentHashMap._02_ConcurrentHashMapStructure.Tree._04_RedBlackTree;

/*
 * 红黑树的实现, 注意, 红黑树与2-3树是等价的;
 */

public class RedBlackTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private boolean color;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
            color = RED;  // 新插入的元素默认为红色, 因为对应到2-3树中, 新插入的元素始终先与某个节点进行融合
        }
    }

    private Node root;
    private int size;

    public RedBlackTree() {
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
     * 判断并返回node节点是否是红色节点
     */
    private boolean isRed(Node node) {
        if (node == null)
            return BLACK;
        return node.color;
    }

    /*
     * 红黑树中的左旋转:
     * 1. 情况一:
     *    当向一个黑色节点的右边插入一个元素, 而此黑节点没有红色左孩子时, (对应到2-3树中就是向一个2节点的右边插入一个元素),
     *    就需要左旋转, 并维护节点颜色, 以保证红黑树的性质;
     * 2. 情况二:
     *    当向一个红色节点的右边插入一个元素时, (对应到2-3树中就是向一个3节点的中间插入一个元素),
     *    需要3步:
     *    a). 对这个红色节点进行左旋转;
     *    b). 对父节点(黑色节点)进行右旋转, 详见rightRotate()的情况二;
     *    c). 颜色翻转, 详见flipColors()的情况三;
     *
     * 左旋转:
     *    node                     x
     *  /     \      左旋转       /   \
     * T1     x    --------->  node  T3
     *      /  \              /   \
     *     T2  T3            T1   T2
     * 旋转完之后, x的颜色应赋值为node的原有颜色, 然后node的颜色应赋值为红色;
     */
    private Node leftRotate(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    /*
     * 红黑树中的右旋转:
     * 1. 情况一:
     *    当向一个红色节点的左边插入一个元素时, (对应到2-3树中就是向一个3节点的左边插入一个元素),
     *    就需要右旋转, 并维护节点颜色, 以保证红黑树的性质;
     *    需要颜色翻转, 详见flipColors()的情况二;
     * 2. 情况二:
     *    详见上方leftRotate()的情况二;
     *
     * 右旋转:
     *      node                     x
     *     /    \       右旋转      /   \
     *    x     T2    --------->  y    node
     *  /  \                          /    \
     * y   T1                        T1    T2
     * 旋转完之后, x的颜色应赋值为node的原有颜色, 然后node的颜色应赋值为红色;
     */
    private Node rightRotate(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    /*
     * 红黑树中的颜色翻转:
     * 1. 情况一:
     *    当向一个黑色节点的右边插入一个元素, 而此黑节点有一个红色左孩子时, (对应到2-3树中就是向一个3节点的右边插入一个元素),
     *    就需要颜色翻转, 以保证红黑树的性质;
     * 2. 情况二:
     *    详见rightRotate()的情况一;
     * 3. 情况三:
     *    详见leftRotate()的情况二;
     *
     */
    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    /**
     * 维护以node为根的树的红黑树性质;
     * 这是一个逻辑链条, 详见图片"向红黑树中添加新元素的维护逻辑链条.png";
     */
    private Node reBalance(Node node) {
        // 这些不是互斥的, 需要一步一步判断
        if (isRed(node.right) && !isRed(node.left))
            node = leftRotate(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rightRotate(node);
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);
        return node;
    }

    public void add(K key, V value) {
        root = add(root, key, value);
        root.color = BLACK;  // 保持整棵树的根节点为黑色
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);  // 默认插入的节点为红色, 详见构造方法
        }

        if (key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if (key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else
            node.value = value;

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

    /*
     * 红黑树的删除比较复杂, 不在这里实现
     */
}
