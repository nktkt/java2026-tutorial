package lesson23_iterator;

import java.util.*;
// java.util パッケージをインポート。
// Iterator, Iterable, List, ArrayList, Arrays 等が含まれる。
// Iterator はコレクションの要素を順に走査するためのインターフェース。
// Iterable はそのコレクションが for-each 文で使えることを示すインターフェース。

/**
 * ===== レッスン23: Iterator と Iterable =====
 *
 * 【目標】コレクションの要素を安全に走査・削除する方法を学ぶ
 *
 * 【Iterator<T>】要素を1つずつ取り出すオブジェクト
 *   hasNext() → 次の要素があるか
 *   next()    → 次の要素を取得
 *   remove()  → 最後にnext()した要素を削除
 *
 * 【Iterable<T>】拡張for文で使えるようにするインターフェース
 *   iterator() メソッドを実装すると for(T x : obj) が使える
 *
 * 【注意: ConcurrentModificationException】
 *   for-eachループ中にリストを変更するとエラーになる。
 *   → Iterator.remove() を使うか、removeIf() を使う。
 *
 * 【なぜ Iterator パターンが必要なのか？】
 *   - コレクションの内部構造（配列なのかリンクリストなのか木構造なのか）を
 *     知らなくても、統一された方法で要素を走査できる。
 *   - 走査中に安全に要素を削除できる（remove()メソッド）。
 *   - for-each 文は内部で Iterator を使っている（シンタックスシュガー）。
 *
 * 【Iterator vs for-each の使い分け】
 *   - 単純な走査（読み取りのみ） → for-each が簡潔で読みやすい
 *   - 走査中に要素を削除したい  → Iterator を使う（またはremoveIf）
 *   - 複数の Iterator を同時に使いたい → 明示的に Iterator を使う
 */
public class IteratorLesson {

    // =============================================
    // 自作 Iterable クラスの定義
    // =============================================
    // static class NumberRange implements Iterable<Integer> {
    //     // Iterable<Integer> を実装することで、for-each 文で使えるようになる。
    //     // for (int n : new NumberRange(1, 5)) { ... } のように書ける。
    //     // Iterable インターフェースは iterator() メソッドの実装を要求する。
    //     // このクラスは「開始値から終了値までの整数の範囲」を表す。
    //
    //     private final int start, end;
    //     // start: 範囲の開始値、end: 範囲の終了値。
    //     // final にすることで、コンストラクタで一度設定した後は変更できなくなる。
    //     // イミュータブル（不変）なオブジェクトはスレッドセーフで安全。
    //     // private で外部から直接アクセスできないようにしている。
    //
    //     public NumberRange(int start, int end) {
    //         this.start = start; this.end = end;
    //     }
    //     // コンストラクタ。範囲の開始値と終了値を受け取る。
    //     // 例: new NumberRange(1, 5) は 1, 2, 3, 4, 5 を表す範囲。
    //
    //     @Override
    //     public Iterator<Integer> iterator() {
    //         // Iterable インターフェースが要求する iterator() メソッドを実装する。
    //         // このメソッドは、要素を1つずつ取り出すための Iterator オブジェクトを返す。
    //         // for-each 文が使われるたびに、このメソッドが呼ばれて新しい Iterator が作られる。
    //         // そのため、同じ NumberRange を何度 for-each しても最初から走査される。
    //
    //         return new Iterator<>() {
    //             // 匿名クラス（anonymous class）を使って Iterator を実装している。
    //             // new Iterator<>() { ... } は「Iterator インターフェースを実装する名前のないクラス」を
    //             // その場で定義してインスタンス化する構文。
    //             // <> のダイヤモンド演算子は、外側の Iterator<Integer> から Integer を推論する。
    //
    //             private int current = start;
    //             // 現在の位置を追跡するフィールド。start の値で初期化される。
    //             // 匿名クラスは外側のクラスの final フィールド（start）にアクセスできる。
    //             // next() が呼ばれるたびに current がインクリメントされる。
    //
    //             public boolean hasNext() { return current <= end; }
    //             // 次の要素があるかどうかを判定する。
    //             // current が end 以下なら、まだ返していない要素があるので true を返す。
    //             // current が end を超えたら false を返し、走査が終了する。
    //             // while (it.hasNext()) や for-each 文の内部で毎回呼ばれる。
    //
    //             public Integer next() { return current++; }
    //             // 次の要素を返し、current を1つ進める。
    //             // current++ は「まず current の値を返してから、current を +1 する」後置インクリメント。
    //             // 例: current=1 のとき、1を返してから current が2になる。
    //             // int を返しているが、戻り値型が Integer なのでオートボクシングされる。
    //             // 注意: hasNext() を呼ばずに next() を呼ぶと、範囲外の値を返してしまう。
    //             // 実務では NoSuchElementException をスローするのがベストプラクティス。
    //         };
    //     }
    // }

