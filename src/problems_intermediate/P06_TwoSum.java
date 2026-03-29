package problems_intermediate;

import java.util.HashMap;

/**
 * ===== 中級01: Two Sum =====
 * 出典: LeetCode #1（最も有名な問題）
 *
 * 【問題】
 *   整数の配列 nums と整数 target が与えられる。
 *   配列の中から合計が target になる2つの数のインデックスを見つけよ。
 *   答えは必ず1つ存在するものとする。
 *
 * 【例】
 *   nums = {2, 7, 11, 15}, target = 9
 *   → 出力: [0, 1]  (nums[0] + nums[1] = 2 + 7 = 9)
 *
 *   nums = {3, 2, 4}, target = 6
 *   → 出力: [1, 2]  (nums[1] + nums[2] = 2 + 4 = 6)
 *
 * 【アプローチ】
 *
 *   方法1: 総当たり（Brute Force）O(n²)
 *     - 二重ループで全ペアを試す
 *     - 簡単だが遅い
 *
 *   方法2: HashMap を使う O(n)
 *     - 配列を1回走査するだけで解ける
 *     - 「target - 現在の値」がすでに出現していたかをHashMapで調べる
 *     - HashMap: キーと値のペアを保存し、キーで高速に検索できるデータ構造
 *
 * 【HashMapの基本】
 *   HashMap<キーの型, 値の型> map = new HashMap<>();
 *   map.put(キー, 値)        → データを保存
 *   map.get(キー)            → 値を取得
 *   map.containsKey(キー)    → キーが存在するか確認（true/false）
 *
 * 【学べること】
 *   - HashMap の基本操作
 *   - 計算量の改善（O(n²) → O(n)）
 *   - 「補数」を探すテクニック
 */
public class P06_TwoSum {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        // ここにコードを書こう
        // 合計が target になる2つのインデックスを見つけて表示する


        // ========== 解答例1: 総当たり O(n²) ==========
        // for (int i = 0; i < nums.length; i++) {
        //     for (int j = i + 1; j < nums.length; j++) {
        //         // j = i+1 から始める → 同じ要素を2回使わない＆重複を避ける
        //         if (nums[i] + nums[j] == target) {
        //             System.out.println("[" + i + ", " + j + "]");
        //             return;    // 見つかったら終了
        //         }
        //     }
        // }

        // ========== 解答例2: HashMap O(n) ==========
        // HashMap<Integer, Integer> map = new HashMap<>();
        // // キー: 配列の値、値: そのインデックス
        //
        // for (int i = 0; i < nums.length; i++) {
        //     int complement = target - nums[i];
        //     // complement = 補数（target を作るために必要なもう一方の数）
        //     // 例: target=9, nums[i]=2 → complement=7
        //
        //     if (map.containsKey(complement)) {
        //         // 補数がすでにmapにある → ペアが見つかった！
        //         System.out.println("[" + map.get(complement) + ", " + i + "]");
        //         return;
        //     }
        //     map.put(nums[i], i);
        //     // 現在の値とインデックスをmapに保存（次のループで使う）
        // }
    }
}
