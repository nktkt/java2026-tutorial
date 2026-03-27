package lesson10_interfaces;

// 抽象クラスを継承 + インターフェースを実装
public class Circle extends Shape implements Printable {

    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public String shapeName() {
        return "円";
    }

    @Override
    public void printInfo() {
        System.out.println("  [Circle] 半径=" + radius + ", 色=" + color);
    }
}
