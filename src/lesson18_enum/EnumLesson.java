package lesson18_enum;

/**
 * ===== レッスン18: 列挙型 (enum) =====
 *
 * 【目標】決まった選択肢を型安全に表現する方法を学ぶ
 *
 * 【enumとは？】
 *   取りうる値が決まっている定数の集まり。
 *   文字列や数値で状態を管理するよりも安全。
 *
 *   例: 曜日（月〜日）、季節（春夏秋冬）、方角（東西南北）
 *
 * 【なぜenumを使う？】
 *   String day = "Monday"; だと typo（"Mondya"）してもエラーにならない。
 *   Day day = Day.MONDAY; なら typo はコンパイルエラーになる。
 *   → コンパイル時にミスを発見できるので、バグを未然に防げる。
 *
 * 【enumの機能】
 *   - フィールドやメソッドを持てる
 *   - コンストラクタを持てる（privateのみ）
 *   - switch文で使える
 *   - values() で全値の配列を取得
 *   - name() で名前の文字列を取得
 *   - ordinal() で順番（0始まり）を取得
 */
public class EnumLesson {

    // ========== 基本的なenum ==========
    // enum は「取りうる値が決まっている」型を定義する。
    // 季節は SPRING, SUMMER, AUTUMN, WINTER の4つしかないので、enum で表現するのが最適。

    // enum Season {
    // ↑ enum キーワードで列挙型を定義する。class の代わりに enum を書く。
    //   Season は型名。変数宣言時に Season now = Season.SUMMER; のように使える。
    //   enum はクラスの内側にも外側にも定義できる。ここではクラスの内側に定義している。

    //     SPRING, SUMMER, AUTUMN, WINTER
    //     ↑ 列挙定数（enum constants）。Season 型が取りうる値の一覧。
    //       それぞれがSeasonクラスのインスタンス（オブジェクト）として自動的に作られる。
    //       命名規則: 定数なので全て大文字。複数単語の場合はアンダースコア区切り（例: DARK_RED）。
    //       SPRING は内部的に ordinal=0、SUMMER は ordinal=1、AUTUMN は ordinal=2、WINTER は ordinal=3。
    //       カンマ(,)で区切り、最後はセミコロン(;)なしでもOK（メソッドやフィールドがない場合）。

    // }

    // ========== フィールドとメソッドを持つenum ==========
    // enum は単なる定数の集まりではなく、フィールドやメソッドも持てる。
    // 各定数にデータを付与し、そのデータを使った計算もできる。

    // enum Planet {
    // ↑ 惑星を表す enum。各惑星に質量と半径のデータを持たせる。
    //   enum でもクラスのようにフィールド・コンストラクタ・メソッドを定義できる。

    //     MERCURY(3.303e+23, 2.4397e6),
    //     ↑ 水星の定数。コンストラクタに質量（kg）と半径（m）を引数として渡している。
    //       3.303e+23 は科学的記数法で 3.303 × 10の23乗 を表す（double のリテラル）。
    //       2.4397e6 は 2.4397 × 10の6乗 = 2,439,700 メートル。
    //       各定数の後にカンマ(,)を付けて区切る。

    //     EARTH(5.976e+24, 6.37814e6),
    //     ↑ 地球の定数。質量 5.976 × 10の24乗 kg、半径 6,378,140 m。
    //       コンストラクタ Planet(mass, radius) が自動的に呼ばれ、フィールドに値が設定される。

    //     MARS(6.421e+23, 3.3972e6);
    //     ↑ 火星の定数。最後の定数はセミコロン(;)で終わる。
    //       セミコロンは、この後にフィールドやメソッドの定義が続くことを示す。
    //       【注意】定数の後にフィールド/メソッドがある場合、最後の定数はセミコロン必須。

    //
    //     private final double mass;     // kg
    //     ↑ 質量を保持するフィールド。private final で外部から変更不可。
    //       final は一度代入したら変更できないことを保証する。
    //       enum の定数は不変であるべきなので、フィールドは final にするのが一般的。

    //     private final double radius;   // m
    //     ↑ 半径を保持するフィールド。private final で外部から変更不可。
    //       単位はメートル(m)。

