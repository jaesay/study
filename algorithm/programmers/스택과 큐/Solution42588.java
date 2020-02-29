import java.util.*;

// https://programmers.co.kr/learn/courses/30/lessons/42588
public class Solution42588 {

    public int[] solution(int[] heights) {
        int[] answer = new int[heights.length];

        for (int i=heights.length-1; i>0; i--) {
            for (int j = i-1; j>=0; j--) {
                if (heights[j] > heights[i]) {
                    answer[i] = j+1;
                    break;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {

        int[] answer = new Solution42588().solution(new int[]{6, 9, 5, 7, 4});

        System.out.println(Arrays.toString(answer));
    }
}
