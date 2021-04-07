package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_19238_스타트택시 {
	static int N, M, K, goal;
	static int[][] map, gsMap, gfMap;
	static int sr, sc;
	static int[][] guest;
	static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	static HashMap<String, Integer> sGuests, fGuests;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		sr = Integer.parseInt(st.nextToken()) - 1;
		sc = Integer.parseInt(st.nextToken()) - 1;

		gsMap = new int[N][N];
		gfMap = new int[N][N];
		guest = new int[M + 1][4];
		sGuests = new HashMap<String, Integer>();
		fGuests = new HashMap<String, Integer>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			guest[i + 1][0] = Integer.parseInt(st.nextToken()) - 1;
			guest[i + 1][1] = Integer.parseInt(st.nextToken()) - 1;
			guest[i + 1][2] = Integer.parseInt(st.nextToken()) - 1;
			guest[i + 1][3] = Integer.parseInt(st.nextToken()) - 1;

			gsMap[guest[i + 1][0]][guest[i + 1][1]] = i + 1;
			gfMap[guest[i + 1][2]][guest[i + 1][3]] = i + 1;

			String start = guest[i + 1][0] + "" + guest[i + 1][1];
			String finish = guest[i + 1][2] + "" + guest[i + 1][3];
			sGuests.put(start, i + 1);
			fGuests.put(finish, i + 1);
		}

		goal = -1;
		for (int i = 0; i < M; i++) {
			int[] next = findGuest(sr, sc);
			goal = next[1];
			if (next[0] == 0) {
				K = -1;
				break;
			}
//			for (int[] s : gsMap) {
//				System.out.println(Arrays.toString(s));
//			}

			K = next[0];
			sr = guest[goal][0];
			sc = guest[goal][1];
			System.out.println("goal, K    " + goal + " " + K);
			System.out.println("goal 위치   " + sr + " " + sc);

			int cost = moveGoal();
			for (int[] s : gfMap) {
				System.out.println(Arrays.toString(s));
			}

			sr = guest[goal][2];
			sc = guest[goal][3];

			if (K == -1)
				break;

			K = K + cost * 2;
			System.out.println("goal, K    " + goal + " " + K);
			System.out.println("goal 위치   " + sr + " " + sc);
		}
		System.out.println(K);

	}

	private static int moveGoal() {
		if (gfMap[sr][sc] == goal) {
			String key = sr + "" + sc;
			if (fGuests.containsKey(key)) {
				gfMap[guest[goal][0]][guest[goal][1]] = 0;
				return 0;
			}
		}

		Queue<int[]> queue = new LinkedList<int[]>();
		queue.add(new int[] { sr, sc, 0, K });
		boolean[][] visit = new boolean[N][N];
		visit[sr][sc] = true;
		int min = Integer.MAX_VALUE;

		while (!queue.isEmpty()) {
			int[] q = queue.poll();
			if (q[3] == 0) {
				continue;
			}

			for (int d = 0; d < 4; d++) {
				int nr = q[0] + dir[d][0];
				int nc = q[1] + dir[d][1];
				if (isIn(nr, nc) && !visit[nr][nc] && map[nr][nc] != 1) {
					System.out.println("nr nc  " + nr + " " + nc);
					if (gfMap[nr][nc] != goal) {
						queue.add(new int[] { nr, nc, q[2] + 1, q[3] - 1 });
						visit[nr][nc] = true;
					} else if (gfMap[nr][nc] == goal) {
						String key = nr + "" + nc;
						if (fGuests.containsKey(key)) {
							if (min > q[2] + 1) {
								min = q[2] + 1;
							}
						}
						break;
					}
				}
			}
		}
		if (min == Integer.MAX_VALUE) {
			K = -1;
			return -1;
		}
		K = K - min;
		gfMap[guest[goal][2]][guest[goal][3]] = 0;
		return min;
	}

	private static int[] findGuest(int r, int c) {
		if (gsMap[sr][sc] != 0) {
			System.out.println("시작하자 마자 찾음");
			String key = sr + "" + sc;
			if (sGuests.containsKey(key)) {
				int[] answer = new int[3];
				answer[0] = K;
				answer[1] = gsMap[sr][sc];
				gsMap[guest[answer[1]][0]][guest[answer[1]][1]] = 0;
				return answer;
			}

		}

		Queue<int[]> queue = new LinkedList<int[]>();
		queue.add(new int[] { r, c, 0, K });
		boolean[][] visit = new boolean[N][N];
		visit[r][c] = true;
		int min = Integer.MAX_VALUE;
		int guestNo = 0;

		while (!queue.isEmpty()) {
			int[] q = queue.poll();
			if (q[3] == 0)
				continue;

			for (int d = 0; d < 4; d++) {
				int nr = q[0] + dir[d][0];
				int nc = q[1] + dir[d][1];
				if (isIn(nr, nc) && !visit[nr][nc] && map[nr][nc] != 1) {
					if (gsMap[nr][nc] == 0) {
						queue.add(new int[] { nr, nc, q[2] + 1, q[3] - 1 });
						visit[nr][nc] = true;
					} else {
						String key = nr + "" + nc;
						if (sGuests.containsKey(key)) {
							if (min > q[2] + 1) {
								min = q[2] + 1;
								guestNo = sGuests.get(key);
							} else if (min == q[2] + 1) {
								if (nr < guest[guestNo][0]) {
									min = q[2] + 1;
									guestNo = sGuests.get(key);
								} else if (nr == guest[guestNo][0]) {
									if (nc < guest[guestNo][1]) {
										min = q[2] + 1;
										guestNo = sGuests.get(key);
									}
								}
							}

						}
					}
				}
			}
		}

		if (min == Integer.MAX_VALUE) {
			int[] answer = new int[3];
			answer[0] = 0;
			answer[1] = -1;
			return answer;
		}

		int[] answer = new int[3];
		answer[0] = K - min;
		answer[1] = guestNo;
		gsMap[guest[answer[1]][0]][guest[answer[1]][1]] = 0;
		return answer;
	}

	private static boolean isIn(int nr, int nc) {
		return (nr > -1 && nr < N && nc > -1 && nc < N);
	}
}
