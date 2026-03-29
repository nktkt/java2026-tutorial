// ============================================================================
// パッケージ宣言: このクラスが payment_project.model パッケージに属することを示す
// Product, CartItem と同じ model パッケージに配置し、データモデル層を構成する
// ============================================================================
package payment_project.model;

// java.time.LocalDateTime: Java 8 以降で導入された日時を扱うクラス
// 旧来の java.util.Date や java.sql.Timestamp よりも使いやすく、イミュータブル（不変）
// タイムゾーン情報を含まないローカル日時を表す（日本国内システム向け）
// グローバル対応が必要な場合は ZonedDateTime や OffsetDateTime を使う
import java.time.LocalDateTime;

// java.util.List: 順序付きコレクション（要素の追加順を保持する）
// CartItem のリストを保持するために使用する
// List はインターフェースであり、実装クラス（ArrayList 等）は CartService 側で決定される
import java.util.List;

/**
 * 注文データを表すモデルクラス
 *
 * 【デザインパターン・設計思想】
 * - Entity パターン: データベースの「orders」テーブルの1行に対応する
 * - ステートパターンの簡易版: Status enum で注文の状態遷移を管理する
 *   PENDING → COMPLETED → REFUNDED のように状態が変化する
 * - 不変データの保持: 注文確定後の商品情報や金額は変更されるべきではない
 *   実運用では、注文時点の価格を別途保存する（商品の値段が後から変わっても注文金額は変わらない）
 *
 * 【決済の流れでの位置づけ】
 * カート確認 → 決済処理 → Order 作成 → DB保存 → 完了画面表示
 * PaymentService.checkout() メソッドの中でこの Order オブジェクトが生成される
 *
 * 【Webアプリケーションでの使われ方】
 * - CheckoutServlet で PaymentService.checkout() を呼び、戻り値の Order を取得
 * - request.setAttribute("order", order) で JSP に渡す
 * - complete.jsp で注文番号、合計金額、注文状態などを表示する
 * - 注文履歴ページでは、OrderDAO.findByUserId() で過去の注文を取得して表示する
 *
 * 【セキュリティに関する注意点】
 * - 注文IDは推測されにくいランダム値を使用すべき（連番だと他人の注文情報を推測される恐れ）
 *   → 実運用では UUID や、タイムスタンプ + ランダム値の組み合わせを使う
 * - userId はセッションから取得し、リクエストパラメータから受け取ってはならない
 *   （他人のIDを指定して注文されることを防ぐため）
 * - totalAmount はサーバーサイドで計算し、クライアントから送信された金額を信用しない
 */
public class Order {

    // ========================================================================
    // 注文ステータスを表す列挙型（enum）
    // enum を使うことで、ステータスの値を限定し、不正な値が設定されるのを防ぐ
    // 文字列（"pending", "completed" 等）で管理すると、タイプミスによるバグが発生しやすい
    // enum なら、コンパイル時に不正な値を検出できる（型安全性）
    //
    // 【状態遷移図】
    // PENDING ──→ COMPLETED ──→ REFUNDED
    //    │
    //    └──→ FAILED
    //
    // 実運用では、遷移の制約をメソッドで管理する（例: COMPLETED からのみ REFUNDED に遷移可能）
    // ========================================================================
    public enum Status {
        PENDING,      // 処理中: 注文が作成されたが、決済がまだ完了していない状態
        COMPLETED,    // 完了: 決済が成功し、注文が確定した状態
        FAILED,       // 失敗: 決済処理中にエラーが発生した状態（カード不正、残高不足など）
        REFUNDED      // 返金済み: 注文がキャンセルされ、返金処理が完了した状態
    }

    // ========================================================================
    // フィールド定義
    // ========================================================================

    // 注文ID: 各注文を一意に識別するための番号
    // 実運用では、データベースの AUTO_INCREMENT や SEQUENCE で自動採番される
    // このデモでは Math.random() でランダムに生成している（PaymentService.checkout() 参照）
    // 【セキュリティ注意】 連番だと他人の注文IDを推測される可能性があるため、
    // 実運用では UUID や暗号学的に安全な乱数を使うべき
    private int id;

