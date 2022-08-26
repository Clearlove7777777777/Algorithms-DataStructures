package com.zjh.base;

import java.util.Arrays;

/**
 * Class7_Trie class
 * 前缀树
 *
 * @author zjh
 * @date 2022/8/15 16:54
 */
public class Class7_Trie {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.addAll(Arrays.asList("abc","ab","ab","iop","aop","bcd"));
        System.out.println(trie.prefixNumber(""));
        System.out.println(trie.search("abc"));
        trie.add("abc");
        System.out.println(trie.search("abc"));
        trie.delete("abc");
        System.out.println(trie.search("abc"));
        trie.delete("abc");
        System.out.println(trie.search("abc"));
    }
    
    // 前缀树节点
    static class TrieNode {
        // 无需值字段，因为经典的前缀树的值信息是保存在路中的
        int pass;
        int end;
        // 假设前缀树中所有字符串都是以小写英文字母构成   如果不限制，则可以使用Map<Character,TrieNode>
        TrieNode[] nexts = new TrieNode[26];
    }

    // 前缀树
    static class Trie {
        private TrieNode root;  // 根节点的pass表示一共往前缀树加入了多少个字符串

        public Trie() {
            this.root = new TrieNode();
        }

        public void add(String word) {
            if (word == null)
                return;
            TrieNode node = root;
            node.pass++;
            char[] chs = word.toCharArray();
            int index = 0;
            for (char ch : chs) {
                index = ch - 'a';
                if (node.nexts[index] == null)
                    node.nexts[index] = new TrieNode();
                node = node.nexts[index];
                node.pass++;
            }
            node.end++;
        }

        public void addAll(Iterable<String> arr) {
            for (String s : arr) {
                add(s);
            }
        }

        /**
         * 查询一个字符串被加入了多少次
         *
         * @param word
         * @return
         */
        public int search(String word) {
            if (word == null)
                return 0;
            char[] chs = word.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (char ch : chs) {
                index = ch - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.end;
        }

        /**
         * 加入的字符串中有多少个是以pre为开头的
         *
         * @param pre
         * @return
         */
        public int prefixNumber(String pre) {
            if (pre == null)
                return 0;
            char[] chs = pre.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (char ch : chs) {
                index = ch - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.pass;
        }

        /**
         * 删除前缀树中的字符串
         *
         * @param word
         */
        public void delete(String word) {
            if (search(word) != 0) { // 只有加入过的字符串才删除
                char[] chs = word.toCharArray();
                TrieNode node = root;   
                int index = 0;
                for (char ch : chs) {
                    index = ch - 'a';
                    if (--node.nexts[index].pass == 0) {
                        node.nexts[index] = null;
                        return;
                    }
                    node = node.nexts[index];
                }
                // 若删除时pass并未=0，表示该字符串被加入了不止一次，end--即可
                node.end--;
            }
        }
    }
}
