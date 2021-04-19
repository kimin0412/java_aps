package Algorithm.src.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * 
 * @author taeheekim
 * 	17972kb	488ms
 *  메모리, 시간 모두 유리 
 */

// 나무들의 나이를 리스트로 관리하고  Spring,Summer 합친 버전 
public class Main_16235_나무제테크_보충_개선 {

	static int N, M, K, a[][], map[][];
	static ArrayList<Integer>[][] mapTree; // 각 칸의 나무들의 나이리스트
	static int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	static int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		a = new int[N][N];
		mapTree = new ArrayList[N][N];
		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; ++j) {
				a[i][j] = Integer.parseInt(st.nextToken());
				map[i][j] = 5; // 초기양분
				mapTree[i][j] = new ArrayList<Integer>();////// 변경
			}
		}
		int x, y, age;
		for (int i = 0; i < M; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			x = Integer.parseInt(st.nextToken()) - 1;
			y = Integer.parseInt(st.nextToken()) - 1;
			age = Integer.parseInt(st.nextToken());
			mapTree[x][y].add(age); // 모든 나무는 초기 위치가 다 다르므로 각칸에는 한나무만 존재
		}
		for (int k = 0; k < K; ++k) {
			springAndSummer();
			fall();
			winter();
		}
		System.out.println(getCount());
	}

	// 각 나무는 자기칸의 양분만큼 안에서 자기 나이만큼 먹음 -> 그 후 나이 1 증가
	// 나이 어린 나무부터
	// 먹을 양분이 부족하면 죽음
	// 봄에 죽은 나무가 양분으로 변함.
	private static void springAndSummer() {

		int amount = 0, nAmount = 0;
		ArrayList<Integer> list = null; /////// 변경
		for (int x = 0; x < N; ++x) {
			for (int y = 0; y < N; ++y) {
				amount = map[x][y];
				list = mapTree[x][y];
				nAmount = 0;
				for (int i = list.size() - 1; i >= 0; --i) {
					int age = list.get(i); ////// 변경 //  현 나무의 나이
					if (amount < age) { ////// 변경
						nAmount += age / 2;////// 변경 // 자기칸의 양분이 나무의 나이보다 부족하다면 해당 나무는 죽고 자신의 나이의 반만큼 양분이 됨.
						list.remove(i); // 현 나이 나무의 삭제
					} else {
						amount -= age; ////// 변경
						list.set(i, age + 1); ////// 변경 // 현 나무의 나이를 1증가시켜 다시 리스트의 그 자리에 set
					}
				}
				map[x][y] = amount + nAmount; // 각 위치의 양분은 남은 양분(amount)과 죽은 나무의 새양분(nAmount)으로 갱신
			}
		}
	}

	// 살아 있는 나무의 8방 번식
	private static void fall() {
		int newX = 0, newY = 0;
		for (int x = 0; x < N; ++x) {
			for (int y = 0; y < N; ++y) {
				ArrayList<Integer> list = mapTree[x][y];////// 변경
				for (int age : list) {////// 변경
					if (age % 5 > 0)
						continue;////// 변경
					for (int d = 0; d < 8; ++d) {
						newX = x + dx[d];
						newY = y + dy[d];
						if (newX >= 0 && newX < N && newY >= 0 && newY < N) {
							mapTree[newX][newY].add(1); ////// 변경 // 1살 나무 리스트 맨 뒤로 추가
						}
					}

				}
			}
		}
	}

	private static void winter() {
		for (int x = 0; x < N; ++x) {
			for (int y = 0; y < N; ++y)
				map[x][y] += a[x][y];
		}
	}

	private static int getCount() {
		int count = 0;
		for (int x = 0; x < N; ++x) {
			for (int y = 0; y < N; ++y)
				count += mapTree[x][y].size();
		}
		return count;
	}
}
