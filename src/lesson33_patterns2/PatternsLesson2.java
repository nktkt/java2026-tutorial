package lesson33_patterns2;

// java.util パッケージ: コレクションフレームワーク（List, Map, Set等）や
// その他のユーティリティクラスを含むパッケージ
// ArrayList, List などの Observer パターンのデモで使用する
import java.util.*;

/**
 * ===== レッスン33: デザインパターン（振る舞い） =====
 *
 * 【振る舞いパターンとは？】
 *   オブジェクト間の効率的なコミュニケーションや責任の割り当てに関するパターン。
 *   前回（Lesson 32）の生成パターンが「オブジェクトの作り方」に焦点を当てるのに対し、
 *   振る舞いパターンは「オブジェクトの動き方・連携の仕方」に焦点を当てる。
 *
 * 【1. Strategy パターン】
 *   アルゴリズムをインターフェースで抽象化し、実行時に切り替える。
 *   例: ソートアルゴリズムの切り替え、支払い方法の切り替え
 *   ※ Comparator は Strategy パターンの典型例
 *   【利点】if-else の分岐を減らし、新しいアルゴリズムの追加が容易
 *
 * 【2. Observer パターン】
 *   あるオブジェクトの状態変化を、他のオブジェクトに自動通知する。
 *   例: イベントリスナー、SNSのフォロー通知、MVC のView更新
 *   【別名】Pub-Sub（Publish-Subscribe）パターン
 *   【利点】通知する側と受け取る側が疎結合（互いの詳細を知らなくてよい）
 *
 * 【3. Template Method パターン】
 *   処理の骨格を親クラスで定義し、詳細を子クラスで実装する。
 *   例: フレームワークの「ここだけ実装してね」パターン
 *   【利点】共通の処理フローを統一でき、カスタマイズ可能な部分だけ子クラスに任せる
 *
 * 【よくあるミス】
 *   1. Strategy: 戦略の切り替えが不要な場面で使う（過剰設計）
 *   2. Observer: 通知の循環（AがBに通知→BがAに通知→無限ループ）
 *   3. Template Method: 継承の階層が深くなりすぎる
 */
public class PatternsLesson2 {

    // ========== Strategy パターン ==========
    // 【Strategyとは？】処理のアルゴリズム（戦略）をオブジェクトとしてカプセル化し、
    // 実行時に自由に入れ替えられるようにするパターン
    // 【身近な例】ECサイトの支払い方法（クレジット、現金、PayPay等）を切り替える
    // 各支払い方法が独立したクラスで、共通インターフェースを通じて呼び出される

    // PaymentStrategy インターフェース: 支払い戦略の共通契約（抽象化）
    // 全ての具体的な支払い方法がこのインターフェースを実装する
    // pay(int amount) メソッドで金額を受け取り、支払い処理を行う
    // 【設計原則】「実装ではなくインターフェースに対してプログラムせよ」
    // interface PaymentStrategy {
    //     void pay(int amount);
    // }

    // CreditCard クラス: クレジットカード支払いの具体的な戦略
    // PaymentStrategy インターフェースを実装し、pay() の中身を定義する
    // static class CreditCard implements PaymentStrategy {

    //     pay() メソッドの具体的な実装: クレジットカードでの支払い処理
    //     実際のアプリでは決済API呼び出しやカード情報検証などの処理が入る
    //     public void pay(int amount) { System.out.println("クレジット: " + amount + "円"); }

    // }

    // Cash クラス: 現金支払いの具体的な戦略
    // 同じ PaymentStrategy インターフェースを実装しているが、処理内容が異なる
    // static class Cash implements PaymentStrategy {

    //     pay() メソッドの具体的な実装: 現金での支払い処理
    //     public void pay(int amount) { System.out.println("現金: " + amount + "円"); }

    // }

    // ShoppingCart クラス: 買い物カート。戦略（支払い方法）を保持し、精算時に使う
    // 【重要】カート自体は支払い方法の詳細を知らない（PaymentStrategy インターフェースだけ知っている）
    // これにより、新しい支払い方法（例: QRコード決済）を追加しても ShoppingCart の修正が不要
    // static class ShoppingCart {

    //     strategy フィールド: 現在設定されている支払い戦略を保持する
    //     PaymentStrategy 型なので、CreditCard でも Cash でも格納できる（ポリモーフィズム）
    //     private PaymentStrategy strategy;

    //     setStrategy() で支払い戦略を設定または切り替える
    //     【ポイント】実行時に戦略を動的に切り替えられることが Strategy パターンの核心
    //     void setStrategy(PaymentStrategy s) { this.strategy = s; }

