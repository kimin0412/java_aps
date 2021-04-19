package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_20056_마법사상어와파이어볼 {
	static int N, M, K;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] dir = { { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 } };
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		Queue<Fireball> fires = new LinkedList<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			fires.add(new Fireball(r, c, m, s, d));
		}

		for (int k = 0; k < K; k++) {
			Queue<Fireball>[][] qMap = new LinkedList[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					qMap[i][j] = new LinkedList<>();
				}
			}

			while (!fires.isEmpty()) {
				Fireball tmp = fires.poll();
				int r = tmp.r;
				int c = tmp.c;
				int tmpS = tmp.s % N;
				int nr = r + dir[tmp.d][0] * tmpS;
				int nc = c + dir[tmp.d][1] * tmpS;
				if (nr < 0) {
					nr = N + nr;
				} else if (nr >= N) {
					nr = nr - N;
				}
				if (nc < 0) {
					nc = N + nc;
				} else if (nc >= N) {
					nc = nc - N;
				}
				qMap[nr][nc].add(new Fireball(nr, nc, tmp.m, tmp.s, tmp.d));
			} // 이동 끝

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (qMap[i][j].size() > 1) {
						Fireball f = qMap[i][j].poll();
						int sSum = f.s;
						int mSum = f.m;
						int cnt = qMap[i][j].size() + 1;
						int flag = 0;
						if (f.d % 2 == 0) { // 짝수라면
							flag = 1;
						} else { // 홀수라면
							flag = -1;
						}
						while (!qMap[i][j].isEmpty()) {
							f = qMap[i][j].poll();
							sSum += f.s;
							mSum += f.m;
							if (flag == 1) { // 지금까지 짝수였는데
								if (f.d % 2 == 0) { // 짝수라면
									flag = 1;
								} else {
									flag = 0;
								}
							} else if (flag == -1) { // 지금까지 홀수였는데
								if (f.d % 2 == 0) { // 짝수라면
									flag = 0;
								} else {
									flag = -1;
								}
							}
						}

						Fireball[] nextF = new Fireball[4];
						int nm = mSum / 5;
						int ns = sSum / cnt;
						if (nm != 0) {
							if (flag == 0) {
								nextF[0] = new Fireball(i, j, nm, ns, 1);
								nextF[1] = new Fireball(i, j, nm, ns, 3);
								nextF[2] = new Fireball(i, j, nm, ns, 5);
								nextF[3] = new Fireball(i, j, nm, ns, 7);
							} else {
								nextF[0] = new Fireball(i, j, nm, ns, 0);
								nextF[1] = new Fireball(i, j, nm, ns, 2);
								nextF[2] = new Fireball(i, j, nm, ns, 4);
								nextF[3] = new Fireball(i, j, nm, ns, 6);
							}
							for (int l = 0; l < 4; l++) {
								fires.add(nextF[l]);
							}
						}
					} else if (qMap[i][j].size() == 1) {
						Fireball f = qMap[i][j].poll();
						fires.add(f);
					}
				}
			}
		}

		int ans = 0;
		while (!fires.isEmpty()) {
			Fireball f = fires.poll();
			ans += f.m;
		}
		System.out.println(ans);

	}

	static private class Fireball {
		int r, c, m, s, d;

		public Fireball(int r, int c, int m, int s, int d) {
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
		}
	}
}
