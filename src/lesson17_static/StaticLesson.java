package lesson17_static;

/**
 * ===== レッスン17: static キーワード =====
 *
 * 【目標】staticの意味と、インスタンスメンバーとの違いを理解する
 *
 * 【staticとは？】
 *   「クラスに属する」という意味。オブジェクトを作らなくても使える。
 *   メモリ上にクラスがロードされた時点で存在し、プログラム終了まで残る。
 *
 * 【インスタンス vs static】
 *   インスタンスメンバー: オブジェクトごとに別々の値を持つ
 *     → Dog dog1 = new Dog(); dog1.name → dog1固有の名前
 *
 *   staticメンバー: クラス全体で1つだけ。全オブジェクトで共有される。
 *     → Dog.count → Dogクラス全体で共有する犬の数
 *
 * 【staticフィールド】
 *   クラス名.フィールド名 でアクセス（オブジェクト不要）
 *   用途: カウンター、設定値、定数 など
 *
 * 【staticメソッド】
 *   クラス名.メソッド名() で呼び出す（オブジェクト不要）
 *   注意: staticメソッドからインスタンスメンバーにアクセスできない
 *   例: Math.max(), Integer.parseInt(), Arrays.sort()
 *
 * 【static定数】
 *   static final で「クラスに属する変更不可の値」を定義
 *   例: Math.PI, Integer.MAX_VALUE
 */
public class StaticLesson {

    public static void main(String[] args) {

        // ========== staticフィールド（全オブジェクトで共有） ==========
        // static フィールドは、クラスのすべてのインスタンスが同じ値を共有する。
        // 1つのインスタンスが変更すると、他のインスタンスからも変更後の値が見える。
        // インスタンスを1つも作らなくても、クラス名で直接アクセスできる。

        // System.out.println("--- staticフィールド ---");
        // ↑ セクションタイトルを表示する。

        // System.out.println("初期カウント: " + Counter.count);  // → 0
        // ↑ Counter.count でstaticフィールドにアクセスしている。
        //   「クラス名.フィールド名」でアクセスするのが static の特徴。
        //   オブジェクトを一つも作っていないのに、値を取得できる。
        //   count は static int で初期値 0 に設定されているので、0 が表示される。

        //
        // Counter c1 = new Counter("A");
        // ↑ Counter クラスのインスタンスを作成。コンストラクタに名前 "A" を渡す。
        //   コンストラクタ内で count++ が実行されるので、count は 0 → 1 になる。

        // Counter c2 = new Counter("B");
        // ↑ 2つ目のインスタンスを作成。名前は "B"。
        //   コンストラクタ内で count++ が実行されるので、count は 1 → 2 になる。
        //   c1 の count も c2 の count も同じ値（2）を指している。なぜなら static だから。

        // Counter c3 = new Counter("C");
        // ↑ 3つ目のインスタンスを作成。名前は "C"。
        //   count は 2 → 3 になる。3つのオブジェクトすべてで count は 3。

        //
        // System.out.println("作成数: " + Counter.count);  // → 3（全体で共有）
        // ↑ Counter.count で static フィールドの値を取得。結果は 3。
        //   c1, c2, c3 を作るたびに count++ されたので、合計3。
        //   Counter.count はクラス全体で1つしか存在しないため、全インスタンスの作成がカウントされる。
        //   【注意】c1.count でもアクセスできるが、static フィールドはクラス名でアクセスするのが正しい書き方。
        //   c1.count と書くと「インスタンスに属するフィールド」と誤解されやすいため推奨されない。

        // System.out.println("c1の名前: " + c1.name);      // → A（オブジェクト固有）
        // ↑ c1.name はインスタンスフィールド。各オブジェクト固有の値を持つ。
        //   c1.name は "A"、c2.name は "B"、c3.name は "C" と、それぞれ異なる。
        //   static フィールド（count: 共有）とインスタンスフィールド（name: 固有）の違いがここで分かる。

        // ========== staticメソッド ==========
        // static メソッドは、オブジェクトを作らずにクラス名で直接呼び出せるメソッド。
        // 「ユーティリティメソッド」（計算や変換など、オブジェクトの状態に依存しない処理）に最適。
        // Java標準ライブラリでは Math.max(), Integer.parseInt(), Arrays.sort() などが有名。

        // System.out.println("\n--- staticメソッド ---");
        // ↑ セクションタイトルを表示する。\n で空行を入れる。

        // // オブジェクトを作らずにクラス名で直接呼べる
        // ↑ static メソッドの最大の特徴。new でインスタンスを作る必要がない。
        //   Math.max(3, 5) のように、クラス名.メソッド名() で呼ぶ。

        // System.out.println("足し算: " + MathUtil.add(3, 5));
        // ↑ MathUtil クラスの static メソッド add() を呼び出している。
        //   MathUtil のオブジェクトを作らずに、クラス名で直接呼べる。
        //   3 + 5 = 8 が返される。出力: "足し算: 8"

        // System.out.println("最大値: " + MathUtil.max(10, 20));
        // ↑ static メソッド max() を呼び出す。2つの引数のうち大きい方を返す。
        //   10 と 20 を比較し、20 が返される。出力: "最大値: 20"
        //   Java標準の Math.max() と同じ仕組みのメソッドを自作している。

        // System.out.println("円の面積: " + MathUtil.circleArea(5));
        // ↑ static メソッド circleArea() を呼び出す。半径5の円の面積を計算する。
        //   計算式: PI * radius * radius = 3.14... * 5 * 5 = 78.54...
        //   static final 定数 PI を使って計算する。出力: "円の面積: 78.53..."

        // ========== staticメソッドの制限 ==========
        // static メソッドからは、同じクラスのインスタンスメンバー（非staticフィールド・メソッド）に
        // アクセスできない。これは Java の重要なルール。

        // // staticメソッドから this やインスタンスメンバーは使えない
        // ↑ static メソッドは特定のオブジェクトに紐づいていないため、
        //   this（現在のオブジェクトへの参照）が存在しない。

        // // なぜ？ → staticはオブジェクトなしで呼ばれるので「this（自分）」がない
        // ↑ 例えば MathUtil.add(3, 5) を呼ぶとき、MathUtil のオブジェクトは存在しない。
        //   オブジェクトがないので、this が指す先がない。
        //   よって、インスタンスフィールドやインスタンスメソッドにはアクセスできない。
        //   【覚え方】static = オブジェクト不要 = this がない = インスタンスメンバーは使えない
        //   【逆に】インスタンスメソッドから static メンバーにはアクセスできる（static は常に存在するため）。

        // ===== 練習問題 =====
        // 1. staticフィールドでIDを自動採番するクラスを作ってみよう
        //    new するたびに id が 1, 2, 3... と自動で振られる
        //    【ヒント】static int nextId = 1; を用意し、コンストラクタ内で
        //    this.id = nextId++; とすれば自動的にIDが増えていく。
        //
        // 2. 温度変換のstaticメソッドを作ってみよう
        //    celsius→fahrenheit, fahrenheit→celsius
        //    【ヒント】F = C * 9/5 + 32, C = (F - 32) * 5/9
    }
}