    //     checkout() で精算を行う。設定された戦略の pay() を呼び出す
    //     ShoppingCart は「どうやって支払うか」を知らず、strategy に委譲している
    //     void checkout(int amount) { strategy.pay(amount); }

    // }

    // ========== Observer パターン ==========
    // 【Observerとは？】あるオブジェクト（Subject/Publisher）の状態が変化したとき、
    // 登録されている全てのオブジェクト（Observer/Subscriber）に自動で通知するパターン
    // 【身近な例】YouTubeのチャンネル登録: 新動画が投稿されたら登録者全員に通知が届く
    // 【JavaのAPI例】Swing の ActionListener、JavaFX の Observable、PropertyChangeListener

    // Observer インターフェース: 通知を受け取る側の共通契約
    // update(String message) で通知メッセージを受け取る
    // interface Observer {
    //     void update(String message);
    // }

    // NewsAgency クラス: ニュース配信者（Subject/Publisher の役割）
    // 購読者（Observer）を管理し、ニュースが出たら全員に通知する
    // static class NewsAgency {

    //     observers リスト: 登録されている全ての Observer を保持するリスト
    //     ArrayList を使用して動的にサイズが変わるリストとして管理
    //     private List<Observer> observers = new ArrayList<>();

    //     subscribe() で新しい Observer を登録する
    //     登録された Observer は、publish() 時に通知を受け取る
    //     【実務】unsubscribe() メソッドも用意して、登録解除できるようにするのが一般的
    //     void subscribe(Observer o) { observers.add(o); }

    //     publish() でニュースを全ての登録済み Observer に通知する
    //     for-each ループで observers リストを順に走査し、各 Observer の update() を呼ぶ
    //     【ポイント】NewsAgency は Observer の具体的なクラスを知らない（インターフェースだけ知っている）
    //     新しい種類の Observer（例: メール通知、プッシュ通知）を追加しても NewsAgency の修正は不要
    //     void publish(String news) {
    //         for (Observer o : observers) o.update(news);
    //     }

    // }

    // User クラス: Observer インターフェースを実装した具体的な購読者
    // ニュースを受け取ったときの振る舞いを定義する
    // static class User implements Observer {

    //     name フィールド: ユーザー名。通知表示時にどのユーザーに届いたか識別するため
    //     private String name;

    //     コンストラクタ: ユーザー名を受け取って初期化する
    //     User(String name) { this.name = name; }

    //     update() メソッド: Observer インターフェースの実装
    //     publish() から呼ばれ、ニュースメッセージを受け取って処理する
    //     ここでは単に名前付きで通知内容を表示している
    //     実際のアプリでは、UIの更新、メール送信、ログ記録など様々な処理が考えられる
    //     public void update(String message) {
    //         System.out.println(name + "に通知: " + message);
    //     }

    // }

    // ========== Template Method パターン ==========
    // 【Template Methodとは？】処理の大まかな流れ（テンプレート）を親クラスで定義し、
    // 各ステップの具体的な実装を子クラスに任せるパターン
    // 【身近な例】料理のレシピ: 「準備→調理→盛り付け」の流れは同じだが、料理ごとに中身が違う
    // 【Javaの実例】HttpServlet の doGet()/doPost()、JUnit のsetUp()/tearDown()
    // 【重要】テンプレートメソッド自体は final にして、子クラスが変更できないようにする

    // DataProcessor: データ処理の骨格（テンプレート）を定義する抽象クラス
    // abstract クラスなので直接インスタンス化できない。子クラスで具体化する必要がある
    // static abstract class DataProcessor {

    //     process() がテンプレートメソッド: 処理の全体フローを定義する
    //     final 修飾子により、子クラスでこのメソッドをオーバーライドできないようにしている
    //     【なぜ final？】フローの順序を変えられると、意図しない動作になる可能性がある
    //     「readData → processData → writeData」の順序は固定したいので final にする
    //     final void process() {
    //         ① まずデータを読み込む（具体的な方法は子クラスが決める）
    //         readData();
    //         ② 次にデータを処理する（具体的な方法は子クラスが決める）
    //         processData();
    //         ③ 最後にデータを出力する（デフォルト実装があるが、子クラスで変更も可能）
    //         writeData();
    //     }

    //     abstract メソッド: 子クラスが必ず実装しなければならないメソッド
    //     abstract なので本体（{}）を持たない
    //     データの読み込み方法は形式（CSV, JSON, XML等）によって異なるため、子クラスに委ねる
    //     abstract void readData();      // 子クラスが実装

    //     abstract メソッド: データの処理ロジックも子クラスごとに異なる
    //     abstract void processData();   // 子クラスが実装