    //
    //     // enumのコンストラクタ（privateのみ）
    //     Planet(double mass, double radius) {
    //     ↑ enum のコンストラクタ。MERCURY(3.303e+23, 2.4397e6) のように定数を定義するときに呼ばれる。
    //       【重要】enum のコンストラクタは暗黙的に private。public や protected にはできない。
    //       なぜなら、enum の定数は enum 内部でのみ作成されるべきだから。
    //       外部から new Planet(...) とすることはできない（コンパイルエラー）。
    //       private を明示的に書いてもよいが、省略しても自動的に private になる。

    //         this.mass = mass;
    //         ↑ 引数 mass をフィールド this.mass に代入する。
    //           コンストラクタの引数と同名のフィールドを区別するために this を使う。

    //         this.radius = radius;
    //         ↑ 引数 radius をフィールド this.radius に代入する。

    //     }
    //
    //     // メソッドも定義できる
    //     double surfaceGravity() {
    //     ↑ 表面重力を計算するメソッド。enum にもメソッドを定義できる。
    //       各惑星の定数（MERCURY, EARTH, MARS）がそれぞれ自分の mass と radius を持っているので、
    //       EARTH.surfaceGravity() のように呼ぶと、地球の表面重力が計算される。
    //       戻り値は double 型。アクセス修飾子は省略（パッケージプライベート）。

    //         final double G = 6.67300E-11;
    //         ↑ 万有引力定数 G（Gravitational constant）をローカル変数として定義。
    //           6.67300E-11 は 6.67300 × 10の-11乗（非常に小さい値）。
    //           final を付けて変更不可にしている（定数であることを明示）。

    //         return G * mass / (radius * radius);
    //         ↑ 表面重力の計算式: g = G × M / R²
    //           G: 万有引力定数、mass: 惑星の質量、radius: 惑星の半径。
    //           radius * radius で半径の2乗を計算。
    //           各定数（MERCURY, EARTH, MARS）が持つ mass と radius の値が使われる。

    //     }
    // }

