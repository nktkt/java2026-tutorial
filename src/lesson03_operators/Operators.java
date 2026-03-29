package lesson03_operators;

/**
 * ===== レッスン03: 演算子 =====
 *
 * 【目標】計算・比較・論理判定の方法を学ぶ
 *
 * 【演算子の種類】
 *   算術演算子:   +（足す） -（引く） *（掛ける） /（割る） %（余り）
 *   比較演算子:   ==（等しい） !=（等しくない） < > <= >=
 *   論理演算子:   &&（かつ） ||（または） !（否定）
 *   代入演算子:   = += -= *= /= %=
 *   インクリメント: ++（1増やす） --（1減らす）
 *
 * 【注意点】
 *   - 整数同士の割り算は小数が切り捨てられる（10 / 3 → 3）
 *   - ==は「等しいか比較」、=は「代入」。間違えやすい！
 *   - Stringの比較は == ではなく .equals() を使う（レッスン08で詳しく）
 */
public class Operators {

    public static void main(String[] args) {

        // ========== 算術演算子 ==========
        // 数学の四則演算 + 余り（%）
        //
        // int a = 10;
        // int b = 3;
        //
        // System.out.println("--- 算術演算子 ---");
        // System.out.println("a + b = " + (a + b));   // 足し算 → 13
        // System.out.println("a - b = " + (a - b));   // 引き算 → 7
        // System.out.println("a * b = " + (a * b));   // 掛け算 → 30
        // System.out.println("a / b = " + (a / b));   // 割り算 → 3 ※整数同士は小数切り捨て！
        // System.out.println("a % b = " + (a % b));   // 余り   → 1 ※10÷3=3あまり1
        //
        // ↑ (a + b)の()がないと、"a + b = " + a + b → "a + b = 103" になってしまう
        //   文字列 + 数値 は文字列結合になるため、先に計算したいなら()で囲む

        // ---------- 整数と小数の割り算の違い ----------
        // int同士: 10 / 3 → 3（小数部分が消える）
        // doubleを使うと: 10.0 / 3 → 3.3333...
        //
        // double c = 10.0;
        // System.out.println("c / b = " + (c / b));

        // ========== インクリメント・デクリメント ==========
        // ++ → 1だけ増やす（count = count + 1 の省略形）
        // -- → 1だけ減らす（count = count - 1 の省略形）
        // ループ（レッスン05）でよく使う
        //
        // System.out.println("\n--- インクリメント・デクリメント ---");
        // int count = 0;
        // count++;
        // System.out.println("count++ → " + count);  // → 1
        // count--;
        // System.out.println("count-- → " + count);  // → 0

        // ---------- 複合代入演算子 ----------
        // x += 5 は x = x + 5 の省略形
        // 同様に -= *= /= %= もある
        //
        // int x = 10;
        // x += 5;   // x = x + 5 → 15
        // System.out.println("x += 5 → " + x);
        // x *= 2;   // x = x * 2 → 30
        // System.out.println("x *= 2 → " + x);

        // ========== 比較演算子 ==========
        // 2つの値を比較して、結果は boolean（true/false）になる
        // if文（レッスン04）の条件に使う
        //
        // System.out.println("\n--- 比較演算子 ---");
        // System.out.println("10 == 10 → " + (10 == 10));  // 等しい → true
        // System.out.println("10 != 5  → " + (10 != 5));   // 等しくない → true
        // System.out.println("10 > 5   → " + (10 > 5));    // より大きい → true
        // System.out.println("10 < 5   → " + (10 < 5));    // より小さい → false
        // System.out.println("10 >= 10 → " + (10 >= 10));  // 以上 → true
        // System.out.println("10 <= 5  → " + (10 <= 5));   // 以下 → false

        // ========== 論理演算子 ==========
        // 複数の条件を組み合わせるときに使う
        //   && (AND): 両方trueのときだけtrue
        //   || (OR):  どちらかがtrueならtrue
        //   !  (NOT): true→false、false→trueに反転
        //
        // System.out.println("\n--- 論理演算子 ---");
        // boolean sunny = true;
        // boolean warm = false;
        //
        // System.out.println("sunny && warm = " + (sunny && warm));  // 両方true? → false
        // System.out.println("sunny || warm = " + (sunny || warm));  // どちらかtrue? → true
        // System.out.println("!sunny = " + (!sunny));                // 反転 → false

        // ===== 練習問題 =====
        // 1. 1234を7で割った余りを求めてみよう
        // 2. 「年齢が18以上 かつ 学生である」をboolean変数で表現してみよう
    }
}
