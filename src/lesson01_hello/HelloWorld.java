package lesson01_hello;

/**
 * ===== レッスン01: Hello World =====
 *
 * Javaプログラムの基本構造を学びます。
 *
 * ポイント:
 * - すべてのJavaコードは「クラス」の中に書く
 * - プログラムは main メソッドから実行が始まる
 * - 文の末尾にはセミコロン(;)が必要
 * - System.out.println() で画面に文字を表示する
 *
 * 実行方法: IntelliJで右クリック → Run 'HelloWorld.main()'
 */
public class HelloWorld {

    public static void main(String[] args) {

        // 文字列を表示する（println = print line、改行付き）
        System.out.println("Hello, World!");

        // 複数行の表示
        System.out.println("Javaの学習を始めましょう！");
        System.out.println("私の名前はNaokiです。");

        // print は改行なしで表示する
        System.out.print("これは");
        System.out.print("つながって");
        System.out.println("表示されます。");

        // ===== 練習問題 =====
        // 1. 自分の好きな食べ物を表示してみよう
        // 2. 3行の自己紹介を表示してみよう
    }
}
