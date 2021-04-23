package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
/**
 * 1h Solved!
 */
public class Main_14502_연구소 {
	static int N, M, ans;
	static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	static int[][] virus;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int[][] map = new int[N][M];
		Queue<int[]> vq = new LinkedList<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 2) {
					vq.add(new int[] { i, j });
				}
			}
		}

		virus = new int[vq.size()][2];
		int idx = 0;
		while (!vq.isEmpty()) {
			int[] tmp = vq.poll();
			virus[idx][0] = tmp[0];
			virus[idx][1] = tmp[1];
			idx++;
		}

		ans = 0;
		pickWall(map, 0, 0);

		System.out.println(ans);
	}

	private static void pickWall(int[][] map, int idx, int cnt) {
		if (cnt == 3) {
			int[][] bfsMap = new int[N][M];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					bfsMap[i][j] = map[i][j];
				}
			}
			bfs(bfsMap);
		} else {
			if (idx != N * M) {
				int r = idx / M;
				int c = idx % M;
				if (map[r][c] == 0) {
					map[r][c] = 1;
					pickWall(map, idx + 1, cnt + 1); // 벽 세우기
					map[r][c] = 0;
				}
				pickWall(map, idx + 1, cnt); // 벽 안세우고 다음칸으로
			}
		}
	}

	private static void bfs(int[][] map) {
		boolean[][] visit = new boolean[N][M];
		for (int i = 0; i < virus.length; i++) {
			int[] start = virus[i];
			Queue<int[]> queue = new LinkedList<>();
			queue.add(start);
			visit[start[0]][start[1]] = true;
			while (!queue.isEmpty()) {
				int[] tmp = queue.poll();
				for (int d = 0; d < 4; d++) {
					int nr = tmp[0] + dir[d][0];
					int nc = tmp[1] + dir[d][1];
					if (isIn(nr, nc)) {
						if (map[nr][nc] != 1 && !visit[nr][nc]) {
							map[nr][nc] = 2;
							queue.add(new int[] { nr, nc });
							visit[nr][nc] = true;
						}
					}
				}
			}
		}

		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 0) {
					cnt++;
				}
			}

		}
		ans = Math.max(ans, cnt);
	}

	private static boolean isIn(int nr, int nc) {
		return (nr > -1 && nr < N && nc > -1 && nc < M);
	}
}