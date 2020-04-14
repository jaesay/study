import java.util.Scanner;

public class SolutionDay20 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];
        for(int a_i=0; a_i < n; a_i++){
            a[a_i] = in.nextInt();
        }

        int totalSwapNum = 0;

        for (int i=0; i<n; i++) {
            int numberOfSwaps = 0;
            for (int j=0; j<n-1; j++) {
                if (a[j] > a[j+1]) {
                    swap(a, j, j+1);
                    numberOfSwaps++;
                }
            }

            if (numberOfSwaps == 0) {
                break;
            }
            totalSwapNum += numberOfSwaps;
        }

        System.out.println("Array is sorted in " + totalSwapNum + " swaps.");
        System.out.println("First Element: " + a[0]);
        System.out.println("Last Element: " + a[a.length-1]);
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}