    // ユーザーID: この注文を行ったユーザーを識別する文字列
    // Webアプリでは HttpSession から取得する（ログイン時にセッションに保存されている）
    // 【セキュリティ注意】 リクエストパラメータから受け取ると、なりすまし注文が可能になる
    private String userId;

    // 注文に含まれる商品（CartItem）のリスト
    // カートの内容をそのまま保持する
    // 【設計上の注意点】 実運用では、注文確定時の商品情報（名前、価格）を
    // OrderItem（注文明細）テーブルに別途保存すべき
    // 理由: 後から商品の価格や名前が変更されても、注文時点の情報を正確に保持するため
    private List<CartItem> items;

    // 合計金額（税込）: 注文に含まれる全商品の小計の合計
    // コンストラクタ内で CartItem の小計を合算して算出する
    // 一度計算された合計金額は変更されない（注文確定後の金額は不変であるべき）
    private int totalAmount;

    // 注文の状態: enum Status の値を持つ
    // 注文のライフサイクルに応じて PENDING → COMPLETED → REFUNDED と遷移する
    private Status status;

    // 注文日時: 注文が作成された日時
    // LocalDateTime.now() で現在時刻が設定される
    // 注文履歴の表示や、注文の時系列ソートに使用される
    // 【注意】 LocalDateTime はタイムゾーン情報を含まない
    // サーバーのデフォルトタイムゾーン（日本なら JST = UTC+9）の時刻が使われる
    private LocalDateTime createdAt;

    // 支払方法: "credit"（クレジットカード）, "bank"（銀行振込）, "convenience"（コンビニ払い）
    // PaymentMethod インターフェースの getName() から取得した文字列が設定される
    // DBに保存する際は、この文字列値がそのまま格納される
    private String paymentMethod;

    // ========================================================================
    // コンストラクタ
    // ========================================================================

    // 注文オブジェクトを生成するコンストラクタ
    // PaymentService.checkout() メソッドの中で呼ばれる
    // 【引数の意味】
    //   userId: 注文するユーザーのID（セッションから取得した安全な値）
    //   items: カートの商品リスト（CartService.getItems() から取得）
    //   paymentMethod: 支払い方法の名前（PaymentMethod.getName() から取得）
    public Order(String userId, List<CartItem> items, String paymentMethod) {
        this.userId = userId;             // ユーザーIDを設定する
        this.items = items;               // カートの商品リストを設定する
        this.paymentMethod = paymentMethod; // 支払い方法を設定する

        // 合計金額の計算: Stream API を使って全 CartItem の小計を合算する
        // items.stream() でリストをストリームに変換
        // .mapToInt(CartItem::getSubtotal) で各CartItemの小計（int）に変換
        //   CartItem::getSubtotal はメソッド参照（ラムダ式 item -> item.getSubtotal() と同じ意味）
        // .sum() で全小計を合計する
        // 【Stream API の利点】
        // - for ループよりも宣言的で意図が明確（「何をしたいか」がコードに直接表現される）
        // - 並列化も容易（parallelStream() に変更するだけ）
        this.totalAmount = items.stream()
            .mapToInt(CartItem::getSubtotal) // 各カートアイテムの小計を int 値に変換する
            .sum();                          // 全小計を合計して totalAmount に代入する

        // 初期ステータスは PENDING（処理中）に設定する
        // 決済が成功した後、PaymentService で COMPLETED に変更される
        this.status = Status.PENDING;

        // 現在の日時を注文作成日時として設定する
        // LocalDateTime.now() はサーバーのシステム時刻を取得する
        this.createdAt = LocalDateTime.now();
    }

    // ========================================================================
    // getter/setter メソッド
    // ========================================================================

    // 注文IDのgetter: 注文を一意に識別するIDを返す
    // 注文完了画面で「注文番号: #12345」のように表示する際に使用する
    public int getId() { return id; }

    // 注文IDのsetter: 注文IDを設定する
    // PaymentService.checkout() でランダム生成されたIDを設定する際に呼ばれる
    // 実運用では DB の AUTO_INCREMENT で自動設定されるため、setter は不要になることもある
    public void setId(int id) { this.id = id; }

