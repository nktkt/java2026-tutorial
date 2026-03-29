// ============================================================================
// パッケージ宣言: このクラスが payment_project.service パッケージに属することを示す
// service パッケージは「サービス層」（ビジネスロジック層）に該当する
// MVCアーキテクチャでは、Controller（Servlet）から呼ばれ、Model を操作する役割を担う
// Model（データ）と Controller（リクエスト処理）の間に Service 層を設けることで、
// ビジネスロジックを Servlet から分離し、テストしやすく再利用しやすい設計になる
// ============================================================================
package payment_project.service;

// 同じプロジェクトの model パッケージから CartItem と Product をインポートする
// CartItem: カート内の1商品（商品 + 数量）を表すモデル
// Product: 商品データを表すモデル
import payment_project.model.CartItem;
import payment_project.model.Product;

// java.util パッケージのコレクション関連クラスをまとめてインポートする
// ワイルドカード（*）を使うことで、ArrayList, LinkedHashMap, List, Map 等を個別に書かなくて済む
// 【注意】 ワイルドカードインポートは便利だが、どのクラスを使っているか分かりにくくなる
// チーム開発では、個別インポートを推奨するコーディング規約もある
import java.util.*;

/**
 * ショッピングカートの管理サービスクラス
 *
 * 【デザインパターン・設計思想】
 * - サービス層パターン: ビジネスロジックを1か所に集約する
 *   Servlet はリクエスト/レスポンスの処理に専念し、ビジネスロジックは Service に委譲する
 * - ファサードパターン（Facade）: カート操作の複雑さを隠蔽し、シンプルなAPIを提供する
 *   内部では Map の操作、バリデーション、在庫チェック等を行うが、
 *   呼び出し元は addItem(), removeItem() 等の単純なメソッドを呼ぶだけでよい
 *
 * 【責務（このクラスが担当すること）】
 * - 商品の追加・削除・数量変更
 * - カート内の合計計算
 * - 在庫チェック（カート追加時）
 * - カート内容の表示
 *
 * 【Webアプリケーションでのセッション管理】
 * HttpSession にこの CartService オブジェクトを保存することで、
 * ユーザーごとに独立したカートを管理できる:
 *   session.setAttribute("cart", cartService);  // カートをセッションに保存
 *   CartService cart = (CartService) session.getAttribute("cart");  // セッションから取得
 *
 * 【セキュリティに関する注意点】
 * - セッション固定攻撃の防止: ログイン後に session.invalidate() → 新セッション作成
 * - セッションタイムアウト: 一定時間操作がないとカートが消える（web.xml で設定）
 * - CSRF対策: カート操作のPOSTリクエストにCSRFトークンを含める
 * - クラスタ環境ではセッションレプリケーションのため Serializable の実装を検討する
 *
 * 【スレッドセーフティに関する注意】
 * - Servlet は複数のリクエストを同時に処理する（マルチスレッド）
 * - 同じユーザーが複数タブでカート操作した場合、競合が発生する可能性がある
 * - 対策: synchronized メソッドにするか、ConcurrentHashMap を使う
 */
public class CartService {

    // ========================================================================
    // フィールド定義
    // ========================================================================

    // カート内の商品を管理するマップ（Map）
    // キー: 商品ID（Integer）、値: CartItem オブジェクト
    //
    // 【LinkedHashMap を選んだ理由】
    // - HashMap: 順序を保証しない → カート内の表示順が毎回変わる可能性がある
    // - TreeMap: キーの自然順（数値順）で並ぶ → 追加順ではない
    // - LinkedHashMap: 挿入順序を保持する → ユーザーがカートに追加した順で表示される
    //   → ECサイトのカート画面では「追加した順」が最も直感的なため、LinkedHashMap が適切
    //
    // 【final 修飾子の意味】
    // items の参照先（Map オブジェクト）を変更できない（別の Map を代入できない）
    // ただし、Map の中身（要素の追加・削除）は変更できる
    // これにより、items が null になることを防ぎ、NullPointerException を回避できる
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    // ========================================================================
    // カートに商品を追加するメソッド
    // ========================================================================

