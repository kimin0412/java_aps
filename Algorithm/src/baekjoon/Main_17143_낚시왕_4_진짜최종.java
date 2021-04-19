package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17143_낚시왕_4_진짜최종 {
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
			int d = Integer.parseInt(st.nextToken()) - 1;
			int z = Integer.parseInt(st.nextToken());

			sharks[i] = new Shark(r, c, s, d, z, true);
			map[r][c] = i;
		}

		int sum = 0;
		for (int cnt = 0; cnt < C; cnt++) {
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
			HashMap<Integer, int[]> moveEnd = new HashMap<Integer, int[]>();
			// HashMap<Integer, Integer> moveEnd = new HashMap<Integer, Integer>();

			for (int i = 1; i <= M; i++) {
				// System.out.println(i);
				if (sharks[i].alive) {
					int r = sharks[i].r;
					int c = sharks[i].c;
					int s = sharks[i].s;
					int d = sharks[i].d;
					int pr = r;
					int pc = c;

					// 코드 추가
					if (d == 0 || d == 1) {
						s = s % ((R - 1) * 2);
					}
					if (d == 2 || d == 3) {
						s = s % ((C - 1) * 2);
					}
					// System.out.println("s : " + s);
					for (int j = 0; j < s; j++) {
						int nr = pr + dir[d][0];
						int nc = pc + dir[d][1];
						// System.out.println("nr, nc : " + nr + " " + nc);
						if (!(nr > -1 && nr < R && nc > -1 && nc < C)) {
							// if (!isIn(nr, nc)) {
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

					if (mapTmp[pr][pc] > 0 && map[pr][pc] != i) { // 이동이 끝난 다른 상어가 있으면
						if (sharks[mapTmp[pr][pc]].z < sharks[i].z) {
							sharks[mapTmp[pr][pc]].alive = false;
							map[r][c] = 0;
							mapTmp[pr][pc] = i;
							// System.out.println(pr + " " + pc + " " + i);
							moveEnd.remove(mapTmp[pr][pc]);
							moveEnd.put(i, new int[] { pr, pc });
							// mapTmp[pr][pc] = i;
							sharks[i].r = pr;
							sharks[i].c = pc;
						} else {
							sharks[i].alive = false;
							map[r][c] = 0;
						}
					} else {
						map[r][c] = 0;
						mapTmp[pr][pc] = i;
						moveEnd.put(i, new int[] { pr, pc });
						// mapTmp[pr][pc] = i;
						sharks[i].r = pr;
						sharks[i].c = pc;
					}
					// map[pr][pc] = mapTmp[pr][pc];
				}

				for (Map.Entry<Integer, int[]> entry : moveEnd.entrySet()) {
					int key = entry.getKey();
					int[] value = entry.getValue();
					// System.out.println("key, v : " + key + " " + value[0] + " " + value[1]);
					map[value[0]][value[1]] = key;
				}

				// for (int rr = 0; rr < R; rr++) {
				// for (int cc = 0; cc < C; cc++) {
				// if (mapTmp[rr][cc] != 0)
				// map[rr][cc] = mapTmp[rr][cc];
				// }
				// }
				// System.out.println("map : ");
				// for (int[] s : map) {
				// 	System.out.println(Arrays.toString(s));
				// }
				// System.out.println();

				// System.out.println("mapTmp : ");
				// for (int[] s : mapTmp) {
				// 	System.out.println(Arrays.toString(s));
				// }
				// System.out.println();
			}
		}
		System.out.println(sum);
	}

	// private static boolean isIn(int nr, int nc) {
	// return (nr > -1 && nr < R && nc > -1 && nc < C);
	// }

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
