package com.zjh;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class7_Greedy class
 * 贪心算法
 *
 * @author zjh
 * @date 2022/8/16 14:04
 */
public class Class7_Greedy {
    public static void main(String[] args) {
        String[] arr = new String[]{"abc", "zde", "gf", "gh"};
        System.out.println(minDictionaryOrder(arr));

        Queue<Integer> testPeek = new LinkedList<>();
        testPeek.add(3);
        testPeek.add(2);
        testPeek.add(1);
        System.out.println(testPeek.peek());
        System.out.println(testPeek.peek());
        System.out.println(testPeek.peek());
        System.out.println("==================");
        Project[] projects = new Project[]{new Project(1, 1), new Project(1, 4), new Project(1000, 3000), new Project(2, 3), new Project(2, 7), new Project(3, 2), new Project(4, 10)};
        System.out.println(maxProfit(projects, 1, 100));

        System.out.println(quickMid(new int[]{1, 2, 3}));
    }

    /**
     * 题1：给定一个字符串数组，请将其所含的所有字符串按任意顺序拼接，要求最终得到的字符串字典序是所有可能的拼接中最小的（字典序：一个字符串在字典中的顺序，例如apple字典序小于zoo，abandon小于apple）
     * 脑洞1：先对数组中的所有字符串按照字典序进行排序，然后按照排完的顺序直接拼接   -------这种思路看似是对的，但是可以举出反例，对于["b","ba"]，按上述思路最终得到bba，而bab显然是小于bba的
     * <p>
     * 解：
     * 定义比较器，若a+b <= B+a 则把a放在b的前面String的compareTo方法就是按照字典序进行返回的
     * 证明：
     * 1. 证明定义的比较器是具有传递性（传递性指的是 a<b,b<c ==> a<c）
     * 2. 证明得到的排完顺序的数组顺序是最优解，只要交换任意位置的数据，得到的最终结果都比交换前大
     */
    public static String minDictionaryOrder(String[] arr) {
        Arrays.sort(arr, String::compareTo);

        StringBuilder result = new StringBuilder();
        for (String s : arr) {
            result.append(s);
        }
        return result.toString();
    }

    /**
     * 题2：一块金条切成两半， 是需要花费和长度数值一样的铜板的。 比如长度为20的 金条， 不管切成长度多大的两半， 都要花费20个铜板。 一群人想整分整块金 条， 怎么分最省铜板？
     * <p>
     * 示例：
     * 例如,给定数组{10,20,30}， 代表一共三个人， 整块金条长度为10+20+30=60. 金条要分成10,20,30三个部分。
     * <p>
     * 如果， 先把长度60的金条分成10和50， 花费60 再把长度50的金条分成20和30，花费50 一共花费110铜板。
     * 但是如果， 先把长度60的金条分成30和30， 花费60 再把长度30金条分成10和20， 花费30 一共花费90铜板。输入一个数组， 返回分割的最小代价。
     */
    public static int lessMoney(int[] arr) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i : arr) {
            queue.add(i);
        }
        int res = 0;
        int cur;
        while (queue.size() > 1) {
            cur = queue.poll() + queue.poll();
            queue.add(cur);
            res += cur;
        }
        return res;
    }

    /**
     * 题3：假如你是一个创业者，有初始资金为M，一共最多做K个项目，一次只能做一个项目[本金，利润]，且项目不可重复做，请求出条件下的累计的最大资产
     */
    public static int maxProfit(Project[] projects, int m, int k) {
        int res = m;
        PriorityQueue<Project> minQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        PriorityQueue<Project> maxQueue = new PriorityQueue<>((o1, o2) -> o2.profit - o1.profit);
        Collections.addAll(minQueue, projects);
        for (int i = 0; i < k; i++) {
            getCanPayProject(minQueue, maxQueue, res);
            if (maxQueue.isEmpty())
                return res;
            res += maxQueue.poll().profit;
        }
        return res;
    }

    public static void getCanPayProject(PriorityQueue<Project> minQueue, PriorityQueue<Project> maxQueue, int m) {
        while (!minQueue.isEmpty() && minQueue.peek().cost <= m) {
            maxQueue.add(minQueue.poll());
        }
    }

    // 暴力算法对数器
    // public static int maxProfitViolence(Project[] projects,int m,int k){
    //     int res = m;
    //     projects
    // }

    static class Project {
        int cost;   // 成本

        int profit; // 利润

        public Project(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    /**
     * 题4：快速得出中位数
     * <p>
     * 利用大根堆和小跟堆，对于数组中小于等于中位数的放在大根堆，大于等于中位数的放在小跟堆
     * 让数据类似于在中位数处拐弯了
     * maxQueue | minQueue
     * 2      |    3
     * 1      |    4
     * |    5
     * 可以做到任何数据量算法的时间复杂度都是O(log2^N)
     */
    public static Double quickMid(int[] arr) {
        if (arr.length < 2) {
            return (double) arr[0];
        }
        PriorityQueue<Integer> minQueue = new PriorityQueue<>();
        PriorityQueue<Integer> maxQueue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        maxQueue.add(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] <= maxQueue.peek()) {
                maxQueue.add(arr[i]);
            } else {
                minQueue.add(arr[i]);
            }
            if (Math.abs(maxQueue.size() - minQueue.size()) > 1) {
                if (maxQueue.size() > minQueue.size()) {
                    minQueue.add(maxQueue.poll());
                } else {
                    maxQueue.add(minQueue.poll());
                }
            }
        }
        return arr.length % 2 == 0 ? (minQueue.peek() + maxQueue.peek()) / 2.0 : Double.valueOf(minQueue.peek());
    }

    /**
     * N皇后问题
     * N皇后问题是指在N*N的棋盘上要摆N个皇后，要求任何两个皇后不同行、不同列，也不在同一条斜线上。
     * 给定一个整数n,返回n皇后的摆法有多少种。
     * n=1,返回1
     * n=2或3,2皇后和3皇后问题无论怎么摆都不行，返回0.
     * n=8,返回92。
     */
    public static int nQueen(int n) {
        // 记录摆放好的皇后的位置 record[0]=1表示在(0,1)出放置了皇后
        int[] record = new int[n];
        return processQueen(record, 0, n);
    }

    public static int processQueen(int[] record, int i, int n) {
        if (i == n) {    // 全部行都合法的放置了皇后
            return 1;
        }
        int res = 0;
        for (int j = 0; j < n; j++) { // 当前在第i行，尝试所有列
            if (queenValid(record, i, j)) {
                record[i] = j;
                res += processQueen(record, i + 1, n);
            }
        }
        return res;
    }

    /**
     * 判断在x,y位置放置皇后是否合法
     *
     * @param record
     * @param i
     * @param j
     * @return
     */
    public static boolean queenValid(int[] record, int i, int j) {
        for(int k = 0;k<i;k++){
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)){
                return false;
            }
        }
        return true;
    }
}
