package lesson35_io_streams;

// java.io パッケージをワイルドカードでインポートする。
// このパッケージにはファイルやストリームの読み書きに必要な全クラスが含まれる。
// 具体的には FileInputStream, FileOutputStream, BufferedReader, BufferedWriter,
// FileReader, FileWriter, PrintWriter, ObjectInputStream, ObjectOutputStream,
// IOException, Serializable などが含まれる。
// ワイルドカード (*) を使うとパッケージ内の全クラスが利用可能になるが、
// 実行時にはコンパイラが必要なクラスだけを解決するので性能に影響はない。
import java.io.*;

/**
 * ===== レッスン35: 入出力ストリーム (I/O Streams) =====
 *
 * 【目標】バイトストリームと文字ストリームの違いと使い方を学ぶ
 *
 * 【Javaの入出力の体系】
 *
 *   ■ バイトストリーム（1バイト単位） — 画像、音声、バイナリファイル
 *     InputStream  → FileInputStream, BufferedInputStream, ByteArrayInputStream
 *     OutputStream → FileOutputStream, BufferedOutputStream
 *
 *   ■ 文字ストリーム（文字単位） — テキストファイル
 *     Reader → FileReader, BufferedReader, InputStreamReader
 *     Writer → FileWriter, BufferedWriter, OutputStreamWriter, PrintWriter
 *
 *   ■ オブジェクトストリーム — Javaオブジェクトの直列化(Serialization)
 *     ObjectInputStream / ObjectOutputStream
 *
 * 【バイト vs 文字】
 *   バイト: 0〜255の数値。画像・動画・圧縮ファイルなど。
 *   文字:  文字コード(UTF-8等)を考慮した読み書き。テキストファイルに。
 *
 * 【Buffered の意味】
 *   バッファ(メモリ上の一時領域)にまとめて読み書きすることで高速化。
 *   FileReader/Writer を直接使うより BufferedReader/Writer で包むのが定石。
 *
 * 【PrintWriter】
 *   println(), printf() が使える便利なWriter。
 *   System.out と同じ感覚でファイルに出力できる。
 *
 * 【Serialization（直列化）】
 *   Javaオブジェクトをバイト列に変換して保存/送信する仕組み。
 *   Serializable インターフェースを実装したクラスのみ対象。
 */
public class IOStreamsLesson {

