package lesson09_inheritance;

/**
 * レッスン09のメインクラス。
 * 継承とポリモーフィズム（多態性）を体験する。
 *
 * 【手順】
 *   1. まず Animal.java のコメントを外す
 *   2. Cat.java の extends Animal を有効にしてコメントを外す
 *   3. Bird.java の extends Animal を有効にしてコメントを外す
 *   4. このファイルのコメントを外して実行する
 */
public class Main {

    public static void main(String[] args) {

        // ========== 各クラスのオブジェクト作成 ==========
        // System.out.println("--- オブジェクト作成 ---");
        // Cat tama = new Cat("タマ", 3, true);
        // Bird suzume = new Bird("スズメ", 1, true);
        // Bird penguin = new Bird("ペンギン", 5, false);

        // ========== 継承したメソッドと独自メソッド ==========
        // tama.introduce();  // Cat でオーバーライドした introduce が呼ばれる
        // tama.purr();       // Cat 独自のメソッド
        // tama.eat();        // Animal から継承したメソッド（Catに書いてないのに使える！）
        // tama.sleep();      // Animal から継承したメソッド

        // System.out.println();
        // suzume.introduce();
        // suzume.fly();      // Bird 独自のメソッド

        // System.out.println();
        // penguin.introduce();
        // penguin.fly();     // canFly=false なので「飛べません」と表示される

        // ========== ポリモーフィズム（多態性） ==========
        // 【重要】親クラスの型で子クラスのオブジェクトを扱える！
        //
        // Animal[] animals = ... → Cat も Bird も Animal の一種なので配列に入る
        // animal.introduce()     → 実際の型（Cat/Bird）の introduce が呼ばれる
        //                         これがポリモーフィズム！
        //
        // System.out.println("\n--- ポリモーフィズム ---");
        // Animal[] animals = {tama, suzume, penguin};
        //
        // for (Animal animal : animals) {
        //     animal.introduce();
        //     // ↑ 変数の型は Animal だが、実際のオブジェクトに応じた
        //     //   introduce() が呼ばれる（Cat版 or Bird版）
        // }

        // ===== 練習問題 =====
        // 1. Animalを継承した Fish クラスを作ってみよう（swimメソッドを追加）
        // 2. Vehicle（乗り物）クラスを親にして、Car と Bicycle を作ってみよう
    }
}
