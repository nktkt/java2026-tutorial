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
        System.out.println("=== 決済Webアプリケーション 設計ガイド ===");
        System.out.println();

        System.out.println("""
        【プロジェクト構成】

        payment-app/
        ├── src/
        │   ├── model/
        │   │   ├── Product.java         ← 商品データ
        │   │   ├── CartItem.java        ← カート内の1商品
        │   │   ├── Order.java           ← 注文データ
        │   │   └── User.java            ← ユーザーデータ
        │   ├── dao/
        │   │   ├── ProductDAO.java       ← 商品のDB操作
        │   │   ├── OrderDAO.java         ← 注文のDB操作
        │   │   └── UserDAO.java          ← ユーザーのDB操作
        │   ├── service/
        │   │   ├── CartService.java      ← カート管理ロジック
        │   │   ├── PaymentService.java   ← 決済処理ロジック
        │   │   └── OrderService.java     ← 注文処理ロジック
        │   ├── servlet/
        │   │   ├── ProductListServlet.java  ← 商品一覧(GET)
        │   │   ├── CartServlet.java         ← カート操作(GET/POST)
        │   │   ├── CheckoutServlet.java     ← 決済画面(GET/POST)
        │   │   ├── LoginServlet.java        ← ログイン(GET/POST)
        │   │   └── AuthFilter.java          ← 認証フィルター
        │   └── util/
        │       └── DBConnection.java        ← DB接続ユーティリティ
        └── webapp/
            ├── WEB-INF/
            │   └── web.xml
            ├── login.jsp
            ├── products.jsp             ← 商品一覧画面
            ├── cart.jsp                 ← カート画面
            ├── checkout.jsp             ← 決済確認画面
            └── complete.jsp             ← 決済完了画面


        【画面遷移フロー】

        ログイン → 商品一覧 → カートに追加 → カート確認 → 決済 → 完了


        【決済処理の流れ】

        1. ユーザーがカートの商品を確認して「支払う」ボタンを押す
        2. CheckoutServlet が POST を受け取る
        3. PaymentService が以下を実行:
           a. 在庫チェック
           b. 合計金額計算（税込）
           c. 決済処理（外部API呼び出し or DB更新）
           d. 注文レコード作成
           e. カートをクリア
        4. 成功 → complete.jsp にフォワード
           失敗 → エラーメッセージ付きで checkout.jsp に戻る

        ※ 全体をトランザクションで囲み、途中で失敗したらロールバック
        """);
    }
}
