package com.zjh;

import java.util.ArrayList;
import java.util.List;
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
