import java.util.*;

// 4 6
// a t c i s w

public class Main1759 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int L = sc.nextInt();
        int C = sc.nextInt();
        String[] alphas = new String[C];

        for (int i=0; i<C; i++) {
            alphas[i] = sc.next();
        }

        Arrays.sort(alphas);

        go(alphas, "", 0, L);

    }

    private static void go(String[] alphas, String password, int i, int l) {

        if (password.length() == l) {
            if (check(password)) {
                System.out.println(password);
            }
        }

        if (i == alphas.length) return;

        go(alphas, password + alphas[i], i+1, l);
        go(alphas, password, i+1, l);
    }

    private static boolean check(String password) {
        int ja = 0;
        int mo = 0;
        for (char x : password.toCharArray()) {
            if (x == 'a' || x == 'e' || x == 'i' || x == 'o' || x == 'u') {
                mo += 1;
            } else {
                ja += 1;
            }
        }
        return ja >= 2 && mo >= 1;
    }

}