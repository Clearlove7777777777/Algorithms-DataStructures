package com.zjh;

import sun.plugin.net.protocol.jar.CachedJarURLConnection;

import javax.swing.tree.TreeNode;
import java.math.BigDecimal;
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
import java.util.logging.Level;

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
                // 当已经发现过左右两个节点不是双全 之后遇到的每个节点都应该是叶子节点
                    (flag && (l != null || r != null))
                            ||
                            (l == null && r != null)
            )
                return false;
            if (l != null)
                queue.add(l);
            if (r != null)
                queue.add(r);
            if (r == null)
                flag = true;
        }
        return true;
    }

    /**
     * 找一个二叉树的最大值
     */
    public static int findMax(Node node) {
        if (node == null)
            return Integer.MIN_VALUE;

        int lMax = findMax(node.left);
        int rMax = findMax(node.right);
        return Math.max(Math.max(lMax, rMax), node.value);
    }

    /**
     * 求一棵二叉树的树高
     */
    public static int getHeight(Node node) {
        if (node == null)
            return 0;

        int l = getHeight(node.left);
        int r = getHeight(node.right);
        return Math.max(l, r) + 1;
    }

    /**
     * 解二叉树的递归套路-----可以解决一切树型DP（动态规划）问题
     * 列出所有的限制条件
     * 分析左树需要提供什么信息，右数需要提供什么信息
     * 只要一个问题可以被分解成从左树收集一些信息，右树收集一些信息，然后将这两个信息汇总后做某些处理这个问题可以解决的话，就可以使用这个套路对二叉树问题求解
     * 注意：并不是所有的问题都可以通过这个套路解决
     *      例如：求一个二叉树所有节点数字的中位数
     *          得到左树的中位数和右数的中位数是没有意义的，因为中位数是需要做全局的考量
     */

    /**
     * （1）判断一棵树是否是平衡二叉树，平衡二叉树指的是左树与有树的高度差不超过1
     * 限制条件：1.左树需要是平衡二叉树
     * 2.右树需要是平衡二叉树
     * 3.|左树高-右树高| <= 1
     * 分析左右树需要提供的信息：
     * 左树：1.是否是平衡二叉树 2.树高
     * 右树：1.是否是平衡二叉树 2.树高
     */
    // 对左右子树需要提供的信息进行封装
    static class ReturnType {
        public boolean isBalanced;
        public int height;

        public ReturnType(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static ReturnType isBalanced(Node node) {
        if (node == null)
            return new ReturnType(true, 0);

        ReturnType leftData = isBalanced(node.left);    // 左树提供的信息
        ReturnType rightData = isBalanced(node.right);  // 右树提供的信息

        int height = Math.max(leftData.height, rightData.height) + 1;   // 当前节点需要提供的信息（高）
        boolean isBalanced = leftData.isBalanced && rightData.isBalanced && Math.abs(leftData.height - rightData.height) <= 1;  // 当前节点需要提供的信息（是否平衡）
        return new ReturnType(isBalanced, height);
    }

    /**
     * （2）判断一棵树是否是搜索二叉树，搜索二叉树指的是，对于任意节点x，左树的任意节点小于x，右树的任意节点大于x
     * 限制条件：
     * 1.左树是搜索二叉树
     * 2.右树是搜索二叉树
     * 分析左右需要提供的信息：
     * 1.左树提供最大值、是否是搜索二叉树
     * 2.右树提供最小值，是否是搜索二叉树
     * 但由于是递归，需要对每个节点一视同仁，因为一个节点既可以是某棵树的左节点，也可以是另一棵树的右节点
     * 因此我们返回（是否是搜索二叉树、最大值、最小值）
     */
    static class ReturnType2 {
        public boolean isResearch;
        public long max;
        public long min;

        public ReturnType2(boolean isResearch, long max, long min) {
            this.isResearch = isResearch;
            this.max = max;
            this.min = min;
        }

        @Override
        public String toString() {
            return "ReturnType2{" +
                    "isResearch=" + isResearch +
                    ", max=" + max +
                    ", min=" + min +
                    '}';
        }
    }

    public static ReturnType2 isResearchTree(Node node) {
        if (node == null)
            return new ReturnType2(true, Long.MIN_VALUE, Long.MAX_VALUE);

        ReturnType2 leftData = isResearchTree(node.left);
        ReturnType2 rightData = isResearchTree(node.right);
        boolean isResearch = leftData.isResearch && rightData.isResearch && leftData.max < node.value && rightData.min > node.value;
        long max = Math.max(Math.max(leftData.max, rightData.max), node.value);
        long min = Math.min(Math.min(leftData.min, rightData.min), node.value);

        return new ReturnType2(isResearch, max, min);
    }

    /**
     * （3）判断一棵树是否是满二叉树
     * 限制条件：
     * * 1.左树是满二叉树
     * * 2.右树是满二叉树
     * 分析：
     * 1.高度
     * 2.个数
     * 3.是否是
     */
    static class ReturnType3 {
        public int height;
        public int nodes;
        public boolean is;

        public ReturnType3(boolean is, int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
            this.is = is;
        }

        @Override
        public String toString() {
            return "ReturnType3{" +
                    "height=" + height +
                    ", nodes=" + nodes +
                    ", is=" + is +
                    '}';
        }
    }

    public static ReturnType3 isFullTree(Node node) {
        if (node == null)
            return new ReturnType3(true, 0, 0);
        ReturnType3 leftData = isFullTree(node.left);
        ReturnType3 rightData = isFullTree(node.right);
        int height = (Math.max(leftData.height, rightData.height) + 1);
        int nodes = (leftData.nodes + rightData.nodes + 1);
        boolean is = leftData.is && rightData.is && Math.pow(2, height) - 1 == nodes;
        return new ReturnType3(is, height, nodes);
    }

    /**
     * （3）判断二叉树中两个节点的最低公共祖先（最低公共祖先指的是最早汇聚的点，根节点是所有节点的公共祖先） 
     * 限制条件：
     *      * 1.左树是满二叉树
     *      * 2.右树是满二叉树
     * 分析：
     *      1.高度
     *      2.个数
     *      3.是否是
     */
    /**
     * 最低公共祖先
     * <p>
     * 解法A：处理为父链路求相交问题
     *
     * @param head
     * @param o1   必属于head为头的树
     * @param o2   必属于head为头的树
     * @return
     */
    public static Node findLowestCommonAncestor(Node head, Node o1, Node o2) {
        if (o1 == o2)
            return o1;
        Map<Node, Node> fatherMap = new HashMap<>();
        fatherMap.put(head, head);
        fatherMapProcess(head, fatherMap);
        Set<Node> set1 = new HashSet<>();
        while (o1 != head) {
            set1.add(o1);
            o1 = fatherMap.get(o1);
        }
        set1.add(head);
        while (!set1.contains(o2)) {
            o2 = fatherMap.get(o2);
        }
        return o2;
    }

    public static void fatherMapProcess(Node node, Map<Node, Node> fatherMap) {
        if (node == null)
            return;
        if (node.left != null)
            fatherMap.put(node.left, node);
        if (node.right != null)
            fatherMap.put(node.right, node);
        fatherMapProcess(node.left, fatherMap);
        fatherMapProcess(node.right, fatherMap);
    }

    /**
     * 最低公共祖先
     * <p>
     * 解法B：分析可能性问题 有且只有两种可能
     * 1）o1是o2的最低公共祖先，或者o2是o1的最低公共祖先
     * 2）o1,o2不互为最低公共祖先，一同汇聚至第三点作为最低公共祖先
     *
     * @param o1 必属于head为头的树
     * @param o2 必属于head为头的树
     * @return
     */
    public static Node findLowestCommonAncestor2(Node head, Node o1, Node o2) {
        if (head == null || head == o1 || head == o2)
            return head;
        Node l = findLowestCommonAncestor2(head.left, o1, o2);
        Node r = findLowestCommonAncestor2(head.right, o1, o2);

        if (l != null && r != null)
            return head;
        return l != null ? l : r;
    }

    /**
     * 现定义一种特殊的二叉树结构，该结构除了基础的二叉树结构以外，增加一个父节点属性，每个节点都有其父节点且父节点指向都是正确的
     */
    static class PNode {
        int value;
        PNode left;
        PNode right;
        PNode parent;

        public PNode(int value) {
            this.value = value;
        }

        public PNode() {
        }

        @Override
        public String toString() {
            return "PNode{" +
                    "value=" + value +
                    '}';
        }
    }

    /**
     * 给定一颗节点包含父节点信息的二叉树，求指定节点的后继节点
     * （后继节点：对二叉树中序遍历结果为abc的话，a的后继节点就是b，b的后继就时c，c的后继就是null）
     */
    /**
     * 解1：使用中序遍历即可，但时间复杂度为O（n）
     *
     * @param head
     * @param node
     * @return
     */
    public static PNode findAfterNode(PNode head, PNode node) {
        Queue<PNode> queue = new LinkedList<>();
        inOrder(head, queue);
        while (!queue.isEmpty()) {
            if (queue.poll() == node) {
                break;
            }
        }
        return queue.poll();
    }

    public static void inOrder(PNode head, Queue<PNode> queue) {
        if (head == null)
            return;
        inOrder(head.left, queue);
        queue.add(head);
        inOrder(head.right, queue);
    }

    /**
     * 若所求节点与其后继节点的链路长度为K，希望时间复杂度为O(K)而非与二叉树总规模n有关
     * 解2：对于任何节点共有两种情况
     * 1. 有右树，右树中的最左节点就是后继节点
     * 2. 无右树，找父节点，如果是父节点的左孩子，返回父节点
     */
    public static PNode findAfterNode2(PNode head, PNode node) {
        if (node == null)
            return null;
        if (node.right != null) { // 有右树，找右树的最左侧节点
            node = node.right;
            for (; ; ) {
                if (node.left == null)
                    return node;
                node = node.left;
            }
        }

        PNode parent = node.parent;
        while (parent != null) {
            if (parent.left == node) {
                break;
            }
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    /**
     * 二叉树的序列化和反序列化
     *      序列化：将内存中的二叉树变成字符串（要求可以通过该字符串还原为原内存中的二叉树结构）
     *      反序列化：将字符串变成二叉树
     */
    public static String serialize(Node root) {
        StringBuilder sb = new StringBuilder();
        // 非递归先序遍历
        Stack<Node> stack = new Stack<>(); 
        stack.push(root);
        
        while (!stack.isEmpty()){
            Node pop = stack.pop();
            sb.append(pop != null ? pop.value : "#");
            if (pop != null){
                stack.push(pop.right);
                stack.push(pop.left);
            }
        }
        return sb.toString();
    }

    public static String serialize2(Node root) {
        if (root == null)
            return "#";
        String res = root.value+"";
        res += serialize2(root.left);
        res += serialize2(root.right);
        return res;
    }

    // Decodes your encoded data to tree.
    public static Node deserialize2(String data) {
        Queue<String> queue = new LinkedList<>();
        for (char c : data.toCharArray()) {
            queue.add(c+"");
        }
        return processSerializeString(queue);
    }
    
    public static Node processSerializeString(Queue<String> queue){
        String s = queue.poll();
        if ("#".equals(s))
            return null;
        Node node = new Node(Integer.parseInt(s));
        node.left = processSerializeString(queue);  // 构建左树
        node.right = processSerializeString(queue); // 构建右树
        return node;
    }

    /**
     * 微软折纸问题
     * 对于一张长纸条，我们将一侧面对自己，然后进行对着，可以发现第一条折痕是凹的，然后第二次对折，折痕为凹凸凹，第三次对折为凹凹凸凹凹凸凸
     * 现要求根据折叠次数输出折痕由上至下的顺序
     */
    public static void origami(int count){
        // 凹 1 凸 -1
        Node head = new Node(1);
        origamiProcess(head,1,count);
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            if (head.left != null) {
                stack.push(head.left);
                head = head.left;
                continue;
            }
            head = stack.pop();
            System.out.println(head.value == 1 ? "凹" : "凸");
            if (head.right != null) {
                stack.push(head.right);
                head = head.right;
            }
        }
    }
    public static Node origamiProcess(Node node,int x,int count){
        if (x>=count)
            return null;
        node.left = new Node(1);
        node.right = new Node(-1);
        origamiProcess(node.left,x+1,count);
        origamiProcess(node.right,x+1,count);
        return node;
    }

    public static void origami2(int count){
        origamiProcess2(1,count,true);
    }

    public static void origamiProcess2(int i,int count,boolean down){
       if (i>count)
           return;
       origamiProcess2(i+1,count,true);
       System.out.println(down ? "凹" : "凸");
       origamiProcess2(i+1,count,false);
    }


    public static void main(String[] args) {
        origami2(3);
        System.out.println("===========");
        origami(3);
        BigDecimal bigDecimal = new BigDecimal("0.0179");
        System.out.println(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println(bigDecimal);
        Stack<Node> stack = new Stack<>();
        stack.push(null);
        stack.push(new Node(1));
        stack.push(null);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

        // 构建一棵带父节点的PNode树
        PNode pn1 = new PNode(1);
        PNode pn2 = new PNode(2);
        PNode pn3 = new PNode(3);
        PNode pn4 = new PNode(4);
        PNode pn5 = new PNode(5);
        PNode pn6 = new PNode(6);
        PNode pn7 = new PNode(7);
        pn1.left = pn2;
        pn1.right = pn3;
        pn2.left = pn4;
        pn2.right = pn5;
        pn3.left = pn6;
        pn3.right = pn7;

        pn4.parent = pn2;
        pn5.parent = pn2;
        pn6.parent = pn3;
        pn7.parent = pn3;
        pn2.parent = pn1;
        pn3.parent = pn1;
        System.out.println(findAfterNode(pn1, pn1));
        System.out.println(findAfterNode2(pn1, pn1));

        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        n1.left = n2;
        n1.right = n3;
        System.out.println(serialize2(n1));
        Node node2 = deserialize2(serialize2(n1));
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;
        System.out.println(serialize(n1));

        Node node = deserialize2(serialize2(n1));
        System.out.println(findLowestCommonAncestor(n1, n6, n7));
        System.out.println(findLowestCommonAncestor2(n1, n6, n7));
        System.out.println(isFullTree(n1));
        System.out.println(getHeight(n1));
        System.out.println(findMax(n1));
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
        Node x5 = new Node(99);
        Node x3 = new Node(3);
        Node x7 = new Node(7);
        Node x1 = new Node(10);
        Node x4 = new Node(4);
        Node x6 = new Node(6);
        Node x9 = new Node(9);
        x5.left = x3;
        x5.right = x7;
        x3.left = x1;
        x3.right = x4;
        x7.left = x6;
        x7.right = x9;
        
        // System.out.println(isSearchNode2(x5));
        // System.out.println(isResearchTree(x5));
        // System.out.println(isCompleteBinaryTree(x5));
        // System.out.println(isBalanced(x5).isBalanced);
        // System.out.println("==========================");

        Node test = new Node(1);
        System.out.println("=======" + isResearchTree(test));

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

    // @Override
    // public String toString() {
    //     return "Node{" +
    //             "value=" + value +
    //             ", left=" + left.value +
    //             ", right=" + right.value +
    //             '}';
    // }
}
