import java.util.*;

public class Main15654 {

    private static boolean[] check;
    private static int[] nums;
    private static int[] answer;

    private static void go(int index, int n, int m) {

        if (index == m) {
            for (int i=0; i<m; i++) {
                if (i < m-1) System.out.print(answer[i] + " ");
                else System.out.println(answer[i]);
            }
            return;
        }

        for (int i=0; i<n; i++) {
            if (check[i]) continue;
            check[i] = true;
            answer[index] = nums[i];
            go(index+1, n, m);
            check[i] = false;
        }

    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        nums = new int[n];
        check = new boolean[n];
        answer = new int[m];

        for (int i=0; i<n; i++) nums[i] = sc.nextInt();

        Arrays.sort(nums);
        go(0, n, m);
    }
}