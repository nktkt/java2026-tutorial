package problems_advanced;

import java.util.ArrayList;
import java.util.List;

/**
 * ===== 上級08: バックトラッキング (Permutations) =====
 * 出典: LeetCode #46
 *
 * 【問題】
 *   整数の配列が与えられる。すべての順列（並び替え）を列挙せよ。
 *
 * 【例】
 *   nums = {1, 2, 3}
 *   → [[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]]
 *
 * 【バックトラッキングとは？】
 *   「選択 → 再帰 → 取り消し」を繰り返して全パターンを探索する手法。
 *   1つの選択肢を試してダメなら戻って別の選択肢を試す。
 *
 *   イメージ: 迷路で行き止まりになったら引き返して別の道を試す
 *
 * 【パターン】
 *   void backtrack(現在の状態) {
 *       if (終了条件) { 結果を保存; return; }
 *       for (各選択肢) {
 *           選択する;
 *           backtrack(次の状態);   // 再帰
 *           選択を取り消す;         // バックトラック
 *       }
 *   }
 *
 * 【学べること】
 *   - バックトラッキングのパターン
 *   - 順列の列挙
 *   - 全探索の系統的な方法
 */
public class P26_Backtracking {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        // List<List<Integer>> result = permute(nums);
        // for (List<Integer> perm : result) {
        //     System.out.println(perm);
        // }
    }

    // ========== 解答例 ==========
    // static List<List<Integer>> permute(int[] nums) {
    //     List<List<Integer>> result = new ArrayList<>();
    //     boolean[] used = new boolean[nums.length];
    //     // used[i]: nums[i] が現在の順列で使用中かどうか
    //     backtrack(nums, new ArrayList<>(), used, result);
    //     return result;
    // }

    // static void backtrack(int[] nums, List<Integer> current,
    //                        boolean[] used, List<List<Integer>> result) {
    //     // 終了条件: 全要素を使った
    //     if (current.size() == nums.length) {
    //         result.add(new ArrayList<>(current));
    //         // ↑ new ArrayList<>(current) でコピーを保存
    //         //   currentは変更されるので、参照そのものを入れるとダメ
    //         return;
    //     }
    //
    //     for (int i = 0; i < nums.length; i++) {
    //         if (used[i]) continue;           // 使用中ならスキップ
    //
    //         current.add(nums[i]);            // 選択
    //         used[i] = true;
    //
    //         backtrack(nums, current, used, result);  // 再帰
    //
    //         current.remove(current.size() - 1);  // 取り消し（バックトラック）
    //         used[i] = false;
    //     }
    // }
}
