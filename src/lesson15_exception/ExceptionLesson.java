package lesson15_exception;

import java.util.Scanner;
// ↑ Scanner クラスをインポート。ユーザーからのキーボード入力を受け取るために必要。
//   java.util パッケージに含まれている。練習問題で使う可能性がある。

/**
 * ===== レッスン15: 例外処理 (Exception Handling) =====
 *
 * 【目標】エラーが起きてもプログラムが止まらないようにする方法を学ぶ
 *
 * 【例外とは？】
 *   実行中に起こるエラーのこと。例外が発生すると、処理されなければプログラムが停止する。
 *   コンパイル時ではなく「実行時」に発生するのがポイント。
 *   例外処理を書くことで、エラーが起きても適切に対応し、プログラムを続行できる。
 *
 * 【よくある例外】
 *   ArithmeticException          → 0で割り算した
 *   ArrayIndexOutOfBoundsException → 配列の範囲外にアクセスした
 *   NullPointerException         → nullのオブジェクトのメソッドを呼んだ
 *   NumberFormatException        → 文字列を数値に変換できなかった
 *   StringIndexOutOfBoundsException → 文字列の範囲外にアクセスした
 *
 * 【try-catch 構文】
 *   try {
 *       例外が起きるかもしれない処理;
 *   } catch (例外の型 変数名) {
 *       例外が起きたときの処理;
 *   } finally {
 *       例外の有無に関係なく必ず実行される処理;（省略可能）
 *   }
 *
 * 【throwsとthrow】
 *   throws: メソッド宣言に書く。「このメソッドは例外を投げるかも」
 *   throw:  自分で例外を発生させる。 throw new 例外("メッセージ");
 */
public class ExceptionLesson {

