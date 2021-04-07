package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * https://st-lab.tistory.com/194
 */
public class Main_1010_다리놓기 {
	static int[][] dp = new int[30][30];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());
		int[] arr = new int[T];
		for (int i = 0; i < T; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			arr[i] = combi(M, N);
		}
		for (int i = 0; i < T; i++) {
			System.out.println(arr[i]);
		}
	}

	static int combi(int n, int r) {
		// 이미 풀린 경우 바로 반환
		if (dp[n][r] > 0) {
			return dp[n][r];
		}
		// 2번 성질
		if (n == r || r == 0) {
			return dp[n][r] = 1;
		}
		// 1번 성질
		return dp[n][r] = combi(n - 1, r - 1) + combi(n - 1, r);
	}
}
