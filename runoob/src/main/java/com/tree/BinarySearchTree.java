package com.tree;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 二叉查找树（大的放右边，小的放左边）
 */
public class BinarySearchTree {

    /**
     * 树的根节点
     */
    private TreeNode root;
    /**
     * 树节点
     */
    public class TreeNode {
        int num;
        TreeNode parent;
        TreeNode left;
        TreeNode right;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            if (left != null) {
                left.setParent(this);
            }
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            if (right != null) {
                right.setParent(this);
            }
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "num=" + num +
                    '}';
        }
    }

    public void insertNode (TreeNode treeNode) {
        if (root == null) {
            root = treeNode;
        } else {
            TreeNode currentNode = root;
            while (true) {
                if (treeNode.getNum() <= currentNode.getNum()) {
                    if (currentNode.getLeft() != null) {
                        currentNode = currentNode.getLeft();
                        continue;
                    } else {
                        currentNode.setLeft(treeNode);
                        break;
                    }
                } else {
                    if (currentNode.getRight() != null) {
                        currentNode = currentNode.getRight();
                        continue;
                    } else {
                        currentNode.setRight(treeNode);
                        break;
                    }
                }
            }
        }
    }

    public TreeNode getRoot () {
        return root;
    }

    /**
     * 前序遍历
     * @return
     */
    public List<TreeNode> getDLRList () {
        return null;
    }

    /**
     * 中续遍历
     * @return
     */
    public List<TreeNode> getLDRList () {
        return null;
    }

    /**
     * 后续遍历
     * @return
     */
    public List<TreeNode> getLRDList () {
        return null;
    }


    public static void main(String[] args) {
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        List<Integer> list = Arrays.stream("3390, 11839, 13094, 113468, 161602, 184718, 194322, 209175, 244673, 256218, 311423, 318218, 334207, 358422, 386821, 412102, 449648, 530735, 599824, 615111, 719784, 720097, 765853, 832045, 854056, 855980, 856680, 911422, 938166, 956280".split(", ")).map(x -> {return Integer.parseInt(x);}).collect(Collectors.toList());
        for (int v : list) {
            TreeNode treeNode = binarySearchTree.new TreeNode();
            treeNode.setNum(v);
            binarySearchTree.insertNode(treeNode);
        }
    }

}
