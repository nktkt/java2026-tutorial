package lesson05_loops;

/**
 * ===== レッスン05: 繰り返し（ループ） =====
 *
 * 同じ処理を何度も繰り返す。
 *
 * for文:       回数が決まっているとき
 * while文:     条件がtrueの間繰り返す
 * do-while文:  最低1回は実行してから条件をチェック
 * break:       ループを途中で抜ける
 * continue:    今の回をスキップして次の回へ
 */
public class Loops {

    public static void main(String[] args) {

        // ========== for文 ==========
        System.out.println("--- for文 ---");
        // for (初期値; 条件; 更新) { ... }
        for (int i = 1; i <= 5; i++) {
            System.out.println("  " + i + "回目のループ");
        }

        // ========== 合計を求める ==========
        System.out.println("\n--- 1から10の合計 ---");
        int sum = 0;
        for (int i = 1; i <= 10; i++) {
            sum += i;  // sum = sum + i
        }
        System.out.println("合計: " + sum);  // → 55

        // ========== while文 ==========
        System.out.println("\n--- while文 ---");
        int count = 3;
        while (count > 0) {
            System.out.println("  カウントダウン: " + count);
            count--;
        }
        System.out.println("  発射！");

        // ========== do-while文 ==========
        System.out.println("\n--- do-while文 ---");
        int num = 1;
        do {
            System.out.println("  num = " + num);
            num *= 2;
        } while (num <= 16);
        // 条件を後でチェックするので、最低1回は実行される

        // ========== break（ループを抜ける） ==========
        System.out.println("\n--- break ---");
        for (int i = 1; i <= 10; i++) {
            if (i == 6) {
                System.out.println("  6が見つかったのでループを終了！");
                break;
            }
            System.out.println("  i = " + i);
        }

        // ========== continue（スキップ） ==========
        System.out.println("\n--- continue（奇数だけ表示） ---");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue;  // 偶数はスキップ
            }
            System.out.println("  i = " + i);
        }

        // ========== 二重ループ ==========
        System.out.println("\n--- 九九の一部（2の段） ---");
        int dan = 2;
        for (int j = 1; j <= 9; j++) {
            System.out.println("  " + dan + " x " + j + " = " + (dan * j));
        }

        // ===== 練習問題 =====
        // 1. 1から100までの偶数の合計を求めてみよう
        // 2. 九九の表（1の段〜9の段）を全部表示してみよう
        // 3. ★を使って以下の三角形を表示してみよう
        //    ★
        //    ★★
        //    ★★★
        //    ★★★★
        //    ★★★★★
    }
}
