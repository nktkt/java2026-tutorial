package lesson10_interfaces;

/**
 * 【抽象クラスとは？】
 *   一部だけ実装された設計図。インターフェースとクラスの中間的な存在。
 *
 * 【特徴】
 *   - abstract キーワードをつける
 *   - 抽象メソッド: 中身なし。子クラスが必ず実装する
 *   - 通常メソッド: 中身あり。子クラスがそのまま使える
 *   - 直接 new できない（new Shape() はエラー）
 *   - 子クラスが extends して使う
 *
 * 【インターフェースとの使い分け】
 *   抽象クラス:     「〜は〜の一種」（is-a関係）。共通のフィールドやメソッドがある。
 *   インターフェース: 「〜ができる」（has-ability関係）。能力を定義する。
 *
 *   例: Circle is a Shape（円は図形の一種） → extends Shape
 *       Circle is Printable（円は印刷できる） → implements Printable
 *
 * 下のクラス宣言を「public abstract class Shape {」に書き換えよう
 */

// public abstract class Shape {
public class Shape {

    // 全Shapeに共通のフィールド
    //
    // protected String color;

    // コンストラクタ（抽象クラスでもコンストラクタは書ける）
    // ただし直接 new Shape() はできない。子クラスの super() から呼ばれる
    //
    // public Shape(String color) {
    //     this.color = color;
    // }

    // ---------- 抽象メソッド ----------
    // abstract をつけると中身を書かない（子クラスが必ず実装する）
    // 「面積を計算する方法は図形によって違う」→ 抽象にして子に任せる
    //
    // public abstract double area();
    // public abstract String shapeName();

    // ---------- 通常メソッド ----------
    // 中身がある。子クラスがそのまま使える。
    // area() と shapeName() は子クラスの実装が呼ばれる（ポリモーフィズム）
    //
    // public void describe() {
    //     System.out.println(color + "の" + shapeName() + "（面積: " + area() + "）");
    // }
}
