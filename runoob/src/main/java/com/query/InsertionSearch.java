package com.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 插值查找（二分查找的改良版）
 */
public class InsertionSearch {

    static List<Integer> list = new ArrayList<>();
    static Random rand = new Random();

    /**
     * 递归版本
     * @param num
     * @param start
     * @param end
     * @return
     */
    public static Integer searchRecursion (int num, int start, int end) {
        System.out.println(start + " | " + (num - list.get(start)) + " | " + (list.get(end - 1) - list.get(start)));
        // 计算期望 min为list中最小的数，max为list中最大的数，num为要查找的数(max - num) / (max - nim)
        float p = (float) (num - list.get(start)) / (float) (list.get(end - 1) - list.get(start));
        // 计算下标
        System.out.println(Math.ceil(p));
        int flag = (int) Math.floor((end - 1 - start) * Math.ceil(p));
        System.out.println(flag);
        if (start == end && list.get(start) != num) {
            return -1;
        }
        if (num < list.get(flag)) {
            System.out.println("num : " + num + " 小于 flag num : " + list.get(flag));
            return searchRecursion(num, start, flag - 1);
        } else if (num > list.get(flag)) {
            System.out.println("num : " + num + " 大于 flag num : " + list.get(flag));
            return searchRecursion(num, flag + 1, end);
        } else {
            // 找到了
            System.out.println("got : " + flag);
            return flag;
        }
    }

    /**
     * 非递归版本
     * @param num
     * @return
     */
    public static Integer searchWithoutRecursion (int num) {
        int start = 0;
        int end = list.size() - 1;
        float p = 0;
        int flag = 0;
        while (start <= end) {
            p = (float) (num - list.get(start)) / (float) (list.get(end) - list.get(start));
            flag = (int) Math.floor((end - start) * Math.ceil(p));
            if (list.get(flag) == num) {
                return flag;
            } else if (num < list.get(flag)) {
                end = flag - 1;
            } else {
                start = start + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        int max = 0;
//        for (int i = 0; i < 30; i++) {
//            int current = rand.nextInt(1000000);
//            if (current > max) {
//                max = current;
//            }
//            list.add(current);
//        }

        // 插值查找要求list为有序list，先排序
        list = Arrays.stream("3390, 11839, 13094, 113468, 161602, 184718, 194322, 209175, 244673, 256218, 311423, 318218, 334207, 358422, 386821, 412102, 449648, 530735, 599824, 615111, 719784, 720097, 765853, 832045, 854056, 855980, 856680, 911422, 938166, 956280".split(", ")).map(x -> {return Integer.parseInt(x);}).collect(Collectors.toList());

        System.out.println(list);

        System.out.println(956280 + ", index : " + searchRecursion(956280, 0, list.size()));
        System.out.println(956280 + ", index : " + searchWithoutRecursion(956280));
    }

}
