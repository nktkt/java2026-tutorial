package problems_advanced;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * ===== 上級05: グラフの幅優先探索 (BFS) =====
 * 出典: Codeforces / LeetCode グラフ問題の基礎
 *
 * 【問題】
 *   無向グラフが与えられる。頂点 start から各頂点への最短距離を求めよ。
 *   （辺の重みはすべて1とする）
 *
 * 【グラフとは？】
 *   頂点（ノード）と辺（エッジ）の集合。
 *   地図の「場所」と「道」、SNSの「人」と「友達関係」などを表現できる。
 *
 *   例（0〜5の6頂点）:
 *     0 --- 1 --- 3
 *     |     |
 *     2 --- 4 --- 5
 *
 * 【BFS（幅優先探索）とは？】
 *   スタート地点から「近い頂点」から順に探索する。
 *   キュー（FIFO: 先入れ先出し）を使う。
 *
 *   アルゴリズム:
 *   1. スタート地点をキューに入れ、距離0とする
 *   2. キューから1つ取り出す
 *   3. その頂点の隣の未訪問頂点をキューに入れ、距離+1
 *   4. キューが空になるまで繰り返す
 *
 * 【キュー(Queue)の基本操作】
 *   Queue<Integer> queue = new LinkedList<>();
 *   queue.add(値)     → 末尾に追加
 *   queue.poll()      → 先頭を取り出す
 *   queue.isEmpty()   → 空かどうか
 *
 * 【隣接リスト（Adjacency List）】
 *   グラフの表現方法。各頂点ごとに「隣の頂点リスト」を持つ。
 *   List<List<Integer>> graph = new ArrayList<>();
 *   graph.get(0) → 頂点0に隣接する頂点のリスト
 *
 * 【学べること】
 *   - グラフの基本とデータ構造（隣接リスト）
 *   - BFS のアルゴリズムと実装
 *   - Queue の使い方
 *   - 最短距離問題の基本
 */
public class P15_GraphBFS {

    public static void main(String[] args) {
        //     0 --- 1 --- 3
        //     |     |
        //     2 --- 4 --- 5
        int n = 6;  // 頂点数

        // 隣接リストでグラフを構築
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // 辺を追加するヘルパーメソッド（無向グラフなので双方向）
        addEdge(graph, 0, 1);
        addEdge(graph, 0, 2);
        addEdge(graph, 1, 3);
        addEdge(graph, 1, 4);
        addEdge(graph, 2, 4);
        addEdge(graph, 4, 5);

        System.out.println("頂点0からの最短距離:");
        int[] distances = bfs(graph, 0, n);
        for (int i = 0; i < n; i++) {
            System.out.println("  0 → " + i + " : " + distances[i]);
        }
        // 期待出力:
        // 0→0: 0, 0→1: 1, 0→2: 1, 0→3: 2, 0→4: 2, 0→5: 3
    }

    static void addEdge(List<List<Integer>> graph, int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);  // 無向グラフなので逆方向も追加
    }

    static int[] bfs(List<List<Integer>> graph, int start, int n) {

        // ここにコードを書こう
        return new int[n]; // 仮の戻り値


        // ========== 解答例 ==========
        // int[] dist = new int[n];
        // boolean[] visited = new boolean[n];
        // // visited[i]: 頂点 i を訪問済みかどうか（同じ頂点を2回探索しない）
        //
        // Arrays.fill(dist, -1);     // -1 = 未到達
        // dist[start] = 0;
        // visited[start] = true;
        //
        // Queue<Integer> queue = new LinkedList<>();
        // queue.add(start);
        //
        // while (!queue.isEmpty()) {
        //     int current = queue.poll();      // キューの先頭を取り出す
        //
        //     // current の隣の頂点をすべて見る
        //     for (int neighbor : graph.get(current)) {
        //         if (!visited[neighbor]) {
        //             visited[neighbor] = true;
        //             dist[neighbor] = dist[current] + 1;
        //             // 「current までの距離 + 1」が neighbor への距離
        //             queue.add(neighbor);
        //         }
        //     }
        // }
        // return dist;
    }
}
