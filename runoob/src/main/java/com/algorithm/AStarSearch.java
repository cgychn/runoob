package com.algorithm;

import java.util.*;
import java.util.stream.Collectors;

public class AStarSearch {

    static Tile startTile;
    static Tile endTile;

    // 待处理点集合
    static List<Tile> openList = new ArrayList<>();
    // 处理完毕的点集合
    static List<Tile> closeList = new ArrayList<>();

    /**
     * 瓦片实体
     */
    class Tile {
        // 瓦片的编号，求最短路径时肯能用到
        int num;
        int xIndex;
        int yIndex;
        boolean isBlocked = false;
        Tile parentTile;
        int distanceToEnd;
        int distanceToStart;
        boolean onWay = false;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getDistanceToEnd() {
            return distanceToEnd;
        }

        public void setDistanceToEnd(int distanceToEnd) {
            this.distanceToEnd = distanceToEnd;
        }

        public int getDistanceToStart() {
            return distanceToStart;
        }

        public void setDistanceToStart(int distanceToStart) {
            this.distanceToStart = distanceToStart;
        }

        public Tile getParentTile() {
            return parentTile;
        }

        public void setParentTile(Tile parentTile) {
            this.parentTile = parentTile;
        }

        public int getXIndex() {
            return xIndex;
        }

        public void setXIndex(int xIndex) {
            this.xIndex = xIndex;
        }

        public int getYIndex() {
            return yIndex;
        }

        public void setYIndex(int yIndex) {
            this.yIndex = yIndex;
        }

        public boolean getIsBlocked() {
            return isBlocked;
        }

        public void setIsBlocked(boolean isBlocked) {
            this.isBlocked = isBlocked;
        }

        @Override
        public String toString() {
            return "[" + (xIndex + " | " + yIndex + ", ") + (isBlocked ? (1 + "") : (0+ "")) + ", " + num + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return xIndex == tile.xIndex &&
                    yIndex == tile.yIndex;
        }

        public boolean isOnWay() {
            return onWay;
        }

        public void setOnWay(boolean onWay) {
            this.onWay = onWay;
        }

        @Override
        public int hashCode() {
            return Objects.hash(xIndex, yIndex);
        }

        private int getF () {
            return getDistanceToEnd() + getDistanceToStart();
        }
    }

    /**
     * 地图（实际是一个二维数组）
     */
    static List<List<Tile>> map = new ArrayList<>();

    public static Tile getFMinimalTile () {
        Tile minTile = openList.get(0);
        for (Tile tile : openList) {
            if (tile.getF() < minTile.getF()) {
                minTile = tile;
            }
        }
        return minTile;
    }



