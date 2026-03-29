package problems_advanced;

import java.util.PriorityQueue;
import java.util.Collections;

/**
 * ===== 上級09: 優先度付きキュー (Priority Queue / Heap) =====
 * 出典: LeetCode #215 - 配列のK番目に大きい要素
 *
 * 【問題】
 *   整数の配列と整数 k が与えられる。
 *   k番目に大きい要素を求めよ。
 *
 * 【例】
 *   nums = {3, 2, 1, 5, 6, 4}, k = 2 → 5（2番目に大きい）
 *   nums = {3, 2, 3, 1, 2, 4, 5, 5, 6}, k = 4 → 4
 *
 * 【PriorityQueue（優先度付きキュー）とは？】
 *   取り出すとき常に最小値（または最大値）が出てくるキュー。
 *   内部的にはヒープ（二分木）で実装されている。
 *
 *   Java の PriorityQueue はデフォルトで最小値が先に出る（最小ヒープ）。
 *   最大ヒープにするには: new PriorityQueue<>(Collections.reverseOrder())
 *
 *   操作:
 *     add(値)  / offer(値)  → 追加 O(log n)
 *     poll()               → 最小値を取り出す O(log n)
 *     peek()               → 最小値を見る O(1)
 *
 * 【アプローチ: サイズkの最小ヒープ】
 *   1. 最小ヒープにサイズkを維持しながら要素を入れる
 *   2. サイズがkを超えたらpoll()（最小値を捨てる）
 *   3. 最後にheapの先頭 = k番目に大きい要素
 *
 * 【学べること】
 *   - PriorityQueue の使い方
 *   - ヒープの概念
 *   - Top-K 問題のパターン
 */
public class P27_PriorityQueue {

    public static void main(String[] args) {

        // ---------- PriorityQueueの基本 ----------
        // PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        // minHeap.add(5);
        // minHeap.add(1);
        // minHeap.add(3);
        // System.out.println("--- 最小ヒープ ---");
        // while (!minHeap.isEmpty()) {
        //     System.out.println("  " + minHeap.poll());  // 1, 3, 5 の順
        // }

        // PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        // maxHeap.add(5);
        // maxHeap.add(1);
        // maxHeap.add(3);
        // System.out.println("--- 最大ヒープ ---");
        // while (!maxHeap.isEmpty()) {
        //     System.out.println("  " + maxHeap.poll());  // 5, 3, 1 の順
        // }

        // ---------- K番目に大きい要素 ----------
        int[] nums = {3, 2, 1, 5, 6, 4};
        System.out.println("2番目に大きい: " + findKthLargest(nums, 2)); // → 5
    }

    static int findKthLargest(int[] nums, int k) {
        // ここにコードを書こう
        return 0;

        // ========== 解答例 ==========
        // PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        // for (int num : nums) {
        //     minHeap.add(num);
        //     if (minHeap.size() > k) {
        //         minHeap.poll();   // サイズがkを超えたら最小値を捨てる
        //     }
        //     // ヒープにはk個の大きい値だけが残る
        // }
        // return minHeap.peek();    // 先頭 = k番目に大きい値
    }
}
