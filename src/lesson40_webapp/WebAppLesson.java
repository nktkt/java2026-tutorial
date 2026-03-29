package lesson40_webapp;

/**
 * ===== レッスン40: Webアプリケーション総合 =====
 *
 * 【目標】Servlet + JSP + JDBC を組み合わせたWebアプリの全体像を理解する
 *
 * 【Webアプリケーションの構成（MVC）】
 *
 *   ブラウザ → Tomcat → Servlet(Controller)
 *                          ↓
 *                    Service / Model(ビジネスロジック)
 *                          ↓
 *                    JDBC → データベース
 *                          ↓
 *              Servlet → JSP(View) → ブラウザ
 *
 * 【レイヤー構成】
 *
 *   ■ プレゼンテーション層（View）
 *     JSP ファイル。HTMLを生成してブラウザに返す。
 *
 *   ■ コントローラー層（Controller）
 *     Servlet。リクエストを受け取り、適切なサービスを呼び、結果をViewに渡す。
 *
 *   ■ サービス層（Service / Business Logic）
 *     ビジネスロジック。複数のDAO操作をまとめたり、検証を行う。
 *
 *   ■ データアクセス層（DAO = Data Access Object）
 *     JDBCでデータベースとやり取り。SQL実行はここに集約。
 *
 *   ■ モデル（Model / Entity / DTO）
 *     データを格納するクラス（例: User, Product, Order）。
 *
 * 【セッション管理】
 *   HttpSession: ユーザーごとの状態をサーバー側で保持する仕組み。
 *   ログイン状態、ショッピングカートなどに使う。
 *
 *   session.setAttribute("user", user);     // 保存
 *   User user = (User) session.getAttribute("user"); // 取得
 *   session.invalidate();                   // セッション破棄（ログアウト）
 *
 * 【フィルター（Filter）】
 *   全リクエストに共通の処理を差し込む（認証チェック、文字コード設定等）。
 *   @WebFilter("/*") で全URLに適用。
 *
 * 【以下は決済Webアプリのプロジェクト構成例です】
 */
public class WebAppLesson {