    public static void main(String[] args) {

        // =============================================
        // Iterator の基本的な使い方
        // =============================================

        // List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        // 変更可能な ArrayList を作成する。
        // Arrays.asList() は固定サイズのリストを返すが、new ArrayList<>() で包むことで
        // add/remove が可能な ArrayList に変換している。
        // Iterator で要素を削除するには、元のリストが変更可能でなければならないため、
        // この変換は重要。

        // Iterator<String> it = list.iterator();
        // リストから Iterator オブジェクトを取得する。
        // ArrayList の iterator() メソッドが呼ばれ、リストの先頭を指す Iterator が返される。
        // Iterator は「カーソル」のようなもので、最初は最初の要素の「前」を指している。

        // while (it.hasNext()) {
        //     // hasNext() は、まだ返していない要素があるかを確認する。
        //     // リストの末尾に達するまで true を返し続ける。
        //     // while ループと組み合わせることで、全要素を走査できる。
        //
        //     String s = it.next();
        //     // next() は次の要素を返し、カーソルを1つ進める。
        //     // 最初の呼び出しで "A" が返り、次に "B"、"C"、"D" の順。
        //     // hasNext() が false なのに next() を呼ぶと NoSuchElementException が発生する。
        //
        //     System.out.print(s + " ");
        //     // 現在の要素を空白区切りで出力する。
        //
        //     if (s.equals("B")) it.remove();  // Bを安全に削除
        //     // Iterator.remove() は「最後に next() で取得した要素」をリストから安全に削除する。
        //     // for-each ループ中に list.remove() を使うと ConcurrentModificationException が発生するが、
        //     // Iterator.remove() なら安全に削除できる。
        //     // これは Iterator が内部の状態（カーソル位置等）を正しく更新するため。
        //     // 注意: remove() は next() を呼んだ後に1回だけ呼べる。
        //     //       next() を呼ばずに remove() を呼ぶと IllegalStateException が発生する。
        //     //       連続して2回 remove() を呼ぶのもエラーになる。
        // }

        // System.out.println("\n削除後: " + list);  // [A, C, D]
        // "B" が削除されたリストを表示する。
        // Iterator.remove() により "B" が安全に削除され、[A, C, D] になっている。
        // \n は改行文字。前のループで print() を使ったため、改行を入れている。

        // =============================================
        // for-each 中の削除（やってはいけない例）
        // =============================================

        // // for (String s : list) {
        // //     if (s.equals("C")) list.remove(s);  // ConcurrentModificationException!
        // // }
        // 【これは絶対にやってはいけないパターン！】
        // for-each 文は内部で Iterator を使っているが、ループ中に list.remove() を直接呼ぶと、
        // Iterator が知らないところでリストが変更されたことになり、
        // ConcurrentModificationException（並行変更例外）が発生する。
        // これは「フェイルファスト（fail-fast）」と呼ばれる安全機構。
        // リストが予期しない状態になることを防ぐために、即座に例外をスローする。
        //
        // 【解決策は3つ】
        //   1. Iterator.remove() を使う（上記で示した方法）
        //   2. list.removeIf(条件) を使う（Java 8以降、最も簡潔）
        //   3. 新しいリストを作って、削除しない要素だけをコピーする

        // // 代替: list.removeIf(s -> s.equals("C"));
        // removeIf() は Java 8 で追加されたメソッドで、条件に合致する全要素を安全に削除する。
        // 内部で Iterator を使って削除するため、ConcurrentModificationException は発生しない。
        // ラムダ式 s -> s.equals("C") は Predicate<String> として解釈される。
        // 戻り値は boolean で、要素が1つ以上削除されたら true を返す。
        // 最も簡潔で安全な方法なので、実務では removeIf() が推奨される。

        // =============================================
        // 自作 Iterable の利用
        // =============================================

        // NumberRange range = new NumberRange(1, 5);
        // 1から5までの範囲を表す NumberRange オブジェクトを作成する。
        // NumberRange は Iterable<Integer> を実装しているため、for-each 文で使える。

        // for (int n : range) {
        //     // for-each 文は内部で以下のように展開される：
        //     //   Iterator<Integer> it = range.iterator();
        //     //   while (it.hasNext()) { int n = it.next(); ... }
        //     // range.iterator() が呼ばれると、新しい匿名 Iterator が作られ、
        //     // current = 1 から始まって 5 まで順にイテレートする。
        //     // int n で受け取っているが、Iterator は Integer を返すので
        //     // オートアンボクシングが行われる。
        //
        //     System.out.print(n + " ");   // 1 2 3 4 5
        //     // 各数値を空白区切りで出力する。
        //     // 出力: 1 2 3 4 5
        // }

        // ===== 練習問題 =====
        // 1. リストから偶数を Iterator で安全に削除してみよう
        //    ヒント: List<Integer> nums = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        //    Iterator で走査し、n % 2 == 0 の要素を it.remove() で削除する。
        //    または nums.removeIf(n -> n % 2 == 0) でも同じことができる。
        //
        // 2. フィボナッチ数列の Iterable を作ってみよう
        //    ヒント: Iterator 内部で前2つの値を保持し、next() で次のフィボナッチ数を計算する。
        //    hasNext() は常に true を返すか、上限値を設定して制限する。
        //    無限イテレータの場合、for-each で使うと無限ループになるので注意。
    }
}
