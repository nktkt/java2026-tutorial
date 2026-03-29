package problems_expert;

import java.util.*;

/**
 * ===== エキスパート13: 0-1 BFS =====
 * 出典: Codeforces / AtCoder
 *
 * 【問題】
 *   辺の重みが 0 か 1 のグラフで、始点から各頂点への最短距離を求めよ。
 *
 * 【0-1 BFS とは？】
 *   通常のBFS(重み1のみ)とDijkstra(任意の重み)の間の問題。
 *   Deque（両端キュー）を使い:
 *   - 重み0の辺 → addFirst（先頭に追加）
 *   - 重み1の辺 → addLast（末尾に追加）
 *   これにより O(V+E) で最短距離が求められる（Dijkstraより速い）。
 *
 * 【学べること】0-1 BFS、Dequeの応用、Dijkstraとの使い分け
 */
public class P43_ZeroOneBFS {

    public static void main(String[] args) {
        int n = 5;
        // graph[u] = list of {v, weight(0 or 1)}
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        // 例: 0→1(重み1), 0→2(重み0), 1→3(重み0), 2→3(重み1), 3→4(重み0)
        graph.get(0).add(new int[]{1, 1});
        graph.get(0).add(new int[]{2, 0});
        graph.get(1).add(new int[]{3, 0});
        graph.get(2).add(new int[]{3, 1});
        graph.get(3).add(new int[]{4, 0});

        // int[] dist = zeroOneBFS(graph, 0, n);
        // System.out.println(Arrays.toString(dist));
        // 期待: [0, 1, 0, 1, 1]
    }

    // static int[] zeroOneBFS(List<List<int[]>> graph, int start, int n) {
    //     int[] dist = new int[n];
    //     Arrays.fill(dist, Integer.MAX_VALUE);
    //     dist[start] = 0;
    //
    //     Deque<Integer> deque = new ArrayDeque<>();
    //     deque.addFirst(start);
    //
    //     while (!deque.isEmpty()) {
    //         int u = deque.pollFirst();
    //
    //         for (int[] edge : graph.get(u)) {
    //             int v = edge[0], w = edge[1];
    //             if (dist[u] + w < dist[v]) {
    //                 dist[v] = dist[u] + w;
    //                 if (w == 0) {
    //                     deque.addFirst(v);   // 重み0 → 先頭
    //                 } else {
    //                     deque.addLast(v);    // 重み1 → 末尾
    //                 }
    //             }
    //         }
    //     }
    //     return dist;
    // }
}