    //     writeData() はデフォルト実装を持つ通常のメソッド（abstract ではない）
    //     多くのデータ形式で共通の出力方法を使えるなら、デフォルトを用意しておく
    //     子クラスは必要に応じてオーバーライドして独自の出力方法を定義できる
    //     【デザインの選択肢】全メソッドをabstractにするか、一部にデフォルト実装を提供するか
    //     void writeData() { System.out.println("  デフォルト出力"); } // デフォルト実装

    // }

    // CsvProcessor: DataProcessor を継承して CSV 処理に特化した子クラス
    // テンプレート（process()のフロー）はそのままに、各ステップの中身だけを定義する
    // static class CsvProcessor extends DataProcessor {

    //     readData() の CSV 版: CSV ファイルの読み込み処理を実装する
    //     実際のアプリでは BufferedReader で CSV を読み込み、行ごとにパースする
    //     void readData() { System.out.println("  CSVを読み込み"); }

    //     processData() の CSV 版: CSV データの加工処理を実装する
    //     実際のアプリではフィルタリング、集計、変換などの処理を行う
    //     void processData() { System.out.println("  CSVを処理"); }

    //     writeData() はオーバーライドしていないため、親クラスのデフォルト実装が使われる
    //     必要に応じて「CSVとして出力」に変更することもできる

    // }

    // main メソッド: 3つの振る舞いデザインパターンのデモを実行する
    public static void main(String[] args) {

        // --- Strategy パターンの使用例 ---

        // ShoppingCart のインスタンスを作成する
        // この時点ではまだ支払い戦略が設定されていない（strategy は null）
        // ShoppingCart cart = new ShoppingCart();

        // setStrategy() でクレジットカード支払いの戦略を設定する
        // new CreditCard() で具体的な戦略オブジェクトを作り、カートに注入する
        // 【設計用語】これは「依存性の注入（DI: Dependency Injection）」の一形態
        // cart.setStrategy(new CreditCard());  // 戦略を設定

        // checkout(1000) で 1000円 の精算を行う
        // 内部では CreditCard の pay(1000) が呼ばれる
        // 出力: "クレジット: 1000円"
        // cart.checkout(1000);

        // setStrategy() で戦略を現金支払いに「動的に切り替え」る
        // 同じカートオブジェクトでも、戦略を変えるだけで支払い方法が変わる
        // 【Strategy パターンの核心】実行時にアルゴリズムを入れ替えられること
        // if-else で分岐するよりも拡張性が高い（新しい支払い方法はクラスを追加するだけ）
        // cart.setStrategy(new Cash());        // 戦略を切り替え

        // 今度は現金支払いの戦略で 500円 を精算する
        // 内部では Cash の pay(500) が呼ばれる
        // 出力: "現金: 500円"
        // cart.checkout(500);

        // --- Observer パターンの使用例 ---

        // NewsAgency（ニュース配信者）のインスタンスを作成する
        // この時点では購読者がいないため、publish() しても誰にも通知されない
        // NewsAgency agency = new NewsAgency();

        // subscribe() で「田中」ユーザーを購読者として登録する
        // これ以降、agency.publish() が呼ばれると「田中」に通知が届く
        // agency.subscribe(new User("田中"));

        // subscribe() で「鈴木」ユーザーも購読者として登録する
        // 複数の Observer を登録できるのが Observer パターンの特徴
        // agency.subscribe(new User("鈴木"));

        // publish() でニュースを配信する
        // 登録済みの全 Observer（田中、鈴木）の update() が順番に呼ばれる
        // 出力:
        //   "田中に通知: Java 25 リリース！"
        //   "鈴木に通知: Java 25 リリース！"
        // 【ポイント】agency は田中や鈴木の具体的な処理を知らない。update() を呼ぶだけ
        // agency.publish("Java 25 リリース！");

        // --- Template Method パターンの使用例 ---

        // CsvProcessor のインスタンスを DataProcessor 型の変数に代入する
        // 親クラスの型で参照することで、ポリモーフィズムを活用
        // 将来 JsonProcessor や XmlProcessor を作っても、同じ DataProcessor 型で扱える
        // DataProcessor processor = new CsvProcessor();

        // process() を呼ぶと、テンプレートメソッドの定義通りに
        // readData() → processData() → writeData() の順に実行される
        // 出力:
        //   "  CSVを読み込み"    ← CsvProcessor の readData()
        //   "  CSVを処理"        ← CsvProcessor の processData()
        //   "  デフォルト出力"    ← DataProcessor の writeData()（デフォルト実装）
        // 【ポイント】呼び出し側は process() を呼ぶだけ。処理の流れは親クラスが制御する
        // processor.process();
    }
}
