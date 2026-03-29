package lesson21_generics;

/**
 * ===== レッスン21: ジェネリクス (Generics) =====
 *
 * 【目標】型を引数として渡す仕組みを理解する
 *
 * 【ジェネリクスとは？】
 *   クラスやメソッドが扱う型を「後から指定できる」仕組み。
 *   ArrayList<String> の <String> がジェネリクス。
 *
 * 【なぜ必要？】
 *   - 型安全: コンパイル時に型の間違いを検出できる
 *   - キャスト不要: Object型に入れて取り出す必要がなくなる
 *   - コードの再利用: 1つのクラスで複数の型に対応できる
 *
 * 【基本構文】
 *   クラス:  class Box<T> { T value; }
 *   メソッド: static <T> T getFirst(T[] arr) { return arr[0]; }
 *
 * 【型パラメータの慣習】
 *   T = Type, E = Element, K = Key, V = Value, N = Number
 *
 * 【境界型パラメータ (Bounded Type)】
 *   <T extends Number>     → T は Number のサブクラスのみ
 *   <T extends Comparable<T>> → T は Comparable を実装していること
 *
 * 【ワイルドカード】
 *   <?>              → 任意の型（読み取り専用）
 *   <? extends Number> → Number のサブクラス（上限）
 *   <? super Integer>  → Integer のスーパークラス（下限）
 *
 * 【型消去(Type Erasure)】
 *   ジェネリクスの型情報はコンパイル後に消える（互換性のため）。
 *   実行時には Box<String> も Box<Integer> も同じ Box クラス。
 *
 * 【実務でのポイント】
 *   - ジェネリクスを使わないと、Object型で何でも入るリストになり、
 *     取り出す時にClassCastExceptionが実行時に起きる危険がある。
 *   - ジェネリクスを使えば、間違った型をコンパイル時にエラーにできるので安全。
 *   - 「型消去」があるため、実行時に T が何型かを調べること（instanceof T）はできない。
 *     これはJava特有の制約で、C#等の他言語とは異なる点。
 */
public class GenericsLesson {

    // =============================================
    // ジェネリッククラスの定義
    // =============================================
    // class Box<T> {
    //     // <T> は「型パラメータ」。Box を使う側が具体的な型（String, Integer等）を指定する。
    //     // T は仮の型名であり、クラス内ではあたかも実際の型のように使える。
    //     // 慣習的に T (Type) を使うが、任意の名前でもOK（ただし1文字大文字が慣例）。
    //
    //     private T value;
    //     // フィールドの型も T で宣言する。
    //     // Box<String> と指定すれば、この value は String 型として扱われる。
    //     // Box<Integer> と指定すれば、この value は Integer 型として扱われる。
    //     // つまり1つのクラス定義で、あらゆる型のBoxを作れるのがジェネリクスの強み。
    //
    //     public Box(T value) { this.value = value; }
    //     // コンストラクタの引数も T 型で受け取る。
    //     // new Box<String>("Hello") と書けば、T=String として value に "Hello" が入る。
    //     // コンパイラが型チェックするので、new Box<String>(123) はコンパイルエラーになる。
    //
    //     public T getValue() { return value; }
    //     // 戻り値の型も T。Box<String> なら String を返す。
    //     // ジェネリクスがなかった時代は Object で返して呼び出し側でキャストしていたが、
    //     // ジェネリクスのおかげでキャスト不要になり、型安全が保証される。
    //
    //     public void setValue(T value) { this.value = value; }
    //     // セッターの引数も T 型。型が合わない値を渡すとコンパイルエラーになる。
    //     // 例: Box<String> box なら box.setValue(123) はコンパイルエラー。
    //
    //     @Override
    //     public String toString() { return "Box[" + value + "]"; }
    //     // toString() は Object クラスのメソッドをオーバーライドしている。
    //     // value の toString() が自動的に呼ばれて文字列連結される。
    //     // System.out.println(box) とすると、この toString() が呼ばれる。
    // }

    // =============================================
    // 境界型パラメータ (Bounded Type Parameter)
    // =============================================
    // class NumberBox<T extends Number> {
    //     // <T extends Number> は「TはNumberクラスまたはそのサブクラスに限定する」という意味。
    //     // これにより、Integer, Double, Long, Float 等のみ使えるようになる。
    //     // String や自作クラスを指定するとコンパイルエラーになる。
    //     // 「extends」はインターフェースの場合も extends を使う（implements ではない）。
    //     // 例: <T extends Comparable<T>> も extends で書く。
    //
    //     private T value;
    //     // T は Number のサブクラスに限定されているので、
    //     // Number クラスのメソッド（doubleValue(), intValue() 等）が使える。
    //
    //     public NumberBox(T value) { this.value = value; }
    //     // コンストラクタ。Number のサブクラスのみ受け取れる。
    //     // new NumberBox<Integer>(42) → OK
    //     // new NumberBox<String>("hello") → コンパイルエラー（StringはNumberではない）
    //
    //     public double doubleValue() { return value.doubleValue(); }
    //     // T extends Number なので、Number クラスの doubleValue() メソッドが呼べる。
    //     // もし境界型を指定しなければ（ただの <T> なら）、T は Object 扱いになるため、
    //     // doubleValue() のような Number 固有のメソッドは呼べない。
    //     // これが境界型パラメータの最大のメリット：型の機能を利用できる。
    // }

