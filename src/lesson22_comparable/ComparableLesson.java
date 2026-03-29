package lesson22_comparable;

import java.util.*;
// java.util パッケージをまとめてインポートする。
// このパッケージには Collections, ArrayList, Arrays, Comparator 等のクラスが含まれる。
// ワイルドカード * を使うと、パッケージ内の全クラスが利用可能になる。
// 実務では必要なクラスだけを個別にインポートする方が望ましいが、学習用には便利。

/**
 * ===== レッスン22: Comparable と Comparator =====
 *
 * 【目標】オブジェクトのソート方法を自由にカスタマイズする
 *
 * 【Comparable<T>】自然順序を定義する
 *   クラス自体に implements Comparable<T> を付け、compareTo() を実装。
 *   Collections.sort(list) や Arrays.sort() で使われる。
 *   戻り値: 負(自分<相手), 0(等しい), 正(自分>相手)
 *
 * 【Comparator<T>】別の順序を外部から定義する
 *   ソート時に「別の基準」で並べたいときに使う。
 *   Comparator.comparing() でラムダ式で簡潔に書ける。
 *
 * 【Comparable vs Comparator の使い分け】
 *   - Comparable: そのクラスにとって「最も自然な順序」が1つ決まっている場合に使う。
 *     例: String は辞書順、Integer は数値順。クラスの内部に組み込む。
 *   - Comparator: 用途に応じて「異なる順序」でソートしたい場合に使う。
 *     例: Studentを点数順にも名前順にもソートしたい場合。クラスの外部から指定する。
 *
 * 【compareTo() の実装で注意すべき点】
 *   - 一貫性: a.compareTo(b) が正なら b.compareTo(a) は負であること。
 *   - 推移性: a > b かつ b > c なら a > c であること。
 *   - equals との整合性: a.compareTo(b)==0 なら a.equals(b)==true が望ましい。
 *   - 自分自身との比較: a.compareTo(a) は常に 0 であること。
 *
 * 【int値の比較で引き算を使わない理由】
 *   return this.score - other.score; はオーバーフローの危険がある。
 *   Integer.compare(a, b) を使うのが安全。
 */
public class ComparableLesson {

    // =============================================
    // Comparable を実装したクラスの定義
    // =============================================
    // static class Student implements Comparable<Student> {
    //     // static class: このクラスは外部クラス ComparableLesson の中に定義されている「静的内部クラス」。
    //     // static を付けることで、外部クラスのインスタンスがなくても生成できる。
    //     // implements Comparable<Student> で、Student 同士の自然順序を定義することを宣言する。
    //     // Comparable のジェネリクスに <Student> を指定することで、
    //     // compareTo() の引数が Student 型に型安全になる。
    //
    //     String name;
    //     // 生徒の名前を保持するフィールド。
    //     // アクセス修飾子を省略しているため「パッケージプライベート」（同一パッケージからアクセス可能）。
    //     // 実務では private にして getter/setter を用意するのが一般的。
    //
    //     int score;
    //     // 生徒の点数を保持するフィールド。
    //     // このフィールドが自然順序（compareTo）の比較基準になる。
    //
    //     public Student(String name, int score) {
    //         this.name = name; this.score = score;
    //     }
    //     // コンストラクタ。名前と点数を受け取ってフィールドを初期化する。
    //     // this.name は「このオブジェクトの name フィールド」を指し、
    //     // 引数の name と区別するために this を付けている。
    //
    //     @Override
    //     public int compareTo(Student other) {
    //         return Integer.compare(this.score, other.score); // 点数の昇順
    //     }
    //     // Comparable<Student> インターフェースの compareTo() メソッドを実装している。
    //     // @Override アノテーションは、スーパークラスやインターフェースのメソッドを
    //     // オーバーライドしていることをコンパイラに伝える。タイプミスを防止できる。
    //     //
    //     // Integer.compare(a, b) は以下を返す：
    //     //   a < b → 負の値（-1）
    //     //   a == b → 0
    //     //   a > b → 正の値（1）
    //     //
    //     // 【なぜ this.score - other.score と書かないのか？】
    //     //   引き算による比較は、値が非常に大きい/小さい場合にオーバーフローする危険がある。
    //     //   例: Integer.MIN_VALUE - 1 はオーバーフローして正の値になってしまう。
    //     //   Integer.compare() はオーバーフローなしで安全に比較できる。
    //     //
    //     // この実装では「点数の昇順」になる（点数が小さい → 先に来る）。
    //     // 降順にしたい場合は other.score と this.score を逆にする。
    //
    //     @Override
    //     public String toString() { return name + "(" + score + ")"; }
    //     // toString() をオーバーライドして、System.out.println() 等で
    //     // 生徒情報を分かりやすく表示できるようにする。
    //     // 例: "田中(85)" のように「名前(点数)」形式で出力される。
    //     // List を println() すると各要素の toString() が呼ばれるため、
    //     // [田中(85), 鈴木(92), 佐藤(78)] のように表示される。
    // }

