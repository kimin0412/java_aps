package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 2h 하고 틀렸습니다....
 */
public class Main_20058_마법사상어와파이어스톰 {
	static int n, Q, N, max;
	static int[][] map;
	static int[][] nextMap;
	static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		n = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		N = (int) Math.pow(2, n);
		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int[] Lq = new int[Q];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < Lq.length; i++) {
			Lq[i] = Integer.parseInt(st.nextToken());
		}

		// 시작
		for (int lq = 0; lq < Q; lq++) {
			nextMap = new int[N][N];
			int q = Lq[lq];
			int size = (int) Math.pow(2, q);
			int cnt = N / size;

			int[] limitR = { -size, -1 };
			int[] limitC = { -size, -1 };
			for (int i = 0; i < cnt; i++) {
				limitR[0] += size;
				limitR[1] += size;
				limitC[0] = -size;
				limitC[1] = -1;
				for (int j = 0; j < cnt; j++) {
					limitC[0] += size;
					limitC[1] += size;
					rotate(size, limitR, limitC);
				}
			}

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					map[i][j] = nextMap[i][j];
				}
			}
			melt();
		}

		int ans = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				ans += map[i][j];
			}
		}
		max = Integer.MIN_VALUE;
		getBig();
		System.out.println(ans);
		System.out.println(max);

	}

	private static void getBig() {
		boolean[][] visit = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] != 0) {
					Queue<int[]> queue = new LinkedList<>();
					queue.add(new int[] { i, j });
					visit[i][j] = true;
					int count = 1;
					while (!queue.isEmpty()) {
						int[] q = queue.poll();
						visit[q[0]][q[1]] = true;
						for (int d = 0; d < 4; d++) {
							int nr = q[0] + dir[d][0];
							int nc = q[1] + dir[d][1];
							if ((nr > -1 && nr < N && nc > -1 && nc < N) && !visit[nr][nc]) {
								if (map[nr][nc] != 0) {
									queue.add(new int[] { nr, nc });
									visit[nr][nc] = true;
									count++;
								}
							}
						}
					}
					max = Math.max(max, count);
				}
			}
		}
	}

	private static void melt() {
		int[][] meltMap = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				meltMap[i][j] = map[i][j];
				int cnt = 0;
				for (int d = 0; d < 4; d++) {
					int nr = i + dir[d][0];
					int nc = j + dir[d][1];
					if ((nr > -1 && nr < N && nc > -1 && nc < N)) {
						if (map[nr][nc] != 0)
							cnt++;
					}
				}
				if (cnt <= 2) {
					if (map[i][j] > 0)
						meltMap[i][j]--;
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = meltMap[i][j];
			}
		}
	}

	private static void rotate(int size, int[] limitR, int[] limitC) {
		int[][] tmp = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				tmp[i][j] = map[limitR[0] + i][limitC[0] + j];
			}
		}

		int[][] tmp2 = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				tmp2[j][size - i - 1] = tmp[i][j];
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				nextMap[limitR[0] + i][limitC[0] + j] = tmp2[i][j];
			}
		}
	}
}
