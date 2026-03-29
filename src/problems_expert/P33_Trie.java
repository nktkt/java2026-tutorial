package problems_expert;

/**
 * ===== エキスパート03: トライ木 (Trie / Prefix Tree) =====
 * 出典: LC #208
 *
 * 【問題】Trieを実装せよ。insert, search, startsWith の3操作をサポート。
 *
 * 【Trieとは？】
 *   文字列を効率的に格納・検索する木構造。
 *   各ノードが1文字に対応し、root→leaf で1つの単語を表す。
 *   単語の検索・接頭辞検索が O(m)（m=文字列長）で可能。
 *
 * 【具体例で理解する】
 *   "apple", "app", "bat" を挿入した後のTrieの形:
 *
 *        root
 *       /    \
 *      a      b
 *      |      |
 *      p      a
 *      |      |
 *      p*     t*     ← * は isEnd=true（ここで単語が終わる）
 *      |
 *      l
 *      |
 *      e*
 *
 *   search("app")     → 'a'→'p'→'p' と辿り、isEnd=true → true
 *   search("ap")      → 'a'→'p' と辿り、isEnd=false → false（"ap"という単語はない）
 *   startsWith("ap")  → 'a'→'p' と辿れる → true（"ap"で始まる単語がある）
 *   search("cat")     → 'c' のノードが root に存在しない → false
 *
 * 【各操作の動き: insert("apple")】
 *   root → children['a'-'a'] = children[0] が null → 新規作成
 *   → children['p'-'a'] = children[15] が null → 新規作成
 *   → children['p'-'a'] = children[15] が null → 新規作成
 *   → children['l'-'a'] = children[11] が null → 新規作成
 *   → children['e'-'a'] = children[4] が null → 新規作成
 *   最後のノード（'e'のノード）の isEnd = true に設定
 *
 * 【計算量】
 *   時間計算量:
 *     - insert: O(m)   ... m = 挿入する文字列の長さ（各文字を1回ずつ処理）
 *     - search: O(m)   ... m = 検索する文字列の長さ
 *     - startsWith: O(m) ... m = 接頭辞の長さ
 *   空間計算量:
 *     - O(N * L * 26) ... N = 挿入する単語数、L = 平均文字列長、26 = アルファベット数
 *     - 各ノードが26個の子ポインタを持つため、メモリ効率は良くない
 *     - HashMap を使えばメモリ効率は改善するが、定数倍が遅くなる
 *
 * 【競プロTips】
 *   - Trie は文字列検索の基本データ構造。接頭辞に関する問題に非常に有効
 *   - 「辞書中の全単語について、与えられた文字列のどこから始まるか」→ Aho-Corasick（Trie + KMP的失敗関数）
 *   - XOR最大化問題: 整数をビット列としてTrieに入れ、貪欲に最大XORを求める
 *   - children を配列[26]ではなく HashMap<Character, TrieNode> にすると、
 *     文字種が多い場合（Unicode等）にメモリ節約できる
 *   - 配列ベースのTrie（ノードをint配列で管理）は高速で競プロ向き
 *
 * 【学べること】木構造の自作、文字列探索の効率化
 */
public class P33_Trie {

