package problems_beginner;

import java.util.Scanner;

/**
 * ===== 初級03: 文字列の反転 (Reverse String) =====
 * 出典: LeetCode #344 風
 *
 * 【問題】
 *   文字列が与えられる。その文字列を逆順にして表示せよ。
 *
 * 【入力例】
 *   hello
 *
 * 【出力例】
 *   olleh
 *
 * 【もう1つの例】
 *   入力: Java  → 出力: avaJ
 *   入力: abcde → 出力: edcba
 *
 * 【ヒント】
 *   方法1: 文字列の末尾から先頭に向かってfor文で1文字ずつ取り出す
 *          - str.charAt(i) でi番目の文字を取得
 *          - str.length() で文字数を取得
 *
 *   方法2: StringBuilder クラスの reverse() メソッドを使う（簡単だが学習にならない）
 *
 * 【学べること】
 *   - 文字列の操作（charAt, length）
 *   - 逆順ループ（i-- でカウントダウン）
 */
public class P03_ReverseString {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("文字列を入力: ");
        String str = sc.nextLine();
        // ↑ nextLine(): 1行分の文字列を読み取る（スペース含む）
        //   nextInt() は整数、next() はスペースまでの文字列

        // ここにコードを書こう
        // 文字列を逆順にして表示する


        // ========== 解答例 ==========
        // String reversed = "";
        // for (int i = str.length() - 1; i >= 0; i--) {
        //     // str.length()-1 = 最後の文字のインデックス
        //     // i-- で後ろから前に向かう
        //     reversed += str.charAt(i);
        //     // ↑ charAt(i): i番目の文字を取得
        //     //   文字列 + 文字 → 文字列に結合される
        // }
        // System.out.println(reversed);

        sc.close();
    }
}
