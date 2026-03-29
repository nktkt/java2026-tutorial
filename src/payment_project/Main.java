// ============================================================================
// パッケージ宣言: このクラスが payment_project パッケージに属することを示す
// Main クラスはプロジェクトのルートパッケージに配置し、
// エントリーポイント（プログラムの開始点）として機能する
// ============================================================================
package payment_project;

// model パッケージの全クラス（Product, CartItem, Order）をインポートする
// ワイルドカード（*）インポートにより、パッケージ内の全 public クラスが利用可能になる
import payment_project.model.*;

// service パッケージの全クラス（CartService, PaymentService）をインポートする
import payment_project.service.*;

/**
 * ===== 決済プロジェクト: メイン（コンソール版デモ） =====
 *
 * 【このクラスの目的】
 * Tomcat（Webサーバー）なしで決済処理の流れを体験できるコンソールデモ
 * ここで学んだ構造をそのまま Servlet + JSP に置き換えれば Webアプリケーションになる
 *
 * 【MVCアーキテクチャとの対応】
 * - Model: Product, CartItem, Order → データ構造を表す
 * - View: System.out.println() → Webアプリでは JSP に置き換わる
 * - Controller: main() メソッドの処理フロー → Webアプリでは Servlet に置き換わる
 * - Service: CartService, PaymentService → MVCのどちらにも依存せず再利用可能
 *
 * 【学べること】
 * - Model（Product, CartItem, Order）の設計とデータの流れ
 * - Service（CartService, PaymentService）の責務分離
 * - Strategy パターンによる支払い方法の切り替え
 * - 例外処理（try-catch）による異常系の制御
 * - enum（Order.Status）によるステータス管理
 * - Stream API による集計処理
 *
 * 【Webアプリへの移行手順】
 * 1. Product データ → ProductDAO（DB操作クラス）から取得するように変更
 * 2. CartService → HttpSession に保存し、ユーザーごとに管理
 * 3. main() の処理フロー → 各 Servlet の doGet()/doPost() に分割
 * 4. System.out.println() → JSP のEL式・JSTLタグに置き換え
 * 5. 例外処理 → エラーメッセージを request にセットし、エラーページに遷移
 */
public class Main {

