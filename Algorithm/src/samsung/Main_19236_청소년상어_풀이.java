package Algorithm.src.samsung;

import java.util.*;
import java.io.*;

class Main_19236_청소년상어_풀이 {

	// 0 부터 7 까지 순서대로 ↑, ↖, ←, ↙, ↓, ↘, →, ↗
	static int[] dr = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dc = { 0, -1, -1, -1, 0, 1, 1, 1 };
	static int maxSum = 0;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int[][] arr = new int[4][4];
		List<Fish> fishes = new ArrayList<>();

		// input
		for (int i = 0; i < 4; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < 4; j++) {
				Fish f = new Fish();
				f.id = Integer.parseInt(st.nextToken());
				f.dir = Integer.parseInt(st.nextToken()) - 1;
				f.r = i;
				f.c = j;

				fishes.add(f);
				arr[i][j] = f.id;
			}
		}

		// 물고기는 작은 순서부터 이동해야 하기 때문에 미리 정렬해둠
		Collections.sort(fishes, new Comparator<Fish>() {
			@Override
			public int compare(Fish o1, Fish o2) {
				return o1.id - o2.id;
			}
		});

		// 상어는 (0, 0) 물고기를 먹고 시작하며 위치는 -1 로 표현
		Fish f = fishes.get(arr[0][0] - 1);
		Shark shark = new Shark(0, 0, f.dir, f.id);
		f.dead = true;
		arr[0][0] = -1;

		// solution
		sharkEat(arr, shark, fishes);
		System.out.println(maxSum);
	}

	// 재귀로 상어가 이동할 수 없을 때까지 반복한다.
	static void sharkEat(int[][] arr, Shark shark, List<Fish> fishes) {
		// 잡아먹은 양의 최대값 구하기
		maxSum = Math.max(maxSum, shark.eat);
//		if (maxSum < shark.eat) {
//			maxSum = shark.eat;
//		}

		// 모든 물고기 순서대로 이동
		fishes.forEach(e -> moveFish(e, arr, fishes));

		for (int dist = 1; dist < 4; dist++) {
			int nr = shark.r + dr[shark.dir] * dist;
			int nc = shark.c + dc[shark.dir] * dist;
//			0 <= nx && nx < 4 && 0 <= ny && ny < 4
			if (isIn(nr, nc) && arr[nr][nc] > 0) {
				// 물고기 잡아먹고 dfs
				int[][] arrCopies = copyArr(arr);
				List<Fish> fishCopies = copyFishes(fishes);

				arrCopies[shark.r][shark.c] = 0;
				Fish f = fishCopies.get(arr[nr][nc] - 1);
				Shark newShark = new Shark(f.r, f.c, f.dir, shark.eat + f.id);
				f.dead = true;
				arrCopies[f.r][f.c] = -1;

				sharkEat(arrCopies, newShark, fishCopies);
			}
		}
	}

	private static boolean isIn(int nr, int nc) {
		return -1 < nr && nr < 4 && -1 < nc && nc < 4;
	}

	// 이동가능한 칸은 빈칸, 다른 물고기가 있는 칸
	// 45도 반시계 방향으로 회전하면서 스캔
	static void moveFish(Fish fish, int[][] arr, List<Fish> fishes) {
		if (fish.dead == true)
			return;

		for (int i = 0; i < 8; i++) {
			int nextDir = (fish.dir + i) % 8;
			int nr = fish.r + dr[nextDir];
			int nc = fish.c + dc[nextDir];

			if (isIn(nr, nc) && arr[nr][nc] > -1) {
				arr[fish.r][fish.c] = 0;

				if (arr[nr][nc] == 0) {
					fish.r = nr;
					fish.c = nc;
				} else {
					Fish tmp = fishes.get(arr[nr][nc] - 1);
					tmp.r = fish.r;
					tmp.c = fish.c;
					arr[fish.r][fish.c] = tmp.id;

					fish.r = nr;
					fish.c = nc;
				}

				arr[nr][nc] = fish.id;
				fish.dir = nextDir;
				return;
			}
		}
	}

	static int[][] copyArr(int[][] arr) {
		int[][] temp = new int[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				temp[i][j] = arr[i][j];
			}
		}

		return temp;
	}

	static List<Fish> copyFishes(List<Fish> fishes) {
		List<Fish> tmp = new ArrayList<>();
		fishes.forEach(e -> tmp.add(new Fish(e.id, e.r, e.c, e.dir, e.dead)));
		return tmp;
	}

	static class Shark {
		int r, c, dir, eat;

		Shark() {
		}

		Shark(int r, int c, int dir, int eat) {
			this.r = r;
			this.c = c;
			this.dir = dir;
			this.eat = eat;
		}
	}

	static class Fish {
		int id, r, c, dir;
		boolean dead = false;

		Fish() {
		}

		Fish(int id, int r, int c, int dir, boolean dead) {
			this.id = id;
			this.r = r;
			this.c = c;
			this.dir = dir;
			this.dead = dead;
		}
	}
}