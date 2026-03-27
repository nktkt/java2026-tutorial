package lesson08_classes;

/**
 * レッスン08のメインクラス。
 * Dogクラスを使ってオブジェクト指向の基本を学ぶ。
 *
 * 実行方法: このファイルを右クリック → Run 'Main.main()'
 */
public class Main {

    public static void main(String[] args) {

        // ========== オブジェクトの作成 ==========
        System.out.println("--- オブジェクトの作成 ---");
        Dog dog1 = new Dog("ポチ", "柴犬", 3);
        Dog dog2 = new Dog("マロン", "トイプードル", 5);

        // ========== メソッドの呼び出し ==========
        dog1.introduce();
        dog1.bark();

        dog2.introduce();
        dog2.bark();

        // ========== オブジェクト同士の比較 ==========
        System.out.println("\n--- 比較 ---");
        if (dog1.isOlderThan(dog2)) {
            System.out.println(dog1.name + "の方が年上です。");
        } else {
            System.out.println(dog2.name + "の方が年上です。");
        }

        // ========== フィールドへの直接アクセス ==========
        System.out.println("\n--- フィールドアクセス ---");
        System.out.println(dog1.name + "の年齢: " + dog1.age);
        dog1.age = 4;  // 誕生日！
        System.out.println(dog1.name + "の新しい年齢: " + dog1.age);

        // ========== 配列にオブジェクトを入れる ==========
        System.out.println("\n--- オブジェクトの配列 ---");
        Dog[] dogs = {dog1, dog2, new Dog("ハチ", "秋田犬", 7)};
        for (Dog dog : dogs) {
            dog.introduce();
        }

        // ===== 練習問題 =====
        // 1. Studentクラスを作ってみよう（名前、学年、得意科目をフィールドに持つ）
        // 2. Studentクラスに自己紹介メソッドを作ってみよう
        // 3. Student配列を作り、全員の自己紹介を表示してみよう
    }
}
