package lesson09_inheritance;

/**
 * レッスン09のメインクラス。
 * 継承とポリモーフィズム（多態性）を学ぶ。
 */
public class Main {

    public static void main(String[] args) {

        // ========== 各クラスのオブジェクト作成 ==========
        System.out.println("--- オブジェクト作成 ---");
        Cat tama = new Cat("タマ", 3, true);
        Bird suzume = new Bird("スズメ", 1, true);
        Bird penguin = new Bird("ペンギン", 5, false);

        // ========== 固有メソッドの呼び出し ==========
        tama.introduce();
        tama.purr();         // 猫だけのメソッド
        tama.eat();          // 親クラスから継承したメソッド
        tama.sleep();        // 親クラスから継承したメソッド

        System.out.println();
        suzume.introduce();
        suzume.fly();        // 鳥だけのメソッド

        System.out.println();
        penguin.introduce();
        penguin.fly();

        // ========== ポリモーフィズム（多態性） ==========
        // 親クラスの型で子クラスのオブジェクトを扱える！
        System.out.println("\n--- ポリモーフィズム ---");
        Animal[] animals = {tama, suzume, penguin};

        for (Animal animal : animals) {
            animal.introduce();  // 各クラスのintroduce()が呼ばれる
        }

        // ===== 練習問題 =====
        // 1. Animalを継承した Fish クラスを作ってみよう（swimメソッドを追加）
        // 2. Vehicle（乗り物）クラスを親にして、Car と Bicycle を作ってみよう
    }
}
