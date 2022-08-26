package com.zjh.promote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class_11 class
 *
 * @author zjh
 * @date 2022/8/26 15:26
 */
public class Class_11 {
    /**
     * 对于一个矩阵，其内的数据只有0、1，我们每一个1都可以与其上下左右的1连接，这样连接的1称之为1个岛，给出矩阵请求出其内岛的个数
     *
     * 0 0 1 0 0
     * 1 1 1 0 1
     * 0 1 0 1 1
     * 1 0 1 1 0
     * 上述矩阵岛的个数为3
     */
    public static int getIsLandCount(int[][] matrix) {
        int N = matrix.length;
        int M = matrix[0].length;
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (matrix[i][j] == 1) {
                    res++;
                    isLandProc(matrix, i, j, N, M);
                }
            }
        }
        return res;
    }

    /**
     * 进阶：怎样设计一个并行算法来处理上述问题
     */

    /**
     * 并查集结构
     */
    

    /**
     * 感染过程
     *
     * @param matrix
     * @param i
     * @param j
     * @param N      总行数
     * @param M      总列数
     */
    private static void isLandProc(int[][] matrix, int i, int j, int N, int M) {
        if (i < 0 || i >= N || j < 0 || j >= M || matrix[i][j] != 1) {
            return;
        }
        matrix[i][j] = 2;
        isLandProc(matrix, i - 1, j, N, M);   // 当前元素上元素
        isLandProc(matrix, i + 1, j, N, M);   // 当前元素下元素
        isLandProc(matrix, i, j - 1, N, M);   // 当前元素左元素
        isLandProc(matrix, i, j + 1, N, M);   // 当前元素右元素
    }


    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                new int[]{0, 0, 1, 0, 1},
                new int[]{1, 1, 1, 0, 0},
                new int[]{0, 1, 0, 1, 1},
                new int[]{1, 0, 1, 1, 0}
        };
        System.out.println(getIsLandCount(matrix));
    }
}

/**
 * 并查集结构
 * 
 * 并查集结构需要一次性将数据全部初始化，不支持后续添加
 * 
 * @param <V>
 */
class DisjointSet<V>{
    class Element<V> {
        private V value;

        public Element(V value) {
            this.value = value;
        }
    }
    // 对象与包装类型
    private Map<V,Element<V>> elementMap;
    // 包装对象与其服包装对象
    private Map<Element<V>,Element<V>> fatherMap;
    // 代表元素与其代表的元素个数
    private Map<Element<V>,Integer> sizeMap;
    
    public DisjointSet(List<V> list) {
        elementMap = new HashMap<>();
        fatherMap = new HashMap<>();
        sizeMap = new HashMap<>();

        for (V item : list) {
            Element<V> element = new Element<>(item);
            elementMap.put(item,element);
            fatherMap.put(element,element);
            sizeMap.put(element,1);
        }
    }
    
    public boolean contains(V v){
        return elementMap.containsKey(v);
    }

    /**
     * 判断两元素是否是同一集合
     * 
     * @param v1
     * @param v2
     * @return
     */
    public boolean isSameSet(V v1,V v2){
        if (contains(v1) && contains(v2)){
            return findHead(elementMap.get(v1)) == findHead(elementMap.get(v2));
        }
        return false;
    }


    /**
     * 查找该元素最上层元素返回
     * 
     * @param
     * @return
     */
    private Element<V> findHead(Element<V> element) {
        while (element != fatherMap.get(element)){
            element = fatherMap.get(element);
        }
        return element;
    }

    /**
     * 合并两个元素所在的集合
     * 
     * @param v1
     * @param v2
     */
    public void union(V v1,V v2){
        if (contains(v1) && contains(v2) && findHead(elementMap.get(v1)) != findHead(elementMap.get(v2))){
            
        }
    }

}
