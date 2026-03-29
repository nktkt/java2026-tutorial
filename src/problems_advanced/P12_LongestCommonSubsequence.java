package problems_advanced;

/**
 * ===== 上級02: 最長共通部分列 (Longest Common Subsequence) =====
 * 出典: LeetCode #1143 / DP の代表問題
 *
 * 【問題】
 *   2つの文字列が与えられる。共通部分列の最長の長さを求めよ。
 *   部分列: 元の順序を保ったまま一部の文字を取り出したもの（連続でなくてOK）
 *
 * 【例】
 *   text1 = "abcde", text2 = "ace"
 *   → 出力: 3  (共通部分列 "ace")
 *
 *   text1 = "abc", text2 = "abc"
 *   → 出力: 3
 *
 *   text1 = "abc", text2 = "def"
 *   → 出力: 0  (共通する文字がない)
 *
 * 【DPテーブルの考え方】
 *   dp[i][j] = text1の先頭i文字と text2の先頭j文字の LCS の長さ
 *
 *   text1[i-1] == text2[j-1] のとき:
 *     → dp[i][j] = dp[i-1][j-1] + 1  (共通文字を見つけた！+1)
 *
 *   text1[i-1] != text2[j-1] のとき:
 *     → dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])
 *       (text1を1文字減らした場合 or text2を1文字減らした場合の大きい方)
 *
 * 【DPテーブルの例】 text1="ace", text2="abcde"
 *       ""  a  b  c  d  e
 *   ""   0  0  0  0  0  0
 *   a    0  1  1  1  1  1
 *   c    0  1  1  2  2  2
 *   e    0  1  1  2  2  3
 *
 * 【学べること】
 *   - 二次元DPテーブルの構築
 *   - 部分問題への分割（文字列を1文字ずつ処理）
 *   - 競技プログラミングの頻出パターン
 */
public class P12_LongestCommonSubsequence {

    public static void main(String[] args) {
        System.out.println(lcs("abcde", "ace"));   // → 3
        System.out.println(lcs("abc", "abc"));      // → 3
        System.out.println(lcs("abc", "def"));      // → 0
        System.out.println(lcs("AGGTAB", "GXTXAYB")); // → 4 ("GTAB")
    }

    static int lcs(String text1, String text2) {

        // ここにコードを書こう
        return 0; // 仮の戻り値


        // ========== 解答例 ==========
        // int m = text1.length();
        // int n = text2.length();
        //
        // // dp[i][j] = text1の先頭i文字とtext2の先頭j文字のLCS長
        // int[][] dp = new int[m + 1][n + 1];
        // // dp[0][j] と dp[i][0] は0（空文字列との共通部分列は0）
        //
        // for (int i = 1; i <= m; i++) {
        //     for (int j = 1; j <= n; j++) {
        //         if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
        //             // 文字が一致 → 斜め上 + 1
        //             dp[i][j] = dp[i - 1][j - 1] + 1;
        //         } else {
        //             // 文字が不一致 → 上 or 左 の大きい方
        //             dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        //         }
        //     }
        // }
        // return dp[m][n];
    }
}
