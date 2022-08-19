package com.zjh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class8 class
 * 补充
 *
 * @author zjh
 * @date 2022/8/18 14:11
 */
public class Class8 {
    public static void main(String[] args) {
        hannuo(3);
        System.out.println(count);

        printSubsequence("abc");
        System.out.println("===================");
        // System.out.println(countSubsequence("pcrdhwdxmqdznbenhwjsenjhvulyve"));
        printSubsequence2("abc");

    }

    /**
     * 汉诺塔问题
     * <p>
     * 由汉诺塔问题思考：递归是反人类思想的，递归不能通过全局的角度去看，只需要关注子问题，保证子问题的细节处理时正确的，那么整体一定是正确的
     *
     * @param n
     */
    public static void hannuo(int n) {
        if (n > 0) {
            hannuoProc(n, "左", "右", "中");
        }
    }

    public static int count = 0;

    private static void hannuoProc(int i, String start, String end, String other) {
        if (i == 1) {
            System.out.println("将[1]从[" + start + "]移至[" + end + "]");
            count++;
            return;
        }
        hannuoProc(i - 1, start, other, end);
        System.out.println("将[" + i + "]从[" + start + "]移至[" + end + "]");
        count++;
        hannuoProc(i - 1, other, end, start);
    }

    /**
     * 打印字符串的所有子序列
     * <p>
     * 例如：abc -> “” a b c ab ac bc abc
     *
     * @param s
     */
    public static void printSubsequence(String s) {
        char[] chars = s.toCharArray();
        subsequenceProcess(chars, 0, new ArrayList<Character>());
    }

    /**
     * @param chars 所有字符
     * @param i     当前来到的位置
     * @param res   之前选择的字符
     */
    private static void subsequenceProcess(char[] chars, int i, List<Character> res) {
        if (i == chars.length) {
            String string = "";
            for (Character c : res) {
                string += c;
            }
            System.out.println("".equals(string) ? "\"\"" : string);
            return;
        }
        List<Character> choseList = new ArrayList<>(res);
        List<Character> unChoseList = new ArrayList<>(res);
        choseList.add(chars[i]);
        subsequenceProcess(chars, i + 1, choseList);
        subsequenceProcess(chars, i + 1, unChoseList);
    }

    /**
     * 打印字符串的所有子序列
     * <p>
     * 例如：abc -> “” a b c ab ac bc abc
     *
     * @param s
     */
    public static void printSubsequence2(String s) {
        char[] chars = s.toCharArray();
        subsequenceProcess2(chars, 0);
    }

    /**
     * @param chars 所有字符
     * @param i     当前来到的位置
     */
    private static void subsequenceProcess2(char[] chars, int i) {
        if (i == chars.length) {
            System.out.println(String.valueOf(chars));
            return;
        }
        subsequenceProcess2(chars, i + 1); // 要当前字符的路
        char temp = chars[i];
        chars[i] = ' ';
        subsequenceProcess2(chars, i + 1); // 不要当前字符的路
        System.out.println("====" + Arrays.toString(chars));
        chars[i] = temp;
        System.out.println(Arrays.toString(chars) + "====");

        System.out.println("--------------------");
        System.out.println(printAllSonString("aba"));

    }

    /**
     * 计算字符串的所有非空子序列个数
     * <p>
     * 例如：abc -> “” a b c ab ac bc abc
     *
     * @param s
     */
    public static int countSubsequence(String s) {
        char[] chars = s.toCharArray();
        Set<String> set = new HashSet<>();
        return countSubsequenceProcess(chars, 0, new ArrayList<>(), 0, set);
    }

    /**
     * @param chars 所有字符
     * @param i     当前来到的位置
     * @param res   之前选择的字符
     */
    private static int countSubsequenceProcess(char[] chars, int i, List<Character> res, int count, Set<String> set) {
        if (i == chars.length) {
            if (res.size() == 0) {
                return 0;
            }
            String s = "";
            for (Character character : res) {
                s += character;
            }
            if (!set.contains(s)) {
                set.add(s);
                return 1;
            }
            return 0;
        }
        List<Character> choseList = new ArrayList<>(res);
        List<Character> unChoseList = new ArrayList<>(res);
        choseList.add(chars[i]);
        int x = countSubsequenceProcess(chars, i + 1, choseList, count, set);
        int y = countSubsequenceProcess(chars, i + 1, unChoseList, count, set);
        return x + y;
    }

    /**
     * 输出字符串的所有子串
     *
     * @param s
     */
    public static List<String> printAllSonString(String s) {
        char[] chars = s.toCharArray();
        List<String> res = new ArrayList<>();
        printAllSonProcess(chars, 0, res);
        return res;
    }

    /**
     * @param chars 0-（i-1）是已经选完的 i-1 - chars.length-1 需要随机排列所有的
     * @param i
     * @param res   将所有产生的结果放入res
     */
    private static void printAllSonProcess(char[] chars, int i, List<String> res) {
        if (i == chars.length) {
            res.add(String.valueOf(chars));
            return;
        }
        // 若不采用flag，可以返回一个带重复项的list，最后使用set去重即可，但是这样是所有可能都要走的，增加了常数项（字母相同的尝试），同时增加了清洗数据的时间
        boolean[] flag = new boolean[26];   // 哪一位是true，代表当前选中了这个字母，后面的随机交换不允许与该字母一致，一致会导致重复字符串
        for (int j = i; j < chars.length; j++) {
            if (!flag[chars[j] - 'a']) {    // 剪支 分支限界
                flag[chars[j] - 'a'] = true;
                swap(chars, j, i);
                printAllSonProcess(chars, i + 1, res);
                swap(chars, j, i);
            }
        }
    }

    /**
     * swap arr[j] arr[i]
     * 
     * @param chars
     * @param j
     * @param i
     */
    private static void swap(char[] chars, int j, int i) {
        char temp = chars[j];
        chars[j] = chars[i];
        chars[i] = temp;
    }

    /**
     * 给定一个整型数组arr,代表数值不同的纸牌排成一条线。玩家A和玩家B依次拿走每张纸
     * 牌，规定玩家A先拿，玩家B后拿，但是每个玩家每次只能拿走最左或最右的纸牌，玩家A
     * 和玩家B都绝顶聪明。请返回最后获胜者的分数。
     * 【举例】
     * arr=[1,2,100,4]。
     * 开始时，玩家A只能拿走1或4。如果开始时玩家A拿走1，则排列变为[2,100,4]，接下来
     * 玩家B可以拿走2或4，然后继续轮到玩家A.
     * 如果开始时玩家A拿走4，则排列变为[1,2,100]，接下来玩家B可以拿走1或100,
     * 然后继
     * 续轮到玩家A..
     * 玩家A作为绝顶聪明的人不会先拿4，因为拿4之后，玩家B将拿走100。所以玩家A会先拿1,
     * 让排列变为[2,100,4]，接下来玩家B不管怎么选，100都会被玩家A拿走。玩家A会获胜，
     * 分数为101。所以返回101.
     * arr=[1,100,2]。
     * 开始时，玩家A不管拿1还是2，玩家B作为绝顶聪明的人，
     * 都会把100拿走。玩家B会获胜，
     * 分数为100。所以返回100。
     * @param cards
     * @return 获胜者的分数
     */
    public static int gameScore(int[] cards){
        return 0;   
    }

}