    /**
     * 寻路
     * @return
     */
    public static List<Tile> findWay () {
        // 先将开始节点添加到closeList
        openList.add(startTile);
        Tile currentTile;
        // 一直循环，直到endTile出现在openList中
        while (!openList.contains(endTile)) {
            if (openList.size() == 0) {
                return new ArrayList<>();
            }
            Tile minTile = getFMinimalTile();
            System.out.println(minTile);
            openList.remove(minTile);
            closeList.add(minTile);
            currentTile = minTile;

            // 计算当前节点的上下左右节点
            Tile left = getLeftTile(currentTile);
            Tile right = getRightTile(currentTile);
            Tile top = getTopTile(currentTile);
            Tile bottom = getBottomTile(currentTile);

            if (left != null) {
                // 判断是否在openList中,在的话重新计算F值
                if (!openList.contains(left)) {
                    left.setDistanceToStart(getTileToStartTileDistance(left));
                    openList.add(left);
                } else {
                    // 更新F值
                    int leftG = left.getDistanceToStart();
                    int currentG = currentTile.getDistanceToStart() + 1;
                    if (currentG < leftG) {
                        left.setDistanceToStart(currentG);
                        left.setParentTile(currentTile);
                    }
                }

//                if (openList.contains(left)) {
//                    left.setParentTile(currentTile);
//                }
            }
            if (right != null) {
                openList.add(right);
                if (!openList.contains(right)) {
                    right.setDistanceToStart(getTileToStartTileDistance(right));
                    openList.add(right);
                } else {
                    // 更新F值
                    int rightG = right.getDistanceToStart();
                    int currentG = currentTile.getDistanceToStart() + 1;
                    if (currentG < rightG) {
                        right.setDistanceToStart(currentG);
                        right.setParentTile(currentTile);
                    }
                }
//                if (openList.contains(right)) {
//                    right.setParentTile(currentTile);
//                }
            }
            if (top != null) {
                if (!openList.contains(top)) {
                    top.setDistanceToStart(getTileToStartTileDistance(top));
                    openList.add(top);
                } else {
                    // 更新F值
                    int topG = top.getDistanceToStart();
                    int currentG = currentTile.getDistanceToStart() + 1;
                    if (currentG < topG) {
                        top.setDistanceToStart(currentG);
                        top.setParentTile(currentTile);
                    }
                }

//                if (openList.contains(top)) {
//                    top.setParentTile(currentTile);
//                }
            }
            if (bottom != null) {
                if (!openList.contains(bottom)) {
                    bottom.setDistanceToStart(getTileToStartTileDistance(bottom));
                    openList.add(bottom);
                } else {
                    int bottomG = bottom.getDistanceToStart();
                    int currentG = currentTile.getDistanceToStart() + 1;
                    if (currentG < bottomG) {
                        bottom.setDistanceToStart(currentG);
                        bottom.setParentTile(currentTile);
                    }
                }
//                if (openList.contains(bottom)) {
//                    bottom.setParentTile(currentTile);
//                }
            }

        }

        List<Tile> way = new ArrayList<>();

        currentTile = endTile;
        while (currentTile != null) {
            way.add(currentTile);
            currentTile.setOnWay(true);
            currentTile = currentTile.getParentTile();
        }

        // way可能不是最优的走法，在way中规划出n条ST到ED的路，选取最短的一条
        // 反转way（从起点到终点）
        Collections.reverse(way);
        int i = 0;
        for (Tile tile : way) {
            tile.setNum(i);
            i ++;
        }








        // 清空两个列表
        openList.clear();
        closeList.clear();

        // 求最短路径
        int[][] tileTable = new int[way.size()][way.size()];
        List<Tile> handledList = new ArrayList<>();
        for (Tile tile : way) {
            // 前后左后相邻的4个端点
            Tile left = getLeftTile(tile);
            Tile right = getRightTile(tile);
            Tile top = getTopTile(tile);
            Tile bottom = getBottomTile(tile);
            // 加入到已处理的列表中，防止下一个节点又重复遍历
            handledList.add(tile);
            if (left != null) {
                // 说明tile节点到left可直达
                if (way.contains(left) && !handledList.contains(left)) {
                    tileTable[tile.getNum()][left.getNum()] = 1;
                }
            }
            if (right != null) {
                // 说明tile节点到left可直达
                if (way.contains(right) && !handledList.contains(right)) {
                    tileTable[tile.getNum()][right.getNum()] = 1;
                }
            }
            if (top != null) {
                // 说明tile节点到top可直达
                if (way.contains(top) && !handledList.contains(top)) {
                    tileTable[tile.getNum()][top.getNum()] = 1;
                }
            }
            if (bottom != null) {
                // 说明tile节点到bottom可直达
                if (way.contains(bottom) && !handledList.contains(bottom)) {
                    tileTable[tile.getNum()][bottom.getNum()] = 1;
                }
            }
        }


        // 从start节点计算所有到end的通路，并计算其最短路径
//        getBestWay(tileTable, way);

        map.forEach(line -> {
            line.forEach(tile -> {
                if (tile == startTile) {
                    System.out.print("ST\t");
                } else if (tile == endTile) {
                    System.out.print("ED\t");
                } else {
                    System.out.print(tile.isBlocked ? "||\t" : tile.isOnWay() ? "()\t" : "::\t");
                }
            });
            System.out.println();
        });

        return way;
    }

