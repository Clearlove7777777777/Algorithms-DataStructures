package com.zjh.base;

import java.util.Arrays;

/**
 * Class1 class
 *
 * @author zjh
 * @date 2022/7/11 16:01
 */
public class Class1 {
    /**
     * 给定一个数组，数组中只有一种数有奇数个，其余数都是偶数个，找出那个出现了奇数个的数
     *
     * @param array
     * @return
     */
    public static int findOdd(int[] array) {
        int x = 0;
        for (int i = 0; i < array.length; i++) {
            x ^= array[i];
        }
        return x;
    }

    /**
     * 给定一个数组，数组中只有2种数有奇数个，其余数都是偶数个，找出那2个出现了奇数个的数
     *
     * @param array
     * @return
     */
    public static int[] findOdd2(int[] array) {
        int ab = 0;
        for (int i = 0; i < array.length; i++) {
            ab ^= array[i];
        }
        // 提取最右侧的1
        int rightOne = ab & (~ab + 1);
        int aOrb = 0;
        for (int el : array) {
            if ((el & rightOne) == 0) {  // 这里与出来只有可能是0或者rightOne
                aOrb ^= el;
            }
        }
        int[] result = new int[2];
        result[0] = aOrb;
        result[1] = aOrb ^ ab;
        return result;
    }

    /**
     * 二分应用
     * <p>
     * 在有序数组中判断一个数是否存在，存在返回下标，不存在返回-1
     */
    public static int findInOrdinalArray(int[] array, int target) {
        if (target < array[0] || target > array[array.length - 1])
            return -1;
        for (int i = 0, j = array.length - 1; i <= j; ) {
            int centerIndex = (i + j) / 2;
            if (array[centerIndex] == target) {
                return centerIndex;
            } else if (array[centerIndex] < target) {
                i = i != centerIndex ? centerIndex : i + 1;
            } else {
                j = j != centerIndex ? centerIndex : j - 1;
            }
        }
        return -1;
    }

    /**
     * 二分应用
     * <p>
     * 在有序数组中查找>=target的最左侧位置
     * [1 2 2 2 3 3 4 4 4 5] target = 3
     * ^
     */
    public static int findInOrdinalArray2(int[] array, int target) {
        for (int i = 0, j = array.length - 1; i <= j; ) {
            if (j - i <= 1) {
                if (array[i] <= target && array[j] >= target) {
                    return j;
                } else {
                    return -1;
                }
            }
            int centerIndex = (i + j) / 2;
            if (array[centerIndex] >= target) {
                j = centerIndex;
            } else {
                i = centerIndex;
            }
        }
        return -1;
    }

    /**
     * 二分应用 
     * <p>
     * 在一个无序数组中，相邻的数一定不相等
     * 定义概念-局部最小：对于长度为n数组X
     * 对0位置上的数，若X[0]<X[1]，则称X[0]为局部最小
     * 对n-1位置上的数，若X[n-1]<X[n-2]，则称X[n-1]为局部最小
     * 对于i位置上的数，若X[i-1]>X[i]<X[i+1],则称X[i]为局部最小
     * 问：求一个局部最小数的位置，且要求时间复杂度好于O(n)
     * 4 3 2 1 2
     */
    public static int findInOrdinalArray3(int[] array) {
        if (array.length < 2) {
            return -1;
        }
        if (array[0] < array[1]) {
            return 0;
        }
        if (array[array.length - 1] < array[array.length - 2]) {
            return array.length - 1;
        }
        /**
         * 如果上述条件都不满足，则数组最左侧是斜右下趋势，最右侧是斜左下趋势，又因为相邻两数一定不相等，数组中一定存在一值是两趋势的交点，构成大小大，即局部最小
         * 如下图:
         *      \    /    \      /
         *       \  /      \/\  /
         *        \/          \/
         * 由此可以抽象出，当所求问题，你可以通过一个条件废弃掉左右任意一侧时，就可以使用二分       
         */
        for (int i = 0, j = array.length - 1;;) {
            int centerIndex = (i + j) / 2;
            if (array[centerIndex] < array[centerIndex - 1] && array[centerIndex] < array[centerIndex + 1]){
                return centerIndex;
            }
            if (array[centerIndex] > array[centerIndex - 1] && array[centerIndex] < array[centerIndex + 1]){
                j = centerIndex;
                continue;
            }
            i = centerIndex;
        }
    }


    public static void main(String[] args) {
        // int[] test3 = new int[]{3, 5, 7, 8,9,10,11,24,56,78,99,100};
        // System.out.println(findInOrdinalArray(test3, 56));
        //
        // int[] test1 = new int[]{1, 1, 1, 2, 2, 2, 3, 1, 3, 231312, 231312, 231312};
        // System.out.println(Arrays.toString(findOdd2(test1)));
        //
        // System.out.println(14 & 2);
        //
        // int[] ints = {3, 2, 1, 6, 8, 9, 4};
        // System.out.println(Arrays.toString(insertSort(ints)));
        //
        // int a = 10;
        // int b = 10;
        // a = a ^ b;
        // b = a ^ b;
        // a = a ^ b;
        // System.out.println(a + b);
        // int[] test = new int[]{0, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 5, 6, 7, 8, 9};
        // System.out.println(findInOrdinalArray2(test, 5));
        
        int[] test = new int[]{9,8,7,6,5,4,3,4,5,6,7,8,9};
        System.out.println(findInOrdinalArray3(test));

        String[] split = "123".split(",");
        System.out.println(Arrays.toString(split));
    }

    // 3 7 2 5 4
    // 插入排序
    public static int[] insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0 && array[j] < array[j - 1]; j--) {
                swap(j, j - 1, array);
            }
        }
        return array;
    }

    public static void swap(int i, int j, int[] array) {
        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[i] ^ array[j];
    }
}
