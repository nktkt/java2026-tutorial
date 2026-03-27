package lesson10_interfaces;

public class Rectangle extends Shape implements Printable, Saveable {

    private double width;
    private double height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public String shapeName() {
        return "長方形";
    }

    @Override
    public void printInfo() {
        System.out.println("  [Rectangle] 幅=" + width + ", 高さ=" + height + ", 色=" + color);
    }

    @Override
    public void save() {
        System.out.println("  " + shapeName() + "のデータを保存しました。");
    }
}
