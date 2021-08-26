package com.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Digraph {

    List<Node> nodes = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();
    int[][] graphMatrix;
    List<MainNode> graphTable;

    public class TreeNode {
        Node node;
        int parentToCurrentDistance = 0;
        List<TreeNode> children = new ArrayList<>();
        TreeNode parent;

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public int getParentToCurrentDistance() {
            return parentToCurrentDistance;
        }

        public void setParentToCurrentDistance(int parentToCurrentDistance) {
            this.parentToCurrentDistance = parentToCurrentDistance;
        }

        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        public void setChildren(List<TreeNode> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "node=" + node +
                    ", children=" + children +
                    '}';
        }
    }

    public class MainNode {
        Node node;
        LinkedList<NodeWithDistance> linkedList;

        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }

        public LinkedList<NodeWithDistance> getLinkedList() {
            return linkedList;
        }

        public void setLinkedList(LinkedList<NodeWithDistance> linkedList) {
            this.linkedList = linkedList;
        }

        @Override
        public String toString() {
            return "NodeMain{" +
                    "node=" + node +
                    ", linkedList=" + linkedList +
                    '}';
        }
    }

    public class NodeWithDistance {
        int dist;
        Node node;

        public int getDist() {
            return dist;
        }

        public void setDist(int dist) {
            this.dist = dist;
        }

        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public String toString() {
            return "NodeWithDistance{" +
                    "dist=" + dist +
                    ", node=" + node +
                    '}';
        }
    }

    public class Node {
        int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "num=" + num +
                    '}';
        }
    }

    public class Edge {
        int distance;
        Node source;
        Node target;

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public Node getSource() {
            return source;
        }

        public void setSource(Node source) {
            this.source = source;
        }

        public Node getTarget() {
            return target;
        }

        public void setTarget(Node target) {
            this.target = target;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "distance=" + distance +
                    ", source=" + source +
                    ", target=" + target +
                    '}';
        }
    }

    public void drawGraph (List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    // 得到邻接矩阵
    public int[][] genGraphMatrix () {
        int size = nodes.size();
        // 生成邻接矩阵
        this.graphMatrix = new int[size][size];
        for (Edge edge : this.edges) {
            this.graphMatrix[edge.getSource().getNum()][edge.getTarget().getNum()] = edge.getDistance();
        }
        return this.graphMatrix;
    }

    // 得到邻接表
    public List<MainNode> genGraphTable () {
        int size = this.nodes.size();
        // 生成邻接表
        this.graphTable = new ArrayList<>();
        for (int i = 0; i < size; i ++) {
            Node node = getNodeByNum(nodes.get(i).getNum());
            MainNode main = this.new MainNode();
            main.setNode(node);
            main.setLinkedList(new LinkedList<>());
            for (Edge edge : edges) {
                if (edge.getSource().getNum() == node.getNum()) {
                    NodeWithDistance nodeWithDistance = this.new NodeWithDistance();
                    nodeWithDistance.setDist(edge.getDistance());
                    nodeWithDistance.setNode(edge.getTarget());
                    main.getLinkedList().add(nodeWithDistance);
                }
            }
            this.graphTable.add(main);
        }
        return this.graphTable;
    }

    private Node getNodeByNum (int num) {
        for (Node node : nodes) {
            if (node.getNum() == num) {
                return node;
            }
        }
        return null;
    }

    public TreeNode transGraphToTree (Node startNode) {
        MainNode mainNode = getMainNodeFromGraphTable(startNode);
        TreeNode treeNode = new TreeNode();
        treeNode.setNode(startNode);

        List<TreeNode> nds = new LinkedList<>();

        for (NodeWithDistance nodeWithDistance : mainNode.getLinkedList()) {
            TreeNode tN = new TreeNode();
            tN.setNode(nodeWithDistance.getNode());
            tN.setParentToCurrentDistance(nodeWithDistance.getDist());
            treeNode.getChildren().add(tN);
            tN.setParent(treeNode);
            nds.add(tN);
        }

        while (!nds.isEmpty()) {
            TreeNode parent = nds.remove(nds.size() - 1);
            for (NodeWithDistance nodeWithDistance : getMainNodeFromGraphTable(parent.getNode()).getLinkedList()) {
                TreeNode tNd = new TreeNode();
                tNd.setNode(nodeWithDistance.getNode());
                tNd.setParentToCurrentDistance(nodeWithDistance.getDist());
                parent.getChildren().add(tNd);
                tNd.setParent(parent);
                nds.add(tNd);
            }

            System.out.println("nds : " + nds);
        }
        return treeNode;
    }

    private MainNode getMainNodeFromGraphTable(Node currentNode) {
        for (MainNode mainNode : this.genGraphTable()) {
            if (mainNode.getNode().getNum() == currentNode.getNum()) {
                return mainNode;
            }
        }
        return null;
    }

    /**
     * 深度优先遍历
     * @param treeNode
     */
    public void depthFirstSearch (TreeNode treeNode, List<LinkedList<TreeNode>> way) {
        if (treeNode.getChildren().size() == 0) {
            // 遍历到最跟节点
            LinkedList<TreeNode> linkedList = new LinkedList<>();
            TreeNode currentNode = treeNode;
            while (currentNode != null) {
                linkedList.add(0, currentNode);
                currentNode = currentNode.getParent();
            }
            way.add(linkedList);
        }
        for (TreeNode sub : treeNode.getChildren()) {
            System.out.print(sub.getNode().getNum() + "\t");
            depthFirstSearch(sub, way);
        }
    }

    public static void main(String[] args) {
        Digraph digraph = new Digraph();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            Node node = digraph.new Node();
            node.setNum(i);
            nodes.add(node);
        };

        digraph.nodes = nodes;

        List<Edge> edges = new ArrayList<>();
        Edge edge = digraph.new Edge();
        edge.setSource(digraph.getNodeByNum(0));
        edge.setTarget(digraph.getNodeByNum(1));
        edge.setDistance(10);

        Edge edge1 = digraph.new Edge();
        edge1.setSource(digraph.getNodeByNum(0));
        edge1.setTarget(digraph.getNodeByNum(2));
        edge1.setDistance(11);

        Edge edge2 = digraph.new Edge();
        edge2.setSource(digraph.getNodeByNum(0));
        edge2.setTarget(digraph.getNodeByNum(3));
        edge2.setDistance(9);

        Edge edge3 = digraph.new Edge();
        edge3.setSource(digraph.getNodeByNum(1));
        edge3.setTarget(digraph.getNodeByNum(3));
        edge3.setDistance(6);

        Edge edge4 = digraph.new Edge();
        edge4.setSource(digraph.getNodeByNum(1));
        edge4.setTarget(digraph.getNodeByNum(4));
        edge4.setDistance(7);

        Edge edge5 = digraph.new Edge();
        edge5.setSource(digraph.getNodeByNum(2));
        edge5.setTarget(digraph.getNodeByNum(3));
        edge5.setDistance(8);

        Edge edge6 = digraph.new Edge();
        edge6.setSource(digraph.getNodeByNum(3));
        edge6.setTarget(digraph.getNodeByNum(4));
        edge6.setDistance(15);

        edges.add(edge);
        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);
        edges.add(edge4);
        edges.add(edge5);
        edges.add(edge6);

        digraph.drawGraph(nodes, edges);

        System.out.println(digraph.genGraphTable());

        int[][] matrix = digraph.genGraphMatrix();

        for (int i = 0; i < matrix.length; i ++) {
            for (int j = 0; j < matrix[i].length; j ++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }


        TreeNode treeNode = digraph.transGraphToTree(digraph.getNodeByNum(0));

        System.out.println(treeNode);

        // 广度优先遍历
        List<TreeNode> treeNodes = new LinkedList<>();
        treeNodes.addAll(treeNode.getChildren());

        List<LinkedList<TreeNode>> way = new LinkedList<>();

        // 深度优先遍历
        digraph.depthFirstSearch(treeNode, way);

        System.out.println();
        for (LinkedList<TreeNode> linkedList : way) {
            System.out.println(linkedList.stream().map(x -> {return x.getParentToCurrentDistance() + " | " + x.getNode().getNum();}).collect(Collectors.toList()));
        }


        System.out.println();

        List<LinkedList<TreeNode>> way2 = new LinkedList<>();

        List<TreeNode> tempList = new LinkedList<>();
        while (!treeNodes.isEmpty()) {
            TreeNode firstNode = treeNodes.remove(0);
            if (firstNode.getChildren().size() == 0) {
                LinkedList<TreeNode> treeNodeLinkedList = new LinkedList<>();
                TreeNode currentNode = firstNode;
                while (currentNode != null) {
                    treeNodeLinkedList.add(0, currentNode);
                    currentNode = currentNode.getParent();
                }
                way2.add(treeNodeLinkedList);
            }
            tempList.addAll(firstNode.getChildren());
            System.out.print(firstNode.getNode().getNum() + "\t");
            if (treeNodes.isEmpty()) {
                treeNodes.addAll(tempList);
                tempList.clear();
            }
        }
        System.out.println();

        for (LinkedList<TreeNode> linkedList : way2) {
            System.out.println(linkedList.stream().map(x -> {return x.getParentToCurrentDistance() + " | " + x.getNode().getNum();}).collect(Collectors.toList()));
        }


    }

}
