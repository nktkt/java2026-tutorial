package lesson36_network;

// java.io パッケージ — BufferedReader, InputStreamReader など、
// ネットワークから受信したデータをストリームとして読み取るために必要。
// URL.openStream() の戻り値は InputStream なので、これを Reader に変換して使う。
import java.io.*;

// java.net パッケージ — URL, URI などのネットワーク関連基本クラスを含む。
// URL はリソースの場所を示すクラス（例: "https://example.com"）。
// URI は URL の上位概念で、より厳密な構文解析を行う。
// Java 11 以降の HttpClient では URI を使うことが推奨されている。
import java.net.*;

// java.net.http パッケージ — Java 11 で導入された新しい HTTP クライアントAPI。
// HttpClient（HTTPリクエストの送信を管理）、HttpRequest（リクエストの構築）、
// HttpResponse（レスポンスの受信と解析）の3つの主要クラスを含む。
// 従来の HttpURLConnection よりもモダンで使いやすく、
// HTTP/2 や非同期通信にも対応している。
import java.net.http.*;

/**
 * ===== レッスン36: ネットワーク通信 =====
 *
 * 【目標】Javaでネットワーク通信（HTTP、ソケット）を行う方法を学ぶ
 *
 * 【HTTPの基本】
 *   Web の通信プロトコル。リクエストとレスポンスのやり取り。
 *
 *   リクエスト:
 *     GET  → データを取得（URLにパラメータ）
 *     POST → データを送信（ボディにパラメータ）
 *     PUT  → データを更新
 *     DELETE → データを削除
 *
 *   レスポンス:
 *     200 OK        → 成功
 *     404 Not Found → リソースが見つからない
 *     500 Internal Server Error → サーバーエラー
 *
 * 【Java の HTTP クライアント（Java 11+）】
 *   HttpClient   → HTTPリクエストを送るクライアント
 *   HttpRequest  → リクエストの内容（URL、メソッド、ヘッダー）
 *   HttpResponse → レスポンス（ステータスコード、ボディ）
 *
 * 【ソケット通信】
 *   TCP/IP で直接通信する低レベルAPI。
 *   ServerSocket → サーバー側（接続を待つ）
 *   Socket       → クライアント側（接続する）
 *
 * 【URL クラス】
 *   URL url = new URL("https://example.com");
 *   URLから直接ストリームを開いてデータを読める。
 */
public class NetworkLesson {

