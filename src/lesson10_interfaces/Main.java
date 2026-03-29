package lesson10_interfaces;

/**
 * レッスン10のメインクラス。
 * インターフェースと抽象クラスの使い方を体験する。
 *
 * 【手順】
 *   1. Printable.java, Saveable.java のコメントを外す（インターフェース）
 *   2. Shape.java を abstract class に書き換えてコメントを外す（抽象クラス）
 *   3. Circle.java の extends/implements を書き換えてコメントを外す
 *   4. Rectangle.java の extends/implements を書き換えてコメントを外す
 *   5. このファイルのコメントを外して実行する
 */
public class Main {

    public static void main(String[] args) {

        // ========== オブジェクトの作成 ==========
        // System.out.println("--- 抽象クラス（Shape） ---");
        // Circle circle = new Circle("赤", 5.0);
        // Rectangle rect = new Rectangle("青", 4.0, 6.0);
        //
        // // describe() は Shape に定義された通常メソッド
        // // 中で area() と shapeName() を呼んでいる → 子クラスの実装が使われる
        // circle.describe();   // → 赤の円（面積: 78.53...）
        // rect.describe();     // → 青の長方形（面積: 24.0）

        // ========== ポリモーフィズム（抽象クラスの型で扱う） ==========
        // Shape[] に Circle も Rectangle も入れられる
        // describe() を呼ぶと、実際のクラスの area()/shapeName() が動く
        //
        // System.out.println("\n--- ポリモーフィズム ---");
        // Shape[] shapes = {circle, rect};
        // for (Shape shape : shapes) {
        //     shape.describe();
        // }

        // ========== インターフェースの型で扱う ==========
        // Printable[] に Circle も Rectangle も入れられる
        // （どちらも implements Printable しているから）
        //
        // System.out.println("\n--- インターフェース（Printable） ---");
        // Printable[] printables = {circle, rect};
        // for (Printable p : printables) {
        //     p.printInfo();   // 各クラスの printInfo() が呼ばれる
        // }

        // ---------- Saveable はRectangleだけ ----------
        // Circle は Saveable を実装していないので save() を持たない
        // この「必要な能力だけ実装する」のがインターフェースの強み
        //
        // System.out.println("\n--- インターフェース（Saveable） ---");
        // rect.save();

        // ========== まとめ ==========
        // System.out.println("\n===== まとめ =====");
        // System.out.println("抽象クラス: 「〜は〜の一種」（is-a関係）を表す");
        // System.out.println("  例: CircleはShapeの一種");
        // System.out.println("インターフェース: 「〜ができる」（能力）を表す");
        // System.out.println("  例: Circleは印刷できる(Printable)");
        // System.out.println("  例: Rectangleは印刷できて保存もできる(Printable, Saveable)");

        // ===== 練習問題 =====
        // 1. Shapeを継承したTriangle（三角形）クラスを作ってみよう
        //    面積 = 底辺 × 高さ / 2
        // 2. Calculatable インターフェースを作り、calculate()メソッドを定義してみよう
        //    それを実装するTaxCalculatorクラスを作ってみよう
    }
}
