package problems_expert;

/**
 * ===== エキスパート06: KMP文字列探索 =====
 * 出典: LC #28 上級版 / 文字列アルゴリズムの基本
 *
 * 【問題】
 *   テキスト中にパターンが最初に現れる位置を求めよ。
 *
 * 【ナイーブな方法 O(n*m) の問題点】
 *   テキストの各位置からパターンと1文字ずつ比較。
 *   不一致時にテキスト位置を1つだけ進めてやり直す → 同じ文字を何度もチェックして無駄。
 *
 *   例: text="AAAAAB", pattern="AAAB"
 *   位置0: AAA"A" vs AAA"B" → 不一致（4回比較して失敗）
 *   位置1: AAA"B" vs AAA"B" → 不一致（4回比較して失敗）
 *   位置2: AAB vs AAA → 不一致...  → 合計 O(n*m) 回の比較
 *
 * 【KMPアルゴリズム O(n+m)】
 *   失敗関数(failure function / prefix function)を前計算し、
 *   不一致時にパターンのどこまで戻れば良いかを O(1) で決定。
 *   テキストのポインタは絶対に後退しない → O(n+m)
 *
 * 【失敗関数とは？】
 *   fail[i] = pattern[0..i] の「最長の、接頭辞かつ接尾辞の長さ」
 *   （ただし文字列全体は含まない）
 *
 *   例: pattern = "ABABCABAB"
 *     fail[0] = 0  "A"         → 接頭辞=接尾辞は空のみ
 *     fail[1] = 0  "AB"        → 一致する接頭辞=接尾辞なし
 *     fail[2] = 1  "ABA"       → "A" が接頭辞かつ接尾辞
 *     fail[3] = 2  "ABAB"      → "AB" が接頭辞かつ接尾辞
 *     fail[4] = 0  "ABABC"     → 一致する接頭辞=接尾辞なし
 *     fail[5] = 1  "ABABCA"    → "A" が接頭辞かつ接尾辞
 *     fail[6] = 2  "ABABCAB"   → "AB" が接頭辞かつ接尾辞
 *     fail[7] = 3  "ABABCABA"  → "ABA" が接頭辞かつ接尾辞
 *     fail[8] = 4  "ABABCABAB" → "ABAB" が接頭辞かつ接尾辞
 *
 * 【失敗関数の意味】
 *   不一致が pattern[j] で起きた場合、pattern[0..j-1] まではマッチ済み。
 *   fail[j-1] の値が「マッチ済み部分の中で再利用できる長さ」を教えてくれる。
 *   j = fail[j-1] に戻れば、テキストのポインタを動かさずに探索を続行できる。
 *
 * 【KMP探索の具体例】
 *   text = "ABABDABACDABABCABAB", pattern = "ABABCABAB"
 *   fail = [0,0,1,2,0,1,2,3,4]
 *
 *   i=0,j=0: A==A → j=1
 *   i=1,j=1: B==B → j=2
 *   i=2,j=2: A==A → j=3
 *   i=3,j=3: B==B → j=4
 *   i=4,j=4: D!=C → j=fail[3]=2  (ABは再利用)
 *   i=4,j=2: D!=A → j=fail[1]=0
 *   i=4,j=0: D!=A → i進む
 *   ...（省略）...
 *   i=10,j=0: A==A → j=1
 *   ...
 *   i=18,j=9: j==pattern.length() → マッチ！位置=18-9+1=10
 *
 * 【計算量】
 *   時間計算量: O(n + m)
 *     - 失敗関数の構築: O(m) ... m = パターン長
 *     - 探索: O(n)           ... n = テキスト長
 *     - 合計 O(n + m)
 *     - ポイント: i は常に増加し、j は fail で戻る回数が合計で O(n) に抑えられる
 *       （j が1増えるのは i が1増える時だけ。j は0未満にならないので、
 *        j の減少回数の合計 <= j の増加回数の合計 <= n）
 *   空間計算量: O(m)
 *     - 失敗関数の配列サイズ
 *
 * 【競プロTips】
 *   - Java では String.indexOf() が O(n*m) なので、大量のマッチングには KMP が有効
 *   - KMP の失敗関数自体が「文字列の周期構造」を教えてくれる
 *     → 例: 周期の長さ = m - fail[m-1]
 *   - Z-Algorithm も同等の機能を持ち、場合によっては実装が簡単
 *   - 複数パターンの同時検索 → Aho-Corasick（Trie + 失敗関数）
 *   - 文字列ハッシュ（Rabin-Karp）は確率的だが実装が簡単で競プロで多用される
 *
 * 【学べること】失敗関数、文字列マッチングの効率化
 */
