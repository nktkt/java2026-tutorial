// ============================================================================
// パッケージ宣言: このクラスが payment_project.servlet パッケージに属することを示す
// servlet パッケージは「コントローラ層」に該当する
// MVCアーキテクチャにおいて、Controller はユーザーのリクエストを受け取り、
// Service 層を呼び出し、結果を View（JSP）に渡す役割を担う
// ============================================================================
package payment_project.servlet;

/**
 * 決済 Servlet の実装例（コード参照用）
 *
 * 【このファイルの目的】
 * Tomcat 環境がなくても、Servlet + JSP を使ったWebアプリケーションの構造を
 * 学習できるよう、コード例をコメント形式で記述したリファレンスファイル
 * main() メソッドで実行すると、Servlet のコード例がコンソールに表示される
 *
 * 【注意】
 * このファイルは Tomcat 環境がないとコンパイルできないコードをコメント化している
 * コードの構造を学ぶための参照用ファイルであり、直接実行される本番コードではない
 *
 * 【必要な依存ライブラリ】
 * - jakarta.servlet-api（Tomcat 10+ / Jakarta EE 9+）
 *   → パッケージ名: jakarta.servlet, jakarta.servlet.http
 * - javax.servlet-api（Tomcat 9以前 / Java EE 8以前）
 *   → パッケージ名: javax.servlet, javax.servlet.http
 * - sqlite-jdbc（DB使用時）: SQLite データベースへの JDBC 接続用
 *
 * 【MVCアーキテクチャの全体像】
 *   ブラウザ → Servlet（Controller） → Service → Model
 *                ↓                              ↓
 *              JSP（View）    ←   request.setAttribute()
 *
 * 【このプロジェクトの Servlet 一覧】
 * 1. ProductListServlet（/products）: 商品一覧を表示する
 * 2. CartServlet（/cart）: カート操作（追加・削除・表示）
 * 3. CheckoutServlet（/checkout）: 決済処理を実行する
 * 4. AuthFilter（/*）: 認証チェック（フィルター）
 */
public class PaymentServletExample {

