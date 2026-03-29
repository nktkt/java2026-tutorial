package lesson02_variables;

/**
 * ===== レッスン02: 変数とデータ型 =====
 *
 * 【目標】データを変数に保存し、画面に表示する方法を学ぶ
 *
 * 【変数とは？】
 *   データを入れておく「箱」のようなもの。
 *   Javaでは箱を作るとき、入れるデータの種類（型）を指定する必要がある。
 *
 *   書き方: 型 変数名 = 値;
 *   例:     int age = 25;
 *
 * 【基本的なデータ型（プリミティブ型）】
 *   int     → 整数（-21億〜21億）        例: 42, -10, 0
 *   long    → 大きい整数                 例: 100000000000L
 *   double  → 小数（倍精度浮動小数点）    例: 3.14, -0.5
 *   float   → 小数（単精度、あまり使わない）例: 3.14f
 *   boolean → 真偽値（2択）               true または false のみ
 *   char    → 1文字                       例: 'A', 'あ'（シングルクォート）
 *
 * 【参照型】
 *   String  → 文字列（大文字で始まる）     例: "Hello"（ダブルクォート）
 *             ※Stringはクラス。プリミティブ型ではないが最もよく使う
 *
 * 【命名ルール】
 *   - 先頭は英字か_（数字で始められない）
 *   - キャメルケースが慣習（myAge, firstName）
 *   - 定数はすべて大文字+アンダースコア（TAX_RATE）
 */
public class Variables {

    public static void main(String[] args) {

        // ---------- 整数型 (int) ----------
        // int = integer（整数）の略
        // 整数の計算に使う最も基本的な型
        //
        // int age = 25;
        // System.out.println("年齢: " + age);
        // ↑ "文字列" + 変数 → 文字列として結合される

        // ---------- 小数型 (double) ----------
        // double = 倍精度浮動小数点数
        // 小数を扱うときに使う（intだと小数部分が切り捨てられる）
        //
        // double height = 170.5;
        // System.out.println("身長: " + height + "cm");

        // ---------- 真偽値型 (boolean) ----------
        // boolean = true（真）か false（偽）の2択
        // 条件分岐（レッスン04）で大活躍する
        //
        // boolean isStudent = true;
        // System.out.println("学生ですか: " + isStudent);

        // ---------- 文字型 (char) ----------
        // char = character（文字）の略
        // シングルクォート ' ' で囲む（1文字だけ）
        //
        // char grade = 'A';
        // System.out.println("成績: " + grade);

        // ---------- 文字列型 (String) ----------
        // String = 文字の並び（0文字以上）
        // ダブルクォート " " で囲む
        // ※ String は大文字で始まる（クラスだから）
        //
        // String name = "Naoki";
        // System.out.println("名前: " + name);

        // ---------- 変数の値を変更する（再代入） ----------
        // 一度作った変数に別の値を入れ直せる
        // 再代入のときは型名を書かない（書くと別の新しい変数になる）
        //
        // int score = 80;
        // System.out.println("変更前のスコア: " + score);
        // score = 95;
        // System.out.println("変更後のスコア: " + score);

        // ---------- 定数 (final) ----------
        // final をつけると値を変更できなくなる
        // 変わらない値（税率、円周率など）に使う
        // 定数名は慣習的に「大文字_大文字」で書く
        //
        // final double TAX_RATE = 0.10;
        // System.out.println("消費税率: " + TAX_RATE);
        // TAX_RATE = 0.08;  // ← コンパイルエラー！ コメントを外して試してみよう

        // ---------- 文字列の結合 ----------
        // + 演算子で文字列同士をつなげることができる
        //
        // String firstName = "太郎";
        // String lastName = "山田";
        // String fullName = lastName + firstName;
        // System.out.println("フルネーム: " + fullName);

        // ===== 練習問題 =====
        // 1. 自分の名前、年齢、趣味を変数に入れて表示してみよう
        // 2. 商品の価格(int)と税率(double)から税込価格を計算して表示してみよう
    }
}
