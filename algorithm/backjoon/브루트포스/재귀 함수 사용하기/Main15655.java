import java.util.*;

public class Main15655 {

    private static int[] nums;
    private static int[] answer;

    private static void go(int index, int count,int n, int m) {

        if (count == m) {
            for (int i=0; i<m; i++) {
                if (i < m-1) System.out.print(answer[i] + " ");
                else System.out.println(answer[i]);
            }
            return;
        }

        if (index == n) return;

        answer[count] = nums[index];
        go(index+1, count+1, n, m);
        go(index+1, count, n, m);
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        nums = new int[n];
        answer = new int[m];

        for (int i=0; i<n; i++) nums[i] = sc.nextInt();

        Arrays.sort(nums);
        go(0, 0, n, m);
    }
}