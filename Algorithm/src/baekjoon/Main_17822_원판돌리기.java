package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main_17822_원판돌리기 {
	static int N, M, T;
	static int ans = 0;
	static LinkedList<Number>[] babels;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		babels = new LinkedList[N + 1];
		for (int i = 1; i <= N; i++) {
			babels[i] = new LinkedList<Number>();
		}

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				int tmpN = Integer.parseInt(st.nextToken());
				Number tmp = new Number(tmpN, false);
				babels[i].add(tmp);
			}
		}

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			rotate(x, d, k);
		}

		int sum = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = 0; j < M; j++) {
				Number tmp = babels[i].get(j);
				if (tmp.num != 0) {
					sum += tmp.num;
				}
			}
		}
		System.out.println(sum);
	}

	private static void rotate(int x, int d, int k) {
		for (int i = 1; i <= N; i++) {
			if (i % x == 0) {
				if (d == 0) { // 시계방향 회전
					for (int j = 0; j < k; j++) {
						Number tmp = babels[i].pollLast();
						babels[i].addFirst(tmp);
					}
				} else {
					for (int j = 0; j < k; j++) {
						Number tmp = babels[i].pollFirst();
						babels[i].addLast(tmp);
					}
				}
			}
		}

		// 인접 지우기
		Number find;
		int cnt = 0;

		for (int i = 1; i <= N; i++) {
			for (int j = 0; j < M; j++) {
				find = babels[i].get(j);
				if (find.num != 0) {
					int left = j - 1;
					int right = j + 1;
					if (left == -1)
						left = M - 1;
					if (right == M)
						right = 0;

					if (find.num == babels[i].get(left).num) {
						Number tmp = babels[i].get(left);
						tmp.check = true;
						find.check = true;
						babels[i].set(left, tmp);
						cnt++;
					}
					if (find.num == babels[i].get(right).num) {
						Number tmp = babels[i].get(right);
						tmp.check = true;
						find.check = true;
						babels[i].set(right, tmp);
						cnt++;
					}
					if (i != N && find.num == babels[i + 1].get(j).num) {
						Number tmp = babels[i + 1].get(j);
						tmp.check = true;
						find.check = true;
						babels[i + 1].set(j, tmp);
						cnt++;
					}
				}
			}
		}

		if (cnt != 0) {
			for (int i = 1; i <= N; i++) {
				for (int j = 0; j < M; j++) {
					Number tmp = babels[i].get(j);
					if (tmp.num != 0) {
						if (tmp.check) {
							tmp.num = 0;
							tmp.check = false; // 안해줘도됨
							babels[i].set(j, tmp);
						}
					}
				}
			}
		} else {
			double sum = 0;
			cnt = 0;
			for (int i = 1; i <= N; i++) {
				for (int j = 0; j < M; j++) {
					Number tmp = babels[i].get(j);
					if (tmp.num != 0) {
						sum += tmp.num;
						cnt++;
					}
				}
			}
			double avg = sum / cnt;
			for (int i = 1; i <= N; i++) {
				for (int j = 0; j < M; j++) {
					Number tmp = babels[i].get(j);
					if (tmp.num != 0) {
						if (tmp.num > avg) {
							tmp.num--;
							babels[i].set(j, tmp);
						} else if (tmp.num < avg) {
							tmp.num++;
							babels[i].set(j, tmp);
						}
					}
				}
			}
		}
	}

	private static class Number {
		int num;
		boolean check = false;

		public Number(int num, boolean check) {
			super();
			this.num = num;
			this.check = check;
		}
	}
}