    public static void main(String[] args) {

        // ========== 例外なしだとプログラムが停止する ==========
        // まずは例外処理なしで何が起きるかを見てみよう。
        // try-catch を書かずに例外が発生すると、プログラムはその場で強制終了する。

        // System.out.println("--- 例外の例 ---");
        // ↑ セクションタイトルを出力して、今何のデモをしているかを示す

        // int a = 10;
        // ↑ 割り算の被除数（割られる数）を変数 a に代入する

        // int b = 0;
        // ↑ 割り算の除数（割る数）を変数 b に代入する。0を入れている。

        // // System.out.println(a / b);  // ← ArithmeticException で停止！
        // ↑ 10 / 0 を計算しようとしている。数学では0で割ることは未定義なので、
        //   Javaは ArithmeticException（算術例外）をスローしてプログラムが強制停止する。
        //   この行以降のコードは一切実行されない。
        //   【重要】二重コメントアウトされているのは、実行すると本当にクラッシュするため。
        //   試したいときは外側の // だけ外して実行してみよう。

        // ========== try-catch で例外をキャッチ ==========
        // try-catch を使えば、例外が発生しても「キャッチ」して処理を続行できる。
        // try ブロック内で例外が発生すると、残りの try 内コードはスキップされ、
        // 該当する catch ブロックにジャンプする。

        // System.out.println("\n--- try-catch ---");
        // ↑ \n は改行文字。見やすくするために空行を入れてからタイトルを出力する。

        // try {
        // ↑ try ブロックの開始。この中に「例外が起きるかもしれない処理」を書く。
        //   try は「試す」という意味。「この処理を試してみて、エラーが起きたら catch に飛ぶ」

        //     int result = a / b;
        //     ↑ a(10) / b(0) を計算しようとする。ここで ArithmeticException が発生する。
        //       例外が発生した瞬間、この行の残りの処理（result への代入）は行われず、
        //       すぐに catch ブロックへジャンプする。

        //     System.out.println("結果: " + result);  // ここは実行されない
        //     ↑ 上の行で例外が発生するため、この行には到達しない。
        //       try ブロック内で例外が起きると、それ以降の try 内のコードはすべてスキップされる。

        // } catch (ArithmeticException e) {
        // ↑ catch ブロック。try 内で ArithmeticException が発生したときだけ実行される。
        //   (ArithmeticException e) の e は例外オブジェクトを受け取る変数。
        //   e を通じて例外の詳細情報（メッセージやスタックトレース）を取得できる。
        //   変数名 e は慣習的な名前。exception の頭文字。

        //     // 例外が起きたらここに飛ぶ
        //     System.out.println("エラー: 0で割れません！");
        //     ↑ ユーザーに分かりやすいエラーメッセージを表示する。
        //       実際のアプリケーションでは、ログに記録したり、代替処理を行ったりする。

        //     System.out.println("例外メッセージ: " + e.getMessage());
        //     ↑ e.getMessage() で例外に含まれるメッセージ文字列を取得する。
        //       ArithmeticException の場合、"/ by zero" というメッセージが返される。
        //       デバッグ時に原因を特定するのに役立つ。

        // }
        // ↑ catch ブロックの終了。

        // System.out.println("プログラムは続行します。");
        // ↑ try-catch の後なので、例外が発生してもこの行は必ず実行される。
        //   これが try-catch の最大のメリット: プログラムが停止しない。

        // // ↑ try-catch があるので、プログラムは止まらない
        // ↑ 補足コメント。try-catch がなければ ArithmeticException でプログラムが停止するが、
        //   try-catch で例外を処理したので、プログラムはその後も正常に動き続ける。

        // ========== finally（必ず実行される） ==========
        // finally ブロックは、例外が発生してもしなくても「必ず」実行される特別なブロック。
        // 主な用途: ファイルやデータベース接続などのリソースを確実に閉じるため。
        // 省略可能だが、リソース管理では非常に重要。

        // System.out.println("\n--- finally ---");
        // ↑ セクションタイトルを出力。

        // try {
        // ↑ try ブロック開始。配列の範囲外アクセスを試みる。

        //     int[] arr = {1, 2, 3};
        //     ↑ 要素が3つの int 配列を作成。インデックスは 0, 1, 2 の範囲が有効。
        //       arr[0]=1, arr[1]=2, arr[2]=3 となる。

        //     System.out.println(arr[5]);  // 範囲外アクセス
        //     ↑ arr[5] はインデックス5にアクセスしようとしている。
        //       配列の有効インデックスは 0〜2 なので、5 は範囲外。
        //       ArrayIndexOutOfBoundsException が発生する。
        //       【よくあるミス】配列の長さが3なら、最大インデックスは2（長さ-1）。

        // } catch (ArrayIndexOutOfBoundsException e) {
        // ↑ 配列の範囲外アクセス例外をキャッチする。

        //     System.out.println("エラー: 配列の範囲外です！");
        //     ↑ ユーザーに配列の範囲外アクセスが起きたことを知らせる。

        // } finally {
        // ↑ finally ブロック開始。try-catch の結果に関係なく、必ず実行される。
        //   例外が発生しても、発生しなくても、catch で別の例外が起きても実行される。

        //     System.out.println("finallyブロック: 必ず実行される");
        //     ↑ finally が確実に実行されることを確認するための出力。

        //     // ファイルを閉じる、リソースを解放するなどに使う
        //     ↑ 実際の開発での finally の主な使い道:
        //       - ファイルの close() を呼ぶ
        //       - データベース接続を切断する
        //       - ネットワーク接続を閉じる
        //       - 一時ファイルを削除する
        //       これらは例外が起きても起きなくても必ず行うべき処理。

        // }

        // ========== 複数のcatch ==========
        // 1つの try に対して複数の catch を書ける。
        // 発生した例外の型に一致する catch ブロックが実行される。
        // 【重要】catch は上から順に評価されるので、具体的な例外を先に、
        //         広い例外（Exception）を後に書く。逆にするとコンパイルエラーになる。

        // System.out.println("\n--- 複数のcatch ---");
        // ↑ セクションタイトルを出力。

        // try {
        // ↑ try ブロック開始。null に対する操作を試みる。

        //     String str = null;
        //     ↑ str に null を代入。null は「何も参照していない」状態。
        //       オブジェクトが存在しないことを表す特別な値。

        //     System.out.println(str.length());  // NullPointerException
        //     ↑ null の str に対して length() メソッドを呼ぼうとしている。
        //       null には実体がないのでメソッドを呼べず、NullPointerException が発生する。
        //       【実務で最も多いバグの一つ】データベースやAPIから取得した値が null の場合に起きやすい。

        // } catch (NullPointerException e) {
        // ↑ NullPointerException を具体的にキャッチする。
        //   この catch が最初に評価され、例外の型が一致すればここが実行される。

        //     System.out.println("エラー: nullです！");
        //     ↑ NullPointerException が起きたことを知らせる。

        // } catch (Exception e) {
        // ↑ Exception は全ての例外の親クラス（スーパークラス）。
        //   上の catch (NullPointerException) に該当しなかった例外はすべてここでキャッチされる。
        //   「セーフティネット」の役割。Exception を最後に書くのが定石。
        //   【注意】Exception を最初に書くと、後の具体的な catch に到達できずコンパイルエラーになる。

        //     // Exception は全例外の親クラス。上のcatchに該当しない例外をここでキャッチ
        //     System.out.println("その他のエラー: " + e.getMessage());
        //     ↑ 想定外の例外が発生した場合のメッセージを表示する。
        //       e.getMessage() で例外の詳細メッセージを取得。

        // }

        // ========== 文字列→数値の変換エラー ==========
        // Integer.parseInt() は文字列を int に変換するメソッドだが、
        // 数値として解釈できない文字列を渡すと NumberFormatException が発生する。
        // ユーザー入力を数値に変換する場面では必ず try-catch で囲むべき。

        // System.out.println("\n--- NumberFormatException ---");
        // ↑ セクションタイトルを出力。

        // String input = "abc";
        // ↑ 数値に変換できない文字列 "abc" を用意する。
        //   "123" なら変換できるが、"abc" は数値ではないので変換に失敗する。

        // try {
        // ↑ 変換処理を try で囲む。失敗する可能性があるため。

        //     int num = Integer.parseInt(input);
        //     ↑ Integer.parseInt() は文字列を int 型に変換する static メソッド。
        //       "abc" は数値に変換できないので NumberFormatException が発生する。
        //       "123" なら int の 123 が返される。" 123 " のように空白があっても失敗する。

        //     System.out.println("変換成功: " + num);
        //     ↑ 変換が成功した場合にのみ実行される。今回は上の行で例外が出るので到達しない。

        // } catch (NumberFormatException e) {
        // ↑ 数値変換の失敗をキャッチする。ユーザー入力を処理する際の定番パターン。

        //     System.out.println("\"" + input + "\" は数値に変換できません！");
        //     ↑ どの文字列が変換できなかったかを表示する。
        //       \" はダブルクォートのエスケープ。出力: "abc" は数値に変換できません！

        // }

        // ========== throw で自分から例外を投げる ==========
        // throw キーワードを使うと、自分で例外を発生させることができる。
        // 不正な引数が渡されたときなど、「ここでエラーにすべき」という場面で使う。
        // メソッドの入り口でバリデーション（検証）を行い、不正なら throw するのが一般的。

        // System.out.println("\n--- throw ---");
        // ↑ セクションタイトルを出力。

        // try {
        // ↑ setAge() メソッドが例外を投げる可能性があるので try で囲む。

        //     setAge(-5);
        //     ↑ 年齢に -5 を設定しようとする。負の年齢は不正なので、
        //       setAge メソッド内で IllegalArgumentException が throw される。

        // } catch (IllegalArgumentException e) {
        // ↑ IllegalArgumentException（不正な引数の例外）をキャッチする。
        //   この例外は、メソッドに渡された引数が不正な場合に使う標準的な例外クラス。

        //     System.out.println("エラー: " + e.getMessage());
        //     ↑ throw 時に設定したメッセージ（"年齢は0以上にしてください: -5"）が表示される。
        //       e.getMessage() で throw new IllegalArgumentException("...") の "..." 部分が取れる。

        // }

        // ===== 練習問題 =====
        // 1. ユーザーから数値を入力してもらい、数値以外が入力されたら
        //    「数値を入力してください」と表示して再入力を促すプログラムを書こう
        //    【ヒント】while ループの中で try-catch を使い、正しい数値が入力されるまで繰り返す
        //    【ヒント】Scanner の nextLine() で文字列を取得し、Integer.parseInt() で変換する
        //
        // 2. 配列の要素にアクセスするプログラムで、範囲外アクセスをキャッチしてみよう
        //    【ヒント】ユーザーにインデックスを入力させ、配列の範囲外なら catch する
    }

