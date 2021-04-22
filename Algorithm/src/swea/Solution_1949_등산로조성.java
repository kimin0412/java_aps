package Algorithm.src.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
* 51m 컷
*/
public class Solution_1949_등산로조성 {
    static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    static int N, K, ans;
    static int[][] map, tcMap;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TC; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            tcMap = new int[N][N];
            int max = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    tcMap[i][j] = map[i][j];
                    max = Math.max(max, map[i][j]);
                }
            }

            Queue<int[]> high = new LinkedList<>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] == max)
                        high.add(new int[] { i, j, map[i][j] });
                }
            }

            ans = 0;
            while (!high.isEmpty()) {
                int[] now = high.poll();
                // System.out.println("@@@@@@@@@@@@@@@@ now : " + now[0] + " " + now[1]);
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        // System.out.println("i, j : " + i + " " + j);
                        if (!(i == now[0] && j == now[1])) {
                            for (int k = 1; k <= K; k++) {
                                tcMap[i][j] -= k;
                                // for (int[] is : tcMap) {
                                // System.out.println(Arrays.toString(is));
                                // }
                                // System.out.println();
                                boolean[][] visit = new boolean[N][N];
                                visit[now[0]][now[1]] = true;
                                int cnt = 1;
                                dfs(now[0], now[1], visit, cnt);
                                tcMap[i][j] = map[i][j];

                            }
                        }
                    }
                }
            }
            System.out.println("#" + tc + " " + ans);
        }

    }

    private static void dfs(int sr, int sc, boolean[][] visit, int cnt) {
        // System.out.println("sr, sc : " + sr + " " + sc);
        // System.out.println("cnt : " + cnt);
        // for (boolean[] bs : visit) {
        // System.out.println(Arrays.toString(bs));
        // }
        ans = Math.max(ans, cnt);
        for (int d = 0; d < 4; d++) {
            int nr = sr + dir[d][0];
            int nc = sc + dir[d][1];
            if (isIn(nr, nc) && !visit[nr][nc]) {
                if (tcMap[nr][nc] < tcMap[sr][sc]) {
                    visit[nr][nc] = true;
                    dfs(nr, nc, visit, cnt + 1);
                    visit[nr][nc] = false;
                }
            }
        }
    }

    private static boolean isIn(int nr, int nc) {
        return (nr > -1 && nr < N && nc > -1 && nc < N);
    }
}
