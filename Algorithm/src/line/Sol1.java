package Algorithm.src.line;

import java.util.Arrays;

public class Sol1 {

	public static void main(String[] args) {
		String[] programs = { "line", "line" };
		String[][] flag_rules = { { "-s STRING", "-n NUMBER", "-e NULL" }, { "-s STRING", "-n NUMBER", "-e NULL" } };
		String[][] commands = { { "line -n 100 -s hi -e", "lien -s Bye" }, { "line -s 123 -n HI", "line fun" } };

		for (int i = 0; i < programs.length; i++) {
			System.out.println(Arrays.toString(solution(programs[i], flag_rules[i], commands[i])));
		}

	}

	public static boolean[] solution(String program, String[] flag_rules, String[] commands) {
		boolean[] answer = new boolean[commands.length]; // 명령어의 수만큼 배열 할당
		for (int i = 0; i < answer.length; i++) {
			answer[i] = true;
		}

		String[][] rulesArr = new String[flag_rules.length][2]; // 명령어 규칙을 명령어 이름과 타입으로 구분하기 위해 2 사이즈의 배열로 재할당
		for (int i = 0; i < flag_rules.length; i++) {
			rulesArr[i] = flag_rules[i].split(" "); // 공백을 구분자로 나누어 준다.
		}

		// 명령어 검사 시작
		top: for (int i = 0; i < commands.length; i++) { // 중간에 명령어 검사 중 조건과 맞지 않으면 반복문 탈출하기 위한 label
			String[] cmds = commands[i].split(" "); // 명령어를 공백으로 나누어준다.
			String cmdProgram = cmds[0]; // 먼저 프로그램 이름 검사
			if (!cmdProgram.equals(program)) { // 프로그램 이름과 맞지 않으면
				answer[i] = false; // false로 바꿔주고
				continue; // 탈출 (다음 명령어 검사)
			}

			int order = 0; // 0일땐 flag_name검사, 1일땐 flag_argument_type 검사. 프로그램 이름 뒤엔 먼저 flag_name을 검사해야 하므로
							// 초기값은 0
			int type = 0; // 타입은 0으로 임의 할당 해준다.
			for (int j = 1; j < cmds.length; j++) {
				if (order == 0) { // flag_name을 검사할 차례일때
					if (cmds[j].charAt(0) != '-') { // flag_name의 첫번째 문자가 '-'가 아닌 경우
						answer[i] = false; // false로 바꿔주고
						break; // 탈출
					}

					// flag_name의 첫번째 문자가 '-'가 맞을 경우 타입을 찾아본다.
					String flag_name = cmds[j].substring(1); // flag_argument_type을 가져온다.
					for (int k = 0; k < rulesArr.length; k++) { // flag_rules로 입력받은 규칙을 반복문으로 돌면서 찾는다.
						if (rulesArr[k][0].substring(1).equals(flag_name)) { // 탐색하다가 발견했을때
							switch (rulesArr[k][1]) {
							case "STRING": // 규칙의 argument_type이 STRING일 경우
								type = 0; // 명령어의 다음 요소 type 검사를 String으로 한다.
								order = 1; // flag_argument_type을 검사할 차례라고 명시해준다.
								break;
							case "NUMBER": // 규칙의 argument_type이 NUMBER일 경우
								type = 1; // 명령어의 다음 요소 type 검사를 NUMBER으로 한다.
								order = 1; // flag_argument_type을 검사할 차례라고 명시해준다.
								break;
							case "NULL": // 규칙의 argument_type이 NULL일 경우
								type = 2; // 명령어의 다음 요소 type 검사를 NULL으로 한다.
								order = 0; // NULL이므로 flag_argument_type을 검사하지 않아도 되니 flag_name을 검사할 차례라고 명시해줌
								break;
							case "STRINGS": // 규칙의 argument_type이 STRING일 경우
								type = 3; // 명령어의 다음 요소 type 검사를 String으로 한다.
								order = 1; // flag_argument_type을 검사할 차례라고 명시해준다.
								break;
							case "NUMBERS": // 규칙의 argument_type이 NUMBER일 경우
								type = 4; // 명령어의 다음 요소 type 검사를 NUMBER으로 한다.
								order = 1; // flag_argument_type을 검사할 차례라고 명시해준다.
								break;
							}
							break; // 맞는 규칙을 찾았으니 탈출해준다.
						}
					}
				} else if (order == 1) { // flag_argument_type을 검사할 차례일때
					if (type == 0) { // argument가 String 타입인지 검사해야할 때
						String cmd_argument = cmds[j]; // 명령어의 argument를 가져온다
						for (int k = 0; k < cmd_argument.length(); k++) { // 명령어의 argument를 문자 하나씩 돌아가며 검사한다.
							char c = cmd_argument.charAt(k);
							if (!((c >= 97 && c <= 122) || (c >= 65 && c <= 90))) { // 소문자 영어, 대문자 영어가 아니라면
								answer[i] = false; // false로 바꿔주고
								continue top; // 다음 명령어를 검사하기 위해 탈출한다.
							}
						}
					}
					if (type == 1) { // argument가 Number 타입인지 검사해야할 때
						String cmd_argument = cmds[j]; // 명령어의 argument를 가져온다
						for (int k = 0; k < cmd_argument.length(); k++) { // 명령어의 argument를 문자 하나씩 돌아가며 검사한다.
							char c = cmd_argument.charAt(k);
							if (!(c >= 48 && c <= 57)) { // 숫자가 아니라면
								answer[i] = false; // false로 바꿔주고
								continue top; // 다음 명령어를 검사하기 위해 탈출한다.
							}
						}
					}

					if (type == 3) { // argument가 String 타입인지 검사해야할 때
						int cnt = 0;
						String cmd_argument = cmds[j]; // 명령어의 argument를 가져온다
						while (true) {
							cmd_argument = cmds[j];
							if (cnt >= 2 && cmd_argument.charAt(0) == '-')
								break;
							for (int k = 0; k < cmd_argument.length(); k++) { // 명령어의 argument를 문자 하나씩 돌아가며 검사한다.
								char c = cmd_argument.charAt(k);
								if (!((c >= 97 && c <= 122) || (c >= 65 && c <= 90))) { // 소문자 영어, 대문자 영어가 아니라면
									answer[i] = false; // false로 바꿔주고
									continue top; // 다음 명령어를 검사하기 위해 탈출한다.
								}
							}
							j++;
							cnt++;
						}
					}
					if (type == 4) { // argument가 String 타입인지 검사해야할 때
						int cnt = 0;
						String cmd_argument = cmds[j]; // 명령어의 argument를 가져온다
						for (int k = 0; k < cmd_argument.length(); k++) { // 명령어의 argument를 문자 하나씩 돌아가며 검사한다.
							char c = cmd_argument.charAt(k);
							if (!((c >= 97 && c <= 122) || (c >= 65 && c <= 90))) { // 소문자 영어, 대문자 영어가 아니라면
								answer[i] = false; // false로 바꿔주고
								continue top; // 다음 명령어를 검사하기 위해 탈출한다.
							}
						}
					}
//					order = 2; // 끝까지 검사해서 중간에 탈출하지 않았다면 다시 flag_name을 검사할 차례이므로 바꿔준다.
				} else if (order == 2) { // flag_name이나 flag_argument_type 아무거나 상관없을 때

				}
			}
		}
		return answer;
	}
}
