// ============================================================================
// パッケージ宣言: このクラスが payment_project.service パッケージに属することを示す
// CartService と同じ service パッケージに配置し、ビジネスロジック層を構成する
// ============================================================================
package payment_project.service;

// model パッケージの全クラス（Product, CartItem, Order）をインポートする
// ワイルドカード（*）インポートにより、個別のクラス名を列挙しなくて済む
import payment_project.model.*;

// java.util.List をインポート: CartItem のリストを扱うために必要
import java.util.List;

/**
 * 決済処理サービスクラス
 *
 * 【デザインパターン】
 * ★ Strategy パターン（戦略パターン）を適用している ★
 * - PaymentMethod インターフェースで「支払い方法」という振る舞いを抽象化
 * - CreditCardPayment, BankTransferPayment, ConveniencePayment が具体的な戦略（Strategy）
 * - checkout() メソッドが PaymentMethod を引数で受け取り、支払い方法を動的に切り替える
 * - 新しい支払い方法（PayPay、楽天ペイ等）を追加する際、既存コードを変更せずに
 *   新しいクラスを追加するだけでよい（OCP: 開放閉鎖原則）
 *
 * 【Strategy パターンの構造】
 *   PaymentMethod（インターフェース）← 抽象戦略
 *       ├── CreditCardPayment    ← 具体戦略1
 *       ├── BankTransferPayment   ← 具体戦略2
 *       └── ConveniencePayment    ← 具体戦略3
 *   PaymentService.checkout()     ← コンテキスト（戦略を利用する側）
 *
 * 【責務（このクラスが担当すること）】
 * - 決済の実行（カート → 注文作成の一連の処理）
 * - 支払い方法の検証と実行
 * - 在庫の更新（決済成功後に在庫を減らす）
 * - エラーハンドリング（カート空チェック、在庫不足、決済失敗）
 *
 * 【実際の商用システムでの考慮事項】
 * - 外部の決済API（Stripe, PayPay, Square, GMOペイメント等）との連携
 * - トランザクション管理: DB操作は「全成功 or 全ロールバック」にする
 *   → @Transactional アノテーション（Spring）や、手動での Connection.commit()/rollback()
 * - 冪等性（べきとうせい）の保証: 同じリクエストが2回来ても二重決済にならないようにする
 *   → リクエストIDやトランザクションIDで重複チェックする
 * - 監査ログ: 決済の成功・失敗をログに記録する（法的要件にも関わる）
 * - タイムアウト処理: 外部APIの応答が遅い場合のハンドリング
 *
 * 【セキュリティに関する注意点】
 * - カード番号は PCI DSS 基準に準拠して取り扱う必要がある
 *   → 通常はカード番号を自サーバーに保存せず、決済代行サービスのトークンを使う
 * - ログにカード番号を出力してはならない（マスキングが必須）
 * - 決済金額はサーバーサイドで計算し、クライアントからの値を信用しない
 */
public class PaymentService {

    // ========================================================================
    // ★ Strategy パターン: 支払い方法インターフェース ★
    // ========================================================================

    // PaymentMethod インターフェース: 全ての支払い方法が実装すべき契約を定義する
    // 【インターフェースの役割】
    // - 「支払い処理」と「名前取得」という2つの振る舞いを抽象的に定義する
    // - checkout() メソッドは具体的な支払い方法を知らなくても、
    //   このインターフェースを通じて一律に決済処理を呼び出せる（多態性 / ポリモーフィズム）
    // - 新しい支払い方法を追加する場合は、このインターフェースを実装するだけでよい
    //
    // 【Webアプリでの使い方】
    // CheckoutServlet で、リクエストパラメータ "paymentMethod" の値に基づいて
    // 適切な実装クラスのインスタンスを生成する（ファクトリ的な使い方）
    public interface PaymentMethod {
        // 決済処理を実行するメソッド
        // 引数: amount（決済金額、税込）
        // 戻り値: 決済成功なら true、失敗なら false
        // 実際のシステムでは、決済APIのレスポンスに基づいて true/false を返す
        boolean processPayment(int amount);

        // 支払い方法の名前を返すメソッド
        // Order に保存する際や、ログ出力時に使用する
        // 戻り値: "credit", "bank", "convenience" 等の識別文字列
        String getName();
    }

    // ========================================================================
    // ★ 具体戦略1: クレジットカード決済 ★
    // ========================================================================

