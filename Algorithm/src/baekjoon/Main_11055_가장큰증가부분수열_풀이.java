package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * https://m.blog.naver.com/occidere/220793914361
 */
public class Main_11055_가장큰증가부분수열_풀이 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		int[] dp = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int max = 0;
		for (int i = 0; i < N; i++) {
			dp[i] = arr[i];
			for (int j = 0; j < i; j++)
				if (arr[j] < arr[i] && dp[i] < dp[j] + arr[i])
					dp[i] = dp[j] + arr[i];
			if (max < dp[i])
				max = dp[i];
		}
		
		for (int d : dp) {
			System.out.println(d);
		}
		System.out.println(max);
	}
}
