package lesson09_inheritance;

// ========== 子クラス: Cat ==========
// 「猫は動物の一種」→ Animal を継承する
//
// extends Animal を追加すると、Animalのフィールドとメソッドが使えるようになる
// 下のクラス宣言を「public class Cat extends Animal {」に書き換えよう

// public class Cat extends Animal {
public class Cat {

    // Cat独自のフィールド（Animalにはない）
    //
    // private boolean isIndoor;    // 室内飼いかどうか

    // コンストラクタ
    // super(name, age) で親クラス(Animal)のコンストラクタを呼ぶ
    // → 親のフィールド(name, age)の初期化は親に任せる
    // → Cat独自のフィールド(isIndoor)だけ自分で初期化
    //
    // public Cat(String name, int age, boolean isIndoor) {
    //     super(name, age);        // ← 親のコンストラクタを呼ぶ（必ず最初の行に書く）
    //     this.isIndoor = isIndoor;
    // }

    // Cat独自のメソッド（Animalにはない）
    //
    // public void purr() {
    //     System.out.println(name + "「ゴロゴロ...」");
    //     // ↑ name は親クラス(Animal)の protected フィールド。子からアクセスできる
    // }

    // @Override → 親クラスの同じメソッドを上書き（オーバーライド）する
    // 猫用の自己紹介に変更する
    //
    // @Override
    // public void introduce() {
    //     String type = isIndoor ? "室内飼い" : "外飼い";
    //     System.out.println("猫: " + name + "（" + age + "歳, " + type + "）");
    // }
}