    // CreditCardPayment: クレジットカードによる決済を実装するクラス
    // PaymentMethod インターフェースを実装（implements）する
    // 【static 内部クラスの意味】
    // static を付けることで、PaymentService のインスタンスなしに生成できる
    // 使用例: new PaymentService.CreditCardPayment("1234567890123456")
    // static がないと、外部クラスのインスタンスが必要になる
    //
    // 【実運用での改善点】
    // - カード番号の代わりに決済代行サービスのトークンを受け取るべき
    // - カード番号の Luhn アルゴリズムによるバリデーション
    // - 有効期限、セキュリティコード（CVV）のチェック
    // - 3Dセキュア認証の実装
    public static class CreditCardPayment implements PaymentMethod {
        // クレジットカード番号を保持するフィールド
        // final にすることで、一度設定されたら変更できないようにする（不変性の保証）
        // 【セキュリティ警告】 実運用では、カード番号をメモリ上に保持する時間を最小限にすべき
        // PCI DSS では、カード番号の平文保存を厳しく制限している
        private final String cardNumber;

        // コンストラクタ: カード番号を受け取って保持する
        // 【Webアプリでの呼び出し】
        // CheckoutServlet で new CreditCardPayment(request.getParameter("cardNumber"))
        // として、フォームから送信されたカード番号を渡す
        // 【セキュリティ注意】 カード番号はHTTPSで暗号化されて送信されるべき
        public CreditCardPayment(String cardNumber) {
            this.cardNumber = cardNumber; // 引数のカード番号をフィールドに代入する
        }

        // 決済処理の実装: クレジットカードで指定金額を決済する
        // 【実際のシステムでは】
        // - Stripe API: stripe.charges.create({amount: amount, source: token})
        // - GMOペイメント: ExecTran API を呼び出す
        // - レスポンスの成功/失敗コードに基づいて true/false を返す
        @Override
        public boolean processPayment(int amount) {
            // 決済処理のログ出力（実運用ではログフレームワーク（SLF4J + Logback）を使用する）
            // カード番号の下4桁だけを表示する（マスキング処理）
            // substring(length - 4): 文字列の末尾4文字を取得する
            // 例: "1234567890123456" → "3456"
            // ****3456 のように表示して、番号の大部分を隠す（セキュリティ対策）
            System.out.printf("  [クレジット] カード: ****%s で %,d円を決済中...%n",
                cardNumber.substring(cardNumber.length() - 4), amount);

            // カード番号の簡易バリデーション: 16桁であることをチェックする
            // 【注意】 これは非常に簡易的なバリデーションで、デモ用
            // 実運用では以下のバリデーションが必要:
            // - Luhn アルゴリズムによるチェックディジット検証
            // - カード番号の先頭桁でブランド判定（4: Visa, 5: Mastercard, 3: AMEX 等）
            // - 有効期限の検証
            // - CVV（セキュリティコード）の検証
            // - 3Dセキュア認証
            if (cardNumber.length() != 16) {
                System.out.println("  [エラー] カード番号が不正です"); // エラーメッセージを出力する
                return false; // 決済失敗を示す false を返す
            }

            // 決済成功のメッセージを出力する
            System.out.println("  [クレジット] 決済成功！");
            return true; // 決済成功を示す true を返す
        }

        // 支払い方法の名前を返す: "credit" という識別文字列を返す
        // Order の paymentMethod フィールドに保存される
        @Override
        public String getName() { return "credit"; }
    }

    // ========================================================================
    // ★ 具体戦略2: 銀行振込 ★
    // ========================================================================

    // BankTransferPayment: 銀行振込による決済を実装するクラス
    // クレジットカードと異なり、即時決済ではなく「振込依頼の作成」を行う
    // 入金確認は別途のバッチ処理や手動確認で行われる
    //
    // 【実運用での処理フロー】
    // 1. 注文を PENDING 状態で作成
    // 2. ユーザーに振込先口座情報と振込金額を通知（メール + 画面表示）
    // 3. 振込期限（例: 3日以内）を設定
    // 4. 入金確認後に注文ステータスを COMPLETED に変更
    // 5. 振込期限を過ぎた場合は自動キャンセル
    public static class BankTransferPayment implements PaymentMethod {
        // 銀行振込の決済処理: 振込依頼を作成する
        // 即時決済ではないため、常に true を返す（依頼の作成自体は常に成功する）
        // 実際の入金は後から確認する
        @Override
        public boolean processPayment(int amount) {
            // 振込依頼の作成メッセージを出力する
            // %,d でカンマ区切りの金額表示（例: 12,345円）
            System.out.printf("  [銀行振込] %,d円の振込依頼を作成しました%n", amount);

            // 入金確認後に発送する旨を通知する
            // 実運用では、振込先口座情報もここで表示する
            // 例: 「三菱UFJ銀行 〇〇支店 普通 1234567 カ）〇〇ショップ」
            System.out.println("  [銀行振込] 入金確認後に発送します");

            return true; // 振込依頼の作成は常に成功する
        }

        // 支払い方法の名前を返す: "bank" という識別文字列を返す
        @Override
        public String getName() { return "bank"; }
    }

    // ========================================================================
    // ★ 具体戦略3: コンビニ払い ★
    // ========================================================================