    public static void main(String[] args) {

        // ==========================================================
        // ■ セクション1: バイトストリーム (Byte Streams)
        // ==========================================================
        // バイトストリームは、データを1バイト（8ビット、0〜255）単位で読み書きする。
        // テキストだけでなく、画像・音声・動画・圧縮ファイルなどの
        // あらゆるバイナリデータを扱える最も基本的なストリーム。
        // 基底クラスは InputStream（読み取り）と OutputStream（書き込み）。

        // ---------- ファイルにバイトを書き込む ----------
        // FileOutputStream は、指定したファイルにバイト単位でデータを書き込むクラス。
        // ファイルが存在しない場合は新規作成され、既に存在する場合は上書きされる。
        // ※ 追記モードにしたい場合は new FileOutputStream("bytes.dat", true) のように
        //   第2引数に true を渡す。

        // try-with-resources 構文を使用している。
        // try の () 内で宣言したリソース（ここでは FileOutputStream）は、
        // try ブロック終了時に自動的に close() が呼ばれる。
        // これにより、例外が発生してもリソースリーク（閉じ忘れ）を防げる。
        // Java 7 以前は finally ブロックで手動 close する必要があったが、
        // try-with-resources により安全かつ簡潔に書けるようになった。

        // try (FileOutputStream fos = new FileOutputStream("bytes.dat")) {
        //     // write(int) メソッドは、引数の下位8ビット（0〜255）をファイルに書き込む。
        //     // ここでは ASCII コード 72 を書き込んでいる。
        //     // ASCII 72 は大文字の 'H' に対応する。
        //     // ASCII（American Standard Code for Information Interchange）は
        //     // 英数字・記号を 0〜127 の数値に対応させた文字コード規格。
        //     fos.write(72);   // 'H' のASCIIコード — 1バイト（0x48）をファイルに書き込む
        //
        //     // ASCII 73 は大文字の 'I' に対応する。
        //     // つまりここまでで "HI" の2バイトがファイルに書き込まれた状態。
        //     fos.write(73);   // 'I' のASCIIコード — 1バイト（0x49）をファイルに書き込む
        //
        //     // write(byte[]) メソッドは、バイト配列の全要素を一度に書き込む。
        //     // ここでは {33, 10} を書き込んでいる。
        //     // ASCII 33 = '!' (感嘆符)、ASCII 10 = '\n' (改行文字、LF: Line Feed)。
        //     // byte 配列リテラルは new byte[]{値1, 値2, ...} の形式で作成する。
        //     // 配列を使うことで複数バイトをまとめて書き込め、効率が良い。
        //     fos.write(new byte[]{33, 10});  // '!' と 改行 — 2バイトを一括で書き込む
        //
        //     // ここまでで "HI!\n" の4バイトがファイルに書き込まれた。
        //     System.out.println("バイト書き込み完了");
        //
        // } catch (IOException e) {
        //     // IOException はファイルの読み書き中に発生する例外の親クラス。
        //     // ファイルが見つからない（FileNotFoundException）、ディスク容量不足、
        //     // アクセス権限がないなど、様々な I/O エラーがこの例外でキャッチされる。
        //     // e.printStackTrace() はエラーの詳細（スタックトレース）をコンソールに出力する。
        //     // 本番環境ではログライブラリ（Log4j, SLF4J等）を使うのが望ましい。
        //     e.printStackTrace();
        // }

        // ---------- ファイルからバイトを読み取る ----------
        // FileInputStream は、指定したファイルからバイト単位でデータを読み取るクラス。
        // ファイルが存在しない場合は FileNotFoundException がスローされる。
        // 大きなファイルを効率よく読むには BufferedInputStream で包むのが望ましい。

        // try (FileInputStream fis = new FileInputStream("bytes.dat")) {
        //     // read() の戻り値を格納する変数。int 型を使う理由は、
        //     // read() が 0〜255 のバイト値に加え、ファイル末尾を示す -1 を返すため。
        //     // byte 型（-128〜127）では -1 と実データの区別ができないので int を使う。
        //     int b;
        //     System.out.print("バイト読み取り: ");
        //
        //     // fis.read() は1バイトを読み取り、0〜255 の int 値として返す。
        //     // ファイルの末尾に到達すると -1 を返す。
        //     // while の条件式の中で代入 (b = fis.read()) と比較 (!= -1) を同時に行う
        //     // イディオム（定型パターン）。Java の I/O プログラミングで非常によく使われる。
        //     // ループの各反復で1バイトずつ読み取り、-1 になったらループを終了する。
        //     while ((b = fis.read()) != -1) {  // -1 = ファイル末尾（EOF: End Of File）
        //         // 読み取った int 値を char にキャストして文字として出力する。
        //         // (char) 72 → 'H', (char) 73 → 'I', (char) 33 → '!' のように変換される。
        //         // これは ASCII 範囲の文字に対してのみ正しく動作する。
        //         // 日本語のようなマルチバイト文字はバイトストリームでは正しく扱えない。
        //         // 日本語を含むテキストには文字ストリーム（Reader/Writer）を使うべき。
        //         System.out.print((char) b);
        //     }
        //     // ループ終了後に改行を出力して、出力を整える。
        //     System.out.println();
        //
        // } catch (IOException e) {
        //     // ファイルが見つからない場合やディスク読み取りエラーの場合にここに来る。
        //     // FileNotFoundException は IOException のサブクラスなので、ここでキャッチ可能。
        //     e.printStackTrace();
        // }

        // ==========================================================
        // ■ セクション2: 文字ストリーム（Buffered Character Streams）
        // ==========================================================
        // 文字ストリームは、データを文字（char、2バイト）単位で読み書きする。
        // 文字コード（UTF-8, Shift-JIS 等）を意識した変換を自動で行うため、
        // 日本語を含むテキストファイルの読み書きに適している。
        // 基底クラスは Reader（読み取り）と Writer（書き込み）。
        //
        // 【なぜ Buffered を使うのか？】
        // FileReader/FileWriter を直接使うと、1文字読み書きするたびに
        // OS にシステムコール（ディスクアクセス要求）が発行される。
        // これは非常に遅い。BufferedReader/BufferedWriter で包むと、
        // 内部バッファ（デフォルト 8192 文字）にまとめて読み書きするため、
        // システムコールの回数が大幅に減り、パフォーマンスが劇的に向上する。
        // 実務では常に Buffered 版を使うのが定石。

        // ---------- テキストファイルに書き込む ----------
        // BufferedWriter は FileWriter を包んで（ラップして）バッファリングを提供する。
        // これは「デコレータパターン」というデザインパターンの一例で、
        // 既存のオブジェクトに機能を追加する手法。Java の I/O ライブラリ全体が
        // このパターンで設計されている。

        // try (BufferedWriter bw = new BufferedWriter(new FileWriter("text.txt"))) {
        //     // write(String) メソッドで文字列をバッファに書き込む。
        //     // この時点ではまだファイルに物理的に書き込まれていない可能性がある。
        //     // バッファが満杯になるか、flush() や close() が呼ばれた時に
        //     // 実際のファイル書き込みが行われる（これを「フラッシュ」という）。
        //     // 日本語の文字列も正しく書き込める。エンコーディングは OS のデフォルト。
        //     // UTF-8 を明示したい場合は new FileWriter("text.txt", StandardCharsets.UTF_8) を使う。
        //     bw.write("こんにちは、Java！");
        //
        //     // newLine() は OS に応じた改行文字を出力するメソッド。
        //     // Windows なら "\r\n"（CR+LF）、macOS/Linux なら "\n"（LF）が出力される。
        //     // "\n" を直接書くよりも newLine() を使う方がプラットフォーム非依存で安全。
        //     bw.newLine();
        //
        //     // 2行目のテキストをバッファに書き込む。
        //     bw.write("ストリームの学習中です。");
        //
        //     // 書き込み完了メッセージを標準出力（コンソール）に表示。
        //     // この println は System.out への出力であり、ファイルへの書き込みではない。
        //     System.out.println("テキスト書き込み完了");
        //
        // } catch (IOException e) {
        //     // ファイル作成失敗、ディスク容量不足、書き込み権限がない場合などにキャッチされる。
        //     e.printStackTrace();
        // }
        // ※ try-with-resources により bw.close() が自動呼出しされ、
        //   バッファの残りデータもファイルにフラッシュされる。

        // ---------- テキストファイルを読み込む ----------
        // BufferedReader は FileReader を包んでバッファリングを提供するとともに、
        // readLine() メソッドで1行ずつテキストを読み取る便利な機能を提供する。

        // try (BufferedReader br = new BufferedReader(new FileReader("text.txt"))) {
        //     // readLine() の戻り値を格納する変数。
        //     // readLine() は改行文字を含まない1行分の文字列を返す。
        //     // ファイル末尾に到達すると null を返す。
        //     String line;
        //     System.out.println("--- テキスト読み取り ---");
        //
        //     // readLine() を呼び出して1行読み取り、null でなければループを続ける。
        //     // このパターンはバイトストリームの read() != -1 と同様の定型パターン。
        //     // null はファイルの終わり（EOF）を意味する。
        //     // 空行の場合は null ではなく空文字列 "" が返される点に注意。
        //     while ((line = br.readLine()) != null) {
        //         // 読み取った行を先頭にスペース2つ付きで出力する（見やすさのため）。
        //         System.out.println("  " + line);
        //     }
        //
        // } catch (IOException e) {
        //     // ファイルが存在しない、読み取り権限がない、エンコーディングが不正な場合など。
        //     e.printStackTrace();
        // }

        // ==========================================================
        // ■ セクション3: PrintWriter（便利な出力クラス）
        // ==========================================================
        // PrintWriter は Writer のサブクラスで、println(), printf(), print() など
        // System.out と同じメソッドを使ってファイルに書き込める便利なクラス。
        // フォーマット出力（printf）が使えるので、整形されたテキスト出力に最適。
        // また、PrintWriter は内部で IOException を自動でキャッチするため、
        // メソッド呼び出し側で try-catch が不要（ただし例外を見逃す可能性あり）。
        // checkError() メソッドでエラーの有無を確認できる。

        // try (PrintWriter pw = new PrintWriter(new FileWriter("print.txt"))) {
        //     // println(String) は文字列の後に改行を付けてファイルに書き込む。
        //     // System.out.println() と完全に同じ使用感でファイル出力ができる。
        //     pw.println("名前: 田中");
        //
        //     // printf(String, Object...) はC言語由来の書式付き出力メソッド。
        //     // %d は整数のプレースホルダ（95 が代入される）。
        //     // %n はプラットフォーム非依存の改行文字（Windows: \r\n, macOS/Linux: \n）。
        //     // %s は文字列、%f は浮動小数点数、%b はブール値のプレースホルダ。
        //     // 書式文字列は String.format() と同じ仕様。
        //     pw.printf("スコア: %d点%n", 95);
        //
        //     // もう1行書き込み。PrintWriter の便利さを示すメッセージ。
        //     pw.println("PrintWriter は System.out と同じ感覚で使える");
        //
        //     System.out.println("PrintWriter書き込み完了");
        //
        // } catch (IOException e) {
        //     // この catch は FileWriter のコンストラクタでの例外をキャッチする。
        //     // PrintWriter の println/printf 自体は IOException を投げない。
        //     e.printStackTrace();
        // }

        // ==========================================================
        // ■ セクション4: オブジェクトの直列化 (Serialization)
        // ==========================================================
        // 直列化（シリアライゼーション）とは、Javaオブジェクトのフィールド値を
        // バイト列に変換してファイルやネットワークに書き出す仕組み。
        // 逆に、バイト列からオブジェクトを復元することを「復直列化（デシリアライゼーション）」という。
        //
        // 【用途】
        //   - オブジェクトをファイルに保存して永続化する
        //   - ネットワーク経由でオブジェクトを送受信する（RMI等）
        //   - セッション情報の保存（Tomcatのセッション永続化等）
        //
        // 【注意点】
        //   - Serializable インターフェースを実装したクラスのみ直列化可能
        //   - serialVersionUID を明示的に定義しないと、クラス変更時に互換性問題が発生する
        //   - transient キーワードを付けたフィールドは直列化の対象外になる
        //     （パスワードや一時データなど、保存したくないフィールドに使う）
        //   - セキュリティ上、信頼できないデータの復直列化は危険
        //     （悪意あるオブジェクトが復元される可能性がある）

        // // --- オブジェクトの書き込み（直列化） ---
        // // ObjectOutputStream はオブジェクトをバイト列に変換して出力するストリーム。
        // // FileOutputStream と組み合わせてファイルにオブジェクトを保存する。
        // // 拡張子 .ser は Serialized の略で慣習的に使われるが、任意でよい。
        // try (ObjectOutputStream oos = new ObjectOutputStream(
        //         new FileOutputStream("user.ser"))) {
        //
        //     // UserData クラスのインスタンスを作成。
        //     // このクラスは Serializable を実装しているため直列化可能。
        //     // コンストラクタに名前と年齢を渡してオブジェクトを初期化する。
        //     UserData user = new UserData("田中", 25);
        //
        //     // writeObject() メソッドでオブジェクトをバイト列に変換してファイルに書き込む。
        //     // オブジェクトの全フィールド（transient 以外）が自動的に書き込まれる。
        //     // オブジェクトが参照している他のオブジェクトも再帰的に直列化される。
        //     // 書き込まれるデータにはクラスの情報（名前、serialVersionUID等）も含まれる。
        //     oos.writeObject(user);
        //
        //     System.out.println("\nオブジェクト書き込み完了");
        //
        // } catch (IOException e) {
        //     // NotSerializableException（Serializable 未実装の場合）もここでキャッチされる。
        //     e.printStackTrace();
        // }
        //
        // // --- オブジェクトの読み込み（復直列化） ---
        // // ObjectInputStream はバイト列からオブジェクトを復元するストリーム。
        // // FileInputStream と組み合わせてファイルからオブジェクトを読み込む。
        // try (ObjectInputStream ois = new ObjectInputStream(
        //         new FileInputStream("user.ser"))) {
        //
        //     // readObject() はファイルから読み取ったバイト列をオブジェクトに復元する。
        //     // 戻り値は Object 型なので、適切な型にキャスト（型変換）する必要がある。
        //     // (UserData) のキャストにより、Object → UserData に変換される。
        //     // クラスの定義がコンパイル時と異なる場合（フィールド追加・削除など）、
        //     // InvalidClassException が発生する。これを防ぐために serialVersionUID を定義する。
        //     UserData loaded = (UserData) ois.readObject();
        //
        //     // 復元したオブジェクトの toString() メソッドが呼ばれ、内容が表示される。
        //     System.out.println("読み込み: " + loaded);
        //
        // } catch (IOException | ClassNotFoundException e) {
        //     // IOException: ファイル読み取りエラー
        //     // ClassNotFoundException: 復元先のクラス（UserData）がクラスパスに見つからない場合。
        //     // Java 7 以降のマルチキャッチ構文で、2種類の例外を1つの catch で処理。
        //     // | で区切ることで複数の例外型をまとめてキャッチできる。
        //     e.printStackTrace();
        // }

        // ===== 練習問題 =====
        // 1. テキストファイルをコピーするプログラムを書いてみよう
        //    ヒント: BufferedReader で1行ずつ読み取り、BufferedWriter で書き込む
        // 2. CSV形式で商品データを書き込み・読み込みするプログラムを書いてみよう
        //    ヒント: PrintWriter で "商品名,価格,個数" の形式で書き込み、
        //           BufferedReader で読み込んで split(",") で分割する
    }
}

