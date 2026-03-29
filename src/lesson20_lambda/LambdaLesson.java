package lesson20_lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ===== レッスン20: ラムダ式と Stream API =====
 *
 * 【目標】モダンなJavaの書き方を学ぶ（Java 8以降の機能）
 *
 * 【ラムダ式とは？】
 *   メソッドを簡潔に書く記法。名前のない関数（無名関数）。
 *   書き方: (引数) -> { 処理 }
 *   1行なら:  (引数) -> 式
 *
 *   例:
 *     (a, b) -> a + b         2つの値を足す
 *     (s) -> s.length()       文字列の長さを返す
 *     () -> System.out.println("Hello")  引数なし
 *
 * 【ラムダ式 vs 匿名クラス】
 *   ラムダ式は「関数型インターフェース」（抽象メソッドが1つだけのインターフェース）を
 *   簡潔に実装するための構文糖（シンタックスシュガー）。
 *   内部的には匿名クラスとは異なる仕組み（invokedynamic命令）で実装されており、
 *   匿名クラスよりも軽量で高速。
 *
 *   匿名クラス: new Comparator<String>() { ... } → クラスファイルが生成される
 *   ラムダ式:   (a, b) -> a.compareTo(b)         → invokedynamic で動的に生成
 *
 * 【Stream API とは？】
 *   コレクション（ListやArray）の要素を連鎖的に処理するAPI。
 *   for文で書くよりも簡潔に、「何をしたいか」を宣言的に書ける。
 *
 *   ★ Stream は「パイプライン」として動く:
 *     ソース → 中間操作(0個以上) → 終端操作(1個)
 *
 *     ソース:   .stream() で Stream を生成する
 *     中間操作: filter, map, sorted など。遅延評価（lazy）で、終端操作が呼ばれるまで実行されない。
 *     終端操作: collect, forEach, count, reduce など。これが呼ばれて初めてパイプライン全体が実行される。
 *
 *   主要メソッド:
 *     filter(条件)    → 条件を満たす要素だけ残す（中間操作）
 *     map(変換)       → 各要素を変換する（中間操作）
 *     sorted()        → ソートする（中間操作）
 *     forEach(処理)   → 各要素に処理を適用（終端操作）
 *     collect(...)    → 結果をListなどに集める（終端操作）
 *     count()         → 要素数を返す（終端操作）
 *     reduce(...)     → 全要素を1つにまとめる（合計など）（終端操作）
 *
 *   ★ 注意: Stream は1回しか使えない！終端操作を実行した後に再利用すると
 *     IllegalStateException が発生する。再度使いたい場合は .stream() からやり直す。
 *
 * 【メソッド参照】
 *   ラムダ式の省略形。引数をそのまま1つのメソッドに渡すだけの場合に使える。
 *   s -> System.out.println(s)  →  System.out::println
 *   s -> s.toUpperCase()        →  String::toUpperCase
 *   s -> Integer.parseInt(s)    →  Integer::parseInt
 *
 * 【よくあるミス・注意点】
 *   ① Stream を変数に保存して2回使おうとする → IllegalStateException
 *   ② ラムダ式内でローカル変数を変更しようとする → コンパイルエラー
 *      ラムダ式の外の変数は「実質的にfinal」でなければならない
 *   ③ collect(Collectors.toList()) の戻り値は変更可能なリストとは限らない
 *      確実に変更可能なリストが欲しい場合は collect(Collectors.toCollection(ArrayList::new))
 *   ④ 空のStreamに対する reduce（初期値なし版）は Optional を返す → NullPointerException に注意
 */
public class LambdaLesson {

