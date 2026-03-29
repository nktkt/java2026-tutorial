package problems_beginner;

import java.util.Scanner;

/**
 * ===== 初級05: 文字の出現回数 (Character Frequency) =====
 * 出典: Codeforces A問題風
 *
 * 【問題】
 *   文字列と1文字が与えられる。その文字が文字列中に何回出現するか数えよ。
 *
 * 【入力例】
 *   banana
 *   a
 *
 * 【出力例】
 *   3
 *   (解説: banana の中に 'a' は3つ)
 *
 * 【もう1つの例】
 *   入力: programming, g → 出力: 2
 *   入力: hello, l       → 出力: 2
 *
 * 【ヒント】
 *   - カウント用の変数を0で初期化
 *   - for文で文字列を1文字ずつ見て、一致したらカウントを増やす
 *   - char の比較は == でOK（Stringと違って基本型だから）
 *
 * 【学べること】
 *   - カウントパターン（変数を用意してループで数える）
 *   - charとStringの違い
 *   - Scanner の next().charAt(0) で1文字を読む方法
 */
public class P05_CountFrequency {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("文字列を入力: ");
        String str = sc.nextLine();

        System.out.print("数える文字を入力: ");
        char target = sc.nextLine().charAt(0);
        // ↑ nextLine()で文字列として読み、charAt(0)で最初の1文字を取得

        // ここにコードを書こう


        // ========== 解答例 ==========
        // int count = 0;
        // for (int i = 0; i < str.length(); i++) {
        //     if (str.charAt(i) == target) {   // char同士の比較は == でOK
        //         count++;
        //     }
        // }
        // System.out.println(count);

        sc.close();
    }
}