    // === throw の例: 不正な値を検出して例外を発生させるメソッド ===

    // static void setAge(int age) {
    // ↑ static メソッドとして定義。main から直接呼べるようにするため static にしている。
    //   戻り値は void（何も返さない）。引数として年齢（int age）を受け取る。

    //     if (age < 0) {
    //     ↑ 引数のバリデーション（検証）。年齢が 0 未満なら不正な値。
    //       メソッドの最初に引数を検証するのは「防御的プログラミング」と呼ばれる良い習慣。

    //         throw new IllegalArgumentException("年齢は0以上にしてください: " + age);
    //         ↑ throw キーワードで例外オブジェクトを「投げる」。
    //           new IllegalArgumentException(...) で新しい例外オブジェクトを作成する。
    //           引数の文字列はエラーメッセージ。getMessage() で取得できる。
    //           IllegalArgumentException は「不正な引数」を表す標準例外。
    //           throw された後、このメソッドの残りのコードは実行されず、
    //           呼び出し元の catch ブロックに処理が移る。
    //         // ↑ 不正な引数が来たら例外を投げる

    //     }

    //     System.out.println("年齢を" + age + "に設定しました。");
    //     ↑ age が 0 以上の場合のみ、ここに到達する。
    //       正常な値が渡されたことを確認するメッセージを表示する。
    //       実際のアプリでは、ここでフィールドに値を代入する処理を書く。

    // }
}
