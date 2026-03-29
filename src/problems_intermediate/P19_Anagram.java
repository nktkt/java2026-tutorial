package problems_intermediate;

import java.util.Arrays;

/**
 * ===== 中級07: アナグラム判定 (Valid Anagram) =====
 * 出典: LeetCode #242
 *
 * 【問題】
 *   2つの文字列がアナグラムかどうか判定せよ。
 *   アナグラム = 文字の並べ替えで同じになる（同じ文字を同じ回数含む）
 *
 * 【例】
 *   "listen", "silent" → true
 *   "hello", "world"   → false
 *   "anagram", "nagaram" → true
 *
 * 【アプローチ1: ソートして比較】
 *   両方を文字でソートして一致すればアナグラム。O(n log n)
 *
 * 【アプローチ2: 文字の出現回数を比較】
 *   各文字の出現回数が全て同じならアナグラム。O(n)
 *   アルファベット26文字分のint配列で数える。
 *
 * 【学べること】
 *   - 文字列→char配列の変換と操作
 *   - 出現回数のカウントパターン（配列/HashMap）
 */
public class P19_Anagram {

    public static void main(String[] args) {
        System.out.println(isAnagram("listen", "silent"));   // → true
        System.out.println(isAnagram("hello", "world"));     // → false
        System.out.println(isAnagram("anagram", "nagaram")); // → true
    }

    static boolean isAnagram(String s, String t) {
        // ここにコードを書こう
        return false;

        // ========== 解答例1: ソート ==========
        // if (s.length() != t.length()) return false;
        // char[] sArr = s.toCharArray();
        // char[] tArr = t.toCharArray();
        // Arrays.sort(sArr);
        // Arrays.sort(tArr);
        // return Arrays.equals(sArr, tArr);

        // ========== 解答例2: カウント配列 ==========
        // if (s.length() != t.length()) return false;
        // int[] count = new int[26];   // a〜z の26文字分
        // for (int i = 0; i < s.length(); i++) {
        //     count[s.charAt(i) - 'a']++;   // sの文字を+1
        //     count[t.charAt(i) - 'a']--;   // tの文字を-1
        //     // 'a' - 'a' = 0, 'b' - 'a' = 1, ... 'z' - 'a' = 25
        // }
        // for (int c : count) {
        //     if (c != 0) return false;   // 1つでも0でなければ不一致
        // }
        // return true;
    }
}
