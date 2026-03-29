package lesson01_hello;
// ↑ パッケージ宣言: このファイルが属するフォルダ（グループ）を示す
//   lesson01_helloフォルダの中にあることを意味する

/**
 * ===== レッスン01: Hello World =====
 *
 * 【目標】Javaプログラムの基本構造を理解し、文字を画面に表示する
 *
 * 【Javaプログラムの構造】
 *   package 宣言;          ← ファイルの所属先
 *   public class クラス名 { ← クラス（プログラムの入れ物）
 *       public static void main(String[] args) { ← エントリーポイント（実行開始地点）
 *           処理;           ← ここに書いたコードが上から順に実行される
 *       }
 *   }
 *
 * 【重要ルール】
 * - クラス名とファイル名は一致させる（HelloWorld.java → class HelloWorld）
 * - 文の末尾にはセミコロン(;)が必要
 * - 大文字・小文字は区別される（System ≠ system）
 * - { } の対応を間違えないこと
 *
 * 【実行方法】IntelliJで右クリック → Run 'HelloWorld.main()'
 */

// ↓ クラス宣言: public = どこからでもアクセスできる、class = クラスの定義
//   ファイル名と同じ名前にする必要がある
public class HelloWorld {

    // ↓ mainメソッド: Javaプログラムの実行はここから始まる
    //   public        = どこからでも呼び出せる
    //   static        = クラスから直接呼び出せる（オブジェクト不要）
    //   void          = 戻り値がない（何も返さない）
    //   String[] args = コマンドライン引数（今は気にしなくてOK）
    public static void main(String[] args) {

        // ---------- println（プリントライン） ----------
        // System.out.println("文字列") → 文字列を表示して改行する
        //   System     = Javaの標準クラス
        //   out        = 標準出力（画面のこと）
        //   println    = print + line（改行付き表示）
        //   "..."      = ダブルクォートで囲んだものが文字列
        //
        // 下のコメントを外して実行してみよう:
        // System.out.println("Hello, World!");

        // ---------- 複数行の表示 ----------
        // println を複数回呼ぶと、1行ずつ表示される
        //
        // System.out.println("Javaの学習を始めましょう！");
        // System.out.println("私の名前はNaokiです。");

        // ---------- print（改行なし） ----------
        // System.out.print("文字列") → 文字列を表示するが改行しない
        // 次の print/println の出力が同じ行に続く
        //
        // System.out.print("これは");
        // System.out.print("つながって");
        // System.out.println("表示されます。");
        // ↑ 結果: 「これはつながって表示されます。」と1行で出る

        // ===== 練習問題 =====
        // 1. 自分の好きな食べ物を表示してみよう
        // 2. 3行の自己紹介を表示してみよう
    }
}
