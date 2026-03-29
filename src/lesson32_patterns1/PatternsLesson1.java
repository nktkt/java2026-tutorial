package lesson32_patterns1;

/**
 * ===== レッスン32: デザインパターン（生成・構造） =====
 *
 * 【目標】よく使われる設計の型（パターン）を学ぶ
 *
 * 【デザインパターンとは？】
 *   ソフトウェア設計でよく出る問題の「定石」。再発明する必要がなくなる。
 *   Gang of Four（GoF）の書籍で23パターンが定義された（1994年）。
 *   実務では全部覚える必要はないが、主要パターンの理解は必須。
 *
 * 【1. Singleton パターン】（生成パターン）
 *   インスタンスが1つだけであることを保証する。
 *   用途: 設定管理、データベース接続、ログ管理
 *   【注意】マルチスレッド環境では要注意（後述）
 *
 * 【2. Factory パターン】（生成パターン）
 *   new を直接呼ばず、生成処理を別クラスに委譲する。
 *   用途: 条件に応じて異なるクラスのインスタンスを作るとき
 *   【利点】生成ロジックを1箇所に集約でき、変更に強い
 *
 * 【3. Builder パターン】（生成パターン）
 *   多くのパラメータを持つオブジェクトを段階的に構築する。
 *   用途: コンストラクタの引数が多すぎるとき
 *   【利点】どのパラメータに何を設定したか読みやすい。必須/任意の区別が明確。
 *
 * 【よくあるミス】
 *   1. Singletonの乱用（テストしにくくなる、グローバル状態の問題）
 *   2. Factoryの過剰な使用（単純なnewで十分な場面にFactoryを使う）
 *   3. Builderのbuild()呼び忘れ
 */
public class PatternsLesson1 {

    // ========== Singleton パターン ==========
    // 【Singletonとは？】アプリケーション全体でインスタンスが1つだけ存在することを保証するパターン
    // 【使い所】データベース接続、設定管理、ロガーなど「1つだけあれば十分」なもの
    // 【実装の3つのポイント】
    //   ① private コンストラクタ（外部から new できないようにする）
    //   ② private static なインスタンス保持フィールド
    //   ③ public static な getInstance() メソッド

    // static class DatabaseConnection {

    //     唯一のインスタンスを保持する static フィールド
    //     static なのでクラスに対して1つだけ存在する（インスタンスごとではない）
    //     private なので外部から直接アクセスできない
    //     初期値は null（まだインスタンスが作られていない状態）
    //     【遅延初期化(Lazy Initialization)】必要になるまでインスタンスを作らない方式
    //     private static DatabaseConnection instance;  // 唯一のインスタンス

    //     private コンストラクタ: 外部クラスから new DatabaseConnection() できなくする
    //     これにより、getInstance() 経由でしかインスタンスを取得できなくなる
    //     【重要】private を付け忘れると、誰でも new できてしまい Singleton の意味がなくなる
    //     private DatabaseConnection() {}  // private コンストラクタ（外部からnew不可）

    //     唯一のアクセスポイント: このメソッドを通じてのみインスタンスを取得できる
    //     static メソッドなので、DatabaseConnection.getInstance() と呼べる
    //     public static DatabaseConnection getInstance() {

    //         instance が null（まだ1回も作られていない）かどうかチェック
    //         初回呼び出し時のみ new してインスタンスを作成する
    //         2回目以降は既存のインスタンスをそのまま返す
    //         【マルチスレッド問題】複数スレッドが同時にこの if に到達すると、
    //         2つのインスタンスが作られる可能性がある！
    //         解決策: ① synchronized を付ける ② ダブルチェックロッキング
    //         ③ staticイニシャライザ（最も簡単）: private static final instance = new DatabaseConnection();
    //         if (instance == null) {
    //             instance = new DatabaseConnection();
    //         }

    //         常に同じインスタンスへの参照を返す
    //         何回呼んでも同じオブジェクトが返ってくることが保証される
    //         return instance;   // 常に同じインスタンスを返す

    //     }

    //     インスタンスメソッド: SQLクエリを実行する（シミュレーション）
    //     実際のアプリケーションでは JDBC を使ってデータベースに接続する
    //     public void query(String sql) {
    //         System.out.println("実行: " + sql);
    //     }

    // }

