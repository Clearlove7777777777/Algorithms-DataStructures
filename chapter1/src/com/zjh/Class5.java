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
     * @param node
     */
    public static void preOrderRecur(Node node){
        if (node == null)
            return;
        System.out.println(node.value);
        preOrderRecur(node.left);
        preOrderRecur(node.right);
    }

    /**
     * 中序遍历
     * @param node
     */
    public static void inOrderRecur(Node node){
        if (node == null)
            return;
        inOrderRecur(node.left);
        System.out.println(node.value);
        inOrderRecur(node.right);
    }

    /**
     * 后序遍历
     * @param node
     */
    public static void posOrderRecur(Node node){
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
     * @param node
     */
    public static void preOrderUnRecur(Node node){
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()){
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
     * @param node
     */
    public static void inOrderUnRecur(Node node){
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()){
            if (node.left != null){
                stack.push(node.left);
                node = node.left;
                continue;
            }
            node = stack.pop(); 
            System.out.println(node.value);
            if (node.right != null){
                stack.push(node.right);
                node = node.right;
            }
        }
    }

    /**
     * 后序遍历
     * 准备一个栈和一个辅助栈，压入一个节点，弹出并压入辅助栈，压入左节点，压入右节点，重复，当前栈为空后，依次弹出辅助栈输出
     * @param node
     */
    public static void posOrderUnRecur(Node node){
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(node);
        while (!stack1.isEmpty()){
            Node outNode = stack1.pop();
            stack2.push(outNode);
            if (outNode.left != null)
                stack1.push(outNode.left);
            if (outNode.right != null)
                stack1.push(outNode.right);
        }
        while (!stack2.isEmpty()){
            System.out.println(stack2.pop().value);
        }
    }

    /**
     * 二叉树的宽度优先遍历（层序遍历）
     * 使用队列（先进先出）,入队一个节点，
     * @param node
     */
    public static void wideOrder(Node node){
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()){
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
     * @param node
     */
    public static void findMaxWidthUseMap(Node node){
        Map<Node,Integer> map = new HashMap<>(); 
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        
        int maxLevel = 1;
        int maxLevelCount = 1;
        int curLevel = 1;
        int curLevelCount = 0;
        map.put(node,curLevel);
        while (!queue.isEmpty()){
            Node poll = queue.poll();
            if (map.get(poll) == curLevel){
                // 在同一层
                curLevelCount++;
            }else {
                // 新的层
                maxLevel = curLevelCount > maxLevelCount ? curLevel : maxLevel;
                maxLevelCount = Math.max(curLevelCount,maxLevelCount);
                curLevel++;
                curLevelCount = 1;
            }
            if (poll.left != null) {
                map.put(poll.left,curLevel+1);
                queue.add(poll.left);
            }
            if (poll.right != null) {
                map.put(poll.right,curLevel+1);
                queue.add(poll.right);
            }
        }
        // 最后一层的数量在循环内没有被比较到，最后再判断一次
        boolean flag = maxLevelCount < curLevelCount;
        System.out.println("maxLevel:"+(flag?++maxLevel:maxLevel)+",maxLevelCount:"+(flag?curLevelCount:maxLevelCount));
    }

    /**
     * 找到二叉树的最大宽度层以及该层的数量
     * 不使用map
     * @param node
     */
    public static void findMaxWidth(Node node){
        
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
        int[] a = new int[]{1,2,0,3};
        for (int i : a) {
            try {
                System.out.println(1/i);
                System.out.println(i);
            }catch (Exception e){
                
            }
        }
    }
}

/**
 * 二叉树
 */
class Node{
    int value;
    
    Node left;
    
    Node right;

    public Node(int value) {
        this.value = value;
    }

    public Node() {
    }
}