public class P36_KMP {

    public static void main(String[] args) {
        // System.out.println(kmpSearch("ABABDABACDABABCABAB", "ABABCABAB"));
        // // → 10（パターンが位置10から一致）
        // System.out.println(kmpSearch("hello world", "world"));
        // // → 6（"world"は位置6から）
    }

    // =====================================================================
    // buildFailure: 失敗関数（prefix function）を構築する
    //   pattern: パターン文字列
    //   戻り値: 失敗関数の配列 fail[i] = pattern[0..i] の最長 proper prefix-suffix の長さ
    //
    //   この関数自体が KMP 探索と同じロジック（パターンの自己マッチング）
    // =====================================================================
    // static int[] buildFailure(String pattern) {
    //     int[] fail = new int[pattern.length()];
    //     // fail[i] = pattern[0..i] の中で「接頭辞＝接尾辞」となる最長の長さ
    //     // fail[0] = 0 は自明（1文字では proper prefix-suffix は空のみ）
    //     // Java の int 配列は 0 で初期化されるので明示的な初期化不要
    //
    //     int j = 0;
    //     // j = 現在マッチ中の接頭辞の長さ
    //     // pattern[0..j-1] が pattern[?..i-1] と一致している状態
    //
    //     for (int i = 1; i < pattern.length(); i++) {
    //         // i = 1 から開始（i=0 は fail[0]=0 で確定済み）
    //
    //         while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
    //             j = fail[j - 1];
    //             // 不一致: pattern[i] != pattern[j]
    //             // j を fail[j-1] に戻す（より短い接頭辞＝接尾辞を試す）
    //             // これが KMP の核心: 完全にやり直すのではなく、再利用できる部分まで戻る
    //             // j=0 になったらこれ以上戻れないのでループ終了
    //         }
    //
    //         if (pattern.charAt(i) == pattern.charAt(j)) j++;
    //         // 一致: マッチ中の接頭辞の長さを1増やす
    //
    //         fail[i] = j;
    //         // fail[i] に現在のマッチ長を記録
    //     }
    //
    //     return fail;
    // }
    //
    // =====================================================================
    // kmpSearch: KMPアルゴリズムでテキスト中のパターンを探索する
    //   text: 探索対象のテキスト
    //   pattern: 探索するパターン
    //   戻り値: パターンが最初に現れる位置（0-indexed）。見つからない場合は -1
    // =====================================================================
    // static int kmpSearch(String text, String pattern) {
    //     int[] fail = buildFailure(pattern);
    //     // 事前に失敗関数を構築。O(m) の前計算
    //
    //     int j = 0;
    //     // j = パターン側のポインタ（何文字目まで一致しているか）
    //
    //     for (int i = 0; i < text.length(); i++) {
    //         // i = テキスト側のポインタ（常に +1 ずつ進む。後退しない！）
    //
    //         while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
    //             j = fail[j - 1];
    //             // 不一致: text[i] != pattern[j]
    //             // j を fail[j-1] に戻す（再利用可能な接頭辞の長さまで戻る）
    //             // 失敗関数のおかげで、テキスト側は後退不要
    //             // 最悪でも j が 0 になるまでループ
    //         }
    //
    //         if (text.charAt(i) == pattern.charAt(j)) j++;
    //         // 一致: パターン側のポインタを1進める
    //
    //         if (j == pattern.length()) return i - j + 1;
    //         // パターン全体がマッチした！
    //         // マッチ開始位置 = i（現在のテキスト位置）- j（パターン長）+ 1
    //         // 全てのマッチ位置を見つけたい場合: ここで return せず結果をリストに追加し、
    //         // j = fail[j-1] として探索を続行する
    //     }
    //
    //     return -1;
    //     // テキスト全体を走査してもパターンが見つからなかった
    // }
}
