import java.util.*;

// https://programmers.co.kr/learn/courses/30/lessons/42576
public class Solution43165 {

    public int solution(int[] numbers, int target) {
        int answer = 0;
        answer = dfs(numbers, 0, 0, target);
        return answer;
    }

    private int dfs(int[] numbers, int i, int sum, int target) {
        if (i == numbers.length) {
            if (sum == target) {
                return 1;
            } else {
                return 0;
            }
        }

        return dfs(numbers, i+1, sum + numbers[i], target) + dfs(numbers, i+1, sum - numbers[i], target);
    }

    public static void main(String[] args) {

        int answer = new Solution43165().solution(new int[] {1, 1, 1, 1, 1}, 3);

        System.out.println(answer);
    }
}
