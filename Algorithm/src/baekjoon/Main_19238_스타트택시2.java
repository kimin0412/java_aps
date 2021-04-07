package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_19238_스타트택시2 {
	static int N, M, K;
	static int[][] map;
	static int sr, sc, goal;
	static int[][] guest;
	static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	static HashMap<String, Integer> sGuests;
	static HashMap<String, ArrayList> fGuests;

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

		guest = new int[M + 1][4];
		sGuests = new HashMap<String, Integer>();
		fGuests = new HashMap<String, ArrayList>();

		for (int i = 0; i < M; i++) {
//			System.out.println(i + 1);
			st = new StringTokenizer(br.readLine(), " ");
			guest[i + 1][0] = Integer.parseInt(st.nextToken()) - 1;
			guest[i + 1][1] = Integer.parseInt(st.nextToken()) - 1;
			guest[i + 1][2] = Integer.parseInt(st.nextToken()) - 1;
			guest[i + 1][3] = Integer.parseInt(st.nextToken()) - 1;

			String start = guest[i + 1][0] + "" + guest[i + 1][1];
			String finish = guest[i + 1][2] + "" + guest[i + 1][3];

			sGuests.put(start, i + 1);

			if (!fGuests.containsKey(finish)) {
				ArrayList<Integer> fQ = new ArrayList<>();
				fQ.add(i + 1);
				fGuests.put(finish, fQ);
			} else {
				ArrayList<Integer> fQ = new ArrayList<>();
				fQ = fGuests.get(finish);
				fQ.add(i + 1);
				fGuests.put(finish, fQ);
			}
		}

		goal = -1;
		for (int i = 0; i < M; i++) {
			System.out.println((i + 1) + " 번째 ");
//			int[] next = findGuest(sr, sc);
//			goal = next[1];
//			if (K == 0) {
//				K = -1;
//				break;
//			}

//			K = next[0];
			findGuest();
			if (K == -1)
				break;
//			sr = guest[goal][2];
//			sc = guest[goal][3];
			System.out.println("goal, K    " + goal + " " + K);
			System.out.println("goal 위치   " + sr + " " + sc);
//			sr = guest[goal][2];
//			sc = guest[goal][3];
//			System.out.println("goal, K    " + goal + " " + K);
//			System.out.println("goal 위치   " + sr + " " + sc);

			int cost = moveGoal();
			System.out.println("goal, K    " + goal + " " + K);
			System.out.println("goal 위치   " + sr + " " + sc);
			sr = guest[goal][0];
			sc = guest[goal][1];

			if (K == -1)
				break;

			K = K + cost * 2;
			System.out.println("goal, K    " + goal + " " + K);
			System.out.println("goal 위치   " + sr + " " + sc);
		}
		System.out.println(K);

	}

	private static int moveGoal() {
//		String key1 = sr + "" + sc;
//		if (fGuests.containsKey(key1)) {
//			System.out.println("시작하자 마자 찾음");
//			int[] answer = new int[3];
//			answer[0] = K;
//			ArrayList<Integer> sQ = new ArrayList<>();
//			sQ = fGuests.get(key1);
//			int idx = 0;
//			for (int i = 0; i < sQ.size(); i++) {
//				if (sQ.get(i) == goal) {
//					idx = i;
//					break;
//				}
//			}
//			sQ.remove(idx);
//			fGuests.put(key1, sQ);
//			return 0;
//		}

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
					if (nr != guest[goal][2] || nc != guest[goal][3]) {
						queue.add(new int[] { nr, nc, q[2] + 1, q[3] - 1 });
						visit[nr][nc] = true;
					} else if (nr == guest[goal][2] && nc == guest[goal][3]) {
						String key = nr + "" + nc;
						if (fGuests.containsKey(key)) {
							if (min > q[2] + 1) {
								min = q[2] + 1;
							}
						}
						break;
					}
//					String key = nr + "" + nc;
//					System.out.println("nr nc  " + nr + " " + nc);
//					if (fGuests.containsKey(key)) {
////						if (nr == 4 && nc == 4) {
//						System.out.println("들어감");
////						}
//						System.out.println(fGuests.get(key));
//
//						ArrayList<Integer> sQ = new ArrayList<>();
//						sQ = fGuests.get(key1);
//						int idx = 0;
//						for (int i = 0; i < sQ.size(); i++) {
//							if (sQ.get(i) == goal) {
//								if (min > q[2] + 1) {
//									min = q[2] + 1;
//									idx = i;
//									break;
//								}
//							}
//						}
//						sQ.remove(idx);
//						fGuests.put(key, sQ);
//						break;
//
//					} else {
//						if (nr == 4 && nc == 4) {
//							System.out.println("안들어감");
//						}
//						queue.add(new int[] { nr, nc, q[2] + 1, q[3] - 1 });
//						visit[nr][nc] = true;
//					}
				}
			}
		}
		if (min == Integer.MAX_VALUE) {
			K = -1;
			return -1;
		}
		K = K - min;
		return min;
	}

	private static void findGuest() {
		String key1 = sr + "" + sc;
		if (sGuests.containsKey(key1)) {
			System.out.println("시작하자 마자 찾음");
			int idx = sGuests.get(key1);
			sGuests.remove(key1);
			goal = idx;
			return;
		}

		Queue<int[]> queue = new LinkedList<int[]>();
		queue.add(new int[] { sr, sc, 0, K });
		boolean[][] visit = new boolean[N][N];
		visit[sr][sc] = true;
		int min = Integer.MAX_VALUE;
		int guestNo = 0;

		while (!queue.isEmpty()) {
			int[] q = queue.poll();
			if (q[3] < 0)
				continue;

			for (int d = 0; d < 4; d++) {
				int nr = q[0] + dir[d][0];
				int nc = q[1] + dir[d][1];
				if (isIn(nr, nc) && !visit[nr][nc] && map[nr][nc] != 1) {
					String key = nr + "" + nc;
					if (!sGuests.containsKey(key)) {
						queue.add(new int[] { nr, nc, q[2] + 1, q[3] - 1 });
						visit[nr][nc] = true;
					} else {
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

		if (min == Integer.MAX_VALUE) {
//			int[] answer = new int[3];
//			answer[0] = 0;
//			answer[1] = -1;
			K = -1;
			goal = -1;
			return;
		}

//		int[] answer = new int[3];
		K = K - min;
		goal = guestNo;
		String str = guest[goal][0] + "" + guest[goal][1];
		sGuests.remove(str);
		return;
//		answer[0] = K - min;
//		answer[1] = guestNo;
//		return answer;
	}

	private static boolean isIn(int nr, int nc) {
		return (nr > -1 && nr < N && nc > -1 && nc < N);
	}
}