    // main() メソッド: Javaプログラムのエントリーポイント（最初に実行されるメソッド）
    // JVM（Java仮想マシン）は、指定されたクラスの public static void main(String[] args) を探して実行する
    // 【修飾子の意味】
    //   public: どこからでもアクセス可能（JVMから呼び出されるため public が必須）
    //   static: インスタンスを生成せずにクラスから直接呼び出せる（JVMが new Main() せずに呼べる）
    //   void: 戻り値なし
    //   String[] args: コマンドライン引数を受け取る配列（java Main arg1 arg2 → args[0]="arg1", args[1]="arg2"）
    public static void main(String[] args) {

        // ====================================================================
        // ステップ 1. 商品データの作成（本来はDBから取得する）
        // ====================================================================
        // Product 配列を初期化する
        // 実際のWebアプリでは、ProductDAO.findAll() でDBから取得する:
        //   List<Product> products = ProductDAO.findAll();
        //   → SELECT * FROM products WHERE stock > 0 ORDER BY id
        // ここではデモ用にハードコーディングしている
        //
        // 配列の初期化構文: { 要素1, 要素2, ... }
        // 各要素は new Product(id, name, price, stock) で生成する
        Product[] products = {
            new Product(1, "Java入門書", 2800, 10),           // ID:1, 税抜2800円, 在庫10冊
            new Product(2, "USBメモリ 64GB", 1200, 50),       // ID:2, 税抜1200円, 在庫50個
            new Product(3, "ワイヤレスマウス", 3500, 5),        // ID:3, 税抜3500円, 在庫5個
            new Product(4, "プログラミングキーボード", 12000, 3), // ID:4, 税抜12000円, 在庫3台
        };

        // 商品一覧を表示する
        // Webアプリでは、ProductListServlet が products を JSP に渡し、
        // JSP の <c:forEach> タグで一覧表示する
        System.out.println("===== 商品一覧 =====");

        // 拡張for文で配列の全商品をループし、情報を表示する
        // 拡張for文（for-each）: 配列やコレクションの全要素を順番に処理する構文
        // 従来のfor文「for (int i = 0; i < products.length; i++)」よりも簡潔
        for (Product p : products) {
            // printf() でフォーマット付き出力
            // %d: 整数、%s: 文字列、%,d: カンマ区切りの整数、%n: 環境依存の改行
            // getTaxIncludedPrice() で税込価格を計算して表示する
            System.out.printf("  [%d] %s - %,d円（税込%,d円）在庫%d%n",
                p.getId(), p.getName(), p.getPrice(),
                p.getTaxIncludedPrice(), p.getStock());
        }

        // ====================================================================
        // ステップ 2. カートに商品を追加する
        // ====================================================================
        // CartService のインスタンスを生成する
        // Webアプリでは、セッションからカートを取得する:
        //   CartService cart = (CartService) session.getAttribute("cart");
        //   if (cart == null) { cart = new CartService(); session.setAttribute("cart", cart); }
        CartService cart = new CartService();

        // 3つの商品をカートに追加する
        // addItem() は内部で在庫チェックを行い、在庫不足の場合は例外をスローする
        // Webアプリでは CartServlet.doPost() の action="add" に相当する処理
        cart.addItem(products[0], 1);  // Java入門書を1冊カートに追加する
        cart.addItem(products[1], 2);  // USBメモリを2個カートに追加する
        cart.addItem(products[2], 1);  // ワイヤレスマウスを1個カートに追加する

        // カートの内容を表示する
        // printCart() はコンソールデモ用のメソッド
        // Webアプリでは、cart.getItems() の結果を JSP で表示する
        System.out.println("\n===== カートに追加 =====");
        cart.printCart(); // カート内の全商品と合計金額を出力する

        // ====================================================================
        // ステップ 3. 数量を変更する
        // ====================================================================
        // USBメモリ（ID:2）の数量を2個から3個に変更する
        // updateQuantity() は第1引数が商品ID、第2引数が新しい数量
        // 数量を0にするとカートから削除される
        // Webアプリでは、カート画面の「数量変更」フォームから送信された値で呼ばれる
        cart.updateQuantity(2, 3);  // 商品ID:2（USBメモリ）の数量を3に変更する

        // 数量変更後のカート内容を確認する
        System.out.println("\n===== 数量変更後 =====");
        cart.printCart(); // 更新後のカート内容を出力する

        // ====================================================================
        // ステップ 4. クレジットカードで決済する
        // ====================================================================
        // PaymentService のインスタンスを生成する
        // Webアプリでは、CheckoutServlet.doPost() 内で生成する
        // 【改善ポイント】 DI（Dependency Injection）を使えば、テスト時にモックに差し替えられる
        PaymentService paymentService = new PaymentService();

        // クレジットカード決済の PaymentMethod を生成する（Strategy パターン）
        // 16桁のカード番号を引数に渡す（デモ用のダミーカード番号）
        // Webアプリでは、checkout フォームのカード番号入力欄の値が渡される:
        //   new PaymentService.CreditCardPayment(request.getParameter("cardNumber"))
        //
        // 【Strategy パターンのポイント】
        // PaymentMethod 型の変数に CreditCardPayment インスタンスを代入している
        // この変数を BankTransferPayment や ConveniencePayment に差し替えるだけで
        // 支払い方法を切り替えられる（PaymentService.checkout() のコードは変更不要）
        PaymentService.PaymentMethod creditCard =
            new PaymentService.CreditCardPayment("1234567890123456");

        // try-catch で決済処理を実行し、エラーをハンドリングする
        // 【try-catch の役割】
        // checkout() メソッド内で例外（在庫不足、決済失敗等）が発生した場合、
        // プログラムが異常終了するのを防ぎ、エラーメッセージを表示する
        // Webアプリでは、catch 節でエラーメッセージを request に設定し、エラーページに遷移する
        try {
            // checkout() を呼び出して決済を実行する
            // 引数: カート、ユーザーID、支払い方法
            // 戻り値: 生成された Order オブジェクト（注文番号、合計金額、ステータス等を含む）
            // 内部で以下の処理が順に行われる:
            //   1. カートの検証（空でないか）
            //   2. 在庫チェック（全商品の在庫が十分か）
            //   3. 決済処理（CreditCardPayment.processPayment() 呼び出し）
            //   4. 注文レコード作成（Order オブジェクト生成）
            //   5. 在庫更新（在庫を減らす）
            //   6. カートクリア（カートを空にする）
            Order order = paymentService.checkout(cart, "naoki", creditCard);

            // 決済成功: 注文詳細を表示する
            // order.toString() が自動的に呼ばれ、注文番号・商品一覧・合計金額等が出力される
            // Webアプリでは request.setAttribute("order", order) で JSP に渡す
            System.out.println("\n" + order);

        } catch (Exception e) {
            // 決済失敗: エラーメッセージを表示する
            // e.getMessage() で例外に設定されたメッセージを取得する
            // 例: "決済に失敗しました"、"カートが空です"、"〇〇の在庫が不足しています"
            // Webアプリでは request.setAttribute("error", e.getMessage()) でエラーページに遷移
            System.out.println("決済エラー: " + e.getMessage());
        }

        // ====================================================================
        // ステップ 5. 在庫確認（決済後に在庫が減っていることを確認する）
        // ====================================================================
        // 決済処理（checkout）内で、購入数量分だけ在庫が減らされている
        // Product オブジェクトは参照渡しなので、PaymentService で setStock() した変更が
        // ここで確認できる
        // 例: Java入門書は元々10冊 → 1冊購入 → 9冊に減っているはず
        System.out.println("===== 在庫確認 =====");

        // 全商品の現在の在庫数を表示する
        for (Product p : products) {
            System.out.printf("  %s: 在庫 %d%n", p.getName(), p.getStock());
        }

        // ====================================================================
        // ステップ 6. 別の支払い方法（コンビニ払い）を試す
        // ====================================================================
        // Strategy パターンの利点を実証: 支払い方法を変えるだけで同じ checkout() メソッドが使える
        // CreditCardPayment → ConveniencePayment に差し替えるだけ
        System.out.println("\n===== コンビニ払いのテスト =====");

        // 新しい商品をカートに追加する（前回の checkout() でカートはクリアされている）
        // カートが空のため、新たに商品を追加する必要がある
        cart.addItem(products[3], 1);  // プログラミングキーボードを1台カートに追加する

        // コンビニ払いの PaymentMethod を生成する
        // コンストラクタに引数は不要（支払番号は processPayment() 内で自動生成される）
        PaymentService.PaymentMethod convenience =
            new PaymentService.ConveniencePayment();

        // コンビニ払いで決済を実行する
        // checkout() メソッドは全く同じだが、paymentMethod の具体型が異なるため
        // processPayment() の振る舞いが変わる（ポリモーフィズム）
        // → クレジットカード: カード番号で即時決済
        // → コンビニ払い: 支払番号を発行して表示
        try {
            // コンビニ払いで決済を実行する
            Order order2 = paymentService.checkout(cart, "naoki", convenience);

            // 注文詳細を表示する（支払方法が "convenience" になっているはず）
            System.out.println("\n" + order2);

        } catch (Exception e) {
            // エラーが発生した場合はメッセージを表示する
            System.out.println("決済エラー: " + e.getMessage());
        }

        // ====================================================================
        // ステップ 7. エラーケースのテスト（空のカートで決済を試みる）
        // ====================================================================
        // 正常系だけでなく、異常系（エラーケース）のテストも重要
        // ここでは、カートが空の状態で checkout() を呼び出し、
        // 適切にエラーハンドリングされることを確認する
        // 【期待される結果】
        // PaymentService.checkout() 内の cart.isEmpty() チェックに引っかかり、
        // IllegalStateException("カートが空です") がスローされる
        // → catch 節で "エラー: カートが空です" と表示される
        System.out.println("\n===== エラーケース: 空カートで決済 =====");

        try {
            // 空のカートで決済を試みる（ステップ6の checkout() でカートはクリア済み）
            // この呼び出しは必ず例外をスローするはず
            paymentService.checkout(cart, "naoki", creditCard);

        } catch (Exception e) {
            // 予期されたエラー: "カートが空です" というメッセージが表示される
            // Webアプリでは、このエラーメッセージをユーザーに表示し、
            // カート画面や商品一覧画面にリダイレクトする
            System.out.println("エラー: " + e.getMessage());
        }
    }
}
