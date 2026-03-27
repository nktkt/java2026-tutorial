package lesson10_interfaces;

/**
 * レッスン10のメインクラス。
 * インターフェースと抽象クラスの使い方を学ぶ。
 */
public class Main {

    public static void main(String[] args) {

        // ========== 抽象クラスの利用 ==========
        System.out.println("--- 抽象クラス（Shape） ---");
        Circle circle = new Circle("赤", 5.0);
        Rectangle rect = new Rectangle("青", 4.0, 6.0);

        // 親クラスの通常メソッドが使える
        circle.describe();  // → 赤の円（面積: 78.53...）
        rect.describe();    // → 青の長方形（面積: 24.0）

        // ========== ポリモーフィズム（親の型で扱う） ==========
        System.out.println("\n--- ポリモーフィズム ---");
        Shape[] shapes = {circle, rect};
        for (Shape shape : shapes) {
            shape.describe();  // それぞれのクラスの実装が呼ばれる
        }

        // ========== インターフェースの利用 ==========
        System.out.println("\n--- インターフェース（Printable） ---");
        Printable[] printables = {circle, rect};
        for (Printable p : printables) {
            p.printInfo();
        }

        // Saveableインターフェース（Rectangleだけ実装）
        System.out.println("\n--- インターフェース（Saveable） ---");
        rect.save();

        // ========== まとめ ==========
        System.out.println("\n===== まとめ =====");
        System.out.println("抽象クラス: 「〜は〜の一種」（is-a関係）を表す");
        System.out.println("  例: CircleはShapeの一種");
        System.out.println("インターフェース: 「〜ができる」（能力）を表す");
        System.out.println("  例: Circleは印刷できる(Printable)");
        System.out.println("  例: Rectangleは印刷できて保存もできる(Printable, Saveable)");

        // ===== 練習問題 =====
        // 1. Shapeを継承したTriangle（三角形）クラスを作ってみよう
        // 2. Calculatable インターフェースを作り、calculate()メソッドを定義してみよう
        //    それを実装するTaxCalculatorクラスを作ってみよう
    }
}
