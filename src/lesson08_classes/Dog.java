package lesson08_classes;

/**
 * ===== レッスン08: クラスとオブジェクト =====
 *
 * クラス = 設計図（どんなデータと動作を持つか定義する）
 * オブジェクト = クラスから作った実体（インスタンス）
 *
 * フィールド（変数）: オブジェクトが持つデータ
 * メソッド: オブジェクトが持つ動作
 * コンストラクタ: オブジェクトを作るときに呼ばれる特別なメソッド
 */
public class Dog {

    // ========== フィールド（このクラスが持つデータ） ==========
    String name;
    String breed;
    int age;

    // ========== コンストラクタ（オブジェクト作成時に呼ばれる） ==========
    public Dog(String name, String breed, int age) {
        this.name = name;     // this = このオブジェクト自身
        this.breed = breed;
        this.age = age;
    }

    // ========== メソッド（このクラスの動作） ==========
    public void bark() {
        System.out.println(name + "「ワンワン！」");
    }

    public void introduce() {
        System.out.println("名前: " + name + ", 犬種: " + breed + ", 年齢: " + age + "歳");
    }

    public boolean isOlderThan(Dog other) {
        return this.age > other.age;
    }
}
