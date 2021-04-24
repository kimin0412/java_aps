package Algorithm.src.samsung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Re) 28m Solved!!
 */
public class 나무제테크_다시 {
    static int N, M, K;
    static int[][] Arr, map;
    static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
    static PriorityQueue<Tree> alive;
    static Queue<Tree> dead, tmpQ;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        Arr = new int[N][N];
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                Arr[i][j] = Integer.parseInt(st.nextToken());
                map[i][j] = 5;
            }
        }

        dead = new LinkedList<>();
        tmpQ = new LinkedList<>();
        alive = new PriorityQueue<>(new Comparator<Tree>() {

            @Override
            public int compare(Tree o1, Tree o2) {
                if (o1.age > o2.age) {
                    return 1;
                } else if (o1.age == o2.age) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int age = Integer.parseInt(st.nextToken());
            alive.add(new Tree(r, c, age));
        }

        for (int k = 0; k < K; k++) {
            spring();
            summer();
            fall();
            winter();
        }
        System.out.println(alive.size());

    }

    private static void winter() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] += Arr[i][j];
            }
        }
    }

    private static void fall() {
        while (!tmpQ.isEmpty()) {
            Tree tmp = tmpQ.poll();
            alive.add(tmp);
            if (tmp.age % 5 == 0) {
                for (int d = 0; d < 8; d++) {
                    int nr = tmp.r + dir[d][0];
                    int nc = tmp.c + dir[d][1];

                    if ((nr > -1 && nr < N && nc > -1 && nc < N)) {
                        alive.add(new Tree(nr, nc, 1));
                    }

                }
            }
        }
    }

    private static void summer() {
        while (!dead.isEmpty()) {
            Tree tmp = dead.poll();
            map[tmp.r][tmp.c] += tmp.age / 2;
        }
    }

    private static void spring() {
        // tmpQ = new LinkedList<>();

        while (!alive.isEmpty()) {
            Tree tmp = alive.poll();
            int res = map[tmp.r][tmp.c] - tmp.age;
            if (res < 0) {
                dead.add(tmp);
            } else {
                map[tmp.r][tmp.c] = res;
                tmp.age++;
                tmpQ.add(tmp);
            }
        }
    }

    static private class Tree {
        int r, c, age;

        public Tree(int r, int c, int age) {
            this.r = r;
            this.c = c;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Tree [r=" + r + ", c=" + c + ", age=" + age + "]";
        }
    }
}
