package Algorithm.src.samsung;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_19236_청소년상어 {
//	static int[][] map;
	static int[][] direction = { {}, { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 },
			{ -1, 1 } };
	static int ans = 0, max = 0;
	static String[] sss = { ".", "↑", "↖", "←", "↙", "↓", "↘", "→", "↗" };

	public static void main(String[] args) throws Exception {
		int[][] map = new int[4][4];
		HashMap<Integer, Fish> fishes = new HashMap<Integer, Fish>();

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

		for (int[] m : map) {
			System.out.println(Arrays.toString(m));
		}
		System.out.println();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (map[i][j] == 0)
					System.out.print(". ");
				else
					System.out.print(fishes.get(map[i][j]).dir + " ");
			}
			System.out.println();
		}
		System.out.println();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (map[i][j] == 0)
					System.out.print(". ");
				else
					System.out.print(sss[fishes.get(map[i][j]).dir] + " ");
			}
			System.out.println();
		}
		System.out.println();

		fishMove(map, shark, fishes);

		System.out.println(max);

	}

	private static void fishMove(int[][] map, Shark shark, HashMap<Integer, Fish> fishes) {

		System.out.println("Fish Move");
		for (int i = 1; i <= 16; i++) {
			if (i == 13)
				System.out.println("************************************************");
			System.out.println(i);

			Fish f = fishes.get(i);
			if (f.dir != 0) {
				int fr = f.r;
				int fc = f.c;
				int fd = f.dir;
				System.out.println("fr, fc, fd : " + fr + " " + fc + " " + fd);

				int nr = fr + direction[fd][0];
				int nc = fc + direction[fd][1];
				if (isIn(nr, nc) && !(shark.r == nr && shark.c == nc)) {
					System.out.println("nr, nc : " + nr + " " + nc);
					Fish tmpFish = new Fish(0, 0, 0);
					Fish nextFish = new Fish(0, 0, 0);
					if (map[nr][nc] != 0) { // 3
						nextFish = fishes.get(map[nr][nc]);
						tmpFish.r = nextFish.r;
						tmpFish.c = nextFish.c;
						tmpFish.dir = fd;
						nextFish.r = fr;
						nextFish.c = fc;
					}
					else {
						tmpFish.r = nr;
						tmpFish.c = nc;
						tmpFish.dir = fd;
					}
					Fish ff = new Fish(0, 0, 0);
					ff.r = tmpFish.r;
					ff.c = tmpFish.c;
					ff.dir = tmpFish.dir;
					fishes.put(map[fr][fc], ff);
					fishes.put(map[nr][nc], nextFish);

					int tmp = map[fr][fc];
					map[fr][fc] = map[nr][nc];
					map[nr][nc] = tmp;

				} else {
					boolean[] check = new boolean[9];
					int ro = fd;
					while (true) {
						System.out.println("ro : " + ro);
						if (check[ro])
							break;
						else {
							int nnr = fr + direction[ro][0];
							int nnc = fc + direction[ro][1];
							System.out.println(nnr + " " + nnc);
							if (isIn(nnr, nnc) && !(shark.r == nnr && shark.c == nnc)) {
								System.out.println("f : " + fr + " " + fc + " " + map[fr][fc]);
								System.out.println("n : " + nnr + " " + nnc + " " + map[nnr][nnc]);
								Fish tmpFish = new Fish(0, 0, 0);
								Fish nextFish = new Fish(0, 0, 0);
								if (map[nnr][nnc] != 0) { // 3
									nextFish = fishes.get(map[nnr][nnc]);
									tmpFish.r = nextFish.r;
									tmpFish.c = nextFish.c;
									tmpFish.dir = ro;
									nextFish.r = fr;
									nextFish.c = fc;
								}else {
									tmpFish.r = nnr;
									tmpFish.c = nnc;
									tmpFish.dir = ro;
								}
								Fish ff = new Fish(0, 0, 0);
								ff.r = tmpFish.r;
								ff.c = tmpFish.c;
								ff.dir = tmpFish.dir;
								System.out.println(map[fr][fc] + " " + ff.toString());
								System.out.println(map[nnr][nnc] + " " + nextFish.toString());
								fishes.put(map[fr][fc], ff);
								fishes.put(map[nnr][nnc], nextFish);

								int tmp = map[fr][fc];
								map[fr][fc] = map[nnr][nnc];
								map[nnr][nnc] = tmp;
								break;
							} else {
								check[ro] = true;
								ro++;
							}
							if (ro == 9)
								ro = 1;
						}
					}
				}

			}
//			for (int[] m : map) {
//				System.out.println(Arrays.toString(m));
//			}
			
			for (int ii = 0; ii < 4; ii++) {
				for (int jj = 0; jj < 4; jj++) {
					System.out.print(map[ii][jj]);
					if (map[ii][jj] == 0)
						System.out.print(". ");
					else
						System.out.print(sss[fishes.get(map[ii][jj]).dir] + " ");
				}
				System.out.println();
			}
			System.out.println();
			
			for (int ii = 0; ii < 4; ii++) {
				for (int jj = 0; jj < 4; jj++) {
					if (map[ii][jj] == 0)
						System.out.print(". ");
					else
						System.out.print(fishes.get(map[ii][jj]).dir + " ");
				}
				System.out.println();
			}
			System.out.println();

			for (int ii = 0; ii < 4; ii++) {
				for (int jj = 0; jj < 4; jj++) {
					if (map[ii][jj] == 0)
						System.out.print(". ");
					else
						System.out.print(sss[fishes.get(map[ii][jj]).dir] + " ");
				}
				System.out.println();
			}
			System.out.println();

//			for (int j = 1; j <= 16; j++) {
//				System.out.print(fishes.get(j).dir + " ");
//				if (j % 4 == 0)
//					System.out.println();
//			}
//			System.out.println();
//			for (int j = 1; j <= 16; j++) {
//				System.out.print(sss[fishes.get(j).dir] + " ");
//				if (j % 4 == 0)
//					System.out.println();
//			}
//			System.out.println();
			System.out.println();
		}
		int sr = shark.r;
		int sc = shark.c;
		int sd = shark.dir;
		int cnt = 0;
		System.out.println("지금 샤크 : " + shark.toString());
		while (true) {
			System.out.println("sd : " + sd);
			sr += direction[sd][0];
			sc += direction[sd][1];
			if (isIn(sr, sc)) {
				int[][] nextMap = new int[4][4];
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						nextMap[i][j] = map[i][j];
					}
				}

				HashMap<Integer, Fish> nexrFishes = new HashMap<Integer, Fish>();
				for (int i = 1; i <= 16; i++) {
					Fish f = fishes.get(i);
					nexrFishes.put(i, f);
				}

				if (map[sr][sc] != 0) {
					int nexttN = map[sr][sc];
					nextMap[sr][sc] = 0;
					Fish nextF = fishes.get(nexttN);
					Shark nextShark = new Shark(sr, sc, nextF.dir, shark.eat + nexttN);
					nexrFishes.put(nexttN, new Fish(-1, -1, 0));
					System.out.println("다음 샤크 : " + nextShark.toString());
					System.out.println("다음 물고기들 : ");
					fishMove(nextMap, nextShark, nexrFishes);
					cnt++;
				}
			} else {
				break;
			}
		}
		if (cnt == 0) {
			max = Math.max(max, shark.eat);
			return;
		}
	}

	private static boolean isIn(int r, int c) {
		return r > -1 && r < 4 && c > -1 && c < 4;
	}

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

		@Override
		public String toString() {
			return "Shark [r=" + r + ", c=" + c + ", dir=" + dir + ", eat=" + eat + "]";
		}
	}
}
