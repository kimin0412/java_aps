package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * pushBlue, pushGreen 다르게 했더니 맞음**
 * 확인하기***
 * 
 */
public class Main_20061_모노미노도미노2_틀린점찾기2 {
	static int[][] redMap, blueMap, greenMap;
	static int ans = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		redMap = new int[4][4];
		blueMap = new int[4][6];
		greenMap = new int[6][4];

		int N = Integer.parseInt(br.readLine());
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int t = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			blueStart(t, x, y);
			greenStart(t, x, y);

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
			greenMap[0][y] = 1;
			for (int i = 1; i < 6; i++) {
				if (greenMap[i][y] == 0) {
					greenMap[i - 1][y] = 0;
					greenMap[i][y] = 1;
				} else
					break;
			}
			break;
		case 2:
			greenMap[0][y] = 1;
			greenMap[0][y + 1] = 1;
			for (int i = 1; i < 6; i++) {
				if (greenMap[i][y] == 0 && greenMap[i][y + 1] == 0) {
					greenMap[i - 1][y] = 0;
					greenMap[i - 1][y + 1] = 0;
					greenMap[i][y] = 1;
					greenMap[i][y + 1] = 1;
				} else
					break;
			}
			break;
		case 3:
			greenMap[0][y] = 1;
			greenMap[1][y] = 1;
			for (int i = 2; i < 6; i++) {
				if (greenMap[i][y] == 0) {
					greenMap[i - 2][y] = 0;
					greenMap[i][y] = 1;
				} else
					break;
			}
			break;
		}
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
			pushGreen(1);
		} else if (cnt == 2) {
			pushGreen(2);
		}
	}

	private static void pushGreen(int count) {
		for (int i = 5; i >= 2; i--) {
			for (int j = 0; j < 4; j++)
				greenMap[i][j] = greenMap[i - count][j];
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 4; j++)
				greenMap[i][j] = 0;
	}

	private static void getGreenScore() {
		for (int i = 2; i < 6; i++) {
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

	private static void blueStart(int t, int x, int y) {
		switch (t) {
		case 1:
			blueMap[x][0] = 1;
			for (int i = 1; i < 6; i++) {
				if (blueMap[x][i] == 0) {
					blueMap[x][i - 1] = 0;
					blueMap[x][i] = 1;
				} else
					break;
			}
			break;
		case 2:
			blueMap[x][0] = 1;
			blueMap[x][1] = 1;
			for (int i = 2; i < 6; i++) {
				if (blueMap[x][i] == 0) {
					blueMap[x][i - 2] = 0;
					blueMap[x][i] = 1;
				} else
					break;
			}
			break;
		case 3:
			blueMap[x][0] = 1;
			blueMap[x + 1][0] = 1;
			for (int i = 1; i < 6; i++) {
				if (blueMap[x][i] == 0 && blueMap[x + 1][i] == 0) {
					blueMap[x][i - 1] = 0;
					blueMap[x + 1][i - 1] = 0;
					blueMap[x][i] = 1;
					blueMap[x + 1][i] = 1;
				} else
					break;
			}
			break;

		}
		getBlueScore();
		checkBlue();
	}

	private static void checkBlue() {
		int cnt = 0;
		for (int i = 0; i < 2; i++) {
			if (blueMap[0][i] == 1 || blueMap[1][i] == 1 || blueMap[2][i] == 1 || blueMap[3][i] == 1) {
				cnt++;
			}
		}
		if (cnt == 1) {
			pushBlue(1);
		} else if (cnt == 2) {
			pushBlue(2);
		}
	}

	private static void pushBlue(int count) {
		for (int i = 5; i >= 2; i--) {
			for (int j = 0; j < 4; j++)
				blueMap[j][i] = blueMap[j][i - count];
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 4; j++)
				blueMap[j][i] = 0;
	}

	private static void getBlueScore() {
		for (int i = 2; i < 6; i++) {
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