    public static void main(String[] args) {

        // ========== ラムダ式の基本: ソートのカスタマイズ ==========
        //
        // 【この例で学ぶこと】
        //   匿名クラスの冗長な書き方と、ラムダ式の簡潔な書き方を比較する。
        //   どちらも「2つの文字列の比較方法」を Collections.sort に渡しているが、
        //   ラムダ式のほうが圧倒的に短い。

        // List<String> names = new ArrayList<>(Arrays.asList("Charlie", "Alice", "Bob"));
        //   ↑ 文字列のリストを作成する。
        //     Arrays.asList("Charlie", "Alice", "Bob") → 固定長リストを作る（追加・削除不可）。
        //     new ArrayList<>(...) で囲むことで、変更可能（mutable）な ArrayList に変換している。
        //     こうしないと Collections.sort() でソートする際に UnsupportedOperationException が出る場合がある。
        //     ★ Arrays.asList は内部的に固定長の配列をラップしているだけで、add/remove ができない。

        //
        // // 従来の書き方（匿名クラス）
        // // Collections.sort(names, new Comparator<String>() {
        // //   ↑ Collections.sort の第2引数に、Comparator インターフェースの匿名クラスを渡す。
        // //     Comparator<String> は「2つの String をどう比較するか」を定義するインターフェース。
        // //     匿名クラスとは: インターフェースや抽象クラスを、名前を付けずにその場で実装するクラス。
        // //     new Comparator<String>() { ... } で Comparator を実装する匿名クラスのインスタンスを作る。

        // //     public int compare(String a, String b) {
        // //       ↑ Comparator インターフェースが要求する compare メソッドを実装する。
        // //         引数 a と b は比較する2つの要素。
        // //         戻り値: 負の数 → a が先、0 → 同じ、正の数 → b が先。

        // //         return a.compareTo(b);
        // //           ↑ String の compareTo メソッドで辞書順比較する。
        // //             "Alice".compareTo("Bob") → 負の数（A < B だから）
        // //             "Charlie".compareTo("Alice") → 正の数（C > A だから）

        // //     }
        // // });
        //
        // // ラムダ式で簡潔に
        // Collections.sort(names, (a, b) -> a.compareTo(b));
        //   ↑ 上の匿名クラスと全く同じ処理を、ラムダ式で1行に短縮している。
        //     (a, b) → compare メソッドの引数 a, b に対応。型は List<String> から自動推論される。
        //     -> → ラムダ演算子。「左の引数を使って、右の式を実行する」という意味。
        //     a.compareTo(b) → compare メソッドの本体。{ } と return は1行なら省略可能。
        //
        //     ★ さらに短くできる:
        //       Collections.sort(names, String::compareTo);  ← メソッド参照
        //       names.sort(String::compareTo);               ← List のメソッドを直接使う
        //       Collections.sort(names, Comparator.naturalOrder()); ← 自然順序

        // System.out.println("--- ソート ---");
        //   ↑ 見出しを表示する。ソート結果を確認するためのラベル。

        // System.out.println(names);   // [Alice, Bob, Charlie]
        //   ↑ ソート後のリストを表示する。List の toString() が自動的に呼ばれ、
        //     "[Alice, Bob, Charlie]" という形式で出力される。
        //     辞書順（アルファベット順）にソートされていることを確認できる。


        // ========== forEach: 各要素に処理 ==========
        //
        // 【この例で学ぶこと】
        //   forEach は「リストの各要素に対して同じ処理を繰り返す」ための終端操作。
        //   for文の代わりにラムダ式で書ける。

        // System.out.println("\n--- forEach ---");
        //   ↑ "\n" で空行を入れてからセクション見出しを表示する。

        // names.forEach(name -> System.out.println("  Hello, " + name));
        //   ↑ リスト names の各要素に対して、ラムダ式の処理を実行する。
        //     name → forEach が1つずつ渡してくる要素を受け取る仮引数。名前は自由。
        //     -> → ラムダ演算子。
        //     System.out.println("  Hello, " + name) → 各要素に対する処理。"Hello, Alice" 等を表示。
        //
        //     これは以下の for文と同じ動作:
        //       for (String name : names) {
        //           System.out.println("  Hello, " + name);
        //       }
        //
        //     出力:
        //       Hello, Alice
        //       Hello, Bob
        //       Hello, Charlie

        // // メソッド参照版: names.forEach(System.out::println);
        //   ↑ メソッド参照を使ったさらに短い書き方。
        //     name -> System.out.println(name) と同じ意味。
        //     「引数をそのまま println に渡すだけ」なので、メソッド参照で省略できる。
        //     System.out::println は「System.out オブジェクトの println メソッドへの参照」。
        //     ★ ただしこの例では "Hello, " を付けているので、メソッド参照は使えない。


        // ========== Stream API: filter（絞り込み） ==========
        //
        // 【この例で学ぶこと】
        //   filter は「条件に合う要素だけを残す」中間操作。
        //   SQL の WHERE 句に似ている。
        //
        // 【パイプラインの流れ】
        //   numbers (List) → .stream() → .filter(偶数?) → .collect(toList) → evens (List)
        //   [1,2,3,...,10]                 [2,4,6,8,10]                       [2,4,6,8,10]

        // List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //   ↑ 1から10までの整数リストを作成する。
        //     Arrays.asList は固定長リストを返すが、ここでは変更しないので問題ない。
        //     ★ int ではなく Integer を使う理由: ジェネリクス（<>）にはプリミティブ型を指定できない。
        //       Javaのオートボクシング機能により、int → Integer の変換は自動で行われる。

        //
        // System.out.println("\n--- filter（偶数だけ） ---");
        //   ↑ 改行+セクション見出し表示。

        // List<Integer> evens = numbers.stream()
        //   ↑ numbers リストから Stream<Integer> を生成する。
        //     .stream() は Collection インターフェースのデフォルトメソッド。
        //     Stream は「データの流れ」を表すオブジェクトで、元のリストを変更しない。
        //     ★ 重要: この時点ではまだ何も処理は実行されていない（遅延評価）。

        //         .filter(n -> n % 2 == 0)     // 偶数だけ残す
        //           ↑ 中間操作。各要素 n に対して条件 n % 2 == 0 を評価し、
        //             true の要素だけを次のステップに流す。
        //             n % 2 == 0 → n を 2 で割った余りが 0（つまり偶数）。
        //             ラムダ式の型は Predicate<Integer>（boolean を返す関数）。
        //             ★ filter は中間操作なので、まだ実行されない。collect が呼ばれて初めて動く。

        //         .collect(Collectors.toList()); // List に集める
        //           ↑ 終端操作。Stream のパイプラインを実行し、結果を List に収集する。
        //             Collectors.toList() は「Stream の要素を新しい List にまとめる」Collector。
        //             この行が実行された瞬間に、stream() → filter → collect の全ステップが実行される。
        //             ★ collect は終端操作なので、この後 evens の Stream は使えない。

        // System.out.println(evens);  // [2, 4, 6, 8, 10]
        //   ↑ フィルタリングされた結果のリストを表示する。偶数だけが残っている。


        // ========== Stream API: map（変換） ==========
        //
        // 【この例で学ぶこと】
        //   map は「各要素を別の値に変換する」中間操作。
        //   入力と出力の型が異なってもよい（例: String → Integer）。
        //
        // 【パイプラインの流れ】
        //   numbers (List) → .stream() → .map(n * 2) → .collect(toList) → doubled (List)
        //   [1,2,3,...,10]                [2,4,6,...,20]                    [2,4,6,...,20]

        // System.out.println("\n--- map（2倍にする） ---");
        //   ↑ 改行+セクション見出し表示。

        // List<Integer> doubled = numbers.stream()
        //   ↑ numbers リストから新しい Stream を生成する（前回の Stream とは別物）。

        //         .map(n -> n * 2)              // 各要素を2倍
        //           ↑ 中間操作。各要素 n を n * 2 に変換する。
        //             ラムダ式の型は Function<Integer, Integer>（Integer を受け取り Integer を返す関数）。
        //             元のリスト numbers は変更されない。Stream は新しい要素の流れを作るだけ。
        //             例: 1→2, 2→4, 3→6, ..., 10→20

        //         .collect(Collectors.toList());
        //           ↑ 終端操作。map で変換された要素を新しい List に収集する。

        // System.out.println(doubled); // [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
        //   ↑ 全要素が2倍になったリストを表示する。


        // ========== Stream API: チェーン（連鎖） ==========
        //
        // 【この例で学ぶこと】
        //   Stream の中間操作は複数つなげることができる（メソッドチェーン）。
        //   各操作は前の操作の出力を入力として受け取る。
        //
        // 【パイプラインの流れ】
        //   names → .stream() → .filter(4文字以上) → .map(大文字化) → .sorted() → .collect() → result
        //   [Alice,Bob,Charlie]  [Alice,Charlie]      [ALICE,CHARLIE]   [ALICE,CHARLIE]           [ALICE,CHARLIE]
        //
        //   ★ Bob は3文字なので filter で除外される。
        //   ★ sorted() 後も順序は変わらない（ALICEはCHARLIEより辞書順で先）。

        // System.out.println("\n--- チェーン ---");
        //   ↑ 改行+セクション見出し表示。

        // List<String> result = names.stream()
        //   ↑ names リスト（[Alice, Bob, Charlie]）から Stream を生成する。

        //         .filter(name -> name.length() > 3)    // 4文字以上
        //           ↑ 中間操作。文字列の長さが 3 より大きい（= 4文字以上の）要素だけを残す。
        //             name.length() で文字列の長さを取得する。
        //             "Alice"(5文字) → true（残る）、"Bob"(3文字) → false（除外）、"Charlie"(7文字) → true（残る）。

        //         .map(String::toUpperCase)              // 大文字に変換
        //           ↑ 中間操作。各文字列を大文字に変換する。
        //             String::toUpperCase はメソッド参照で、name -> name.toUpperCase() と同じ意味。
        //             String クラスの toUpperCase() メソッドを各要素に適用する。
        //             "Alice" → "ALICE"、"Charlie" → "CHARLIE"

        //         .sorted()                              // ソート
        //           ↑ 中間操作。要素を自然順序（String の場合は辞書順）でソートする。
        //             引数なしの sorted() は Comparable の compareTo を使う。
        //             カスタム順序にしたい場合は sorted(Comparator) を使う。
        //             "ALICE" と "CHARLIE" → "ALICE", "CHARLIE"（変化なし、既に辞書順）。

        //         .collect(Collectors.toList());
        //           ↑ 終端操作。パイプライン全体を実行し、結果を List に収集する。

        // System.out.println(result);  // [ALICE, CHARLIE]
        //   ↑ フィルタ → 変換 → ソートの結果を表示する。Bob は除外されている。


        // ========== Stream API: reduce（集約） ==========
        //
        // 【この例で学ぶこと】
        //   reduce は「全要素を1つの値にまとめる」終端操作。
        //   合計、最大値、文字列の連結など、リストを1つの値に「畳み込む」処理に使う。
        //
        // 【reduce の動作（初期値 0, 加算の場合）】
        //   ステップ 1: 0 + 1 = 1     （初期値 + 最初の要素）
        //   ステップ 2: 1 + 2 = 3     （前の結果 + 次の要素）
        //   ステップ 3: 3 + 3 = 6
        //   ステップ 4: 6 + 4 = 10
        //   ...
        //   ステップ10: 45 + 10 = 55  ← 最終結果
        //
        //   reduce(初期値, (累積値, 現在の要素) -> 新しい累積値)

        // System.out.println("\n--- reduce（合計） ---");
        //   ↑ 改行+セクション見出し表示。

        // int sum = numbers.stream()
        //   ↑ numbers リスト（[1,2,...,10]）から Stream を生成する。

        //         .reduce(0, (a, b) -> a + b);
        //           ↑ 終端操作。全要素を1つの値に集約する。
        //             第1引数 0 → 初期値（アキュムレータの開始値）。合計なので 0 から始める。
        //             第2引数 (a, b) -> a + b → 集約関数。
        //               a は「これまでの累積値」、b は「現在の要素」。
        //               a + b で新しい累積値を作る。
        //             ★ 初期値ありの reduce は必ず int を返す（空の Stream でも初期値が返る）。
        //             ★ 初期値なしの reduce(BinaryOperator) は Optional<Integer> を返す。
        //             ★ Integer::sum というメソッド参照でも同じ: .reduce(0, Integer::sum)

        // // 0（初期値）から始めて、全要素を足す
        // System.out.println("合計: " + sum);  // → 55
        //   ↑ 集約結果を表示する。1+2+3+...+10 = 55。


        // ========== Stream API: count ==========
        //
        // 【この例で学ぶこと】
        //   count は「Stream の要素数を返す」終端操作。
        //   filter と組み合わせることで、「条件を満たす要素が何個あるか」を簡潔に求められる。

        // long count = numbers.stream()
        //   ↑ numbers リストから Stream を生成する。

        //         .filter(n -> n > 5)
        //           ↑ 中間操作。n > 5（6以上）の要素だけを残す。
        //             残る要素: 6, 7, 8, 9, 10 の5個。

        //         .count();
        //           ↑ 終端操作。Stream に残っている要素の数を long 型で返す。
        //             ★ 戻り値が int ではなく long であることに注意！
        //               大規模なデータでも要素数が int の範囲を超える可能性があるため。
        //             ★ count() の結果を int 変数に入れたい場合は (int) でキャストする。

        // System.out.println("5より大きい数: " + count);  // → 5
        //   ↑ 条件を満たす要素の個数を表示する。6,7,8,9,10 の5個。


        // ===== 練習問題 =====
        //
        // 1. 文字列リストから"a"を含む要素だけを抽出してみよう
        //    ヒント: .filter(s -> s.contains("a"))
        //    例: ["apple","banana","cherry"] → ["apple","banana"]
        //
        // 2. 数値リストの偶数だけの合計を Stream で求めてみよう
        //    ヒント: filter で偶数を残し、reduce で合計する
        //    例: [1,2,3,4,5,6,7,8,9,10] → 2+4+6+8+10 = 30
        //
        // 3. 文字列リストを文字数の短い順にソートしてみよう
        //    ヒント: .sorted((a, b) -> a.length() - b.length())
        //    または: .sorted(Comparator.comparingInt(String::length))
        //    例: ["Charlie","Alice","Bob"] → ["Bob","Alice","Charlie"]
    }
}
