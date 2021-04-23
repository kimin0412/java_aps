package Algorithm.src.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 2h30m 쯤... 걸렸을듯
 */
public class Solution_2382_미생물격리 {
    static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    static int N, M, K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TC; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            int[][] map = new int[N][N];
            Virus[] viruses = new Virus[K + 1];
            viruses[0] = new Virus(0, 0, 0, 0);
            for (int i = 1; i < K + 1; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int r = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int s = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken()) - 1;
                Virus v = new Virus(r, c, s, d);
                viruses[i] = v;
                map[r][c] = i;
            }

            for (int m = 0; m < M; m++) {

                int[][] tmp = new int[N][N];
                Queue<Integer>[][] tmpMap = new LinkedList[N][N];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        tmpMap[i][j] = new LinkedList<>();
                    }
                }

                for (int k = 1; k < K + 1; k++) {

                    if (viruses[k] != null) {
                        Virus now = viruses[k];
                        map[now.r][now.c] = 0;
                        now.r += dir[now.d][0];
                        now.c += dir[now.d][1];

                        if (isBound(now.r, now.c)) {
                            now.s /= 2;
                            switch (now.d) {
                            case 0:
                                now.d = 1;
                                break;
                            case 1:
                                now.d = 0;
                                break;
                            case 2:
                                now.d = 3;
                                break;
                            case 3:
                                now.d = 2;
                                break;
                            }
                            if (now.s == 0) {
                                viruses[k] = null;
                            } else {
                                viruses[k].s = now.s;
                                viruses[k].d = now.d;
                            }
                        }
                        if (viruses[k] != null) {
                            tmpMap[now.r][now.c].add(k);
                        }
                    }
                }

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        Virus V = new Virus(0, 0, 0, 0);
                        int maxN = 0;
                        int sumS = 0;
                        while (!tmpMap[i][j].isEmpty()) {
                            int k = tmpMap[i][j].poll();
                            sumS += viruses[k].s;
                            if (V.s < viruses[k].s) {
                                V.r = viruses[k].r;
                                V.c = viruses[k].c;
                                V.d = viruses[k].d;
                                V.s = viruses[k].s;
                                maxN = k;
                            }
                            viruses[k] = null;
                        }
                        map[i][j] = maxN;
                        viruses[maxN] = V;
                        viruses[maxN].s = sumS;
                    }
                }
            }

            int ans = 0;

            for (int k = 1; k < K + 1; k++) {
                if (viruses[k] != null) {
                    Virus now = viruses[k];
                    ans += now.s;
                }
            }

            System.out.println("#" + tc + " " + ans);
        }
    }

    private static boolean isBound(int nr, int nc) {
        return (nr == 0 || nr == N - 1 || nc == 0 || nc == N - 1);
    }

    static private class Virus {
        int r, c;
        int s, d;

        public Virus(int r, int c, int s, int d) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
        }

        @Override
        public String toString() {
            return "Virus [r=" + r + ", c=" + c + ", s=" + s + ", d=" + d + "]";
        }

    }
}
