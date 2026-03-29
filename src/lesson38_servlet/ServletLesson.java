package lesson38_servlet;

/**
 * ===== レッスン38: Servlet（Tomcatの基本） =====
 *
 * 【目標】JavaでWebアプリケーションを作る基礎を学ぶ
 *
 * 【Servletとは？】
 *   Javaで書かれたサーバーサイドプログラム。
 *   HTTPリクエストを受け取り、処理してHTTPレスポンスを返す。
 *   Apache Tomcat などのServletコンテナ上で動作する。
 *
 * 【Tomcatとは？】
 *   Servlet/JSP を実行するWebサーバー（Servletコンテナ）。
 *   Apache Software Foundation が開発。無料。
 *   Webアプリケーションのデプロイ先。
 *
 * 【Webアプリケーションの仕組み】
 *
 *   ブラウザ  →  HTTP リクエスト  →  Tomcat  →  Servlet
 *   ブラウザ  ←  HTTP レスポンス  ←  Tomcat  ←  Servlet
 *
 *   1. ブラウザが URL にアクセス（例: http://localhost:8080/hello）
 *   2. Tomcat がリクエストを受け取り、対応する Servlet を呼ぶ
 *   3. Servlet が処理してレスポンス（HTML等）を返す
 *   4. ブラウザがレスポンスを表示
 *
 * 【Servlet のライフサイクル】
 *   init()     → Servlet 初期化時に1回だけ呼ばれる
 *   doGet()    → GET リクエスト時に呼ばれる
 *   doPost()   → POST リクエスト時に呼ばれる
 *   destroy()  → Servlet 破棄時に呼ばれる
 *
 * 【HttpServletRequest / HttpServletResponse】
 *   Request:  リクエスト情報を取得
 *     getParameter("name")  → フォーム/URLパラメータ取得
 *     getMethod()           → HTTPメソッド（GET/POST）
 *     getSession()          → セッション取得
 *
 *   Response: レスポンスを構築
 *     setContentType("text/html; charset=UTF-8")
 *     getWriter()           → PrintWriter でHTML出力
 *     sendRedirect(url)     → リダイレクト
 *
 * 【プロジェクト構成（Tomcat用）】
 *   webapp/
 *   ├── WEB-INF/
 *   │   ├── web.xml         ← Servlet設定（アノテーションなら不要）
 *   │   └── classes/        ← コンパイル済み.classファイル
 *   ├── index.html           ← 静的ファイル
 *   └── *.jsp                ← JSPファイル
 *
 * 【注意】
 *   Servletの実行には Tomcat + servlet-api.jar が必要です。
 *   IntelliJ Ultimate Edition なら Tomcat 連携が簡単です。
 *   Community Edition の場合は手動でTomcatにデプロイします。
 *
 * =====================================
 * 以下は Servlet のコード例です。
 * 実行するには Tomcat 環境が必要です。
 * =====================================
 */
public class ServletLesson {

