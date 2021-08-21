package com.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 顺序查找
 */
public class SequenceSearch {

    static List<Integer> list = new ArrayList<>();
    static Random rand = new Random();

    /**
     * 查找
     * @param num
     * @return
     */
    public static Integer search (int num) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == num) {
                // 返回下标
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int max = 0;
        for (int i = 0; i < 30; i++) {
            int current = rand.nextInt(1000000);
            if (current > max) {
                max = current;
            }
            list.add(current);
        }
        System.out.println(list);
        System.out.println(max + ", index : " + search(max));
    }

}
