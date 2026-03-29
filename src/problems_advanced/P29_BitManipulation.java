package problems_advanced;

/**
 * ===== 上級11: ビット演算 (Bit Manipulation) =====
 * 出典: LeetCode #136, #191 等
 *
 * 【問題1: Single Number】
 *   配列の中で1つだけ1回しか出現しない数を見つけよ。他は全て2回出現する。
 *
 * 【問題2: Number of 1 Bits】
 *   整数の2進数表現で1のビットの数を数えよ。
 *
 * 【ビット演算子】
 *   &  (AND):  両方1なら1        1010 & 1100 = 1000
 *   |  (OR):   どちらか1なら1     1010 | 1100 = 1110
 *   ^  (XOR):  異なれば1          1010 ^ 1100 = 0110
 *   ~  (NOT):  ビット反転         ~1010 = 0101
 *   << (左シフト): ビットを左に     1 << 3 = 1000 (=8)
 *   >> (右シフト): ビットを右に     1000 >> 2 = 10 (=2)
 *
 * 【XORの特性】
 *   a ^ a = 0    （同じ値のXORは0）
 *   a ^ 0 = a    （0とのXORはそのまま）
 *   → 全要素をXORすると、2回出現する値は消え、1回だけの値が残る！
 *
 * 【学べること】
 *   - ビット演算の基本
 *   - XOR の特性を利用した問題解決
 *   - 低レベルな操作の理解
 */
public class P29_BitManipulation {

    public static void main(String[] args) {
        // ---------- ビット演算の基本 ----------
        // System.out.println("--- ビット演算 ---");
        // System.out.println("5 & 3 = " + (5 & 3));    // 101 & 011 = 001 → 1
        // System.out.println("5 | 3 = " + (5 | 3));    // 101 | 011 = 111 → 7
        // System.out.println("5 ^ 3 = " + (5 ^ 3));    // 101 ^ 011 = 110 → 6
        // System.out.println("1 << 3 = " + (1 << 3));  // 1000 → 8
        // System.out.println("8 >> 2 = " + (8 >> 2));  // 10 → 2

        // ---------- 問題1: Single Number ----------
        int[] nums = {4, 1, 2, 1, 2};
        System.out.println("Single Number: " + singleNumber(nums)); // → 4

        // ---------- 問題2: 1のビット数 ----------
        System.out.println("11の1ビット数: " + countBits(11)); // 1011 → 3
    }

    static int singleNumber(int[] nums) {
        // ここにコードを書こう
        return 0;

        // ========== 解答例 ==========
        // int result = 0;
        // for (int num : nums) {
        //     result ^= num;    // 全要素をXOR
        //     // 4^1^2^1^2 = 4^(1^1)^(2^2) = 4^0^0 = 4
        // }
        // return result;
    }

    static int countBits(int n) {
        // ここにコードを書こう
        return 0;

        // ========== 解答例 ==========
        // int count = 0;
        // while (n != 0) {
        //     count += n & 1;   // 最下位ビットが1か確認
        //     n >>= 1;          // 右に1シフト（最下位ビットを捨てる）
        // }
        // return count;
    }
}