    public static void main(String[] args) {
        // この main メソッドは Servlet の動作説明用であり、
        // 実際の Servlet はTomcat上で動作するため main から直接実行するものではない。
        // Servlet は Tomcat が自動的にインスタンスを生成し、
        // HTTPリクエストに応じて doGet()/doPost() を呼び出す。
        System.out.println("=== Servlet のコード例 ===");
        System.out.println("実行には Tomcat + servlet-api.jar が必要です。");
        System.out.println();
        System.out.println("以下のコードを参考に Servlet を作成してください。");
        System.out.println("----------------------------------------------");

        // ==========================================================
        // ■ セクション1: Hello Servlet（最も基本的な Servlet の例）
        // ==========================================================
        // この Servlet は HTTP GET リクエストに対してシンプルな HTML ページを返す。
        // ブラウザで http://localhost:8080/アプリ名/hello にアクセスすると表示される。
        System.out.println("""

        // ----- HelloServlet.java -----
        // jakarta.servlet パッケージは Servlet API の中核。
        // ※ Java EE 8 以前は javax.servlet だったが、Jakarta EE 9 以降は
        //    パッケージ名が jakarta.servlet に変更された。
        //    Tomcat 10+ では jakarta、Tomcat 9 以前では javax を使う。
        //    使用するTomcatのバージョンに合わせて import を変更する必要がある。
        // import jakarta.servlet.*;           // ServletException 等の基本クラス
        // import jakarta.servlet.http.*;      // HttpServlet, HttpServletRequest, HttpServletResponse 等
        // import jakarta.servlet.annotation.WebServlet;  // URL マッピング用アノテーション
        // import java.io.*;                   // PrintWriter, IOException 等
        //
        // // @WebServlet アノテーションで、この Servlet が処理する URL パターンを指定する。
        // // "/hello" は「アプリケーションのコンテキストパス + /hello」にマッチする。
        // // 例: アプリ名が myapp なら http://localhost:8080/myapp/hello でアクセスできる。
        // // web.xml に <servlet-mapping> を書く従来の方法もあるが、
        // // @WebServlet アノテーションの方が簡潔でモダンな手法。
        // // 複数のURLに対応する場合は @WebServlet({"/hello", "/hi"}) のように配列で指定。
        // @WebServlet("/hello")  // この Servlet は "/hello" パスへのリクエストを処理する
        // public class HelloServlet extends HttpServlet {
        //
        //     // doGet() メソッドをオーバーライドして GET リクエストの処理を定義する。
        //     // HttpServlet の doGet() デフォルト実装は 405 (Method Not Allowed) を返す。
        //     // @Override でオーバーライドを明示し、メソッド名のタイプミスを防ぐ。
        //     //
        //     // 引数:
        //     //   request  — クライアントから送られたリクエストの全情報を保持するオブジェクト。
        //     //              URL パラメータ、ヘッダー、Cookie、セッション情報等を取得できる。
        //     //   response — サーバーからクライアントに返すレスポンスを構築するオブジェクト。
        //     //              ステータスコード、ヘッダー、ボディ（HTML等）を設定できる。
        //     //
        //     // throws:
        //     //   ServletException — Servlet 処理中の一般的なエラー
        //     //   IOException     — レスポンス書き込み中の I/O エラー
        //     @Override
        //     protected void doGet(HttpServletRequest request,
        //                          HttpServletResponse response)
        //             throws ServletException, IOException {
        //
        //         // setContentType() でレスポンスの MIME タイプと文字エンコーディングを設定する。
        //         // "text/html" はHTMLコンテンツであることを示す MIME タイプ。
        //         // "charset=UTF-8" は文字エンコーディングの指定。これを省略すると
        //         // 日本語が文字化けする可能性がある。UTF-8 は世界中の文字を表現でき、
        //         // Web の標準エンコーディングとして最も広く使われている。
        //         // 【重要】setContentType() は getWriter() より前に呼ぶこと。
        //         // getWriter() の後に呼ぶと文字コードの設定が無視される場合がある。
        //         response.setContentType("text/html; charset=UTF-8");
        //
        //         // getWriter() で PrintWriter オブジェクトを取得する。
        //         // PrintWriter を使ってレスポンスボディ（HTMLなど）を書き込む。
        //         // println() メソッドで文字列を出力すると、それがブラウザに送られる。
        //         // これは System.out.println() と同じ PrintWriter クラスのインスタンスだが、
        //         // 出力先がコンソールではなくHTTPレスポンスボディになっている点が異なる。
        //         PrintWriter out = response.getWriter();
        //
        //         // 以下で HTML を1行ずつ出力してブラウザに返す。
        //         // Servlet で直接 HTML を書く方法はシンプルだが、
        //         // HTML が複雑になると可読性が低下するため、
        //         // 実務では JSP（次のレッスン）を使って表示部分を分離する（MVCパターン）。
        //         out.println("<!DOCTYPE html>");   // HTML5 の文書型宣言
        //         out.println("<html><body>");       // HTML のルート要素と body 要素の開始
        //         out.println("<h1>Hello, Servlet!</h1>");  // 見出し（h1 タグ）
        //         out.println("<p>これはJava Servletからの応答です。</p>");  // 段落テキスト
        //         out.println("</body></html>");    // body と html の閉じタグ
        //         // ※ PrintWriter の close() は Servlet コンテナ（Tomcat）が自動で呼ぶため、
        //         //   明示的に閉じなくてよい。
        //     }
        // }
        // → ブラウザで http://localhost:8080/アプリ名/hello にアクセスすると
        //   「Hello, Servlet!」という見出しとメッセージが表示される。
        """);

        // ==========================================================
        // ■ セクション2: フォーム処理の例（GET でフォーム表示、POST で送信処理）
        // ==========================================================
        // Web アプリケーションの最も基本的なパターン:
        // 1. GET リクエスト → フォーム（入力画面）を HTML で返す
        // 2. ユーザーがフォームに入力して送信ボタンを押す
        // 3. POST リクエスト → 送信されたデータを処理して結果を返す
        //
        // 【GET と POST の使い分け（Servlet の場合）】
        //   GET:  画面の表示、データの取得（副作用のない操作）
        //   POST: データの送信、登録、変更、削除（副作用のある操作）
        //   この使い分けは REST の原則に基づいている。
        System.out.println("""
        // ----- FormServlet.java -----
        // @WebServlet("/form") でこの Servlet を /form パスにマッピングする。
        // doGet() と doPost() の両方をオーバーライドすることで、
        // GET リクエスト（フォーム表示）と POST リクエスト（データ送信）の
        // 両方を1つの Servlet で処理できる。
        // @WebServlet("/form")
        // public class FormServlet extends HttpServlet {
        //
        //     // doGet() — GET リクエスト時にフォーム（入力画面）を表示する。
        //     // ブラウザの URL バーに直接 /form と入力してアクセスした場合や、
        //     // リンク <a href="/form"> をクリックした場合に呼ばれる。
        //     @Override
        //     protected void doGet(HttpServletRequest request,
        //                          HttpServletResponse response)
        //             throws ServletException, IOException {
        //
        //         // レスポンスの Content-Type を HTML + UTF-8 に設定。
        //         response.setContentType("text/html; charset=UTF-8");
        //
        //         // PrintWriter を取得してHTML（フォーム）を出力する。
        //         PrintWriter out = response.getWriter();
        //
        //         // <form method='post'> は HTML のフォーム要素。
        //         // method='post' により、送信ボタンを押すと POST リクエストが送信される。
        //         // action 属性を省略すると、現在のURL（/form）に POST される。
        //         // つまり、同じ Servlet の doPost() メソッドが呼ばれる。
        //         out.println("<form method='post'>");
        //
        //         // <input name='name'> はテキスト入力欄。
        //         // name 属性の値 'name' は、サーバー側で request.getParameter("name") で
        //         // 取得する際のキー名になる。この値が POST データのパラメータ名になる。
        //         // <br> は改行（line break）タグ。
        //         out.println("  名前: <input name='name'><br>");
        //
        //         // <input type='number'> は数値入力欄。
        //         // ブラウザがスピンボタン（上下矢印）を表示し、数値以外の入力を防ぐ。
        //         // ただしサーバー側でもバリデーション（入力値の検証）は必須。
        //         // クライアント側の制限はブラウザの開発者ツールで簡単に回避できるため。
        //         out.println("  年齢: <input name='age' type='number'><br>");
        //
        //         // <button type='submit'> は送信ボタン。
        //         // クリックすると、フォーム内の全 input の値が POST リクエストとして送信される。
        //         // データは "name=入力値&age=入力値" の形式でリクエストボディに含まれる。
        //         out.println("  <button type='submit'>送信</button>");
        //         out.println("</form>");
        //     }
        //
        //     // doPost() — POST リクエスト時にフォームの送信データを処理する。
        //     // ユーザーがフォームの「送信」ボタンを押すと、ブラウザが POST リクエストを送信し、
        //     // Tomcat がこの doPost() メソッドを呼び出す。
        //     @Override
        //     protected void doPost(HttpServletRequest request,
        //                           HttpServletResponse response)
        //             throws ServletException, IOException {
        //
        //         // setCharacterEncoding("UTF-8") でリクエストボディの文字エンコーディングを指定する。
        //         // これを設定しないと、日本語の入力値が文字化けする。
        //         // 【重要】getParameter() より前に呼ぶこと。
        //         // 一度 getParameter() を呼ぶとエンコーディング設定が固定されるため。
        //         // 実務では Filter で全リクエストに一括設定するのが一般的
        //         // （毎回手動で書く手間を省ける）。
        //         request.setCharacterEncoding("UTF-8");
        //
        //         // getParameter("name") でフォームの name 入力欄の値を取得する。
        //         // "name" は HTML の <input name='name'> の name 属性に対応する。
        //         // GET パラメータ（URL の ?name=値）でも POST パラメータでも同じメソッドで取得できる。
        //         // 値が送信されなかった場合は null が返るため、null チェックが望ましい。
        //         // 【セキュリティ注意】ユーザー入力をそのまま HTML に出力すると
        //         //   XSS（クロスサイトスクリプティング）攻撃の脆弱性になる。
        //         //   例: name に "<script>alert('XSS')</script>" が入力された場合、
        //         //   そのまま出力するとブラウザでスクリプトが実行される。
        //         //   対策: HTML エスケープ処理を行う（< → &lt; , > → &gt; 等）。
        //         //   JSP の EL式 ${} は自動エスケープしないため、<c:out> タグや
        //         //   fn:escapeXml() を使うのが安全。
        //         String name = request.getParameter("name");
        //
        //         // getParameter() は常に String を返すため、数値として使うには
        //         // Integer.parseInt() で変換が必要。
        //         // 【注意】数値以外の文字列が入力された場合、NumberFormatException が発生する。
        //         // 実務では try-catch で囲んでエラーメッセージを返すか、
        //         // バリデーションライブラリを使って入力値を検証する。
        //         int age = Integer.parseInt(request.getParameter("age"));
        //
        //         // レスポンスの Content-Type を設定。
        //         response.setContentType("text/html; charset=UTF-8");
        //         PrintWriter out = response.getWriter();
        //
        //         // 処理結果を HTML として出力する。
        //         // 【XSS対策が必要】name をエスケープせずに出力しているため、
        //         // 悪意あるスクリプトが実行される可能性がある。本番コードでは必ずエスケープすること。
        //         out.println("<h1>こんにちは、" + name + "さん！</h1>");
        //         out.println("<p>あなたは" + age + "歳ですね。</p>");
        //
        //         // <a href='/form'> はフォーム画面に戻るリンク。
        //         // リンクをクリックすると GET /form が送信され、doGet() が呼ばれてフォームが再表示される。
        //         out.println("<a href='/form'>戻る</a>");
        //     }
        // }
        """);
    }
}
