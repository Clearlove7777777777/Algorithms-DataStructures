package com.zjh.base;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class6_Graph class
 * 图
 *
 * @author zjh
 * @date 2022/8/11 9:56
 */
public class Class6_Graph {
    /**
     * A———2———B
     * |   1/ |
     * 3| /    |5
     * C      D
     * 表示图的方法
     * 1. 邻接表法
     * A:B2 C3
     * B:A2 C1 D5
     * C:A3 B1
     * D: B5
     * 2. 邻接矩阵
     * A  B  C  D
     * A   0  2  3 无穷
     * B   2  0  1  5
     * C   3  1  0 无穷
     * D 无穷 5 无穷 0
     * <p>
     * 在实际的算法题中，图的题表可能会非常独特，我们需要做的是将题目中的图结构转换为我们熟悉的结构，然后在进行处理
     * [
     * [from,to,weight],[from,to,weight],[from,to,weight]
     * ]
     */
    public static void main(String[] args) {
        int[][] initial = new int[][]{new int[]{4, 1, 1}, new int[]{4, 2, 1}, new int[]{2, 1, 1}, new int[]{5, 2, 1}, new int[]{3, 1, 1}, new int[]{4, 5, 1}, new int[]{5, 4, 1}, new int[]{1, 2, 1}, new int[]{1, 3, 1}, new int[]{1, 4, 1}, new int[]{2, 4, 1}, new int[]{2, 5, 1}};
        Graph graph = createGraph(initial);
        bfs(graph.nodes.get(4));
        System.out.println("=====================");
        dfs(graph.nodes.get(1));
        // 构建拓扑图
        int[][] pology = new int[][]{new int[]{1, 2, 1}, new int[]{2, 3, 1}, new int[]{3, 5, 1}, new int[]{2, 4, 1}, new int[]{4, 5, 1}};
        Graph graph1 = createGraph(pology);
        System.out.println(sortedToPology(graph1));
        // 构建最小生成树所用图
        int[][] mst = new int[][]{new int[]{1, 2, 5}, new int[]{2, 1, 5}, new int[]{2, 3, 4}, new int[]{3, 2, 4}, new int[]{3, 4, 7}, new int[]{4, 3, 7}, new int[]{1, 3, 1000}, new int[]{3, 1, 1000}, new int[]{2, 4, 1}, new int[]{4, 2, 1}, new int[]{1, 4, 100}, new int[]{4, 1, 100}};
        Graph graph2 = createGraph(mst);
        System.out.println();
        System.out.println(kmst(graph2));
        System.out.println(pmst(graph2));

        // dijkstra
        int[][] dijkstra = new int[][]{new int[]{1, 2, 5}, new int[]{2, 5, 3}, new int[]{5, 3, 6}, new int[]{3, 4, 100}, new int[]{4, 1, 4}, new int[]{2, 6, 1}, new int[]{6, 4, 6}, new int[]{6, 1, 2}, new int[]{6, 3, 3}, new int[]{2, 3, 7},
                new int[]{2, 1, 5}, new int[]{5, 2, 3}, new int[]{3, 5, 6}, new int[]{4, 3, 100}, new int[]{1, 4, 4}, new int[]{6, 2, 1}, new int[]{4, 6, 6}, new int[]{1, 6, 2}, new int[]{3, 6, 3}, new int[]{3, 2, 7}
        };
        Graph dijkstrag = createGraph(dijkstra);
        GraphNode root = null;
        for (GraphNode node : dijkstrag.nodes.values()) {
            if (node.value == 1) {
                root = node;
                break;
            }
        }
        Map<GraphNode, DijkstraHelp> dijkstra1 = dijkstra(dijkstrag, root);
    }

