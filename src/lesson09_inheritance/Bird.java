package lesson09_inheritance;

// ========== 子クラス: Bird ==========
// 「鳥は動物の一種」→ Animal を継承する
// 下のクラス宣言を「public class Bird extends Animal {」に書き換えよう

// public class Bird extends Animal {
public class Bird {

    // Bird独自のフィールド
    //
    // private boolean canFly;      // 飛べるかどうか

    // コンストラクタ
    //
    // public Bird(String name, int age, boolean canFly) {
    //     super(name, age);        // 親のコンストラクタを呼ぶ
    //     this.canFly = canFly;
    // }

    // Bird独自のメソッド
    // 飛べるかどうかで表示を変える
    //
    // public void fly() {
    //     if (canFly) {
    //         System.out.println(name + "は空を飛んでいます！");
    //     } else {
    //         System.out.println(name + "は飛べません...");
    //     }
    // }

    // 親のintroduceをオーバーライド
    //
    // @Override
    // public void introduce() {
    //     String ability = canFly ? "飛べる" : "飛べない";
    //     System.out.println("鳥: " + name + "（" + age + "歳, " + ability + "）");
    // }
}
