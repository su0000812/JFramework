package com.Jsu.JFramework;

import java.util.Arrays;

/**
 * Created by JSu on 2016/7/26.
 */
public class Algorithm {

    public static void main(String[] args) {
        shellSort(new int[]{4, 3, 8, 2, 4, 2, 7, 10, 5, 6});
    }

    /**
     * 选择排序
     * 运行时间和输入无关
     * 数据移动最少
     *
     * @param arr
     */
    public static void selectSort(int[] arr) {
        int arrLength = arr.length;

        for (int i = 0; i < arrLength; i++) {
            int min = i;
            for (int j = i + 1; j < arrLength; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            System.out.println(Arrays.toString(arr));
            int temp = 0;
            temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
            System.out.println(Arrays.toString(arr));
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 插入排序
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && arr[j] < arr[j - 1]; j--) {
                System.out.println(Arrays.toString(arr));
                int temp = 0;
                temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                System.out.println(Arrays.toString(arr));
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 希尔排序
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        int n = arr.length;
        int h = 1;
        while (h < n / 3)
            h = 3 * h + 1;
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                System.out.println("1" + Arrays.toString(arr));
                for (int j = i; j >= h && arr[j] < arr[j - h]; j -= h) {
                    System.out.println("2" + Arrays.toString(arr));
                    int temp = 0;
                    temp = arr[j];
                    arr[j] = arr[j - h];
                    arr[j - h] = temp;
                    System.out.println("3" + Arrays.toString(arr));
                }
            }
            h = h / 3;
            System.out.println("h == " + h);
        }
    }

    /**
     * 冒泡排序
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