    // main() メソッド: このクラスを直接実行した際のエントリーポイント
    // Servlet のコード例をテキストブロック（Text Block）で表示する
    // テキストブロック: Java 15 で正式導入された複数行文字列リテラル（""" で囲む）
    // 従来の文字列連結や \n を使わずに、読みやすい形で複数行の文字列を記述できる
    public static void main(String[] args) {
        // ヘッダーメッセージを表示する
        System.out.println("=== 決済 Servlet のコード例 ===");
        System.out.println(); // 空行を出力して見やすくする

        // テキストブロックで Servlet のコード例を出力する
        // """ で始まり """ で終わる（Java 15+ のテキストブロック機能）
        // インデント処理: テキストブロック内の共通インデントは自動的に除去される
        System.out.println("""
        // ===== 商品一覧 Servlet =====
        // 【役割】 商品一覧ページ（/products）のリクエストを処理するコントローラ
        // 【URLマッピング】 @WebServlet("/products") でURL「/products」にマッピング
        //   → ブラウザで http://localhost:8080/app/products にアクセスすると doGet() が呼ばれる
        // 【HTTPメソッド】 GET（ページの表示はGETリクエスト）
        //
        // @WebServlet("/products")
        // public class ProductListServlet extends HttpServlet {
        //
        //     // doGet(): HTTP GETリクエストを処理するメソッド
        //     // HttpServlet クラスの doGet() をオーバーライドする
        //     // 【引数】
        //     //   request: クライアントからのリクエスト情報（パラメータ、ヘッダ等）を含む
        //     //   response: クライアントへのレスポンス（HTML出力、リダイレクト等）を制御する
        //     // 【throws 宣言】
        //     //   ServletException: Servlet 処理中のエラー
        //     //   IOException: 入出力エラー（レスポンス書き込み失敗等）
        //     @Override
        //     protected void doGet(HttpServletRequest request,
        //                          HttpServletResponse response)
        //             throws ServletException, IOException {
        //
        //         // 商品データをDAOから取得する（本来はDBから取得）
        //         // DAO（Data Access Object）: DB操作を担当するクラス
        //         // ProductDAO.findAll() は全商品をリストで返す
        //         // 【セキュリティ注意】 DAOでは PreparedStatement を使ってSQLインジェクションを防ぐ
        //         List<Product> products = ProductDAO.findAll();
        //
        //         // JSP にデータを渡すために request スコープに属性をセットする
        //         // setAttribute("products", products) で、JSP 内で ${products} としてアクセス可能
        //         // 【スコープの種類】
        //         //   request スコープ: 1回のリクエスト内で有効（最も一般的）
        //         //   session スコープ: ユーザーのセッション内で有効（ログイン情報、カート等）
        //         //   application スコープ: アプリ全体で共有（設定情報等）
        //         request.setAttribute("products", products);
        //
        //         // JSP にフォワードして HTML を生成する
        //         // getRequestDispatcher("/products.jsp"): 指定したJSPのディスパッチャを取得する
        //         // forward(request, response): リクエストとレスポンスをJSPに転送する
        //         // 【フォワードとリダイレクトの違い】
        //         //   フォワード: サーバー内部で転送（URLは変わらない、requestの属性が引き継がれる）
        //         //   リダイレクト: クライアントに新しいURLへのアクセスを指示（URLが変わる、属性は引き継がれない）
        //         request.getRequestDispatcher("/products.jsp")
        //                .forward(request, response);
        //     }
        // }


        // ===== カート操作 Servlet =====
        // 【役割】 カート画面（/cart）のリクエストを処理するコントローラ
        // 【URLマッピング】 @WebServlet("/cart") でURL「/cart」にマッピング
        // 【HTTPメソッド】
        //   POST: カートへの商品追加・削除（データの変更を伴う操作）
        //   GET: カート内容の表示（データの参照のみ）
        // 【PRG パターン（Post/Redirect/Get）を使用】
        //   POST → 処理実行 → リダイレクト → GET → 画面表示
        //   → ブラウザの「戻る」「更新」ボタンによる二重送信を防止できる
        //
        // @WebServlet("/cart")
        // public class CartServlet extends HttpServlet {
        //
        //     // doPost(): HTTP POSTリクエストを処理する（カートの変更操作）
        //     // フォームの「カートに追加」「カートから削除」ボタンから呼ばれる
        //     @Override
        //     protected void doPost(HttpServletRequest request,
        //                           HttpServletResponse response)
        //             throws ServletException, IOException {
        //
        //         // セッションを取得する（なければ新規作成される）
        //         // HttpSession: ユーザーごとに独立したデータ保存領域
        //         // セッションIDはCookie（JSESSIONID）でクライアントと紐づけられる
        //         // 【セキュリティ注意】 セッションハイジャック対策として、
        //         //   HTTPS 使用、Cookie の Secure/HttpOnly 属性設定が必要
        //         HttpSession session = request.getSession();
        //
        //         // セッションからカートを取得する（なければ新規作成）
        //         // getAttribute() は Object 型で返すので、CartService にキャストする
        //         // 初回アクセス時はカートが存在しないので null が返る
        //         CartService cart = (CartService) session.getAttribute("cart");
        //         if (cart == null) {
        //             // 初回アクセス時: 新しい CartService を作成してセッションに保存する
        //             cart = new CartService();
        //             session.setAttribute("cart", cart);
        //         }
        //
        //         // リクエストパラメータからアクション種別を取得する
        //         // action: "add"（追加）または "remove"（削除）
        //         // 【セキュリティ注意】 パラメータの値を検証すべき（ホワイトリスト方式）
        //         String action = request.getParameter("action");
        //
        //         // リクエストパラメータから商品IDを取得する
        //         // 【セキュリティ注意】 Integer.parseInt() は不正な入力で NumberFormatException が発生する
        //         //   → try-catch で例外を捕捉し、エラーページに遷移すべき
        //         //   → また、int 型なのでSQLインジェクションのリスクは低い
        //         int productId = Integer.parseInt(request.getParameter("productId"));
        //
        //         // アクション種別に応じて処理を分岐する
        //         // switch 文で文字列の比較を行う（Java 7 以降で文字列 switch が使用可能）
        //         switch (action) {
        //             case "add":
        //                 // 商品をカートに追加する
        //                 // まずDBから商品情報を取得する（価格改ざん防止のためサーバー側の価格を使用）
        //                 Product product = ProductDAO.findById(productId);
        //                 cart.addItem(product, 1);  // 数量1で追加（数量選択機能を追加する場合はパラメータから取得）
        //                 break;
        //             case "remove":
        //                 // 商品をカートから削除する
        //                 cart.removeItem(productId);
        //                 break;
        //         }
        //
        //         // PRG パターン: POST 処理後に GET /cart にリダイレクトする
        //         // リダイレクトにより、ブラウザの「更新」ボタンによるPOSTの再送信を防止する
        //         // 【重要】 リダイレクトすると request スコープの属性はクリアされる
        //         //   → エラーメッセージ等を渡したい場合は、session スコープを使う
        //         response.sendRedirect("cart");  // GET /cart にリダイレクト
        //     }
        //
        //     // doGet(): HTTP GETリクエストを処理する（カート内容の表示）
        //     // ブラウザで /cart にアクセスした時、またはPOST後のリダイレクトで呼ばれる
        //     @Override
        //     protected void doGet(HttpServletRequest request,
        //                          HttpServletResponse response)
        //             throws ServletException, IOException {
        //
        //         // セッションからカートを取得する
        //         HttpSession session = request.getSession();
        //         CartService cart = (CartService) session.getAttribute("cart");
        //
        //         // JSP にカート内容を渡す
        //         // カートが null の場合は空のリストと合計0を渡す（三項演算子でnullチェック）
        //         // List.of() は空の不変リストを返す（Java 9+）
        //         request.setAttribute("cartItems", cart != null ? cart.getItems() : List.of());
        //         request.setAttribute("total", cart != null ? cart.getTotalAmount() : 0);
        //
        //         // cart.jsp にフォワードしてカート画面を表示する
        //         request.getRequestDispatcher("/cart.jsp")
        //                .forward(request, response);
        //     }
        // }


        // ===== 決済 Servlet =====
        // 【役割】 決済処理（/checkout）のリクエストを処理するコントローラ
        // 【URLマッピング】 @WebServlet("/checkout") でURL「/checkout」にマッピング
        // 【HTTPメソッド】 POST（決済は重要なデータ変更操作なので POST を使用）
        //   GET は使わない（URL に決済情報が含まれてしまうため、セキュリティリスクがある）
        //
        // 【セキュリティ対策】
        // - CSRF トークンの検証（決済フォームに hidden フィールドでトークンを埋め込む）
        // - 二重送信防止（トランザクションIDの一意性チェック、ボタンの無効化）
        // - HTTPS の強制（決済情報は暗号化された通信で送受信する）
        //
        // @WebServlet("/checkout")
        // public class CheckoutServlet extends HttpServlet {
        //
        //     // doPost(): 決済処理を実行する
        //     @Override
        //     protected void doPost(HttpServletRequest request,
        //                           HttpServletResponse response)
        //             throws ServletException, IOException {
        //
        //         // セッションからカートとユーザーIDを取得する
        //         HttpSession session = request.getSession();
        //         CartService cart = (CartService) session.getAttribute("cart");
        //
        //         // 【セキュリティ重要】 ユーザーIDはセッションから取得する
        //         // リクエストパラメータから取得すると、なりすまし攻撃が可能になる
        //         String userId = (String) session.getAttribute("userId");
        //
        //         // カートの存在チェック: カートが null または空の場合はエラー
        //         if (cart == null || cart.isEmpty()) {
        //             // エラーメッセージを設定してカート画面に戻る
        //             request.setAttribute("error", "カートが空です");
        //             request.getRequestDispatcher("/cart.jsp")
        //                    .forward(request, response);
        //             return;  // ここで処理を終了（以降の決済処理は実行しない）
        //         }
        //
        //         // フォームから支払い方法を取得する
        //         String method = request.getParameter("paymentMethod");
        //
        //         // 支払い方法に応じて PaymentMethod の実装クラスをインスタンス化する
        //         // switch 式（Java 14+）: switch の結果を変数に代入できる新構文
        //         // アロー構文（->）: break 不要で、1つの式を返す
        //         // 【Strategy パターンの活用】 ここでどの戦略（具体クラス）を使うかを決定する
        //         PaymentService.PaymentMethod payment = switch (method) {
        //             case "credit" -> new PaymentService.CreditCardPayment(
        //                 request.getParameter("cardNumber"));  // カード番号をフォームから取得
        //             case "bank" -> new PaymentService.BankTransferPayment();
        //             case "convenience" -> new PaymentService.ConveniencePayment();
        //             default -> throw new IllegalArgumentException("不正な支払い方法");
        //               // 不正な値が送信された場合は例外をスローする
        //               // 【セキュリティ注意】 ホワイトリスト方式で有効な値のみ受け付ける
        //         };
        //
        //         // try-catch で決済処理を実行し、エラーハンドリングを行う
        //         try {
        //             // PaymentService のインスタンスを生成する
        //             PaymentService paymentService = new PaymentService();
        //
        //             // 決済を実行し、Order オブジェクトを取得する
        //             // 内部で在庫チェック、決済処理、注文作成、在庫更新、カートクリアが行われる
        //             Order order = paymentService.checkout(cart, userId, payment);
        //
        //             // 決済成功: Order を JSP に渡して完了画面を表示する
        //             request.setAttribute("order", order);
        //             request.getRequestDispatcher("/complete.jsp")
        //                    .forward(request, response);
        //
        //         } catch (Exception e) {
        //             // 決済失敗: エラーメッセージを設定してチェックアウト画面に戻る
        //             // 【改善ポイント】 例外の種類によってエラーメッセージを分ける
        //             //   IllegalStateException: 在庫不足 → 「在庫が不足しています」
        //             //   RuntimeException: 決済失敗 → 「決済に失敗しました。再度お試しください」
        //             request.setAttribute("error", e.getMessage());
        //             request.getRequestDispatcher("/checkout.jsp")
        //                    .forward(request, response);
        //         }
        //     }
        // }


        // ===== 認証フィルター =====
        // 【役割】 全リクエストに対してログイン状態をチェックするフィルター
        // 【URLマッピング】 @WebFilter("/*") で全URL（/*）にフィルターを適用
        // 【Filter とは】 Servlet の前後に処理を挿入できる仕組み（AOP的なアプローチ）
        //   リクエスト → Filter → Servlet → Filter → レスポンス
        //   複数のフィルターをチェーン（連鎖）させることも可能
        //
        // 【フィルターの典型的な用途】
        // - 認証・認可チェック（このコードの例）
        // - 文字エンコーディングの設定
        // - CORS（Cross-Origin Resource Sharing）ヘッダの追加
        // - アクセスログの記録
        // - レスポンスの圧縮
        //
        // @WebFilter("/*")
        // public class AuthFilter implements Filter {
        //
        //     // 認証不要な公開パスの一覧（ホワイトリスト）
        //     // Set.of() で不変の Set を生成する（Java 9+）
        //     // これらのパスには、ログインしていなくてもアクセスできる
        //     // 【セキュリティ注意】 公開パスは必要最小限にする（最小権限の原則）
        //     private static final Set<String> PUBLIC_PATHS =
        //         Set.of("/login", "/register", "/css/", "/js/");
        //
        //     // doFilter(): フィルター処理のメインメソッド
        //     // 全リクエストに対して呼ばれ、認証チェックを行う
        //     // 【引数】
        //     //   req: リクエスト（ServletRequest 型なので、HttpServletRequest にキャストが必要）
        //     //   res: レスポンス（同様にキャストが必要）
        //     //   chain: フィルターチェーン（次のフィルターまたは Servlet に処理を渡す）
        //     @Override
        //     public void doFilter(ServletRequest req, ServletResponse res,
        //                          FilterChain chain)
        //             throws IOException, ServletException {
        //
        //         // ServletRequest → HttpServletRequest にダウンキャストする
        //         // HTTP固有のメソッド（getSession(), getRequestURI() 等）を使うために必要
        //         HttpServletRequest request = (HttpServletRequest) req;
        //         HttpServletResponse response = (HttpServletResponse) res;
        //
        //         // リクエストされたURLのパスを取得する
        //         // 例: http://localhost:8080/app/products → "/app/products"
        //         String path = request.getRequestURI();
        //
        //         // 公開パスのチェック: リクエストURLが公開パスで始まるか確認する
        //         // Stream API の anyMatch() で、公開パスのいずれかに一致するかを判定
        //         // path::startsWith はメソッド参照（p -> path.startsWith(p) と同義）
        //         // 【セキュリティ注意】 パストラバーサル攻撃（../ を含むパス）への対策も検討すべき
        //         if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
        //             chain.doFilter(req, res);  // 公開パスなので、認証なしで次の処理に進む
        //             return;  // フィルター処理を終了
        //         }
        //
        //         // ログインチェック: セッションにユーザーIDが存在するか確認する
        //         // getSession(false): セッションが存在しない場合は null を返す（新規作成しない）
        //         //   getSession(true) や getSession() だと新規作成されてしまうので false を指定
        //         HttpSession session = request.getSession(false);
        //
        //         // セッションが null（未ログイン）またはユーザーIDが設定されていない場合
        //         if (session == null || session.getAttribute("userId") == null) {
        //             // ログインページにリダイレクトする
        //             // 【改善ポイント】 リダイレクト前のURLを保存し、ログイン後に元のページに戻す
        //             //   session.setAttribute("redirectUrl", path);
        //             response.sendRedirect("login");
        //             return;  // フィルター処理を終了（以降の Servlet は実行されない）
        //         }
        //
        //         // 認証OK: フィルターチェーンの次の処理に進む（Servlet が実行される）
        //         chain.doFilter(req, res);
        //     }
        // }
        """); // テキストブロックの終了
    }
}
