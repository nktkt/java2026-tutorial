// ============================================================================
// パッケージ宣言: このクラスが属するパッケージを指定する
// payment_project.model パッケージは「モデル層」に該当する
// MVCアーキテクチャにおいて、Model はデータ構造とビジネスロジックの一部を担当する
// Webアプリでは、Servlet（Controller）がこのモデルを生成し、JSP（View）に渡す
// ============================================================================
package payment_project.model;

/**
 * 商品データを表すモデルクラス（Entity / DTO）
 *
 * 【デザインパターン・設計思想】
 * - Entity パターン: データベースの「products」テーブルの1行に対応するJavaオブジェクト
 * - DTO（Data Transfer Object）: 層間（DB → Service → Servlet → JSP）でデータを受け渡す役割
 * - JavaBean 規約: 引数なしコンストラクタ + getter/setter を持つことで、
 *   フレームワーク（JPA、MyBatis、Jackson 等）が自動的にオブジェクトを生成できる
 *
 * 【Webアプリケーションでの使われ方】
 * - DAO（Data Access Object）層: DBから取得した ResultSet を Product オブジェクトに変換する
 * - Servlet 層: request.setAttribute("product", product) で JSP にデータを渡す
 * - JSP 層: ${product.name} のように EL式でフィールドにアクセスする（getterが自動呼出される）
 *
 * 【セキュリティに関する注意点】
 * - price フィールドはサーバーサイドで管理し、クライアントから送られた価格を信用してはならない
 *   （価格改ざん攻撃を防ぐため、決済時は必ずサーバー側の価格を使用すること）
 * - stock フィールドは同時アクセス時に競合状態（Race Condition）が発生する可能性がある
 *   → 実運用では DB のトランザクション + 排他ロック（SELECT ... FOR UPDATE）で対応する
 *
 * 【ポイント】
 * - フィールドは private（カプセル化）: 外部から直接アクセスできないようにする
 * - getter/setter でアクセス: フィールドの読み書きを制御できる（バリデーション追加が容易）
 * - toString() をオーバーライドしてデバッグしやすくする
 * - equals()/hashCode() は id ベースで比較するのが望ましい（今後の拡張ポイント）
 */
public class Product {

    // ========================================================================
    // フィールド定義（private でカプセル化）
    // すべてのフィールドを private にすることで、外部クラスから直接アクセスを防ぐ
    // これにより、将来バリデーションロジックを追加しても、呼び出し元を変更せずに済む
    // ========================================================================

    // 商品ID: データベースの主キー（PRIMARY KEY）に対応する
    // int 型を使用しているが、実運用では自動採番（AUTO_INCREMENT）されることが多い
    // 大規模システムでは long 型や UUID を使うケースもある
    private int id;

    // 商品名: ユーザーに表示される商品の名前
    // String はイミュータブル（不変）なので、セキュリティ上安全
    // JSP で表示する際は、XSS（クロスサイトスクリプティング）対策として
    // fn:escapeXml() や JSTL の <c:out> タグを使ってHTMLエスケープすること
    private String name;

    // 税抜価格（円）: 消費税を含まない本体価格
    // int 型なので最大約21億円まで扱える（通常の商品であれば十分）
    // 注意: 小数点以下の精度が必要な場合は BigDecimal を使うべき
    // 例えば、外貨建て決済や割引計算で端数が出る場合は int では精度不足になる
    private int price;

    // 在庫数: 現在の在庫数量
    // 0 になると「売り切れ」となり、カートに追加できなくなる
    // 負の値にならないよう、CartService や PaymentService でバリデーションを行う
    // 実運用では DB 側で CHECK 制約（stock >= 0）を設定するのが望ましい
    private int stock;

    // ========================================================================
    // コンストラクタ
    // ========================================================================

    // デフォルトコンストラクタ（引数なし）
    // JavaBean 規約に準拠するため、引数なしのコンストラクタが必要
    // フレームワーク（JPA の EntityManager、Jackson の ObjectMapper 等）が
    // リフレクションを使ってインスタンスを生成する際に、このコンストラクタが呼ばれる
    // リフレクションとは: クラス情報を実行時に動的に取得・操作するJavaの仕組み
    public Product() {}

