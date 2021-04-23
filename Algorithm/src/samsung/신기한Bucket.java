package Algorithm.src.samsung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 신기한Bucket {
    static int N;
    // static int[][] map;
    static int[][] blocks;
    static int[][] rules = new int[8][8];
    static int[][] dir = { { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 } };

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        blocks = new int[N][2];
        for (int i = 0; i < 8; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 8; j++) {
                rules[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            blocks[i][0] = Integer.parseInt(st.nextToken());
            blocks[i][1] = Integer.parseInt(st.nextToken());
        }
        int[][] map = new int[101][4];
        if (blocks[0][1] == 0) {
            play(0, map, 0, 1);
            play(0, map, 0, 2);
            play(0, map, 0, 3);
            play(0, map, 0, 4);
        } else {
            play(0, map, 0, blocks[0][1]);
        }

        // for (int i = 0; i < N; i++) {

        // }

    }

    private static void play(int n, int[][] map, int score, int c) {
        if (n == N - 1)
            return;

        for (int i = 0; i < 101; i++) {
            if (map[i][c - 1] != 0) {
                map[i - 1][c - 1] = blocks[n][0];
            }
        }

        if (map[100][0] != 0 && map[100][1] != 0 && map[100][2] != 0 && map[100][3] != 0) {
            score++;
            map[100][0] = 0;
            map[100][1] = 0;
            map[100][2] = 0;
            map[100][3] = 0;

            for (int i = 99; i >= 0; i--) {
                map[i + 1][0] = map[i][0];
                map[i + 1][1] = map[i][1];
                map[i + 1][2] = map[i][2];
                map[i + 1][3] = map[i][3];
            }
            map[0][0] = 0;
            map[0][1] = 0;
            map[0][2] = 0;
            map[0][3] = 0;
        }

        int[][] tmp = new int[101][4];
        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 4; j++) {
                if (map[i][j] != 0) {
                    for (int d = 0; d < 8; d++) {
                        int r = rules[blocks[n][0] - 1][d];
                        int nr = i + dir[r - 1][0];
                        int nc = i + dir[r - 1][0];
                        if ((nr > -1 && nr < 100 && nc > -1 && nc < 4)) {
                            if (tmp[nr][nc] == 0) {
                                tmp[nr][nc] = map[i][j];
                                map[i][j] = 0;
                            } else {
                                if (tmp[nr][nc] > map[i][j]) {
                                    tmp[nr][nc] = map[i][j];
                                }
                                map[i][j] = 0;
                            }
                            break;
                        }

                    }
                }
            }
        }

        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 4; j++) {
                map[i][j] = tmp[i][j];
            }
        }

        for (int j = 0; j < 4; j++) {
            for (int i = 99; i >= 0; i--) {
                if (map[i + 1][j] == 0) {
                    map[i + 1][j] = map[i][j];
                    map[i][j] = 0;
                } else {
                    break;
                }
            }
        }

        if (map[100][0] != 0 && map[100][1] != 0 && map[100][2] != 0 && map[100][3] != 0) {
            score++;
            map[100][0] = 0;
            map[100][1] = 0;
            map[100][2] = 0;
            map[100][3] = 0;

            for (int i = 99; i >= 0; i--) {
                map[i + 1][0] = map[i][0];
                map[i + 1][1] = map[i][1];
                map[i + 1][2] = map[i][2];
                map[i + 1][3] = map[i][3];
            }
            map[0][0] = 0;
            map[0][1] = 0;
            map[0][2] = 0;
            map[0][3] = 0;
        }

        // for (int i = 0; i < 101; i++) {
        // if (map[i][0] != 0 && map[i][1] != 0 && map[i][2] != 0 && map[i][3] != 0) {
        // score++;
        // map[i][0] = 0;
        // map[i][1] = 0;
        // map[i][2] = 0;
        // map[i][3] = 0;

        // map[i - 1][c - 1] = 1;
        // }
        // }

    }
}
