package lesson09_inheritance;

/**
 * ===== レッスン09: 継承 =====
 *
 * 【目標】既存のクラスを拡張して新しいクラスを作る方法を学ぶ
 *
 * 【継承とは？】
 *   親クラスの機能（フィールド・メソッド）を子クラスが引き継ぐこと。
 *   「猫は動物の一種」→ Cat extends Animal
 *   猫は動物が持つ機能（食べる、寝る）をそのまま使え、
 *   さらに猫だけの機能（ゴロゴロ鳴く）を追加できる。
 *
 * 【キーワード】
 *   extends    → 親クラスを継承する（class Cat extends Animal）
 *   super      → 親クラスを参照する
 *                super(name, age) → 親のコンストラクタを呼ぶ
 *   @Override  → 親のメソッドを子で上書き（オーバーライド）する
 *   protected  → 自分と子クラスからアクセスできるアクセス修飾子
 *                public（どこからでも） > protected（自分+子） > private（自分だけ）
 *
 * 【ポリモーフィズム（多態性）】
 *   親クラスの型で子クラスのオブジェクトを扱える。
 *   Animal animal = new Cat(...);  ← OK！
 *   同じメソッド呼び出しでも、実際のクラスによって動作が変わる。
 *
 * 【手順】
 *   1. まず Animal.java のコメントを外す（親クラス）
 *   2. Cat.java, Bird.java のコメントを外す（子クラス）
 *   3. Main.java のコメントを外して実行する
 */

// ========== 親クラス（スーパークラス） ==========
// Cat, Bird などの共通部分をまとめたクラス
public class Animal {

    // protected: このクラスと子クラスからアクセスできる
    // private にすると子クラスから直接アクセスできなくなる
    //
    // protected String name;
    // protected int age;

    // コンストラクタ: name と age を受け取って初期化
    //
    // public Animal(String name, int age) {
    //     this.name = name;
    //     this.age = age;
    // }

    // 共通メソッド: すべての動物が食べる・寝る
    // 子クラスでそのまま使える（オーバーライドしなくてOK）
    //
    // public void eat() {
    //     System.out.println(name + "は食事中です。");
    // }

    // public void sleep() {
    //     System.out.println(name + "は眠っています。");
    // }

    // このメソッドは子クラスでオーバーライド（上書き）される予定
    //
    // public void introduce() {
    //     System.out.println("動物: " + name + "（" + age + "歳）");
    // }
}
