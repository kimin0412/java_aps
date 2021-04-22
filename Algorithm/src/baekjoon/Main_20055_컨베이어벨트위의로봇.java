package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main_20055_컨베이어벨트위의로봇 {
    static int N, K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        Deque<Belt> belts = new LinkedList<>();
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < 2 * N; i++) {
            belts.add(new Belt((i + 1), Integer.parseInt(st.nextToken()), false));
        }

        Deque<Integer> robots = new LinkedList<>();
        int ans = 0;
        while (true) {
            ans++;
            for (int i = 0; i < 2 * N; i++) {
                Belt l = belts.pollLast();
                if (i == N + 1) {
                    if (l.robot) {
                        l.robot = false;
                    }
                }
                belts.addFirst(l);
            }
            Belt l = belts.pollLast();
            belts.addFirst(l);

            Deque<Belt> tmp = new LinkedList<>();
            for (int i = 0; i < N; i++) { // 끝 하나 전에서 시작 (맨끝엔 어차피 로봇이 없다.)
                Belt tmpB = belts.pollLast();
                tmp.addFirst(tmpB);
            }

            Belt tmpB = belts.pollLast();
            tmp.addFirst(tmpB);

            for (int i = 1; i < N; i++) { // 끝 하나 전에서 시작 (맨끝엔 어차피 로봇이 없다.)
                tmpB = belts.pollLast();
                if (tmpB.robot) {
                    Belt first = tmp.pollFirst();
                    if (first.remain > 0 && !first.robot) {
                        first.remain--;
                        if (i == 1) {
                            first.robot = false;
                        } else {
                            first.robot = true;
                        }
                        tmpB.robot = false;
                    }
                    tmp.addFirst(first);
                    tmp.addFirst(tmpB);
                } else {
                    tmp.addFirst(tmpB);
                }
            }

            Belt tmpF = tmp.pollFirst();
            if (tmpF.remain > 0 && !tmpF.robot) {
                tmpF.remain--;
                tmpF.robot = true;
            }
            tmp.addFirst(tmpF);

            belts = new LinkedList<>();
            int cnt = 0;
            while (!tmp.isEmpty()) {
                Belt t = tmp.pollLast();
                if (t.remain == 0) {
                    cnt++;
                }
                belts.addFirst(t);
            }

            if (cnt >= K)
                break;
        }
        System.out.println(ans);
    }

    static private class Belt {
        int id;
        int remain;
        boolean robot;

        public Belt(int id, int remain, boolean robot) {
            this.id = id;
            this.remain = remain;
            this.robot = robot;
        }

        @Override
        public String toString() {
            return "Belt [id=" + id + ", remain=" + remain + ", robot=" + robot + "]";
        }
    }
}