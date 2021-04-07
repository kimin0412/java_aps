package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_3190_뱀2 {
	static int N, K, L, ans;
	static int[][] map;
	static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
//							  우			하		  좌 		  상

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		map = new int[N][N];

		StringTokenizer st;
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			map[a][b] = 1;
		}

		L = Integer.parseInt(br.readLine());
		Direction[] directions = new Direction[L];
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			String c = st.nextToken();
			directions[i] = new Direction(x, c);
		}

//		LinkedList<int[]> snake = new LinkedList<int[]>();
//		snake.add(new int[] { 0, 0 });
		int[][] snake = new int[N][N];
		snake[0][0] = 1;
		int hr = 0, hc = 0, tr = 0, tc = 0;
		int ro = 0;
		int cnt = 0;
		int cntL = 0;

		while (true) {
			System.out.println("cnt : " + cnt);
			for (int[] s : snake) {
				System.out.println(Arrays.toString(s));
			}
			cnt++;
//			int[] head = snake.get(0);
//			int nr = head[0] + dir[ro][0];
//			int nc = head[1] + dir[ro][1];
			int nr = hr + dir[ro][0];
			int nc = hc + dir[ro][1];
			System.out.println("nr, nc : " + nr + " " + nc);
			if (Isin(nr, nc) && snake[nr][nc] == 0) {
//			if (Isin(nr, nc) && map[nr][nc] == 0) {
//				snake.addFirst(new int[] { nr, nc });
				snake[nr][nc] = 1;
			} else {
				break;
			}

//			if (map[head[0]][head[1]] == 1) {
//				map[head[0]][head[1]] = 0;
//			} else if (map[head[0]][head[1]] != 1) {
//				snake.pollLast();
//			}
			if (map[hr][hc] == 1) {
				map[hr][hc] = 0;
			} else if (map[hr][hc] != 1) {
//				snake.pollLast();
			}

//			String c = directions.get(cnt);
			String c = "";
			if (cntL < L) {
				if (directions[cntL].x == cnt) {
					c = directions[cntL].c;
					cntL++;
				}
			}

//			for (int[] s : snake) {
//				System.out.println(Arrays.toString(s));
//			}

			System.out.println("c : " + c);
			if (c.equals("L")) { // 왼쪽 90도
				ro--;
			} else if (c.equals("D")) {
				ro++;
			}
			if (ro == -1)
				ro = 3;
			if (ro == 4)
				ro = 0;
			System.out.println("ro : " + ro);
			System.out.println();

		}

		ans = 0;
		System.out.println(cnt);
	}

	private static boolean Isin(int r, int c) {
		return (r > -1 && r < N && c > -1 && c < N);
	}

	static class Direction {
		int x;
		String c;

		public Direction(int x, String c) {
			super();
			this.x = x;
			this.c = c;
		}
	}
}