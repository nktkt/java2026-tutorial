package problems_advanced;

/**
 * ===== 上級06: 島の数 (Number of Islands) - DFS =====
 * 出典: LeetCode #200
 *
 * 【問題】
 *   2Dグリッドが与えられる。'1'は陸地、'0'は水。
 *   上下左右に隣接する陸地で構成される「島」の数を数えよ。
 *
 * 【例】
 *   1 1 0 0 0
 *   1 1 0 0 0
 *   0 0 1 0 0
 *   0 0 0 1 1
 *   → 島は 3つ
 *
 * 【DFS（深さ優先探索）とは？】
 *   1つの方向にどんどん深く進み、行き止まりで戻って別の方向を探す。
 *   スタック（または再帰）を使う。
 *
 * 【アルゴリズム】
 *   1. グリッドを走査して '1' を見つけたら島の数+1
 *   2. その '1' からDFSで隣接する '1' をすべて '0' に変える（訪問済みマーク）
 *   3. 1に戻って次の未訪問の '1' を探す
 *
 * 【学べること】
 *   - DFS（深さ優先探索）の実装
 *   - 2Dグリッド上の探索パターン
 *   - 再帰の実践的な使い方
 */
public class P24_DFS_Islands {

    public static void main(String[] args) {
        char[][] grid = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
        System.out.println("島の数: " + numIslands(grid)); // → 3
    }

    static int numIslands(char[][] grid) {
        // ここにコードを書こう
        return 0;

        // ========== 解答例 ==========
        // int count = 0;
        // for (int i = 0; i < grid.length; i++) {
        //     for (int j = 0; j < grid[0].length; j++) {
        //         if (grid[i][j] == '1') {
        //             count++;          // 新しい島を発見
        //             dfs(grid, i, j);  // この島の陸地をすべて訪問済みにする
        //         }
        //     }
        // }
        // return count;
    }

    // static void dfs(char[][] grid, int i, int j) {
    //     // 範囲外 or 水 → 何もしない
    //     if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) return;
    //     if (grid[i][j] == '0') return;
    //
    //     grid[i][j] = '0';   // 訪問済みにする（'1' → '0'）
    //
    //     // 上下左右に再帰
    //     dfs(grid, i - 1, j);  // 上
    //     dfs(grid, i + 1, j);  // 下
    //     dfs(grid, i, j - 1);  // 左
    //     dfs(grid, i, j + 1);  // 右
    // }
}