    public static void main(String[] args) {

        // =============================================
        // テストデータの作成
        // =============================================

        // List<Student> students = new ArrayList<>(Arrays.asList(
        //     new Student("田中", 85),
        //     new Student("鈴木", 92),
        //     new Student("佐藤", 78)
        // ));
        // Arrays.asList() は引数から固定サイズの List を作成する。
        // ただし Arrays.asList() が返すリストはサイズ変更ができない（add/remove不可）。
        // そのため new ArrayList<>() で包むことで、変更可能な ArrayList に変換している。
        // これをしないと、後で Collections.sort() が内部でリストを操作する際に
        // UnsupportedOperationException が発生する可能性がある。
        // ※実際にはsort自体は固定サイズリストでも動作するが、
        //   一般的にはArrayListに変換しておくのが安全で、add/removeも可能になる。

        // =============================================
        // Comparable（自然順序）によるソート
        // =============================================

        // Collections.sort(students);   // compareTo() が呼ばれる
        // Collections.sort() は、リストの要素が Comparable を実装していることを前提として、
        // compareTo() メソッドを使ってソートを行う。
        // Student クラスでは compareTo() で「点数の昇順」を定義しているため、
        // 点数の小さい順に並び替えられる。
        // 内部的にはTimSortアルゴリズム（安定ソート、O(n log n)）が使われている。
        // 安定ソートとは、同じ値の要素の元の順序が保持されるということ。

        // System.out.println("点数昇順: " + students);
        // 出力: 点数昇順: [佐藤(78), 田中(85), 鈴木(92)]
        // List の toString() が呼ばれ、各要素の toString() が [, ] で囲まれて表示される。

        // =============================================
        // Comparator（別の順序）によるソート
        // =============================================

        // // 名前のアルファベット順
        // students.sort(Comparator.comparing(s -> s.name));
        // List.sort() はJava 8で追加されたメソッドで、Comparator を引数に取る。
        // Comparator.comparing() は、ラムダ式で「どのフィールドで比較するか」を指定できる。
        // s -> s.name は「Student オブジェクト s から name フィールドを取り出す」という意味。
        // name は String 型で、String は Comparable を実装しているため、
        // 自動的に String の自然順序（辞書順 = Unicode コードポイント順）で比較される。
        // 日本語の場合、Unicode のコードポイント順で並ぶため、五十音順とは異なる場合がある。

        // System.out.println("名前順: " + students);
        // 名前の Unicode 順でソートされた結果が出力される。

        // // 点数の降順
        // students.sort(Comparator.comparingInt((Student s) -> s.score).reversed());
        // Comparator.comparingInt() は int 値で比較する Comparator を作る。
        // comparingInt は comparing よりも効率的（オートボクシングを回避できる）。
        // (Student s) -> s.score で「Student の score フィールドで比較」を指定。
        // .reversed() は比較順序を逆転させるメソッド。これにより降順（大きい順）になる。
        //
        // 【(Student s) と型を明示する理由】
        //   ラムダ式の型推論がうまく働かない場合、明示的に型を書く必要がある。
        //   reversed() を呼ぶとジェネリクスの推論が複雑になり、
        //   型を省略すると Object として推論されてしまうことがある。
        //   そのため (Student s) と明示して型推論を助ける。

        // System.out.println("点数降順: " + students);
        // 出力: 点数降順: [鈴木(92), 田中(85), 佐藤(78)]

        // // 複合ソート: 点数昇順 → 同点なら名前順
        // students.sort(Comparator.comparingInt((Student s) -> s.score)
        //     .thenComparing(s -> s.name));
        // thenComparing() は「最初の比較基準が同じ（0を返した）場合に使う第2のソート基準」。
        // まず点数の昇順で並べ、同じ点数の生徒がいたら名前の辞書順で並べる。
        // thenComparing はいくつでもチェーンできる：
        //   .thenComparing(基準2).thenComparing(基準3)...
        // SQLの ORDER BY score ASC, name ASC に相当する概念。
        // 実務では「同じ点数の人が複数いる」状況はよくあるので、複合ソートは頻出パターン。

        // ===== 練習問題 =====
        // 1. Comparable を実装した Product クラス（価格順）を作ってみよう
        //    ヒント: class Product implements Comparable<Product> { String name; int price; ... }
        //    compareTo() で price を比較する。
        // 2. 文字列リストを「文字数の短い順」にソートしてみよう（Comparator）
        //    ヒント: Comparator.comparingInt(String::length) を使う。
        //    String::length はメソッド参照で、s -> s.length() と同じ意味。
    }
}
