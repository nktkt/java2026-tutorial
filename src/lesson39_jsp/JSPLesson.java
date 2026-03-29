package lesson39_jsp;

/**
 * ===== レッスン39: JSP (JavaServer Pages) =====
 *
 * 【目標】HTMLの中にJavaコードを埋め込むJSPの仕組みを学ぶ
 *
 * 【JSPとは？】
 *   HTML の中に Java コードを埋め込めるテンプレート技術。
 *   .jsp ファイルは初回アクセス時に Servlet に自動変換されて実行される。
 *
 * 【Servlet vs JSP】
 *   Servlet: Java のコードの中に HTML を書く → ロジック向き
 *   JSP:     HTML の中に Java を埋め込む     → 表示向き
 *   → 実務では Servlet で処理 → JSP で表示（MVCパターン）
 *
 * 【JSPの記法】
 *
 *   <%@ page ... %>     ← ページディレクティブ（設定）
 *   <%@ page contentType="text/html; charset=UTF-8" %>
 *
 *   <% コード %>        ← スクリプトレット（Javaコード実行）
 *   <%= 式 %>           ← 式の値をHTMLに出力
 *   <%! 宣言 %>         ← メソッドやフィールドの宣言
 *
 *   ${式}               ← EL式（Expression Language）推奨
 *   <c:forEach>等       ← JSTLタグ（JSP Standard Tag Library）推奨
 *
 * 【暗黙オブジェクト】
 *   request   → HttpServletRequest
 *   response  → HttpServletResponse
 *   session   → HttpSession
 *   out       → JspWriter（出力用）
 *   application → ServletContext
 *
 * 【MVC パターン（Model-View-Controller）】
 *   Model:      データとビジネスロジック（Javaクラス）
 *   View:       表示（JSP）
 *   Controller: リクエスト処理とルーティング（Servlet）
 *
 *   Servlet(Controller) → 処理 → request.setAttribute("data", data)
 *                       → request.getRequestDispatcher("view.jsp").forward(req, res)
 *   JSP(View) → ${data} で表示
 */
public class JSPLesson {

