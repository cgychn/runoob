package com.graph;

import java.util.LinkedList;

public class Undigraph {

    public class MainNode {
        Digraph.Node node;
        LinkedList<NodeWithDistance> linkedList;

        public Digraph.Node getNode() {
            return node;
        }

        public void setNode(Digraph.Node node) {
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
        Node node1;
        Node node2;

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public Node getNode1() {
            return node1;
        }

        public void setNode1(Node node1) {
            this.node1 = node1;
        }

        public Node getNode2() {
            return node2;
        }

        public void setNode2(Node node2) {
            this.node2 = node2;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "distance=" + distance +
                    ", node1=" + node1 +
                    ", node2=" + node2 +
                    '}';
        }
    }

}
