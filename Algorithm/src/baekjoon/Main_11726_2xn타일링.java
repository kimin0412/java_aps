package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_11726_2xn타일링 {
	static int[][] dp = new int[30][30];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		long[] DP = new long[n + 1];

		DP[0] = 1;
		DP[1] = 1;

		for (int i = 2; i <= n; i++) {
			DP[i] = (DP[i - 1] + DP[i - 2]) % 10007;
		}

		System.out.println(DP[n]);
	}
}
