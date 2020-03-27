import java.util.*;

//-9 -9 -9  1 1 1
//        0 -9  0  4 3 2
//        -9 -9 -9  1 2 3
//        0  0  8  6 6 0
//        0  0  0 -2 0 0
//        0  0  1  2 4 0
public class Solution_Left_Rotation {

    public static void main(String args[]) {

        int n = 5, d = 2;
        int[] a = {1, 2, 3, 4, 5};

        for (int i=0; i<n; i++) {
            System.out.print(a[(i+d)%n] + " ");
        }

    }
}