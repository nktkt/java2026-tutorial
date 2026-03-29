package problems_expert;

import java.util.*;

/**
 * ===== エキスパート12: N-Queens =====
 * 出典: LC #51
 *
 * 【問題】
 *   NxN のチェス盤に N 個のクイーンを、互いに攻撃し合わないように配置せよ。
 *   クイーンは縦・横・斜めに移動できる。
 *
 * 【例】 N=4 → 2つの解がある
 *
 * 【アルゴリズム: バックトラッキング】
 *   1行ずつクイーンを配置。列・対角線の使用状態を管理。
 *   配置不可なら戻って別の列を試す。
 *
 * 【学べること】高度なバックトラッキング、制約充足問題
 */
public class P42_NQueens {

    public static void main(String[] args) {
        int n = 4;
        // List<List<String>> solutions = solveNQueens(n);
        // System.out.println(n + "-Queens: " + solutions.size() + "解");
        // for (List<String> sol : solutions) {
        //     sol.forEach(System.out::println);
        //     System.out.println();
        // }
    }

    // static List<List<String>> solveNQueens(int n) {
    //     List<List<String>> result = new ArrayList<>();
    //     boolean[] cols = new boolean[n];
    //     boolean[] diag1 = new boolean[2 * n];  // 左上→右下の対角線
    //     boolean[] diag2 = new boolean[2 * n];  // 右上→左下の対角線
    //     int[] queens = new int[n];              // queens[row] = col
    //
    //     backtrack(0, n, queens, cols, diag1, diag2, result);
    //     return result;
    // }
    //
    // static void backtrack(int row, int n, int[] queens, boolean[] cols,
    //                        boolean[] diag1, boolean[] diag2, List<List<String>> result) {
    //     if (row == n) {
    //         List<String> board = new ArrayList<>();
    //         for (int i = 0; i < n; i++) {
    //             char[] r = new char[n];
    //             Arrays.fill(r, '.');
    //             r[queens[i]] = 'Q';
    //             board.add(new String(r));
    //         }
    //         result.add(board);
    //         return;
    //     }
    //     for (int col = 0; col < n; col++) {
    //         if (cols[col] || diag1[row - col + n] || diag2[row + col]) continue;
    //         queens[row] = col;
    //         cols[col] = diag1[row - col + n] = diag2[row + col] = true;
    //         backtrack(row + 1, n, queens, cols, diag1, diag2, result);
    //         cols[col] = diag1[row - col + n] = diag2[row + col] = false;
    //     }
    // }
}
