package lesson34_competitive;

// java.io パッケージ: 入出力（I/O）クラス群
// BufferedReader, InputStreamReader, IOException などが含まれる
// 競技プログラミングでは Scanner の代わりに BufferedReader を使うことで高速入力を実現する
import java.io.*;

// java.util パッケージ: コレクション（List, Map等）やユーティリティクラス群
// Arrays, StringTokenizer, Collections などが含まれる
// 競技プログラミングでは Arrays.sort(), Arrays.binarySearch() などを頻繁に使う
import java.util.*;

/**
 * ===== レッスン34: 競技プログラミング実践テクニック =====
 *
 * 【目標】Codeforces/AtCoder/LeetCode で戦うための実践テクニックを学ぶ
 *
 * 【1. 高速入出力】
 *   Scanner は遅い。BufferedReader + StringTokenizer が高速。
 *   Scanner は正規表現を使ってトークン分割するため遅い（10〜100倍の差が出ることも）
 *   AtCoder/Codeforces で TLE（Time Limit Exceeded）の原因になることがある
 *
 * 【2. よく使うテンプレート】
 *   - 配列入力パターン（N個の整数を読み取る）
 *   - MOD演算（10^9+7 で余りを取る）
 *   - long のオーバーフロー対策（int の範囲は約±21億まで）
 *
 * 【3. 便利メソッド】
 *   Arrays.sort()      → プリミティブ配列のソート（O(n log n)、内部はデュアルピボットクイックソート）
 *   Arrays.fill()      → 配列の一括初期化（全要素を指定値にする）
 *   Arrays.binarySearch() → ソート済み配列での二分探索（O(log n)）
 *   Collections.sort()  → Listのソート
 *   Math.max/min/abs   → 数学関数
 *   Integer.MAX_VALUE  → int最大値（2,147,483,647 ≒ 約21億）
 *   Long.MAX_VALUE     → long最大値（約9.2 × 10^18）
 *
 * 【4. 計算量の目安】（1秒あたり約10^8回の演算が目安）
 *   N ≤ 10^6  → O(N) か O(N log N)
 *   N ≤ 10^4  → O(N^2) もギリギリOK
 *   N ≤ 500   → O(N^3) もOK
 *   N ≤ 20    → O(2^N) も間に合う
 *   N ≤ 10    → O(N!) も間に合う（全順列探索）
 *
 * 【よくあるミス（競プロ特有）】
 *   1. int のオーバーフロー（掛け算で21億を超える → long を使うべき）
 *   2. MOD を取り忘れる（途中計算で MOD を取らないとオーバーフロー）
 *   3. 配列の境界外アクセス（0-indexed vs 1-indexed の混乱）
 *   4. Scanner の遅さによる TLE
 *   5. 出力の改行漏れ、余分な空白
 */
public class CompetitiveLesson {

    // ========== 高速入力テンプレート ==========
    // 【なぜ Scanner ではダメか？】
    //   Scanner は内部で正規表現を使ってパースするため非常に遅い
    //   N=10^5 以上の入力があると TLE の原因になることがある
    //   BufferedReader + StringTokenizer の組み合わせが標準的な高速入力方法

    // BufferedReader: バッファリング付きの文字入力ストリーム
    // InputStreamReader(System.in): 標準入力（キーボード/パイプ）をバイト→文字に変換する
    // BufferedReader でラップすることで、1行ずつ効率的に読み込めるようになる
    // static フィールドにすることで、メソッド内から直接アクセスできる
    // static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // StringBuilder: 出力を溜めるための可変文字列バッファ
    // System.out.println() を何万回も呼ぶと I/O のオーバーヘッドが大きくなる
    // StringBuilder に溜めておいて最後に一括出力する方が圧倒的に速い
    // 【性能差】println() 1万回: 数百ms → StringBuilder + print(): 数ms
    // static StringBuilder sb = new StringBuilder();  // 出力用

    // readInt(): 1行から整数を1つ読み取るヘルパーメソッド
    // 入力例: "42\n" → 42 を返す
    // static int readInt() throws IOException {

    //     br.readLine() で1行を文字列として読み込む
    //     .trim() で前後の空白や改行を除去する（改行が残っているとパースエラーになる）
    //     Integer.parseInt() で文字列を int に変換する
    //     【注意】整数でない文字列が来ると NumberFormatException が発生する
    //     return Integer.parseInt(br.readLine().trim());

    // }

    // readIntArray(): 1行にスペース区切りで並んだ複数の整数を配列として読み取る
    // 入力例: "3 1 4 1 5\n" → {3, 1, 4, 1, 5} を返す
    // 【使い所】"N個の整数が1行に与えられる" という問題文で頻出
    // static int[] readIntArray() throws IOException {

