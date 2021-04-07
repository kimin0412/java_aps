package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * https://life-with-coding.tistory.com/316
 * https://huiung.tistory.com/120
 * https://hyunjiishailey.tistory.com/m/271?category=381511
 */
public class Main_7579_앱 {
	static int N, M;
	static App[] apps;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		apps = new App[N + 1];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i < N + 1; i++) {
			int b = Integer.parseInt(st.nextToken());
			apps[i] = new App(b, 0);
		}

		int hap = 0;
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i < N + 1; i++) {
			int c = Integer.parseInt(st.nextToken());
			apps[i].c = c;
			hap += c;
		}
		System.out.println(hap);
		int[][] dp = new int[N + 1][hap + 1];

		int min = Integer.MAX_VALUE;
		for (int i = 1; i < N + 1; i++) {
			for (int j = 1; j < hap + 1; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j >= apps[i].c)
					dp[i][j] = Math.max(dp[i - 1][j - apps[i].c] + apps[i].b, dp[i - 1][j]);
				if (dp[i][j] >= M)
					min = Math.min(min, j);
			}
		}
		
		for (int[] d : dp) {
			System.out.println(Arrays.toString(d));
		}
		System.out.println(min);
	}

	public static class App {
		public int b; // 바이트
		public int c; // 비용

		public App(int b, int c) {
			super();
			this.b = b;
			this.c = c;
		}

	}
}