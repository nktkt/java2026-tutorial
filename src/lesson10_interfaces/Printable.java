package lesson10_interfaces;

/**
 * ===== レッスン10: インターフェースと抽象クラス =====
 *
 * 【インターフェースとは？】
 *   「何ができるか」を定義する契約書のようなもの。
 *   メソッドの名前と引数だけ書き、中身は書かない。
 *   implements で実装したクラスが中身を書く義務を負う。
 *
 * 【特徴】
 *   - メソッドの中身がない（「何をするか」だけ定義）
 *   - 1つのクラスが複数のインターフェースを実装できる
 *     class X implements Printable, Saveable { ... }
 *   - インターフェースの型で変数を宣言できる
 *     Printable p = new Circle(...);
 *
 * 【クラスとの違い】
 *   クラスの継承: 1つだけ（extends は1つ）
 *   インターフェース: いくつでも（implements は複数OK）
 */

// インターフェースの定義
// interface キーワードを使う（class ではない）
public interface Printable {

    // メソッドのシグネチャ（名前+引数+戻り値）だけ定義する
    // { } の中身は書かない。実装クラスが書く。
    //
    // void printInfo();
}
