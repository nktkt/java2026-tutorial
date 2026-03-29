package lesson24_collections;

import java.util.*;
// java.util パッケージをインポート。
// TreeSet, TreeMap, LinkedHashMap, ArrayDeque, List 等のコレクションクラスが含まれる。
// Java のコレクションフレームワークは、データ構造の実装を統一されたインターフェースで提供する。

/**
 * ===== レッスン24: コレクション詳細 =====
 *
 * 【目標】場面に応じた最適なコレクションを選べるようになる
 *
 * 【コレクション選択ガイド】
 *   順序付きリスト:  ArrayList（ランダムアクセス速い）/ LinkedList（挿入削除速い）
 *   ソート済み集合:  TreeSet（自動ソート、O(log n)）
 *   挿入順保持集合: LinkedHashSet
 *   重複なし集合:   HashSet（最速O(1)）
 *   キー→値:       HashMap（最速）/ TreeMap（キーソート済み）/ LinkedHashMap（挿入順）
 *   キュー:        ArrayDeque（両端キュー、Stack代替にも）
 *
 * 【Deque（ダブルエンドキュー）】
 *   両端から追加・取得できるキュー。Stack より ArrayDeque が推奨。
 *   addFirst/addLast, pollFirst/pollLast, peekFirst/peekLast
 *
 * 【不変コレクション】
 *   List.of(), Set.of(), Map.of()  → 変更不可のコレクション（Java 9+）
 *   Collections.unmodifiableList() → 既存リストの変更不可ビュー
 *
 * 【計算量の比較（重要！面接でも聞かれる）】
 *   ArrayList:  get O(1), add末尾 O(1)*, add途中 O(n), remove O(n), contains O(n)
 *   LinkedList: get O(n), add先頭 O(1), add途中 O(1)*, remove先頭 O(1), contains O(n)
 *   HashSet:    add O(1), remove O(1), contains O(1)
 *   TreeSet:    add O(log n), remove O(log n), contains O(log n)
 *   HashMap:    put O(1), get O(1), remove O(1)
 *   TreeMap:    put O(log n), get O(log n), remove O(log n)
 *   (* = 償却計算量。リサイズが発生すると一時的にO(n)になる)
 */
public class CollectionsLesson {

