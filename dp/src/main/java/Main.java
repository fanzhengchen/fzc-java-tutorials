import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-15
 * Time: 6:03 PM
 * poj 2411
 * @author: MarkFan
 * @since v1.0.0
 */
public class Main {

    public static void main(String[] args) {
        new Main().main();
    }

    boolean isOkState(int n, int states) {
        int zeros = 0;
        for (int i = 0; i < n; ++i) {
            if ((states >> i & 1) == 1) {
                if ((zeros & 1) == 1) {
                    return false;
                }
                zeros = 0;
            } else {
                ++zeros;
            }
        }
        return (zeros & 1) == 0;
    }

    void main() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            long ans = 0;
            if (n > m) {
                ans = run(m, n);
            } else {
                ans = run(n, m);
            }
            System.out.println(ans);
        }
    }

    long run(int n, int m) {
        if (((n * m) & 1) == 1) {
            return 0;
        }
        int states = 1 << n;
        long[][] dp = new long[m][1 << n];
        boolean[] ok = new boolean[1 << n];
        for (int state = 0; state < states; ++state) {
            if (isOkState(n, state)) {
                dp[0][state] = 1;
                ok[state] = true;

            }
        }

        for (int i = 1; i < m; ++i) {
            for (int pre = 0; pre < states; ++pre) {
                for (int cur = 0; cur < states; ++cur) {
                    int t = cur & pre;
                    if (ok[cur] && t == pre) {
                        int next = cur ^ pre;
                        dp[i][next] += dp[i - 1][pre];

                    }
                }
            }
        }
        return dp[m - 1][0];

    }

}