    // カートに商品を追加する
    // 【処理の流れ】
    // 1. 数量のバリデーション（1以上であること）
    // 2. 在庫数のチェック（要求数量が在庫以下であること）
    // 3. すでにカートにある場合は数量を加算、なければ新規追加
    //
    // 【Webアプリでの呼び出し元】
    // CartServlet.doPost() → action が "add" の場合に呼ばれる
    // product は ProductDAO.findById() で DB から取得したオブジェクト
    // quantity はリクエストパラメータから取得した値（Integer.parseInt() で変換）
    public void addItem(Product product, int quantity) {
        // 数量が0以下の場合は不正な入力なので例外をスローする
        // IllegalArgumentException: 引数が不正な場合に投げる標準例外
        // Webアプリではこの例外を catch してエラーメッセージを画面に表示する
        if (quantity <= 0) {
            throw new IllegalArgumentException("数量は1以上にしてください");
        }

        // 要求数量が在庫数を超えている場合は在庫不足エラーをスローする
        // 【セキュリティ注意】 在庫チェックはサーバーサイドで必ず行う
        // クライアントサイド（JavaScript）でのチェックだけでは、改ざんされる可能性がある
        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("在庫不足です（在庫: " + product.getStock() + "）");
        }

        // Product の getId() で商品IDを取得し、Map の検索キーとして使用する
        int productId = product.getId();

