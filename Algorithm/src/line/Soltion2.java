package Algorithm.src.line;

import java.util.Arrays;

public class Soltion2 {

	public static void main(String[] args) {
		String[] pass = { "AaTa+!12-3", "aaaaZZZZ)", "CaCbCgCdC888834A", "UUUUU", "ZzZz9Z824" };
		for (int i = 0; i < pass.length; i++) {
			System.out.println(Arrays.toString(solution(pass[i])));
		}
	}

	private static int[] solution(String inp_str) {
		boolean[] check = new boolean[5];

		if (!(inp_str.length() >= 8 && inp_str.length() <= 15)) {
			check[0] = true;
		}

		int cntChar = 0;
		boolean[] checkChar = new boolean[4];
		int[] count = new int[72];
		for (int i = 0; i < inp_str.length(); i++) {
			char c = inp_str.charAt(i);
			if (c >= 97 && c <= 122) {
				checkChar[0] = true;
				count[c - 97]++;
			} else if (c >= 65 && c <= 90) {
				checkChar[1] = true;
				count[c - 65 + 26]++;
			} else if (c >= 48 && c <= 57) {
				checkChar[2] = true;
				count[c - 48 + 52]++;
			} else if ("~!@#$%^&*".indexOf(c) != -1) {
				checkChar[3] = true;
				int index = "~!@#$%^&*".indexOf(c);
				count[62 + index]++;
			} else {
				check[1] = true;
			}
		}

		for (int j = 0; j < 4; j++) {
			if (checkChar[j])
				cntChar++;
		}

		if (cntChar < 3) {
			check[2] = true;
		}

		int maxSame = 0;
		for (int i = 0; i < inp_str.length() - 4; i++) {
			int cntSame = 1;
			char c1 = inp_str.charAt(i);
			char c2 = inp_str.charAt(i + 1);
			char c3 = inp_str.charAt(i + 2);
			char c4 = inp_str.charAt(i + 3);
			char c5 = inp_str.charAt(i + 4);

			if (c1 == c2) {
				cntSame++;
			}
			if (c2 == c3) {
				cntSame++;
			}
			if (c3 == c4) {
				cntSame++;
			}
			if (c4 == c5) {
				cntSame++;
			}
			maxSame = Math.max(maxSame, cntSame);
		}
		if (maxSame >= 4) {
			check[3] = true;
		}

		for (int i = 0; i < 72; i++) {
			if (count[i] >= 5)
				check[4] = true;
		}

		int answerCnt = 0;
		for (int i = 0; i < 5; i++) {
			if (check[i]) {
				answerCnt++;
			}
		}

		int[] answer;
		if (answerCnt != 0) {
			answer = new int[answerCnt];
			int tmp = 0;
			for (int i = 0; i < 5; i++) {
				if (check[i]) {
					answer[tmp] = i + 1;
					tmp++;
				}
			}
		} else {
			answer = new int[1];
			answer[0] = 0;
		}
		return answer;
	}

}
