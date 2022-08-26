package com.zjh.base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Class4 class
 * 链表
 * @author zjh
 * @date 2022/7/26 13:11
 */
public class Class4 {
    /**
     * 输出链表
     */
    public static void printSingleNode(SingleNode node){
        System.out.println("单链表输出开始--------->");
        while (node != null){
            System.out.println(node.next);
            node = node.next;
        }
        System.out.println("单链表输出结束--------->");
    }

    /**
     * 反转单链表
     * 1->2->3->4
     * @param
     * @return
     */
    public static SingleNode reverse(SingleNode head){
        if (head == null){
            return null;
        }
        SingleNode cur = head;
        SingleNode oldHead = null;
        SingleNode newHead = null;
        while(cur != null){            
            oldHead = cur.next;
            cur.next = newHead; // 这里是值传递
            newHead = cur;
            cur = oldHead;
        }
        return newHead;
    }

    /**
     * 反转双链表
     * @param head    <-1->  <-2->  <-3->
     *                
     * @return
     */
    public static DoubleSideNode reverse(DoubleSideNode head){
        if (head == null){
            return null;
        }
        DoubleSideNode cur = head;
        DoubleSideNode oldHead = null;
        DoubleSideNode newHead = null;
        while(cur != null){
            oldHead = cur.right;
            cur.right = newHead; // 这里是值传递
            cur.left = oldHead;
            newHead = cur;
            cur = oldHead;
        }
        return newHead;
    }

    /**
     * 打印两个有序单链表的公共部分
     * @param head1
     * @param head2
     */
    public static void printSingleRepeat(SingleNode head1,SingleNode head2){
        while(head1 != null && head2 != null){
            if (head1.value == head2.value){
                System.out.println(head1.value);
                head1 = head1.next;
                head2 = head2.next;
                continue;
            }
            if (head1.value < head2.value){
                head1 = head1.next;
                continue;
            }
            head2 = head2.next;
        }
    }