    public static void getBestWay (int[][] tileTable, List<Tile> way) {
        List<Tile> S = new ArrayList<>();
        List<Tile> T = new ArrayList<>();
        S.add(startTile);
        T.addAll(way);
        T.remove(startTile);
        HashMap<Integer, Integer> lastRes = new HashMap<>();
        HashMap<Integer, String> nodeMinWay = new HashMap<>();
        int flag = 0;
        while (T.size() > 0) {
            HashMap<Integer, Integer> currentRes = new HashMap<>();
            if (flag != 0) {
                Map.Entry<Integer, Integer> minEntry = getMinEntry(lastRes, S);
                int minNodeNum = minEntry.getKey();
                int minNodeValue = minEntry.getValue();
                Tile minNode = getNodeByNum(way, minNodeNum);
                for (int i = 1; i < way.size(); i ++) {
                    if (S.stream().map(x -> {return x.getNum();}).collect(Collectors.toList()).contains(i)) {
                        // i已经处理过，不再处理
                        currentRes.put(i, lastRes.get(i));
                    } else {
                        if (tileTable[minNodeNum][i] != 0) {
                            // 当前节点到i节点可达，
                            int currentValue = tileTable[minNodeNum][i] + minNodeValue;
                            if (currentValue < lastRes.get(i)) {
                                nodeMinWay.put(i, nodeMinWay.get(minNodeNum) + "," + i);
                                currentRes.put(i, currentValue);
                            } else {
                                currentRes.put(i, lastRes.get(i));
                            }
                        } else {
                            // 当前节点到i节点不可达
                            currentRes.put(i, lastRes.get(i));
                        }
                    }
                }
                T.remove(minNode);
                S.add(minNode);

            } else {
                for (int i = 1; i < way.size(); i ++) {
                    currentRes.put(i, tileTable[startTile.getNum()][i] == 0 ? Integer.MAX_VALUE : tileTable[startTile.getNum()][i]);
                    nodeMinWay.put(i, "0," + i);
                }
                flag = 1;
            }
//            System.out.println("currentRes : " + currentRes);
            lastRes = currentRes;
//            System.out.println("nodeMinWay : " + nodeMinWay + "\n");
        }
        System.out.println(lastRes);
        System.out.println(nodeMinWay);
        System.out.println(S);

        for (Tile tile : way) {
            if (!Arrays.stream(nodeMinWay.get(endTile.getNum()).split(",")).collect(Collectors.toList()).contains(tile.getNum() + "")) {
                tile.setOnWay(false);
            }
        }
    }


