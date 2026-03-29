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
 *   辺の重みが 0 と 1 だけのグラフに特化した最短経路アルゴリズム。
 *   通常の BFS（重み1のみ）と Dijkstra（任意の重み）の中間的な存在。
 *
 *   Deque（両端キュー）を使い:
 *   - 重み0の辺 → addFirst（先頭に追加）
 *   - 重み1の辺 → addLast（末尾に追加）
 *
 *   これにより O(V+E) で最短距離が求められる（Dijkstra の O((V+E) log V) より速い）。
 *
 * 【なぜ Deque を使うのか（核心的な直感）】
 *   通常の BFS ではキューの中の頂点は「距離 d」と「距離 d+1」の2種類だけ。
 *   0-1 BFS では:
 *   - 重み0の辺で遷移 → 距離が変わらない → 先頭に追加（現在と同じ距離のグループ）
 *   - 重み1の辺で遷移 → 距離が1増える → 末尾に追加（次の距離のグループ）
 *
 *   結果、Deque 内の頂点は常に「距離 d」「距離 d+1」の順に並ぶ。
 *   これは BFS のキューと同じ性質なので、正しい最短距離が求められる。
 *
 * 【具体例】
 *   5頂点のグラフ:
 *   0 →(1)→ 1 →(0)→ 3 →(0)→ 4
 *   0 →(0)→ 2 →(1)→ 3
 *
 *   始点: 0
 *
 *   初期状態: dist = [0, INF, INF, INF, INF], deque = [0]
 *
 *   ステップ1: u=0 を取り出し
 *     辺 0→1(重み1): dist[1] = 0+1 = 1, addLast(1) → deque = [2, 1]
 *     辺 0→2(重み0): dist[2] = 0+0 = 0, addFirst(2) → deque = [2, 1]
 *
 *   ステップ2: u=2 を取り出し（先頭。距離0）
 *     辺 2→3(重み1): dist[3] = 0+1 = 1, addLast(3) → deque = [1, 3]
 *
 *   ステップ3: u=1 を取り出し（先頭。距離1）
 *     辺 1→3(重み0): dist[1]+0 = 1 ≥ dist[3]=1 → 更新なし
 *
 *   ステップ4: u=3 を取り出し（先頭。距離1）
 *     辺 3→4(重み0): dist[4] = 1+0 = 1, addFirst(4) → deque = [4]
 *
 *   ステップ5: u=4 を取り出し（先頭。距離1）
 *     辺なし
 *
 *   最終結果: dist = [0, 1, 0, 1, 1]
 *
 *   頂点2は重み0の辺で到達できるので距離0。
 *   頂点1,3,4は重み1の辺を1回は通る必要があるので距離1。
 *
 * 【Dijkstra との比較】
 *   - Dijkstra: O((V+E) log V)。PriorityQueue を使い、任意の正の重みに対応
 *   - 0-1 BFS: O(V+E)。Deque を使い、重み0と1だけに特化。log V 分だけ速い
 *   - 0-1 BFS は Dijkstra の特殊ケースとして理解できる
 *     （PQ の代わりに Deque を使えるのは、重みが0と1しかないから）
 *
 * 【計算量】
 *   時間計算量: O(V + E)
 *     - 各頂点は Deque に最大2回追加される（0の辺と1の辺から各1回）
 *     - 各辺は1回だけ処理される
 *     - Deque の addFirst/addLast/pollFirst は全て O(1)
 *   空間計算量: O(V + E)
 *     - dist 配列: O(V)
 *     - グラフの隣接リスト: O(V + E)
 *     - Deque: 最悪 O(V)
 *
 * 【競プロTips】
 *   - 「辺の重みが0か1」という条件が出たら 0-1 BFS を疑う
 *   - グリッド問題で「壁を壊すコストが1、移動コストが0」のような設定に頻出
 *   - AtCoder では ABC の D,E 問題レベルで出題されることがある
 *   - 辺の重みが 0 と 1 でなく、0 と c（定数）なら、距離を c で正規化して使える
 *   - 辺の重みが 0, 1, 2, ... k なら k+1 層の Deque や Dial's algorithm を使う
 *   - 頂点を複数回キューに追加しても、dist[v] が既に最適なら自動でスキップされる
 *     （ただし明示的に visited 配列を使うとより効率的）
 *
 * 【学べること】0-1 BFS、Dequeの応用、Dijkstraとの使い分け
 */
public class P43_ZeroOneBFS {

