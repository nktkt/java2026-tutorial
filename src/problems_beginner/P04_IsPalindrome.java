package problems_beginner;

import java.util.Scanner;

/**
 * ===== 初級04: 回文判定 (Palindrome) =====
 * 出典: LeetCode #125 風
 *
 * 【問題】
 *   文字列が与えられる。回文（前から読んでも後ろから読んでも同じ）かどうか判定せよ。
 *
 * 【入力・出力例】
 *   入力: racecar → 出力: true（回文）
 *   入力: hello   → 出力: false（回文ではない）
 *   入力: madam   → 出力: true
 *   入力: aba     → 出力: true
 *
 * 【ヒント】
 *   方法1: 反転した文字列を作って、元の文字列と比較する
 *          - 文字列の比較は == ではなく .equals() を使う！
 *            "abc" == "abc" → 動かないことがある
 *            "abc".equals("abc") → 確実にtrue
 *
 *   方法2: 先頭と末尾から1文字ずつ比較する（Two Pointer法の入門）
 *          - left = 0, right = length-1 から始めて、中央に向かって進む
 *          - 1つでも不一致があれば回文ではない
 *
 * 【学べること】
 *   - 文字列の比較（.equals()）
 *   - Two Pointer法の基本的な考え方
 */
public class P04_IsPalindrome {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("文字列を入力: ");
        String str = sc.nextLine();

        // ここにコードを書こう


        // ========== 解答例1: 反転して比較 ==========
        // String reversed = "";
        // for (int i = str.length() - 1; i >= 0; i--) {
        //     reversed += str.charAt(i);
        // }
        // System.out.println(str.equals(reversed));

        // ========== 解答例2: Two Pointer法 ==========
        // boolean isPalindrome = true;
        // int left = 0;                    // 先頭のインデックス
        // int right = str.length() - 1;    // 末尾のインデックス
        //
        // while (left < right) {
        //     if (str.charAt(left) != str.charAt(right)) {
        //         // 1文字でも違えば回文ではない
        //         isPalindrome = false;
        //         break;
        //     }
        //     left++;      // 先頭側を1つ右へ
        //     right--;     // 末尾側を1つ左へ
        // }
        // System.out.println(isPalindrome);

        sc.close();
    }
}
