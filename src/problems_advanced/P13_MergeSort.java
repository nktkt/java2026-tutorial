package problems_advanced;

/**
 * ===== 上級03: マージソート (Merge Sort) =====
 * 出典: LeetCode #912 / 計算量 O(n log n) のソートアルゴリズム
 *
 * 【問題】
 *   配列をマージソートで昇順にソートせよ。
 *
 * 【マージソートとは？】
 *   「分割統治法」に基づくソート。
 *   ① 分割: 配列を半分に分ける
 *   ② 再帰: それぞれをソートする（自分自身を呼ぶ）
 *   ③ 統合: ソート済みの2つの配列をマージ（合体）する
 *
 *   例: [5, 2, 8, 1]
 *       → [5, 2] と [8, 1] に分割
 *       → [5] [2] と [8] [1] にさらに分割
 *       → [2, 5] と [1, 8] にソートしてマージ
 *       → [1, 2, 5, 8] に最終マージ
 *
 * 【再帰（recursion）とは？】
 *   メソッドが自分自身を呼び出すこと。
 *   必ず「ベースケース（終了条件）」が必要（ないと無限ループ）。
 *
 * 【なぜ O(n log n)？】
 *   - 分割: log n 回（毎回半分にする）
 *   - 各段階でのマージ: O(n)
 *   - 合計: O(n log n)
 *
 * 【学べること】
 *   - 再帰の考え方
 *   - 分割統治法
 *   - O(n log n) ソートの実装
 */
public class P13_MergeSort {

    public static void main(String[] args) {
        int[] nums = {38, 27, 43, 3, 9, 82, 10};

        System.out.print("ソート前: ");
        printArray(nums);

        // ここにコードを書こう
        // mergeSort(nums, 0, nums.length - 1);

        System.out.print("ソート後: ");
        printArray(nums);
    }

    static void printArray(int[] arr) {
        for (int n : arr) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    // ========== 解答例 ==========

    // --- メインの再帰メソッド ---
    // static void mergeSort(int[] arr, int left, int right) {
    //     if (left >= right) {
    //         return;   // ベースケース: 要素が1つ以下ならソート不要
    //     }
    //
    //     int mid = left + (right - left) / 2;
    //     // 中央で分割
    //
    //     mergeSort(arr, left, mid);       // 左半分をソート（再帰）
    //     mergeSort(arr, mid + 1, right);  // 右半分をソート（再帰）
    //     merge(arr, left, mid, right);    // ソート済みの2つをマージ
    // }

    // --- 2つのソート済み部分をマージ ---
    // static void merge(int[] arr, int left, int mid, int right) {
    //     // 一時配列にコピー
    //     int[] temp = new int[right - left + 1];
    //     int i = left;       // 左半分のポインタ
    //     int j = mid + 1;    // 右半分のポインタ
    //     int k = 0;          // temp配列のポインタ
    //
    //     // 両方にまだ要素がある間
    //     while (i <= mid && j <= right) {
    //         if (arr[i] <= arr[j]) {
    //             temp[k++] = arr[i++];    // 左が小さい → 左を採用
    //         } else {
    //             temp[k++] = arr[j++];    // 右が小さい → 右を採用
    //         }
    //     }
    //
    //     // 左半分の残りをコピー
    //     while (i <= mid) {
    //         temp[k++] = arr[i++];
    //     }
    //
    //     // 右半分の残りをコピー
    //     while (j <= right) {
    //         temp[k++] = arr[j++];
    //     }
    //
    //     // temp の結果を元の配列に書き戻す
    //     for (int x = 0; x < temp.length; x++) {
    //         arr[left + x] = temp[x];
    //     }
    // }
}
