package lesson25_functional;

import java.util.*;
// java.util パッケージをインポート。Arrays, List 等を使用する。

import java.util.function.*;
// java.util.function パッケージをインポート。
// Java 8 で追加された関数型インターフェース群が含まれる。
// Predicate, Function, Consumer, Supplier, UnaryOperator, BinaryOperator 等。
// これらはラムダ式やメソッド参照と組み合わせて使う。

/**
 * ===== レッスン25: 関数型インターフェース =====
 *
 * 【目標】Javaの関数型プログラミングの基盤を理解する
 *
 * 【関数型インターフェースとは？】
 *   抽象メソッドが1つだけのインターフェース。ラムダ式で実装できる。
 *   @FunctionalInterface アノテーションを付けると、
 *   抽象メソッドが2つ以上あるとコンパイルエラーになる（安全装置）。
 *
 * 【主要な関数型インターフェース（java.util.function）】
 *   Predicate<T>     : T → boolean   (判定)        test(T)
 *   Function<T,R>    : T → R         (変換)        apply(T)
 *   Consumer<T>      : T → void      (消費)        accept(T)
 *   Supplier<T>      : () → T        (生成)        get()
 *   UnaryOperator<T> : T → T         (同型変換)     apply(T)
 *   BinaryOperator<T>: (T,T) → T     (二項演算)     apply(T,T)
 *   BiFunction<T,U,R>: (T,U) → R     (二引数変換)   apply(T,U)
 *
 * 【合成メソッド】
 *   Predicate:  and(), or(), negate()
 *   Function:   andThen(), compose()
 *   Consumer:   andThen()
 *
 * 【なぜ関数型インターフェースが重要なのか？】
 *   - Stream API (filter, map, forEach 等) の引数はすべて関数型インターフェース
 *   - 処理を「値」として扱える → メソッドに渡したり、変数に代入できる
 *   - 「何をするか」と「どう使うか」を分離できる（柔軟で再利用可能なコード）
 *   - Optional の map, filter, ifPresent 等も関数型インターフェースを使う
 *
 * 【ラムダ式 vs メソッド参照】
 *   ラムダ式:       s -> s.length()
 *   メソッド参照:    String::length    （同じ意味だがより簡潔）
 *   コンストラクタ参照: ArrayList::new  （Supplier<ArrayList> に使える）
 */
public class FunctionalLesson {

