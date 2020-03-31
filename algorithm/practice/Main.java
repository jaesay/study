import java.util.LinkedList;
import java.util.Queue;

//2 5
//1 0 5
//1 1 7
//1 0 3
//2 1 0
//2 1 1
public class Main {

    class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        Node root = main.createBinaryTree();
        System.out.println("===preorder===");
        main.preorder(root);
        System.out.println("\n");
        System.out.println("===inorder===");
        main.inorder(root);
        System.out.println("\n");
        System.out.println("===postorder===");
        main.postorder(root);
        System.out.println("\n");

    }

    private void preorder(Node root) {
        if (root == null) return;
        System.out.println(root.data);
        preorder(root.left);
        preorder(root.right);
    }

    private void inorder(Node root) {
        if (root == null) return;
        inorder(root.left);
        System.out.println(root.data);
        inorder(root.right);
    }

    private void postorder(Node root) {
        if (root == null) return;
        postorder(root.left);
        postorder(root.right);
        System.out.println(root.data);
    }

    private Node createBinaryTree() {
        Node root = new Node(10);
        root.left = new Node(5);
        root.right = new Node(5);
        root.left.right = new Node(2);
        root.right.right = new Node(1);
        root.right.right.left = new Node(-1);

        return root;
    }
}