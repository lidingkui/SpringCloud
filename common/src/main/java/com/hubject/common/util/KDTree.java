package com.hubject.common.util;

import com.hubject.common.dto.bff.StationPositionDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

// 单例类
@Data
public class KDTree {
    private int k;
    private Node root;

    @Data
    private static class Node {
        StationPositionDTO data;
        Node left;
        Node right;
        int split; // 切分的维度

        Node(StationPositionDTO data, Node left, Node right, int split) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.split = split;
        }
    }

    // 大根堆
    private class Heap {
        List<Node> elements;
        int size;
        int capacity;
        double[] target;

        Heap(int capacity, double[] target) {
            this.capacity = capacity;
            this.target = target;
            elements = new ArrayList<>(capacity);
        }

        void siftUp() {
            int i = size;
            while (i != 0 && distance(target, elements.get(i).data.getPoints()) > distance(target, elements.get((i - 1) / 2).data.getPoints())) {
                Node tmp = elements.get(i);
                elements.set(i, elements.get((i - 1) / 2));
                elements.set((i - 1) / 2, tmp);
                i = (i - 1) / 2;
            }
        }

        void siftDown() {
            int i = 0;
            while (i < size) {
                int left = i << 1 | 1;
                int right = left + 1;
                int longest = i;
                if (left < size && distance(target, elements.get(longest).data.getPoints()) < distance(target, elements.get(left).data.getPoints()))
                    longest = left;
                if (right < size && distance(target, elements.get(longest).data.getPoints()) < distance(target, elements.get(right).data.getPoints()))
                    longest = right;
                if (longest == i) return;
                Node tmp = elements.get(i);
                elements.set(i, elements.get(longest));
                elements.set(longest, tmp);
                i = longest;
            }
        }

        void insert(Node node) {
            if (size < capacity) {
                elements.add(node);
                siftUp();
                size++;
            } else {
                double a = distance(target, elements.get(0).data.getPoints());
                double b = distance(target, node.data.getPoints());
                if (b <= a) {
                    elements.set(0, node);
                    siftDown();
                }
            }
        }
    }

    // 4 serialize
    private KDTree() {
        super();
    }

    public KDTree(int k, List<StationPositionDTO> list) {
        this.k = k;
        this.root = buildTree(list, 0, list.size() - 1);
    }

    //  范围查询
    public List<String> rangePoints(double[] low, double[] height) {
        if (low == null || height == null || low.length != k || height.length != k)
            throw new RuntimeException("维度无法对齐!");
        List<String> ret = new ArrayList<>();
        rangePoints(ret, this.root, low, height);
        return ret;
    }

    private void rangePoints(List<String> ret, Node node, double[] low, double[] height) {
        if (node == null) return;
        double[] points = node.data.getPoints();

        boolean contains = true;
        for (int i = 0; i < k; i++) {
            if (points[i] < low[i] || points[i] > height[i]) {
                contains = false;
                break;
            }
        }
        if (contains) ret.add(node.data.getId());
        int ci = node.split;
        if (points[ci] < low[ci]) {
            rangePoints(ret, node.right, low, height);
        } else if (points[ci] > height[ci]) {
            rangePoints(ret, node.left, low, height);
        } else {
            rangePoints(ret, node.right, low, height);
            rangePoints(ret, node.left, low, height);
        }
    }


    public List<String> nearestPoint(double[] targetPoint, int n) {
        Heap heap = new Heap(n, targetPoint);
        nearest(targetPoint, this.root, heap, new HashSet<>());
        return heap.elements.stream().map(o -> o.data.getId()).collect(Collectors.toList());
    }

    private void nearest(double[] targetPoint, Node node, Heap heap, Set<Node> vis) {
        if (node == null || vis.contains(node)) return;
        vis.add(node);
        int ci = node.split;
        if (node.data.getPoints()[ci] > targetPoint[ci]) { // 左子树
            nearest(targetPoint, node.left, heap, vis);
            heap.insert(node);
            if (heap.size < heap.capacity
                    || Math.abs(targetPoint[ci] - node.data.getPoints()[ci])
                    < distance(targetPoint, heap.elements.get(0).data.getPoints())) {
                nearest(targetPoint, node.right, heap, vis);
            }
        } else if (node.data.getPoints()[ci] < targetPoint[ci]) {// 右子树
            nearest(targetPoint, node.right, heap, vis);
            heap.insert(node);
            if (heap.size < heap.capacity
                    || Math.abs(targetPoint[ci] - node.data.getPoints()[ci])
                    < distance(targetPoint, heap.elements.get(0).data.getPoints())) { // 向左子树寻找解
                nearest(targetPoint, node.left, heap, vis);
            }
        } else {
            nearest(targetPoint, node.left, heap, vis);
            nearest(targetPoint, node.right, heap, vis);
            heap.insert(node);
        }
    }

    // 构建KD TREE
    private Node buildTree(List<StationPositionDTO> list, int l, int r) {
        if (l > r) return null;
        int mid = l + (r - l) / 2;
        int ci = getSplitIndex(list, l, r);
        partition(list, l, r, ci, mid);
        return new Node(list.get(mid),
                buildTree(list, l, mid - 1),
                buildTree(list, mid + 1, r),
                ci);
    }


    private void partition(List<StationPositionDTO> list, int l, int r, int ci, int k) {
        if (l > r) return;
        double pivot = list.get(new Random().nextInt(r - l + 1) + l).getPoints()[ci];
        int pl = l - 1, pr = r + 1, i = l;
        while (i != pr) {
            StationPositionDTO tmp = list.get(i);
            if (list.get(i).getPoints()[ci] < pivot) {
                list.set(i++, list.get(++pl));
                list.set(pl, tmp);
            } else if (list.get(i).getPoints()[ci] > pivot) {
                list.set(i, list.get(--pr));
                list.set(pr, tmp);
            } else i++;
        }
        if (k < pl) {
            partition(list, l, pl, ci, k);
        } else if (k > pr) {
            partition(list, pr, r, ci, k);
        }
    }

    // 获取方差最大的维度
    private int getSplitIndex(List<StationPositionDTO> list, int l, int r) {
        double maxVariance = 0;
        int index = 0;
        for (int i = 0; i < k; i++) {
            double avg = .00000000000001;
            double variance = 0;
            for (int j = l; j <= r; j++) {
                avg += list.get(j).getPoints()[i];
            }
            avg /= r - l + 1;
            for (int j = l; j <= r; j++) {
                variance += Math.pow(list.get(j).getPoints()[i] - avg, 2) / avg;
            }

            if (variance > maxVariance) {
                maxVariance = variance;
                index = i;
            }
        }
        return index;
    }

    // 欧拉距离
    private double distance(double[] a, double[] b) {
        double distance = 0;
        for (int i = 0; i < a.length; i++) {
            distance += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(distance);
    }

    // 序列化&反序列二叉树
    public static String serialize(int k, Node root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(k).append("@");
        preOrder(sb, root);
        return sb.toString();
    }

    public static KDTree deserialize(String str) {
        if (str == null || str.equals("")) return null;
        String[] split = str.split("@");
        int k = Integer.parseInt(split[0]);
        String[] nodesArray = split[1].split("#");
        LinkedList<String> list = new LinkedList<>();
        for (String nodeStr : nodesArray) {
            if (StringUtils.isNotBlank(nodeStr)) list.addLast(nodeStr); // 去掉第一个空
        }
        Node root = buildKDTreeBySerializeTemplate(list);
        KDTree kdTree = new KDTree();
        kdTree.setK(k);
        kdTree.setRoot(root);
        return kdTree;
    }

    private static Node buildKDTreeBySerializeTemplate(LinkedList<String> nodesArray) {
        if (nodesArray.isEmpty()) return null;
        String first = nodesArray.removeFirst();
        if (first.equals("N")) return null;
        String[] data = first.split(",");
        String id = data[0];
        int split = Integer.parseInt(data[1]);
        double[] points = {Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4])};

        return new Node(new StationPositionDTO(points, id),
                buildKDTreeBySerializeTemplate(nodesArray),
                buildKDTreeBySerializeTemplate(nodesArray),
                split);
    }

    private static void preOrder(StringBuilder sb, Node node) {
        if (node == null) {
            sb.append("#N");
            return;
        }
        StationPositionDTO data = node.getData();
        double[] points = data.getPoints();
        String id = data.getId();
        int split = node.getSplit();

        sb.append("#");
        sb.append(id).append(",");
        sb.append(split).append(",");
        sb.append(points[0]).append(",").append(points[1]).append(",").append(points[2]);

        preOrder(sb, node.left);
        preOrder(sb, node.right);
    }


}
