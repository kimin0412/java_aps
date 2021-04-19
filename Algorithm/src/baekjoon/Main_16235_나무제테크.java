package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_16235_나무제테크 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };

		int[][] Arr = new int[N][N];
		int[][] ground = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				ground[i][j] = 5;
			}
		}

		ArrayList[][] map = new ArrayList[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = new ArrayList<Tree>();
			}
		}

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				Arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int age = Integer.parseInt(st.nextToken());
			map[r][c].add(new Tree(r, c, age, true));
		}

		// for (int i = 0; i < N; i++) {
		// for (int j = 0; j < N; j++) {
		// System.out.println("i, j : " + i + " " + j);
		// map[i][j].forEach(e -> System.out.print(e.toString() + " "));
		// map[i][j] = new ArrayList<Tree>();
		// }
		// }

		for (int k = 0; k < K; k++) {
			// System.out.println("K : " + (k + 1));
			// 봄
			ArrayList<Tree> deadTree = new ArrayList<>();
			ArrayList<Integer> deadNum = new ArrayList<>();

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// System.out.println("i, j : " + i + " " + j);
					Collections.sort(map[i][j]);
					// map[i][j].forEach(e -> System.out.print(e.toString() + " "));
					// System.out.println();
					int size = map[i][j].size();
					// System.out.println(size);
					for (int aa = 0; aa < size; aa++) {
						// System.out.println(aa);
						Tree tmp = (Tree) map[i][j].get(aa);
						// if (tmp.alive) {
						int res = ground[i][j] - tmp.age;
						if (res < 0) {
							// System.out.println(tmp.age + " 죽음 ");
							deadTree.add(tmp);
							deadNum.add(aa);
							// map[i][j].remove(aa);
						} else {
							// System.out.println("res : " + res);
							ground[i][j] = res;
							tmp.age++;
							map[i][j].set(aa, tmp);
						}
						// }
					}
				}
			}

			System.out.println("$#$#$#$# 여름 전 #$%#$#$#");
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					System.out.println("i, j : " + i + " " + j);
					map[i][j].forEach(e -> System.out.print(e.toString() + " "));
					System.out.println();
				}
			}

			System.out.println("\ndeadTree");
			deadTree.forEach(e -> System.out.print(e.toString()));

			System.out.println("\ndeadNum");
			deadNum.forEach(e -> System.out.print(e.toString()));

			// 여름
			for (int i = 0; i < deadTree.size(); i++) {
				Tree dead = (Tree) deadTree.get(i);
				ground[dead.r][dead.c] += dead.age / 2;
				// System.out.println(deadTree.get(deadNum.get(i)));
				System.out.println(deadNum.get(i));
				map[dead.r][dead.c].remove(deadNum.get(i));
				// map[dead.r][dead.c].remove(1);
				System.out.print("지우고 ");
				map[dead.r][dead.c].forEach(e -> System.out.print(e.toString() + " "));
			}

			System.out.println("$#$#$#$# 여름 끝 #$%#$#$#");
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					System.out.println("i, j : " + i + " " + j);
					map[i][j].forEach(e -> System.out.print(e.toString() + " "));
					System.out.println();
				}
			}

			// 가을
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// Collections.sort(map[i][j]);
					int size = map[i][j].size();
					for (int aa = 0; aa < size; aa++) {
						Tree tmp = (Tree) map[i][j].get(aa);
						if (tmp.age % 5 == 0) {
							System.out.println("번식 i, j : " + i + " " + j);
							for (int d = 0; d < 8; d++) {
								int nr = tmp.r + dir[d][0];
								int nc = tmp.c + dir[d][1];
								if ((nr > -1 && nr < N && nc > -1 && nc < N)) {
									map[nr][nc].add(new Tree(nr, nc, 1, true));
								}
							}
						}
					}
				}
			}

			// 겨울
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					ground[i][j] += Arr[i][j];
				}
			}

			for (int[] s : ground) {
				System.out.println(Arrays.toString(s));
			}
			System.out.println();

		}

		int sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += map[i][j].size();
			}
		}
		System.out.println(sum);

	}

	// static private class Tree implements Comparator<Tree> {
	static private class Tree implements Comparable<Tree> {
		// static private class Tree{
		int r, c, age;
		boolean alive;

		public Tree(int r, int c, int age, boolean alive) {
			this.r = r;
			this.c = c;
			this.age = age;
			this.alive = alive;
		}

		// @Override
		// public int compare(Tree o1, Tree o2) {
		// // return o2.age - o1.age;
		// if (o1.age > o2.age)
		// return 1;
		// if (o1.age < o2.age)
		// return -1;
		// return 0;
		// }

		@Override
		public String toString() {
			return "Tree [age=" + age + ", alive=" + alive + ", c=" + c + ", r=" + r + "]";
		}

		// @Override
		// public int compareTo(Tree o) {
		// if (this.age > o.age) {
		// return 1; // x에 대해서는 오름차순
		// } else
		// return -1;
		// }

		@Override
		public int compareTo(Tree o) {
			if (this.age > o.age) {
				return 1; // x에 대해서는 오름차순
			} else
				return -1;
		}

	}
}
