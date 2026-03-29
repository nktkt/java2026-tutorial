package lesson10_interfaces;

// Shape を継承 + Printable と Saveable の2つのインターフェースを実装
// → 「長方形は図形の一種で、印刷も保存もできる」
//
// 下のクラス宣言を
// 「public class Rectangle extends Shape implements Printable, Saveable {」に書き換えよう

// public class Rectangle extends Shape implements Printable, Saveable {
public class Rectangle {

    // Rectangle独自のフィールド
    //
    // private double width;        // 幅
    // private double height;       // 高さ

    // コンストラクタ
    //
    // public Rectangle(String color, double width, double height) {
    //     super(color);
    //     this.width = width;
    //     this.height = height;
    // }

    // Shape の抽象メソッド area() を実装
    // 長方形の面積 = 幅 × 高さ
    //
    // @Override
    // public double area() {
    //     return width * height;
    // }

    // Shape の抽象メソッド shapeName() を実装
    //
    // @Override
    // public String shapeName() {
    //     return "長方形";
    // }

    // Printable インターフェースの printInfo() を実装
    //
    // @Override
    // public void printInfo() {
    //     System.out.println("  [Rectangle] 幅=" + width + ", 高さ=" + height + ", 色=" + color);
    // }

    // Saveable インターフェースの save() を実装
    // ※ Circle は Saveable を実装していないので save() がない
    //    Rectangle だけが保存機能を持つ
    //
    // @Override
    // public void save() {
    //     System.out.println("  " + shapeName() + "のデータを保存しました。");
    // }
}