    //     StringTokenizer: 文字列をデリミタ（区切り文字）で分割するクラス
    //     デフォルトのデリミタはスペース、タブ、改行
    //     String.split() より高速（正規表現を使わないため）
    //     br.readLine() で1行読み込み、StringTokenizer で空白区切りに分割する
    //     StringTokenizer st = new StringTokenizer(br.readLine());

    //     countTokens() で分割されたトークン（要素）の数を取得する
    //     この数が配列のサイズになる
    //     int n = st.countTokens();

    //     n 個の要素を持つ int 配列を確保する
    //     int[] arr = new int[n];

    //     for ループで各トークンを順番に読み取り、int に変換して配列に格納する
    //     for (int i = 0; i < n; i++) {

    //         st.nextToken() で次のトークン（文字列）を取得する
    //         Integer.parseInt() で文字列を int に変換して配列の i 番目に格納する
    //         arr[i] = Integer.parseInt(st.nextToken());

    //     }

    //     読み取った整数配列を返す
    //     return arr;

    // }

    public static void main(String[] args) {

        // ---------- MOD演算（余りの計算） ----------

        // 10^9 + 7 は競技プログラミングで最頻出の法（modulus）
        // 【なぜ 10^9+7 なのか？】
        //   ① 素数である → 逆元が計算できる（割り算のMODが可能になる）
        //   ② int に収まる最大級の素数 → long 同士の掛け算がオーバーフローしない
        //      (10^9)^2 = 10^18 < 9.2×10^18 = Long.MAX_VALUE
        //   ③ 多くの問題で「答えが大きくなるので 10^9+7 で割った余りを出力せよ」と指定される
        // 【数値リテラルの _ 区切り】1_000_000_007 のようにアンダースコアで桁区切りできる
        //   → 読みやすさのための Java 7 以降の機能（値は 1000000007 と同じ）
        // final long MOD = 1_000_000_007;   // 10^9 + 7（素数）

        // MOD 演算が必要な理由: 大きな数同士の掛け算は long でもオーバーフローする可能性がある
        // 例: a = 10^6, b = 10^6 → a * b = 10^12（long に収まる）
        // しかし: a = 10^9, b = 10^9 → a * b = 10^18（long のギリギリ）
        // 途中で MOD を取ることで、値が 10^9+7 未満に保たれ、安全に計算できる
        // long a = 1_000_000;

        // 別の大きな数を定義する
        // long b = 1_000_000;

        // (a * b) % MOD で掛け算の結果に MOD を適用する
        // a * b = 10^12 で、これを 10^9+7 で割った余りを計算する
        // 【MOD演算の重要な性質】
        //   (a + b) % MOD = ((a % MOD) + (b % MOD)) % MOD  ← 足し算は分配可能
        //   (a * b) % MOD = ((a % MOD) * (b % MOD)) % MOD  ← 掛け算も分配可能
        //   (a - b) % MOD = ((a % MOD) - (b % MOD) + MOD) % MOD  ← 引き算は+MODが必要（負の数対策）
        //   ★ 割り算は単純に分配できない → 逆元（フェルマーの小定理）を使う
        // long result = (a * b) % MOD;      // MODを取らないとオーバーフロー
        // System.out.println("MOD演算: " + result);

        // ---------- long のオーバーフロー注意 ----------

        // Integer.MAX_VALUE は int 型の最大値: 2,147,483,647（約21億）
        // この定数は配列の初期化（最大値で初期化して最小値を求める）や
        // 不可能な値のマーカーとして頻繁に使う
        // int big = Integer.MAX_VALUE;       // 約21億

        // int の範囲は -2,147,483,648 〜 2,147,483,647
        // big + 1 を int で計算すると、最大値を超えてオーバーフローする
        // Java の int オーバーフローは例外を出さず、負の値に回り込む（ラップアラウンド）
        // 2,147,483,647 + 1 = -2,147,483,648（！）
        // 【危険】オーバーフローは無言で発生するため、バグに気づきにくい
        // // int overflow = big + 1;          // → -2147483648（オーバーフロー！）

        // (long) big + 1 のように long にキャストしてから加算すれば安全
        // long の範囲は約 ±9.2×10^18 なので、int の範囲は余裕で収まる
        // 【キャストの順序が重要！】(long)(big + 1) だと int の段階でオーバーフローしてから long 化される → NG
        // (long) big + 1 だと big が先に long に変換されてから + 1 されるので安全 → OK
        // long safe = (long) big + 1;         // long にキャストすれば安全

        // int の最大値を表示して確認する
        // System.out.println("int最大: " + big);

        // long にキャストした結果: 2,147,483,648（正しい値）
        // System.out.println("long化: " + safe);

        // ---------- 配列の便利操作 ----------

        // ソートされていない int 配列を定義する
        // int[] arr = {5, 3, 1, 4, 2};

        // Arrays.sort() で配列を昇順（小さい順）にソートする
        // 計算量: O(n log n)。内部ではデュアルピボットクイックソートが使われている
        // 【注意】Arrays.sort() は配列を直接変更する（破壊的操作）。新しい配列を返さない
        // 結果: {1, 2, 3, 4, 5}
        // 【降順にしたい場合】Integer[] にして Arrays.sort(arr, Collections.reverseOrder()) を使う
        //   プリミティブ int[] では Collections.reverseOrder() が使えないことに注意
        // Arrays.sort(arr);                  // 昇順ソート O(n log n)

        // Arrays.toString() で配列の内容を文字列に変換して表示する
        // 配列を直接 println すると "[I@16進数" のようなアドレスが出てしまうが、
        // Arrays.toString() なら "[1, 2, 3, 4, 5]" のように中身が表示される
        // System.out.println("ソート: " + Arrays.toString(arr));

        // Arrays.binarySearch() でソート済み配列から値 3 の位置を二分探索する
        // 計算量: O(log n)。ソート済みでないと正しい結果が得られない！
        // 戻り値: 見つかった場合はインデックス、見つからない場合は負の値（-(挿入位置)-1）
        // 例: {1, 2, 3, 4, 5} で 3 を探す → インデックス 2 を返す
        // 【注意】同じ値が複数あるとき、どのインデックスが返るかは保証されない
        // int idx = Arrays.binarySearch(arr, 3);  // ソート済みで二分探索

        // 二分探索の結果を表示する
        // System.out.println("3の位置: " + idx);

        // サイズ 5 の新しい int 配列を作成する（全要素の初期値は 0）
        // int[] filled = new int[5];

        // Arrays.fill() で配列の全要素を指定値（-1）で埋める
        // 【使い所】
        //   - DP（動的計画法）テーブルの初期化で「未計算」を -1 で表す
        //   - 距離配列を Integer.MAX_VALUE で初期化する（最短経路問題）
        //   - visited 配列を false で初期化する（探索問題）
        // Arrays.fill(arr, 0, 3, -1) のように範囲指定も可能
        // Arrays.fill(filled, -1);           // 全要素を-1に

        // 初期化した配列の内容を表示する: [-1, -1, -1, -1, -1]
        // System.out.println("fill: " + Arrays.toString(filled));

        // ---------- 出力の高速化 ----------

        // 【問題】System.out.println() は内部でフラッシュ（バッファの書き出し）を行う
        // 何万回も呼ぶとフラッシュのオーバーヘッドが蓄積して TLE の原因になる
        // 【解決策】StringBuilder に文字列を溜めて、最後に System.out.print() で一括出力する

        // StringBuilder のインスタンスを作成する
        // StringBuilder は内部に char 配列を持ち、文字列の連結が O(1)（均償）で行える
        // String の + 演算子による連結は毎回新しい String を作るため O(n) でパフォーマンスが悪い
        // StringBuilder sb = new StringBuilder();

        // 0 から 9 まで 10 回ループして、各数値を改行付きで StringBuilder に追加する
        // for (int i = 0; i < 10; i++) {

        //     sb.append(i) で数値 i を文字列に変換して StringBuilder の末尾に追加する
        //     .append("\n") でさらに改行文字を追加する
        //     メソッドチェーン: append() は StringBuilder 自身を返すので連続して呼べる
        //     【比較】System.out.println(i) を10回呼ぶ場合、I/Oが10回発生する
        //     StringBuilder なら I/O は最後の1回だけ
        //     sb.append(i).append("\n");

        // }

        // 溜めた文字列を一括出力する
        // print() を使う（println() だと余分な改行が追加される）
        // sb.toString() が自動的に呼ばれ、StringBuilder の内容が String に変換されて出力される
        // 【本番テンプレート】PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        //   pw.println(sb); pw.flush(); を使うとさらに高速
        // System.out.print(sb);

        // ===== 練習問題 =====
        // 1. BufferedReader で N 個の整数を読み取り、合計を出力するプログラムを書こう
        //    入力例:
        //      5         ← N（整数の個数）
        //      3 1 4 1 5 ← N個の整数
        //    出力例:
        //      14        ← 合計
        //    ヒント: readInt() で N を読み、readIntArray() で配列を読み、for ループで合計する
        //
        // 2. MOD演算で 2^100 % (10^9+7) を求めてみよう（繰り返し二乗法）
        //    【繰り返し二乗法とは？】2^100 を100回掛けるのではなく、
        //    2^1 → 2^2 → 2^4 → 2^8 → ... と二乗していく方法
        //    計算量: O(log n)（100回ではなく約7回で計算できる）
        //    ヒント:
        //      long power(long base, long exp, long mod) {
        //          long result = 1;
        //          base %= mod;
        //          while (exp > 0) {
        //              if (exp % 2 == 1) result = result * base % mod;
        //              exp /= 2;
        //              base = base * base % mod;
        //          }
        //          return result;
        //      }
    }
}
