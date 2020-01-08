package com.f.mml.common.sequence;


import com.alibaba.fastjson.JSONObject;

public class DataSequence {

    /**
     * 内排序可以分为以下几类：
     *
     * 　　(1)、插入排序：直接插入排序、二分法插入排序、希尔排序。
     *
     * 　　(2)、选择排序：简单选择排序、堆排序。
     *
     * 　　(3)、交换排序：冒泡排序、快速排序。
     *
     * 　　(4)、归并排序
     *
     * 　　(5)、基数排序
     */

    /**
     * 2.1 直接插入排序
     * 算法思想：
     *
     * <1>.从第一个元素开始，该元素可以认为已经被排序；
     * <2>.取出下一个元素，在已经排序的元素序列中从后向前扫描；
     * <3>.如果该元素（已排序）大于新元素，将该已排序元素移到下一位置；
     * <4>.重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
     * <5>.将新元素插入到该位置后；
     * <6>.重复步骤2~5。
     */

    public int[] insertSort(int[] arr){
        //从前向后遍历待排序列
        for (int i = 1; i < arr.length; i++) {
            //当前正在遍历的元素值
            int key = arr[i];
            //从后向前扫描已排序序列，依次与当前元素对比
            int j = i - 1;
            System.out.println("key-"+key+"key 前一个数"+arr[j]);
            while(j >= 0 && arr[j] > key){
                arr[j+1] = arr[j];
                System.out.println("现在前一个数大于后一个数的话-"+arr[j+1] );
                j--;
            }
            arr[j+1] = key;
            System.out.println("现在前一个数-"+arr[0] );
            System.out.println(JSONObject.toJSONString(arr));
        }
        return arr;
    }

    /**
     * 2.2 二分排序（折半插入排序）
     * 二分法查找基本思想：对于一个有序的待查序列，定义三个指针low、high、mid，分别指向待查序列所在范围的下界、上界及区间中间位置，
     * 即mid=（low+high）/2。对比待查数据与mid所指的值，若相等则查找成功并返回mid，
     * 若待查数小于mid值，则令high=mid-1，否则令low=mid+1，得到新的需要查找的区间，如此循环直到找出或找不到。
     */


    public int[] binaryInsertSort(int[] arr) {
        int low, high, mid;
        int key;
        for (int i = 1; i < arr.length; i++) {
            key = arr[i];
            low = 0;
            high = i - 1;

            //执行二分查找，注意符号（<、<=...）
            while(low <= high){
                mid = (low + high)/2;
                if (key < arr[mid]){
                    high = mid -1;
                }else {
                    low = mid + 1;
                }
            }

            //二分查找终止，说明找到合适的位置low
            //将low位置及之后的所有元素右移一位，再将当前遍历元素插入到low处
            for (int j = i - 1; j >= low; j--) {
                arr[j+1] = arr[j];
            }
            arr[low] = key;
            System.out.println(JSONObject.toJSONString(arr));
        }
        return arr;
    }

    /**
     * 3.1 冒泡排序（Bubble Sort）
     *
     */
    public int[] bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j]>arr[j+1]) {
                    swap(arr,j,j+1);
                }
            }
        }
        return arr;
    }

    /**
     * 交换两个数
     */
    public void swap(int[] arr,int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * .1 简单选择排序（Selection Sort）
     * 基本思想：首先在未排序序列中找到最小元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小元素，然后放到排序序列末尾。
     * 以此类推，直到所有元素均排序完毕。具体做法是：选择最小的元素与未排序部分的首部交换，使得序列的前面为有序。
     *
     *
     */
    public int[] selectionSort(int[] arr) {
        int minIndex;
        int temp;
        //一趟找出一个最小值
        for (int i = 0; i < arr.length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < arr.length ; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                    System.out.println(minIndex);
                }
            }
            System.out.println("arr[minIndex]"+arr[minIndex]);
            //将找到的最小值放到已排序序列的末尾
            temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
            System.out.println(JSONObject.toJSONString(arr));
        }
        return arr;
    }




     /***
     * @param args
     */
    public static void main(String [] args){
        DataSequence ds = new  DataSequence();
        int[] arr = new int[]{3,2,5,9,4,6,1};
        int[] ss = ds.selectionSort(arr);
        System.out.println(JSONObject.toJSONString(ss));
    }


}