    // ConveniencePayment: コンビニ払いによる決済を実装するクラス
    // コンビニのレジで支払番号を伝えて支払いを行う方式
    //
    // 【実運用での処理フロー】
    // 1. 決済代行サービス（GMOペイメント等）のAPIを呼び出して支払番号を取得
    // 2. ユーザーに支払番号と支払い期限を通知（メール + 画面表示）
    // 3. ユーザーがコンビニで支払いを行う
    // 4. 決済代行サービスから入金通知（Webhook）を受信
    // 5. 注文ステータスを COMPLETED に変更
    public static class ConveniencePayment implements PaymentMethod {
        // コンビニ払いの決済処理: 支払番号を生成して表示する
        @Override
        public boolean processPayment(int amount) {
            // 6桁のランダムな支払番号を生成する
            // Math.random(): 0.0 以上 1.0 未満の乱数を返す
            // * 900000 + 100000: 100000 ～ 999999 の範囲の数値を生成する
            // (int) でキャスト: 小数点以下を切り捨てて整数にする
            // String.valueOf(): int を String に変換する
            // 【セキュリティ注意】 Math.random() は暗号学的に安全ではない
            // 実運用では SecureRandom を使うか、決済代行サービスが番号を発行する
            String paymentCode = String.valueOf((int)(Math.random() * 900000) + 100000);

            // 支払番号と金額を表示する
            System.out.printf("  [コンビニ] 支払番号: %s (%,d円)%n", paymentCode, amount);

            // コンビニでの支払い方法を案内する
            // 実運用では、対応コンビニ一覧や支払い期限も表示する
            System.out.println("  [コンビニ] コンビニのレジで支払番号をお伝えください");

            return true; // 支払番号の発行は常に成功する
        }

        // 支払い方法の名前を返す: "convenience" という識別文字列を返す
        @Override
        public String getName() { return "convenience"; }
    }

    // ========================================================================
    // ★ 決済処理のメインメソッド ★
    // ========================================================================

