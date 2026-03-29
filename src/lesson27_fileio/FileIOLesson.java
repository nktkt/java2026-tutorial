package lesson27_fileio;

// java.ioパッケージ: Javaの従来型I/O（入出力）クラス群をまとめてインポートする
// IOException, BufferedReader, BufferedWriter などが含まれる
// 「.*」はワイルドカードで、このパッケージ内の全クラスを一括インポートする意味
import java.io.*;

// java.nio.fileパッケージ: Java 7以降の「New I/O（NIO）」ファイル操作クラス群
// Path（ファイルパスを表すオブジェクト）やFiles（ファイル操作ユーティリティ）が含まれる
// 従来のjava.ioよりも使いやすく、機能が豊富で推奨されている
import java.nio.file.*;

// java.util.Listインターフェース: 順序付きコレクション（リスト）を扱うためのインターフェース
// Files.readAllLines()がList<String>を返すため、ここでインポートが必要
import java.util.List;

/**
 * ===== レッスン27: ファイルI/O と try-with-resources =====
 *
 * 【目標】ファイルの読み書きとリソースの安全な管理を学ぶ
 *
 * 【try-with-resources】
 *   try (リソース宣言) { ... }  → ブロック終了時に自動でclose()
 *   AutoCloseable を実装したクラスに使える（Reader, Writer, Stream等）
 *
 * 【Files クラス（java.nio.file）】簡単なファイル操作
 *   Files.readAllLines(path)      → 全行をList<String>で読む
 *   Files.readString(path)        → 全文を1つのStringで読む
 *   Files.write(path, lines)      → List<String>を書き込む
 *   Files.writeString(path, str)  → 文字列を書き込む
 *   Files.exists(path)            → ファイルが存在するか
 *
 * 【BufferedReader/Writer】大きなファイル向け
 *   バッファリングにより効率的に読み書きする。
 *
 * 【なぜファイルI/Oが重要か？】
 *   プログラムが終了してもデータを残したい場合（永続化）にファイルが必要。
 *   ログ出力、設定ファイルの読み込み、CSVデータの処理など実務で頻繁に使う。
 *
 * 【よくあるミス】
 *   1. close()を忘れてリソースリークが起きる → try-with-resourcesで防げる
 *   2. ファイルパスの指定間違い（相対パス vs 絶対パス）
 *   3. 文字コードの不一致（UTF-8想定がShift_JISだった等）
 *   4. IOException のハンドリング漏れ（検査例外なので必ずcatchかthrowsが必要）
 */
public class FileIOLesson {

