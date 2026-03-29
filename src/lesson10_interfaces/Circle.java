package lesson10_interfaces;

// 抽象クラス Shape を継承 + インターフェース Printable を実装
// → 「円は図形の一種で、印刷もできる」
//
// 下のクラス宣言を「public class Circle extends Shape implements Printable {」に書き換えよう

// public class Circle extends Shape implements Printable {
public class Circle {

    // Circle独自のフィールド
    //
    // private double radius;       // 半径

    // コンストラクタ
    // super(color) で親クラス Shape のコンストラクタを呼ぶ
    //
    // public Circle(String color, double radius) {
    //     super(color);            // Shape のコンストラクタ（colorを設定）
    //     this.radius = radius;
    // }

    // Shape の抽象メソッド area() を実装（必須）
    // 円の面積 = π × 半径²
    //
    // @Override
    // public double area() {
    //     return Math.PI * radius * radius;
    //     // Math.PI = 円周率（3.14159...）を提供する定数
    // }

    // Shape の抽象メソッド shapeName() を実装（必須）
    //
    // @Override
    // public String shapeName() {
    //     return "円";
    // }

    // Printable インターフェースの printInfo() を実装（必須）
    //
    // @Override
    // public void printInfo() {
    //     System.out.println("  [Circle] 半径=" + radius + ", 色=" + color);
    // }
}
