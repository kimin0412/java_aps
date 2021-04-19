package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17143_낚시왕_3 {
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
		Map<Integer, Shark> sharks = new HashMap<>();

		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken()) - 1;
			int z = Integer.parseInt(st.nextToken());
			Shark sh = new Shark(r, c, s, d, z);

			sharks.put(i, sh);
			map[r][c] = i;
		}


		int sum = 0;
		for (int cnt = 0; cnt < C; cnt++) {
			int now = 0;
			for (int i = 0; i < R; i++) { // 상어잡기
				now = map[i][cnt];
				if (now > 0) {
					Shark s = sharks.get(now);
					map[i][cnt] = 0;
					sum += s.z;
					sharks.remove(now);
					break;
				}
			}

			Queue<Integer> del = new LinkedList<>();
			mapTmp = new int[R][C];
			for (Integer key : sharks.keySet()) {
				Shark sh = sharks.get(key);
				int r = sh.r;
				int c = sh.c;
				int s = sh.s;
				int d = sh.d;

				int pr = r;
				int pc = c;
				for (int j = 0; j < s; j++) {
					int nr = pr + dir[d][0];
					int nc = pc + dir[d][1];

					if (!isIn(nr, nc)) {
						switch (d) {
						case 0:
							d = 1;
							sh.d = 1;
							sharks.put(key, sh);
							break;
						case 1:
							d = 0;
							sh.d = 0;
							sharks.put(key, sh);
							break;
						case 2:
							d = 3;
							sh.d = 3;
							sharks.put(key, sh);
							break;
						case 3:
							d = 2;
							sh.d = 2;
							sharks.put(key, sh);
							break;
						}
						j--;
					} else {
						pr = nr;
						pc = nc;
					}
				}

				if (mapTmp[pr][pc] > 0 && map[pr][pc] != key) { // 이동이 끝난 후 다른 상어가 있으면
					if (sharks.get(mapTmp[pr][pc]).z < sh.z) {
						del.add(mapTmp[pr][pc]);
						map[r][c] = 0;
						mapTmp[pr][pc] = key;
						sh.r = pr;
						sh.c = pc;
						sharks.put(key, sh);
					} else {
						del.add(key);
						map[r][c] = 0;
					}
				} else {
					map[r][c] = 0;
					mapTmp[pr][pc] = key;
					sh.r = pr;
					sh.c = pc;
					sharks.put(key, sh);
				}

				for (int rr = 0; rr < R; rr++) {
					for (int cc = 0; cc < C; cc++) {
						if (mapTmp[rr][cc] != 0)
							map[rr][cc] = mapTmp[rr][cc];
					}
				}
			}
			while (!del.isEmpty()) {
				int dd = del.poll();
				sharks.remove(dd);
			}
		}
		System.out.println(sum);
	}

	private static boolean isIn(int nr, int nc) {
		return (nr > -1 && nr < R && nc > -1 && nc < C);
	}

	static private class Shark {
		int r, c, s, d, z;

		public Shark(int r, int c, int s, int d, int z) {
			this.r = r; // 행
			this.c = c; // 열
			this.s = s; // 속력
			this.d = d; // 방향 (0:상, 1:하, 2:우, 3:좌)
			this.z = z; // 크기
		}

		public Shark() {
		}

	}
}
