package problems_intermediate;

import java.util.Arrays;

/**
 * ===== 中級11: Two Pointers (3Sum) =====
 * 出典: LeetCode #15 風（簡易版: ペアの数を数える）
 *
 * 【問題】
 *   ソート済み配列と target が与えられる。
 *   合計が target になるペアの数を求めよ（同じ要素を2回使わない）。
 *
 * 【例】
 *   nums = {1, 2, 3, 4, 5, 6}, target = 7
 *   → 3ペア: (1,6), (2,5), (3,4)
 *
 * 【Two Pointers法】
 *   左右両端からポインタを動かして探索する O(n)。
 *
 *   left=0, right=末尾 から:
 *   - nums[left] + nums[right] == target → ペア発見！ 両方動かす
 *   - nums[left] + nums[right] < target  → 合計が小さい → leftを右に
 *   - nums[left] + nums[right] > target  → 合計が大きい → rightを左に
 *
 * 【学べること】
 *   - Two Pointers法の本格的な使い方
 *   - ソート済み配列に対する効率的な探索
 */
public class P23_TwoPointers {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6};
        System.out.println("ペア数: " + countPairs(nums, 7)); // → 3
    }

    static int countPairs(int[] nums, int target) {
        // ここにコードを書こう
        return 0;

        // ========== 解答例 ==========
        // int left = 0;
        // int right = nums.length - 1;
        // int count = 0;
        //
        // while (left < right) {
        //     int sum = nums[left] + nums[right];
        //     if (sum == target) {
        //         System.out.println("  ペア: (" + nums[left] + ", " + nums[right] + ")");
        //         count++;
        //         left++;
        //         right--;
        //     } else if (sum < target) {
        //         left++;      // 合計を増やすために左を右へ
        //     } else {
        //         right--;     // 合計を減らすために右を左へ
        //     }
        // }
        // return count;
    }
}
