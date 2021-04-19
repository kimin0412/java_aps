package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17143_낚시왕_5_진짜레알최종 {
	static int R, C, M;
	static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
	public static Shark[][] map;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new Shark[R][C];
		Shark[] sharks = new Shark[M + 1];
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken()) - 1;
			int z = Integer.parseInt(st.nextToken());

			map[r][c] = new Shark(r, c, s, d, z);
		}

		int sum = 0;
		for (int cnt = 0; cnt < C; cnt++) {
			for (int i = 0; i < R; i++) { // 상어잡기
				if (map[i][cnt] != null) {
					sum += map[i][cnt].z; // 2. 가장 가까운 상어 크기 정답 변수에 저장
					map[i][cnt] = null; // map에서 상어 없애기
					break;
				}
			}

			Queue<Shark> queue = new LinkedList<>();
			for (int i = 0; i < R; i++) {
				for (int j = 0; j < C; j++) {
					if (map[i][j] != null) { // 현재 map에 있는 상어들 큐에 추가
						queue.add(new Shark(i, j, map[i][j].s, map[i][j].d, map[i][j].z));
					}
				}
			}

			map = new Shark[R][C]; // 새로운 낚시판 만들기위해 배열 초기화

			// 모든 상어 한마리씩 꺼내서 이동
			while (!queue.isEmpty()) {
				Shark sh = queue.poll();
				int s = sh.s;
				int d = sh.d;

				// 코드 추가
				if (d == 0 || d == 1) {
					s = s % ((R - 1) * 2);
				}
				if (d == 2 || d == 3) {
					s = s % ((C - 1) * 2);
				}

				for (int j = 0; j < s; j++) {
					int nr = sh.r + dir[d][0];
					int nc = sh.c + dir[d][1];
					if (!(nr > -1 && nr < R && nc > -1 && nc < C)) {
						switch (d) {
						case 0:
							d = 1;
							sh.d = 1;
							break;
						case 1:
							d = 0;
							sh.d = 0;
							break;
						case 2:
							d = 3;
							sh.d = 3;
							break;
						case 3:
							d = 2;
							sh.d = 2;
							break;
						}
						j--;
					} else {
						sh.r = nr;
						sh.c = nc;
					}
				}

				// 4. 새로운 위치가 빈 공간인지 이미 상어가 있는지 확인
				if (map[sh.r][sh.c] != null) { // 이미 상어가 있다면 두 상어 크기 비교
					if (map[sh.r][sh.c].z < sh.z) { // 기존 상어보다 현재 상어가 크다면
						map[sh.r][sh.c] = new Shark(sh.r, sh.c, sh.s, sh.d, sh.z); // 현재 상어 넣어줌
					}
				} else { // 없다면 현재 상어 바로 넣어줌
					map[sh.r][sh.c] = new Shark(sh.r, sh.c, sh.s, sh.d, sh.z);
				}
			}
		}
		System.out.println(sum);
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
