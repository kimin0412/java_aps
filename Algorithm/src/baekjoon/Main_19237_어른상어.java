package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_19237_어른상어 {
	static int N, M, K;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] direction = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		ArrayList<Shark> sharks = new ArrayList<>();
		HashMap<Integer, Queue> sync = new HashMap<>();

		int[][] map = new int[N][N];
		int[][] sMap = new int[N][N];
		int[][] rMap = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 상어 위치 map

				if (map[i][j] != 0) {
					sharks.add(new Shark(map[i][j], i, j, 0, false));
					sMap[i][j] = map[i][j]; // 냄새 위치 sMap
					rMap[i][j] = K;
				}
			}
		} // 맵, 상어 위치 입력 끝

		Collections.sort(sharks, new Comparator<Shark>() {
			@Override
			public int compare(Shark o1, Shark o2) {
				return o1.id - o2.id;
			}
		});

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < M; i++) {
			sharks.get(i).dir = Integer.parseInt(st.nextToken()) - 1;
		} // 상어 처음 방향 입력 끝

		int[][][] prio = new int[M][4][4];
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < 4; j++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j2 = 0; j2 < 4; j2++) {
					prio[i][j][j2] = Integer.parseInt(st.nextToken()) - 1;
				}
			}
		} // 상어 방향 우선순위 입력 끝

		int sec = 0;
		while (true) {
			if (sec > 1000) {
				sec = -1;
				break;
			}

			int cnt = M;
			boolean flag = true;
			for (int i = 0; i < M; i++) {
				Shark ch = sharks.get(i);
				if (ch.out == true)
					cnt--;
			}
			if (cnt == 1)
				break;

			sync = new HashMap<>();
			sec++;

			for (int w = 0; w < sharks.size(); w++) {
				Shark sh = sharks.get(w);
				if (sh.out)
					continue;

				int id = sh.id;
				int r = sh.r;
				int c = sh.c;
				int dir = sh.dir;
				boolean check = false;

				for (int i = 0; i < 4; i++) {
					int nDir = prio[id - 1][dir][i];
					int nr = r + direction[nDir][0];
					int nc = c + direction[nDir][1];
					if (isIn(nr, nc) && sMap[nr][nc] == 0) { // 맵 범위 안인지, 흔적이 없는곳인지 검사
						check = true;
						if (sync.get(N * nr + nc) == null) {
							Queue<Shark> q = new LinkedList<Shark>();
							q.add(new Shark(id, nr, nc, nDir, false));
							sync.put(N * nr + nc, q);
							map[r][c] = 0;
						} else {
							Queue<Shark> q = sync.get(N * nr + nc);
							q.add(new Shark(id, nr, nc, nDir, false));
							sync.put(N * nr + nc, q);
							map[r][c] = 0;
						}
						break;
					}
				}

				if (!check) {
					for (int i = 0; i < 4; i++) {
						int nDir = prio[id - 1][dir][i];
						int nr = r + direction[nDir][0];
						int nc = c + direction[nDir][1];
						if (isIn(nr, nc) && sMap[nr][nc] == id) { // 맵 범위 안인지, 흔적이 없는곳인지 검사
							if (sync.get(N * nr + nc) == null) {
								Queue<Shark> q = new LinkedList<Shark>();
								q.add(new Shark(id, nr, nc, nDir, false));
								sync.put(N * nr + nc, q);
								map[r][c] = 0;
							} else {
								Queue<Shark> q = sync.get(N * nr + nc);
								q.add(new Shark(id, nr, nc, nDir, false));
								sync.put(N * nr + nc, q);
								map[r][c] = 0;
							}
							break;
						}
					}
				}

			}

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (rMap[i][j] != 0) {
						if (rMap[i][j] == 1)
							sMap[i][j] = 0;
						rMap[i][j]--;
					}
				}
			}

			for (int i = 0; i < N * N; i++) {
				if (sync.containsKey(i)) {
					Queue q = sync.get(i);
					Shark ss;
					int min = Integer.MAX_VALUE;

					while (q.size() != 1) {
						ss = (Shark) q.poll();
						if (min > ss.id) {
							min = ss.id;
							q.add(ss);
						} else {
							sharks.set(ss.id - 1, new Shark(ss.id, ss.r, ss.c, ss.dir, true));
						}
					}

					ss = (Shark) q.poll();
					int nr = i / N;
					int nc = i % N;

					map[nr][nc] = ss.id;
					sMap[nr][nc] = ss.id;

					sharks.set(ss.id - 1, new Shark(ss.id, ss.r, ss.c, ss.dir, false));
					sMap[nr][nc] = ss.id;
					rMap[nr][nc] = K;

				}
			}

		}

		System.out.println(sec);

	}

	private static boolean isIn(int nr, int nc) {
		return (nr > -1 && nr < N && nc > -1 && nc < N);
	}

	static class Shark {
		int id, r, c, dir;
		boolean out;

		public Shark() {
			super();
		}

		public Shark(int id, int r, int c, int dir, boolean out) {
			super();
			this.id = id;
			this.r = r;
			this.c = c;
			this.dir = dir;
			this.out = out;
		}

	}

	static class Smell {
		int id, r, c, k;
		boolean done;

		public Smell(int id, int r, int c, int k, boolean done) {
			super();
			this.id = id;
			this.r = r;
			this.c = c;
			this.k = k;
			this.done = done;
		}

	}
}
