package com.zjh;

import java.util.Arrays;

/**
 * Class2 class
 * <p>
 * 递归
 *
 * @author zjh
 * @date 2022/7/12 9:29
 */
public class Class2 {
    public static int i = 0;

    /**
     * 使用递归求数组的最大值
     * 每个节点都 需要自己的子节点进行汇总信息
     */
    public static int findArrayMax(int[] array, int left, int right) {
        if (left == right) {
            return array[left];
        }
        int mid = (left + ((right - left) >> 1));
        int leftMax = findArrayMax(array, left, mid);
        int rightMax = findArrayMax(array, mid + 1, right);
        return Math.max(leftMax, rightMax);
    }

    /**
     * 归并排序
     */
    public static void mergeSort(int[] array, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = (l + ((r - l) >> 1));
        mergeSort(array, l, mid);
        mergeSort(array, mid + 1, r);
        mergeArray(array, l, mid, r);
    }

    /**
     * 合并
     */
    private static void mergeArray(int[] array, int l, int mid, int r) {
        // System.out.println("old:"+Arrays.toString(array));
        int[] temp = new int[r - l + 1];
        int tempIndex = 0;
        int lIndex = l;
        int rIndex = mid + 1;
        while (lIndex <= mid && rIndex <= r) {
            temp[tempIndex++] = array[lIndex] <= array[rIndex] ? array[lIndex++] : array[rIndex++];
        }

        while (lIndex <= mid) {
            temp[tempIndex++] = array[lIndex++];
        }

        while (rIndex <= r) {
            temp[tempIndex++] = array[rIndex++];
        }

        for (int i = 0; i < temp.length; i++) {
            array[l + i] = temp[i];
        }
    }

    public static void main(String[] args) {
        // int a = 1000;
        // int b = 1000;
        //
        // a = a ^ b;
        // b = a ^ b;
        // a = a ^ b;
        //
        // System.out.println(new BigDecimal("11").add(new BigDecimal("12")));
        // System.out.println(findArrayMax(new int[]{1, 2, 3}, 0, 2));
        // System.out.println(i);
        // 1 3 4 2 5


        // int[] test = new int[]{2, 1};
        // mergeSort(test, 0, test.length - 1);
        // System.out.println(Arrays.toString(test));
        // System.out.println(Arrays.toString(test));

        System.out.println(findMinSum(new int[]{1,2,1,3},0,3));
    }

    /**
     * 求小和
     * 对于数组[1,4,3,5,2]，对于1来说左侧无数字比自己小，因此其位置小和为0
     * 对于4来说，左侧有一个数字1比自己小，因此其位置小和为1
     * 对于数字3来说，左侧有一个数字1比自己小，因此其位置小和为1
     * 对于数字5来说左侧有1,4,3比自己小，因此其位置小和为8
     * 对于数字2来说左侧有1比自己小，因此其位置小和为1
     * 综上，0+1+1+8+1=11，因此数组的小和为11
     */
    public static int findMinSum(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return findMinSum(arr, l, mid) + findMinSum(arr, mid + 1, r) + mergeArray2(arr, l, mid, r);
    }


    /**
     * 合并
     */
    public static int mergeArray2(int[] arr, int l, int mid, int r) {
        int[] temp = new int[r - l + 1];
        int res = 0;
        int tempIndex = 0;
        int lIndex = l;
        int rIndex = mid + 1;
        while (lIndex <= mid && rIndex <= r) {
            boolean flag = arr[lIndex] < arr[rIndex];
            res += flag ? arr[lIndex] * (r - rIndex + 1) : 0;
            temp[tempIndex++] = flag ? arr[lIndex++] : arr[rIndex++];
        }
        
        while (lIndex <= mid){
            temp[tempIndex++] = arr[lIndex++];
        }
        
        while(rIndex <= r){
            temp[tempIndex++] = arr[rIndex++];
        }

        for (int i = 0; i < temp.length; i++) {
            arr[l+i] = temp[i];
        }
        return res;
    }

}
