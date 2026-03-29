package lesson37_jdbc;

// ※ 実行するには以下の import のコメントを外し、sqlite-jdbc の jar をクラスパスに追加する。
// import java.sql.Connection;         — DBへの接続を表すインターフェース
// import java.sql.DriverManager;      — DB接続を管理するクラス
// import java.sql.Statement;          — SQL文を実行するインターフェース
// import java.sql.PreparedStatement;  — パラメータ付きSQL文を実行するインターフェース
// import java.sql.ResultSet;          — SELECT結果を保持するインターフェース
// import java.sql.SQLException;       — DB操作中に発生する例外クラス

/**
 * ===== レッスン37: JDBC（データベース接続） =====
 *
 * 【目標】JavaからSQLデータベースに接続してCRUD操作する方法を学ぶ
 *
 * 【JDBCとは？】
 *   Java Database Connectivity の略。
 *   Java からリレーショナルDBに接続する標準API。
 *   MySQL, PostgreSQL, SQLite, Oracle 等に対応。
 *
 * 【主要クラス（java.sql パッケージ）】
 *   DriverManager     → DB接続を管理。接続URLからConnectionを取得。
 *   Connection        → DBへの接続。SQL実行のためのStatementを作成。
 *   Statement         → SQLを実行する。SQL注入の危険あり。
 *   PreparedStatement → パラメータ付きSQL。SQL注入を防ぐ。必ずこちらを使う。
 *   ResultSet         → SELECT の結果セット。1行ずつ読み取る。
 *
 * 【CRUD操作】
 *   Create: INSERT INTO ...
 *   Read:   SELECT ... FROM ...
 *   Update: UPDATE ... SET ... WHERE ...
 *   Delete: DELETE FROM ... WHERE ...
 *
 * 【SQLインジェクション対策】
 *   NG: "SELECT * FROM users WHERE name = '" + input + "'"
 *       → input に "'; DROP TABLE users; --" が入ると全データ消失
 *   OK: PreparedStatement の ? プレースホルダを使う
 *       → 入力値がSQL文として解釈されない
 *
 * 【注意】
 *   このレッスンを実行するには JDBC ドライバ（jar）が必要です。
 *   SQLite なら sqlite-jdbc、MySQL なら mysql-connector-java。
 *   IntelliJ: Project Structure → Libraries → + → jarを追加
 */
public class JDBCLesson {

