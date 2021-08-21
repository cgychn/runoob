package com.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 斐波那契搜索（二分查找改良）
 */
public class FibonacciSearch {

    static List<Integer> list = new ArrayList<>();
    static Random rand = new Random();

    // 得到斐波那契数列中的第n个
    public static void getNthFibonacci (int start, int nth) {
        int current = 0;
        int last = 0;
        for (int i = 0; i <= nth; i ++) {
            if (i == 0 || i == 1) {
                last = start;
                current = start;
            } else {
                int temp = last;
                last = current;
                current = current + temp;
            }
            System.out.println("current : " + current);
        }
        System.out.println(nth + "th : " + current);
    }

    public static int f (int n) {
        int current = 0;
        int last = 0;
        if (n == 0) {
            return 0;
        }
        for (int i = 1; i <= n; i ++) {
            if (i == 1 || i == 2) {
                last = 1;
                current = 1;
            } else {
                int temp = last;
                last = current;
                current = current + temp;
            }
            System.out.println("current : " + current);
        }
        System.out.println("f(" + n + ") : " + current);
        return current;
    }

    public static String getFibonacciNAndValueWithMax (int max) {
        int current = 0;
        int last = 0;
        int i = 0;
        while (current < max) {
            if (i == 0) {
                current = 0;
            } else if (i == 1 || i == 2) {
                current = 1;
                last = 1;
            } else {
                int temp = last;
                last = current;
                current = current + temp;
            }
            i ++;
        }
        return current + " || " + (i > 0 ? i - 1 : i);
    }

    /**
     * 递归方式
     * @param num
     * @param start
     * @param end
     * @return
     */
    public static Integer searchRecursion (int num, int start, int end, int fN) {
        // 计算f（n-1）
        System.out.println(start + " , " + end + " , " + fN);
        int flag = start + f(fN - 1) - 1;
        if (start == end && list.get(start) != num) {
            return -1;
        } else if (num < list.get(flag)) {
            return searchRecursion(num, start, flag - 1, fN - 1);
        } else if (num > list.get(flag)) {
            return searchRecursion(num, flag + 1, end, fN - 2);
        } else {
            return flag;
        }
    }

    public static Integer searchWithoutRecursion (int num, int fN) {
        int start = 0;
        int end = list.size() - 1;
        int flag = 0;
        while (start <= end) {
            flag = start + f(fN - 1) - 1;
            if (start == end && list.get(start) == num) {
                return start;
            } else if (num < list.get(flag)) {
                end = flag - 1;
                fN = fN - 1;
            } else if (num > list.get(flag)) {
                start = flag + 1;
                fN = fN - 2;
            } else if (num == list.get(flag)) {
                return flag;
            }
        }
        return -1;
    }

    public static void main (String[] args) {

        list = Arrays.stream("3390, 11839, 13094, 113468, 161602, 184718, 194322, 209175, 244673, 256218, 311423, 318218, 334207, 358422, 386821, 412102, 449648, 530735, 599824, 615111, 719784, 720097, 765853, 832045, 854056, 855980, 856680, 911422, 938166, 956280".split(", ")).map(x -> {return Integer.parseInt(x);}).collect(Collectors.toList());

        String res = getFibonacciNAndValueWithMax(list.size());
        String[] resParts = res.split(" \\|\\| ");
        Integer value = Integer.parseInt(resParts[0]);
        Integer index = Integer.parseInt(resParts[1]);
        // 填充25个元素到value个
        int max = list.get(list.size() - 1);
        for (int i = list.size(); i < value; i ++) {
            list.add(max);
        }
        // 查找
        System.out.println(956280 + ", index : " + searchRecursion(956280, 0, list.size(), index));
        System.out.println(956280 + ", index : " + searchWithoutRecursion(956280, index));
    }

}
