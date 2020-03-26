import java.util.*;

public class Main15650 {

    public static int[] a = new int[10];

    public static void go(int index, int selected, int n, int m) {
        if (selected == m) {
            for (int i=0; i<m; i++) {
                System.out.print(a[i] + " ");
            }
            System.out.println();
            return;
        }
        if (index > n) return;
        a[selected] = index;
        go(index+1, selected+1, n, m);
        a[selected] = 0;
        go(index+1, selected, n, m);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        go(1, 0, n, m);
    }
}