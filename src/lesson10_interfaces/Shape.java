package lesson10_interfaces;

/**
 * 抽象クラス = 一部だけ実装された設計図
 *
 *   - abstract キーワードをつける
 *   - 抽象メソッド（中身なし）と通常メソッド（中身あり）を混ぜられる
 *   - 直接インスタンスを作れない（new Shape() はエラー）
 *   - 子クラスが抽象メソッドを必ず実装する
 */
public abstract class Shape {

    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    // 抽象メソッド（子クラスが必ず実装する）
    public abstract double area();
    public abstract String shapeName();

    // 通常メソッド（そのまま使える）
    public void describe() {
        System.out.println(color + "の" + shapeName() + "（面積: " + area() + "）");
    }
}
