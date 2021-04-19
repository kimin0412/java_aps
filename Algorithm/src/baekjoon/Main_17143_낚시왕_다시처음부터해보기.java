package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_17143_낚시왕_다시처음부터해보기 {
	static int R, C, M;
	static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
	static int[][] map, mapTmp;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[R][C];

		Shark[] sharks = new Shark[M + 1];
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int s = Integer.parseInt(st.nextToken());
			// int tmp = Integer.parseInt(st.nextToken());
			// int s = 100;
			int d = Integer.parseInt(st.nextToken()) - 1;
			int z = Integer.parseInt(st.nextToken());

			sharks[i] = new Shark(r, c, s, d, z, true);
			map[r][c] = i;
		}

		int sum = 0;
		for (int cnt = 0; cnt < C; cnt++) {
			// System.out.println("cnt : " + cnt);
			int now = 0;
			for (int i = 0; i < R; i++) { // 상어잡기
				now = map[i][cnt];
				if (now > 0) {
					sharks[now].alive = false;
					map[i][cnt] = 0;
					sum += sharks[now].z;
					break;
				}
			}

			mapTmp = new int[R][C];
			for (int i = 1; i <= M; i++) {
				// System.out.println("i : " + i);
				if (sharks[i].alive) {
					int r = sharks[i].r;
					int c = sharks[i].c;
					int s = sharks[i].s;
					int d = sharks[i].d;

					// int nr = r;
					// int nc = c;
					int pr = r;
					int pc = c;
					int nr = r;
					int nc = c;
					int ns = s;

					while (ns != 0) {
						int round = 0;
						switch (d) {
						case 0:
							round = (r - 1) * 2;
							if (ns >= round) {
								ns = ns % round;
							} else if (ns < round) {
								for (int j = 0; j < s; j++) {
									nr = pr + dir[d][0];
									nc = pc + dir[d][1];

									if (!isIn(nr, nc)) {
										switch (d) {
										case 0:
											d = 1;
											sharks[i].d = 1;
											break;
										case 1:
											d = 0;
											sharks[i].d = 0;
											break;
										case 2:
											d = 3;
											sharks[i].d = 3;
											break;
										case 3:
											d = 2;
											sharks[i].d = 2;
											break;
										}
										j--;
									} else {
										pr = nr;
										pc = nc;
									}
								}
							}
							break;
						case 1:
							round = (r - 1) * 2;
							if (ns >= round) {
								ns = ns % round;
							} else if (ns < round) {
								for (int j = 0; j < s; j++) {
									nr = pr + dir[d][0];
									nc = pc + dir[d][1];

									if (!isIn(nr, nc)) {
										switch (d) {
										case 0:
											d = 1;
											sharks[i].d = 1;
											break;
										case 1:
											d = 0;
											sharks[i].d = 0;
											break;
										case 2:
											d = 3;
											sharks[i].d = 3;
											break;
										case 3:
											d = 2;
											sharks[i].d = 2;
											break;
										}
										j--;
									} else {
										pr = nr;
										pc = nc;
									}
								}
							}
							break;
						case 2:
							d = 3;
							sharks[i].d = 3;
							break;
						case 3:
							d = 2;
							sharks[i].d = 2;
							break;
						}
					}

					// int z = sharks[i].z;
					// int pr = r;
					// int pc = c;
					// for (int j = 0; j < s; j++) {
					// int nr = pr + dir[d][0];
					// int nc = pc + dir[d][1];
					// // if (i == 2) {
					// // System.out.println("j : " + (j+1));
					// // System.out.println("nr, nc : " + nr + " " + nc);
					// // }
					// if (!isIn(nr, nc)) {
					// switch (d) {
					// case 0:
					// d = 1;
					// sharks[i].d = 1;
					// break;
					// case 1:
					// d = 0;
					// sharks[i].d = 0;
					// break;
					// case 2:
					// d = 3;
					// sharks[i].d = 3;
					// break;
					// case 3:
					// d = 2;
					// sharks[i].d = 2;
					// break;
					// }
					// j--;
					// } else {
					// pr = nr;
					// pc = nc;
					// }
					// }

					// for (int rr = 0; rr < R; rr++) {
					// for (int cc = 0; cc < C; cc++) {
					// System.out.print(map[rr][cc] + " ");
					// }
					// System.out.println();
					// }
					// System.out.println();

					if (mapTmp[pr][pc] > 0 && map[pr][pc] != i) { // 이동이 끝난 후 다른 상어가 있으면
						// System.out.println("다른 상어 있음!");
						if (sharks[mapTmp[pr][pc]].z < sharks[i].z) {
							sharks[mapTmp[pr][pc]].alive = false;
							map[r][c] = 0;
							// map[pr][pc] = i;
							mapTmp[pr][pc] = i;
							sharks[i].r = pr;
							sharks[i].c = pc;
						} else {
							sharks[i].alive = false;
							map[r][c] = 0;
							// map[pr][pc] = i;
							// sharks[i].r = pr;
							// sharks[i].c = pc;
						}
					} else {
						map[r][c] = 0;
						mapTmp[pr][pc] = i;
						sharks[i].r = pr;
						sharks[i].c = pc;
					}
				}
				// for (int rr = 0; rr < R; rr++) {
				// for (int cc = 0; cc < C; cc++) {
				// System.out.print(map[rr][cc] + " ");
				// }
				// System.out.println();
				// }
				// System.out.println();
				for (int rr = 0; rr < R; rr++) {
					for (int cc = 0; cc < C; cc++) {
						if (mapTmp[rr][cc] != 0)
							map[rr][cc] = mapTmp[rr][cc];
					}
				}
			}
			// System.out.println();
		}
		System.out.println(sum);
	}

	// private static boolean isEnd(int nr, int nc) {
	// return (nr == 0 || nr < R && nc > -1 && nc < C);
	// }

	private static boolean isIn(int nr, int nc) {
		return (nr > -1 && nr < R && nc > -1 && nc < C);
	}

	static private class Shark {
		int r, c, s, d, z;
		boolean alive;

		public Shark(int r, int c, int s, int d, int z, boolean alive) {
			this.r = r; // 행
			this.c = c; // 열
			this.s = s; // 속력
			this.d = d; // 방향 (0:상, 1:하, 2:우, 3:좌)
			this.z = z; // 크기
			this.alive = alive; // 살아있니
		}

		public Shark() {
		}

	}
}