    // ========== Factory パターン ==========
    // 【Factoryとは？】オブジェクトの生成ロジックを専用のクラス/メソッドに委譲するパターン
    // 【使い所】条件に応じて異なるクラスのインスタンスを作りたいとき
    // 【利点】
    //   - 生成ロジックが1箇所にまとまるので変更が楽
    //   - 呼び出し側は具体的なクラスを知らなくてよい（インターフェースだけ知っていればOK）
    //   - 新しい種類を追加するとき、Factory だけ修正すればよい

    // Shape インターフェース: 全ての図形が共通して持つ振る舞い（draw）を定義する
    // 呼び出し側は Shape 型として扱うので、具体的なクラス（Circle, Square）を知る必要がない
    // これが「インターフェースへのプログラミング」という重要な設計原則
    // interface Shape { void draw(); }

    // Circle クラス: Shape インターフェースを実装した「円」の具体クラス
    // draw() メソッドで円の描画処理を行う（ここでは文字出力のみ）
    // static class Circle implements Shape { public void draw() { System.out.println("○ 円を描画"); } }

    // Square クラス: Shape インターフェースを実装した「四角」の具体クラス
    // static class Square implements Shape { public void draw() { System.out.println("□ 四角を描画"); } }

    // ShapeFactory クラス: 図形オブジェクトの生成を担当するファクトリクラス
    // 呼び出し側は「circle」「square」という文字列を渡すだけで、適切なオブジェクトが返ってくる
    // static class ShapeFactory {

    //     create() メソッド: 引数の type に応じて異なる Shape オブジェクトを生成して返す
    //     戻り値の型は Shape インターフェース（具体クラスではない）
    //     static メソッドなので ShapeFactory.create("circle") と呼べる
    //     static Shape create(String type) {

    //         Java 14以降の switch式（expression）を使って、条件に応じたインスタンスを返す
    //         switch式は値を返せるため、変数に直接代入したり return できる
    //         「->」はアロー構文で、break が不要で fall-through も起きない
    //         return switch (type) {
    //             "circle" の場合: 新しい Circle インスタンスを作って返す
    //             case "circle" -> new Circle();
    //             "square" の場合: 新しい Square インスタンスを作って返す
    //             case "square" -> new Square();
    //             上記以外の不明な type の場合: 例外を投げてエラーにする
    //             IllegalArgumentException は「不正な引数」を示す実行時例外
    //             default → throw で例外を投げる
    //             default -> throw new IllegalArgumentException("不明: " + type);
    //         };

    //     }
    // }

    // ========== Builder パターン ==========
    // 【Builderとは？】多くのパラメータを持つオブジェクトを段階的に組み立てるパターン
    // 【使い所】コンストラクタの引数が4つ以上あるとき、必須/任意のパラメータがあるとき
    // 【利点】
    //   - どのパラメータに何を設定したか読みやすい（new User("田中", 25, null, null) → 何が何？）
    //   - 任意パラメータにはデフォルト値を設定できる
    //   - メソッドチェーンで流暢に書ける（Fluent API）
    // 【実務】Lombokの @Builder アノテーションで自動生成するのが主流

    // static class User {

    //     全フィールドは final（一度設定したら変更不可=イミュータブル）
    //     イミュータブルなオブジェクトはスレッドセーフで、バグが起きにくい
    //     private final String name;

    //     年齢フィールド。int型のデフォルト値は0
    //     private final int age;

    //     メールアドレスフィールド。任意のパラメータ
    //     private final String email;

    //     電話番号フィールド。任意のパラメータ
    //     private final String phone;

    //     private コンストラクタ: Builder 経由でのみ User を生成できるようにする
    //     引数に Builder オブジェクトを受け取り、そこから各フィールドの値をコピーする
    //     外部から直接 new User(...) はできない
    //     private User(Builder b) {
    //         Builder の各フィールドから User のフィールドに値を移す
    //         this.name = b.name; this.age = b.age;
    //         this.email = b.email; this.phone = b.phone;
    //     }

    //     @Override で Object クラスの toString() をオーバーライドする
    //     System.out.println(user) と書いたときに自動的に呼ばれ、読みやすい文字列を返す
    //     @Override
    //     public String toString() {
    //         各フィールドの値を整形して文字列として返す
    //         return name + "(age=" + age + ", email=" + email + ", phone=" + phone + ")";
    //     }

    //     Builder は User の内部 static クラスとして定義する
    //     【なぜ内部クラス？】User と密接に関連しており、User の private コンストラクタにアクセスする必要がある
    //     static class Builder {