    // =============================================
    // ジェネリックメソッド
    // =============================================
    // static <T> void printArray(T[] arr) {
    //     // メソッド単位でもジェネリクスが使える。
    //     // static の後の <T> が「このメソッド専用の型パラメータ宣言」。
    //     // クラスのジェネリクスとは独立しており、メソッド呼び出し時に型が推論される。
    //     // 呼び出し例: printArray(new Integer[]{1,2,3}) → T は Integer に推論される。
    //     // 明示的に型指定もできる: GenericsLesson.<Integer>printArray(nums)
    //
    //     for (T item : arr) {
    //         // 拡張for文で配列の各要素を T 型として取り出す。
    //         // T がどんな型でも for-each で走査できる（配列は Iterable ではないが、
    //         // 拡張for文は配列にも対応している）。
    //         System.out.print(item + " ");
    //         // item.toString() が暗黙に呼ばれて文字列に変換される。
    //         // T が Object 以上の情報を持たないため、呼べるメソッドは Object のメソッドのみ。
    //     }
    //     System.out.println();
    //     // 改行を出力して、出力を見やすくする。
    // }

    // =============================================
    // 境界型のジェネリックメソッド
    // =============================================
    // static <T extends Comparable<T>> T findMax(T[] arr) {
    //     // <T extends Comparable<T>> は「T は Comparable<T> を実装していること」を要求する。
    //     // つまり compareTo() メソッドが使える型のみ受け付ける。
    //     // Integer, String, Double 等は Comparable を実装しているので使える。
    //     // 自作クラスでも Comparable を実装していればOK。
    //     // 戻り値は T 型なので、渡した型と同じ型で最大値が返ってくる。
    //
    //     T max = arr[0];
    //     // 配列の最初の要素を暫定の最大値とする。
    //     // 注意: 空配列を渡すと ArrayIndexOutOfBoundsException が発生する。
    //     // 実務では引数チェックを行うべき。
    //
    //     for (T item : arr) {
    //         // 配列の全要素を順に走査する。
    //
    //         if (item.compareTo(max) > 0) max = item;
    //         // compareTo() の戻り値：
    //         //   正の値 → item > max（item の方が大きい）
    //         //   0     → item == max（等しい）
    //         //   負の値 → item < max（item の方が小さい）
    //         // T extends Comparable<T> と宣言しているからこそ compareTo() が呼べる。
    //         // もし <T> だけなら compareTo() は Object に存在しないためコンパイルエラーになる。
    //     }
    //     return max;
    //     // 最大値を返す。型は T なので、呼び出し側でキャスト不要。
    // }

