package Algorithm.src.line;

import java.util.Arrays;

public class Soltion1 {

	public static void main(String[] args) {
		String[] table1 = { "SI JAVA JAVASCRIPT SQL PYTHON C#", "CONTENTS JAVASCRIPT JAVA PYTHON SQL C++",
				"HARDWARE C C++ PYTHON JAVA JAVASCRIPT", "PORTAL JAVA JAVASCRIPT PYTHON KOTLIN PHP",
				"GAME C++ C# JAVASCRIPT C JAVA" };
		String[] languages1 = { "PYTHON", "C++", "SQL" };
		int[] preference1 = { 7, 5, 5 };
		String[] table2 = { "SI JAVA JAVASCRIPT SQL PYTHON C#", "CONTENTS JAVASCRIPT JAVA PYTHON SQL C++",
				"HARDWARE C C++ PYTHON JAVA JAVASCRIPT", "PORTAL JAVA JAVASCRIPT PYTHON KOTLIN PHP",
				"GAME C++ C# JAVASCRIPT C JAVA" };
		String[] languages2 = { "JAVA", "JAVASCRIPT" };
		int[] preference2 = { 7, 5 };

		System.out.println(solution(table1, languages1, preference1));
		System.out.println(solution(table2, languages2, preference2));
	}

	public static String solution(String[] table, String[] languages, int[] preference) {
		String answer = "";
//		int idx = 0;
		int[][] lanScore = new int[5][languages.length];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < languages.length; j++) {
				lanScore[i][j] = 6;
			}
		}

		for (int[] is : lanScore) {
			System.out.println(Arrays.toString(is));
		}

		for (int i = 0; i < table.length; i++) {
			String[] strArr = table[i].split(" ");
			for (int j = 0; j < strArr.length; j++) {
				for (int k = 0; k < languages.length; k++) {
					if (strArr[j] != languages[k]) {
						System.out.println(strArr[j] + " " + languages[k]);
						lanScore[i][k]--;
					}
				}
//				System.out.println(strArr[j]);
			}

		}
		for (int[] is : lanScore) {
			System.out.println(Arrays.toString(is));
		}
		return answer;
	}
}
