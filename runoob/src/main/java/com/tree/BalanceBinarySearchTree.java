package com.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 平衡二叉搜索树
 */
public class BalanceBinarySearchTree {

    /**
     * 该树的根节点
     */
    private TreeNode root;
    /**
     * 树节点
     */
    class TreeNode {
        String id;
        Integer depth = 1; // 节点默认深度是1
        Integer num; // 节点编号（决定节点在树中的位置）
        Object data; // 节点存放的数据
        TreeNode parent; // 节点的父节点
        TreeNode left; // 左子树
        TreeNode right; // 右子树
        String leftOrRight = "C"; // 是父节点的左节点还是右节点，默认为C，占位用

        public String getLeftOrRight() {
            return leftOrRight;
        }

        public void setLeftOrRight(String leftOrRight) {
            this.leftOrRight = leftOrRight;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            if (left != null) {
                left.setParent(this);
                left.setLeftOrRight("L");
            }
            this.left = left;
            calcDepthToTop(this);
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            if (right != null) {
                right.setParent(this);
                right.setLeftOrRight("R");
            }
            this.right = right;
            calcDepthToTop(this);
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer getDepth() {
            return depth;
        }

        public void setDepth(Integer depth) {
            this.depth = depth;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
            if (parent == null) {
                this.setLeftOrRight("C");
            }
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "depth=" + depth +
                    ", num=" + num +
                    ", pNum=" + (parent == null ? null : parent.num) +
                    ", left_num=" + (left == null ? null : left.num) +
                    ", right_num=" + (right == null ? null : right.num) +
                    ", left_depth=" + getLeftDepth(this) +
                    ", right_depth=" + getRightDepth(this) +
                    '}';
        }
    }

