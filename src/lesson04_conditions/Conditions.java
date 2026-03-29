package lesson04_conditions;

/**
 * ===== レッスン04: 条件分岐 =====
 *
 * 【目標】条件によってプログラムの動きを変える方法を学ぶ
 *
 * 【if文の構造】
 *   if (条件) {
 *       条件がtrueのとき実行;
 *   } else if (別の条件) {
 *       最初の条件がfalseで、この条件がtrueのとき実行;
 *   } else {
 *       すべての条件がfalseのとき実行;
 *   }
 *
 * 【switch文の構造】
 *   switch (値) {
 *       case 値1: 処理; break;
 *       case 値2: 処理; break;
 *       default:  どれにも一致しないとき;
 *   }
 *
 * 【三項演算子】
 *   変数 = (条件) ? trueのときの値 : falseのときの値;
 *   ※ 短い if-else の省略形
 *
 * 【使い分け】
 *   - if文: 範囲の判定（>=, <=）や複雑な条件に向いている
 *   - switch文: 特定の値との一致判定に向いている
 */
public class Conditions {

    public static void main(String[] args) {

        // ========== if文 ==========
        // 条件（boolean式）がtrueなら{ }の中を実行する
        // else if で条件を追加、else で「それ以外」を処理
        // ※ 上から順に判定され、最初にtrueになったブロックだけ実行される
        //
        // System.out.println("--- if文 ---");
        // int score = 75;
        //
        // if (score >= 80) {
        //     // score が 80以上のとき
        //     System.out.println("優秀です！");
        // } else if (score >= 60) {
        //     // score が 60以上80未満のとき（上の条件がfalseなので）
        //     System.out.println("合格です。");
        // } else {
        //     // score が 60未満のとき
        //     System.out.println("不合格です…");
        // }

        // ========== 複数条件の組み合わせ ==========
        // && (AND) や || (OR) で条件を組み合わせられる
        // 例: 「18歳以上」かつ「免許を持っている」
        //
        // System.out.println("\n--- 複数条件 ---");
        // int age = 20;
        // boolean hasLicense = true;
        //
        // if (age >= 18 && hasLicense) {
        //     // 両方の条件を満たすとき
        //     System.out.println("運転できます。");
        // } else {
        //     // どちらかの条件を満たさないとき
        //     System.out.println("運転できません。");
        // }

        // ========== switch文 ==========
        // 変数の値が case に一致したら、そのブロックを実行する
        //
        // 【重要】break を忘れると次の case も続けて実行される（フォールスルー）
        //   → 逆に利用して、複数のcaseに同じ処理をさせることもできる
        //     （下の例では月〜金がすべて「平日」になる）
        //
        // System.out.println("\n--- switch文 ---");
        // String day = "月";
        //
        // switch (day) {
        //     case "月":   // ← breakがないので次のcaseに落ちる（フォールスルー）
        //     case "火":
        //     case "水":
        //     case "木":
        //     case "金":
        //         System.out.println(day + "曜日は平日です。");
        //         break;   // ← ここでswitch文を抜ける
        //     case "土":
        //     case "日":
        //         System.out.println(day + "曜日は休日です。");
        //         break;
        //     default:     // ← どのcaseにも一致しなかったとき
        //         System.out.println("不明な曜日です。");
        //         break;
        // }

        // ========== 三項演算子（短いif-else） ==========
        // 書き方: 条件 ? trueの値 : falseの値
        // 簡単な条件分岐を1行で書ける
        //
        // System.out.println("\n--- 三項演算子 ---");
        // int number = 7;
        // String result = (number % 2 == 0) ? "偶数" : "奇数";
        // // ↑ numberを2で割った余りが0なら"偶数"、そうでなければ"奇数"
        // System.out.println(number + "は" + result + "です。");

        // ===== 練習問題 =====
        // 1. テストの点数(0〜100)で「秀・優・良・可・不可」を判定するプログラムを書いてみよう
        //    秀: 90以上、優: 80以上、良: 70以上、可: 60以上、不可: 60未満
        // 2. 月(1〜12)を入力して春夏秋冬を表示するswitch文を書いてみよう
    }
}
