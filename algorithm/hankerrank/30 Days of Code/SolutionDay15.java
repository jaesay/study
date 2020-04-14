import java.io.*;
import java.util.*;

class Node {
    int data;
    Node next;
    Node(int d) {
        data = d;
        next = null;
    }
}

public class SolutionDay15 {

    public static  Node insert(Node head,int data) {

        Node newNode = new Node(data);

        Node leafNode = findLeafNode(head);
        if (leafNode == null) {
            head = newNode;
        } else {
            leafNode.next = newNode;
        }

        return head;
    }

    private static Node findLeafNode(Node head) {
        if (head == null) return null;
        if (head.next != null) return findLeafNode(head.next);

        return head;
    }

    public static void display(Node head) {
        Node start = head;
        while(start != null) {
            System.out.print(start.data + " ");
            start = start.next;
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Node head = null;
        int N = sc.nextInt();

        while(N-- > 0) {
            int ele = sc.nextInt();
            head = insert(head,ele);
        }
        display(head);
        sc.close();
    }
}