// === Counter クラス: static フィールドの動作を示す ===
// インスタンスフィールド（name）と static フィールド（count）の違いを学ぶためのクラス。

// class Counter {
// ↑ Counter クラスの定義。パッケージプライベート（同じパッケージからのみアクセス可能）。

//     String name;                    // インスタンスフィールド（オブジェクト固有）
//     ↑ インスタンスフィールド。new するたびに各オブジェクトに個別のメモリが確保される。
//       c1.name = "A", c2.name = "B" のように、オブジェクトごとに異なる値を持つ。
//       static を付けていないので、オブジェクトに属する。

//     static int count = 0;           // staticフィールド（クラス全体で共有）
//     ↑ static フィールド。クラス全体で1つだけ存在し、全オブジェクトで値を共有する。
//       メモリ上には Counter クラスがロードされた時点で1か所だけ確保される。
//       初期値 0 を設定。何個インスタンスを作っても、count は1つしかない。
//       【イメージ】教室の黒板（static）は全員で共有。各自のノート（インスタンス）は個別。

//
//     Counter(String name) {
//     ↑ コンストラクタ。引数として名前を受け取る。
//       new Counter("A") のように呼ぶと、このコンストラクタが実行される。

//         this.name = name;
//         ↑ 引数の name をインスタンスフィールド this.name に代入する。
//           this.name はこのオブジェクト固有の名前。

//         count++;                    // new するたびにカウントアップ
//         ↑ static フィールド count を 1 増やす。count++ は count = count + 1 と同じ。
//           static フィールドなので、どのオブジェクトのコンストラクタで実行しても、
//           同じ count が増える。結果として「全体で何個のインスタンスが作られたか」を追跡できる。
//           【よくある使い方】オブジェクトの生成回数をカウントする、自動採番する など。

//     }
// }

// === MathUtil クラス: static メソッドの動作を示す ===
// 数学的な計算を行うユーティリティクラス。
// すべてのメソッドが static なので、オブジェクトを作らずにクラス名で直接呼べる。
// Java標準の Math クラスも同じ構造になっている。

// class MathUtil {
// ↑ MathUtil クラスの定義。ユーティリティクラス（便利ツールをまとめたクラス）。

//     // static final = 定数（変更不可）
//     static final double PI = 3.14159265358979;
//     ↑ static final で定数を定義する。
//       static: クラスに属する（オブジェクト不要でアクセスできる）。
//       final: 一度代入したら変更できない（再代入しようとするとコンパイルエラー）。
//       static + final の組み合わせで「クラスに属する変更不可の値」= 定数になる。
//       定数の命名規則: 全て大文字、単語はアンダースコア(_)で区切る（例: MAX_VALUE）。
//       Java標準の Math.PI と同じ値。実務では Math.PI を使うことが多い。

//
//     // staticメソッド: オブジェクト不要で呼べる
//     static int add(int a, int b) {
//     ↑ static メソッド。MathUtil.add(3, 5) のようにクラス名で直接呼べる。
//       2つの int を受け取り、足し算の結果を int で返す。
//       オブジェクトの状態（インスタンスフィールド）に依存しない純粋な計算なので、
//       static にするのが適切。

//         return a + b;
//         ↑ 2つの引数を足して返す。シンプルな計算メソッド。

//     }
//
//     static int max(int a, int b) {
//     ↑ 2つの int のうち大きい方を返す static メソッド。
//       Java標準の Math.max(a, b) と同じ機能を持つ。

//         return (a > b) ? a : b;
//         ↑ 三項演算子（条件演算子）を使った条件分岐。
//           「(条件) ? 真の場合の値 : 偽の場合の値」の構文。
//           a > b が true なら a を返し、false なら b を返す。
//           if-else で書くと: if (a > b) return a; else return b; と同じ。

//     }
//
//     static double circleArea(double radius) {
//     ↑ 円の面積を計算する static メソッド。引数は半径（radius）で double 型。
//       戻り値も double 型（面積は小数になるため）。

//         return PI * radius * radius;
//         ↑ 円の面積の公式: π × r²（パイ × 半径の2乗）。
//           PI は上で定義した static final 定数。
//           radius * radius は radius の2乗。Math.pow(radius, 2) と同じ結果。
//           同じクラスの static メンバーには、クラス名なしで直接アクセスできる。

//     }
// }
