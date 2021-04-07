package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
/*
 * https://st-lab.tistory.com/132
 * https://st-lab.tistory.com/135
 */
public class Main_2156_포도주시식 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N + 1];
		int[] DP = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}

		DP[0] = 0;
		DP[1] = arr[1];
		if (N >= 2)
			DP[2] = arr[1] + arr[2];

		for (int i = 3; i <= N; i++) {
			DP[i] = Math.max(DP[i - 1], Math.max(DP[i - 2] + arr[i], DP[i - 3] + arr[i - 1] + arr[i]));
		}

		System.out.println(DP[N]);
	}
}