    public static void main(String[] args) {

        // ========== 基本的な使い方 ==========
        // enum の定数は「型名.定数名」でアクセスする。

        // Season now = Season.SUMMER;
        // ↑ Season 型の変数 now に、Season.SUMMER を代入する。
        //   enum は型安全なので、Season now = "SUMMER"; とは書けない（コンパイルエラー）。
        //   Season 型には SPRING, SUMMER, AUTUMN, WINTER の4つしか代入できない。
        //   【メリット】タイプミスするとコンパイルエラーになるので、バグを事前に防げる。

        // System.out.println("--- enum基本 ---");
        // ↑ セクションタイトルを表示する。

        // System.out.println("季節: " + now);             // → SUMMER
        // ↑ enum の定数を println で出力すると、定数名がそのまま文字列として表示される。
        //   内部的には toString() メソッドが呼ばれ、定数名（"SUMMER"）が返される。
        //   出力: "季節: SUMMER"

        // System.out.println("名前: " + now.name());       // → SUMMER
        // ↑ name() メソッドは定数名を文字列として返す。toString() と似ているが、
        //   name() は final メソッドでオーバーライドできない。確実に定数名が欲しいとき使う。
        //   出力: "名前: SUMMER"

        // System.out.println("順番: " + now.ordinal());    // → 1（0始まり）
        // ↑ ordinal() メソッドは、enum 内での定義順を 0 始まりで返す。
        //   SPRING=0, SUMMER=1, AUTUMN=2, WINTER=3。
        //   SUMMER は2番目に定義されているので、ordinal() は 1 を返す。
        //   【注意】ordinal() の値は定義順に依存するため、定数の並びを変えると値が変わる。
        //   データベースに保存する値としては不適切。name() を使う方が安全。

        // ========== switch文で使う ==========
        // enum は switch 文と非常に相性が良い。
        // 全ての定数を case で網羅することで、漏れのない分岐処理ができる。

        // System.out.println("\n--- switch ---");
        // ↑ セクションタイトルを表示する。

        // switch (now) {
        // ↑ switch 文に enum 変数を渡す。now の値（SUMMER）に一致する case が実行される。
        //   【注意】case の中では Season.SPRING ではなく、ただの SPRING と書く。
        //   switch 文の中では enum の型名を省略するのが Java のルール。

        //     case SPRING:
        //     ↑ now が SPRING の場合にここが実行される。
        //       Season.SPRING ではなく SPRING と書く点に注意。

        //         System.out.println("春です。桜が咲きます。");
        //         ↑ 春に対応するメッセージを表示する。

        //         break;
        //         ↑ break で switch 文から抜ける。break がないと次の case に落ちてしまう（フォールスルー）。

        //     case SUMMER:
        //     ↑ now が SUMMER の場合にここが実行される。今回は now = SUMMER なのでここに一致する。

        //         System.out.println("夏です。暑いです。");
        //         ↑ 夏に対応するメッセージを表示する。今回はこれが出力される。

        //         break;
        //         ↑ switch 文から抜ける。

        //     case AUTUMN:
        //     ↑ now が AUTUMN の場合にここが実行される。

        //         System.out.println("秋です。紅葉です。");
        //         ↑ 秋に対応するメッセージを表示する。

        //         break;
        //         ↑ switch 文から抜ける。

        //     case WINTER:
        //     ↑ now が WINTER の場合にここが実行される。

        //         System.out.println("冬です。寒いです。");
        //         ↑ 冬に対応するメッセージを表示する。

        //         break;
        //         ↑ switch 文から抜ける。最後の case でも break を書くのが良い習慣。

        // }

        // ========== values()で全値をループ ==========
        // values() は enum の全定数を配列として返す static メソッド。
        // 拡張for文と組み合わせて、全定数を順番に処理できる。

        // System.out.println("\n--- values() ---");
        // ↑ セクションタイトルを表示する。

        // for (Season s : Season.values()) {
        // ↑ Season.values() は Season の全定数を配列 {SPRING, SUMMER, AUTUMN, WINTER} で返す。
        //   拡張for文（for-each文）で配列の各要素を順番に変数 s に代入してループする。
        //   s は Season 型のループ変数。1周目は SPRING、2周目は SUMMER...と進む。
        //   【便利】新しい定数を追加しても、ループが自動的にそれも含めて処理する。

        //     System.out.println("  " + s.ordinal() + ": " + s);
        //     ↑ 各定数の ordinal()（順番）と名前を表示する。
        //       出力:
        //         0: SPRING
        //         1: SUMMER
        //         2: AUTUMN
        //         3: WINTER
        //       "  " は行頭のインデントで見やすくするための空白。

        // }

        // ========== フィールドを持つenum ==========
        // enum にフィールドやメソッドを持たせることで、定数に関連するデータと振る舞いを
        // 一つにまとめることができる。

        // System.out.println("\n--- Planetの表面重力 ---");
        // ↑ セクションタイトルを表示する。

        // for (Planet p : Planet.values()) {
        // ↑ Planet.values() で全惑星定数の配列を取得し、for-each でループする。
        //   p には MERCURY, EARTH, MARS が順番に代入される。

        //     System.out.printf("  %s: %.2f m/s²%n", p, p.surfaceGravity());
        //     ↑ printf でフォーマット指定して出力する。
        //       %s: 文字列（惑星名が入る）。p の toString() が呼ばれ定数名が表示される。
        //       %.2f: 小数点以下2桁の浮動小数点数（表面重力の値が入る）。
        //       %n: プラットフォーム依存の改行文字（\n と似ているが、OSに適した改行を出力）。
        //       p.surfaceGravity() は各惑星の mass と radius を使って表面重力を計算する。
        //       出力例:
        //         MERCURY: 3.70 m/s²
        //         EARTH: 9.80 m/s²
        //         MARS: 3.71 m/s²

        // }

        // ===== 練習問題 =====
        // 1. 信号(Signal)のenum を作ろう（RED, YELLOW, GREEN）
        //    各色にメッセージ（"止まれ"等）を持たせてみよう
        //    【ヒント】Planet と同じように、フィールドとコンストラクタを作り、
        //    RED("止まれ"), YELLOW("注意"), GREEN("進め") のように定義する。
        //
        // 2. トランプのスート(Suit)のenumを作ってみよう（HEARTS, DIAMONDS, CLUBS, SPADES）
        //    【ヒント】各スートに記号（"♥", "♦", "♣", "♠"）を持たせると面白い。
    }
}
