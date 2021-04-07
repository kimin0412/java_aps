package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_20061_모노미노도미노2_풀이 {
	static int score, blue[][], green[][];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		score = 0;
		blue = new int[4][6];
		green = new int[6][4];
		int loop = Integer.parseInt(br.readLine());
		StringTokenizer stz;

		for (int i = 0; i < loop; i++) {
			stz = new StringTokenizer(br.readLine());
			int t = Integer.parseInt(stz.nextToken());
			int x = Integer.parseInt(stz.nextToken());
			int y = Integer.parseInt(stz.nextToken());

			moveBlock(t, x, y);
			getScore();
			pushGreen(checkGreen());
			pushBlue(checkBlue());
		}
		System.out.println(score + "\n" + count());
	}

	public static void moveBlock(int t, int x, int y) {
		int index;
		switch (t) {
		// 1개
		case 1:
			blue[x][0] = 1;
			green[0][y] = 1;
			index = 1;
			while (index < 6 && blue[x][index] == 0) {
				blue[x][index - 1] = 0;
				blue[x][index] = 1;
				index++;
			}
			index = 1;
			while (index < 6 && green[index][y] == 0) {
				green[index - 1][y] = 0;
				green[index][y] = 1;
				index++;
			}
			break;
		// 1x2 ㅡ
		case 2:
			blue[x][0] = 1;
			blue[x][1] = 1;
			green[0][y] = 1;
			green[0][y + 1] = 1;
			index = 2;
			while (index < 6 && blue[x][index] == 0) {
				blue[x][index - 2] = 0;
				blue[x][index] = 1;
				index++;
			}
			index = 1;
			while (index < 6 && green[index][y] == 0 && green[index][y + 1] == 0) {
				green[index - 1][y] = 0;
				green[index - 1][y + 1] = 0;
				green[index][y] = 1;
				green[index][y + 1] = 1;
				index++;
			}
			break;
		// 2x1 ㅣ
		case 3:
			blue[x][0] = 1;
			blue[x + 1][0] = 1;
			green[0][y] = 1;
			green[1][y] = 1;
			index = 1;
			while (index < 6 && blue[x][index] == 0 && blue[x + 1][index] == 0) {
				blue[x][index - 1] = 0;
				blue[x + 1][index - 1] = 0;
				blue[x][index] = 1;
				blue[x + 1][index] = 1;
				index++;
			}
			index = 2;
			while (index < 6 && green[index][y] == 0) {
				green[index - 2][y] = 0;
				green[index][y] = 1;
				index++;
			}
			break;
		}
	}

	public static void getScore() {
		for (int i = 5; i >= 2; i--) {
			int count = 0;
			for (int j = 0; j < 4; j++) {
				if (green[i][j] == 0)
					break;
				else
					count++;
			}
			if (count == 4) {
				score++;
				cleanGreen(i);
				i++;
			}
		}

		for (int i = 5; i >= 2; i--) {
			int count = 0;
			for (int j = 0; j < 4; j++) {
				if (blue[j][i] == 0)
					break;
				else
					count++;
			}
			if (count == 4) {
				score++;
				cleanBlue(i);
				i++;
			}
		}
	}

	public static void cleanGreen(int line) {
		for (int i = line; i > 0; i--) {
			for (int j = 0; j < 4; j++) {
				green[i][j] = green[i - 1][j];
			}
		}
	}

	public static void cleanBlue(int line) {
		for (int j = line; j > 0; j--) {
			for (int i = 0; i < 4; i++) {
				blue[i][j] = blue[i][j - 1];
			}
		}
	}

	public static int checkGreen() {
		int count = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++)
				if (green[i][j] == 1) {
					count++;
					break;
				}
		}

		return count;
	}

	public static int checkBlue() {
		int count = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++)
				if (blue[j][i] == 1) {
					count++;
					break;
				}
		}

		return count;
	}

	public static void pushGreen(int count) {
		for (int i = 5; i >= 2; i--) {
			for (int j = 0; j < 4; j++)
				green[i][j] = green[i - count][j];
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 4; j++)
				green[i][j] = 0;
	}

	public static void pushBlue(int count) {
		for (int i = 5; i >= 2; i--) {
			for (int j = 0; j < 4; j++)
				blue[j][i] = blue[j][i - count];
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 4; j++)
				blue[j][i] = 0;
	}

	public static int count() {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				if (blue[i][j] == 1)
					count++;
				if (green[j][i] == 1)
					count++;
			}
		}

		return count;
	}
}