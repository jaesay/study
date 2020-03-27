import java.util.*;

public class Solution_Arrays {

    // Complete the reverseArray function below.
    public static int[] reverseArray(int[] a) {
        int[] b = new int[a.length];
        for (int i=0; i<a.length; i++) {
            b[i] = a[a.length-i-1];
        }
        return b;
    }

    public static void main(String args[]) {

        int[] answer = reverseArray(new int[]{1, 2, 3, 4});
        System.out.println(Arrays.toString(answer));
    }
}