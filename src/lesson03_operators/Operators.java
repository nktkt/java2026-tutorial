package lesson03_operators;

/**
 * ===== レッスン03: 演算子 =====
 *
 * 算術演算子: +  -  *  /  %
 * 比較演算子: ==  !=  <  >  <=  >=
 * 論理演算子: &&(かつ)  ||(または)  !(否定)
 */
public class Operators {

    public static void main(String[] args) {

        // ========== 算術演算子 ==========
        int a = 10;
        int b = 3;

        System.out.println("--- 算術演算子 ---");
        System.out.println("a + b = " + (a + b));   // 足し算 → 13
        System.out.println("a - b = " + (a - b));   // 引き算 → 7
        System.out.println("a * b = " + (a * b));   // 掛け算 → 30
        System.out.println("a / b = " + (a / b));   // 割り算 → 3 (整数同士は小数が切り捨て！)
        System.out.println("a % b = " + (a % b));   // 余り   → 1

        // 小数で割り算すると正確な結果が出る
        double c = 10.0;
        System.out.println("c / b = " + (c / b));   // → 3.3333...

        // ========== インクリメント・デクリメント ==========
        System.out.println("\n--- インクリメント・デクリメント ---");
        int count = 0;
        count++;  // 1増やす（count = count + 1 と同じ）
        System.out.println("count++ → " + count);  // → 1
        count--;  // 1減らす
        System.out.println("count-- → " + count);  // → 0

        // 複合代入演算子
        int x = 10;
        x += 5;   // x = x + 5 と同じ
        System.out.println("x += 5 → " + x);  // → 15
        x *= 2;   // x = x * 2 と同じ
        System.out.println("x *= 2 → " + x);  // → 30

        // ========== 比較演算子 ==========
        System.out.println("\n--- 比較演算子 ---");
        System.out.println("10 == 10 → " + (10 == 10));  // 等しい → true
        System.out.println("10 != 5  → " + (10 != 5));   // 等しくない → true
        System.out.println("10 > 5   → " + (10 > 5));    // より大きい → true
        System.out.println("10 < 5   → " + (10 < 5));    // より小さい → false
        System.out.println("10 >= 10 → " + (10 >= 10));  // 以上 → true
        System.out.println("10 <= 5  → " + (10 <= 5));   // 以下 → false

        // ========== 論理演算子 ==========
        System.out.println("\n--- 論理演算子 ---");
        boolean sunny = true;
        boolean warm = false;

        System.out.println("sunny && warm = " + (sunny && warm));  // 両方true → false
        System.out.println("sunny || warm = " + (sunny || warm));  // どちらかtrue → true
        System.out.println("!sunny = " + (!sunny));                // 反転 → false

        // ===== 練習問題 =====
        // 1. 1234を7で割った余りを求めてみよう
        // 2. 「年齢が18以上 かつ 学生である」をboolean変数で表現してみよう
    }
}