    public static void main(String[] args) {
        // この main メソッドは JSP の文法を説明するためのもの。
        // JSP ファイル自体は Tomcat の webapp/ フォルダに .jsp ファイルとして配置し、
        // ブラウザからアクセスすると Tomcat が自動的に Servlet に変換して実行する。
        // つまり JSP はコンパイルや main の実行ではなく、
        // Webブラウザ経由でのアクセスで動作する技術である。
        System.out.println("=== JSP のコード例 ===");
        System.out.println("JSP ファイルは webapp/ フォルダに配置します。");
        System.out.println();

        // ==========================================================
        // ■ セクション1: 基本的な JSP（スクリプトレットと式の使い方）
        // ==========================================================
        // JSP は HTML ファイルの中に Java コードを埋め込むテンプレート技術。
        // 初回アクセス時に Tomcat が JSP を Servlet（Java クラス）に自動変換し、
        // コンパイルして実行する。2回目以降はコンパイル済みのクラスが再利用される。
        //
        // 【JSP の変換プロセス】
        //   hello.jsp → hello_jsp.java（Servlet ソースコード）→ hello_jsp.class（バイトコード）
        //   JSP 内の HTML はそのまま out.write("...") に変換され、
        //   <% %> 内の Java コードは Servlet の _jspService() メソッド内にそのまま挿入される。
        System.out.println("""
        ----- hello.jsp -----

        <%@ page contentType="text/html; charset=UTF-8" %>
        // ↑ ページディレクティブ: JSP ページ全体の設定を行うタグ。
        // contentType="text/html; charset=UTF-8" はレスポンスの MIME タイプと
        // 文字エンコーディングを指定する。Servlet の response.setContentType() と同等。
        // UTF-8 を指定することで日本語が文字化けしない。
        // 他の設定例:
        //   <%@ page import="java.util.*" %>       — Java クラスのインポート
        //   <%@ page session="false" %>             — セッションを使わない（パフォーマンス向上）
        //   <%@ page errorPage="error.jsp" %>       — エラー時のリダイレクト先
        //   <%@ page isErrorPage="true" %>           — エラーページであることを宣言

        <!DOCTYPE html>
        // ↑ HTML5 の文書型宣言。これは JSP タグではなく純粋な HTML。
        // JSP ファイル内の JSP タグ以外の部分は全てそのまま HTML として出力される。

        <html>
        <head><title>Hello JSP</title></head>
        <body>
            <h1>Hello, JSP!</h1>

            <%-- スクリプトレット（Javaコード） --%>
            // ↑ <%-- --%> は JSP コメント。HTML のソース（ブラウザの「ページのソースを表示」）には
            //   表示されない。<!-- --> は HTML コメントで、ソースに表示される。
            //   セキュリティ的に、サーバー側の情報は JSP コメントで書くのが安全。

            <% String name = "Naoki"; %>
            // ↑ <% %> はスクリプトレット（Scriptlet）。この中に Java コードを記述できる。
            //   ここでは String 型の変数 name に "Naoki" を代入している。
            //   スクリプトレット内のコードは Servlet の _jspService() メソッド内に
            //   そのまま挿入されるため、ローカル変数として扱われる。
            //   【注意】スクリプトレットの多用は推奨されない。
            //   理由: JSP に Java コードが混在すると可読性・保守性が低下する。
            //   代替手段: EL式（${...}）と JSTL タグを使うのがモダンな手法（セクション2で解説）。

            <p>名前: <%= name %></p>
            // ↑ <%= %> は式タグ（Expression Tag）。式を評価し、その結果を HTML に出力する。
            //   out.print(name) と同等。= の後にセミコロン(;)は不要（式全体が print の引数になるため）。
            //   ここでは変数 name の値 "Naoki" が <p> タグ内に出力され、
            //   ブラウザには「名前: Naoki」と表示される。

            <%-- ループ --%>
            <ul>
            <% for (int i = 1; i <= 5; i++) { %>
            // ↑ スクリプトレット内で for ループを開始している。
            //   Java の for ループがそのまま使える。
            //   { で開いたブロックは後続の <% } %> で閉じる。
            //   HTML 部分（<li> タグ）はループの各反復で出力される。
            //   つまり、ループが5回実行されるので <li> タグが5つ生成される。

                <li>アイテム <%= i %></li>
            // ↑ ループ変数 i の値を式タグで出力する。
            //   1回目: <li>アイテム 1</li>
            //   2回目: <li>アイテム 2</li>  ... と5回繰り返される。

            <% } %>
            // ↑ for ループの閉じ括弧。スクリプトレットと HTML を交互に書くのが JSP のパターン。
            //   この書き方はスパゲッティコード（絡み合った読みにくいコード）になりやすいため、
            //   実務では JSTL の <c:forEach> タグを使うことが強く推奨される。

            </ul>

            <%-- 現在時刻 --%>
            <p>現在時刻: <%= new java.util.Date() %></p>
            // ↑ 式タグ内で Java オブジェクトを直接生成して出力している。
            //   new java.util.Date() は現在日時を表す Date オブジェクトを作成する。
            //   toString() が自動的に呼ばれ、日時の文字列表現が HTML に出力される。
            //   import ディレクティブ（<%@ page import="java.util.Date" %>）を
            //   ページ上部に書けば、完全修飾名（java.util.Date）を省略して
            //   new Date() と書くこともできる。

        </body>
        </html>
        """);

        // ==========================================================
        // ■ セクション2: EL式 + JSTL + Servlet 連携（MVCパターン）
        // ==========================================================
        // 【MVCパターンとは】
        //   Model（データ）、View（表示）、Controller（制御）を分離する設計パターン。
        //   Java Web 開発では:
        //     Controller = Servlet（リクエストを受け、データを準備し、JSPに転送）
        //     View       = JSP（データを受け取って HTML を生成）
        //     Model      = Java クラス（データとビジネスロジック）
        //   この分離により、表示の変更がロジックに影響しない（逆も然り）。
        //
        // 【EL式 (Expression Language)】
        //   ${...} の形式でデータにアクセスする簡潔な記法。
        //   スクリプトレット <% %> よりも遥かに読みやすく、推奨されている。
        //   request.getAttribute("key") を ${key} と書ける。
        //   ドット記法でプロパティにもアクセスできる: ${user.name}
        //     → user オブジェクトの getName() メソッドを自動的に呼び出す。
        //
        // 【JSTL (JSP Standard Tag Library)】
        //   JSP で使える標準タグライブラリ。ループ・条件分岐などを
        //   Java コードなしで HTML タグ風に書ける。
        //   <c:forEach>, <c:if>, <c:choose>, <c:set>, <fmt:formatDate> など。
        //   使用するには taglib ディレクティブで宣言が必要。
        System.out.println("""
        ----- Servlet から JSP にデータを渡す（MVCパターンの実装） -----

        // 【Controller側: Servlet のコード】
        // Servlet はリクエストを処理し、結果データを request にセットして JSP に転送する。
        // この「処理→データ設定→転送」のパターンが MVC の Controller の役割。

        // List<String> items = Arrays.asList("りんご", "バナナ", "みかん");
        // ↑ 表示するデータを準備する。実務では DB から取得したデータや
        //   ビジネスロジックの処理結果がここに入る。

        // request.setAttribute("items", items);
        // ↑ request オブジェクトにデータを「属性（attribute）」としてセットする。
        //   第1引数 "items" はキー名（JSP 側で ${items} としてアクセスする名前）。
        //   第2引数 items はセットするデータ（Object型なので何でもセットできる）。
        //   request 属性はそのリクエストの間だけ有効（1回のリクエスト-レスポンスサイクル）。
        //   セッション属性（session.setAttribute）はユーザーが接続している間有効。

        // request.setAttribute("userName", "田中");
        // ↑ 同様に、ユーザー名もリクエスト属性としてセットする。
        //   JSP 側で ${userName} として参照できる。

        // request.getRequestDispatcher("/items.jsp").forward(request, response);
        // ↑ RequestDispatcher を使って JSP にリクエストを「フォワード（転送）」する。
        //   フォワードはサーバー内部の転送で、ブラウザの URL は変わらない。
        //   request と response オブジェクトがそのまま JSP に引き継がれるため、
        //   setAttribute() でセットしたデータを JSP で参照できる。
        //
        //   【フォワード vs リダイレクトの違い】
        //     forward():    サーバー内部転送。URL不変。リクエスト属性が引き継がれる。速い。
        //     sendRedirect(): ブラウザに「別のURLに再アクセスしろ」と指示。URL変わる。
        //                     リクエスト属性は引き継がれない。POST後のリダイレクト（PRGパターン）に使う。

        ----- items.jsp（View側: JSP のコード） -----

        <%@ page contentType="text/html; charset=UTF-8" %>
        // ↑ ページディレクティブ。UTF-8 を指定して日本語文字化けを防ぐ。

        <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        // ↑ taglib ディレクティブ: JSTL のコアタグライブラリを使うことを宣言する。
        //   prefix="c" はタグのプレフィックス（接頭辞）。<c:forEach>, <c:if> のように使う。
        //   uri="jakarta.tags.core" はJSTLコアライブラリの識別子。
        //   ※ Jakarta EE 9 以前は uri="http://java.sun.com/jsp/jstl/core" だった。
        //   ※ JSTL を使うには jstl の jar をプロジェクトに追加する必要がある。

        <!DOCTYPE html>
        <html>
        <body>
            <h1>${userName}さんのアイテム一覧</h1>
            // ↑ ${userName} は EL 式（Expression Language）。
            //   Servlet で request.setAttribute("userName", "田中") とセットした値を取得する。
            //   "田中" が出力され、HTML には「田中さんのアイテム一覧」と表示される。
            //   EL式は以下のスコープを順に検索する:
            //     1. pageScope     — ページ内変数
            //     2. requestScope  — リクエスト属性（setAttribute で設定）
            //     3. sessionScope  — セッション属性
            //     4. applicationScope — アプリケーション属性
            //   【XSS注意】EL式 ${} はデフォルトでHTMLエスケープしない。
            //   安全にするには <c:out value="${userName}"/> を使うか、
            //   fn:escapeXml(userName) を使う。

            <%-- JSTL の c:forEach でループ --%>
            // ↑ <%-- --%> は JSP コメント。HTML ソースには出力されない。
            <ul>
            <c:forEach var="item" items="${items}">
            // ↑ <c:forEach> は JSTL のループタグ。Java の for-each ループに相当する。
            //   items="${items}" — ループ対象のコレクション。request 属性 "items" を EL式で取得。
            //   var="item" — ループ変数の名前。各反復で1要素が "item" に代入される。
            //   スクリプトレットの <% for(...) %> を使うよりも遥かに読みやすく、
            //   HTML テンプレートとの親和性が高い。
            //   他のオプション: varStatus="status"（ループインデックス等の情報を取得）
            //     ${status.index} — 0始まりのインデックス
            //     ${status.count} — 1始まりのカウント
            //     ${status.first} — 最初の要素なら true
            //     ${status.last}  — 最後の要素なら true

                <li>${item}</li>
            // ↑ ループ変数 item の値を EL式で出力する。
            //   items リストの各要素が順に出力される:
            //   <li>りんご</li>, <li>バナナ</li>, <li>みかん</li>

            </c:forEach>
            </ul>

            <%-- 条件分岐 --%>
            <c:if test="${not empty items}">
            // ↑ <c:if> は JSTL の条件分岐タグ。Java の if 文に相当する。
            //   test 属性に EL式で条件を記述する。
            //   ${not empty items} は「items が null でなく、かつ空でない場合に true」。
            //   empty 演算子は null、空文字列、空コレクション、空配列を全て true と判定する。
            //   not はその否定。つまり「items に要素が1つ以上ある場合」に true になる。
            //   ※ <c:if> には else に相当するタグがない。
            //   if-else が必要な場合は <c:choose><c:when><c:otherwise> を使う:
            //     <c:choose>
            //       <c:when test="${条件1}">条件1が真の場合</c:when>
            //       <c:when test="${条件2}">条件2が真の場合</c:when>
            //       <c:otherwise>どの条件にも当てはまらない場合</c:otherwise>
            //     </c:choose>

                <p>合計: ${items.size()}個</p>
            // ↑ ${items.size()} はリストの size() メソッドを EL式から呼び出している。
            //   EL 3.0（Java EE 7+）以降では、引数なしのメソッドを EL式から呼び出せる。
            //   それ以前のバージョンでは ${fn:length(items)} のように JSTL 関数を使う必要がある。
            //   ここでは items の要素数（3）が出力され、「合計: 3個」と表示される。

            </c:if>
        </body>
        </html>
        """);
    }
}