    public static void main(String[] args) {

        // ---------- ファイルパスの作成 ----------

        // Path.of("test_output.txt") で Pathオブジェクトを生成する
        // "test_output.txt" は相対パスなので、プログラム実行時のカレントディレクトリ基準になる
        // Path はファイルの場所を表すオブジェクトで、java.nio.file パッケージの中心的存在
        // 【実務Tips】絶対パス（例: "/home/user/data.txt"）を使うと位置が明確になる
        // 【注意】Path.of() は Java 11 以降で使用可能。Java 10以前は Paths.get() を使う
        // Path filePath = Path.of("test_output.txt");

        // ---------- ファイル書き込み（Files.write を使った簡単な方法） ----------

        // try-catch ブロック: ファイル操作は失敗する可能性がある（ディスク満杯、権限なし等）ため
        // IOException（検査例外）が発生する可能性があり、必ず例外処理が必要
        // try {

        //     List.of("Hello", "Java", "File I/O") で不変リスト（変更不可のリスト）を生成する
        //     List.of() は Java 9 以降で使えるファクトリメソッドで、3つの文字列要素を持つリストを作る
        //     このリストの各要素が、ファイル内で1行ずつ書き込まれる
        //     List<String> lines = List.of("Hello", "Java", "File I/O");

        //     Files.write() は、指定されたPathにList<String>の各要素を1行ずつ書き込む
        //     ファイルが存在しない場合は新規作成し、存在する場合は上書きする
        //     デフォルトの文字コードは UTF-8 が使われる
        //     【内部動作】ファイルのオープン → 全行の書き込み → ファイルのクローズ を一括で行う
        //     【便利な点】リソース管理を Files クラスが自動で行ってくれるので close() 不要
        //     Files.write(filePath, lines);

        //     書き込みが成功したことを確認するメッセージを表示する
        //     filePath.toString() が自動的に呼ばれて "test_output.txt" と出力される
        //     System.out.println("書き込み完了: " + filePath);

        // } catch (IOException e) {
        //     IOException をキャッチして、エラーメッセージを表示する
        //     e.getMessage() はエラーの詳細メッセージを返す（例: "アクセスが拒否されました"）
        //     【実務Tips】本番コードではログフレームワーク（SLF4J等）でログに記録する
        //     System.out.println("書き込みエラー: " + e.getMessage());
        // }

        // ---------- ファイル読み込み（Files.readAllLines を使った簡単な方法） ----------

        // try {

        //     Files.readAllLines() はファイルの全行を List<String> として一括読み込みする
        //     各行が1つのString要素になる（改行文字は含まれない）
        //     デフォルトの文字コードは UTF-8
        //     【注意】巨大ファイル（数GB等）だとメモリ不足(OutOfMemoryError)になる危険がある
        //     巨大ファイルの場合は BufferedReader で1行ずつ処理する方が安全
        //     List<String> lines = Files.readAllLines(filePath);

        //     読み込んだ内容を表示するためのヘッダを出力する
        //     "\n" は改行文字で、前の出力との間に空行を入れて読みやすくしている
        //     System.out.println("\n--- 読み込み ---");

        //     拡張for文（for-each文）で、読み込んだ各行を1行ずつ処理する
        //     line 変数に List の各要素（=ファイルの各行）が順番に代入される
        //     for (String line : lines) {

        //         各行の先頭に "  "（スペース2つ）を付けてインデント表示する
        //         これは見やすくするためのフォーマット（必須ではない）
        //         System.out.println("  " + line);

        //     }

        // } catch (IOException e) {
        //     ファイルが存在しない、読み取り権限がない等の場合にここに来る
        //     FileNotFoundException は IOException のサブクラスなので、これ1つでキャッチできる
        //     System.out.println("読み込みエラー: " + e.getMessage());
        // }

        // ---------- try-with-resources（BufferedWriter での書き込み） ----------

        // 【try-with-resources とは？】
        // try の () 内でリソース（ファイルやDB接続など）を宣言すると、
        // ブロック終了時に自動的に close() が呼ばれる Java 7 以降の機能
        // これにより、close() の呼び忘れによるリソースリーク（メモリやファイルハンドルの枯渇）を防げる
        // 条件: リソースは AutoCloseable インターフェースを実装している必要がある

        // try (BufferedWriter writer = Files.newBufferedWriter(Path.of("buffered.txt"))) {

        //     Files.newBufferedWriter() はバッファリング機能付きのWriterを返す
        //     バッファリングとは: 書き込みデータを内部メモリに一時蓄積し、まとめてディスクに書く仕組み
        //     1文字ずつディスクに書くより圧倒的に高速（ディスクI/Oの回数が減る）
        //     Path.of("buffered.txt") で書き込み先のファイルパスを指定する

        //     writer.write("1行目") でバッファに "1行目" という文字列を書き込む
        //     この時点ではまだディスクに書かれていない可能性がある（バッファに溜めている）
        //     writer.write("1行目");

        //     writer.newLine() で OS に適した改行コードを追加する
        //     Windows: "\r\n"、Mac/Linux: "\n"
        //     "\n" をハードコードするより移植性が高い（OS間でプログラムが正しく動く）
        //     writer.newLine();

        //     2行目の内容をバッファに書き込む
        //     writer.write("2行目");

        //     ↑ try-with-resources のブロック終了時に自動で writer.close() が呼ばれる
        //     close() 時にバッファの残りデータがディスクにフラッシュ（書き出し）されてからクローズされる
        //     明示的に writer.flush() を呼ぶ必要はない（close()が内部でflush()する）
        //     例外が発生した場合でも、finally ブロック相当の仕組みで必ず close() される

        // } catch (IOException e) {
        //     e.printStackTrace() はエラーの詳細（スタックトレース）を標準エラー出力に表示する
        //     getMessage() より情報量が多く、どのメソッドのどの行でエラーが起きたか分かる
        //     【デバッグTips】開発中は printStackTrace() が便利。本番では適切なログ出力に変える
        //     e.printStackTrace();
        // }

        // ---------- try-with-resources（BufferedReader での読み込み） ----------

        // try (BufferedReader reader = Files.newBufferedReader(Path.of("buffered.txt"))) {

        //     Files.newBufferedReader() はバッファリング機能付きのReaderを返す
        //     大きなファイルを効率的に読み込むのに適している
        //     ここでもtry-with-resourcesにより、ブロック終了時にreader.close()が自動で呼ばれる

        //     読み込んだ1行を格納する変数を宣言する（初期値なし）
        //     ループ内で代入されるため、ここでは宣言のみ行う
        //     String line;

        //     reader.readLine() は1行を読み込んで String として返す
        //     ファイルの末尾に達すると null を返す → ループ終了の条件になる
        //     「(line = reader.readLine()) != null」は代入と比較を同時に行うイディオム
        //     ① reader.readLine() で1行読む → ② line に代入 → ③ null でないか判定
        //     【注意】readLine() は改行文字を含まない文字列を返す
        //     while ((line = reader.readLine()) != null) {

        //         読み込んだ各行をインデント付きで表示する
        //         System.out.println("  " + line);

        //     }

        // } catch (IOException e) {
        //     ファイルが存在しない、読み取り中にエラーが発生した場合のハンドリング
        //     e.printStackTrace();
        // }

        // ===== 練習問題 =====
        // 1. テキストファイルを読み込み、行数と単語数を数えてみよう
        //    ヒント: line.split("\\s+") で空白区切りで単語に分割できる
        //    ヒント: Files.readAllLines() で全行取得し、lines.size() で行数がわかる
        // 2. ファイルの内容を逆順（最後の行から最初の行）に表示してみよう
        //    ヒント: Collections.reverse(lines) でリストを逆順にできる
        //    ヒント: または for (int i = lines.size() - 1; i >= 0; i--) で逆順ループ
    }
}
