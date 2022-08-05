package com.zjh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Class5 class
 * 二叉树
 *
 * @author zjh
 * @date 2022/8/2 11:38
 */
public class Class5 {

    // 递归算法

    /**
     * 先序遍历
     *
     * @param node
     */
    public static void preOrderRecur(Node node) {
        if (node == null)
            return;
        System.out.println(node.value);
        preOrderRecur(node.left);
        preOrderRecur(node.right);
    }

    /**
     * 中序遍历
     *
     * @param node
     */
    public static void inOrderRecur(Node node) {
        if (node == null)
            return;
        inOrderRecur(node.left);
        System.out.println(node.value);
        inOrderRecur(node.right);
    }

    /**
     * 后序遍历
     *
     * @param node
     */
    public static void posOrderRecur(Node node) {
        if (node == null)
            return;
        posOrderRecur(node.left);
        posOrderRecur(node.right);
        System.out.println(node.value);
    }

    // 非递归算法

    /**
     * 先序遍历
     * 准备一个栈，压入一个节点，弹出，打印，压入右节点，压入左节点，重复
     *
     * @param node
     */
    public static void preOrderUnRecur(Node node) {
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node outNode = stack.pop();
            System.out.println(outNode.value);
            if (outNode.right != null)
                stack.push(outNode.right);
            if (outNode.left != null)
                stack.push(outNode.left);
        }
    }

    /**
     * 中序遍历
     * 准备一个栈，压入头结点及其左边界，然后进行弹出，弹出节点若有右节点，重复上述步骤
     *
     * @param node
     */
    public static void inOrderUnRecur(Node node) {
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            if (node.left != null) {
                stack.push(node.left);
                node = node.left;
                continue;
            }
            node = stack.pop();
            System.out.println(node.value);
            if (node.right != null) {
                stack.push(node.right);
                node = node.right;
            }
        }
    }

    /**
     * 后序遍历
     * 准备一个栈和一个辅助栈，压入一个节点，弹出并压入辅助栈，压入左节点，压入右节点，重复，当前栈为空后，依次弹出辅助栈输出
     *
     * @param node
     */
    public static void posOrderUnRecur(Node node) {
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(node);
        while (!stack1.isEmpty()) {
            Node outNode = stack1.pop();
            stack2.push(outNode);
            if (outNode.left != null)
                stack1.push(outNode.left);
            if (outNode.right != null)
                stack1.push(outNode.right);
        }
        while (!stack2.isEmpty()) {
            System.out.println(stack2.pop().value);
        }
    }

    /**
     * 二叉树的宽度优先遍历（层序遍历）
     * 使用队列（先进先出）,入队一个节点，
     *
     * @param node
     */
    public static void wideOrder(Node node) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            System.out.println(poll.value);
            if (poll.left != null) {
                queue.add(poll.left);
            }
            if (poll.right != null) {
                queue.add(poll.right);
            }
        }
    }

    /**
     * 找到二叉树的最大宽度层以及该层的数量
     * 使用一个map，可以记录每一个节点是第几层
     *
     * @param node
     */
    public static void findMaxWidthUseMap(Node node) {
        Map<Node, Integer> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        int maxLevel = 1;
        int maxLevelCount = 1;
        int curLevel = 1;
        int curLevelCount = 0;
        map.put(node, curLevel);
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            if (map.get(poll) == curLevel) {
                // 在同一层
                curLevelCount++;
            } else {
                // 新的层
                maxLevel = curLevelCount > maxLevelCount ? curLevel : maxLevel;
                maxLevelCount = Math.max(curLevelCount, maxLevelCount);
                curLevel++;
                curLevelCount = 1;
            }
            if (poll.left != null) {
                map.put(poll.left, curLevel + 1);
                queue.add(poll.left);
            }
            if (poll.right != null) {
                map.put(poll.right, curLevel + 1);
                queue.add(poll.right);
            }
        }
        // 最后一层的数量在循环内没有被比较到，最后再判断一次
        boolean flag = maxLevelCount < curLevelCount;
        System.out.println("maxLevel:" + (flag ? ++maxLevel : maxLevel) + ",maxLevelCount:" + (flag ? curLevelCount : maxLevelCount));
    }

    /**
     * 找到二叉树的最大宽度层以及该层的数量
     * 不使用map
     *
     * @param node
     */
    public static void findMaxWidth(Node node) {
        Node curEnd = node; // 当前层的最右节点
        Node nextEnd = null; // 下一层的最右节点
    }

    /**
     * 判断二叉树是否是搜索二叉树（左树比根节点小，右数比根节点大）
     * 中序遍历(非递归)，判断输出是否是升序
     *
     * @param node
     */
    public static boolean isSearchNode(Node node) {
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        Integer var = Integer.MIN_VALUE;
        while (!stack.isEmpty()) {
            if (node.left != null) {
                stack.push(node.left);
                node = node.left;
                continue;
            }
            node = stack.pop();
            // 中序遍历放入list中
            if (var >= node.value) {
                return false;
            }
            var = node.value;
            if (node.right != null) {
                stack.push(node.right);
                node = node.right;
            }
        }
        return true;
    }
    
    /**
     * 判断二叉树是否是搜索二叉树（左树比根节点小，右数比根节点大）
     * 中序遍历(递归)，判断输出是否是升序
     *
     * @param node
     */
    public static int preValue = Integer.MIN_VALUE;

    public static boolean isSearchNode2(Node node) {
        if (node == null) {
            return true;
        }
        boolean leftFlag = isSearchNode2(node.left);
        if (!leftFlag)
            return false;
        if (node.value <= preValue) {
            return false;
        } else {
            preValue = node.value;
        }
        return isSearchNode2(node.right);
    }

    /**
     * 判断一棵树是否是完全二叉树
     * 使用宽度优先遍历
     * 1）任意一个节点，如果（左孩子==null && 右孩子！=null） 直接false
     * 2）宽度遍历过程中，遇到的第一个左右孩子不双全的结点开始，之后的所有节点必须是叶子节点，否则false
     * 
     * @param node
     * @return
     */
    public static boolean isCompleteBinaryTree(Node node) {
        boolean flag = false;   // 是否已经遇到了第一个左右孩子不双全的结点
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        Node l = null;
        Node r = null;
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            l = poll.left;
            r = poll.right;
            if (
                    (flag && (l != null || r != null)) 
                    || 
                    (l == null && r != null)
            )
                return false;
            if (l != null)
                queue.add(l);
            if (r != null)
                queue.add(r);
            if (l == null || r == null)
                flag = true;
        }
        return true;
    }
    

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;
        System.out.println("===inOrderUnRecur===");
        inOrderUnRecur(n1);
        System.out.println("===posOrderUnRecur===");
        posOrderUnRecur(n1);
        System.out.println("===preOrderUnRecur===");
        preOrderUnRecur(n1);
        System.out.println("===wideOrder===");
        wideOrder(n1);
        findMaxWidthUseMap(n1);
        // 构造一棵搜索二叉树
        Node x5 = new Node(5);
        Node x3 = new Node(3);
        Node x7 = new Node(7);
        Node x1 = new Node(1);
        Node x4 = new Node(4);
        Node x6 = new Node(6);
        Node x9 = new Node(9);
        x5.left = x3;
        x5.right = x7;
        x3.left = x1;
        x3.right = x4;
        // x7.left = x6;
        x7.right = x9;
        System.out.println(isSearchNode2(x5));
        System.out.println(isCompleteBinaryTree(x5));
        System.out.println("==========================");

        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("12");
        Object[] array = set.toArray();
        String[] array1 = set.toArray(new String[0]);
        for (Object o : array) {
            System.out.println(o);
        }
        for (String s : array1) {
            System.out.println(s);
        }
        System.out.println("=================");
        int[] a = new int[]{1, 2, 0, 3};
        for (int i : a) {
            try {
                System.out.println(1 / i);
                System.out.println(i);
            } catch (Exception e) {

            }
        }
    }
}

/**
 * 二叉树
 */
class Node {
    int value;

    Node left;

    Node right;

    public Node(int value) {
        this.value = value;
    }

    public Node() {
    }
}