    /**
     * 插入节点（必须要设定number）
     * @param treeNode
     */
    public void insertNode (TreeNode treeNode) {
        if (root == null) {
            root = treeNode;
        } else {
            TreeNode currentNode = root;
            while (true) {
                // 小的放左边
                if (treeNode.getNum() <= currentNode.getNum()) {
                    if (currentNode.getLeft() != null) {
                        currentNode = currentNode.getLeft();
                        continue;
                    } else {
                        currentNode.setLeft(treeNode);
                        checkTreeBalance(treeNode);
                        break;
                    }
                } else if (treeNode.getNum() > currentNode.getNum()) {
                    // 大的放右边
                    if (currentNode.getRight() != null) {
                        currentNode = currentNode.getRight();
                        continue;
                    } else {
                        currentNode.setRight(treeNode);
                        checkTreeBalance(treeNode);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 向上递归计算搜索二叉树的深度
     * @param treeNode
     */
    private void checkTreeBalance (TreeNode treeNode) {
        System.out.println("\n\ncurrent insert treeNode : " + treeNode);
        // 四种类型
        TreeNode treeNodeParent = treeNode.getParent();
        String direct = treeNode.getLeftOrRight();
        while (treeNodeParent != null) {
            int delta = Math.abs(getLeftDepth(treeNodeParent) - getRightDepth(treeNodeParent));
            System.out.println("treeNodeParent : " + treeNodeParent);
            String type = treeNodeParent.getLeftOrRight();
            direct = type + direct;

            if (delta > 1) {
                System.out.println("direct : " + direct);
                // 取中间的两位，应为第一位可能是占位符
                switch (direct.substring(1, 3)) {
                    case "LL":
                        System.out.println("newNode : " + turnRight(treeNodeParent));
                        break;
                    case "RR":
                        System.out.println("newNode : " + turnLeft(treeNodeParent));
                        break;
                    case "LR":
                        System.out.println("newNode1 : " + turnLeft(treeNodeParent.getLeft()));
                        System.out.println("newNode2 : " + turnRight(treeNodeParent));
                        break;
                    case "RL":
                        System.out.println("newNode1 : " + turnRight(treeNodeParent.getRight()));
                        System.out.println("newNode2 : " + turnLeft(treeNodeParent));
                        break;
                }
                break;
            }

            treeNodeParent = treeNodeParent.getParent();
        }

    }

    /**
     * 向左旋转
     */
    private TreeNode turnLeft (TreeNode currentNode) {

//        （1）节点的右孩子替代此节点位置
//        （2）右孩子的左子树变为该节点的右子树
//        （3）节点本身变为右孩子的左子树

        TreeNode rightNode = currentNode.getRight();
        TreeNode parent = currentNode.getParent();
        int nodeIsParentsWhatNode = currentNode.getParent() == null ? -1 : currentNode.getLeftOrRight().equals("L") ? 0 : 1;

        // 将当前节点脱离父节点
        currentNode.setParent(null);
        // 获取当前节点的右节点的左节点
        TreeNode rightNodeLeftNode = rightNode.getLeft();
        // 当前节点的右节点脱离父节点
        rightNode.setParent(null);
        // 当前节点的右节点的左节点作为当前节点的右节点
        currentNode.setRight(rightNodeLeftNode);

        // 当前节点的右节点代替当前节点的位置
        if (parent != null) {
            if (nodeIsParentsWhatNode == 0) {
                // 当前节点是父节点的左子树
                parent.setLeft(rightNode);
            } else if (nodeIsParentsWhatNode == 1) {
                // 当前节点是父节点的右子树
                parent.setRight(rightNode);
            }
        } else {
            root = rightNode;
            root.setParent(null);
        }

        rightNode.setLeft(currentNode);
        return rightNode;
    }

    /**
     * 向右旋转
     */
    private TreeNode turnRight (TreeNode currentNode) {

//        （1）节点的左孩子代表此节点
//        （2）节点的左孩子的右子树变为节点的左子树
//        （3）将此节点作为左孩子节点的右子树。

        TreeNode leftNode = currentNode.getLeft();
        TreeNode parent = currentNode.getParent();
        int nodeIsParentsWhatNode = currentNode.getParent() == null ? -1 : currentNode.getLeftOrRight().equals("L") ? 0 : 1;

        currentNode.setParent(null);
        TreeNode leftNodeRightNode = leftNode.getRight();
        leftNode.setParent(null);
        // 设置为该节点的左子树
        currentNode.setLeft(leftNodeRightNode);

        // 当前节点的左节点代替当前节点的位置
        if (parent != null) {
            if (nodeIsParentsWhatNode == 0) {
                // 当前节点是父节点的左子树
                parent.setLeft(leftNode);
            } else if (nodeIsParentsWhatNode == 1) {
                // 当前节点是父节点的右子树
                parent.setRight(leftNode);
            }
        } else {
            root = leftNode;
            root.setParent(null);
        }

        // 当前节点变为左孩子的右子树
        leftNode.setRight(currentNode);
        return leftNode;
    }


    /**
     * 计算左子树的深度
     * @param TreeNode
     * @return
     */
    private int getLeftDepth (TreeNode TreeNode) {
        if (TreeNode.getLeft() == null) {
            return 0;
        } else {
            return TreeNode.getLeft().getDepth();
        }
    }

    /**
     * 计算右子树的深度
     * @param TreeNode
     * @return
     */
    private int getRightDepth (TreeNode TreeNode) {
        if (TreeNode.getRight() == null) {
            return 0;
        } else {
            return TreeNode.getRight().getDepth();
        }
    }

    /**
     * 递归向上计算树节点的深度
     * @param TreeNode
     */
    private void calcDepthToTop (TreeNode TreeNode) {
        TreeNode currentNode = TreeNode;
        while (true) {
            currentNode.setDepth(Math.max(getLeftDepth(currentNode), getRightDepth(currentNode)) + 1);
            if (currentNode.getParent() != null) {
                currentNode = currentNode.getParent();
            } else {
                root = currentNode;
                break;
            }
        }
    }

    /**
     * 在二叉搜索树中查找
     * @param num
     * @return
     */
    public TreeNode getTreeNodeByNum (int num) {
        TreeNode searchNode = root;
        while (searchNode.getNum() != num) {
             System.out.println(searchNode.getNum() + "," + "深度：" + searchNode.getDepth());
            if (num > searchNode.getNum()) {
                searchNode = searchNode.getRight();
            } else {
                searchNode = searchNode.getLeft();
            }
        }
        System.out.println(searchNode.getParent().getLeft());
        return searchNode;
    }

    /**
     * 测试插入和查找
     * @param args
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        BalanceBinarySearchTree balanceBinaryTree = new BalanceBinarySearchTree();
//        {66, 60, 77, 75, 88, 99}
//        List<Integer> integers = Arrays
//                .stream("633235, 231385, 919950, 940711, 871908, 635674, 12883, 317590, 121192, 892855, 830192, 36117, 208119, 837515, 595873, 88992, 941151, 821539, 663749, 688651, 672068, 700278, 77838, 164111, 921698, 231810, 133074, 561013, 77871, 622596"
//                        .split(", ")).map(x -> {return Integer.parseInt(x);}).collect(Collectors.toList());
        List<Integer> integers = new ArrayList<>();
        Random rand = new Random();
        int max = 0;
        for (int i = 0; i < 30; i++) {
            int current = rand.nextInt(1000000);
            if (current > max) {
                max = current;
            }
            integers.add(current);
        }
        System.out.println(integers);
        for (Integer i : integers) {
            TreeNode treeNode = balanceBinaryTree.new TreeNode();
            treeNode.setNum(i);
            balanceBinaryTree.insertNode(treeNode);
        }
        System.out.println("insertTime:" + (System.currentTimeMillis() - startTime));
        int maxNum = integers.stream().max((a, b) -> { return a > b ? 1 : -1;}).get();
        long qStartTime = System.currentTimeMillis();
        System.out.println(balanceBinaryTree.getTreeNodeByNum(maxNum));
        System.out.println("queryTime:" + (System.currentTimeMillis() - qStartTime));
    }


}
