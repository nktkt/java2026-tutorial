package lesson04_conditions;

/**
 * ===== レッスン04: 条件分岐 =====
 *
 * プログラムの流れを条件によって変える。
 *
 * if文:     条件がtrueのとき実行
 * else if:  前の条件がfalseで、この条件がtrueのとき実行
 * else:     すべての条件がfalseのとき実行
 * switch文: 値が一致するケースを実行
 */
public class Conditions {

    public static void main(String[] args) {

        // ========== if文 ==========
        System.out.println("--- if文 ---");
        int score = 75;

        if (score >= 80) {
            System.out.println("優秀です！");
        } else if (score >= 60) {
            System.out.println("合格です。");
        } else {
            System.out.println("不合格です…");
        }

        // ========== 複数条件の組み合わせ ==========
        System.out.println("\n--- 複数条件 ---");
        int age = 20;
        boolean hasLicense = true;

        if (age >= 18 && hasLicense) {
            System.out.println("運転できます。");
        } else {
            System.out.println("運転できません。");
        }

        // ========== switch文 ==========
        System.out.println("\n--- switch文 ---");
        String day = "月";

        switch (day) {
            case "月":
            case "火":
            case "水":
            case "木":
            case "金":
                System.out.println(day + "曜日は平日です。");
                break;  // breakを忘れると次のcaseも実行される！
            case "土":
            case "日":
                System.out.println(day + "曜日は休日です。");
                break;
            default:
                System.out.println("不明な曜日です。");
                break;
        }

        // ========== 三項演算子（短いif-else） ==========
        System.out.println("\n--- 三項演算子 ---");
        int number = 7;
        String result = (number % 2 == 0) ? "偶数" : "奇数";
        System.out.println(number + "は" + result + "です。");

        // ===== 練習問題 =====
        // 1. テストの点数(0〜100)で「秀・優・良・可・不可」を判定するプログラムを書いてみよう
        //    秀: 90以上、優: 80以上、良: 70以上、可: 60以上、不可: 60未満
        // 2. 月(1〜12)を入力して春夏秋冬を表示するswitch文を書いてみよう
    }
}
