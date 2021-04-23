package Algorithm.src.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
/**
 * 3h 걸렸을듯
 */
public class Solution_2383_점심식사시간 {
    static int N, ans, pCnt;
    static int[][] map;
    static int[][] stairs;
    static int[][] people;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TC; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            pCnt = 0;
            int sCnt = 0;
            stairs = new int[2][3];
            Queue<int[]> pQ = new LinkedList<>();
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if (map[i][j] != 0) {
                        if (map[i][j] == 1) {
                            pQ.add(new int[] { i, j });
                            pCnt++;
                        } else {
                            stairs[sCnt][0] = i;
                            stairs[sCnt][1] = j;
                            stairs[sCnt][2] = map[i][j];
                            sCnt++;
                        }

                    }
                }
            }

            people = new int[pCnt][2];
            int idx = 0;
            while (!pQ.isEmpty()) {
                int[] q = pQ.poll();
                people[idx][0] = q[0];
                people[idx][1] = q[1];
                idx++;
            }

            ans = Integer.MAX_VALUE;
            boolean[] subset = new boolean[pCnt]; // true면 1번 계단으로 가기
            pickStair(subset, 0); // false일때
            subset[0] = true;
            System.out.println("#" + tc + " " + ans);

        }
    }

    private static void pickStair(boolean[] subset, int idx) {
        if (idx == pCnt) {
            move(subset);
        } else {
            subset[idx] = true;
            pickStair(subset, idx + 1);
            subset[idx] = false;
            pickStair(subset, idx + 1);
        }
    }

    private static void move(boolean[] subset) {
        int sec = 0;
        Queue<Integer> people1 = new LinkedList<>();
        Queue<Integer> people2 = new LinkedList<>();

        for (int i = 0; i < pCnt; i++) {
            if (subset[i]) { // 계단 1로
                int a = Math.abs(people[i][0] - stairs[0][0]);
                int b = Math.abs(people[i][1] - stairs[0][1]);
                people1.add(a + b);
            } else {
                int a = Math.abs(people[i][0] - stairs[1][0]);
                int b = Math.abs(people[i][1] - stairs[1][1]);
                people2.add(a + b);
            }
        }

        int cnt = 0;
        Queue<Integer> stair1 = new LinkedList<>();
        Queue<Integer> stair2 = new LinkedList<>();
        Queue<Integer> ready1 = new LinkedList<>();
        Queue<Integer> ready2 = new LinkedList<>();
        
        while (true) {
            sec++;

            // 계단 내려가기
            Queue<Integer> stairTmp = new LinkedList<>();
            while (!stair1.isEmpty()) {
                int tmpSec = stair1.poll();
                tmpSec--;
                if (tmpSec != 0) {
                    stairTmp.add(tmpSec);
                } else {
                    cnt++;
                }
            }
            while (!stairTmp.isEmpty()) {
                int tmpSec = stairTmp.poll();
                stair1.add(tmpSec);
            }

            while (!stair2.isEmpty()) {
                int tmpSec = stair2.poll();
                tmpSec--;
                if (tmpSec != 0) {
                    stairTmp.add(tmpSec);
                } else {
                    cnt++;
                }
            }
            while (!stairTmp.isEmpty()) {
                int tmpSec = stairTmp.poll();
                stair2.add(tmpSec);
            }

            // 대기중인 사람들 내려가기 시작
            while (!ready1.isEmpty()) {
                int size = stair1.size();
                if (size < 3) {
                    ready1.poll();
                    stair1.add(stairs[0][2]);
                } else {
                    break;
                }
            }
            while (!ready2.isEmpty()) {
                int size = stair2.size();
                if (size < 3) {
                    ready2.poll();
                    stair2.add(stairs[1][2]);
                } else {
                    break;
                }
            }

            // 사람들 도착하면 대기로 바꾸기
            Queue<Integer> peopleTmp = new LinkedList<>();
            while (!people1.isEmpty()) {
                int sec1 = people1.poll();
                if (sec1 <= sec) {
                    int size = ready1.size();
                    if (size < 3) {
                        ready1.add(1);
                    } else {
                        peopleTmp.add(sec1);
                    }
                } else {
                    peopleTmp.add(sec1);
                }
            }
            while (!peopleTmp.isEmpty()) {
                int tmpSec = peopleTmp.poll();
                people1.add(tmpSec);
            }

            while (!people2.isEmpty()) {
                int sec2 = people2.poll();
                if (sec2 <= sec) {
                    int size = ready2.size();
                    if (size < 3) {
                        ready2.add(1);
                    } else {
                        peopleTmp.add(sec2);
                    }
                } else {
                    peopleTmp.add(sec2);
                }
            }
            while (!peopleTmp.isEmpty()) {
                int tmpSec = peopleTmp.poll();
                people2.add(tmpSec);
            }

            if (cnt == pCnt) {
                break;
            }

        }
        ans = Math.min(ans, sec);
    }
}
