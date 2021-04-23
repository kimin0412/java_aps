package Algorithm.src.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 50분컷..? DP로 풀어보기
 */
public class Solution_1952_수영장 {
    static int[] pay;
    static int[] plan;
    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TC; tc++) {
            pay = new int[4];
            plan = new int[12];
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < 4; i++) {
                pay[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < 12; i++) {
                plan[i] = Integer.parseInt(st.nextToken());
            }

            ans = pay[3];
            dfs(0, 0);
            System.out.println("#" + tc + " " + ans);
        }
    }

    private static void dfs(int mon, int sum) {
        if (mon == 12) {
            ans = Math.min(ans, sum);
            return;
        }

        if (plan[mon] == 0) {
            dfs(mon + 1, sum);
        } else {
            dfs(mon + 1, sum + (pay[0] * plan[mon])); // 하루치로 계산하기
            dfs(mon + 1, sum + pay[1]);

            if (mon == 10) // 3개월권 11월부터 시작
                dfs(mon + 2, sum + pay[2]);
            else if (mon == 11) // 3개월권 12월부터 시작
                dfs(mon + 1, sum + pay[2]);
            else {
                dfs(mon + 3, sum + pay[2]);

            }
        }
    }
}