    public static void main(String[] args) {

        // =============================================
        // Predicate（判定）: T → boolean
        // =============================================

        // Predicate<Integer> isEven = n -> n % 2 == 0;
        // Predicate<Integer> は「Integer を受け取って boolean を返す関数」を表す。
        // n -> n % 2 == 0 はラムダ式で、「n を2で割った余りが0ならtrue」という判定ロジック。
        // Predicate の抽象メソッドは test(T t) で、このラムダが test() の実装になる。
        // 変数に代入できるのがポイント：判定ロジックをデータとして扱える。
        // 【使いどころ】Stream.filter() の引数、List.removeIf() の引数など。

        // Predicate<Integer> isPositive = n -> n > 0;
        // 「正の数かどうか」を判定する Predicate。
        // n > 0 が true なら正の数。

        // Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
        // Predicate の and() メソッドで2つの条件を合成（論理AND）する。
        // isEven.and(isPositive) は「偶数 かつ 正の数」を判定する新しい Predicate を作る。
        // and() は Predicate のデフォルトメソッドで、新しい Predicate を返す。
        // 他の合成メソッド：
        //   or()     → 論理OR（どちらかが true なら true）
        //   negate() → 論理NOT（結果を反転）
        // 例: isEven.negate() は「奇数かどうか」を判定する Predicate になる。
        // 合成により、小さな判定を組み合わせて複雑な判定を作れるのが強み。

        // System.out.println("--- Predicate ---");
        // 出力セクションの見出し。

        // System.out.println("4は偶数? " + isEven.test(4));                // true
        // test(4) を呼ぶと、ラムダ式 n -> n % 2 == 0 に n=4 が渡される。
        // 4 % 2 == 0 は true なので、true が返る。
        // test() が Predicate の唯一の抽象メソッド。

        // System.out.println("-2は偶数かつ正? " + isEvenAndPositive.test(-2)); // false
        // -2 は偶数（-2 % 2 == 0 → true）だが、正の数ではない（-2 > 0 → false）。
        // and() なので両方 true でないと結果は false。
        // 短絡評価される：最初の条件が false なら2つ目は評価されない。

        // =============================================
        // Function（変換）: T → R
        // =============================================

        // Function<String, Integer> strToLength = String::length;
        // Function<String, Integer> は「String を受け取って Integer を返す関数」。
        // String::length は「メソッド参照」で、s -> s.length() と同じ意味。
        // メソッド参照は、既存のメソッドをそのまま関数として使いたい場合にラムダ式より簡潔。
        // 【メソッド参照の4つの形式】
        //   1. インスタンスメソッド参照:  String::length    (引数がレシーバになる)
        //   2. 静的メソッド参照:         Integer::parseInt  (引数をそのまま渡す)
        //   3. 特定オブジェクトのメソッド: System.out::println (特定インスタンスのメソッド)
        //   4. コンストラクタ参照:        ArrayList::new     (コンストラクタを関数として扱う)

        // Function<Integer, String> intToStars = n -> "★".repeat(n);
        // Integer を受け取って、その数だけ ★ を繰り返した String を返す関数。
        // String.repeat(n) は Java 11 で追加されたメソッドで、文字列を n 回繰り返す。
        // 例: "★".repeat(3) → "★★★"

        // Function<String, String> toStars = strToLength.andThen(intToStars);
        // andThen() は2つの Function を合成する。
        // strToLength（String→Integer）の結果を intToStars（Integer→String）に渡す。
        // つまり、String → Integer → String という変換パイプラインが作られる。
        // "Hello" → 5 → "★★★★★" と変換される。
        // 【andThen vs compose の違い】
        //   andThen(g): this を先に実行し、その結果に g を適用する（this → g）
        //   compose(g): g を先に実行し、その結果に this を適用する（g → this）
        //   数学で言うと: andThen は f.andThen(g) = g(f(x))、compose は f.compose(g) = f(g(x))

        // System.out.println("\n--- Function ---");
        // セクション区切り。\n で改行を入れて見やすくする。

        // System.out.println("Hello → " + toStars.apply("Hello"));  // ★★★★★
        // apply("Hello") を呼ぶと:
        //   1. strToLength.apply("Hello") → 5（文字数）
        //   2. intToStars.apply(5) → "★★★★★"（5個の星）
        // apply() が Function の唯一の抽象メソッド。

        // =============================================
        // Consumer（消費）: T → void
        // =============================================

        // Consumer<String> print = System.out::println;
        // Consumer<String> は「String を受け取って何も返さない（副作用を行う）関数」。
        // System.out::println は PrintStream オブジェクト System.out の println メソッドへの参照。
        // 「特定オブジェクトのメソッド参照」の形式。
        // Consumer は「消費者」という名前の通り、データを受け取って処理するだけで結果を返さない。
        // 【使いどころ】List.forEach() の引数、Stream.forEach() の引数、
        //   Optional.ifPresent() の引数など。

        // Consumer<String> shout = s -> System.out.println(s.toUpperCase() + "!");
        // 文字列を大文字にして "!" を付けて出力する Consumer。
        // toUpperCase() は文字列を全て大文字に変換する String のメソッド。
        // 副作用（画面への出力）を行うだけで、戻り値はない（void）。

        // System.out.println("\n--- Consumer ---");
        // セクション区切り。

        // print.andThen(shout).accept("hello");  // hello → HELLO!
        // Consumer の andThen() で2つの Consumer を連鎖させる。
        // accept("hello") を呼ぶと:
        //   1. print.accept("hello") → "hello" を出力
        //   2. shout.accept("hello") → "HELLO!" を出力
        // accept() が Consumer の唯一の抽象メソッド。
        // 2つの処理が順番に実行される。1つの値に対して複数の処理を適用したい場合に便利。
        // 【注意】Consumer には compose() はない（void を返すので合成の方向が1つしかない）。

        // =============================================
        // Supplier（生成）: () → T
        // =============================================

        // Supplier<Double> randomValue = Math::random;
        // Supplier<Double> は「引数なしで Double を返す関数」。
        // Math::random は Math.random() メソッドへの参照。
        // Math.random() は 0.0 以上 1.0 未満のランダムな double を返す。
        // Supplier は「供給者」という名前の通り、値を生成して提供する。
        // 引数を取らないのが特徴。
        // 【使いどころ】遅延初期化（必要になるまで値を生成しない）、
        //   Optional.orElseGet() の引数、Stream.generate() の引数など。
        //   orElseGet(Supplier) は orElse(値) と異なり、
        //   Optional が空の場合だけ Supplier を実行するため、
        //   値の生成コストが高い場合に効率的。

        // System.out.println("\n--- Supplier ---");
        // セクション区切り。

        // System.out.println("乱数: " + randomValue.get());
        // get() を呼ぶたびに新しいランダム値が生成される。
        // get() が Supplier の唯一の抽象メソッド。
        // get() を呼ぶまで値は生成されない（遅延評価）。

        // =============================================
        // Stream API との連携
        // =============================================

        // List<Integer> nums = Arrays.asList(1, -2, 3, -4, 5, -6);
        // テスト用のリスト。正の数と負の数が混在している。
        // Arrays.asList() は固定サイズの List を返す。

        // Predicate<Integer> positive = n -> n > 0;
        // 「正の数かどうか」を判定する Predicate。
        // Stream.filter() に渡すために変数に入れている。
        // 直接 filter(n -> n > 0) とインラインで書くことも多いが、
        // 変数に入れることで再利用可能になり、可読性も向上する。

        // Function<Integer, Integer> doubleIt = n -> n * 2;
        // 「値を2倍にする」変換関数。
        // Function<Integer, Integer> は入力も出力も Integer。
        // この場合 UnaryOperator<Integer> でも同じ意味になる
        // （UnaryOperator は Function<T,T> の特殊形）。

        // List<Integer> result = nums.stream()
        //     .filter(positive)
        //     .map(doubleIt)
        //     .toList();
        // Stream API を使ったデータ処理パイプライン。
        //
        // nums.stream()    → List から Stream を作成する。
        //                     Stream はデータの「流れ」を表し、遅延評価される。
        //
        // .filter(positive) → 正の数だけを通過させる（Predicate で判定）。
        //                     [1, -2, 3, -4, 5, -6] → [1, 3, 5]
        //                     filter は中間操作で、この時点ではまだ実行されない。
        //
        // .map(doubleIt)    → 各要素を2倍に変換する（Function で変換）。
        //                     [1, 3, 5] → [2, 6, 10]
        //                     map も中間操作。
        //
        // .toList()         → Stream の結果を不変の List に変換する（終端操作）。
        //                     Java 16 で追加されたメソッド。
        //                     それ以前は .collect(Collectors.toList()) と書いていた。
        //                     終端操作が呼ばれた時点で、パイプライン全体が実行される。
        //
        // 【重要】Stream は「遅延評価」される：
        //   filter や map は呼んだ時点では何も起きない。
        //   toList() のような終端操作が呼ばれて初めて、全ての処理が実行される。
        //   これにより、不要な計算を避けて効率的に処理できる。

        // System.out.println("\n正の数を2倍: " + result);  // [2, 6, 10]
        // 正の数 [1, 3, 5] を2倍にした [2, 6, 10] が出力される。

        // ===== 練習問題 =====
        // 1. Predicate<String> で「5文字以上」かつ「aを含む」を合成してみよう
        //    ヒント: Predicate<String> len5 = s -> s.length() >= 5;
        //    Predicate<String> hasA = s -> s.contains("a");
        //    Predicate<String> both = len5.and(hasA);
        //    both.test("banana") → true（6文字で a を含む）
        //    both.test("abc") → false（5文字未満）
        //
        // 2. Function を使って摂氏→華氏の変換関数を作ってみよう
        //    ヒント: Function<Double, Double> cToF = c -> c * 9.0 / 5.0 + 32;
        //    cToF.apply(100.0) → 212.0
        //    andThen() で華氏→ケルビンの変換もチェーンできる。
    }
}
