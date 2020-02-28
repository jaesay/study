import java.util.*;

// https://programmers.co.kr/learn/courses/30/lessons/42576
public class Solution42576 {

    public String solution(String[] participant, String[] completion) {

        String answer = "";
        HashMap<String, Integer> hashMap = new HashMap<>();

        for (String player : participant) hashMap.put(player, hashMap.getOrDefault(player, 0) + 1);
        for (String player : completion) hashMap.put(player, hashMap.get(player) - 1);

        for (String key : hashMap.keySet()) {
            if (hashMap.get(key) != 0) {
                answer = key;
            }
        }

        return answer;
    }

    public static void main(String[] args) {

        String answer = new Solution42576().solution(new String[] {"leo", "kiki", "eden"}, new String[] {"eden", "kiki"});

        System.out.println(answer);
    }
}
