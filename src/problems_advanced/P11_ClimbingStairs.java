package problems_advanced;

/**
 * ===== 上級01: 階段の登り方 (Climbing Stairs) =====
 * 出典: LeetCode #70 / 動的計画法（DP）の定番
 *
 * 【問題】
 *   n 段の階段がある。1回に1段または2段登れる。
 *   頂上まで登る方法は何通りあるか？
 *
 * 【例】
 *   n=1 → 1通り   (1)
 *   n=2 → 2通り   (1+1, 2)
 *   n=3 → 3通り   (1+1+1, 1+2, 2+1)
 *   n=4 → 5通り   (1+1+1+1, 1+1+2, 1+2+1, 2+1+1, 2+2)
 *   n=5 → 8通り
 *
 * 【気づき】
 *   1, 2, 3, 5, 8, ... → フィボナッチ数列！
 *   n段目に到達するには:
 *   - (n-1)段目から1段登る
 *   - (n-2)段目から2段登る
 *   → dp[n] = dp[n-1] + dp[n-2]
 *
 * 【動的計画法(DP)とは？】
 *   大きな問題を小さな部分問題に分けて解く手法。
 *   部分問題の結果を配列に保存して再利用する。
 *
 *   DPの3ステップ:
 *   ① 状態の定義: dp[i] = i段目まで登る方法の数
 *   ② 漸化式:     dp[i] = dp[i-1] + dp[i-2]
 *   ③ 初期値:     dp[1] = 1, dp[2] = 2
 *
 * 【学べること】
 *   - 動的計画法（DP）の基本パターン
 *   - フィボナッチ数列との関係
 *   - 再帰 vs DP の比較
 */
public class P11_ClimbingStairs {

    public static void main(String[] args) {
        for (int n = 1; n <= 10; n++) {
            System.out.println("n=" + n + " → " + climbStairs(n) + "通り");
        }
    }

    static int climbStairs(int n) {

        // ここにコードを書こう
        return 0; // 仮の戻り値


        // ========== 解答例1: DP配列 ==========
        // if (n <= 2) return n;
        //
        // int[] dp = new int[n + 1];
        // dp[1] = 1;    // 1段: 1通り
        // dp[2] = 2;    // 2段: 2通り
        //
        // for (int i = 3; i <= n; i++) {
        //     dp[i] = dp[i - 1] + dp[i - 2];
        //     // i段目 = (i-1段目から1段) + (i-2段目から2段)
        // }
        // return dp[n];

        // ========== 解答例2: 変数2つだけ（メモリ節約版） ==========
        // if (n <= 2) return n;
        //
        // int prev2 = 1;   // dp[i-2] に相当
        // int prev1 = 2;   // dp[i-1] に相当
        //
        // for (int i = 3; i <= n; i++) {
        //     int current = prev1 + prev2;
        //     prev2 = prev1;
        //     prev1 = current;
        // }
        // return prev1;
    }
}