    public static void main(String[] args) {

        // ==========================================================
        // ■ セクション1: データベースへの接続とテーブル作成
        // ==========================================================
        // JDBC では「接続URL」を使ってデータベースに接続する。
        // URL の形式はデータベースの種類によって異なる:
        //   SQLite:     jdbc:sqlite:ファイル名.db   （ファイルベース、サーバー不要）
        //   MySQL:      jdbc:mysql://ホスト:3306/DB名
        //   PostgreSQL: jdbc:postgresql://ホスト:5432/DB名
        //   Oracle:     jdbc:oracle:thin:@ホスト:1521:SID
        //
        // 【SQLite を選んだ理由】
        //   - サーバーのインストール不要（ファイル1つでDB）
        //   - 設定なしですぐに使える（ユーザー名・パスワード不要）
        //   - 学習用途に最適
        //   - 本番環境では MySQL や PostgreSQL を使うのが一般的

        // ※ 実行には sqlite-jdbc の jar が必要
        //
        // // JDBC接続URLを定義する。"jdbc:sqlite:sample.db" は以下の意味:
        // //   "jdbc:"    — JDBCプロトコルであることを示すプレフィックス
        // //   "sqlite:"  — 使用するデータベースの種類（SQLite）
        // //   "sample.db" — データベースファイル名（カレントディレクトリに作成される）
        // // このファイルが存在しない場合は自動的に新規作成される。
        // // インメモリDB（ファイルに保存しない一時DB）を使いたい場合は "jdbc:sqlite::memory:" とする。
        // String url = "jdbc:sqlite:sample.db";  // ファイルベースのDB
        //
        // // --- テーブル作成 (CREATE TABLE) ---
        // // try-with-resources で Connection と Statement を同時に取得する。
        // // セミコロンで区切ることで複数のリソースを1つの try で管理できる。
        // // いずれも AutoCloseable を実装しているため、try ブロック終了時に自動で閉じられる。
        // // 【重要】DB接続は必ず閉じること。閉じ忘れるとコネクションリーク（接続枯渇）が発生し、
        // //         新規接続ができなくなる深刻な問題を引き起こす。
        // try (Connection conn = DriverManager.getConnection(url);
        //      Statement stmt = conn.createStatement()) {
        //
        //     // DriverManager.getConnection(url) はJDBCドライバマネージャに
        //     // URLを渡して適切なドライバを自動選択し、DBへの接続を確立する。
        //     // Java 6以降、JDBCドライバは ServiceLoader 機構により自動登録されるため、
        //     // Class.forName("org.sqlite.JDBC") のような手動ドライバ登録は不要になった。
        //     //
        //     // conn.createStatement() で SQL 文を実行するための Statement オブジェクトを作成する。
        //     // Statement は動的なパラメータを含まない固定SQL文に使用する。
        //     // パラメータが必要な場合は PreparedStatement を使う（後述）。
        //
        //     // stmt.execute() で DDL（Data Definition Language: テーブル作成・変更・削除）を実行する。
        //     // テキストブロック（"""〜"""、Java 13+）を使うと複数行の文字列を読みやすく書ける。
        //     // CREATE TABLE IF NOT EXISTS は「テーブルが存在しなければ作成する」という意味。
        //     // IF NOT EXISTS を付けることで、2回目以降の実行でもエラーにならない（冪等性）。
        //     //
        //     // 【テーブル定義の解説】
        //     //   id INTEGER PRIMARY KEY AUTOINCREMENT
        //     //     → id列は整数型、主キー（各行を一意に識別する列）、自動採番
        //     //     → INSERT時にidを指定しなくても自動的に 1, 2, 3, ... と割り振られる
        //     //   name TEXT NOT NULL
        //     //     → name列はテキスト型、NULL（値なし）を許可しない
        //     //     → INSERT時にnameを省略するとエラーになる（データ整合性の保証）
        //     //   age INTEGER
        //     //     → age列は整数型、NULL許可（省略可能）
        //     stmt.execute("""
        //         CREATE TABLE IF NOT EXISTS users (
        //             id INTEGER PRIMARY KEY AUTOINCREMENT,
        //             name TEXT NOT NULL,
        //             age INTEGER
        //         )
        //     """);
        //     System.out.println("テーブル作成完了");
        //
        // } catch (SQLException e) {
        //     // SQLException はDB操作中のエラーを表す例外クラス。
        //     // SQL構文エラー、接続エラー、制約違反（重複キー等）などが含まれる。
        //     // getSQLState() でエラーの種類を特定でき、getErrorCode() でDB固有のエラーコードを取得できる。
        //     e.printStackTrace();
        // }

        // ==========================================================
        // ■ セクション2: INSERT（データ挿入）— PreparedStatement の使用
        // ==========================================================
        // PreparedStatement は SQL 文にパラメータプレースホルダ（?）を使える Statement。
        //
        // 【PreparedStatement を使う理由（非常に重要）】
        //   1. SQLインジェクション防止（最大の理由）
        //      Statement で文字列連結すると、悪意ある入力が SQL として実行される危険がある。
        //      例: name = "'; DROP TABLE users; --" の場合
        //        Statement: "INSERT INTO users (name) VALUES (''; DROP TABLE users; --')"
        //          → テーブルが削除される（致命的セキュリティ脆弱性）
        //        PreparedStatement: name パラメータとして "'; DROP TABLE users; --" がそのまま文字列として扱われる
        //          → 安全。SQL として解釈されない。
        //   2. パフォーマンス向上
        //      同じ SQL を異なるパラメータで繰り返し実行する場合、
        //      DB側で SQL の解析・最適化結果をキャッシュして再利用できる。
        //   3. コードの可読性向上
        //      文字列連結より ? プレースホルダの方が読みやすい。

        // try (Connection conn = DriverManager.getConnection(url);
        //      PreparedStatement ps = conn.prepareStatement(
        //          "INSERT INTO users (name, age) VALUES (?, ?)")) {
        //
        //     // conn.prepareStatement() でパラメータ付き SQL 文をプリコンパイルする。
        //     // ? はプレースホルダ（パラメータの挿入位置）で、後から値を設定する。
        //     // SQL文自体はこの時点でDBに送信され、構文解析と実行計画の作成が行われる。
        //     // これにより、後で値だけを変えて効率的に繰り返し実行できる。
        //
        //     // setString(1, "田中") で1番目の ? に文字列 "田中" をセットする。
        //     // パラメータのインデックスは 1 から始まる（0 からではない点に注意）。
        //     // setString は値を自動的にエスケープするため、SQLインジェクションを防止できる。
        //     ps.setString(1, "田中");   // 1番目の ? に "田中" をセット
        //
        //     // setInt(2, 25) で2番目の ? に整数 25 をセットする。
        //     // 型に応じたメソッドを使う: setString(文字列), setInt(整数), setDouble(小数),
        //     // setDate(日付), setTimestamp(タイムスタンプ), setNull(NULL値)
        //     ps.setInt(2, 25);          // 2番目の ? に 25 をセット
        //
        //     // executeUpdate() はデータを変更する SQL（INSERT, UPDATE, DELETE）を実行する。
        //     // 戻り値は影響を受けた行数（ここでは 1 行が挿入されるので 1 が返る）。
        //     // ※ SELECT には executeQuery() を使う（後述）。
        //     // ※ DDL（CREATE TABLE等）には execute() を使う。
        //     ps.executeUpdate();        // INSERT を実行。戻り値は影響行数（1）
        //
        //     // 同じ PreparedStatement を再利用して2件目のデータを挿入する。
        //     // SQL文のプリコンパイル結果がキャッシュされているので効率的。
        //     // パラメータをセットし直すだけで別のデータを挿入できる。
        //     ps.setString(1, "鈴木");
        //     ps.setInt(2, 30);
        //     ps.executeUpdate();
        //
        //     System.out.println("データ挿入完了");
        //
        // } catch (SQLException e) {
        //     // 制約違反（NOT NULLにNULLを入れた、UNIQUE制約に重複値を入れた等）や
        //     // テーブルが存在しない場合にここでキャッチされる。
        //     e.printStackTrace();
        // }

        // ==========================================================
        // ■ セクション3: SELECT（データ取得）
        // ==========================================================
        // SELECT 文の結果は ResultSet オブジェクトとして返される。
        // ResultSet はカーソル（現在の読み取り位置）を持ち、
        // next() メソッドで1行ずつ前方にカーソルを移動して読み取る。
        //
        // 【ResultSet の構造イメージ】
        //   初期位置: 最初の行の手前（どの行も指していない）
        //   next() → 1行目に移動（true を返す）
        //   next() → 2行目に移動（true を返す）
        //   ...
        //   next() → 最後の行の次（false を返す → ループ終了）

        // try (Connection conn = DriverManager.getConnection(url);
        //      Statement stmt = conn.createStatement();
        //      ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
        //
        //     // stmt.executeQuery() は SELECT 文を実行し、ResultSet を返す。
        //     // executeUpdate()（INSERT/UPDATE/DELETE用）とは戻り値の型が異なる点に注意。
        //     // "SELECT * FROM users" は users テーブルの全行・全列を取得する SQL。
        //     // * は全列を意味する。本番コードでは必要な列名を明示する方が望ましい
        //     // （SELECT id, name, age FROM users のように）。理由:
        //     //   - テーブルに列が追加されても影響を受けない
        //     //   - 不要な列を読まないので効率的
        //     //   - コードの意図が明確になる
        //
        //     System.out.println("\n--- ユーザー一覧 ---");
        //
        //     // rs.next() はカーソルを次の行に移動し、行が存在すれば true、
        //     // 行が無ければ false を返す。while ループで全行を順に処理する。
        //     // 最初の next() 呼び出しで1行目に移動する（初期位置は行の手前）。
        //     while (rs.next()) {        // 1行ずつカーソルを進めて読み取る
        //         // getInt("id") は現在の行の "id" 列の値を int として取得する。
        //         // 列名（"id"）の代わりにインデックス（1, 2, 3...）でも指定可能。
        //         // 例: rs.getInt(1) — ただし列名の方が可読性が高い。
        //         int id = rs.getInt("id");
        //
        //         // getString("name") は現在の行の "name" 列の値を String として取得する。
        //         String name = rs.getString("name");
        //
        //         // getInt("age") は "age" 列の値を int として取得する。
        //         // NULL値の場合は 0 が返される点に注意。NULL判定が必要な場合は
        //         // rs.wasNull() を getInt() の直後に呼んで確認する。
        //         int age = rs.getInt("age");
        //
        //         // printf で整形して出力する。%d は整数、%s は文字列、%n は改行のプレースホルダ。
        //         System.out.printf("  ID=%d, 名前=%s, 年齢=%d%n", id, name, age);
        //     }
        //
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }

