import java.util.*;

// bridge_length	weight	truck_weights	return
// 2	            10	    [7,4,5,6]	    8
public class Solution42583 {

    public class BridgeDto {
        private int truck_weight;
        private int time;

        public BridgeDto(int truck_weight, int time) {
            this.truck_weight = truck_weight;
            this.time = time;
        }

        public int getTruck_weight() {
            return truck_weight;
        }

        public int getTime() {
            return time;
        }
    }

    public int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<BridgeDto> q = new LinkedList<>();

        int t = 0;
        int i = 0;

        do {
            t++;
            int weightSum = 0;

            BridgeDto currentTruck = q.peek();
            if (currentTruck != null && t == currentTruck.getTime() + bridge_length) {
                q.remove();
            }

            for (BridgeDto bridgeDto : q) {
                weightSum += bridgeDto.getTruck_weight();
            }

            if (i < truck_weights.length && weightSum + truck_weights[i] <= weight) {
                q.add(new BridgeDto(truck_weights[i++], t));
            }

        } while (!q.isEmpty());


        return t;
    }


    public static void main(String args[]) {
        int answer = new Solution42583().solution(2, 10, new int[]{7, 4, 5, 6});

        System.out.println(answer);

    }
}