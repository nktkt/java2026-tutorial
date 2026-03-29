package problems_intermediate;

/**
 * ===== 中級06: スライディングウィンドウ (Sliding Window) =====
 * 出典: LeetCode #643 風
 *
 * 【問題】
 *   整数の配列と整数 k が与えられる。
 *   長さ k の連続する部分配列の合計の最大値を求めよ。
 *
 * 【例】
 *   nums = {2, 1, 5, 1, 3, 2}, k = 3
 *   → 出力: 9  (部分配列 [5, 1, 3])
 *
 * 【アプローチ: スライディングウィンドウ】
 *   固定サイズの「窓」を配列上でスライドさせる。
 *   窓を右に1つ動かすとき:
 *   - 右端の新しい要素を足す
 *   - 左端の古い要素を引く
 *   → 毎回k個を足し直さなくていい → O(n)
 *
 *   [2, 1, 5] 1, 3, 2  → 合計 8
 *    2 [1, 5, 1] 3, 2  → 8 - 2 + 1 = 7
 *    2, 1 [5, 1, 3] 2  → 7 - 1 + 3 = 9 ★最大
 *    2, 1, 5 [1, 3, 2] → 9 - 5 + 2 = 6
 *
 * 【学べること】
 *   - スライディングウィンドウのパターン
 *   - O(n*k) → O(n) の最適化
 */
public class P18_SlidingWindow {

    public static void main(String[] args) {
        System.out.println(maxSumSubarray(new int[]{2, 1, 5, 1, 3, 2}, 3)); // → 9
        System.out.println(maxSumSubarray(new int[]{1, 2, 3, 4, 5}, 2));    // → 9
    }

    static int maxSumSubarray(int[] nums, int k) {
        // ここにコードを書こう
        return 0;

        // ========== 解答例 ==========
        // // 最初のウィンドウの合計
        // int windowSum = 0;
        // for (int i = 0; i < k; i++) {
        //     windowSum += nums[i];
        // }
        // int maxSum = windowSum;
        //
        // // ウィンドウをスライド
        // for (int i = k; i < nums.length; i++) {
        //     windowSum += nums[i];         // 右端を追加
        //     windowSum -= nums[i - k];     // 左端を除去
        //     maxSum = Math.max(maxSum, windowSum);
        // }
        // return maxSum;
    }
}
