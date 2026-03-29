package problems_expert;

import java.util.*;

/**
 * ===== エキスパート12: N-Queens =====
 * 出典: LC #51
 *
 * 【問題】
 *   NxN のチェス盤に N 個のクイーンを、互いに攻撃し合わないように配置せよ。
 *   クイーンは縦・横・斜めの全方向に何マスでも移動できる。
 *
 * 【例】 N=4 → 2つの解がある
 *   解1:          解2:
 *   . Q . .       . . Q .
 *   . . . Q       Q . . .
 *   Q . . .       . . . Q
 *   . . Q .       . Q . .
 *
 *   N=1 → 1解、N=2 → 0解、N=3 → 0解、N=8 → 92解
 *
 * 【アルゴリズム: バックトラッキング】
 *   1行ずつクイーンを配置していく（各行に必ず1つ配置）。
 *   配置時に「同じ列」「同じ対角線」に既にクイーンがないかチェック。
 *   配置不可なら前の行に戻って別の列を試す（バックトラック）。
 *
 * 【対角線の管理（重要！）】
 *   2種類の対角線がある:
 *
 *   対角線1（左上→右下、\方向）: row - col が同じマスは同じ対角線上
 *     例（4x4）:
 *       row-col: -3  -2  -1   0
 *                -2  -1   0   1
 *                -1   0   1   2
 *                 0   1   2   3
 *     row - col は -(n-1) 〜 (n-1) の範囲 → 配列にするには +n のオフセット
 *
 *   対角線2（右上→左下、/方向）: row + col が同じマスは同じ対角線上
 *     例（4x4）:
 *       row+col:  0   1   2   3
 *                 1   2   3   4
 *                 2   3   4   5
 *                 3   4   5   6
 *     row + col は 0 〜 2*(n-1) の範囲
 *
 * 【バックトラッキングの具体例（N=4）】
 *   row=0: col=0 に配置 → cols[0]=true, diag1[0+4]=true, diag2[0]=true
 *     row=1: col=0 → cols[0]=true でスキップ
 *            col=1 → diag2[0+1]=diag2[1] は false だが diag1[1-1+4]=diag1[4] は true でスキップ
 *            col=2 → OK! queens=[0,2,...], cols[2]=true, diag1[1-2+4]=diag1[3]=true, diag2[1+2]=diag2[3]=true
 *       row=2: col=0 → diag2[2+0]=diag2[2]... diag1[2-0+4]=diag1[6] → OK? cols[0]=true → スキップ
 *              ... 全て配置不可 → バックトラック!
 *            col=3 → queens=[0,3,...] ...
 *     ...（探索を続ける）...
 *
 *   最終的に queens=[1,3,0,2] と queens=[2,0,3,1] の2解が見つかる
 *
 * 【計算量】
 *   時間計算量: O(N!)（概算）
 *     - 1行目は N 通り、2行目は約 N-1 通り、...と枝刈りにより減っていく
 *     - 実際は対角線の制約によりもっと少ないが、最悪は O(N!)
 *     - N=12 で約 14200 解。N=15 以上は時間がかかる
 *   空間計算量: O(N)
 *     - 再帰の深さ: O(N)
 *     - 列・対角線の管理配列: O(N)
 *     - 出力のための解のリスト: O(N^2 × 解の数)
 *
 * 【競プロTips】
 *   - N-Queens はバックトラッキングの代表問題
 *   - ビット演算で列・対角線を管理するとさらに高速（ビットマスクで O(1) 判定）
 *   - 「N-Queens の解の数だけ求める」場合はボードの生成が不要で高速
 *   - バックトラッキングの枝刈り（制約伝播）は探索問題全般に応用できる
 *   - 類似問題: 数独ソルバー、グラフ彩色、ハミルトン経路
 *
 * 【学べること】高度なバックトラッキング、制約充足問題
 */
public class P42_NQueens {

    public static void main(String[] args) {
        int n = 4;
        // List<List<String>> solutions = solveNQueens(n);
        // // N-Queens を解き、全ての解をリストで取得
        //
        // System.out.println(n + "-Queens: " + solutions.size() + "解");
        // // 4-Queens: 2解
        //
        // for (List<String> sol : solutions) {
        //     sol.forEach(System.out::println);
        //     // 各解のボードを行ごとに出力
        //     System.out.println();
        //     // 解と解の間に空行
        // }
    }

