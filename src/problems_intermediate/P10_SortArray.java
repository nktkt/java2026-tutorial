package problems_intermediate;

/**
 * ===== 中級05: 配列のソート (Sort Array) =====
 * 出典: LeetCode #912 風 / Codeforces ソート問題
 *
 * 【問題】
 *   整数の配列を昇順（小さい順）にソートせよ。
 *   ※ Arrays.sort() を使わずに自力で実装する
 *
 * 【例】
 *   入力: {5, 2, 8, 1, 9, 3}
 *   出力: {1, 2, 3, 5, 8, 9}
 *
 * 【アプローチ1: バブルソート O(n²)】
 *   隣同士を比較して、大きい方を右に移動する。
 *   これを繰り返すと、大きい値が泡のように右に浮かんでいく。
 *   - 理解しやすいが遅い
 *
 * 【アプローチ2: 選択ソート O(n²)】
 *   未ソート部分から最小値を見つけて、先頭に置く。
 *   - バブルソートより交換回数が少ない
 *
 * 【実務で使うソート】
 *   Arrays.sort(配列) → Java標準のソート（高速）
 *   自力実装は学習用。競プロでは Arrays.sort() を使ってOK。
 *
 * 【学べること】
 *   - ソートアルゴリズムの基本
 *   - 配列の要素の交換（swap）パターン
 *   - O(n²) の計算量を体感する
 */
public class P10_SortArray {

    public static void main(String[] args) {
        int[] nums = {5, 2, 8, 1, 9, 3};

        // ここにコードを書こう（バブルソートまたは選択ソート）


        // ソート結果を表示
        // for (int n : nums) {
        //     System.out.print(n + " ");
        // }
        // System.out.println();
    }

    // ========== 解答例1: バブルソート ==========
    // static void bubbleSort(int[] nums) {
    //     int n = nums.length;
    //     for (int i = 0; i < n - 1; i++) {
    //         // n-1 回のパス（毎回1つずつ確定する）
    //         for (int j = 0; j < n - 1 - i; j++) {
    //             // 隣同士を比較
    //             if (nums[j] > nums[j + 1]) {
    //                 // 左が大きければ交換（swap）
    //                 int temp = nums[j];
    //                 nums[j] = nums[j + 1];
    //                 nums[j + 1] = temp;
    //             }
    //         }
    //         // 1パス終了: 最大値が右端に確定
    //     }
    // }

    // ========== 解答例2: 選択ソート ==========
    // static void selectionSort(int[] nums) {
    //     int n = nums.length;
    //     for (int i = 0; i < n - 1; i++) {
    //         // 未ソート部分(i〜末尾)から最小値を探す
    //         int minIndex = i;
    //         for (int j = i + 1; j < n; j++) {
    //             if (nums[j] < nums[minIndex]) {
    //                 minIndex = j;
    //             }
    //         }
    //         // 最小値を i 番目と交換
    //         int temp = nums[i];
    //         nums[i] = nums[minIndex];
    //         nums[minIndex] = temp;
    //     }
    // }
}
