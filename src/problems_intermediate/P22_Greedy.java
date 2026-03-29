package problems_intermediate;

import java.util.Arrays;

/**
 * ===== 中級10: 貪欲法 (Greedy - Activity Selection) =====
 * 出典: LeetCode #435 風 / 区間スケジューリング問題
 *
 * 【問題】
 *   N個の活動があり、それぞれ開始時刻と終了時刻を持つ。
 *   重ならないように最大何個の活動に参加できるか？
 *
 * 【例】
 *   活動: (1,3), (2,5), (4,7), (6,8), (5,9)
 *   → 最大 3個: (1,3), (4,7), (6,8) ※ (1,3)と(4,7)は重ならない
 *
 * 【貪欲法(Greedy)とは？】
 *   「その時点で最も良い選択」を繰り返す手法。
 *   必ず最適解が得られるとは限らないが、この問題では最適解が得られる。
 *
 * 【アルゴリズム】
 *   1. 終了時刻でソートする（早く終わるものを優先）
 *   2. 最初の活動を選ぶ
 *   3. 次の活動の開始時刻 >= 今の活動の終了時刻 なら選ぶ
 *
 *   なぜ「終了が早い」を優先？
 *   → 早く終わる活動を選べば、残りの時間が多く、より多くの活動を入れられる
 *
 * 【学べること】
 *   - 貪欲法の考え方
 *   - 2次元配列のカスタムソート
 *   - 区間スケジューリング問題（頻出！）
 */
public class P22_Greedy {

    public static void main(String[] args) {
        // {開始, 終了}
        int[][] activities = {{1, 3}, {2, 5}, {4, 7}, {6, 8}, {5, 9}};
        System.out.println("最大参加数: " + maxActivities(activities)); // → 3
    }

    static int maxActivities(int[][] activities) {
        // ここにコードを書こう
        return 0;

        // ========== 解答例 ==========
        // // 終了時刻でソート（ラムダ式を使用）
        // Arrays.sort(activities, (a, b) -> a[1] - b[1]);
        // // ソート後: (1,3), (2,5), (4,7), (6,8), (5,9)
        //
        // int count = 1;                     // 最初の活動は必ず選ぶ
        // int lastEnd = activities[0][1];     // 最後に選んだ活動の終了時刻
        //
        // for (int i = 1; i < activities.length; i++) {
        //     if (activities[i][0] >= lastEnd) {
        //         // この活動の開始が、前の活動の終了以降 → 重ならない
        //         count++;
        //         lastEnd = activities[i][1];
        //     }
        // }
        // return count;
    }
}