    // ユーザーIDのgetter: この注文を行ったユーザーのIDを返す
    // 注文履歴の検索条件や、注文確認画面での表示に使用する
    public String getUserId() { return userId; }

    // 注文商品リストのgetter: この注文に含まれる全商品（CartItem）のリストを返す
    // 注文詳細画面で、各商品の名前・数量・小計を表示する際に使用する
    // 【注意】 直接リストの参照を返しているため、呼び出し元でリストを変更すると
    // Order 内のリストも変更される（防御的コピーをすべきかは設計方針による）
    public List<CartItem> getItems() { return items; }

    // 合計金額のgetter: 税込の合計金額を返す
    // 注文完了画面や注文履歴での金額表示に使用する
    public int getTotalAmount() { return totalAmount; }

    // 注文ステータスのgetter: 現在の注文状態を返す
    // 注文履歴画面で「完了」「処理中」「返金済み」などのステータスを表示する際に使用する
    public Status getStatus() { return status; }

    // 注文ステータスのsetter: 注文の状態を更新する
    // PaymentService で決済成功後に COMPLETED に変更する際に呼ばれる
    // 返金処理時に REFUNDED に変更する際にも呼ばれる
    // 【改善ポイント】 状態遷移の妥当性チェックを入れるべき
    //   例: FAILED から COMPLETED への遷移は禁止する、など
    public void setStatus(Status status) { this.status = status; }

    // 注文日時のgetter: 注文が作成された日時を返す
    // 注文履歴の時系列表示やソートに使用する
    public LocalDateTime getCreatedAt() { return createdAt; }

    // 支払い方法のgetter: 決済に使用された支払い方法の名前を返す
    // 注文確認画面で「支払方法: クレジットカード」のように表示する際に使用する
    public String getPaymentMethod() { return paymentMethod; }

    // ========================================================================
    // Object クラスのメソッドをオーバーライド
    // ========================================================================

    // toString(): 注文の詳細情報を整形した文字列として返す
    // デバッグ時やコンソール出力時に、注文内容を見やすく表示するために定義する
    // StringBuilder を使って効率的に文字列を組み立てる
    // （String の + 連結を繰り返すと、毎回新しい String オブジェクトが生成されて非効率）
    @Override
    public String toString() {
        // StringBuilder: 可変長文字列を効率的に構築するためのクラス
        // String は不変（immutable）なので、文字列連結のたびに新しいオブジェクトが生成される
        // StringBuilder は内部バッファを再利用するため、大量の文字列連結に適している
        StringBuilder sb = new StringBuilder();

        // 注文ヘッダー: 注文IDを表示する
        // String.format() の %n は環境に応じた改行コード（Windows: \r\n, Linux/Mac: \n）
        sb.append(String.format("=== 注文 #%d ===%n", id));

        // ユーザーID を表示する
        sb.append(String.format("ユーザー: %s%n", userId));

        // 注文日時を表示する（LocalDateTime の toString() が "2026-03-27T14:30:00" 形式で出力）
        sb.append(String.format("日時: %s%n", createdAt));

        // 支払方法を表示する
        sb.append(String.format("支払方法: %s%n", paymentMethod));

        // 商品リストのヘッダーを表示する
        sb.append("--- 商品 ---\n");

        // 拡張for文で注文に含まれる全 CartItem を反復処理し、各商品の情報を追加する
        // item.toString() が自動的に呼ばれ、「  商品名 x数量 = 小計円」の形式で出力される
        for (CartItem item : items) {
            sb.append(item).append("\n"); // CartItem の toString() の結果を追加して改行
        }

        // 合計金額を表示する
        // %,d はカンマ区切りの整数フォーマット（例: 12345 → "12,345"）
        sb.append(String.format("合計: %,d円%n", totalAmount));

        // 注文ステータスを表示する（enum の name() が呼ばれ、"COMPLETED" 等が表示される）
        sb.append(String.format("状態: %s%n", status));

        // 組み立てた文字列を返す
        // StringBuilder の toString() で、内部バッファの内容を String に変換する
        return sb.toString();
    }
}
