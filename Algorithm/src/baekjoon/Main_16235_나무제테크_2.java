package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main_16235_나무제테크_2 {

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

		LinkedList<Tree> alive = new LinkedList<>();

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
			alive.add(new Tree(r, c, age));
		}

		for (int k = 0; k < K; k++) {
			// 봄
			ArrayList<Tree> deadTree = new ArrayList<>();
			ArrayList<Tree> aliveTree = new ArrayList<>();

			Collections.sort(alive);

			while (!alive.isEmpty()) {
				Tree tree = alive.poll();
				int res = ground[tree.r][tree.c] - tree.age;
				if (res < 0) {
					deadTree.add(tree);
				} else {
					ground[tree.r][tree.c] = res;
					tree.age++;
					aliveTree.add(tree);
				}
			}

			// alive.clear();
			for (Tree tree : aliveTree) {
				alive.add(tree);
			}

			// 여름
			int size = deadTree.size();
			for (int i = 0; i < size; i++) {
				Tree dead = (Tree) deadTree.get(i);
				ground[dead.r][dead.c] += dead.age / 2;
			}

			// 가을
			while (!alive.isEmpty()) {
				Tree tree = alive.poll();
				if (tree.age % 5 == 0) {
					for (int d = 0; d < 8; d++) {
						int nr = tree.r + dir[d][0];
						int nc = tree.c + dir[d][1];
						if ((nr > -1 && nr < N && nc > -1 && nc < N)) {
							aliveTree.add(new Tree(nr, nc, 1));
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

			// alive.clear();
			for (Tree tree : aliveTree) {
				alive.add(tree);
			}
		}

		System.out.println(alive.size());
	}

	static private class Tree implements Comparable<Tree> {
		int r, c, age;

		public Tree(int r, int c, int age) {
			this.r = r;
			this.c = c;
			this.age = age;
		}

		@Override
		public int compareTo(Tree o) {
			if (this.age > o.age) {
				return 1; // x에 대해서는 오름차순
			} else if (this.age == o.age) {
				return 0;
			} else {
				return -1;
			}
		}
	}
}
