package com.zjh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Class3 class
 *
 * @author zjh
 * @date 2022/7/19 13:40
 */
public class Class3 {
    /**
     * 构建一个大根堆
     */
    public static void createMaxHeap(int[] arr) {
        int heapSize = 0;
        int index = 0;
        while (heapSize < arr.length) {
            int swapIndex = arr[index] > arr[(index - 1) / 2] ? (index - 1) / 2 : index;
            int nowIndex = index;
            while (nowIndex > swapIndex) {
                swap(arr, nowIndex, swapIndex);
                nowIndex = swapIndex;
                swapIndex = arr[nowIndex] > arr[(nowIndex - 1) / 2] ? (nowIndex - 1) / 2 : nowIndex;
            }
            heapSize++;
            index++;
        }
    }

    /**
     * 构建一个小跟堆
     */
    public static void createMinHeap(int[] arr){
        int heapSize = 0;
        int index = 0;
        while (heapSize < arr.length) {
            int swapIndex = arr[index] < arr[(index - 1) / 2] ? (index - 1) / 2 : index;
            int nowIndex = index;
            while (nowIndex > swapIndex) {
                swap(arr, nowIndex, swapIndex);
                nowIndex = swapIndex;
                swapIndex = arr[nowIndex] < arr[(nowIndex - 1) / 2] ? (nowIndex - 1) / 2 : nowIndex;
            }
            heapSize++;
            index++;
        }
    }
    
    public static void heapInsert(int[] arr,int index){
        while(arr[(index-1)/2]<arr[index]){
            swap(arr,(index-1)/2,index);
            index = (index-1)/2;
        }
    }

    /**
     * 将大根堆中的某个位置的数字弹出
     */
    public static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;   // 左孩子下标
        while (left < heapSize) { // 只要还有孩子
            int maxSonIndex = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            int maxIndex = arr[maxSonIndex] > arr[index] ? maxSonIndex : index;
            if (maxIndex == index) {
                break;
            }
            swap(arr, index, maxIndex);
            index = maxIndex;
            left = index * 2 + 1;
        }
    }

    /**
     * 堆排序
     */
    public static void heapSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr,i);
        }
        int heapSize = arr.length;
        while (heapSize > 0) {
            swap(arr, 0, --heapSize);
            heapify(arr, 0, heapSize);
        }
    }

    /**
     * 给定一个数组，其是基本有序的数组，基本有序是指，对其排序后每个元素移动的距离不会超过k，并且k相对于数组来说较小
     * 请给出算法，要求时间复杂度尽可能低
     * 
     * arr = [2,1,4,3] k = 1
     */
    public static void heapKSort(int[] arr,int k){
        // 系统提供的小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            while(heap.size()<k && index< arr.length){
                heap.add(arr[index++]);
            }
            arr[i] = heap.poll();
        }
    }

    /**
     * 交换数组中的两个数
     *
     * @param arr
     * @param x
     * @param y
     */
    private static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
    

    public static void main(String[] args) {
        // int[] test = new int[]{3,2,3,4,7,6,9};
        // heapSort(test);
        // System.out.println(Arrays.toString(test));
        //
        // PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // priorityQueue.add(3);
        // priorityQueue.add(4);
        // priorityQueue.add(6);
        // priorityQueue.add(1);
        // priorityQueue.add(2);
        // priorityQueue.add(9);
        // Iterator<Integer> iterator = priorityQueue.iterator();
        // while (iterator.hasNext()) {
        //     System.out.println(iterator.next());
        // }
        //
        // System.out.println("=============");
        // while (!priorityQueue.isEmpty()) {
        //     System.out.println(priorityQueue.poll());
        // }
        int[] test = new int[]{4,2,3,1,2,6,5,8,1,9};
        // heapKSort(test,123);
        // System.out.println(Arrays.toString(test));
        
        // createMinHeap(test);
        // System.out.println(Arrays.toString(test));

        Student student1 = new Student(1,4);
        Student student2 = new Student(2,3);
        Student student3 = new Student(3,2);
        Student student4 = new Student(4,1);

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        
        Collections.sort(studentList, new Comparator<Student>() {
            // comparator中的compare方法定义了排序的规则
            // 当返回负数时认为o1排在前面，当返回正数时认为o2排在前面，当返回0时认为谁前谁后不重要
            @Override
            public int compare(Student o1, Student o2) {
                return o1.age - o2.age;
            }
        });
        for (Student student : studentList) {
            System.out.println(student);
        }

        System.out.println(new BigDecimal("-0.01").abs());
    }
}

class Student{
    public int id;
    
    public int age;

    public Student(int id, int age) {
        this.id = id;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", age=" + age +
                '}';
    }
}
