package problems_advanced;

import java.util.*;

/**
 * ===== 上級12: ダイクストラ法 (Dijkstra's Algorithm) =====
 * 出典: Codeforces / AtCoder 上級 / グラフ理論の定番
 *
 * 【問題】
 *   重み付きグラフで、始点から各頂点への最短距離を求めよ。
 *
 * 【BFSとの違い】
 *   BFS: 辺の重みが全て同じ（1）のグラフ向け
 *   ダイクストラ: 辺の重みが異なるグラフ向け（負の辺はNG）
 *
 * 【アルゴリズム】
 *   1. 始点の距離を0、他を∞で初期化
 *   2. 「未確定で最短距離の頂点」を選ぶ（優先度付きキューで高速化）
 *   3. その頂点から到達できる頂点の距離を更新（緩和）
 *   4. 全頂点が確定するまで繰り返す
 *
 * 【緩和(Relaxation)】
 *   dist[隣] > dist[今] + 辺の重み なら
 *   dist[隣] = dist[今] + 辺の重み に更新
 *
 * 【計算量】
 *   O((V + E) log V)  ※ V=頂点数, E=辺数
 *
 * 【学べること】
 *   - ダイクストラ法の実装
 *   - PriorityQueue を使った最適化
 *   - 重み付きグラフの最短経路
 */
public class P30_Dijkstra {

    public static void main(String[] args) {
        //       1
        //   0 ----→ 1
        //   |        |
        //  4|    2   |3
        //   ↓  ↗    ↓
        //   2 ----→ 3
        //       5

        int n = 4;
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        // addEdge(graph, from, to, weight)
        addEdge(graph, 0, 1, 1);
        addEdge(graph, 0, 2, 4);
        addEdge(graph, 1, 3, 3);
        addEdge(graph, 2, 1, 2);
        addEdge(graph, 2, 3, 5);

        // int[] dist = dijkstra(graph, 0, n);
        // System.out.println("頂点0からの最短距離:");
        // for (int i = 0; i < n; i++) {
        //     System.out.println("  0 → " + i + " : " + dist[i]);
        // }
        // 期待: 0→0:0, 0→1:1, 0→2:4, 0→3:4
    }

    static void addEdge(List<List<int[]>> graph, int from, int to, int weight) {
        graph.get(from).add(new int[]{to, weight});
    }

    // ========== 解答例 ==========
    // static int[] dijkstra(List<List<int[]>> graph, int start, int n) {
    //     int[] dist = new int[n];
    //     Arrays.fill(dist, Integer.MAX_VALUE);
    //     dist[start] = 0;
    //
    //     // PriorityQueue: {距離, 頂点} のペア。距離が小さい順に取り出す
    //     PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    //     pq.add(new int[]{0, start});
    //
    //     while (!pq.isEmpty()) {
    //         int[] current = pq.poll();
    //         int d = current[0];      // 現在の距離
    //         int u = current[1];      // 現在の頂点
    //
    //         if (d > dist[u]) continue;  // すでにより短い距離で確定済み → スキップ
    //
    //         for (int[] edge : graph.get(u)) {
    //             int v = edge[0];         // 隣の頂点
    //             int weight = edge[1];    // 辺の重み
    //
    //             // 緩和: 現在の経路の方が短いか？
    //             if (dist[u] + weight < dist[v]) {
    //                 dist[v] = dist[u] + weight;
    //                 pq.add(new int[]{dist[v], v});
    //             }
    //         }
    //     }
    //     return dist;
    // }
}
