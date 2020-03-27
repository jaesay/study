import java.util.*;

//-9 -9 -9  1 1 1
//        0 -9  0  4 3 2
//        -9 -9 -9  1 2 3
//        0  0  8  6 6 0
//        0  0  0 -2 0 0
//        0  0  1  2 4 0
public class Solution_2D_Array {

    // Complete the hourglassSum function below.
    static int hourglassSum(int[][] arr) {
        int[] nx = {0, 0, 1, 2, 2, 2};
        int[] ny = {1, 2, 1, 0, 1, 2};
        int row = arr.length;
        int col = arr[0].length;
        int answer = -1000;

        for (int i=0; i<row-2; i++) {
            for (int j=0; j<col-2; j++) {
                int sum = arr[i][j];
                for (int k=0; k<6; k++) {
                    sum += arr[i+nx[k]][j+ny[k]];
                }
                if (sum > answer) answer = sum;
            }
        }

        return answer;
    }

    public static void main(String args[]) {

        int answer = hourglassSum(new int[][] {{-9, -9, -9, 1, 1, 1}, {0, -9,  0,  4, 3, 2}, {-9, -9, -9, 1, 2, 3}, {0, 0, 8, 6, 6, 0}, {0, 0, 0, -2, 0, 0}, {0, 0, 1, 2, 4, 0}});
        System.out.println(answer);
    }
}