        // ==========================================================
        // ■ セクション4: UPDATE（データ更新）
        // ==========================================================
        // UPDATE 文は既存のデータを変更する SQL。
        // WHERE 句で更新対象の行を絞り込む。WHERE を省略すると全行が更新されるので危険。
        // 【重要】WHERE 句を忘れると全行更新される重大な事故につながる。
        // 実務では UPDATE/DELETE を実行する前に SELECT で対象行を確認するのが安全。

        // try (Connection conn = DriverManager.getConnection(url);
        //      PreparedStatement ps = conn.prepareStatement(
        //          "UPDATE users SET age = ? WHERE name = ?")) {
        //
        //     // 1番目の ? に新しい年齢 26 をセットする。
        //     // SET age = ? の部分で、age列の値を更新する。
        //     ps.setInt(1, 26);
        //
        //     // 2番目の ? に更新対象の名前 "田中" をセットする。
        //     // WHERE name = ? の部分で、更新する行を絞り込む。
        //     // PreparedStatement を使うことで、name に悪意ある文字列が入っても安全。
        //     ps.setString(2, "田中");
        //
        //     // executeUpdate() は影響を受けた行数を返す。
        //     // 0 が返った場合は、WHERE 条件に一致する行がなかったことを意味する。
        //     // この値をチェックすることで、更新が正しく行われたか確認できる。
        //     int rows = ps.executeUpdate();
        //     System.out.println(rows + "行更新しました");
        //
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }

        // ==========================================================
        // ■ セクション5: DELETE（データ削除）
        // ==========================================================
        // DELETE 文はテーブルから行を削除する SQL。
        // UPDATE と同様、WHERE 句で削除対象を絞り込む。
        // WHERE を省略すると全行が削除されるので絶対に注意すること。
        // 【セキュリティ】DELETE は取り消しが効かない（トランザクション内でなければ）。
        // 実務では論理削除（deleted_at 列にタイムスタンプを設定）を採用することも多い。
        // 論理削除なら誤操作時にデータを復旧できる。

        // try (Connection conn = DriverManager.getConnection(url);
        //      PreparedStatement ps = conn.prepareStatement(
        //          "DELETE FROM users WHERE name = ?")) {
        //
        //     // 削除対象の名前 "鈴木" をプレースホルダにセットする。
        //     ps.setString(1, "鈴木");
        //
        //     // executeUpdate() で DELETE を実行し、削除された行数を取得する。
        //     // 0 が返った場合は、該当する行がなかったことを意味する。
        //     int rows = ps.executeUpdate();
        //     System.out.println(rows + "行削除しました");
        //
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }

        // ==========================================================
        // ■ セクション6: トランザクション管理
        // ==========================================================
        // トランザクションとは、複数のDB操作を1つの論理的な処理単位としてまとめる仕組み。
        //
        // 【ACID特性（トランザクションが保証する4つの性質）】
        //   A (Atomicity: 原子性)    — 全ての操作が成功するか、全て取り消されるか（All or Nothing）
        //   C (Consistency: 一貫性)  — トランザクション前後でDBの整合性が保たれる
        //   I (Isolation: 分離性)    — 同時実行中のトランザクションが互いに干渉しない
        //   D (Durability: 永続性)   — コミットされたデータは障害後も失われない
        //
        // 【銀行振込の例】
        //   Aさんの口座から1000円引く → Bさんの口座に1000円足す
        //   この2つの操作は「必ず両方成功」か「両方失敗」でなければならない。
        //   途中で障害が起きて片方だけ実行されると、お金が消える or 増殖する。
        //   トランザクションを使えば、途中で失敗した場合に全てロールバック（巻き戻し）される。
        //
        // 【デフォルトの挙動】
        //   JDBC はデフォルトで「自動コミット（autoCommit = true）」が有効。
        //   つまり各 SQL 文が即座にコミット（確定）される。
        //   トランザクションを使うには setAutoCommit(false) で自動コミットを無効にする。

        // try (Connection conn = DriverManager.getConnection(url)) {
        //     // setAutoCommit(false) で自動コミットを無効にする。
        //     // これ以降の SQL 操作は、明示的に commit() するまで確定されない。
        //     // エラー時に rollback() で全ての変更を取り消すことができるようになる。
        //     conn.setAutoCommit(false);  // トランザクション開始（自動コミット無効化）
        //
        //     try {
        //         // 1つ目の操作: 田中の年齢を1減らす。
        //         // 銀行の例でいえば「送金元の口座から引く」に相当する操作。
        //         PreparedStatement ps1 = conn.prepareStatement(
        //             "UPDATE users SET age = age - 1 WHERE name = ?");
        //         ps1.setString(1, "田中");
        //         ps1.executeUpdate();
        //
        //         // ↓ もしここでエラーが起きたら、ps1 の変更もロールバックされる。
        //         // これがトランザクションの原子性（Atomicity）の恩恵。
        //         // テストとして意図的に例外を投げることで動作確認できる:
        //         // if (true) throw new RuntimeException("テスト用エラー");
        //
        //         // 2つ目の操作: 鈴木の年齢を1増やす。
        //         // 銀行の例でいえば「送金先の口座に足す」に相当する操作。
        //         PreparedStatement ps2 = conn.prepareStatement(
        //             "UPDATE users SET age = age + 1 WHERE name = ?");
        //         ps2.setString(1, "鈴木");
        //         ps2.executeUpdate();
        //
        //         // commit() で全ての変更を確定する。
        //         // この時点で初めて、ps1 と ps2 の両方の変更がDBに永続化される。
        //         // commit() が正常に完了すれば、DBの障害が起きてもデータは保持される（永続性）。
        //         conn.commit();          // 全操作成功 → コミット（変更を確定）
        //         System.out.println("トランザクション完了");
        //
        //     } catch (Exception e) {
        //         // いずれかの操作で例外が発生した場合、全変更をロールバック（巻き戻し）する。
        //         // rollback() により、setAutoCommit(false) 以降の全変更が取り消され、
        //         // DBはトランザクション開始前の状態に戻る。
        //         conn.rollback();        // エラー発生 → ロールバック（全変更を取り消し）
        //         System.out.println("ロールバックしました");
        //     }
        //
        // } catch (SQLException e) {
        //     // DriverManager.getConnection() の失敗（DB接続不可）等でここに来る。
        //     e.printStackTrace();
        // }

        // コメントアウトされたコードを実行するにはJDBCドライバが必要であることを通知する。
        System.out.println("※ 実行には sqlite-jdbc の jar が必要です");

        // ===== 練習問題 =====
        // 1. 商品テーブル(id, name, price)を作成し、CRUD操作してみよう
        //    ヒント: CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER)
        // 2. PreparedStatement で検索条件を動的に指定してみよう
        //    ヒント: "SELECT * FROM users WHERE age >= ?" + ps.setInt(1, 20)
    }
}
