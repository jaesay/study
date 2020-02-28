import java.util.*;

// https://programmers.co.kr/learn/courses/30/lessons/43162
public class Solution43162 {

    public int solution(int n, int[][] computers) {
        int answer = 0;

        boolean[] check = new boolean[n];
        for (int i=0; i<n; i++) {
            if (check[i] == false) {
                dfs(computers, check, i);
                answer++;
            }
        }

        return answer;
    }

    private void dfs(int[][] computers, boolean[] check, int i) {
        check[i] = true;
        for (int j=0; j<computers.length; j++) {
            if (computers[i][j] == 1 && check[j] == false) {
                dfs(computers, check, j);
            }
        }
    }

    public static void main(String[] args) {

        int answer = new Solution43162().solution(3, new int[][] {{1,1,0},{1,1,0},{0,0,1}});

        System.out.println(answer);
    }
}
