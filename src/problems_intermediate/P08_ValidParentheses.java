package problems_intermediate;

import java.util.Scanner;
import java.util.Stack;

/**
 * ===== 中級03: 括弧の整合性 (Valid Parentheses) =====
 * 出典: LeetCode #20
 *
 * 【問題】
 *   文字列が '(', ')', '{', '}', '[', ']' のみで構成されている。
 *   括弧が正しく閉じられているか判定せよ。
 *
 * 【例】
 *   "()"        → true
 *   "()[]{}"    → true
 *   "(]"        → false  (開き括弧と閉じ括弧の種類が違う)
 *   "([)]"      → false  (交差している)
 *   "{[]}"      → true   (入れ子はOK)
 *   ""          → true   (空文字列はOK)
 *
 * 【アプローチ: スタック(Stack)を使う】
 *   スタック = 「後入れ先出し(LIFO)」のデータ構造
 *   - push(値): 上に積む
 *   - pop():    一番上を取り出す
 *   - peek():   一番上を見る（取り出さない）
 *   - isEmpty(): 空かどうか
 *
 *   アルゴリズム:
 *   1. 文字列を1文字ずつ見る
 *   2. 開き括弧 → スタックに積む
 *   3. 閉じ括弧 → スタックの一番上と対応しているか確認
 *      - 対応していればスタックからpop
 *      - 対応していなければ false
 *   4. 最後にスタックが空なら true（全部対応した）
 *
 * 【学べること】
 *   - Stack（スタック）の基本操作
 *   - 括弧のマッチングパターン（コンパイラの基本原理）
 */
public class P08_ValidParentheses {

    public static void main(String[] args) {
        // テストケース
        String[] tests = {"()", "()[]{}", "(]", "([)]", "{[]}", ""};

        for (String test : tests) {
            System.out.println("\"" + test + "\" → " + isValid(test));
        }
    }

    static boolean isValid(String s) {

        // ここにコードを書こう
        return false; // 仮の戻り値（実装したら消す）


        // ========== 解答例 ==========
        // Stack<Character> stack = new Stack<>();
        // // Character = char のラッパークラス（Stackにはオブジェクト型が必要）
        //
        // for (int i = 0; i < s.length(); i++) {
        //     char c = s.charAt(i);
        //
        //     if (c == '(' || c == '{' || c == '[') {
        //         // 開き括弧 → スタックに積む
        //         stack.push(c);
        //     } else {
        //         // 閉じ括弧 → スタックの上と対応チェック
        //         if (stack.isEmpty()) {
        //             return false;   // 対応する開き括弧がない
        //         }
        //         char top = stack.pop();  // スタックの一番上を取り出す
        //
        //         // 対応しているかチェック
        //         if (c == ')' && top != '(') return false;
        //         if (c == '}' && top != '{') return false;
        //         if (c == ']' && top != '[') return false;
        //     }
        // }
        // return stack.isEmpty();
        // // スタックが空 = すべての開き括弧に対応する閉じ括弧があった
    }
}