    public static void main(String[] args) {

        // =============================================
        // ジェネリッククラスの利用例
        // =============================================

        // Box<String> strBox = new Box<>("Hello");
        // Box<String> は「String を格納する Box」を意味する。
        // new Box<>() の <> は「ダイヤモンド演算子」と呼ばれ、Java 7 以降で使える。
        // 左辺の型から T=String が推論されるため、右辺では <String> を省略して <> と書ける。
        // これにより冗長な new Box<String>("Hello") と書く必要がなくなる。

        // Box<Integer> intBox = new Box<>(42);
        // Box<Integer> は「Integer を格納する Box」。
        // 42 は int 型のリテラルだが、Javaのオートボクシング機能で自動的に Integer に変換される。
        // ジェネリクスではプリミティブ型（int, double等）は直接使えないため、
        // ラッパークラス（Integer, Double等）を指定する必要がある。

        // System.out.println(strBox);    // Box[Hello]
        // println() に Object を渡すと、内部で toString() が呼ばれる。
        // Box クラスの toString() が "Box[Hello]" を返す。

        // System.out.println(intBox);    // Box[42]
        // 同様に Box の toString() が呼ばれ "Box[42]" が出力される。

        // String s = strBox.getValue();  // キャスト不要！
        // getValue() の戻り値は T=String なので、そのまま String 変数に代入できる。
        // ジェネリクスがない時代は (String) box.getValue() とキャストが必要だった。
        // キャストを忘れたり間違えたりすると実行時にClassCastExceptionが起きていた。

        // int n = intBox.getValue();     // オートアンボクシング
        // getValue() は Integer を返すが、int 型の変数に代入すると
        // Javaの「オートアンボクシング」機能で自動的に Integer → int に変換される。
        // 注意: intBox の値が null の場合、アンボクシング時に NullPointerException が発生する。

        // =============================================
        // ジェネリックメソッドの利用例
        // =============================================

        // Integer[] nums = {3, 1, 4, 1, 5};
        // Integer の配列を作成。int[] ではなく Integer[] なのは、
        // ジェネリクスがプリミティブ型を扱えないため。
        // 配列初期化子 {3, 1, 4, 1, 5} で各要素がオートボクシングされる。

        // String[] words = {"banana", "apple", "cherry"};
        // String の配列を作成。String はもともと参照型なのでそのまま使える。

        // printArray(nums);
        // ジェネリックメソッド printArray() を呼び出す。
        // コンパイラが引数の型 Integer[] から T=Integer を自動推論する。
        // 出力: 3 1 4 1 5

        // printArray(words);
        // 同じメソッドを String[] で呼び出す。T=String に推論される。
        // 1つのメソッド定義で異なる型の配列に対応できるのがジェネリクスの威力。
        // 出力: banana apple cherry

        // System.out.println("最大(nums): " + findMax(nums));
        // findMax() は <T extends Comparable<T>> なので、Comparable を実装している
        // Integer 型の配列を渡せる。Integer は自然順序（数値の大小）で比較される。
        // 出力: 最大(nums): 5

        // System.out.println("最大(words): " + findMax(words));
        // String も Comparable<String> を実装しているので使える。
        // String の compareTo() は辞書順（Unicode値）で比較する。
        // "cherry" > "banana" > "apple" なので最大値は "cherry"。
        // 出力: 最大(words): cherry

        // =============================================
        // ワイルドカードの利用例
        // =============================================

        // java.util.List<Integer> ints = java.util.Arrays.asList(1, 2, 3);
        // Arrays.asList() は固定サイズの List を返す（要素の変更は可能だが追加・削除は不可）。
        // List<Integer> 型で受け取る。

        // java.util.List<Double> doubles = java.util.Arrays.asList(1.1, 2.2);
        // List<Double> 型。Double も Number のサブクラス。

        // printList(ints);       // <? extends Number> で受け取れる
        // printList メソッドの引数は List<? extends Number> なので、
        // List<Integer> を渡せる。Integer は Number のサブクラスだから。
        // 注意: List<Number> の引数には List<Integer> を渡せない（ジェネリクスは不変）。
        // ワイルドカード <? extends Number> を使うことで、Number のどのサブクラスのリストでも受け取れる。

        // printList(doubles);    // Double も Number のサブクラス
        // List<Double> も同様に渡せる。
        // これがワイルドカードの存在意義：異なるジェネリクス型を共通に扱える。

        // ===== 練習問題 =====
        // 1. ジェネリッククラス Pair<K, V> を作ってみよう（キーと値のペア）
        //    ヒント: 型パラメータを2つ使う。Map.Entry のような構造。
        //    getKey(), getValue() メソッドを用意するとよい。
        // 2. ジェネリックメソッド swap(T[] arr, int i, int j) を作ってみよう
        //    ヒント: 配列の i 番目と j 番目の要素を入れ替える。
        //    T temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
    }

    // =============================================
    // ワイルドカードを使ったメソッドの例
    // =============================================
    // static void printList(java.util.List<? extends Number> list) {
    //     // <? extends Number> は「上限境界ワイルドカード」と呼ばれる。
    //     // Number 自身、またはそのサブクラス（Integer, Double, Long 等）の List を受け取れる。
    //     //
    //     // 【重要】ジェネリクスは「不変 (invariant)」であることに注意：
    //     //   Integer は Number のサブクラスだが、
    //     //   List<Integer> は List<Number> のサブタイプではない。
    //     //   したがって List<Number> 引数には List<Integer> を渡せない。
    //     //   ワイルドカードを使うことでこの問題を解決する。
    //     //
    //     // 【読み取り専用の原則】
    //     //   <? extends Number> のリストには要素を追加できない（nullを除く）。
    //     //   なぜなら、実際のリストが List<Integer> か List<Double> か不明なため、
    //     //   安全に要素を追加する手段がないから。読み取り（取得）は Number として安全にできる。
    //     //
    //     // 【反対: <? super Integer>（下限境界ワイルドカード）】
    //     //   Integer のスーパークラス（Number, Object）の List を受け取れる。
    //     //   こちらは要素の追加が安全にできる（Integer 型の要素をリストに入れられる）。
    //     //   PECS原則: Producer → extends, Consumer → super
    //
    //     for (Number n : list) {
    //         // list の各要素を Number 型として取り出す。
    //         // <? extends Number> なので、要素は必ず Number のサブクラスであり、
    //         // Number 型の変数に安全に代入できる。
    //         System.out.print(n + " ");
    //         // Number の toString() が呼ばれて数値が文字列として出力される。
    //     }
    //     System.out.println();
    //     // 出力の最後に改行を入れる。
    // }
}
