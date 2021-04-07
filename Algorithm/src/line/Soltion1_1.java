package Algorithm.src.line;

public class Soltion1_1 {

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
		String[] answer = new String[5];

		int[] hap = new int[5];
		for (int i = 0; i < 5; i++) {
			String[] strArr = table[i].split(" ");
			answer[i] = strArr[0];
			for (int j = 1; j < strArr.length; j++) {
				for (int k = 0; k < languages.length; k++) {
					if (strArr[j].equals(languages[k])) {
						hap[i] += preference[k] * (5 - j + 1);
					}
				}
			}

		}

		int max = 0;
		int maxIdx = 0;
		for (int i = 0; i < 5; i++) {
			if (max < hap[i]) {
				max = hap[i];
				maxIdx = i;
			} else if (max == hap[i]) {
				if (answer[maxIdx].compareTo(answer[i]) > 0) {
					max = hap[i];
					maxIdx = i;
				}
			}
		}
		return answer[maxIdx];
	}
}