    public static void main(String[] args) {
        int n = 5;
        // n = 頂点数

        // graph[u] = list of {v, weight(0 or 1)}
        // graph: 隣接リスト形式のグラフ。graph.get(u) は頂点 u から出る辺のリスト
        // 各辺は int[] {行き先の頂点, 重み(0 or 1)} で表現
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        // n 個の空リストで隣接リストを初期化

        // 例: 0→1(重み1), 0→2(重み0), 1→3(重み0), 2→3(重み1), 3→4(重み0)
        graph.get(0).add(new int[]{1, 1});  // 頂点0 → 頂点1、重み1
        graph.get(0).add(new int[]{2, 0});  // 頂点0 → 頂点2、重み0
        graph.get(1).add(new int[]{3, 0});  // 頂点1 → 頂点3、重み0
        graph.get(2).add(new int[]{3, 1});  // 頂点2 → 頂点3、重み1
        graph.get(3).add(new int[]{4, 0});  // 頂点3 → 頂点4、重み0

        // int[] dist = zeroOneBFS(graph, 0, n);
        // // 0-1 BFS を実行し、始点0から各頂点への最短距離を取得
        // System.out.println(Arrays.toString(dist));
        // // 期待: [0, 1, 0, 1, 1]
        // // 0→0: 距離0（始点）
        // // 0→2: 距離0（重み0の辺1本）
        // // 0→1: 距離1（重み1の辺1本）
        // // 0→3: 距離1（0→1(1)→3(0) または 0→2(0)→3(1) どちらも距離1）
        // // 0→4: 距離1（0→1(1)→3(0)→4(0) で距離1）
    }

    // =====================================================================
    // zeroOneBFS: 0-1 BFS で始点から各頂点への最短距離を求める
    //   graph: 隣接リスト形式のグラフ。graph.get(u) は {v, w} のリスト
    //   start: 始点の頂点番号
    //   n: 頂点数
    //   戻り値: dist[v] = start から v への最短距離。到達不能なら Integer.MAX_VALUE
    // =====================================================================
    // static int[] zeroOneBFS(List<List<int[]>> graph, int start, int n) {
    //     int[] dist = new int[n];
    //     // dist[v] = 始点から頂点 v への最短距離
    //
    //     Arrays.fill(dist, Integer.MAX_VALUE);
    //     // 全頂点の距離を無限大（未到達）で初期化
    //     // Integer.MAX_VALUE = 2^31 - 1 ≈ 2.1 × 10^9
    //
    //     dist[start] = 0;
    //     // 始点の距離は 0
    //
    //     Deque<Integer> deque = new ArrayDeque<>();
    //     // Deque（両端キュー）: 先頭と末尾の両方から要素を追加・取り出しできる
    //     // ArrayDeque は LinkedList より高速（配列ベース、キャッシュ効率が良い）
    //     // 通常の BFS では Queue を使うが、0-1 BFS では Deque が必須
    //
    //     deque.addFirst(start);
    //     // 始点を Deque の先頭に追加
    //
    //     while (!deque.isEmpty()) {
    //         int u = deque.pollFirst();
    //         // Deque の先頭から頂点を取り出す
    //         // 先頭の頂点は常に「現在の最小距離」の頂点（BFS の性質による）
    //
    //         for (int[] edge : graph.get(u)) {
    //             // 頂点 u から出る全ての辺を処理
    //
    //             int v = edge[0], w = edge[1];
    //             // v = 辺の行き先、w = 辺の重み（0 または 1）
    //
    //             if (dist[u] + w < dist[v]) {
    //                 // 緩和条件: u 経由で v に到達する方が、現在の dist[v] より短い場合
    //                 // dist[u] + w: u を経由した v への距離
    //                 // dist[v]: 現在知られている v への最短距離
    //
    //                 dist[v] = dist[u] + w;
    //                 // v の最短距離を更新
    //
    //                 if (w == 0) {
    //                     deque.addFirst(v);
    //                     // 重み0の辺 → Deque の先頭に追加
    //                     // 距離が変わらないので、現在の距離グループの先頭に入れる
    //                     // これにより、距離が同じ頂点が先に処理される（BFS の性質を維持）
    //                 } else {
    //                     deque.addLast(v);
    //                     // 重み1の辺 → Deque の末尾に追加
    //                     // 距離が1増えるので、次の距離グループの末尾に入れる
    //                     // 通常の BFS でキューの末尾に追加するのと同じ
    //                 }
    //                 // この addFirst / addLast の使い分けが 0-1 BFS の全て！
    //                 // Deque 内は常に [距離d の頂点群, 距離d+1 の頂点群] の順に並ぶ
    //             }
    //             // dist[u] + w >= dist[v] の場合: v は既により短い経路で到達済み → 何もしない
    //         }
    //     }
    //
    //     return dist;
    //     // 全頂点への最短距離を返す
    //     // 到達不能な頂点は Integer.MAX_VALUE のまま
    // }
}
