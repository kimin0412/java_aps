package Algorithm.src.samsung;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class dd2 {
	static int[][] map;
//	static PriorityQueue<Fish> fishes;
//	static Queue<Fish> fishes;
	static HashMap<Integer, Fish> fishes;
	static int[][] direction = { {}, { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 },
			{ -1, 1 } };
	static int ans = 0;
//	static int sngR, sngC;

	public static void main(String[] args) throws Exception {
		map = new int[4][4];
//		fishes = new PriorityQueue<Fish>();
		fishes = new HashMap<Integer, Fish>();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < 4; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 4; j++) {
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				Fish f = new Fish(i, j, b);
				fishes.put(a, f);
				map[i][j] = a;
			}
		}

		int firstN = map[0][0];
		map[0][0] = 0;
		Fish firstF = fishes.get(firstN);
		Shark shark = new Shark(0, 0, firstF.dir, firstN);
		fishes.put(firstN, new Fish(-1, -1, 0));

		fishMove(shark);

//		PriorityQueue<Fish> fishes2 = new PriorityQueue<Fish>();
//		PriorityQueue<Fish> fishes2 = new PriorityQueue<Fish>();
//		fishes2 = fishes;
//		int cnt = fishes.size();
//		for (int i = 0; i < cnt; i++) {
//			System.out.println(i);
//			System.out.println(fishes.poll());
//		}

//		for (int i = 0; i < cnt; i++) {
//			System.out.println(i);
//			if (i == 1)
//				System.out.println(fishes.poll());
//		}

	}

	private static void fishMove(Shark shark) {
//		int[][] tmpMap;
		for (int i = 1; i <= 16; i++) {
			Fish f = fishes.get(i);
			if (f.dir != 0) {
				int fr = f.r;
				int fc = f.c;
				int fd = f.dir;
				
				boolean[] check = new boolean[9];
				int ro = fd;
				while (true) {
					if (check[fd])
						break;
					else {

					}
				}
			}

			if (f.dir != 0) {
				int fr = f.r;
				int fc = f.c;
				int fd = f.dir;

				int nr = fr + direction[fd][0];
				int nc = fc + direction[fd][1];
				if (isIn(nr, nc) && !(shark.r == nr && shark.c == nc)) {
					Fish nextFish = fishes.get(map[nr][nc]);

					int tmp = map[fr][fc];
					map[fr][fc] = map[nr][nc];
					map[nr][nc] = tmp;

					fishes.put(map[fr][fc], nextFish);
					fishes.put(map[nr][nc], f);

				} else {

				}

			}
		}

	}

	private static boolean isIn(int r, int c) {
		return r > -1 && r < 4 && c > -1 && c < 4;
	}

//	static class Fish implements Comparable<Fish> {
	static class Fish {
		int r;
		int c;
		int dir;

		public Fish(int r, int c, int dir) {
			super();
			this.r = r;
			this.c = c;
			this.dir = dir;
		}

//		@Override
//		public int compareTo(Fish o) {
//			if (o.num < this.num)
//				return 1;
//			return -1;
//		}

		@Override
		public String toString() {
			return "Fish [r=" + r + ", c=" + c + ", dir=" + dir + "]";
		}

	}

	static class Shark {
		int r, c, dir, eat;

		public Shark(int r, int c, int dir, int eat) {
			super();
			this.r = r;
			this.c = c;
			this.dir = dir;
			this.eat = eat;
		}

	}
}