        // 同じ商品がすでにカートに存在するかチェックする
        // containsKey(): Map に指定したキーが存在すれば true を返す
        if (items.containsKey(productId)) {
            // すでにカートにある場合 → 既存の CartItem の数量に加算する
            // 例: カートに「USBメモリ x2」があり、さらに1個追加 → 「USBメモリ x3」にする

            // Map から既存の CartItem を取得する
            CartItem existing = items.get(productId);

            // 新しい合計数量を計算する（既存数量 + 追加数量）
            int newQty = existing.getQuantity() + quantity;

            // 合計数量が在庫を超えないかチェックする
            // 例: 在庫5個、カートに3個、さらに3個追加 → 合計6個 > 在庫5個 → エラー
            if (newQty > product.getStock()) {
                throw new IllegalArgumentException("在庫を超えています");
            }

            // 既存の CartItem の数量を更新する
            existing.setQuantity(newQty);
        } else {
            // カートに存在しない場合 → 新しい CartItem を作成して Map に追加する
            // Map.put(): キーと値のペアを Map に格納する
            // 同じキーが存在しない場合は新規追加される
            items.put(productId, new CartItem(product, quantity));
        }
    }

    // ========================================================================
    // カートから商品を削除するメソッド
    // ========================================================================

    // 指定した商品IDの商品をカートから完全に削除する
    // 【Webアプリでの呼び出し元】
    // CartServlet.doPost() → action が "remove" の場合に呼ばれる
    // productId はリクエストパラメータから取得した値
    // 【セキュリティ注意】 productId の入力値検証を行うべき
    // 不正な値（SQLインジェクション、負の値等）が送られる可能性がある
    // int 型なのでSQLインジェクションのリスクは低いが、存在しないIDの場合も安全に動作する
    // Map.remove() は指定したキーが存在しない場合は何もしない（例外は発生しない）
    public void removeItem(int productId) {
        items.remove(productId); // 指定された商品IDのエントリを Map から削除する
    }

    // ========================================================================
    // カート内の商品数量を変更するメソッド
    // ========================================================================

    // 指定した商品IDの数量を変更する
    // 数量が0以下の場合は、その商品をカートから削除する
    // 【Webアプリでの使い方】
    // カート画面で数量入力欄を変更して「更新」ボタンを押した時に呼ばれる
    // リクエストパラメータ: productId（商品ID）、quantity（新しい数量）
    public void updateQuantity(int productId, int quantity) {
        // 指定された商品IDのCartItemをMapから取得する
        // 存在しない場合は null が返る
        CartItem item = items.get(productId);

        // カートに存在しない商品の数量変更はエラーとする
        // 不正なリクエストや、別タブで既に削除された商品の場合に発生する
        if (item == null) {
            throw new IllegalArgumentException("商品がカートにありません");
        }

        // 数量が0以下の場合は、カートから商品を削除する
        // 「数量を0にする = 削除」と同義とするUXデザイン
        if (quantity <= 0) {
            items.remove(productId); // 数量0以下なのでカートから削除する
        } else {
            // 数量が1以上の場合は、CartItem の数量を更新する
            // 【改善ポイント】 ここでも在庫チェックを行うべき
            // 現在の実装では、在庫を超える数量に変更できてしまう
            // 対策: if (quantity > item.getProduct().getStock()) { throw ... }
            item.setQuantity(quantity); // 新しい数量を設定する
        }
    }

    // ========================================================================
    // カート内の全商品を取得するメソッド
    // ========================================================================

    // カート内の全商品を List として返す
    // 【戻り値について】
    // new ArrayList<>(items.values()) で新しいリストを生成して返す（防御的コピー）
    // これにより、呼び出し元がリストを変更しても、内部の Map に影響しない
    // 例: 呼び出し元が list.clear() しても、items Map の中身は変わらない
    // 【Webアプリでの使い方】
    // CartServlet.doGet() で cart.getItems() を呼び、
    // request.setAttribute("cartItems", items) で JSP に渡す
    // JSP では <c:forEach items="${cartItems}" var="item"> でループ表示する
    public List<CartItem> getItems() {
        return new ArrayList<>(items.values()); // Map の値（CartItem）を新しい ArrayList にコピーして返す
    }

    // ========================================================================
    // 合計金額を計算するメソッド
    // ========================================================================

    // カート内の全商品の合計金額（税込）を計算して返す
    // Stream API を使って宣言的に記述している
    // 【計算の流れ】
    // 1. items.values() で全 CartItem のコレクションを取得
    // 2. .stream() でストリームに変換
    // 3. .mapToInt(CartItem::getSubtotal) で各CartItemの小計（int）に変換
    //    CartItem::getSubtotal はメソッド参照（item -> item.getSubtotal() と同義）
    // 4. .sum() で全小計を合算
    //
    // 【Webアプリでの使い方】
    // CartServlet で request.setAttribute("total", cart.getTotalAmount()) として JSP に渡す
    // JSP では ${total} で合計金額を表示する
    public int getTotalAmount() {
        return items.values().stream()       // Map の全値（CartItem）をストリームに変換する
            .mapToInt(CartItem::getSubtotal)  // 各 CartItem の小計を int に変換する
            .sum();                           // 全小計を合計して返す
    }

    // ========================================================================
    // カートが空かどうかを判定するメソッド
    // ========================================================================

    // カートが空（商品が1つも入っていない）かどうかを返す
    // 決済処理の前にカートが空でないことを確認するために使用する
    // 空のカートで決済を実行しようとした場合、PaymentService がエラーをスローする
    // 【Webアプリでの使い方】
    // CheckoutServlet で cart.isEmpty() をチェックし、
    // 空の場合はエラーメッセージ付きでカート画面にリダイレクトする
    public boolean isEmpty() {
        return items.isEmpty(); // Map が空（要素数0）なら true、そうでなければ false を返す
    }

    // ========================================================================
    // カートをクリアするメソッド
    // ========================================================================

    // カート内の全商品を削除する（カートを空にする）
    // 決済完了後に PaymentService.checkout() から呼ばれる
    // ユーザーが「カートを空にする」ボタンを押した場合にも使用する
    // Map.clear() は Map 内の全エントリを削除する（Map オブジェクト自体は残る）
    public void clear() {
        items.clear(); // Map 内の全エントリ（商品ID → CartItem のペア）を削除する
    }

    // ========================================================================
    // カート内容をコンソールに表示するメソッド（デバッグ・デモ用）
    // ========================================================================

    // カート内の全商品と合計金額をコンソールに出力する
    // 【このメソッドの用途】
    // - Main クラスのコンソールデモで、カートの内容を確認するために使用する
    // - Webアプリでは、JSP がカート内容の表示を担当するため、このメソッドは使わない
    //   （表示ロジックは View 層の責務であり、Service 層が直接出力するのはMVC違反）
    // - ただし、デバッグ時のログ出力には有用
    public void printCart() {
        // カートが空の場合は「カートは空です。」と表示して処理を終了する
        // return で早期リターンすることで、以降の処理をスキップする（ガード節パターン）
        if (isEmpty()) {
            System.out.println("カートは空です。"); // カートが空であることを通知する
            return; // メソッドを終了する（以降の表示処理をスキップ）
        }

        // カート内容のヘッダーを表示する
        System.out.println("--- カート内容 ---");

        // 拡張for文（for-each）で Map の全値（CartItem）を順番に処理する
        // LinkedHashMap を使用しているため、商品を追加した順番で表示される
        // item.toString() が自動的に呼ばれ、「  商品名 x数量 = 小計円」の形式で出力される
        for (CartItem item : items.values()) {
            System.out.println(item); // CartItem の toString() が呼ばれて1行表示される
        }

        // 合計金額を表示する
        // printf() はフォーマット指定付きの出力メソッド
        // %,d: カンマ区切りの整数表示（例: 12345 → "12,345"）
        // %n: 環境に応じた改行コード
        System.out.printf("合計: %,d円%n", getTotalAmount()); // 合計金額をカンマ区切りで表示する
    }
}
