package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/* N X N 크기의 땅 
 * 처음 양분은 모든 칸에 5씩 들어있음
 * M개의 나무를 구매해 땅에 심음
 * 봄 : 나무가 자신의 나이만큼 양분을 먹고, 나이가 1 증가, 해당 칸에 있는 양분만 먹을 수 있음
 * 여름 : 봄에 죽은 나무가 양분으로 변함. 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가됨.
 * 가을 : 나무가 번식함. 나무의 나이가 5의 배수이어야 하며, 인접8칸에 나이가 1인 나무가 생김
 * 겨울 : 양분 추가
 */
public class Main_16235_나무제테크_동우오빠소스 {
	static int N; // 땅의 크기
	static int M; // 나무의 수
	static int K;
	static int[][] map;
	static int[][] A;
	static PriorityQueue<Tree> trees;
	static Queue<Tree> alive;
	static Queue<Tree> dead;
	static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		A = new int[N][N];
		trees = new PriorityQueue<>();
		alive = new LinkedList<>();
		dead = new LinkedList<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(in.readLine());
			Arrays.fill(map[i], 5);
			for (int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(in.readLine());

			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int z = Integer.parseInt(st.nextToken());
			trees.add(new Tree(x, y, z));
		}

		for (int i = 0; i < K; i++) {
			spring();
			summer();
			fall();
			winter();
		}

		System.out.println(trees.size());

	}

	private static void fall() {
		while (!alive.isEmpty()) {
			Tree tree = alive.poll();
			int r = tree.r;
			int c = tree.c;
			int age = tree.age;

			if (age % 5 == 0) {
				for (int i = 0; i < 8; i++) {
					int nr = r + dir[i][0];
					int nc = c + dir[i][1];
					if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
						trees.offer(new Tree(nr, nc, 1));
					}
				}
			}
			trees.offer(new Tree(r, c, age));
		}
	}

	private static void summer() {
		while (!dead.isEmpty()) {
			Tree tree = dead.poll();
			int r = tree.r;
			int c = tree.c;
			int age = tree.age;
			map[r][c] += (age / 2);
		}
	}

	static class Tree implements Comparable<Tree> {
		int r;
		int c;
		int age;

		Tree() {
		}

		Tree(int r, int c, int age) {
			this.r = r;
			this.c = c;
			this.age = age;
		}

		@Override
		public int compareTo(Tree o) {
			return Integer.compare(this.age, o.age);
		}
	}

	private static void spring() {
		while (!trees.isEmpty()) {
			Tree tree = trees.poll();
			int r = tree.r;
			int c = tree.c;
			int age = tree.age;

			if (map[r][c] >= age) {
				map[r][c] -= age;
				alive.offer(new Tree(r, c, age + 1));
			} else {
				dead.offer(new Tree(r, c, age));
			}
		}
	}

	private static void winter() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] += A[i][j];
			}
		}
	}

}
