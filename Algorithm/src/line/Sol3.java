package Algorithm.src.line;

import java.util.Arrays;

public class Sol3 {

	public static void main(String[] args) {
		String[] programs = { "line", "trip" };
		String[][] flag_rules = { { "-s STRINGS", "-n NUMBERS", "-e NULL" }, { "-days NUMBERS", "-dest STRING" } };
		String[][] commands = { { "line -n 100 102 -s hi -e", "line -n id pwd -n 100" },
				{ "trip -days 15 10 -dest Seoul Paris", "trip -days 10 -dest Seoul" } };

		for (int i = 0; i < programs.length; i++) {
			System.out.println(Arrays.toString(solution(programs[i], flag_rules[i], commands[i])));
		}

	}

	public static boolean[] solution(String program, String[] flag_rules, String[] commands) {
		boolean[] answer = new boolean[commands.length]; // 명령어의 수만큼 배열 할당
		for (int i = 0; i < answer.length; i++) {
			answer[i] = true;
		}

		String[][] rulesArr = new String[flag_rules.length][3]; // 명령어 규칙을 명령어 이름과 타입으로 구분하기 위해 2 사이즈의 배열로 재할당
		String[][] ruleAlias = new String[flag_rules.length][3];
		int aliasIdx = 0;
		for (int i = 0; i < flag_rules.length; i++) {
			rulesArr[i] = flag_rules[i].split(" "); // 공백을 구분자로 나누어 준다.
			if(rulesArr[i][1].equals("ALIAS")) {
				for (int j = 0; j < flag_rules.length; j++) {
					if(ruleAlias[j][0] == rulesArr[i][2]) {
						
						ruleAlias[aliasIdx][1] = rulesArr[i][0];
					}
					
				}
				
			}
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
							case "STRINGS": // 규칙의 argument_type이 STRINGS일 경우
								type = 3; // 명령어의 다음 요소 type 검사를 STRINGS으로 한다.
								order = 1; // flag_argument_type을 검사할 차례라고 명시해준다.
								break;
							case "NUMBERS": // 규칙의 argument_type이 NUMBERS일 경우
								type = 4; // 명령어의 다음 요소 type 검사를 NUMBERS으로 한다.
								order = 1; // flag_argument_type을 검사할 차례라고 명시해준다.
								break;
							}
							break; // 맞는 규칙을 찾았으니 탈출해준다.
						}
					}
				} else if (order == 1) { // flag_argument_type을 검사할 차례일때
					String cmd_argument = cmds[j]; // 명령어의 argument를 가져온다
					for (int k = 0; k < cmd_argument.length(); k++) { // 명령어의 argument를 문자 하나씩 돌아가며 검사한다.
						if (type == 0) { // argument가 String 타입인지 검사해야할 때
							char c = cmd_argument.charAt(k);
							if (!((c >= 97 && c <= 122) || (c >= 65 && c <= 90))) { // 소문자 영어, 대문자 영어가 아니라면
								answer[i] = false; // false로 바꿔주고
								continue top; // 다음 명령어를 검사하기 위해 탈출한다.
							}
							order = 0; // 중간에 탈출하지 않았다면 다시 flag_name을 검사할 차례이므로 바꿔준다.
						}
						if (type == 1) { // argument가 Number 타입인지 검사해야할 때
							char c = cmd_argument.charAt(k);
							if (!(c >= 48 && c <= 57)) { // 숫자가 아니라면
								answer[i] = false; // false로 바꿔주고
								continue top; // 다음 명령어를 검사하기 위해 탈출한다.
							}
							order = 0; // 중간에 탈출하지 않았다면 다시 flag_name을 검사할 차례이므로 바꿔준다.
						}

						if (type == 3) { // argument가 Strings 타입인지 검사해야할 때
							char c = cmd_argument.charAt(k);
							if (!((c >= 97 && c <= 122) || (c >= 65 && c <= 90))) { // 소문자 영어, 대문자 영어가 아니라면
								answer[i] = false; // false로 바꿔주고
								continue top; // 다음 명령어를 검사하기 위해 탈출한다.
							}
							order = 2; // Strings는 1개 이상의 String으로 이루어져 있으므로 뒤에 어떤 요소가 나올지 알 수 없다.
							// 그러므로 order값을 2로 바꾸어 주어서 flag_name을 검사할 차례인지 flag_argument_type을 검사할 차례인지 다시 판단하도록 한다.
						}
						if (type == 4) { // argument가 Numbers 타입인지 검사해야할 때
							char c = cmd_argument.charAt(k);
							if (!(c >= 48 && c <= 57)) { // 소문자 영어, 대문자 영어가 아니라면
								answer[i] = false; // false로 바꿔주고
								continue top; // 다음 명령어를 검사하기 위해 탈출한다.
							}
							order = 2; // Numbers는 1개 이상의 Number으로 이루어져 있으므로 뒤에 어떤 요소가 나올지 알 수 없다.
							// 그러므로 order값을 2로 바꾸어 주어서 flag_name을 검사할 차례인지 flag_argument_type을 검사할 차례인지 다시 판단하도록 한다.
						}
					}

				} else if (order == 2) { // flag_name을 검사할 차례인지 flag_argument_type을 검사할 차례인지 판단 
					if (cmds[j].charAt(0) == '-') { // flag_name의 첫번째 문자가 '-' 라면 
						order = 0; // flag_name을 검사할 차례라고 명시해준 후
						j--; // 인덱스를 뒤로 돌린다음 
						continue; // 다음 반복문으로 넘어가 order=0 일 경우를 실행하도록 한다.
					} else { // flag_name의 첫번째 문자가 '-'가 아니라면 
						order = 1; // flag_argument_type을 검사할 차례라고 명시해준 후
						j--; // 인덱스를 뒤로 돌린다음 
						continue; // 다음 반복문으로 넘어가 order=0 일 경우를 실행하도록 한다.
					}
				}
			}
		}
		return answer;
	}
}
