package problems_beginner;

/**
 * ===== 初級06: 素数判定 (Prime Number) =====
 * 出典: Codeforces A問題 / 数学系の定番
 *
 * 【問題】
 *   正の整数 N が素数かどうか判定せよ。
 *   素数 = 1と自分自身でしか割り切れない2以上の自然数
 *
 * 【例】
 *   2 → true (最小の素数)
 *   7 → true
 *   10 → false (2×5)
 *   1 → false (素数ではない)
 *
 * 【ヒント】
 *   - 2 から √N まで割ってみて、割り切れなければ素数
 *   - なぜ√Nまで？ → N=36 なら 6×6。約数の片方は必ず√N以下
 *   - i * i <= n で √N までのループを表現できる
 *
 * 【学べること】
 *   - 計算量の改善（O(n) → O(√n)）
 *   - 数学的な考え方
 */
public class P16_PrimeNumber {

    public static void main(String[] args) {
        int[] tests = {1, 2, 3, 4, 7, 10, 17, 100, 97};
        for (int n : tests) {
            System.out.println(n + " → " + isPrime(n));
        }
    }

    static boolean isPrime(int n) {
        // ここにコードを書こう
        return false;

        // ========== 解答例 ==========
        // if (n < 2) return false;
        // for (int i = 2; i * i <= n; i++) {
        //     if (n % i == 0) return false;
        // }
        // return true;
    }
}
