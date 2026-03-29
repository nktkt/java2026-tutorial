package problems_advanced;

/**
 * ===== 上級10: Union-Find (素集合データ構造) =====
 * 出典: Codeforces / AtCoder 上級問題の定番
 *
 * 【問題】
 *   N人の人がいる。「AさんとBさんは友達」という情報が複数与えられる。
 *   友達の友達も友達とするとき、友達グループは何個あるか？
 *
 * 【例】
 *   N=5, 友達関係: (0,1), (2,3), (3,4)
 *   → グループ: {0,1}, {2,3,4} の 2グループ
 *
 * 【Union-Findとは？】
 *   グループの「合体(union)」と「同じグループか判定(find)」を高速に行うデータ構造。
 *
 *   操作:
 *     find(x)      → x が属するグループの代表者を返す O(α(n)) ≈ O(1)
 *     union(x, y)  → x と y を同じグループにする
 *     connected(x,y) → x と y が同じグループか判定
 *
 * 【最適化テクニック】
 *   経路圧縮（Path Compression）: find時にノードを直接ルートに繋ぐ
 *   ランク統合（Union by Rank）: 小さい木を大きい木の下に繋ぐ
 *
 * 【学べること】
 *   - Union-Find の実装
 *   - グラフの連結成分
 *   - 競技プログラミングの頻出データ構造
 */
public class P28_UnionFind {

    // ========== 解答例: Union-Find クラス ==========
    // static int[] parent;
    // static int[] rank;
    //
    // static void init(int n) {
    //     parent = new int[n];
    //     rank = new int[n];
    //     for (int i = 0; i < n; i++) {
    //         parent[i] = i;   // 最初は自分自身が親（全員バラバラ）
    //         rank[i] = 0;
    //     }
    // }
    //
    // static int find(int x) {
    //     if (parent[x] != x) {
    //         parent[x] = find(parent[x]);   // 経路圧縮
    //     }
    //     return parent[x];
    // }
    //
    // static void union(int x, int y) {
    //     int rootX = find(x);
    //     int rootY = find(y);
    //     if (rootX == rootY) return;   // すでに同じグループ
    //
    //     // ランクが小さい方を大きい方の下に繋ぐ
    //     if (rank[rootX] < rank[rootY]) {
    //         parent[rootX] = rootY;
    //     } else if (rank[rootX] > rank[rootY]) {
    //         parent[rootY] = rootX;
    //     } else {
    //         parent[rootY] = rootX;
    //         rank[rootX]++;
    //     }
    // }
    //
    // static boolean connected(int x, int y) {
    //     return find(x) == find(y);
    // }

    public static void main(String[] args) {
        int n = 5;

        // init(n);
        // union(0, 1);
        // union(2, 3);
        // union(3, 4);
        //
        // System.out.println("0と1は同じグループ? " + connected(0, 1)); // true
        // System.out.println("0と2は同じグループ? " + connected(0, 2)); // false
        // System.out.println("2と4は同じグループ? " + connected(2, 4)); // true
        //
        // // グループ数を数える
        // java.util.HashSet<Integer> groups = new java.util.HashSet<>();
        // for (int i = 0; i < n; i++) {
        //     groups.add(find(i));
        // }
        // System.out.println("グループ数: " + groups.size());  // → 2
    }
}