    // =====================================================================
    // solveNQueens: N-Queens 問題の全解を求める
    //   n: ボードのサイズ（NxN）& クイーンの数
    //   戻り値: 全ての解のリスト。各解は文字列のリスト（各行の盤面）
    // =====================================================================
    // static List<List<String>> solveNQueens(int n) {
    //     List<List<String>> result = new ArrayList<>();
    //     // 全ての解を格納するリスト
    //
    //     boolean[] cols = new boolean[n];
    //     // cols[c] = true: 列 c にクイーンが既に配置されている
    //     // 各行に1つずつ配置するので行の管理は不要
    //
    //     boolean[] diag1 = new boolean[2 * n];
    //     // diag1: 左上→右下（\方向）の対角線の使用状態
    //     // row - col + n をインデックスとして使う（負の値を避けるため +n）
    //     // diag1[row - col + n] = true: その対角線にクイーンが存在
    //     // サイズは 2*n で十分（row-col の範囲は -(n-1)〜(n-1) → +n で 1〜(2n-1)）
    //
    //     boolean[] diag2 = new boolean[2 * n];
    //     // diag2: 右上→左下（/方向）の対角線の使用状態
    //     // row + col をインデックスとして使う
    //     // diag2[row + col] = true: その対角線にクイーンが存在
    //     // row + col の範囲は 0〜2*(n-1) → サイズ 2*n で十分
    //
    //     int[] queens = new int[n];
    //     // queens[row] = col: row 行目のクイーンの列番号
    //     // 解が見つかった時にボードを生成するために使用
    //
    //     backtrack(0, n, queens, cols, diag1, diag2, result);
    //     // 0行目から探索を開始
    //
    //     return result;
    // }
    //
    // =====================================================================
    // backtrack: バックトラッキングで解を探索する再帰関数
    //   row: 現在配置しようとしている行
    //   n: ボードサイズ
    //   queens: 各行のクイーンの列番号
    //   cols: 列の使用状態
    //   diag1: \方向の対角線の使用状態
    //   diag2: /方向の対角線の使用状態
    //   result: 見つかった解を格納するリスト
    // =====================================================================
    // static void backtrack(int row, int n, int[] queens, boolean[] cols,
    //                        boolean[] diag1, boolean[] diag2, List<List<String>> result) {
    //     if (row == n) {
    //         // ベースケース: 全ての行にクイーンを配置できた → 解が見つかった！
    //
    //         List<String> board = new ArrayList<>();
    //         // この解のボードを文字列のリストとして構築
    //
    //         for (int i = 0; i < n; i++) {
    //             char[] r = new char[n];
    //             // i行目の盤面を文字配列として構築
    //
    //             Arrays.fill(r, '.');
    //             // 全マスを '.'（空）で初期化
    //
    //             r[queens[i]] = 'Q';
    //             // queens[i] 列にクイーンを配置
    //
    //             board.add(new String(r));
    //             // 文字配列を文字列に変換してボードに追加
    //         }
    //
    //         result.add(board);
    //         // 解をリストに追加
    //
    //         return;
    //     }
    //
    //     for (int col = 0; col < n; col++) {
    //         // row 行目のクイーンを col 列に配置できるか全列を試す
    //
    //         if (cols[col] || diag1[row - col + n] || diag2[row + col]) continue;
    //         // 配置不可能な場合はスキップ:
    //         // cols[col]: 同じ列に既にクイーンがある
    //         // diag1[row - col + n]: 同じ \対角線に既にクイーンがある
    //         // diag2[row + col]: 同じ /対角線に既にクイーンがある
    //         // 3つの条件のうち1つでも true なら、この位置には配置できない
    //
    //         queens[row] = col;
    //         // row 行目の col 列にクイーンを仮配置
    //
    //         cols[col] = diag1[row - col + n] = diag2[row + col] = true;
    //         // この列と2つの対角線を「使用中」にマーク
    //         // Java の代入は右から左に連鎖できる: a = b = c = true
    //
    //         backtrack(row + 1, n, queens, cols, diag1, diag2, result);
    //         // 次の行 (row+1) のクイーンを配置しに再帰
    //
    //         cols[col] = diag1[row - col + n] = diag2[row + col] = false;
    //         // バックトラック: 配置を取り消す（元の状態に戻す）
    //         // これにより、同じ行の別の列を試すことができる
    //         // 「使って → 再帰 → 戻す」がバックトラッキングの基本パターン
    //     }
    //     // 全ての列を試しても配置できなかった場合、そのまま return
    //     // → 呼び出し元に戻って、前の行のクイーンを別の位置に変える
    // }
}
