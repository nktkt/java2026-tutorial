package lesson02_variables;

/**
 * ===== レッスン02: 変数とデータ型 =====
 *
 * 変数はデータを入れる「箱」です。
 * Javaでは変数を使う前に「型」を宣言する必要があります。
 *
 * 基本的なデータ型:
 *   int     → 整数          (例: 42, -10)
 *   double  → 小数          (例: 3.14, -0.5)
 *   boolean → 真偽値        (true または false)
 *   char    → 1文字         (例: 'A', 'あ')
 *   String  → 文字列        (例: "Hello") ※大文字で始まる
 */
public class Variables {

    public static void main(String[] args) {

        // --- 整数型 (int) ---
        int age = 25;
        System.out.println("年齢: " + age);

        // --- 小数型 (double) ---
        double height = 170.5;
        System.out.println("身長: " + height + "cm");

        // --- 真偽値型 (boolean) ---
        boolean isStudent = true;
        System.out.println("学生ですか: " + isStudent);

        // --- 文字型 (char) ---シングルクォートを使う
        char grade = 'A';
        System.out.println("成績: " + grade);

        // --- 文字列型 (String) --- ダブルクォートを使う
        String name = "Naoki";
        System.out.println("名前: " + name);

        // --- 変数の値を変更する ---
        int score = 80;
        System.out.println("変更前のスコア: " + score);
        score = 95;  // 再代入（型名は不要）
        System.out.println("変更後のスコア: " + score);

        // --- 定数 (final) --- 値を変更できない変数
        final double TAX_RATE = 0.10;
        System.out.println("消費税率: " + TAX_RATE);
        // TAX_RATE = 0.08;  // ← これはエラーになる！コメントを外して試してみよう

        // --- 文字列の結合 ---
        String firstName = "太郎";
        String lastName = "山田";
        String fullName = lastName + firstName;  // + で文字列をつなげる
        System.out.println("フルネーム: " + fullName);

        // ===== 練習問題 =====
        // 1. 自分の名前、年齢、趣味を変数に入れて表示してみよう
        // 2. 商品の価格(int)と税率(double)から税込価格を計算して表示してみよう
    }
}