    public static void main(String[] args) {

        // =============================================
        // TreeSet（自動ソートされるSet）
        // =============================================

        // TreeSet<Integer> treeSet = new TreeSet<>();
        // TreeSet は要素を自動的にソートされた状態で保持する Set（集合）。
        // 内部的には赤黒木（Red-Black Tree）というバランス二分探索木で実装されている。
        // 要素の追加・削除・検索はすべて O(log n) の計算量。
        // HashSet(O(1)) より遅いが、常にソートされているのが強み。
        // 要素は Comparable を実装している必要がある（または Comparator を渡す）。
        // Integer は Comparable<Integer> を実装しているのでそのまま使える。
        // null は格納できない（NullPointerException が発生する）。

        // treeSet.add(5); treeSet.add(1); treeSet.add(3);
        // 要素を追加する。追加順は 5, 1, 3 だが、TreeSet は自動的にソートするため、
        // 内部的には 1, 3, 5 の順で保持される。
        // Set なので重複した値を追加しても無視される（add が false を返す）。
        // add() の戻り値は boolean で、要素が追加されたら true、既に存在していたら false。

        // System.out.println("TreeSet: " + treeSet);  // [1, 3, 5]（自動ソート）
        // toString() がソートされた順序で [1, 3, 5] と表示する。
        // 追加した順序（5, 1, 3）ではなく、ソートされた順序になっていることに注目。

        // System.out.println("最小: " + treeSet.first());
        // first() は TreeSet 内の最小要素を返す。O(log n) の計算量。
        // TreeSet は SortedSet インターフェースを実装しているため、
        // first(), last() などの追加メソッドが使える。
        // 出力: 最小: 1

        // System.out.println("最大: " + treeSet.last());
        // last() は TreeSet 内の最大要素を返す。
        // 出力: 最大: 5

        // System.out.println("3以下: " + treeSet.headSet(3, true)); // [1, 3]
        // headSet(toElement, inclusive) は、指定した要素より小さい（またはそれ以下の）要素の
        // ビュー（元のセットに連動する部分集合）を返す。
        // 第2引数 true は「3自身を含む」という意味。false なら3は含まれない。
        // NavigableSet インターフェースのメソッド。
        // 他にも tailSet(fromElement, inclusive)（指定以上）、
        // subSet(from, fromInclusive, to, toInclusive)（範囲指定）がある。
        // 出力: 3以下: [1, 3]

        // =============================================
        // TreeMap（キーがソート済みのMap）
        // =============================================

        // TreeMap<String, Integer> treeMap = new TreeMap<>();
        // TreeMap はキーを自動的にソートされた状態で保持する Map。
        // 内部構造は TreeSet と同じく赤黒木。
        // HashMap は挿入順が保証されないが、TreeMap はキーが常にソートされている。
        // キーは Comparable を実装しているか、コンストラクタで Comparator を渡す必要がある。
        // String は Comparable<String> を実装しているので辞書順（Unicode順）でソートされる。
        // 計算量: put/get/remove すべて O(log n)。HashMap の O(1) より遅い。

        // treeMap.put("banana", 2); treeMap.put("apple", 5); treeMap.put("cherry", 1);
        // put(key, value) でキーと値のペアを追加する。
        // 追加順は banana, apple, cherry だが、TreeMap はキーを自動ソートする。
        // 同じキーで再度 put() すると、値が上書きされる（前の値が戻り値として返る）。

        // System.out.println("\nTreeMap: " + treeMap);  // {apple=5, banana=2, cherry=1}
        // キーがアルファベット順にソートされて表示される。
        // \n は改行文字で、前の出力との間に空行を入れるため。
        // 出力形式は {key1=value1, key2=value2, ...}。

        // System.out.println("最初のキー: " + treeMap.firstKey());
        // firstKey() はソート順で最初の（最小の）キーを返す。
        // TreeMap は SortedMap / NavigableMap インターフェースを実装しているため、
        // firstKey(), lastKey(), floorKey(), ceilingKey() 等の追加メソッドが使える。
        // 出力: 最初のキー: apple

        // =============================================
        // LinkedHashMap（挿入順を保持するMap）
        // =============================================

        // LinkedHashMap<String, Integer> lhm = new LinkedHashMap<>();
        // LinkedHashMap は HashMap の機能に加えて、要素の挿入順序を保持する。
        // 内部的にはハッシュテーブル + 双方向リンクリストで実装されている。
        // HashMap より若干メモリを多く使うが、計算量は同じ O(1)。
        // 挿入した順序で要素を走査できるのが最大のメリット。
        // 【使いどころ】JSON のフィールド順序を保持したい場合、
        //   キャッシュの LRU（Least Recently Used）実装などに便利。
        //   LRU キャッシュの場合、コンストラクタの第3引数に true を渡すと
        //   アクセス順序で並び替えるモードになる。

        // lhm.put("C", 3); lhm.put("A", 1); lhm.put("B", 2);
        // C, A, B の順に追加する。

        // System.out.println("\nLinkedHashMap: " + lhm);  // {C=3, A=1, B=2}（挿入順）
        // 通常の HashMap なら順序は不定だが、LinkedHashMap は挿入順を保持するため、
        // C, A, B の順で表示される。
        // TreeMap なら A, B, C（アルファベット順）になる。
        // 出力: LinkedHashMap: {C=3, A=1, B=2}

        // =============================================
        // ArrayDeque（両端キュー / Stack の代替）
        // =============================================

        // ArrayDeque<String> deque = new ArrayDeque<>();
        // ArrayDeque は「両端キュー (Double-Ended Queue)」の配列ベース実装。
        // 先頭と末尾の両方から要素の追加・取得・削除ができる。
        // 【Stack クラスより ArrayDeque が推奨される理由】
        //   - Stack は Vector のサブクラスで、全メソッドが synchronized（同期化）されている。
        //     シングルスレッドで使う場合、この同期化はオーバーヘッドになる。
        //   - ArrayDeque は非同期で高速。
        //   - Java の公式ドキュメントでも ArrayDeque の使用が推奨されている。
        // 【計算量】先頭・末尾への追加/削除: O(1)（償却）
        // 【注意】null は格納できない（NullPointerException が発生する）。

        // // スタックとして使う（LIFO: Last In First Out / 後入れ先出し）
        // deque.push("A"); deque.push("B"); deque.push("C");
        // push() は要素をデックの先頭（スタックの「上」）に追加する。
        // push("A") → [A]
        // push("B") → [B, A]（B が先頭に追加される）
        // push("C") → [C, B, A]（C が先頭に追加される）
        // push() は addFirst() と同じ動作をする。

        // System.out.println("\nStack: " + deque.pop());  // C
        // pop() はデックの先頭（スタックの「上」）から要素を取り出して削除する。
        // 最後に push された "C" が返される（LIFO）。
        // pop() 後のデック: [B, A]
        // pop() は removeFirst() と同じ動作をする。
        // デックが空の状態で pop() を呼ぶと NoSuchElementException が発生する。
        // 安全に取得したい場合は pollFirst() を使う（空なら null を返す）。

        // // キューとして使う（FIFO: First In First Out / 先入れ先出し）
        // deque.addLast("D");
        // addLast() は要素をデックの末尾に追加する。
        // 現在のデック: [B, A] → addLast("D") → [B, A, D]
        // キューの「列に並ぶ」操作に相当する。
        // offer() や offerLast() も同じ動作をするが、
        // 容量制限のあるデック（LinkedBlockingDeque等）では挙動が異なる。

        // System.out.println("Queue: " + deque.pollFirst());  // A
        // pollFirst() はデックの先頭から要素を取り出して削除する。
        // 【注意】ここでの出力は "B" になる（"A" ではない）。
        // 現在のデックは [B, A, D] なので、先頭の "B" が取り出される。
        // pollFirst() はデックが空の場合 null を返す（例外は発生しない）。
        // removeFirst() はデックが空の場合 NoSuchElementException をスローする。
        // キューの「順番が来たので列から抜ける」操作に相当する。

        // =============================================
        // 不変コレクション（Immutable Collections）
        // =============================================

        // List<String> immutable = List.of("A", "B", "C");
        // List.of() は Java 9 で追加された、変更不可（不変）のリストを作成するメソッド。
        // 要素の追加・削除・変更はすべて UnsupportedOperationException をスローする。
        // null 要素も許可されない（NullPointerException が発生する）。
        // 【不変コレクションのメリット】
        //   - スレッドセーフ: 変更されないので同期化不要
        //   - 安全: メソッドの引数や戻り値で渡しても、呼び出し先で変更される心配がない
        //   - 意図の明確化: 「このリストは変更しない」というプログラマの意思を表現できる
        // 【類似メソッド】Set.of(), Map.of(), Map.ofEntries()
        // 【Arrays.asList() との違い】
        //   Arrays.asList() は要素の変更（set）は可能だが、サイズ変更（add/remove）は不可。
        //   List.of() は一切の変更が不可。

        // // immutable.add("D");  // UnsupportedOperationException!
        // 不変リストに対して add() を呼ぶと、UnsupportedOperationException が発生する。
        // set(), remove(), clear() なども同様にエラーになる。
        // これはコンパイル時にはエラーにならず、実行時に例外が発生する点に注意。
        // List インターフェースには add() メソッドがあるため、コンパイラはエラーにできない。

        // System.out.println("\n不変List: " + immutable);
        // 出力: 不変List: [A, B, C]
        // 不変リストも通常のリストと同様に toString() で内容を表示できる。

        // ===== 練習問題 =====
        // 1. TreeMapを使って単語の出現回数をアルファベット順に表示してみよう
        //    ヒント: String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};
        //    TreeMap<String, Integer> countMap = new TreeMap<>();
        //    for (String w : words) { countMap.merge(w, 1, Integer::sum); }
        //    merge() は「キーが存在しなければ追加、存在すれば値を合成」する便利なメソッド。
        //
        // 2. ArrayDequeでStack(LIFO)とQueue(FIFO)の両方を試してみよう
        //    ヒント: push/pop でスタック操作、addLast/pollFirst でキュー操作。
        //    括弧の対応チェック（例: "{[()]}" が正しいか）はスタックの典型的な応用問題。
    }
}
