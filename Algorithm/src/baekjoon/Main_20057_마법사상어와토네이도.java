package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * 1h 30m Solved!
 */
public class Main_20057_마법사상어와토네이도 {
	static int N;
	static int[][] map;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] dir = { { 0, -1 }, { 1, 0 }, { 0, 1 }, { -1, 0 } };
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int sr = N / 2;
		int sc = N / 2;

		int s = 1;
		int cnt = 2;
		int d = 0;
		int ans = 0;
		int[] per = { 2, 10, 7, 1, 5, 10, 7, 1, 2 };
		int[][][] pos = {
				{ { -2, -1 }, { -1, -2 }, { -1, -1 }, { -1, 0 }, { 0, -3 }, { 1, -2 }, { 1, -1 }, { 1, 0 }, { 2, -1 } },
				{ { 1, -2 }, { 2, -1 }, { 1, -1 }, { 0, -1 }, { 3, 0 }, { 2, 1 }, { 1, 1 }, { 0, 1 }, { 1, 2 } },
				{ { 2, 1 }, { 1, 2 }, { 1, 1 }, { 1, 0 }, { 0, 3 }, { -1, 2 }, { -1, 1 }, { -1, 0 }, { -2, 1 } },
				{ { -1, 2 }, { -2, 1 }, { -1, 1 }, { 0, 1 }, { -3, 0 }, { -2, -1 }, { -1, -1 }, { 0, -1 },
						{ -1, -2 } } };
		while (true) {
			if (sr == 0 && sc == 0)
				break;
			for (int i = 0; i < s; i++) {
				if (sr == 0 && sc == 0)
					break;
				int nr = sr + dir[d][0];
				int nc = sc + dir[d][1];
				int total = map[nr][nc]; // 처음 모래양 y

				int a = total;
				int[] sand = new int[9];
				for (int p = 0; p < 9; p++) {
					sand[p] = total * per[p] / 100;
					a -= sand[p];
				} // 흩어진 모래양 구하기

				int[][] sandPos = new int[9][2];
				for (int p = 0; p < 9; p++) {
					sandPos[p][0] = sr + pos[d][p][0];
					sandPos[p][1] = sc + pos[d][p][1];
					if (isIn(sandPos[p][0], sandPos[p][1])) {
						map[sandPos[p][0]][sandPos[p][1]] += sand[p];
					} else {
						ans += sand[p];
					}
				}

				int[] aPos = new int[2];
				aPos[0] = nr + dir[d][0];
				aPos[1] = nc + dir[d][1];
				map[nr][nc] = 0;
				if (isIn(aPos[0], aPos[1])) {
					map[aPos[0]][aPos[1]] += a;
				} else {
					ans += a;
				}
				sr = nr;
				sc = nc;

			}
			cnt--;
			if (cnt == 0) {
				s++;
				cnt = 2;
			}

			d++;
			if (d == 4) {
				d = 0;
			}

		}
		System.out.println(ans);
	}

	private static boolean isIn(int r, int c) {
		return (r > -1 && r < N && c > -1 && c < N);
	}
}
