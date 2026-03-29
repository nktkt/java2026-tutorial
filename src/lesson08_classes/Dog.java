package lesson08_classes;

/**
 * ===== レッスン08: クラスとオブジェクト =====
 *
 * 【目標】オブジェクト指向プログラミング(OOP)の基本を理解する
 *
 * 【クラスとは？】
 *   「設計図」のようなもの。どんなデータ（フィールド）と動作（メソッド）を
 *   持つかを定義する。
 *
 * 【オブジェクト（インスタンス）とは？】
 *   クラスから作った「実体」。設計図から作った製品のイメージ。
 *   new キーワードで作る: Dog dog1 = new Dog("ポチ", "柴犬", 3);
 *
 * 【3つの要素】
 *   フィールド:     オブジェクトが持つデータ（変数）
 *   メソッド:       オブジェクトが持つ動作（処理）
 *   コンストラクタ: オブジェクト生成時に自動で呼ばれる初期化処理
 *
 * 【thisキーワード】
 *   「このオブジェクト自身」を指す。
 *   引数名とフィールド名が同じとき、区別するために使う。
 *   this.name = name; → 「このオブジェクトのname」に「引数のname」を代入
 */
public class Dog {

    // ========== フィールド ==========
    // このクラスのオブジェクトが持つデータ
    // 各オブジェクトごとに別々の値を持つ
    //
    // String name;     // 名前
    // String breed;    // 犬種
    // int age;         // 年齢

    // ========== コンストラクタ ==========
    // クラス名と同じ名前のメソッド（戻り値の型は書かない）
    // new Dog(...) したときに自動で呼ばれる
    // 引数で受け取った値をフィールドに設定する
    //
    // public Dog(String name, String breed, int age) {
    //     this.name = name;     // this.name(フィールド) = name(引数)
    //     this.breed = breed;
    //     this.age = age;
    // }

    // ========== メソッド ==========
    // このクラスのオブジェクトができる動作
    // static がない → オブジェクトから呼ぶ（dog1.bark()）
    //
    // public void bark() {
    //     System.out.println(name + "「ワンワン！」");
    // }

    // public void introduce() {
    //     System.out.println("名前: " + name + ", 犬種: " + breed + ", 年齢: " + age + "歳");
    // }

    // 他のDogオブジェクトと年齢を比較するメソッド
    // public boolean isOlderThan(Dog other) {
    //     return this.age > other.age;
    // }
}
