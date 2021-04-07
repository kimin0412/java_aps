package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_20061_모노미노도미노2_틀린점찾기 {
	static int[][] redMap, blueMap, greenMap;
	static int ans = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		redMap = new int[4][4];
		blueMap = new int[4][6];
		greenMap = new int[6][4];

		int N = Integer.parseInt(br.readLine());
		for (int i = 0; i < N; i++) {
//			System.out.println(i);
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int t = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			blockStart(t, x, y);
//			blueStart(t, x, y);
//			greenStart(t, x, y);
			
//			System.out.println("초록 ");
//			for (int[] s : greenMap) {
//				System.out.println(Arrays.toString(s));
//			}
//			
//			System.out.println("파랑 ");
//			for (int[] s : blueMap) {
//				System.out.println(Arrays.toString(s));
//			}
		}
		System.out.println(ans);
		int cnt = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				if (blueMap[i][j] == 1)
					cnt++;
			}
		}
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				if (greenMap[i][j] == 1)
					cnt++;
			}
		}
		System.out.println(cnt);
	}

	private static void greenStart(int t, int x, int y) {
		switch (t) {
		case 1:
			boolean flag = true;
			for (int i = 0; i < 6; i++) {
				if (greenMap[i][y] == 1) {
					greenMap[i - 1][y] = 1;
					flag = false;
					break;
				}
			}
			if (flag)
				greenMap[5][y] = 1;
			break;
		case 2:
			flag = true;
			for (int i = 0; i < 6; i++) {
				if (greenMap[i][y] == 1 || greenMap[i][y + 1] == 1) {
					greenMap[i - 1][y] = 1;
					greenMap[i - 1][y + 1] = 1;
					flag = false;
					break;
				}
			}
			if (flag) {
				greenMap[5][y] = 1;
				greenMap[5][y + 1] = 1;
			}
			break;
		case 3:
			flag = true;
			for (int i = 0; i < 6; i++) {
				if (greenMap[i][y] == 1) {
					greenMap[i - 2][y] = 1;
					greenMap[i - 1][y] = 1;
					flag = false;
					break;
				}
			}
			if (flag) {
				greenMap[4][y] = 1;
				greenMap[5][y] = 1;
			}
			break;
		}
//		System.out.println("초록 ");
//		for (int[] s : greenMap) {
//			System.out.println(Arrays.toString(s));
//		}
		getGreenScore();
		checkGreen();
	}

	private static void checkGreen() {
		int cnt = 0;
		for (int i = 0; i < 2; i++) {
			if (greenMap[i][0] == 1 || greenMap[i][1] == 1 || greenMap[i][2] == 1 || greenMap[i][3] == 1) {
				cnt++;
			}
		}
		if (cnt == 1) {
			greenMap[5][0] = 0;
			greenMap[5][1] = 0;
			greenMap[5][2] = 0;
			greenMap[5][3] = 0;
			moveDown(5);
			greenMap[1][0] = 0;
			greenMap[1][1] = 0;
			greenMap[1][2] = 0;
			greenMap[1][3] = 0;
		} else if (cnt == 2) {
			greenMap[5][0] = 0;
			greenMap[5][1] = 0;
			greenMap[5][2] = 0;
			greenMap[5][3] = 0;
			greenMap[4][0] = 0;
			greenMap[4][1] = 0;
			greenMap[4][2] = 0;
			greenMap[4][3] = 0;
			moveDown(5);
			moveDown(4);
			greenMap[0][0] = 0;
			greenMap[0][1] = 0;
			greenMap[0][2] = 0;
			greenMap[0][3] = 0;
			greenMap[1][0] = 0;
			greenMap[1][1] = 0;
			greenMap[1][2] = 0;
			greenMap[1][3] = 0;
		}
	}

	private static void getGreenScore() {
		for (int i = 0; i < 6; i++) {
			boolean flag = true;
			if (greenMap[i][0] == 1 && greenMap[i][1] == 1 && greenMap[i][2] == 1 && greenMap[i][3] == 1) {
				greenMap[i][0] = 0;
				greenMap[i][1] = 0;
				greenMap[i][2] = 0;
				greenMap[i][3] = 0;
				ans++;
				moveDown(i);
			}
		}
	}

	private static void moveDown(int r) {
		for (int i = r; i > 0; i--) {
			greenMap[i][0] = greenMap[i - 1][0];
			greenMap[i][1] = greenMap[i - 1][1];
			greenMap[i][2] = greenMap[i - 1][2];
			greenMap[i][3] = greenMap[i - 1][3];
		}
	}

	private static void blockStart(int t, int x, int y) {
		int idx;
		switch (t) {
		case 1:
			blueMap[x][0] = 1;
			idx = 1;
			while (idx < 6 && blueMap[x][idx] == 0) {
				blueMap[x][idx - 1] = 0;
				blueMap[x][idx] = 1;
				idx++;
			}
			idx = 1;
			while (idx < 6 && greenMap[idx][y] == 0) {
				greenMap[idx - 1][y] = 0;
				greenMap[idx][y] = 1;
				idx++;
			}
			break;
		case 2:
			blueMap[x][0] = 1;
			blueMap[x][1] = 1;
			greenMap[0][y] = 1;
			greenMap[0][y + 1] = 1;
			
			idx = 2;
			while (idx < 6 && blueMap[x][idx] == 0) {
				blueMap[x][idx - 2] = 0;
				blueMap[x][idx] = 1;
				idx++;
			}
			idx = 1;
			while (idx < 6 && greenMap[idx][y] == 0 && greenMap[idx][y + 1] == 0) {
				greenMap[idx - 1][y] = 0;
				greenMap[idx - 1][y + 1] = 0;
				greenMap[idx][y] = 1;
				greenMap[idx][y + 1] = 1;
				idx++;
			}
			break;
		case 3:
			blueMap[x][0] = 1;
			blueMap[x + 1][0] = 1;
			greenMap[0][y] = 1;
			greenMap[1][y] = 1;
			idx = 1;
			while (idx < 6 && blueMap[x][idx] == 0 && blueMap[x + 1][idx] == 0) {
				blueMap[x][idx - 1] = 0;
				blueMap[x + 1][idx - 1] = 0;
				blueMap[x][idx] = 1;
				blueMap[x + 1][idx] = 1;
				idx++;
			}
			idx = 2;
			while (idx < 6 && greenMap[idx][y] == 0) {
				greenMap[idx - 2][y] = 0;
				greenMap[idx][y] = 1;
				idx++;
			}
			break;
		}
//		System.out.println("파랑 ");
//		for (int[] s : blueMap) {
//			System.out.println(Arrays.toString(s));
//		}
		getBlueScore();
		checkBlue();
		getGreenScore();
		checkGreen();
//		System.out.println("파랑 후 ");
//		for (int[] s : blueMap) {
//			System.out.println(Arrays.toString(s));
//		}
	}

	private static void checkBlue() {
		int cnt = 0;
		for (int i = 0; i < 2; i++) {
			if (blueMap[0][i] == 1 || blueMap[1][i] == 1 || blueMap[2][i] == 1 || blueMap[3][i] == 1) {
				cnt++;
			}
		}
//		System.out.println("cnt : " + cnt);
		if (cnt == 1) {
			blueMap[0][5] = 0;
			blueMap[1][5] = 0;
			blueMap[2][5] = 0;
			blueMap[3][5] = 0;
			moveRight(5);
			blueMap[0][1] = 0;
			blueMap[1][1] = 0;
			blueMap[2][1] = 0;
			blueMap[3][1] = 0;
		} else if (cnt == 2) {
			blueMap[0][5] = 0;
			blueMap[1][5] = 0;
			blueMap[2][5] = 0;
			blueMap[3][5] = 0;
			blueMap[0][4] = 0;
			blueMap[1][4] = 0;
			blueMap[2][4] = 0;
			blueMap[3][4] = 0;
			moveRight(5);
			moveRight(4);
			blueMap[0][0] = 0;
			blueMap[1][0] = 0;
			blueMap[2][0] = 0;
			blueMap[3][0] = 0;
			blueMap[0][1] = 0;
			blueMap[1][1] = 0;
			blueMap[2][1] = 0;
			blueMap[3][1] = 0;
		}
	}

	private static void getBlueScore() {
		for (int i = 0; i < 6; i++) {
			boolean flag = true;
			if (blueMap[0][i] == 1 && blueMap[1][i] == 1 && blueMap[2][i] == 1 && blueMap[3][i] == 1) {
				blueMap[0][i] = 0;
				blueMap[1][i] = 0;
				blueMap[2][i] = 0;
				blueMap[3][i] = 0;
				ans++;
				moveRight(i);
			}
		}

	}

	private static void moveRight(int c) {
		for (int i = c; i > 0; i--) {
			blueMap[0][i] = blueMap[0][i - 1];
			blueMap[1][i] = blueMap[1][i - 1];
			blueMap[2][i] = blueMap[2][i - 1];
			blueMap[3][i] = blueMap[3][i - 1];
		}
	}

}