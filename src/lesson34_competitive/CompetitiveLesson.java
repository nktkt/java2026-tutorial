package lesson34_competitive;

import java.io.*;
import java.util.*;

/**
 * ===== レッスン34: 競技プログラミング実践テクニック =====
 *
 * 【目標】Codeforces/AtCoder/LeetCode で戦うための実践テクニックを学ぶ
 *
 * 【1. 高速入出力】
 *   Scanner は遅い。BufferedReader + StringTokenizer が高速。
 *
 * 【2. よく使うテンプレート】
 *   - 配列入力パターン
 *   - MOD演算（10^9+7）
 *   - long のオーバーフロー対策
 *
 * 【3. 便利メソッド】
 *   Arrays.sort()      → プリミティブ配列のソート
 *   Arrays.fill()      → 配列の一括初期化
 *   Collections.sort()  → Listのソート
 *   Math.max/min/abs   → 数学関数
 *   Integer.MAX_VALUE  → int最大値（約21億）
 *   Long.MAX_VALUE     → long最大値
 *
 * 【4. 計算量の目安】
 *   N ≤ 10^6  → O(N) か O(N log N)
 *   N ≤ 10^4  → O(N²) もギリギリOK
 *   N ≤ 500   → O(N³) もOK
 *   N ≤ 20    → O(2^N) も間に合う
 */
public class CompetitiveLesson {

    // ========== 高速入力テンプレート ==========
    // static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // static StringBuilder sb = new StringBuilder();  // 出力用
    //
    // static int readInt() throws IOException {
    //     return Integer.parseInt(br.readLine().trim());
    // }
    //
    // static int[] readIntArray() throws IOException {
    //     StringTokenizer st = new StringTokenizer(br.readLine());
    //     int n = st.countTokens();
    //     int[] arr = new int[n];
    //     for (int i = 0; i < n; i++) {
    //         arr[i] = Integer.parseInt(st.nextToken());
    //     }
    //     return arr;
    // }

    public static void main(String[] args) {

        // ---------- MOD演算 ----------
        // final long MOD = 1_000_000_007;   // 10^9 + 7（素数）
        // // 大きな数の掛け算では途中でMODを取る
        // long a = 1_000_000;
        // long b = 1_000_000;
        // long result = (a * b) % MOD;      // MODを取らないとオーバーフロー
        // System.out.println("MOD演算: " + result);

        // ---------- long のオーバーフロー注意 ----------
        // int big = Integer.MAX_VALUE;       // 約21億
        // // int overflow = big + 1;          // → -2147483648（オーバーフロー！）
        // long safe = (long) big + 1;         // long にキャストすれば安全
        // System.out.println("int最大: " + big);
        // System.out.println("long化: " + safe);

        // ---------- 配列の便利操作 ----------
        // int[] arr = {5, 3, 1, 4, 2};
        // Arrays.sort(arr);                  // 昇順ソート O(n log n)
        // System.out.println("ソート: " + Arrays.toString(arr));
        //
        // int idx = Arrays.binarySearch(arr, 3);  // ソート済みで二分探索
        // System.out.println("3の位置: " + idx);
        //
        // int[] filled = new int[5];
        // Arrays.fill(filled, -1);           // 全要素を-1に
        // System.out.println("fill: " + Arrays.toString(filled));

        // ---------- 出力の高速化 ----------
        // // println を何万回も呼ぶと遅い
        // // StringBuilder に貯めて最後に一括出力が速い
        // StringBuilder sb = new StringBuilder();
        // for (int i = 0; i < 10; i++) {
        //     sb.append(i).append("\n");
        // }
        // System.out.print(sb);

        // ===== 練習問題 =====
        // 1. BufferedReader で N 個の整数を読み取り、合計を出力するプログラムを書こう
        // 2. MOD演算で 2^100 % (10^9+7) を求めてみよう（繰り返し二乗法）
    }
}
