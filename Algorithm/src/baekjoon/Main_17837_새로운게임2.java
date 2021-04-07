package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main_17837_새로운게임2 {
	static int N, K;
	static int[][] map;
	static int[][] dir = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
	static String[] ddd = { "→", "←", "↑", "↓" };
	static int time = 0;
	static LinkedList<Knight>[][] board;
	static ArrayList<int[]> knights;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		board = new LinkedList[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				board[i][j] = new LinkedList<Knight>();
			}
		}

		knights = new ArrayList<int[]>();
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken()) - 1;
			board[r][c].add(new Knight(i + 1, d));
			knights.add(new int[] { r, c });
		}

		top: while (true) {
			boolean flag = false;
			for (int c1 = 0; c1 < N; c1++) {
				for (int c2 = 0; c2 < N; c2++) {
					if (board[c1][c2].size() >= 4)
						flag = true;
				}
			}
			if (flag)
				break top;

			if (time > 1001) {
				time = -1;
				break;
			}
			time++;

			for (int i = 0; i < knights.size(); i++) {
				int[] pos = knights.get(i);
				for (int j = 0; j < board[pos[0]][pos[1]].size(); j++) {
					Knight k = board[pos[0]][pos[1]].get(j);
					if (i + 1 == k.id) {
						int nr = pos[0] + dir[k.dir][0];
						int nc = pos[1] + dir[k.dir][1];
						int kd = k.dir;
						boolean check = false;

						if (!isIn(nr, nc) || map[nr][nc] == 2) {
							check = true;
							switch (kd) {
							case 0:
								kd = 1;
								break;
							case 1:
								kd = 0;
								break;
							case 2:
								kd = 3;
								break;
							case 3:
								kd = 2;
								break;
							}
							k.dir = kd;
						}
						nr = pos[0] + dir[k.dir][0];
						nc = pos[1] + dir[k.dir][1];
						board[pos[0]][pos[1]].set(j, k);
						int size = board[pos[0]][pos[1]].size();
						if (isIn(nr, nc)) {
							switch (map[nr][nc]) {
							case 0:
								LinkedList<Knight> tmps = new LinkedList<>();
								Knight add;
								for (int l = size - 1; l >= j; l--) {
									add = board[pos[0]][pos[1]].pollLast();
									knights.set(add.id - 1, new int[] { nr, nc });
									tmps.add(add);
								}
								int tmpSize = tmps.size();
								for (int l = 0; l < tmpSize; l++) {
									add = tmps.pollLast();
									board[nr][nc].add(add);
								}
								break;
							case 1:
								tmps = new LinkedList<>();
								for (int l = size - 1; l >= j; l--) {
									add = board[pos[0]][pos[1]].pollLast();
									knights.set(add.id - 1, new int[] { nr, nc });
									tmps.add(add);
								}
								tmpSize = tmps.size();
								for (int l = 0; l < tmpSize; l++) {
									add = tmps.poll();
									board[nr][nc].add(add);
								}
								break;
							case 2:
								break;
							}

							break;
						}
					}
					for (int c1 = 0; c1 < N; c1++) {
						for (int c2 = 0; c2 < N; c2++) {
							if (board[c1][c2].size() >= 4)
								flag = true;
						}
					}
					if (flag)
						break top;
				}
			}

		}
		System.out.println(time);
	}

	private static boolean isIn(int nr, int nc) {
		return (nr > -1 && nr < N && nc > -1 && nc < N);
	}

	private static class Knight {
		int id;
		int dir;

		public Knight(int id, int dir) {
			super();
			this.id = id;
			this.dir = dir;
		}

		@Override
		public String toString() {
			return "Knight [id=" + id + ", dir=" + dir + "]";
		}

	}
}