    /**
     * 根据二维数组创建图
     * [
     * [from,to,weight],[from,to,weight],[from,to,weight]
     * ]
     *
     * @param matrix
     * @return
     */
    public static Graph createGraph(int[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            int from = matrix[i][0];
            int to = matrix[i][1];
            int weight = matrix[i][2];
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new GraphNode(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new GraphNode(to));
            }
            GraphNode fromNode = graph.nodes.get(from);
            GraphNode toNode = graph.nodes.get(to);
            Edge edge = new Edge(weight, fromNode, toNode);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            fromNode.edges.add(edge);
            graph.edges.add(edge);
        }
        return graph;
    }

    /**
     * 宽度优先遍历
     */
    public static void bfs(GraphNode node) {
        if (node == null)
            return;
        Queue<GraphNode> queue = new LinkedList<>();
        Set<GraphNode> set = new HashSet<>(); // 防止死循环
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()) {
            GraphNode graphNode = queue.poll();
            System.out.println(graphNode.value);
            for (GraphNode next : graphNode.nexts) {
                if (!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

    /**
     * 深度优先遍历
     */
    public static void dfs(GraphNode node) {
        if (node == null)
            return;
        Stack<GraphNode> stack = new Stack<>();
        Set<GraphNode> set = new HashSet<>(); // 防止死循环
        stack.push(node);
        set.add(node);
        System.out.println(node.value);
        while (!stack.isEmpty()) {
            GraphNode cur = stack.pop();
            for (GraphNode next : cur.nexts) {
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    break;  // 一条没走过的路 直接走到黑
                }
            }
        }
    }

    /**
     * 拓扑排序
     * 类似于maven依赖，给出依赖项的依赖关系，求出依赖项的编译顺序
     *
     * @param graph
     * @return
     */
    public static List<GraphNode> sortedToPology(Graph graph) {
        List<GraphNode> res = new ArrayList<>();
        // key:图节点 value:该图节点的入度
        Map<GraphNode, Integer> nodeInMap = new HashMap<>();
        // 只有入度为0的节点才可以进下面的队列
        Queue<GraphNode> queue = new LinkedList<>();
        for (GraphNode node : graph.nodes.values()) {
            nodeInMap.put(node, node.in);
            if (node.in == 0) {
                queue.add(node);
            }
        }
        while (!queue.isEmpty()) {
            GraphNode graphNode = queue.poll();
            res.add(graphNode);
            // 弹出一个节点就把因为它产生的in-1
            for (GraphNode next : graphNode.nexts) {
                nodeInMap.put(next, nodeInMap.get(next) - 1);
                if (nodeInMap.get(next) == 0)
                    queue.add(next);
            }
        }
        return res;
    }

    /**
     * 最小生成树
     * 给定一个带权值的图结构，求可以连通各个节点并且权值和最小的图结构（可以想象成，想要连通各个城市，每个城市之间修路的成本是不一样的，求连通各个城市且修路成本最低的方案）
     */
    /**
     * K算法：对边按照权值从小到大进行遍历，每加入一条边判断是否构成环，若构成则舍弃，不构成则选用
     * 并查集
     */
    public static List<Edge> kmst(Graph graph) {
        List<Edge> res = new ArrayList<>();
        List<List<GraphNode>> list = new ArrayList<>();
        for (GraphNode node : graph.nodes.values()) {
            List<GraphNode> sonList = new ArrayList<>();
            sonList.add(node);
            list.add(sonList);
        }
        List<Edge> edgeList = graph.edges.stream().sorted(Comparator.comparingInt(o -> o.weight)).collect(Collectors.toList());
        for (Edge edge : edgeList) {
            boolean flag = true;
            // 记录只含from节点的集合 用于合并
            List<GraphNode> fromList = new ArrayList<>();
            // 记录只含to节点的集合 用于合并
            List<GraphNode> toList = new ArrayList<>();
            Iterator<List<GraphNode>> iterator = list.iterator();
            while (iterator.hasNext()) {
                List<GraphNode> graphNodeList = iterator.next();
                boolean containsFrom = graphNodeList.contains(edge.from);
                boolean containsTo = graphNodeList.contains(edge.to);
                if (containsFrom && containsTo) {
                    flag = false;
                    break;
                }
                if (containsFrom) {
                    fromList = graphNodeList;
                    iterator.remove();
                }
                if (containsTo) {
                    toList = graphNodeList;
                    iterator.remove();
                }
            }
            if (flag) {
                res.add(edge);
                List<GraphNode> concatList = Stream.concat(toList.stream(), fromList.stream()).collect(Collectors.toList());
                list.add(concatList);
            }

        }
        return res;
    }

    public static List<Edge> kmst2(Graph graph) {
        List<Edge> res = new ArrayList<>();
        Map<GraphNode, Integer> map = new HashMap<>();
        int i = 0;
        for (GraphNode node : graph.nodes.values()) {
            map.put(node, i);
            i++;
        }
        // 以下思路是不可行的
        List<Edge> edgeList = graph.edges.stream().sorted(Comparator.comparingInt(o -> o.weight)).collect(Collectors.toList());
        for (Edge edge : edgeList) {
            if (map.get(edge.from) != map.get(edge.to)) {
                Integer merge = map.get(edge.from) < map.get(edge.to) ? map.get(edge.from) : map.get(edge.to);
                map.put(edge.from, merge);
                map.put(edge.to, merge);
                res.add(edge);
            }
        }
        return res;
    }

    /**
     * p算法求最小生成树
     * <p>
     * 任意取一点，得到其相邻的所有边，选择最小权值边的连通点，判断其是否已被连通过，若没有则选用该边
     *
     * @param graph
     * @return
     */
    public static Set<Edge> pmst(Graph graph) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.weight));
        Set<Edge> res = new HashSet<>();
        Set<GraphNode> set = new HashSet<>();   // 到过的点

        // 随便取一个点   整体的for循环是为了解决若图结构不是整体联通的情况（森林结构），如果案例是整体连通的普通图结构，肯定是任意取一点一次性在底下的while里跑完了不需要这个for
        for (GraphNode node : graph.nodes.values()) {
            if (!set.contains(node)) {
                set.add(node);
                queue.addAll(node.edges);
                while (!queue.isEmpty()) {
                    Edge edge = queue.poll();   // 弹出的边是队列中最小的（由优先级队列保证）
                    GraphNode toNode = edge.to; // 可能的一个新的点
                    if (!set.contains(toNode)) {
                        set.add(toNode);
                        res.add(edge);
                        queue.addAll(toNode.edges);
                    }
                }
            }
        }
        return res;
    }

    /**
     * 迪杰斯特拉算法(Dijkstra)是由荷兰计算机科学家狄克斯特拉于1959年提出的，因此又叫狄克斯特拉算法。 是从一个顶点到其余各顶点的最短路径算法，解决的是有权图中最短路径问题
     * 要求没有负数权值边（更准确的说应该是可以有权值为负数的边，但不能有累加和为负数的环，因为如果有为负数的环，原始点到任意点都可以做到负无穷，一直绕圈刷就行了）
     *
     * @param graph
     * @return
     */
    public static Map<GraphNode, DijkstraHelp> dijkstra(Graph graph, GraphNode root) {
        // 记录距离
        Map<GraphNode, DijkstraHelp> map = new HashMap<>();
        for (GraphNode graphNode : graph.nodes.values()) {
            map.put(graphNode, new DijkstraHelp(graphNode, Integer.MAX_VALUE));
        }
        DijkstraHelp rootHelp = new DijkstraHelp(root, 0);
        map.put(root, rootHelp);
        // for (Edge edge : root.edges) {
        //     map.put(edge.to, new DijkstraHelp(edge.to,edge.weight));
        // }
        PriorityQueue<DijkstraHelp> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.length));
        // 只要还有点没有被使用过
        while (map.values().stream().anyMatch(dijkstraHelp -> !dijkstraHelp.used)) {
            for (Map.Entry<GraphNode, DijkstraHelp> entry : map.entrySet()) {
                if (!entry.getValue().used) {
                    queue.add(entry.getValue());
                }
            }
            DijkstraHelp dijkstraHelp = queue.poll();   // 当前选用的最小的 
            dijkstraHelp.used = true;
            for (Edge edge : dijkstraHelp.graphNode.edges) {
                if (map.get(edge.to).length > (dijkstraHelp.length + edge.weight)) {
                    map.get(edge.to).length = dijkstraHelp.length + edge.weight;
                    map.put(edge.to, map.get(edge.to));
                }
            }
            queue.clear();
        }
        return map;
    }

    static class DijkstraHelp {
        GraphNode graphNode;
        // root节点到当前节点的距离
        Integer length;
        Boolean used = false;

        public DijkstraHelp(GraphNode graphNode, Integer length) {
            this.graphNode = graphNode;
            this.length = length;
        }
    }

    // 对上述的Dijkstra算法进行改进，上面算法中使用了堆结构进行选取最小值，每次都要clear，是很不划算的，否则就是采用遍历得到最小值
    // 因此我们可以自行设计一种堆结构，记录录入的数据，并且可以对录入的数据进行修改，同时在较低的代价下调整堆结构，始终保持小跟堆的特性
    static class NodeHeap {
        private GraphNode[] nodes;
        private HashMap<GraphNode, Integer> heapIndexMap;    // 节点：节点在数组中的下标     value=-1表示该节点进来过但是已经出去了
        private HashMap<GraphNode, Integer> distanceMap;     // 节点：节点与head的距离
        private int size;

        public NodeHeap(int size) {
            this.nodes = new GraphNode[size];
            this.distanceMap = new HashMap<>();
            this.heapIndexMap = new HashMap<>();
            this.size = 0;
        }

        private boolean isEntered(GraphNode node) {
            return heapIndexMap.containsKey(node);
        }

        private boolean inHeap(GraphNode node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            GraphNode temp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = temp;
        }
    }


}

// 图
class Graph {
    HashMap<Integer, GraphNode> nodes; // 点集
    HashSet<Edge> edges; // 边集

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}

// 图节点
class GraphNode {
    int value;
    int in; // 入度
    int out; // 出度
    
    ArrayList<GraphNode> nexts; // 从当前节点直接发散出去的点
    ArrayList<Edge> edges; // 属于当前节点的边

    public GraphNode(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "value=" + value +
                '}';
    }
}

// 边
class Edge {
    int weight; // 长度、权重
    GraphNode from; // 起点节点
    GraphNode to; // 终点节点

    public Edge(int weight, GraphNode from, GraphNode to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                '}';
    }
}

