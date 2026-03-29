package problems_beginner;

/**
 * ===== 初級07: 最大公約数 (GCD - Greatest Common Divisor) =====
 * 出典: Codeforces A/B問題 / 数学の基本
 *
 * 【問題】
 *   2つの正の整数の最大公約数を求めよ。
 *
 * 【例】
 *   GCD(12, 8) → 4
 *   GCD(24, 36) → 12
 *   GCD(7, 5) → 1 (互いに素)
 *
 * 【ユークリッドの互除法】
 *   GCD(a, b) = GCD(b, a % b)
 *   a % b == 0 になったとき、b が最大公約数
 *
 *   例: GCD(24, 36)
 *     → GCD(36, 24)   ※ a < b なら入れ替わる
 *     → GCD(24, 12)   36 % 24 = 12
 *     → GCD(12, 0)    24 % 12 = 0
 *     → 答え: 12
 *
 * 【最小公倍数(LCM)】
 *   LCM(a, b) = a * b / GCD(a, b)
 *
 * 【学べること】
 *   - ユークリッドの互除法（再帰/ループ両方）
 *   - 再帰の実践的な使い方
 */
public class P17_GCD {

    public static void main(String[] args) {
        System.out.println("GCD(12, 8) = " + gcd(12, 8));     // → 4
        System.out.println("GCD(24, 36) = " + gcd(24, 36));   // → 12
        System.out.println("GCD(7, 5) = " + gcd(7, 5));       // → 1

        // LCM
        // System.out.println("LCM(4, 6) = " + lcm(4, 6));    // → 12
    }

    static int gcd(int a, int b) {
        // ここにコードを書こう
        return 0;

        // ========== 解答例1: 再帰 ==========
        // if (b == 0) return a;
        // return gcd(b, a % b);

        // ========== 解答例2: ループ ==========
        // while (b != 0) {
        //     int temp = b;
        //     b = a % b;
        //     a = temp;
        // }
        // return a;
    }

    // static int lcm(int a, int b) {
    //     return a / gcd(a, b) * b;  // オーバーフロー防止で先に割る
    // }
}
