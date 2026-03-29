package problems_beginner;

import java.util.Scanner;

/**
 * ===== 初級01: 各桁の和 (Sum of Digits) =====
 * 出典: Codeforces風 (A問題レベル)
 *
 * 【問題】
 *   正の整数 N が与えられる。N の各桁の数字の合計を求めよ。
 *
 * 【入力例】
 *   1234
 *
 * 【出力例】
 *   10
 *   (解説: 1+2+3+4 = 10)
 *
 * 【もう1つの例】
 *   入力: 9999 → 出力: 36  (9+9+9+9)
 *   入力: 100  → 出力: 1   (1+0+0)
 *
 * 【ヒント】
 *   - N % 10 で一の位の数字が取れる
 *   - N / 10 で一の位を取り除ける
 *   - while文で N が 0 になるまで繰り返す
 *
 * 【学べること】
 *   - while文の使い方
 *   - % (余り) と / (割り算) を使った桁の分解
 *   - Scannerによる入力の受け取り
 */
public class P01_SumOfDigits {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // ↑ Scanner: キーボードからの入力を読み取るクラス
        //   System.in = 標準入力（キーボード）

        System.out.print("整数を入力: ");
        int n = sc.nextInt();
        // ↑ nextInt(): 入力された整数を読み取る

        // ここにコードを書こう
        // 各桁の数字を取り出して合計する


        // ========== 解答例（自分で書いてから見よう） ==========
        // int sum = 0;
        // int temp = n;          // n を壊さないようにコピー
        // while (temp > 0) {
        //     sum += temp % 10;  // 一の位を足す（1234 % 10 = 4）
        //     temp /= 10;       // 一の位を取り除く（1234 / 10 = 123）
        // }
        // System.out.println(sum);

        sc.close();
    }
}
