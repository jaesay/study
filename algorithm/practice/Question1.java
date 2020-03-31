import java.util.*;

//This problem was asked by Facebook.
//
//        You have a large array with most of the elements as zero.
//
//        Use a more space-efficient data structure, SparseArray, that implements the same interface:
//
//        init(arr, size): initialize with the original large array and size.
//        set(i, val): updates index at i with val.
//        get(i): gets the value at index i.

public class Question1 {

    public class SparseArray {

        private Map<Integer, Integer> map = new HashMap<>();
        private int size = 0;

        public void init(int[] arr, int size) {

            if (arr.length != size) throw new IllegalArgumentException();

            for (int i=0; i<arr.length; i++) {
                if (arr[i] != 0) {
                    map.put(i, arr[i]);
                }
            }
            this.size = size;
        }

        public void set(int i, int val) {
            if (i < 0 && i >= this.size) throw new IndexOutOfBoundsException();

            map.put(i, val);
        }

        public int get(int i) {
            if (i < 0 && i >= this.size) throw new IndexOutOfBoundsException();

            if (map.containsKey(i)) return map.get(i);

            return 0;
        }
    }

    private void go() {

        SparseArray sparseArray = new SparseArray();
        int[] arr = new int[100];
        arr[10] = 123;
        arr[25] = 333;
        arr[67] = 644;
        sparseArray.init(arr, arr.length);
        sparseArray.set(11, 111);
        System.out.println(sparseArray.get(11));
        System.out.println(sparseArray.get(25));
    }

    public static void main(String[] args) {
        Question1 question1 = new Question1();

        question1.go();
    }


}