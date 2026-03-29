package lesson08_classes;

/**
 * レッスン08のメインクラス。
 * Dogクラスを使ってオブジェクト指向の基本を体験する。
 *
 * 【手順】
 *   1. まず Dog.java のコメントを外してクラスを完成させる
 *   2. 次にこのファイルのコメントを外して実行する
 *
 * 【実行方法】このファイルを右クリック → Run 'Main.main()'
 */
public class Main {

    public static void main(String[] args) {

        // ========== オブジェクトの作成 ==========
        // new クラス名(引数) でオブジェクト（インスタンス）を作る
        // Dog型の変数に代入する
        //
        // System.out.println("--- オブジェクト作成 ---");
        // Dog dog1 = new Dog("ポチ", "柴犬", 3);        // Dogオブジェクト1つ目
        // Dog dog2 = new Dog("マロン", "トイプードル", 5); // Dogオブジェクト2つ目
        // // ↑ それぞれ別のオブジェクト。名前も犬種も年齢も別々に持つ

        // ========== メソッドの呼び出し ==========
        // オブジェクト.メソッド名() で呼び出す
        //
        // dog1.introduce();    // dog1のintroduceが実行される
        // dog1.bark();         // dog1のbarkが実行される
        //
        // dog2.introduce();    // dog2のintroduceが実行される（dog1とは別の値が表示される）
        // dog2.bark();

        // ========== オブジェクト同士の比較 ==========
        // メソッドに別のオブジェクトを引数として渡せる
        //
        // System.out.println("\n--- 比較 ---");
        // if (dog1.isOlderThan(dog2)) {
        //     System.out.println(dog1.name + "の方が年上です。");
        // } else {
        //     System.out.println(dog2.name + "の方が年上です。");
        // }

        // ========== フィールドへの直接アクセス ==========
        // オブジェクト.フィールド名 で値を読み書きできる
        // ※ 本来は private にして getter/setter を使うのが良い習慣（応用で学ぶ）
        //
        // System.out.println("\n--- フィールドアクセス ---");
        // System.out.println(dog1.name + "の年齢: " + dog1.age);
        // dog1.age = 4;  // フィールドの値を直接変更
        // System.out.println(dog1.name + "の新しい年齢: " + dog1.age);

        // ========== 配列にオブジェクトを入れる ==========
        // オブジェクトも配列に入れられる
        // ループで全オブジェクトに同じ処理ができて便利
        //
        // System.out.println("\n--- オブジェクトの配列 ---");
        // Dog[] dogs = {dog1, dog2, new Dog("ハチ", "秋田犬", 7)};
        // for (Dog dog : dogs) {
        //     dog.introduce();
        // }

        // ===== 練習問題 =====
        // 1. Studentクラスを新しいファイルで作ってみよう
        //    フィールド: 名前(String)、学年(int)、得意科目(String)
        //    コンストラクタ: 全フィールドを初期化
        // 2. Studentクラスに自己紹介メソッドを作ってみよう
        // 3. Student配列を作り、全員の自己紹介を表示してみよう
    }
}
