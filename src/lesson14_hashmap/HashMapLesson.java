package lesson14_hashmap;

// 使用するコレクションクラスを import
import java.util.HashMap;   // キーと値のペアを保存するマップ
import java.util.HashSet;   // 重複のない要素の集合
import java.util.Map;       // Map の共通インターフェース
import java.util.Set;       // Set の共通インターフェース

/**
 * ===== レッスン14: HashMap と HashSet =====
 *
 * 【目標】キーと値のペア、重複のない集合を扱う方法を学ぶ
 *
 * 【HashMap<K, V> とは？】
 *   キー(Key) と 値(Value) のペアを保存するデータ構造。
 *   キーで値を高速に検索できる（平均 O(1)）。
 *   配列が「インデックス→値」なら、HashMap は「任意のキー→値」。
 *
 *   実例:
 *     名前 → 電話番号      ("田中" → "090-1234-5678")
 *     単語 → 出現回数      ("apple" → 3)
 *     商品ID → 価格        (1001 → 2500)
 *
 * 【HashMap の主要メソッド】
 *   put(key, value)        → キーと値のペアを追加。同じキーなら上書き。  O(1)
 *   get(key)               → キーに対応する値を取得。なければ null。    O(1)
 *   getOrDefault(key, def) → キーに対応する値を取得。なければ def を返す。O(1)
 *   containsKey(key)       → キーが存在するか（true/false）            O(1)
 *   containsValue(value)   → 値が存在するか（true/false）              O(n)
 *   remove(key)            → キーとそのペアを削除                      O(1)
 *   size()                 → ペアの数                                 O(1)
 *   keySet()               → すべてのキーを Set で取得
 *   values()               → すべての値を Collection で取得
 *   entrySet()             → すべてのキー&値ペアを Set<Map.Entry> で取得
 *
 * 【HashSet<E> とは？】
 *   重複のない要素の集合（数学の「集合」と同じ概念）。
 *   順序は保証されない。要素の存在確認が高速（平均 O(1)）。
 *
 * 【HashSet の主要メソッド】
 *   add(値)       → 追加（すでにあれば何もしない、false を返す）  O(1)
 *   contains(値)  → 値が含まれているか（true/false）             O(1)
 *   remove(値)    → 値を削除                                    O(1)
 *   size()        → 要素数                                     O(1)
 *
 * 【使い分け】
 *   HashMap: 「キー → 値」の対応が必要なとき
 *   HashSet: 重複を排除したい、存在確認を高速にしたいとき
 *   ArrayList: 順序が重要、インデックスアクセスが必要なとき
 */
public class HashMapLesson {

    public static void main(String[] args) {

        // ========== HashMap ==========
        // // <String, Integer> → キーが String 型、値が Integer 型
        // HashMap<String, Integer> scores = new HashMap<>();
        //
        // // put(key, value): キーと値のペアを追加
        // scores.put("田中", 85);
        // scores.put("鈴木", 92);
        // scores.put("佐藤", 78);
        //
        // // 同じキーで put すると値が上書きされる（キーは一意）
        // scores.put("田中", 90);   // 田中の点数が 85 → 90 に変わる
        //
        // System.out.println("--- HashMap基本 ---");
        //
        // // get(key): キーに対応する値を取得。キーが存在しなければ null を返す。
        // System.out.println("田中の点数: " + scores.get("田中"));    // → 90
        // System.out.println("山田の点数: " + scores.get("山田"));    // → null（存在しない）
        //
        // // getOrDefault(key, default): get() と同じだが、キーがなければ default を返す
        // // null チェックが不要になるので便利
        // System.out.println("山田(default): " + scores.getOrDefault("山田", 0)); // → 0
        //
        // // containsKey(key): キーが存在するか
        // System.out.println("鈴木がいるか: " + scores.containsKey("鈴木")); // → true
        //
        // // size(): ペアの数
        // System.out.println("要素数: " + scores.size());    // → 3

        // ---------- ループ ----------
        // System.out.println("\n--- HashMapループ ---");
        //
        // // 方法1: entrySet() でキーと値を同時に取得（最も推奨）
        // // Map.Entry<K,V> はキーと値のペアを表すオブジェクト
        // for (Map.Entry<String, Integer> entry : scores.entrySet()) {
        //     // getKey(): キーを取得、getValue(): 値を取得
        //     System.out.println("  " + entry.getKey() + " → " + entry.getValue());
        // }
        //
        // // 方法2: keySet() でキーだけ取得し、get() で値を取る
        // for (String name : scores.keySet()) {
        //     System.out.println("  " + name + ": " + scores.get(name));
        // }

        // ---------- 頻出パターン: 出現回数を数える ----------
        // // 【超重要パターン】競プロ・実務で非常によく使う
        // // 各要素の出現回数を HashMap で数える
        //
        // String text = "apple banana apple cherry banana apple";
        // String[] words = text.split(" ");
        // // → words = {"apple", "banana", "apple", "cherry", "banana", "apple"}
        //
        // HashMap<String, Integer> wordCount = new HashMap<>();
        // for (String word : words) {
        //     // getOrDefault: キーがなければ 0 を返す → それに +1 して put
        //     // apple の場合: 0+1=1 → 1+1=2 → 2+1=3
        //     wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        // }
        // System.out.println("\n--- 単語カウント ---");
        // System.out.println(wordCount);
        // // → {apple=3, banana=2, cherry=1}

        // ========== HashSet ==========
        // HashSet<String> set = new HashSet<>();
        //
        // // add(値): 要素を追加。すでに存在する場合は追加されず false を返す。
        // set.add("Java");     // true（追加された）
        // set.add("Python");   // true
        // set.add("Java");     // false（すでに存在、追加されない）
        // set.add("C++");      // true
        //
        // System.out.println("\n--- HashSet ---");
        // System.out.println("Set: " + set);          // 順序は不定（{Java, C++, Python} 等）
        // System.out.println("要素数: " + set.size()); // → 3（Java は1回だけ）
        //
        // // contains(値): 要素が含まれているか。O(1) で高速。
        // // ArrayList の contains は O(n) なので、大量データの存在確認は HashSet が圧倒的に速い
        // System.out.println("Java含む: " + set.contains("Java")); // → true

        // ---------- 頻出パターン: 重複チェック ----------
        // // 配列に重複する要素があるかを HashSet で判定
        // int[] nums = {1, 3, 5, 3, 7, 1};
        // HashSet<Integer> seen = new HashSet<>();
        // boolean hasDuplicate = false;
        //
        // for (int n : nums) {
        //     if (seen.contains(n)) {
        //         // すでに seen に入っている → 重複！
        //         hasDuplicate = true;
        //         System.out.println("重複発見: " + n);
        //         break;
        //     }
        //     seen.add(n);  // まだ見てない要素を set に追加
        // }

        // ===== 練習問題 =====
        // 1. 文字列の各文字の出現回数を HashMap で数えてみよう
        //    例: "hello" → {h=1, e=1, l=2, o=1}
        // 2. int配列に重複があるか HashSet で判定してみよう
        // 3. 2つの ArrayList の共通要素を HashSet で見つけてみよう
        //    ヒント: 1つ目のリストを HashSet に入れ、2つ目のリストの各要素が含まれるか確認
    }
}
