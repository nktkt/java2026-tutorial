package problems_intermediate;

/**
 * ===== 中級09: 累積和 (Prefix Sum) =====
 * 出典: Codeforces / AtCoder 頻出テクニック
 *
 * 【問題】
 *   整数の配列が与えられる。
 *   クエリとして [left, right] が複数与えられるとき、
 *   各クエリで nums[left] 〜 nums[right] の合計を高速に求めよ。
 *
 * 【例】
 *   nums = {1, 2, 3, 4, 5}
 *   query(1, 3) → 2+3+4 = 9
 *   query(0, 4) → 1+2+3+4+5 = 15
 *   query(2, 2) → 3
 *
 * 【累積和の考え方】
 *   prefix[i] = nums[0] + nums[1] + ... + nums[i-1]
 *   prefix = {0, 1, 3, 6, 10, 15}
 *
 *   区間 [left, right] の合計 = prefix[right+1] - prefix[left]
 *
 *   前処理 O(n)、各クエリ O(1) で回答できる！
 *
 * 【学べること】
 *   - 累積和の構築と利用
 *   - 前処理による高速化パターン
 *   - 競技プログラミングの必須テクニック
 */
public class P21_PrefixSum {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};

        // ここにコードを書こう
        // int[] prefix = buildPrefixSum(nums);
        // System.out.println("query(1,3) = " + rangeSum(prefix, 1, 3));  // → 9
        // System.out.println("query(0,4) = " + rangeSum(prefix, 0, 4));  // → 15
        // System.out.println("query(2,2) = " + rangeSum(prefix, 2, 2));  // → 3
    }

    // ========== 解答例 ==========
    // static int[] buildPrefixSum(int[] nums) {
    //     int[] prefix = new int[nums.length + 1];
    //     // prefix[0] = 0 (空区間の合計)
    //     for (int i = 0; i < nums.length; i++) {
    //         prefix[i + 1] = prefix[i] + nums[i];
    //     }
    //     // nums   = {1, 2, 3, 4, 5}
    //     // prefix = {0, 1, 3, 6, 10, 15}
    //     return prefix;
    // }

    // static int rangeSum(int[] prefix, int left, int right) {
    //     return prefix[right + 1] - prefix[left];
    //     // 例: rangeSum(1,3) = prefix[4] - prefix[1] = 10 - 1 = 9
    // }
}
