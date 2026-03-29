package lesson13_arraylist;

// 使用するクラスを import する
import java.util.ArrayList;    // サイズ可変のリスト
import java.util.Collections;  // ソート・検索などの便利メソッド集
import java.util.LinkedList;   // 挿入/削除が速いリスト（参考）
import java.util.List;         // リストの共通インターフェース

/**
 * ===== レッスン13: ArrayList と Collections =====
 *
 * 【目標】サイズ可変のリストと便利な操作を学ぶ
 *
 * 【配列 vs ArrayList】
 *   配列:      int[] a = new int[5];      サイズ固定。作った後に要素数を変えられない。
 *   ArrayList:  ArrayList<Integer> list;   サイズ可変。add() で好きなだけ追加できる。
 *
 * 【ラッパークラス（Wrapper Class）】
 *   ArrayList にはプリミティブ型(int, double等)を直接入れられない。
 *   代わりにオブジェクト型のラッパークラスを使う:
 *     int     → Integer
 *     double  → Double
 *     char    → Character
 *     boolean → Boolean
 *
 *   ただし Java が自動変換してくれる（オートボクシング/アンボクシング）ので
 *   普段は意識しなくてOK:
 *     list.add(42);           // int → Integer に自動変換（オートボクシング）
 *     int n = list.get(0);    // Integer → int に自動変換（アンボクシング）
 *
 * 【ArrayList の主要メソッド】
 *   add(値)          → 末尾に要素を追加                         O(1)
 *   add(index, 値)   → 指定位置に要素を挿入（後ろがずれる）       O(n)
 *   get(index)       → 指定位置の要素を取得                     O(1)
 *   set(index, 値)   → 指定位置の要素を上書き                   O(1)
 *   remove(index)    → 指定位置の要素を削除（後ろが詰まる）       O(n)
 *   size()           → 要素数を返す                            O(1)
 *   contains(値)     → 値が含まれているか（true/false）          O(n)
 *   indexOf(値)      → 値の位置を返す（なければ-1）              O(n)
 *   isEmpty()        → 空かどうか（true/false）                 O(1)
 *   clear()          → 全要素を削除                            O(n)
 *
 * 【Collections クラス】 java.util.Collections
 *   List に対する便利な static メソッドを提供するユーティリティクラス:
 *   Collections.sort(list)          → 昇順ソート
 *   Collections.reverse(list)       → 逆順にする
 *   Collections.max(list)           → 最大値を返す
 *   Collections.min(list)           → 最小値を返す
 *   Collections.frequency(list, 値) → 値の出現回数を返す
 *
 * 【List インターフェース】
 *   ArrayList と LinkedList は共通の List インターフェースを実装している。
 *   変数の型を List<String> にしておくと、後で実装クラスを変えても
 *   残りのコードを変更する必要がない（ポリモーフィズムの活用）。
 *
 *   推奨: List<String> list = new ArrayList<>();
 */
public class ArrayListLesson {

    public static void main(String[] args) {

        // ---------- ArrayList の作成と基本操作 ----------
        // // <String> = ジェネリクス。この ArrayList には String だけ入る。
        // // <> の中にプリミティブ型(int等)は書けない → Integer 等を使う
        // ArrayList<String> fruits = new ArrayList<>();
        //
        // // add(値): 末尾に要素を追加する
        // fruits.add("りんご");     // [りんご]
        // fruits.add("バナナ");     // [りんご, バナナ]
        // fruits.add("みかん");     // [りんご, バナナ, みかん]
        //
        // // add(index, 値): 指定位置に挿入。既存の要素は右にずれる。
        // fruits.add(1, "ぶどう");  // [りんご, ぶどう, バナナ, みかん]
        //
        // System.out.println("--- 基本操作 ---");
        //
        // // toString(): ArrayList を直接 println すると [要素1, 要素2, ...] 形式で表示
        // System.out.println("リスト: " + fruits);
        //
        // // size(): 要素数を返す（配列の .length に相当）
        // System.out.println("要素数: " + fruits.size());     // → 4
        //
        // // get(index): 指定位置の要素を取得（配列の [index] に相当）
        // System.out.println("2番目: " + fruits.get(1));      // → ぶどう
        //
        // // contains(値): 値がリストに含まれているか
        // System.out.println("含む?: " + fruits.contains("バナナ")); // → true

        // ---------- 変更と削除 ----------
        // // set(index, 値): 指定位置の要素を新しい値で上書きする
        // fruits.set(0, "いちご");  // 0番目の「りんご」を「いちご」に変更
        //
        // // remove(index): 指定位置の要素を削除。後ろの要素が詰まる。
        // fruits.remove(2);         // インデックス2（バナナ）を削除
        //
        // System.out.println("\n変更後: " + fruits);
        // // → [いちご, ぶどう, みかん]

        // ---------- ループ ----------
        // System.out.println("\n--- ループ ---");
        //
        // // 方法1: 拡張for文（for-each）— シンプルで推奨
        // for (String fruit : fruits) {
        //     System.out.println("  " + fruit);
        // }
        //
        // // 方法2: インデックス付きfor文 — インデックスが必要なとき
        // for (int i = 0; i < fruits.size(); i++) {
        //     System.out.println("  [" + i + "] " + fruits.get(i));
        // }

        // ---------- 数値の ArrayList ----------
        // // int ではなく Integer を使う（ラッパークラス）
        // // ただし add(30) のように int を渡しても自動変換される
        // ArrayList<Integer> numbers = new ArrayList<>();
        // numbers.add(30);   // int 30 → Integer.valueOf(30) に自動変換
        // numbers.add(10);
        // numbers.add(50);
        // numbers.add(20);
        //
        // System.out.println("\n--- Collections ---");
        // System.out.println("ソート前: " + numbers);
        //
        // // Collections.sort(): 昇順（小さい順）にソート
        // Collections.sort(numbers);
        // System.out.println("ソート後: " + numbers);           // [10, 20, 30, 50]
        //
        // // Collections.reverse(): 順序を逆にする
        // Collections.reverse(numbers);
        // System.out.println("逆順: " + numbers);               // [50, 30, 20, 10]
        //
        // // Collections.max() / min(): 最大値・最小値を返す
        // System.out.println("最大: " + Collections.max(numbers));   // 50
        // System.out.println("最小: " + Collections.min(numbers));   // 10

        // ---------- List インターフェースで宣言（推奨） ----------
        // // 変数の型を List にしておくと、ArrayList → LinkedList に変えても
        // // この行以外のコードを変更する必要がない（ポリモーフィズム）
        // List<String> list = new ArrayList<>();
        // list.add("A");
        // list.add("B");
        // // List<String> list = new LinkedList<>();  ← これに変えても残りのコードは同じ

        // ===== 練習問題 =====
        // 1. 5つの名前を ArrayList に入れ、ソートして表示してみよう
        // 2. 数値の ArrayList から偶数だけを別の ArrayList に抽出してみよう
        // 3. 文字列の ArrayList から重複を除去してみよう（ヒント: contains で確認してから追加）
    }
}
