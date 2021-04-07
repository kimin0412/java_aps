package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main_11055_가장큰증가부분수열 {
	static int[][] dp = new int[30][30];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		MaxArr[] max_arr = new MaxArr[N];
		
		for (int i = 0; i < max_arr.length; i++) {
			max_arr[i] = new MaxArr(arr[i], arr[i]);
		}

		for (int i = 1; i < N; i++) {
			for (int j = i - 1; j >= 0; j--) {
				if (arr[i] > max_arr[j].max) {
					max_arr[i] = new MaxArr(arr[i], arr[i] + max_arr[j].hap);
					break;
				}
			}
		}
		
		for (MaxArr ma : max_arr) {
			System.out.println(ma.max + " " + ma.hap);
		}
		int max = 0;
		for (MaxArr ma : max_arr) {
			max = Math.max(max, ma.hap);
		}
		System.out.println(max);

	}

	static class MaxArr {
		int max;
		int hap;

		public MaxArr(int max, int hap) {
			super();
			this.max = max;
			this.hap = hap;
		}

	}
}
