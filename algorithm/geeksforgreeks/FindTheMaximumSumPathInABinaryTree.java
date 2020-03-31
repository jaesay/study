import java.util.*;

// https://www.geeksforgeeks.org/find-the-maximum-sum-path-in-a-binary-tree/
public class FindTheMaximumSumPathInABinaryTree {

    public class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getLeft() {
            return left;
        }

        public int getData() {
            return data;
        }

        public Node getRight() {
            return right;
        }

        public void setData(int data) {
            this.data = data;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }

    public static void main(String[] args) {
        FindTheMaximumSumPathInABinaryTree findTheMaximumSumPathInABinaryTree = new FindTheMaximumSumPathInABinaryTree();
        Node root = findTheMaximumSumPathInABinaryTree.createTree();
        List<Integer> distances = new ArrayList<>();
        go(0, root, distances);

        for (int distance : distances) {
            System.out.println(distance);
        }
    }

    private static void go(int d, Node node, List<Integer> distances) {
        d += node.data;
        if (node.left == null && node.right == null) {
            distances.add(d);
            return;
        }

        if (node.left != null) go(d, node.left, distances);
        if (node.right != null) go(d, node.right, distances);
    }

    private Node createTree() {
        Node root = new Node(10);
        root.left = new Node(5);
        root.left.right = new Node(2);
        root.right = new Node(5);
        root.right.right = new Node(1);
        root.right.right.left = new Node(-1);

        return root;
    }

}