// ==========================================================
// ■ 直列化用のデータクラス（Serializable 実装）
// ==========================================================
// Serializable はメソッドを持たない「マーカーインターフェース」で、
// 「このクラスのオブジェクトは直列化してよい」ということをJVMに伝える役割を持つ。
// Serializable を実装していないクラスを ObjectOutputStream.writeObject() に渡すと
// NotSerializableException がスローされる。

// class UserData implements Serializable {
//     // serialVersionUID はクラスのバージョンを識別する一意な番号。
//     // 直列化データを復元する際、書き込み時と読み込み時の serialVersionUID が
//     // 一致するかどうかで互換性がチェックされる。
//     // 一致しない場合は InvalidClassException がスローされる。
//     // 明示的に定義しないと、JVMがクラス構造から自動計算するが、
//     // コンパイラやフィールドの変更で値が変わる可能性があるため、
//     // 常に明示的に定義するのがベストプラクティス。
//     // 値は 1L など任意の long 値でよい。クラスを変更したら値を更新する。
//     private static final long serialVersionUID = 1L;  // バージョン管理用の固有ID
//
//     // name フィールド — ユーザーの名前を保持する。
//     // String は Serializable を実装しているので問題なく直列化される。
//     String name;
//
//     // age フィールド — ユーザーの年齢を保持する。
//     // プリミティブ型（int, double 等）は自動的に直列化される。
//     int age;
//
//     // コンストラクタ — 名前と年齢を受け取ってフィールドを初期化する。
//     // this.name は「このオブジェクトの name フィールド」を指し、
//     // 引数の name と区別するために this を使う。
//     UserData(String name, int age) { this.name = name; this.age = age; }
//
//     // toString() をオーバーライドして、オブジェクトの内容を人間が読める文字列で返す。
//     // System.out.println(user) のように出力すると自動的に toString() が呼ばれる。
//     // @Override アノテーションは、親クラス（Object）のメソッドを上書きしていることを明示する。
//     // これにより、メソッド名のタイプミスなどをコンパイル時に検出できる。
//     @Override
//     public String toString() { return name + "(" + age + "歳)"; }
// }