    // =====================================================================
    // TrieNode: Trieの各ノードを表す内部クラス
    // =====================================================================
    // static class TrieNode {
    //     TrieNode[] children = new TrieNode[26];
    //     // 子ノードの配列。children[0]='a', children[1]='b', ..., children[25]='z'
    //     // null の場合、その文字への枝は存在しない
    //     // サイズ26はアルファベット小文字のみを想定（問題の制約に応じて変える）
    //
    //     boolean isEnd = false;
    //     // このノードで単語が終わるかどうかのフラグ
    //     // true: このノードまでの経路が1つの完全な単語を表す
    //     // 例: "app" と "apple" の両方が挿入されている場合、
    //     //     2つ目の 'p' のノードと 'e' のノードの両方で isEnd=true
    // }
    //
    // =====================================================================
    // root: Trieのルートノード（空文字列に対応）
    // =====================================================================
    // static TrieNode root = new TrieNode();
    // // ルートは文字を持たない特殊なノード。全ての単語の探索はここから始まる
    //
    // =====================================================================
    // insert: 単語をTrieに挿入する
    //   word: 挿入する文字列（小文字アルファベットのみ）
    // =====================================================================
    // static void insert(String word) {
    //     TrieNode node = root;
    //     // ルートから探索開始。node は現在位置を表すポインタ
    //
    //     for (char c : word.toCharArray()) {
    //         // 文字列を1文字ずつ処理
    //         // toCharArray() で String を char[] に変換して拡張forで回す
    //
    //         int idx = c - 'a';
    //         // 文字を配列インデックスに変換: 'a'→0, 'b'→1, ..., 'z'→25
    //         // ASCIIコードの差を利用した定番テクニック
    //
    //         if (node.children[idx] == null) {
    //             node.children[idx] = new TrieNode();
    //             // この文字への枝がまだ存在しない場合、新しいノードを作成
    //         }
    //
    //         node = node.children[idx];
    //         // 子ノードに移動（木を1段下がる）
    //     }
    //
    //     node.isEnd = true;
    //     // 最後の文字のノードに「ここで単語が終わる」マークを付ける
    //     // これがないと "apple" を挿入しても search("app") が false にならない
    // }
    //
    // =====================================================================
    // search: 単語がTrieに存在するか検索する
    //   word: 検索する文字列
    //   戻り値: 完全一致する単語が存在すれば true
    // =====================================================================
    // static boolean search(String word) {
    //     TrieNode node = findNode(word);
    //     // findNode で word の最後の文字に対応するノードを探す
    //
    //     return node != null && node.isEnd;
    //     // node != null: 文字列の全文字分の経路が存在する
    //     // node.isEnd: その位置で実際に単語が終わっている
    //     // 両方の条件が必要。経路があっても isEnd=false なら別の単語の途中
    // }
    //
    // =====================================================================
    // startsWith: 指定された接頭辞で始まる単語がTrieに存在するか
    //   prefix: 検索する接頭辞
    //   戻り値: その接頭辞で始まる単語が1つでもあれば true
    // =====================================================================
    // static boolean startsWith(String prefix) {
    //     return findNode(prefix) != null;
    //     // 接頭辞の全文字分の経路が存在すれば true
    //     // search と違い、isEnd のチェックは不要
    //     // 経路が存在する = その先に何らかの単語が続いている（または prefix 自体が単語）
    // }
    //
    // =====================================================================
    // findNode: 文字列 s の最後の文字に対応するノードを返す共通ヘルパー
    //   s: 探索する文字列
    //   戻り値: s の最後の文字に対応するノード。経路が途切れた場合は null
    // =====================================================================
    // static TrieNode findNode(String s) {
    //     TrieNode node = root;
    //     // ルートから探索開始
    //
    //     for (char c : s.toCharArray()) {
    //         // 1文字ずつTrieを辿る
    //
    //         int idx = c - 'a';
    //         // 文字をインデックスに変換
    //
    //         if (node.children[idx] == null) return null;
    //         // この文字への枝が存在しない → 文字列 s はTrieに存在しない
    //         // 早期リターンで無駄な探索を省く
    //
    //         node = node.children[idx];
    //         // 子ノードに移動
    //     }
    //
    //     return node;
    //     // 全文字を辿り終えた → s の最後の文字に対応するノードを返す
    //     // このノードの isEnd を呼び出し側でチェックすることで search と startsWith を区別
    // }

    public static void main(String[] args) {
        // insert("apple");                          // "apple" をTrieに挿入
        // System.out.println(search("apple"));      // true: "apple" は挿入済み
        // System.out.println(search("app"));        // false: "app" は挿入していない（isEnd=false）
        // System.out.println(startsWith("app"));    // true: "apple" が "app" で始まる
        // insert("app");                            // "app" をTrieに挿入（'p'のノードの isEnd=true に）
        // System.out.println(search("app"));        // true: "app" が挿入された
    }
}
