package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1h 10m Solved!
 */
public class Main_14500_테트로미노 {
	// 긴막대기
	static int[][][] tetro1 = { { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 } },
			{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 } } };
	// 네모
	static int[][] tetro2 = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
	// 니은 모양
	static int[][][] tetro3 = { { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 2, 1 } }, { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 2, 0 } },
			{ { 0, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }, { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 2, 0 } },
			{ { 0, 0 }, { 0, 1 }, { 1, 0 }, { 0, 2 } }, { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 2 } },
			{ { 1, 0 }, { 1, 1 }, { 1, 2 }, { 0, 2 } }, { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 1, 2 } } };
	// 지그재그 모양
	static int[][][] tetro4 = { { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 2, 1 } }, { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 2, 0 } },
			{ { 1, 0 }, { 1, 1 }, { 0, 1 }, { 0, 2 } }, { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 2 } } };
	// 뺘큐ㅠ모양,,
	static int[][][] tetro5 = { { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 } }, { { 1, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } },
			{ { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, 2 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, 1 } } };

	static int[][] map;
	static int N, M, ans;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		ans = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				for (int k = 0; k < 2; k++) {
					int sum = 0;
					int cnt = 0;
					for (int k2 = 0; k2 < 4; k2++) {
						int nr = i + tetro1[k][k2][0];
						int nc = j + tetro1[k][k2][1];
						if (!isIn(nr, nc)) {
							break;
						} else {
							sum += map[nr][nc];
							cnt++;
						}
					}
					if (cnt == 4) {
						ans = Math.max(ans, sum);
					}
				}

				for (int k = 0; k < 1; k++) {
					int sum = 0;
					int cnt = 0;
					for (int k2 = 0; k2 < 4; k2++) {
						int nr = i + tetro2[k2][0];
						int nc = j + tetro2[k2][1];
						if (!isIn(nr, nc)) {
							break;
						} else {
							sum += map[nr][nc];
							cnt++;
						}
					}
					if (cnt == 4) {
						ans = Math.max(ans, sum);
					}
				}

				for (int k = 0; k < 8; k++) {
					int sum = 0;
					int cnt = 0;
					for (int k2 = 0; k2 < 4; k2++) {
						int nr = i + tetro3[k][k2][0];
						int nc = j + tetro3[k][k2][1];
						if (!isIn(nr, nc)) {
							break;
						} else {
							sum += map[nr][nc];
							cnt++;
						}
					}
					if (cnt == 4) {
						ans = Math.max(ans, sum);
					}
				}

				for (int k = 0; k < 4; k++) {
					int sum = 0;
					int cnt = 0;
					for (int k2 = 0; k2 < 4; k2++) {
						int nr = i + tetro4[k][k2][0];
						int nc = j + tetro4[k][k2][1];
						if (!isIn(nr, nc)) {
							break;
						} else {
							sum += map[nr][nc];
							cnt++;
						}
					}
					if (cnt == 4) {
						ans = Math.max(ans, sum);
					}
				}

				for (int k = 0; k < 4; k++) {
					int sum = 0;
					int cnt = 0;
					for (int k2 = 0; k2 < 4; k2++) {
						int nr = i + tetro5[k][k2][0];
						int nc = j + tetro5[k][k2][1];
						if (!isIn(nr, nc)) {
							break;
						} else {
							sum += map[nr][nc];
							cnt++;
						}
					}
					if (cnt == 4) {
						ans = Math.max(ans, sum);
					}
				}
			}
		}
		System.out.println(ans);

	}

	private static boolean isIn(int nr, int nc) {
		return (nr > -1 && nr < N && nc > -1 && nc < M);
	}

}