    /**
     * 找到单链表的中点位置
     * @param head
     * @return
     */
    public static SingleNode findMidNode(SingleNode head){
        // 使用快慢指针
        SingleNode slow = head;
        SingleNode fast = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 判断一个单链表是否是回文链表
     * @param head
     * @return
     */
    public static Boolean isPalindrome(SingleNode head){
        SingleNode mid = findMidNode(head);
        Stack<SingleNode> stack = new Stack<>();
        while (mid != null){
            stack.push(mid);
            mid = mid.next;
        }
        while (!stack.isEmpty()){
            if (stack.pop().value != head.value){
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 判断一个单链表是否是回文链表
     * 不使用额外空间
     * @param head
     * @return
     */
    public static Boolean isPalindrome2(SingleNode head){
        // 快慢指针找中点
        SingleNode fast = head;
        SingleNode slow = head;
        SingleNode n1 = head;
        SingleNode n2 = null;
        boolean flag = false; // 偶数标记
        while (fast.next != null){
            fast = fast.next.next;
            if (fast == null){
                flag = true;
                n2 = slow.next;
                slow.next = null;
                break;
            }
            if (fast.next != null){
                slow = slow.next;
            }
        }
        SingleNode mid;
        if (!flag){ // 奇数
            n2 = slow.next.next;
            mid = slow.next;
            mid.next = null;
            slow.next = null;
        }
        boolean res = true;
        n2 = reverse(n2);
        while (n1 != null && n2 != null){
            if (n1.value != n2.value) {
                res = false;
                break;
            }
            n1 = n1.next;
            n2 = n2.next;
        }
        
        // SingleNode mid = findMidNode(head);
        // SingleNode n1 = head;   // 首
        // while (head.next != null){
        //     head = head.next;
        // }
        // SingleNode n2 = head;   // 尾
        // reverse(mid);
        // boolean res = true;
        // while(n1 != null && n2 != null){
        //     if (n1.value != n2.value){
        //         res = false;
        //     }
        //     n1 = n1.next;
        //     n2 = n2.next;
        // }
        // // TODO: 2022/7/27 还原右边被反转的  
        return res;
    }

    // need O(1) extra space
    public static boolean isPalindrome3(SingleNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        SingleNode n1 = head;
        SingleNode n2 = head;
        while (n2.next != null && n2.next.next != null) { // find mid node
            n1 = n1.next; // n1 -> mid
            n2 = n2.next.next; // n2 -> end
        }
        n2 = n1.next; // n2 -> right part first node
        n1.next = null; // mid.next -> null
        SingleNode n3 = null;
        while (n2 != null) { // right part convert
            n3 = n2.next; // n3 -> save next node
            n2.next = n1; // next of right node convert
            n1 = n2; // n1 move
            n2 = n3; // n2 move
        }
        n3 = n1; // n3 -> save last node
        n2 = head;// n2 -> left first node
        boolean res = true;
        while (n1 != null && n2 != null) { // check palindrome
            if (n1.value != n2.value) {
                res = false;
                break;
            }
            n1 = n1.next; // left to mid
            n2 = n2.next; // right to mid
        }
        n1 = n3.next;
        n3.next = null;
        while (n1 != null) { // recover list
            n2 = n1.next;
            n1.next = n3;
            n3 = n1;
            n1 = n2;
        }
        return res;
    }

    /**
     * 拷贝randomnode
     * 使用Map
     * @param head
     * @return
     */
    public static RandomNode copyRandomNode1(RandomNode head){
        RandomNode index = head;
        Map<RandomNode,RandomNode> map = new HashMap<>();
        while (index != null) {
            map.put(index,new RandomNode(index.value));
            index = index.next;
        }
        for (RandomNode key : map.keySet()) {
            map.get(key).random = map.get(key.random);
            map.get(key).next = map.get(key.next);
        }
        return map.get(head);
    }

    /**
     * 拷贝randomnode
     * 不使用额外结构
     * @param head
     * @return
     */
    public static RandomNode copyRandomNode2(RandomNode head){
        if (head == null || head.next == null){
            return head;
        }
        RandomNode index = head;
        while (index != null) {
            RandomNode copyNode = new RandomNode(index.value);
            copyNode.next = index.next;
            index.next = copyNode;
            index = index.next.next;
        }
        index = head;
        while (index != null){
            index.next.random = index.random != null ? index.random.next : null;
            index = index.next.next;
        }
        index = head;
        RandomNode res = head.next;
        while (index != null) {
            RandomNode i = index.next;
            if (i.next == null){
                index.next = null;
                break;
            }
            index.next = index.next.next;
            index = i;
        }
        return res;
    }

    /**
     * 给定一个单链表的头结点head，判断该单链表是否存在环，如果有环返回第一个入环的结点，如果无环返回null
     * 
     * 使用set
     * @param head
     * @return
     */
    public static SingleNode hasRing1(SingleNode head){
        Set<SingleNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head))
                return head;
            set.add(head);
            head = head.next;
        }
        return null;
    }

    /**
     * 给定一个单链表的头结点head，判断该单链表是否存在环，如果有环返回第一个入环的结点，如果无环返回null
     * 
     * 不使用额外空间，使用快慢指针
     * 当快慢指针第一次相遇时，让快指针回到头结点，然后快指针step=1，继续运动，再次相遇时的结点就是环入口
     * @param head
     * @return
     */
    public static SingleNode hasRing2(SingleNode head) {
        SingleNode slow = head;
        SingleNode fast = head;

        while (fast != null) {
            fast = fast.next != null ? fast.next.next : null;
            slow = slow.next;
            if (slow == fast)
                break;
        }
        if (fast == null)
            return null;
        fast = head;
        while (fast != null) {
            if (slow == fast)
                return fast;
            fast = fast.next;
            slow = slow.next;
        }
        return null;
    }
    

    /**
     * 两个单链表相交的一系列问题
     * 给定两个可能有环也可能无环的单链表，头节点head1和head2。
     * 请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交，返回null
     * 如果两个链表长度之和为N,时间复杂度请达到O(N),额外空间复杂度请达到O(1)
     * @param head1
     * @param head2
     * @return
     */
    public static SingleNode findIntersect(SingleNode head1,SingleNode head2){
        SingleNode ringEntrance1 = hasRing2(head1);
        SingleNode ringEntrance2 = hasRing2(head2);
        SingleNode n1 = head1;
        SingleNode n2 = head2;
        // 两个链表都无环
        if (ringEntrance1 == null && ringEntrance2 == null){
            int lengthDifference = 0;   // 长度的差值
            while(n1.next != null){
                n1 = n1.next;
                lengthDifference++;
            }
            while(n2.next != null){
                n2 = n2.next;
                lengthDifference--;
            }
            if (n1 != n2){
                // 如果两个无环链表尾结点不是同一个，那么两链表不可能相交（若两单链表相交，则从相交位置开始必将一路重合至末尾结点）
                return null;
            }
            n1 = lengthDifference > 0 ? head1 : head2;  // n1是长的，n2是短的
            n2 = n1 == head1 ? head2 : head1;
            lengthDifference = Math.abs(lengthDifference);
            while (lengthDifference > 0){
                n1 = n1.next;
                lengthDifference--;
            }
            while (n1 != n2){
                n1 = n1.next;
                n2 = n2.next;
            }
            return n1;
        }
        // 一个链表有环，另一个链表无环，这种情况不可能相交
        if ((ringEntrance1 == null && ringEntrance2 !=null) || (ringEntrance2 == null && ringEntrance1 !=null))
            return null;
        // 两个都有环 入环节点是同一个
        if (ringEntrance1 == ringEntrance2){
            int lengthDifference = 0;   // 长度的差值
            while(n1 != ringEntrance1 && n1.next != ringEntrance1){
                n1 = n1.next;
                lengthDifference++;
            }
            while(n2 != ringEntrance1 && n2.next != ringEntrance1){
                n2 = n2.next;
                lengthDifference--;
            }
            n1 = lengthDifference > 0 ? head1 : head2;  // n1是长的，n2是短的
            n2 = n1 == head1 ? head2 : head1;
            lengthDifference = Math.abs(lengthDifference);
            while (lengthDifference > 0){
                n1 = n1.next;
                lengthDifference--;
            }
            while (n1 != n2){
                n1 = n1.next;
                n2 = n2.next;
            }
            return n1;
        }
        // 两个都有环，但是入环节点不是同一个
        SingleNode circleStart = ringEntrance2;
        boolean flag = true;    // 是否是两个独立的环
        do {
            circleStart = circleStart.next;
            if (circleStart == ringEntrance1){
                flag = false;
                break;
            }
        }while (circleStart != ringEntrance2);
        if (flag)
            return null;
        return ringEntrance1;
    } 
    
    public static void main(String[] args) {
        // RandomNode randomNode1 = new RandomNode(1);
        // RandomNode randomNode2 = new RandomNode(2);
        // RandomNode randomNode3 = new RandomNode(3);
        // randomNode1.next = randomNode2;
        // randomNode2.next = randomNode3;
        //
        // randomNode1.random = randomNode3;
        // randomNode3.random = randomNode1;
        // RandomNode copyResult = copyRandomNode2(randomNode1);


        SingleNode singleNode1 = new SingleNode(1);
        SingleNode singleNode2 = new SingleNode(2);
        SingleNode singleNode3 = new SingleNode(3);
        SingleNode singleNode4 = new SingleNode(4);
        SingleNode singleNode5 = new SingleNode(5);
        singleNode1.next = singleNode2;
        singleNode2.next = singleNode3;
        singleNode3.next = singleNode4;
        singleNode4.next = singleNode5;
        singleNode5.next = singleNode3;
        
        SingleNode singleNode10 = new SingleNode(10);
        SingleNode singleNode11 = new SingleNode(11);
        singleNode10.next = singleNode11;
        singleNode11.next = singleNode3;
        
        System.out.println(findIntersect(singleNode1,singleNode5));

        Set<Long> set = new HashSet<>();
        set.add(1l);
        set.add(2l);
        List<Long> longs = Arrays.asList(set.toArray(new Long[0]));
        // printSingleNode(singleNode1);
        // singleNode1 = singleNode2;
        // singleNode2 = singleNode3;
        // System.out.println(singleNode1);

        // printSingleNode(reverse(singleNode1));
        
        DoubleSideNode<Integer> doubleSideNode1 = new DoubleSideNode<>(1);
        DoubleSideNode<Integer> doubleSideNode2 = new DoubleSideNode<>(2);
        DoubleSideNode<Integer> doubleSideNode3 = new DoubleSideNode<>(3);
        doubleSideNode1.right = doubleSideNode2;
        doubleSideNode2.left = doubleSideNode1;
        doubleSideNode2.right =doubleSideNode3;
        doubleSideNode3.left = doubleSideNode2;
        reverse(doubleSideNode1);
    }
}

class SingleNode{
    public int value;

    public SingleNode next;

    public SingleNode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SingleNode{" +
                "value=" + value +
                ", next=" + next.value +
                '}';
    }
}

class DoubleSideNode<T>{
    public T value;
    
    public DoubleSideNode<T> left;
    
    public DoubleSideNode<T> right;

    public DoubleSideNode(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DoubleSideNode{" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}

class RandomNode{
    int value;
    RandomNode next;
    RandomNode random;

    public RandomNode() {
    }

    public RandomNode(int value) {
        this.value = value;
    }

    // @Override
    // public String toString() {
    //     return "RandomNode{" +
    //             "value=" + value +
    //             ", next=" + next +
    //             ", random=" + random +
    //             '}';
    // }
}