    public static Map.Entry<Integer, Integer> getMinEntry (HashMap<Integer, Integer> hashMap, List<Tile> exceptList) {
        int minValue = Integer.MAX_VALUE;
        Map.Entry<Integer, Integer> minEntry = null;
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            // 排除空的和已处理的
            if (entry.getValue() == null
                    || exceptList.stream().map(x -> {return x.getNum();}).collect(Collectors.toList()).contains(entry.getKey())) {
                continue;
            }
            if (entry.getValue() < minValue) {
                minValue = entry.getValue();
                minEntry = entry;
            }
        }
        return minEntry;
    }

    /**
     * 获取指定编号的节点
     * @param tiles
     * @param num
     * @return
     */
    public static Tile getNodeByNum (List<Tile> tiles, int num) {
        for (Tile tile : tiles) {
            if (tile.getNum() == num) {
                return tile;
            }
        }
        return null;
    }

    /**
     * 获取tile的左tile
     * @param tile
     * @return
     */
    public static Tile getLeftTile (Tile tile) {
        int tileXIndex = tile.getXIndex();
        int tileYIndex = tile.getYIndex();
        int leftTileXIndex = tileXIndex - 1;
        int leftTileYIndex = tileYIndex;
        if (leftTileXIndex < 0) {
            return null;
        }
        Tile leftTile = map.get(leftTileYIndex).get(leftTileXIndex);
        if (closeList.contains(leftTile) || leftTile.isBlocked) {
            return null;
        }
        leftTile.setParentTile(tile);
//        leftTile.setDistanceToStart(getTileToStartTileDistance(leftTile));
        leftTile.setDistanceToEnd(getTileToEndTileDistance(leftTile));
        return leftTile;
    }

    /**
     * 获取tile的右tile
     * @param tile
     * @return
     */
    public static Tile getRightTile (Tile tile) {
        int tileXIndex = tile.getXIndex();
        int tileYIndex = tile.getYIndex();
        int rightTileXIndex = tileXIndex + 1;
        int rightTileYIndex = tileYIndex;
        if (rightTileXIndex >= map.get(rightTileYIndex).size()) {
            return null;
        }
        Tile rightTile = map.get(rightTileYIndex).get(rightTileXIndex);
        if (closeList.contains(rightTile) || rightTile.isBlocked) {
            return null;
        }
        rightTile.setParentTile(tile);
//        rightTile.setDistanceToStart(getTileToStartTileDistance(rightTile));
        rightTile.setDistanceToEnd(getTileToEndTileDistance(rightTile));
        return rightTile;
    }

    /**
     * 获取tile的上面的tile
     * @param tile
     * @return
     */
    public static Tile getTopTile (Tile tile) {
        int tileXIndex = tile.getXIndex();
        int tileYIndex = tile.getYIndex();
        int topTileXIndex = tileXIndex;
        int topTileYIndex = tileYIndex - 1;
        if (topTileYIndex < 0) {
            return null;
        }
        Tile topTile = map.get(topTileYIndex).get(topTileXIndex);
        if (closeList.contains(topTile) || topTile.isBlocked) {
            return null;
        }
        topTile.setParentTile(tile);
//        topTile.setDistanceToStart(getTileToStartTileDistance(topTile));
        topTile.setDistanceToEnd(getTileToEndTileDistance(topTile));
        return topTile;
    }

    /**
     * 获取tile的下面的tile
     * @param tile
     * @return
     */
    public static Tile getBottomTile (Tile tile) {
        int tileXIndex = tile.getXIndex();
        int tileYIndex = tile.getYIndex();
        int bottomTileXIndex = tileXIndex;
        int bottomTileYIndex = tileYIndex + 1;
        if (bottomTileYIndex >= map.size()) {
            return null;
        }
        Tile bottomTile = map.get(bottomTileYIndex).get(bottomTileXIndex);
        if (closeList.contains(bottomTile) || bottomTile.isBlocked) {
            return null;
        }
        bottomTile.setParentTile(tile);
//        bottomTile.setDistanceToStart(getTileToStartTileDistance(bottomTile));
        bottomTile.setDistanceToEnd(getTileToEndTileDistance(bottomTile));
        return bottomTile;
    }


    public static int getTileToEndTileDistance (Tile tile) {
        int tileXIndex = tile.getXIndex();
        int tileYIndex = tile.getYIndex();
        int endTileXIndex = endTile.getXIndex();
        int endTileYIndex = endTile.getYIndex();
        return Math.abs((endTileXIndex - tileXIndex)) + Math.abs((endTileYIndex - tileYIndex));
    }
    public static int getTileToStartTileDistance (Tile tile) {
        int tileXIndex = tile.getXIndex();
        int tileYIndex = tile.getYIndex();
        int startTileXIndex = startTile.getXIndex();
        int startTileYIndex = startTile.getYIndex();
        return Math.abs((startTileXIndex - tileXIndex)) + Math.abs((startTileYIndex - tileYIndex));
    }


    public static void main(String[] args) {
        AStarSearch aStarSearch = new AStarSearch();

        /**
         * 填充地图
         */
        for (int j = 0; j < 20; j ++) {
            List<Tile> line = new ArrayList<>();
            for (int i = 0; i < 20; i ++) {
                Tile tile = aStarSearch.new Tile();
                // 设置横坐标
                tile.setXIndex(i);
                // 设置纵坐标
                tile.setYIndex(j);
                // 设置障碍
                if (j > 1 && j < 20 && i == 10) {
                    tile.setIsBlocked(true);
                }
                line.add(tile);
            }
            map.add(line);
        }

        // 初始化开始节点
        startTile = map.get(10).get(3);
        // 初始化结束节点
        endTile = map.get(17).get(19);
        long startTime = System.currentTimeMillis();
        findWay();
        System.out.println("time : " + (System.currentTimeMillis() - startTime));

    }

}