    // 全フィールドを初期化するコンストラクタ
    // テストコードや Main クラスのデモで、1行で Product オブジェクトを生成するために使用する
    // 引数の名前がフィールド名と同じなので、this キーワードでフィールドを区別する
    // this.id はフィールド、id は引数を指す
    public Product(int id, String name, int price, int stock) {
        this.id = id;       // 引数 id をフィールド id に代入する
        this.name = name;   // 引数 name をフィールド name に代入する
        this.price = price; // 引数 price をフィールド price に代入する
        this.stock = stock; // 引数 stock をフィールド stock に代入する
    }

    // ========================================================================
    // getter/setter メソッド
    // JavaBean 規約: get + フィールド名（先頭大文字）の命名規則
    // JSP の EL式（${product.id}）は内部的に getId() を呼び出す
    // ========================================================================

    // 商品IDのgetter: 商品IDを返す
    // カートでの商品検索や、DBのWHERE句で使用される
    public int getId() { return id; }

    // 商品IDのsetter: 商品IDを設定する
    // DAOがDBから取得した値を設定する際に使用する
    // 通常、アプリケーションコードからIDを直接変更することは避けるべき
    public void setId(int id) { this.id = id; }

    // 商品名のgetter: 商品名を返す
    // JSP で商品一覧や確認画面に表示する際に呼ばれる
    public String getName() { return name; }

    // 商品名のsetter: 商品名を設定する
    // 管理画面から商品情報を更新する際などに使用する
    public void setName(String name) { this.name = name; }

    // 税抜価格のgetter: 税抜価格を返す
    // 消費税率は別途 getTaxIncludedPrice() で計算する
    public int getPrice() { return price; }

    // 税抜価格のsetter: 税抜価格を設定する
    // 価格変更やセール対応時に使用する
    public void setPrice(int price) { this.price = price; }

    // 在庫数のgetter: 現在の在庫数を返す
    // カートに追加する際の在庫チェックや、商品一覧画面での表示に使用する
    public int getStock() { return stock; }

    // 在庫数のsetter: 在庫数を設定する
    // 決済完了後に在庫を減らす（PaymentService.checkout()）際に呼ばれる
    // 入荷処理で在庫を増やす場合にも使用する
    public void setStock(int stock) { this.stock = stock; }

    // ========================================================================
    // ビジネスロジックメソッド
    // ========================================================================

    // 税込価格を計算して返すメソッド（消費税10%）
    // 【計算方法】税抜価格 × 1.10 の結果を int にキャスト（小数点以下切り捨て）
    // 【注意点】
    // - (int) によるキャストは小数点以下を切り捨てる（例: 3080.0 → 3080, 1320.0 → 1320）
    // - 日本の消費税法では、税込価格の1円未満の端数処理は事業者の判断に委ねられている
    //   （切り捨て、切り上げ、四捨五入のいずれでもよい）
    // - 軽減税率（8%）が必要な場合は、商品カテゴリに応じて税率を変える必要がある
    //   → 例: food カテゴリなら price * 1.08、それ以外は price * 1.10
    // - より正確な計算が必要な場合は BigDecimal を使うべき:
    //   BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(1.10)).intValue()
    // 【Webアプリでの使い方】
    // - JSP の EL式: ${product.taxIncludedPrice} で呼び出せる
    //   （getTaxIncludedPrice() の get を外し、先頭小文字にしたプロパティ名）
    public int getTaxIncludedPrice() {
        return (int) (price * 1.10); // 税抜価格に1.10を掛けて税込価格を算出、intにキャストして小数切り捨て
    }

    // ========================================================================
    // Object クラスのメソッドをオーバーライド
    // ========================================================================

    // toString(): オブジェクトの文字列表現を返す
    // System.out.println(product) のように出力すると、このメソッドが自動的に呼ばれる
    // デバッグ時やログ出力時に、オブジェクトの内容を確認しやすくするために定義する
    // String.format() でフォーマット指定: %d は整数、%s は文字列を埋め込む
    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', price=%d円, stock=%d}",
            id, name, price, stock); // 各フィールドの値をフォーマットして文字列に変換
    }
}