    public static void main(String[] args) {
        // このプログラムは Web アプリケーションの設計ガイドであり、
        // 実際の Web アプリは Tomcat 上で動作する Servlet + JSP で構成される。
        // ここではプロジェクト全体の構成、設計パターン、画面遷移、処理フローを解説する。
        // 実装する際は、このガイドを参考にして個々のファイルを作成する。
        System.out.println("=== 決済Webアプリケーション 設計ガイド ===");
        System.out.println();

        System.out.println("""
        【プロジェクト構成】
        // 以下は決済（EC）Webアプリケーションのディレクトリ構成例。
        // MVCパターンに基づき、各レイヤーごとにパッケージ（フォルダ）を分けている。
        // この構成は Java Web 開発のデファクトスタンダード（事実上の標準）であり、
        // Spring Framework や Jakarta EE のプロジェクトでも同様の構造が使われる。

        payment-app/
        ├── src/
        │   ├── model/
        // ↑ Model 層: アプリケーションで扱うデータの構造を定義するクラスを格納する。
        //   「POJO（Plain Old Java Object）」とも呼ばれ、
        //   フィールド + getter/setter + コンストラクタ + toString() で構成される。
        //   DB のテーブル構造と対応させるのが一般的（1テーブル = 1クラス）。
        //   DTO（Data Transfer Object: レイヤー間のデータ受け渡し用）と
        //   Entity（DBテーブルに直接対応するクラス）を分けることもある。

        │   │   ├── Product.java         ← 商品データ
        // ↑ 商品テーブルに対応するモデルクラス。
        //   フィールド例: int id, String name, int price, String description, int stock
        //   ゲッターとセッターを持つ。DB の products テーブルの1行がこのクラスの1インスタンスに対応。

        │   │   ├── CartItem.java        ← カート内の1商品
        // ↑ ショッピングカート内の各商品を表すクラス。
        //   フィールド例: Product product, int quantity
        //   Product をそのまま持つことで、商品情報（名前・価格）にアクセスできる。
        //   quantity（数量）を増減してカート内の個数管理を行う。
        //   合計金額の計算メソッド（getSubtotal() = product.getPrice() * quantity）を持つと便利。

        │   │   ├── Order.java           ← 注文データ
        // ↑ 確定した注文を表すクラス。
        //   フィールド例: int id, int userId, LocalDateTime orderDate, int totalAmount, String status
        //   注文確定時にカートの内容からこのオブジェクトを生成し、DB に保存する。
        //   status は "PENDING"（処理中）、"COMPLETED"（完了）、"CANCELLED"（キャンセル）など。

        │   │   └── User.java            ← ユーザーデータ
        // ↑ ログインユーザーを表すクラス。
        //   フィールド例: int id, String email, String passwordHash, String name
        //   【セキュリティ重要】パスワードは平文（そのまま）で保存してはならない。
        //   bcrypt や scrypt などのハッシュアルゴリズムでハッシュ化して保存する。
        //   ハッシュ化とは元に戻せない変換のことで、ログイン時は入力値をハッシュ化して
        //   保存済みハッシュと比較する。DB が漏洩しても平文パスワードがわからない。

        │   ├── dao/
        // ↑ DAO（Data Access Object）層: データベースとのやり取りを担当する層。
        //   SQL の実行（CRUD操作）をこの層に集約する。
        //   他の層（Service, Servlet）は DAO を通してのみ DB にアクセスする。
        //   これにより:
        //     - SQL が一箇所にまとまり保守しやすい
        //     - DB を変更（MySQL → PostgreSQL）しても、DAO 層だけ修正すればよい
        //     - テスト時にモック（偽の DAO）に差し替えられる

        │   │   ├── ProductDAO.java       ← 商品のDB操作
        // ↑ 商品テーブルに対する CRUD 操作を実装するクラス。
        //   メソッド例:
        //     List<Product> findAll()            — 全商品を取得（SELECT * FROM products）
        //     Product findById(int id)           — IDで1件取得
        //     void insert(Product p)             — 商品を追加（INSERT）
        //     void update(Product p)             — 商品を更新（UPDATE）
        //     void delete(int id)                — 商品を削除（DELETE）
        //     boolean decreaseStock(int id, int qty) — 在庫を減らす（購入時）
        //   全メソッドで PreparedStatement を使い、SQLインジェクションを防止する。

        │   │   ├── OrderDAO.java         ← 注文のDB操作
        // ↑ 注文テーブルに対する CRUD 操作を実装するクラス。
        //   メソッド例:
        //     void insert(Order order)           — 注文を登録
        //     List<Order> findByUserId(int userId) — ユーザーの注文履歴を取得
        //     void updateStatus(int orderId, String status) — 注文ステータスを更新

        │   │   └── UserDAO.java          ← ユーザーのDB操作
        // ↑ ユーザーテーブルに対する操作を実装するクラス。
        //   メソッド例:
        //     User findByEmail(String email)     — メールアドレスでユーザー検索（ログイン用）
        //     void insert(User user)             — ユーザー登録
        //   【セキュリティ】findByEmail は PreparedStatement を必ず使うこと。
        //   ログイン画面はSQLインジェクション攻撃の主要な標的になる。

        │   ├── service/
        // ↑ Service 層: ビジネスロジック（業務ルール）を実装する層。
        //   Controller（Servlet）と DAO の間に位置し、
        //   複数の DAO 操作をまとめたり、業務ルールの検証を行う。
        //   例: 決済処理 = 在庫チェック + 金額計算 + 注文作成 + カートクリア
        //   これらを1つのトランザクションで実行する責任を持つ。
        //   Service 層がないと、Servlet に業務ロジックが入り混じり、肥大化する。

        │   │   ├── CartService.java      ← カート管理ロジック
        // ↑ ショッピングカートの管理を行うサービスクラス。
        //   カート情報はセッション（HttpSession）に保存するのが一般的。
        //   メソッド例:
        //     void addToCart(HttpSession session, int productId, int qty)
        //       — 商品をカートに追加（既に存在すれば数量を加算）
        //     void removeFromCart(HttpSession session, int productId)
        //       — 商品をカートから削除
        //     List<CartItem> getCartItems(HttpSession session)
        //       — カート内の全商品を取得
        //     int getTotal(HttpSession session)
        //       — カートの合計金額を計算
        //     void clearCart(HttpSession session)
        //       — カートを空にする（決済完了後）

        │   │   ├── PaymentService.java   ← 決済処理ロジック
        // ↑ 決済（支払い）処理を担当するサービスクラス。
        //   メソッド例:
        //     PaymentResult processPayment(User user, List<CartItem> items)
        //       — 在庫確認 → 合計計算（税込） → 決済処理 → 注文作成
        //   【トランザクション管理】
        //     決済処理では複数の DB 操作（在庫減少 + 注文作成）を
        //     1つのトランザクションで実行する必要がある。
        //     途中で失敗した場合、全ての変更をロールバックして一貫性を保つ。
        //   【実務での決済】
        //     実際のEC サイトでは外部決済サービス（Stripe, PayPay, クレジットカード会社等）の
        //     API を呼び出して決済を行う。自前で決済処理を実装することはセキュリティ上非常に危険。
        //     PCI DSS（Payment Card Industry Data Security Standard）という
        //     クレジットカード情報のセキュリティ基準に準拠する必要があるため。

        │   │   └── OrderService.java     ← 注文処理ロジック
        // ↑ 注文の作成・管理を行うサービスクラス。
        //   メソッド例:
        //     Order createOrder(User user, List<CartItem> items, int totalAmount)
        //       — 注文オブジェクトを生成して DB に保存
        //     List<Order> getOrderHistory(int userId)
        //       — ユーザーの注文履歴を取得

        │   ├── servlet/
        // ↑ Controller 層: Servlet（コントローラー）を格納するパッケージ。
        //   各 Servlet は特定の URL パターンに対応し、
        //   HTTPリクエストを受け取って処理を振り分ける役割を持つ。
        //   処理の流れ:
        //     1. リクエストパラメータの取得（request.getParameter()）
        //     2. 入力値のバリデーション（検証）
        //     3. Service 層のメソッドを呼び出してビジネスロジックを実行
        //     4. 結果を request 属性にセット（request.setAttribute()）
        //     5. JSP にフォワードして表示（RequestDispatcher.forward()）

        │   │   ├── ProductListServlet.java  ← 商品一覧(GET)
        // ↑ 商品一覧ページを表示する Servlet。@WebServlet("/products") でマッピング。
        //   doGet() で:
        //     ProductDAO.findAll() → 全商品を取得
        //     request.setAttribute("products", products) → JSPにデータを渡す
        //     forward("products.jsp") → 商品一覧JSPに転送

        │   │   ├── CartServlet.java         ← カート操作(GET/POST)
        // ↑ カートの表示と操作を行う Servlet。@WebServlet("/cart") でマッピング。
        //   doGet()  → カートの内容を表示（cart.jsp に転送）
        //   doPost() → カートへの追加・削除（CartService を呼び出し、カートページにリダイレクト）
        //   【PRGパターン (Post/Redirect/Get)】
        //     POST処理後は sendRedirect() で GET にリダイレクトする。
        //     これによりブラウザの「戻る」ボタンや F5 キーで
        //     POST が再送信される問題（二重注文等）を防げる。

        │   │   ├── CheckoutServlet.java     ← 決済画面(GET/POST)
        // ↑ 決済確認と決済実行を行う Servlet。@WebServlet("/checkout") でマッピング。
        //   doGet()  → 決済確認画面を表示（カート内容と合計金額を checkout.jsp に表示）
        //   doPost() → 決済を実行（PaymentService.processPayment() を呼び出す）
        //     成功 → complete.jsp にフォワード
        //     失敗 → エラーメッセージ付きで checkout.jsp に戻る

        │   │   ├── LoginServlet.java        ← ログイン(GET/POST)
        // ↑ ログイン画面の表示と認証を行う Servlet。@WebServlet("/login") でマッピング。
        //   doGet()  → ログインフォームを表示（login.jsp に転送）
        //   doPost() →
        //     1. email と password を getParameter() で取得
        //     2. UserDAO.findByEmail(email) でユーザーを検索
        //     3. パスワードのハッシュ値を照合
        //     4. 認証成功 → session.setAttribute("user", user) でセッションに保存
        //                → 商品一覧ページにリダイレクト
        //     5. 認証失敗 → エラーメッセージ付きで login.jsp に戻る
        //   【セキュリティ】
        //     - パスワードは平文で比較せず、bcrypt 等のハッシュ比較を行う
        //     - ログイン試行回数を制限してブルートフォース攻撃を防ぐ
        //     - セッション固定攻撃を防ぐため、ログイン成功時に session.invalidate() +
        //       新セッション作成を行う

        │   │   └── AuthFilter.java          ← 認証フィルター
        // ↑ 認証チェックを全リクエストに適用するフィルター。
        //   @WebFilter("/*") で全 URL パターンにマッチする。
        //   Filter はServlet の前に実行される「横断的関心事（cross-cutting concern）」の処理。
        //   処理の流れ:
        //     1. リクエスト URL がログインページか静的リソースなら → そのまま通す
        //     2. session.getAttribute("user") で認証状態をチェック
        //     3. ログイン済み → chain.doFilter() でリクエストを次に進める
        //     4. 未ログイン → /login にリダイレクト
        //   他のフィルターの用途:
        //     - 文字エンコーディング設定（request.setCharacterEncoding("UTF-8")）
        //     - アクセスログの記録
        //     - CORS（Cross-Origin Resource Sharing）ヘッダーの設定
        //     - レスポンスの圧縮

        │   └── util/
        │       └── DBConnection.java        ← DB接続ユーティリティ
        // ↑ データベース接続を一元管理するユーティリティクラス。
        //   static メソッドで Connection オブジェクトを取得する。
        //   接続URL、ユーザー名、パスワードをここに集約することで、
        //   DB の接続情報が変更になっても1箇所を修正するだけで済む。
        //   【本番環境】
        //     接続情報はソースコードに直接書かず、設定ファイルや環境変数から読み込む。
        //     コネクションプーリング（接続の使い回し）を使うことで性能を向上させる。
        //     Apache DBCP や HikariCP などのライブラリを使用する。
        //     Tomcat の場合は context.xml に DataSource を定義して JNDI で取得するのが一般的。

        └── webapp/
        // ↑ Web アプリケーションの公開ディレクトリ。
        //   このフォルダの中のファイルがブラウザからアクセス可能になる。
        //   ※ WEB-INF/ フォルダ内のファイルはブラウザから直接アクセスできない
        //     （セキュリティ上の仕組み）。JSP を WEB-INF 配下に置くことで、
        //     Servlet 経由でのみアクセスさせる運用もある。

            ├── WEB-INF/
            │   └── web.xml
        // ↑ Web アプリケーションの設定ファイル（デプロイメントディスクリプタ）。
        //   Servlet のマッピング、フィルター設定、セッションタイムアウト、
        //   エラーページの設定などを XML で記述する。
        //   @WebServlet や @WebFilter アノテーションを使う場合は最小限の設定でよい。
        //   設定例:
        //     <session-config><session-timeout>30</session-timeout></session-config>
        //     → セッションのタイムアウトを30分に設定

            ├── login.jsp
        // ↑ ログイン画面。email と password の入力フォーム。
        //   POST で /login（LoginServlet）にデータを送信する。
        //   エラーメッセージ（${error}）を表示する領域を含む。

            ├── products.jsp             ← 商品一覧画面
        // ↑ 商品一覧表示画面。<c:forEach> で全商品をループ表示する。
        //   各商品に「カートに追加」ボタンがあり、POST で /cart に送信する。
        //   商品の名前、価格、在庫数を表形式で表示する。

            ├── cart.jsp                 ← カート画面
        // ↑ カートの内容を表示する画面。追加された商品、数量、小計を表示。
        //   合計金額を表示し、「決済に進む」ボタンで /checkout に遷移する。
        //   各商品に「削除」ボタンを設けて、カートから取り除ける。

            ├── checkout.jsp             ← 決済確認画面
        // ↑ 決済前の最終確認画面。注文内容（商品名、数量、金額）の一覧を表示し、
        //   「支払う」ボタンで POST /checkout を送信して決済を実行する。
        //   【UX上の重要点】ユーザーに注文内容を確認させてから決済を実行する。
        //   誤注文を防ぐためのステップ。

            └── complete.jsp             ← 決済完了画面
        // ↑ 決済完了後に表示される画面。「ご注文ありがとうございます」メッセージ、
        //   注文番号、合計金額を表示する。
        //   「商品一覧に戻る」リンクで /products に遷移する。


        【画面遷移フロー】
        // 以下はユーザーがアプリを使う際の一般的な操作の流れ（ユーザーストーリー）。
        // 各画面間の遷移は HTTP リクエスト（GET/POST）によって行われる。
        //
        //   ログイン画面（/login GET）
        //     → メールとパスワードを入力して送信（/login POST）
        //     → 認証成功 → 商品一覧画面にリダイレクト（/products GET）
        //
        //   商品一覧画面（/products GET）
        //     → 「カートに追加」ボタン押下（/cart POST）→ カート画面にリダイレクト
        //
        //   カート画面（/cart GET）
        //     → 「決済に進む」ボタン押下（/checkout GET）→ 決済確認画面
        //
        //   決済確認画面（/checkout GET）
        //     → 「支払う」ボタン押下（/checkout POST）→ 決済処理 → 完了画面
        //
        //   決済完了画面（complete.jsp）
        //     → 「商品一覧に戻る」リンク（/products GET）

        ログイン → 商品一覧 → カートに追加 → カート確認 → 決済 → 完了


        【決済処理の流れ（CheckoutServlet.doPost() の内部処理）】
        // 以下は決済処理の詳細なステップ。
        // トランザクション管理が非常に重要で、途中で失敗した場合は
        // 全ての変更をロールバックしてデータの一貫性を保つ。

        1. ユーザーがカートの商品を確認して「支払う」ボタンを押す
        // → ブラウザが POST /checkout リクエストを送信する。
        //   CheckoutServlet の doPost() メソッドが呼び出される。

        2. CheckoutServlet が POST を受け取る
        // → セッションからユーザー情報とカート内容を取得する。
        //   session.getAttribute("user") と CartService.getCartItems(session) を呼ぶ。
        //   カートが空の場合はエラーメッセージを表示して処理を中断する。

        3. PaymentService が以下を実行:
        // → 以下の全ステップを1つのトランザクション内で実行する。
        //   conn.setAutoCommit(false) でトランザクションを開始。

           a. 在庫チェック
        // → 各商品の現在の在庫数を DB から取得し、注文数量以上あるか確認する。
        //   在庫不足の商品がある場合は例外をスローしてロールバック。
        //   【注意】在庫チェックと在庫減少の間に他のユーザーが購入する可能性があるため、
        //   SELECT ... FOR UPDATE で行ロックをかけるか、
        //   UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?
        //   のようにアトミック（分割不能）な更新を行う。

           b. 合計金額計算（税込）
        // → 各商品の price * quantity を合計し、消費税（例: 10%）を加算する。
        //   int subtotal = items.stream().mapToInt(i -> i.getPrice() * i.getQty()).sum();
        //   int tax = (int)(subtotal * 0.10);
        //   int total = subtotal + tax;

           c. 決済処理（外部API呼び出し or DB更新）
        // → 実際のECサイトでは外部決済API（Stripe, PayPay等）を呼び出す。
        //   学習用途では DB の「口座残高」を減算する処理で代用してもよい。
        //   外部API呼び出しはネットワークエラーの可能性があるため、
        //   リトライ処理やタイムアウト設定が重要。

           d. 注文レコード作成
        // → orders テーブルに新しい注文を INSERT する。
        //   注文の詳細（各商品と数量）は order_items テーブルに INSERT する。
        //   注文番号（id）は AUTOINCREMENT で自動採番される。

           e. カートをクリア
        // → セッションからカート情報を削除する。
        //   session.removeAttribute("cart") でカートを空にする。

        4. 成功 → complete.jsp にフォワード
        // → request.setAttribute("order", order) で注文情報をセットし、
        //   RequestDispatcher.forward() で完了画面に転送する。
        //   conn.commit() でトランザクションを確定する。

           失敗 → エラーメッセージ付きで checkout.jsp に戻る
        // → catch ブロックで conn.rollback() を呼び、全変更を取り消す。
        //   request.setAttribute("error", "決済処理に失敗しました") でエラーメッセージをセットし、
        //   checkout.jsp にフォワードしてユーザーに再試行を促す。

        ※ 全体をトランザクションで囲み、途中で失敗したらロールバック
        // → これにより ACID 特性の A（原子性: All or Nothing）が保証される。
        //   在庫は減ったが注文が作成されない、という中途半端な状態を防げる。
        //
        // 【実務でのさらなる考慮事項】
        //   - ログ出力: 全ての決済処理をログに記録する（監査証跡）
        //   - 冪等性: 同じ決済が二重に実行されないようにする（冪等キーの使用）
        //   - 非同期処理: 決済完了メールの送信は非同期（別スレッド/キュー）で行う
        //   - HTTPS: 決済情報は必ず HTTPS（TLS暗号化）で通信する
        //   - CSRF対策: トークンを使ってクロスサイトリクエストフォージェリを防ぐ
        """);
    }
}
