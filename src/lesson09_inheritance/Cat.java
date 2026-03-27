package lesson09_inheritance;

// ========== 子クラス: Cat ==========
public class Cat extends Animal {

    private boolean isIndoor;  // 室内飼いかどうか

    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);  // 親クラスのコンストラクタを呼ぶ
        this.isIndoor = isIndoor;
    }

    // 猫だけのメソッド
    public void purr() {
        System.out.println(name + "「ゴロゴロ...」");
    }

    // 親のメソッドをオーバーライド（上書き）
    @Override
    public void introduce() {
        String type = isIndoor ? "室内飼い" : "外飼い";
        System.out.println("猫: " + name + "（" + age + "歳, " + type + "）");
    }
}
