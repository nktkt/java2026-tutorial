package problems_intermediate;

/**
 * ===== 中級02: 最大部分配列和 (Maximum Subarray) =====
 * 出典: LeetCode #53 / Kadane's Algorithm
 *
 * 【問題】
 *   整数の配列が与えられる。連続する部分配列の合計の最大値を求めよ。
 *   （部分配列 = 配列の中の連続した要素）
 *
 * 【例】
 *   nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4}
 *   → 出力: 6
 *   解説: 部分配列 [4, -1, 2, 1] の合計が最大（= 6）
 *
 *   nums = {1, 2, 3, -2, 5}
 *   → 出力: 9  (全部足すのが最大: 1+2+3-2+5)
 *
 *   nums = {-1, -2, -3}
 *   → 出力: -1  (最も大きい1要素 = -1)
 *
 * 【アプローチ: Kadane's Algorithm】
 *   1. currentSum: 「今見ている位置で終わる部分配列の最大和」
 *   2. maxSum: 「今までに見つけた最大和」
 *
 *   各要素で判断:
 *   - 今の要素を「既存の部分配列に追加する」か「新しく始める」か
 *   - currentSum = Math.max(nums[i], currentSum + nums[i])
 *     → 既存の合計 + 今の値 vs 今の値だけ、大きい方を選ぶ
 *   - maxSum = Math.max(maxSum, currentSum)
 *
 * 【なぜこれで解けるか？】
 *   currentSum が負になったら、その部分配列を引き継ぐより
 *   新しく始めた方が得。この判断を各要素で行う。
 *
 * 【学べること】
 *   - 動的計画法（DP）の入門
 *   - Math.max() の使い方
 *   - 「現在の最適解を更新しながら進む」パターン
 */
public class P07_MaxSubarraySum {

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        // ここにコードを書こう


        // ========== 解答例1: 総当たり O(n²) まず理解用 ==========
        // int maxSum = nums[0];
        // for (int i = 0; i < nums.length; i++) {
        //     int sum = 0;
        //     for (int j = i; j < nums.length; j++) {
        //         sum += nums[j];            // i〜j の合計
        //         maxSum = Math.max(maxSum, sum);
        //     }
        // }
        // System.out.println(maxSum);

        // ========== 解答例2: Kadane's Algorithm O(n) ==========
        // int currentSum = nums[0];   // 現在の部分配列の合計
        // int maxSum = nums[0];       // 全体の最大値
        //
        // for (int i = 1; i < nums.length; i++) {
        //     // 「今までの合計 + 今の値」vs「今の値だけ」→ 大きい方を選ぶ
        //     currentSum = Math.max(nums[i], currentSum + nums[i]);
        //     // もし currentSum + nums[i] < nums[i] なら
        //     //   → 今までの合計が足を引っ張っている → 新しく始める
        //
        //     maxSum = Math.max(maxSum, currentSum);
        //     // 最大値を更新
        // }
        // System.out.println(maxSum);  // → 6
    }
}
