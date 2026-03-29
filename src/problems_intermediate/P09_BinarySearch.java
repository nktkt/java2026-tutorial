package problems_intermediate;

/**
 * ===== 中級04: 二分探索 (Binary Search) =====
 * 出典: LeetCode #704
 *
 * 【問題】
 *   ソート済みの整数配列 nums と整数 target が与えられる。
 *   target が配列の中にあればそのインデックスを、なければ -1 を返せ。
 *
 * 【例】
 *   nums = {-1, 0, 3, 5, 9, 12}, target = 9
 *   → 出力: 4  (nums[4] = 9)
 *
 *   nums = {-1, 0, 3, 5, 9, 12}, target = 2
 *   → 出力: -1  (2は配列にない)
 *
 * 【アプローチ: 二分探索】
 *   ソート済み配列を半分に分けながら探索する → O(log n) で高速
 *
 *   1. 探索範囲: left=0, right=配列の最後
 *   2. 中央 mid = (left + right) / 2 を調べる
 *   3. nums[mid] == target → 見つかった！
 *      nums[mid] < target  → target は右半分にある → left = mid + 1
 *      nums[mid] > target  → target は左半分にある → right = mid - 1
 *   4. left > right になったら見つからなかった
 *
 * 【なぜ速いか？】
 *   線形探索: 最大 n 回の比較（1つずつ見る）
 *   二分探索: 最大 log₂(n) 回の比較（毎回半分に絞る）
 *   n=1,000,000 のとき: 線形→100万回、二分→約20回！
 *
 * 【学べること】
 *   - 二分探索の実装パターン
 *   - O(n) vs O(log n) の計算量の違い
 *   - ソート済み配列に対する効率的な探索
 */
public class P09_BinarySearch {

    public static void main(String[] args) {
        int[] nums = {-1, 0, 3, 5, 9, 12};

        System.out.println("9 のインデックス: " + binarySearch(nums, 9));   // → 4
        System.out.println("2 のインデックス: " + binarySearch(nums, 2));   // → -1
        System.out.println("12のインデックス: " + binarySearch(nums, 12));  // → 5
    }

    static int binarySearch(int[] nums, int target) {

        // ここにコードを書こう
        return -1; // 仮の戻り値


        // ========== 解答例 ==========
        // int left = 0;
        // int right = nums.length - 1;
        //
        // while (left <= right) {
        //     int mid = left + (right - left) / 2;
        //     // ↑ (left + right) / 2 でもよいが、
        //     //   left + right が大きいとオーバーフローする可能性がある
        //     //   left + (right - left) / 2 の方が安全
        //
        //     if (nums[mid] == target) {
        //         return mid;              // 見つかった！
        //     } else if (nums[mid] < target) {
        //         left = mid + 1;          // 右半分を探す
        //         // 例: [1,3,5,7,9] で target=7, mid=5 → 左半分は不要
        //     } else {
        //         right = mid - 1;         // 左半分を探す
        //     }
        // }
        // return -1;  // 見つからなかった
    }
}
