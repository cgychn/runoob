package com.query;

import com.tree.BinarySearchTree;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 二叉树查找
 */
public class BinaryTreeSearch {

    static BinarySearchTree binarySearchTree = new BinarySearchTree();
    static List<Integer> list;

    /**
     * 查找
     * @param num
     * @return
     */
    public static BinarySearchTree.TreeNode search (int num) {
        BinarySearchTree.TreeNode currentNode = binarySearchTree.getRoot();
        while (currentNode != null) {
            if (currentNode.getNum() == num) {
                return currentNode;
            } else if (num > currentNode.getNum()) {
                currentNode = currentNode.getRight();
            } else {
                currentNode = currentNode.getLeft();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        list = Arrays.stream("3390, 11839, 13094, 113468, 161602, 184718, 194322, 209175, 244673, 256218, 311423, 318218, 334207, 358422, 386821, 412102, 449648, 530735, 599824, 615111, 719784, 720097, 765853, 832045, 854056, 855980, 856680, 911422, 938166, 956280".split(", ")).map(x -> {return Integer.parseInt(x);}).collect(Collectors.toList());
        for (int v : list) {
            BinarySearchTree.TreeNode treeNode = binarySearchTree.new TreeNode();
            treeNode.setNum(v);
            binarySearchTree.insertNode(treeNode);
        }
        System.out.println(search(956280));
    }

}