    // checkout(): カートの内容を決済し、注文（Order）を作成する
    // これが決済処理の中心となるメソッド（ビジネスロジックの核心部分）
    //
    // 【引数の説明】
    // cart: ユーザーのショッピングカート（CartService オブジェクト）
    // userId: 注文するユーザーのID（Webアプリではセッションから取得する安全な値）
    // paymentMethod: 支払い方法（Strategy パターンのインターフェース）
    //   → 具体的な型（CreditCardPayment, BankTransferPayment 等）は実行時に決まる
    //   → これがポリモーフィズム（多態性）の実現
    //
    // 【戻り値】
    // Order: 作成された注文オブジェクト（ステータスは COMPLETED）
    //
    // 【例外】
    // IllegalStateException: カートが空の場合、在庫不足の場合
    // RuntimeException: 決済処理が失敗した場合
    //
    // 【処理フロー】
    // 1. カートの検証（空でないことを確認）
    // 2. 在庫チェック（全商品の在庫が十分かを確認）
    // 3. 決済処理（PaymentMethod.processPayment() を呼び出す）
    // 4. 注文レコード作成（Order オブジェクトを生成）
    // 5. 在庫の更新（購入数量分だけ在庫を減らす）
    // 6. カートのクリア（決済完了後にカートを空にする）
    //
    // 【Webアプリでの呼び出し元】
    // CheckoutServlet.doPost() から呼ばれる
    // try-catch で例外を捕捉し、エラー画面に遷移する
    //
    // 【トランザクション管理に関する注意】
    // 実運用では、この一連の処理を DB トランザクション内で実行する
    // 途中でエラーが発生した場合、全ての変更をロールバックする必要がある
    // 例: 在庫を減らした後に注文レコードの保存に失敗した場合 → 在庫を元に戻す
    public Order checkout(CartService cart, String userId, PaymentMethod paymentMethod) {
        // 決済処理開始のログ（区切り線付き）
        System.out.println("\n========== 決済処理開始 ==========");

        // ---- ステップ 1: カートの検証 ----
        // カートが空の場合は決済を実行できないので、例外をスローする
        // IllegalStateException: オブジェクトの状態が不正な場合に投げる標準例外
        // 「引数が不正」ではなく「状態が不正」なので IllegalState を使う
        if (cart.isEmpty()) {
            throw new IllegalStateException("カートが空です"); // カートが空の場合は処理中断
        }

        // カートから商品リストを取得する（防御的コピーされた List が返る）
        List<CartItem> items = cart.getItems();

        // カートの合計金額（税込）を取得する
        int totalAmount = cart.getTotalAmount();

        // 合計金額と支払い方法をログに出力する
        // %,d: カンマ区切りの整数表示（ロケールに依存）
        System.out.printf("合計金額: %,d円%n", totalAmount);
        System.out.println("支払方法: " + paymentMethod.getName()); // "credit", "bank" 等が表示される

        // ---- ステップ 2: 在庫チェック ----
        // 全商品について、カートの数量が在庫以下であることを確認する
        // 【なぜここでチェックするのか】
        // カートに追加した時点では在庫があっても、決済までの間に
        // 他のユーザーが先に購入して在庫が減っている可能性がある（競合状態）
        // → 決済直前に再度チェックすることで、在庫の整合性を保つ
        //
        // 【実運用での改善点】
        // - DB の SELECT ... FOR UPDATE で在庫を排他ロックし、
        //   他のトランザクションから在庫を変更されないようにする
        // - 楽観的ロック（Optimistic Lock）: バージョン番号で更新の競合を検出する
        System.out.println("\n--- 在庫チェック ---");

        // 拡張for文で全カートアイテムをループし、在庫を確認する
        for (CartItem item : items) {
            // CartItem から Product オブジェクトを取得する
            Product product = item.getProduct();

            // 要求数量が在庫を超えていないかチェックする
            if (item.getQuantity() > product.getStock()) {
                // 在庫不足の場合は、商品名と在庫数・要求数を含むエラーメッセージで例外をスローする
                // 【UXのポイント】 エラーメッセージに具体的な数量を含めることで、
                // ユーザーが何個なら購入できるかが分かる
                throw new IllegalStateException(
                    product.getName() + " の在庫が不足しています（在庫: "
                    + product.getStock() + ", 要求: " + item.getQuantity() + "）");
            }

            // 在庫チェック OK の商品を表示する（デバッグ・確認用）
            System.out.printf("  %s: OK (在庫%d, 要求%d)%n",
                product.getName(), product.getStock(), item.getQuantity());
        }

        // ---- ステップ 3: 決済処理 ----
        // PaymentMethod インターフェースの processPayment() を呼び出す
        // 【ポリモーフィズム】 paymentMethod の実際の型に応じて、
        // CreditCardPayment, BankTransferPayment, ConveniencePayment の
        // いずれかの processPayment() が実行される
        // checkout() メソッド自体は、どの支払い方法かを知る必要がない（疎結合）
        System.out.println("\n--- 決済実行 ---");
        boolean paymentSuccess = paymentMethod.processPayment(totalAmount); // 決済を実行し、成功/失敗を受け取る

        // 決済が失敗した場合は RuntimeException をスローする
        // 【実運用での改善点】
        // - 専用の PaymentFailedException（カスタム例外）を定義すべき
        // - 失敗理由（残高不足、カード期限切れ、通信エラー等）を例外に含める
        // - リトライ可能なエラーとリトライ不可のエラーを区別する
        if (!paymentSuccess) {
            throw new RuntimeException("決済に失敗しました"); // 決済失敗時は処理を中断する
        }

        // ---- ステップ 4: 注文レコード作成 ----
        // Order オブジェクトを生成する（コンストラクタ内で合計金額が計算される）
        Order order = new Order(userId, items, paymentMethod.getName());

        // ランダムな注文IDを設定する（10000 ～ 99999 の5桁の整数）
        // 【注意】 Math.random() は重複する可能性がある
        // 実運用では、DB の AUTO_INCREMENT や UUID を使って一意性を保証する
        order.setId((int)(Math.random() * 90000) + 10000);

        // 注文ステータスを COMPLETED（完了）に設定する
        // コンストラクタで PENDING に設定された後、決済成功を確認してから COMPLETED にする
        order.setStatus(Order.Status.COMPLETED);

        // ---- ステップ 5: 在庫を減らす ----
        // 決済が成功したので、各商品の在庫を購入数量分だけ減らす
        // 【重要】 この処理は決済成功後に行うべき（決済前に在庫を減らすと、
        //   決済失敗時に在庫を戻す処理が必要になる）
        // 【トランザクション管理】 実運用では、この在庫更新と注文レコードの保存を
        //   同一トランザクション内で行い、原子性（Atomicity）を保証する
        for (CartItem item : items) {
            Product product = item.getProduct();                            // CartItem から商品を取得する
            product.setStock(product.getStock() - item.getQuantity());      // 現在の在庫から購入数を引いて在庫を更新する
        }

        // ---- ステップ 6: カートをクリア ----
        // 決済完了後にカートを空にする
        // 次回の買い物のために、カートをリセットする
        // 【Webアプリでは】 セッション内の CartService がクリアされる
        cart.clear(); // カート内の全商品を削除する

        // 決済処理完了のログ
        System.out.println("\n========== 決済完了 ==========");

        // 作成された Order オブジェクトを呼び出し元に返す
        // Webアプリでは、この Order を request.setAttribute() で JSP に渡し、
        // 注文完了画面（complete.jsp）を表示する
        return order;
    }
}
