package com.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 二分查找（先找一个基准，如果输入值比基准大，以基准下标为开始，以上次结束为结束重新确定区间，如果输入值比基准小，以基准下标为结束，以上次开始为开始重新确定区间）
 */
public class BinarySearch {

    static List<Integer> list = new ArrayList<>();
    static Random rand = new Random();

    /**
     * 查找（递归版本）
     * @param num
     * @param start
     * @param end
     * @return
     */
    public static Integer searchRecursion (int num, int start, int end) {
        // 找一个基准
        int flag = (start + end) / 2;
        if (start == end && list.get(start) != num) {
            return -1;
        }
        if (num < list.get(flag)) {
            return searchRecursion(num, start, flag - 1);
        } else if (num > list.get(flag)) {
            return searchRecursion(num, flag + 1, end);
        } else {
            return flag;
        }
    }

    public static Integer searchWithOutRecursion (int num) {
        int start = 0;
        int end = list.size();
        int flag;
        while (start <= end) {
            flag = (start + end) / 2;
            if (list.get(flag) == num) {
                return flag;
            } else if (num > list.get(flag)) {
                start = flag + 1;
            } else {
                end = flag - 1;
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
//
//        // 二分查找要求list为有序list，先排序
//        list = list.stream().sorted().collect(Collectors.toList());
        list = Arrays.stream("3390, 11839, 13094, 113468, 113469, 161602, 184718, 194322, 209175, 244673, 256218, 311423, 318218, 334207, 358422, 386821, 412102, 449648, 530735, 599824, 615111, 719784, 720097, 765853, 832045, 854056, 855980, 856680, 911422, 938166, 956280".split(", ")).map(x -> {return Integer.parseInt(x);}).collect(Collectors.toList());
        System.out.println(list);

        System.out.println(161602 + ", index : " + searchRecursion(161602, 0, list.size()));
        System.out.println(161602 + ", index : " + searchWithOutRecursion(161602));
    }

}
