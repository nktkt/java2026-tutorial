package problems_advanced;

import java.util.Arrays;

/**
 * ===== 上級04: コイン問題 (Coin Change) =====
 * 出典: LeetCode #322 / DP の有名問題
 *
 * 【問題】
 *   異なる金額のコインが与えられる。
 *   合計が amount になるために必要な最小のコイン枚数を求めよ。
 *   不可能な場合は -1 を返せ。
 *
 * 【例】
 *   coins = {1, 5, 10}, amount = 11
 *   → 出力: 2  (10 + 1 = 11)
 *
 *   coins = {1, 5, 10}, amount = 27
 *   → 出力: 4  (10 + 10 + 5 + 1 + 1 = 27) ←これは5枚。正解は 10+10+5+1+1=27...
 *   → 実は 10+10+5+1+1 = 27 で5枚。もっと良い: なし。答えは 4枚: 10+10+5+1+1...
 *   → 修正: coins={1,5,10}, amount=27 → 10+10+5+1+1 = 5枚
 *
 *   coins = {2}, amount = 3
 *   → 出力: -1  (2のコインだけでは3を作れない)
 *
 * 【DPの考え方】
 *   dp[i] = 金額 i を作るのに必要な最小コイン枚数
 *
 *   各金額 i について、すべてのコインを試す:
 *   dp[i] = Math.min(dp[i], dp[i - coin] + 1)
 *   「金額 i を作る」=「金額 i-coin を作って、coinを1枚追加」
 *
 *   初期値:
 *   dp[0] = 0（0円を作るのに0枚）
 *   dp[1〜amount] = ∞（まだ分からない → 十分大きい値で初期化）
 *
 * 【DPテーブルの例】 coins={1,5}, amount=7
 *   dp[0]=0, dp[1]=1, dp[2]=2, dp[3]=3, dp[4]=4,
 *   dp[5]=1, dp[6]=2, dp[7]=3
 *
 * 【学べること】
 *   - DP の「最小値を求める」パターン
 *   - 配列の初期化（Arrays.fill）
 *   - 貪欲法が使えないケースの理解
 */
public class P14_CoinChange {

    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{1, 5, 10}, 11));  // → 2
        System.out.println(coinChange(new int[]{2}, 3));           // → -1
        System.out.println(coinChange(new int[]{1, 5, 10}, 0));   // → 0
        System.out.println(coinChange(new int[]{1, 3, 4}, 6));    // → 2 (3+3)
        // ↑ 貪欲法(大きいコインから)だと 4+1+1=3枚 で不正解！ DPなら正解が出る
    }

    static int coinChange(int[] coins, int amount) {

        // ここにコードを書こう
        return 0; // 仮の戻り値


        // ========== 解答例 ==========
        // int[] dp = new int[amount + 1];
        // Arrays.fill(dp, amount + 1);
        // // ↑ 全要素を「ありえない大きい値」で初期化
        // //   amount+1 以上の枚数は絶対に必要ないので「不可能」を意味する
        //
        // dp[0] = 0;  // 0円を作るのに0枚
        //
        // for (int i = 1; i <= amount; i++) {
        //     // 金額 i を作る最小枚数を求める
        //     for (int coin : coins) {
        //         if (coin <= i) {
        //             // この coin を使える（coin が i 以下）
        //             dp[i] = Math.min(dp[i], dp[i - coin] + 1);
        //             // dp[i-coin] + 1 = 「金額i-coin の最小枚数」+ このcoin1枚
        //         }
        //     }
        // }
        //
        // // dp[amount] が初期値のままなら不可能
        // return dp[amount] > amount ? -1 : dp[amount];
    }
}