    //         name は必須パラメータなので final にしてコンストラクタで必ず受け取る
    //         final にすることで、Builder の生成後に name を変更できなくする
    //         private final String name;     // 必須

    //         age は任意パラメータ。設定しなければデフォルト値の 0 が使われる
    //         private int age;               // 任意

    //         email は任意パラメータ。設定しなければ空文字 "" がデフォルト
    //         private String email = "";     // 任意

    //         phone は任意パラメータ。設定しなければ空文字 "" がデフォルト
    //         private String phone = "";     // 任意

    //         Builder のコンストラクタ: 必須パラメータの name を引数で受け取る
    //         必須パラメータをコンストラクタに、任意パラメータをセッターメソッドにするのが定石
    //         Builder(String name) { this.name = name; }

    //         age を設定するメソッド。return this で自身を返し、メソッドチェーンを可能にする
    //         メソッドチェーン: .age(25).email("...").build() のように連続して呼べる
    //         Builder age(int age) { this.age = age; return this; }

    //         email を設定するメソッド。同様に return this でチェーン可能
    //         Builder email(String email) { this.email = email; return this; }

    //         phone を設定するメソッド。同様に return this でチェーン可能
    //         Builder phone(String phone) { this.phone = phone; return this; }

    //         build() メソッド: 設定済みの Builder から User オブジェクトを生成して返す
    //         これが Builder パターンの最終ステップ。必ず呼ぶ必要がある
    //         【実務Tips】build() 内でバリデーション（入力値チェック）を行うことも多い
    //         User build() { return new User(this); }

    //     }
    // }

    // main メソッド: 3つのデザインパターンのデモを実行する
    public static void main(String[] args) {

        // --- Singleton パターンの使用例 ---

        // getInstance() で唯一のインスタンスを取得する（初回はインスタンスが作られる）
        // DatabaseConnection db1 = DatabaseConnection.getInstance();

        // 再び getInstance() を呼ぶ。既にインスタンスが存在するので同じものが返される
        // DatabaseConnection db2 = DatabaseConnection.getInstance();

        // db1 と db2 が同一のオブジェクト（同じアドレス）であることを確認する
        // == は参照比較なので、同じオブジェクトなら true を返す
        // 出力: "同一? true" → Singleton が正しく機能していることの証明
        // System.out.println("同一? " + (db1 == db2));  // true

        // Singleton インスタンスのメソッドを呼ぶ
        // アプリケーション内のどこから呼んでも同じ接続が使われる
        // db1.query("SELECT * FROM users");

        // --- Factory パターンの使用例 ---

        // ShapeFactory.create() に "circle" を渡すと Circle オブジェクトが返される
        // 呼び出し側は Circle クラスを直接参照せず、Shape インターフェースとして扱う
        // 【利点】将来 "triangle" を追加するとき、Factory に case を追加するだけでよい
        // Shape s1 = ShapeFactory.create("circle");

        // "square" を渡すと Square オブジェクトが返される
        // Shape s2 = ShapeFactory.create("square");

        // draw() を呼ぶと、各オブジェクトの具体的な描画処理が実行される
        // s1.draw() → "○ 円を描画"、s2.draw() → "□ 四角を描画"
        // 呼び出し側は Shape 型として統一的に扱っている（ポリモーフィズム）
        // s1.draw();
        // s2.draw();

        // --- Builder パターンの使用例 ---

        // new User.Builder("田中") で必須パラメータ name を指定して Builder を作成
        // .age(25) で年齢を設定（メソッドチェーン）
        // .email("tanaka@example.com") でメールアドレスを設定（メソッドチェーン）
        // .build() で最終的な User オブジェクトを生成する
        // phone は設定していないので、デフォルト値の "" が使われる
        // 【比較】Builder なしの場合: new User("田中", 25, "tanaka@example.com", "")
        //   → どの引数が何を意味するか分かりにくい。Builder なら .email("...") で明確
        // User user = new User.Builder("田中")
        //     .age(25)
        //     .email("tanaka@example.com")
        //     .build();   // phone は未指定 → デフォルト値

        // User の toString() が自動的に呼ばれ、フィールドの内容が整形されて表示される
        // 出力: "田中(age=25, email=tanaka@example.com, phone=)"
        // System.out.println(user);
    }
}