    public static void main(String[] args) {

        // ==========================================================
        // ■ セクション1: HttpClient（Java 11+ 推奨の HTTP 通信API）
        // ==========================================================
        // HttpClient は Java 11 で正式導入された最新の HTTP クライアントAPI。
        // それ以前は HttpURLConnection（Java 1.1〜）や Apache HttpClient（外部ライブラリ）
        // が主流だったが、HttpClient の登場により標準ライブラリだけでモダンな HTTP 通信が可能に。
        //
        // 【HttpClient の特徴】
        //   - HTTP/1.1 と HTTP/2 の両方をサポート
        //   - 同期（send）と非同期（sendAsync）の両方に対応
        //   - ビルダーパターンによる流暢な API 設計
        //   - リダイレクトの自動追従設定が可能
        //   - タイムアウトの設定が可能

        // ---------- GET リクエストの例 ----------
        // System.out.println("--- HttpClient ---");
        // try {
        //     // HttpClient.newHttpClient() でデフォルト設定のクライアントを作成する。
        //     // デフォルト設定: HTTP/2 優先、リダイレクト追従なし、タイムアウトなし。
        //     // カスタマイズしたい場合は HttpClient.newBuilder() を使う。
        //     // 例: HttpClient.newBuilder()
        //     //         .followRedirects(HttpClient.Redirect.NORMAL)
        //     //         .connectTimeout(Duration.ofSeconds(10))
        //     //         .build();
        //     // HttpClient は不変（immutable）でスレッドセーフなので、
        //     // アプリケーション全体で1つのインスタンスを共有して再利用するのが効率的。
        //     HttpClient client = HttpClient.newHttpClient();
        //
        //     // HttpRequest.newBuilder() でリクエストをビルダーパターンで構築する。
        //     // ビルダーパターンとは、メソッドチェーンでオブジェクトを段階的に構築する設計パターン。
        //     // 各メソッドが自分自身（ビルダー）を返すため、.method1().method2().build() と繋げて書ける。
        //     HttpRequest request = HttpRequest.newBuilder()
        //         // uri() でリクエスト先の URL を指定する。
        //         // URI.create() は文字列から URI オブジェクトを生成する静的ファクトリメソッド。
        //         // httpbin.org はHTTPリクエストのテスト用に公開されているサービスで、
        //         // /get エンドポイントは受信したリクエスト情報をJSON形式で返してくれる。
        //         // 開発やテスト時に非常に便利なサービス。
        //         .uri(URI.create("https://httpbin.org/get"))  // テスト用APIのURL
        //
        //         // header() でHTTPヘッダーを追加する。
        //         // "Accept" ヘッダーは、クライアントが受け取りたいレスポンスの形式を
        //         // サーバーに伝えるもの。"application/json" はJSON形式を要求している。
        //         // MIME タイプ（メディアタイプ）と呼ばれる形式で指定する。
        //         // 他の例: "text/html"（HTML）、"text/plain"（プレーンテキスト）、
        //         //         "application/xml"（XML）、"image/png"（PNG画像）
        //         .header("Accept", "application/json")
        //
        //         // GET() で HTTP メソッドを GET に設定する。
        //         // GET はリソースの取得に使われる最も基本的な HTTP メソッド。
        //         // ブラウザで URL にアクセスするのは全て GET リクエスト。
        //         // GET リクエストはボディを持たず、パラメータは URL のクエリ文字列
        //         // （?key=value&key2=value2）で渡す。
        //         // ※ GET() は省略可能（デフォルトが GET のため）。
        //         .GET()
        //
        //         // build() でリクエストオブジェクトを確定して生成する。
        //         // ビルダーパターンの最後に必ず呼ぶメソッド。
        //         .build();
        //
        //     // client.send() で同期的にリクエストを送信し、レスポンスを受け取る。
        //     // 第1引数: 送信するリクエストオブジェクト。
        //     // 第2引数: レスポンスボディの処理方法を指定する BodyHandler。
        //     // BodyHandlers.ofString() はレスポンスボディを String として受け取る指定。
        //     // 他の選択肢:
        //     //   BodyHandlers.ofByteArray()    → byte[] で受け取る（バイナリデータ用）
        //     //   BodyHandlers.ofFile(path)     → ファイルに直接保存する
        //     //   BodyHandlers.ofInputStream()  → InputStream で受け取る（大容量データ用）
        //     //   BodyHandlers.discarding()     → ボディを読み捨てる（ステータスコードだけ確認したい時）
        //     // send() は通信が完了するまでスレッドをブロック（待機）する同期メソッド。
        //     // 非同期で送信したい場合は sendAsync() を使い、CompletableFuture が返る。
        //     HttpResponse<String> response = client.send(
        //         request, HttpResponse.BodyHandlers.ofString());
        //
        //     // statusCode() でHTTPステータスコードを取得する。
        //     // 200 = OK（成功）、301 = リダイレクト、404 = Not Found、500 = サーバーエラー。
        //     // ステータスコードの範囲による分類:
        //     //   1xx: 情報（処理中）、2xx: 成功、3xx: リダイレクト、
        //     //   4xx: クライアントエラー、5xx: サーバーエラー。
        //     System.out.println("ステータス: " + response.statusCode());
        //
        //     System.out.println("ボディ(先頭200文字):");
        //     // body() でレスポンスボディ（本文）を取得する。
        //     // BodyHandlers.ofString() を指定したので、戻り値は String 型。
        //     String body = response.body();
        //
        //     // substring(0, min) でボディの先頭200文字だけを切り出して表示する。
        //     // Math.min() を使う理由は、ボディが200文字未満の場合に
        //     // StringIndexOutOfBoundsException（配列の範囲外アクセス）を防ぐため。
        //     // 短い方の値を上限にすることで安全に切り出せる。
        //     System.out.println(body.substring(0, Math.min(200, body.length())));
        //
        // } catch (Exception e) {
        //     // ここでキャッチされる可能性のある例外:
        //     // - IOException: ネットワーク接続エラー、タイムアウト等
        //     // - InterruptedException: 通信中にスレッドが中断された場合
        //     // - IllegalArgumentException: URI の形式が不正な場合
        //     // 実務では例外の種類ごとに適切な処理を分けるのが望ましい。
        //     System.out.println("エラー: " + e.getMessage());
        // }

        // ==========================================================
        // ■ セクション2: POST リクエスト
        // ==========================================================
        // POST メソッドは、サーバーにデータを送信する際に使われるHTTPメソッド。
        // GET とは異なり、データをリクエストボディに含めて送信する。
        // フォームの送信、APIへのデータ登録、ファイルのアップロードなどに使われる。
        //
        // 【GET vs POST の主な違い】
        //   GET:  データはURLのクエリパラメータに含まれる（?name=value）
        //         → ブラウザ履歴やサーバーログに残る → 機密データには不適切
        //         → URLの長さに制限がある（ブラウザにより異なるが約2000文字程度）
        //         → 同じリクエストを何度送っても結果が同じ（冪等性がある）
        //   POST: データはリクエストボディに含まれる
        //         → URLに表示されない → パスワード等の機密データに適する
        //         → データサイズの制限が緩い（サーバー設定次第）
        //         → 副作用がある操作（データの作成・変更）に使う

        // try {
        //     // HttpClient インスタンスを作成。GET の例と同様。
        //     // 実務では1つの HttpClient を使い回す方が効率的（コネクションプーリング）。
        //     HttpClient client = HttpClient.newHttpClient();
        //
        //     // 送信するJSONデータを文字列として定義する。
        //     // JSON（JavaScript Object Notation）はデータ交換に広く使われるフォーマット。
        //     // {"key": "value"} の形式で、ネストやリスト（配列）も表現できる。
        //     // 実務では Jackson や Gson などのライブラリを使ってオブジェクトから
        //     // 自動的にJSONに変換するのが一般的だが、ここでは理解のために手書きしている。
        //     String json = "{\"name\":\"Naoki\",\"age\":25}";
        //
        //     HttpRequest request = HttpRequest.newBuilder()
        //         // POST 先の URL を指定。httpbin.org/post はPOSTリクエストのテスト用エンドポイント。
        //         // 受信したリクエストの情報（ヘッダー、ボディ等）をJSON形式で返してくれる。
        //         .uri(URI.create("https://httpbin.org/post"))
        //
        //         // Content-Type ヘッダーで、送信するデータの形式をサーバーに伝える。
        //         // "application/json" は「ボディの内容はJSON形式です」という宣言。
        //         // サーバー側はこのヘッダーを見てデータの解釈方法を決定する。
        //         // 他の一般的な Content-Type:
        //         //   "application/x-www-form-urlencoded" — HTMLフォームのデフォルト
        //         //   "multipart/form-data" — ファイルアップロード時に使用
        //         //   "text/plain" — プレーンテキスト
        //         .header("Content-Type", "application/json")
        //
        //         // POST() で HTTP メソッドを POST に設定し、リクエストボディを指定する。
        //         // BodyPublishers.ofString(json) は文字列をリクエストボディとして送信する指定。
        //         // BodyPublishers は送信データの形式を指定するための静的メソッドを持つクラス。
        //         // 他の選択肢:
        //         //   BodyPublishers.ofByteArray(bytes)  → バイト配列を送信
        //         //   BodyPublishers.ofFile(path)        → ファイルの内容を送信
        //         //   BodyPublishers.noBody()            → ボディなし（DELETE等で使用）
        //         .POST(HttpRequest.BodyPublishers.ofString(json))
        //         .build();
        //
        //     // POST リクエストを同期送信し、レスポンスを String で受け取る。
        //     HttpResponse<String> response = client.send(
        //         request, HttpResponse.BodyHandlers.ofString());
        //
        //     // POSTリクエストのステータスコードを表示。
        //     // 成功時は通常 200（OK）または 201（Created: リソースが新規作成された）が返る。
        //     System.out.println("\nPOST ステータス: " + response.statusCode());
        //
        // } catch (Exception e) {
        //     System.out.println("エラー: " + e.getMessage());
        // }

        // ==========================================================
        // ■ セクション3: URL クラス（シンプルな HTTP 読み取り）
        // ==========================================================
        // URL クラスは Java 1.0 から存在する古いクラスだが、
        // シンプルにWebページの内容を読み取る用途ではまだ使える。
        // ただし HttpClient の方が機能が豊富で柔軟なので、新規開発では HttpClient 推奨。
        //
        // 【URL クラスの制限】
        //   - タイムアウト設定が面倒（URLConnection にキャストが必要）
        //   - ヘッダーの設定が不便
        //   - POST 送信が煩雑
        //   - エラーハンドリングが不十分
        //
        // 【InputStreamReader とは】
        //   バイトストリーム（InputStream）を文字ストリーム（Reader）に変換するブリッジクラス。
        //   ネットワークから受信するデータはバイト列なので、テキストとして読むには
        //   文字コードの変換が必要。InputStreamReader がこの変換を行う。
        //   さらに BufferedReader で包むことでバッファリングと readLine() 機能を追加する。

        // try {
        //     // URL オブジェクトを作成。引数の文字列がURLとして解析される。
        //     // "https://example.com" はIANA（Internet Assigned Numbers Authority）が
        //     // ドキュメント・テスト用に予約しているドメイン。常にアクセス可能。
        //     // MalformedURLException（不正なURL形式）がスローされる可能性がある。
        //     URL url = new URL("https://example.com");
        //
        //     // url.openStream() でURLの内容をストリームとして取得する。
        //     // これは内部的に url.openConnection().getInputStream() と同等。
        //     // HTTP GET リクエストが送信され、レスポンスボディが InputStream として返る。
        //     // InputStreamReader でバイト→文字変換し、BufferedReader で行単位読み取りを可能にする。
        //     // この3段階のラッピングは Java I/O の典型的なパターン（デコレータパターン）。
        //     BufferedReader reader = new BufferedReader(
        //         new InputStreamReader(url.openStream()));
        //
        //     String line;
        //     System.out.println("\n--- URLから読み取り ---");
        //
        //     // カウンター変数。出力行数を制限するために使用する。
        //     // Webページの全内容を出力すると大量の行が表示されるため、
        //     // 先頭5行だけを表示するように制限している。
        //     int count = 0;
        //
        //     // readLine() で1行ずつ読み取り、null（ストリーム終端）になるか
        //     // 5行に達したらループを終了する。
        //     // && は短絡論理AND演算子で、左辺が false なら右辺は評価されない。
        //     // つまり、readLine() が null を返したら count < 5 はチェックされずにループ終了。
        //     while ((line = reader.readLine()) != null && count < 5) {
        //         // 読み取った各行を先頭にインデント付きで出力する。
        //         System.out.println("  " + line);
        //         // カウンターをインクリメント（1増加）して出力行数を追跡する。
        //         count++;
        //     }
        //
        //     // reader を手動で閉じる。try-with-resources を使えば自動で閉じられるが、
        //     // ここでは明示的に close() を呼んでいる。
        //     // ネットワークストリームの閉じ忘れは、サーバー側のリソースも保持し続けるため、
        //     // ファイルストリーム以上にリソースリークの影響が大きい。
        //     // 【改善案】try-with-resources を使う方が安全で推奨される。
        //     reader.close();
        //
        // } catch (Exception e) {
        //     // ネットワーク接続失敗、DNS解決失敗、タイムアウト等でここに来る。
        //     // 実務ではリトライ（再試行）やフォールバック処理を実装することが多い。
        //     System.out.println("エラー: " + e.getMessage());
        // }

        // ===== 練習問題 =====
        // 1. 任意のURLにGETリクエストを送り、レスポンスを表示してみよう
        //    ヒント: HttpClient + HttpRequest.newBuilder().uri(...).GET().build()
        // 2. JSONデータをPOSTで送信し、レスポンスを確認してみよう
        //    ヒント: POST(HttpRequest.BodyPublishers.ofString(json)) を使う
    }
}
