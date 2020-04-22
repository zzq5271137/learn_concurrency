package _11_ConcurrentContainer._02_ConcurrentHashMap._02_ConcurrentHashMapStructure.NodePractise;

/*
 * 链表练习:
 * 1. 创建一个链表的数组, 数组长度为5, 每个位置上是一个Node的链表, 链表长度为6, 初始化整个数组;
 * 2. 打印出整个数组;
 * 3. 将数组扩容成两倍, 并将每个链表长度减半分别存储到新数组的两个位置;
 */

import java.util.Random;

class Node {
    int value;
    Node next;

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
}

public class NodePractise {
    /**
     * 初始化Node数组
     *
     * @see #initNodeLink() 初始化数组上每个位置的链表
     */
    public static Node[] initNodeArr() {
        Node[] nodes = new Node[5];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = initNodeLink();
        }
        return nodes;
    }

    /**
     * 初始化链表
     */
    public static Node initNodeLink() {
        Node node = new Node(new Random().nextInt(10), null);
        Node temp = node;
        for (int i = 0; i < 5; i++) {
            temp.next = new Node(new Random().nextInt(10), null);
            temp = temp.next;
        }
        return node;
    }

    /**
     * 扩容Node数组
     *
     * @see #reverseNodeArr(Node[]) 将新生成的Node数组中的每个链表进行倒序, 以保证新数组中链表元素的顺序不变
     */
    public static Node[] resize(Node[] oldNodes, int factor) {
        Node[] newNodes = new Node[oldNodes.length * factor];
        for (int i = 0; i < oldNodes.length; i++) {
            Node node = oldNodes[i];
            int times = 0;
            while (null != node) {
                Node next = node.next;
                int index;
                if (times > 2) {
                    index = 2 * i + 1;
                } else {
                    index = 2 * i;
                }
                node.next = newNodes[index];
                newNodes[index] = node;
                node = next;
                times++;
            }
        }
        reverseNodeArr(newNodes);
        return newNodes;
    }

    /**
     * 将Node数组中的每个链表进行倒序
     *
     * @see #reverseNodeLink(Node) 将单个链表进行倒序操作
     */
    public static void reverseNodeArr(Node[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = reverseNodeLink(nodes[i]);
        }
    }

    /**
     * 将单个链表进行倒序操作
     */
    public static Node reverseNodeLink(Node node) {
        Node temp = null;
        while (node != null) {
            Node next = node.next;
            node.next = temp;
            temp = node;
            node = next;
        }
        return temp;
    }

    /**
     * 打印整个Node数组
     */
    public static void printNodeArr(Node[] nodes) {
        for (Node node : nodes) {
            while (null != node) {
                System.out.print(node.value + " ");
                node = node.next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("Original nodes array:");
        Node[] nodes = initNodeArr();
        printNodeArr(nodes);
        System.out.println("After resize:");
        nodes = resize(nodes, 2);
        printNodeArr(nodes);
    }
}
