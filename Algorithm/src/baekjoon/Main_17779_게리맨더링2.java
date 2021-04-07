package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main_17779_게리맨더링2 {
	static int N;
	static int[][] map;
	static int[][] dir = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		int allSum = 0;
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				allSum += map[i][j];

			}
		}

		System.out.println(allSum);

		int x = 0, y = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				x = i;
				y = j;
				for (int d1 = 1; d1 <= N; d1++) {
					for (int d2 = 1; d2 <= N; d2++) {
						int[] sums = new int[4];
						if (isIn(x + d1 - 1, y - d1 - 1) && isIn(x + d2 - 1, y + d2 - 1)
								&& isIn(x + d1 + d2 - 1, y - d1 + d2 - 1) && isIn(x + d2 + d1 - 1, y + d2 - d1 - 1)) {
							if (x == 3 && y == 2 && d1 == 1 && d2 == 1) {
								System.out.println("i, j, d1, d2 : " + i + " " + j + " " + d1 + " " + d2 + " ");
								sums = cal(x, y, d1, d2);
								System.out.println(Arrays.toString(sums));
								System.out.println();
							}
							map[i][j] = 5;
						}
					}
				}

			}
		}

	}

	private static int[] cal(int x, int y, int d1, int d2) {
		int[][] tmpMap = new int[N][N];
		int[] sums = new int[4];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.println("i, j : " + i + " " + j);
				if (i >= 0 && i <= x + d1 && j >= 0 && j <= y ) {
					sums[0] += map[i][j];
					tmpMap[i][j] = 1;
				} else if (i >= 0 && i < x + d2 - 1 && j - 1 > y && j < N) {
					sums[1] += map[i][j];
					tmpMap[i][j] = 2;
				} else if (i > x + d1 - 1 && i < N && j >= 0 && j < y - d1 + d2 - 1) {
					sums[2] += map[i][j];
					tmpMap[i][j] = 3;
				} else if (i >= 0 && i > x + d2 - 1 && j >= 0 && j < N) {
					sums[3] += map[i][j];
					tmpMap[i][j] = 4;
				}

				for (int[] s : tmpMap) {
					System.out.println(Arrays.toString(s));
				}
				System.out.println();
			}
		}
		return sums;

	}

	private static boolean isIn(int i, int j) {
		return (i >= 0 && i < N && j >= 0 && j < N);
	}
}