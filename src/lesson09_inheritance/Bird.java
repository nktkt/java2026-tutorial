package lesson09_inheritance;

// ========== 子クラス: Bird ==========
public class Bird extends Animal {

    private boolean canFly;

    public Bird(String name, int age, boolean canFly) {
        super(name, age);
        this.canFly = canFly;
    }

    public void fly() {
        if (canFly) {
            System.out.println(name + "は空を飛んでいます！");
        } else {
            System.out.println(name + "は飛べません...");
        }
    }

    @Override
    public void introduce() {
        String ability = canFly ? "飛べる" : "飛べない";
        System.out.println("鳥: " + name + "（" + age + "歳, " + ability + "）");
    }
}
