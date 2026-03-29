package lesson06_arrays;

/**
 * ===== レッスン06: 配列 =====
 *
 * 【目標】複数のデータをまとめて管理する方法を学ぶ
 *
 * 【配列とは？】
 *   同じ型のデータを連続して格納できる入れ物。
 *   変数が「1つの箱」なら、配列は「仕切り付きロッカー」のイメージ。
 *
 * 【作り方】
 *   方法1: int[] scores = new int[3];       // サイズ指定（中身は0で初期化）
 *   方法2: int[] scores = {85, 90, 78};     // 初期値を指定
 *
 * 【インデックス（添字）】
 *   配列の番号は 0 から始まる！（1からではない）
 *   scores[0] → 最初の要素
 *   scores[1] → 2番目の要素
 *   scores[scores.length - 1] → 最後の要素
 *
 * 【注意】
 *   - 一度作った配列のサイズは変更できない
 *   - 存在しないインデックスにアクセスするとエラー（ArrayIndexOutOfBoundsException）
 */
public class Arrays {

    public static void main(String[] args) {

        // ========== 配列の作成 ==========
        // System.out.println("--- 配列の基本 ---");

        // ---------- 方法1: サイズ指定 ----------
        // new int[3] で要素数3の配列を作る（初期値はすべて0）
        // そのあと各要素に値を入れる
        //
        // int[] scores = new int[3];
        // scores[0] = 85;   // インデックス0 = 最初の要素
        // scores[1] = 90;   // インデックス1 = 2番目の要素
        // scores[2] = 78;   // インデックス2 = 3番目の要素

        // ---------- 方法2: 初期値を指定 ----------
        // { } の中に値を並べると、サイズが自動で決まる
        //
        // String[] fruits = {"りんご", "バナナ", "みかん", "ぶどう"};

        // ========== 要素へのアクセス ==========
        // 配列名[インデックス] で読み書きする
        // .length で配列の要素数を取得できる
        //
        // System.out.println("最初のスコア: " + scores[0]);       // → 85
        // System.out.println("2番目のフルーツ: " + fruits[1]);    // → バナナ
        // System.out.println("配列の長さ: " + fruits.length);     // → 4

        // ========== for文でループ ==========
        // インデックス0から length-1 までループするのが定番パターン
        // i < scores.length（<=ではなく<）に注意！
        //
        // System.out.println("\n--- for文でループ ---");
        // for (int i = 0; i < scores.length; i++) {
        //     System.out.println("  scores[" + i + "] = " + scores[i]);
        // }

        // ========== 拡張for文（for-each） ==========
        // 配列の全要素を順番に取り出す簡単な書き方
        // for (型 変数名 : 配列) { ... }
        // ※ インデックスは使えないが、シンプルに書ける
        //
        // System.out.println("\n--- 拡張for文 ---");
        // for (String fruit : fruits) {
        //     System.out.println("  フルーツ: " + fruit);
        // }

        // ========== 配列の合計・平均 ==========
        // よくあるパターン: ループで合計を求め、要素数で割って平均を出す
        //
        // System.out.println("\n--- 合計と平均 ---");
        // int[] testScores = {70, 85, 92, 68, 95};
        // int sum = 0;
        // for (int score : testScores) {
        //     sum += score;        // 1つずつ足していく
        // }
        // double average = (double) sum / testScores.length;
        // // ↑ (double) = キャスト。intをdoubleに変換して小数の割り算にする
        // //   これがないと 410/5=82（整数の割り算）になってしまう
        // System.out.println("合計: " + sum);
        // System.out.println("平均: " + average);

        // ========== 最大値を探す ==========
        // パターン: 最初の要素を仮の最大値にして、ループで比較更新
        //
        // System.out.println("\n--- 最大値 ---");
        // int max = testScores[0];      // 最初の要素を仮の最大値
        // for (int score : testScores) {
        //     if (score > max) {        // 今の最大値より大きければ
        //         max = score;          // 最大値を更新
        //     }
        // }
        // System.out.println("最高点: " + max);

        // ========== 二次元配列 ==========
        // 配列の中に配列を入れる → 表（行と列）のようなデータを扱える
        // アクセス: matrix[行][列]
        //
        // System.out.println("\n--- 二次元配列 ---");
        // int[][] matrix = {
        //     {1, 2, 3},       // matrix[0] = 1行目
        //     {4, 5, 6},       // matrix[1] = 2行目
        //     {7, 8, 9}        // matrix[2] = 3行目
        // };
        // System.out.println("matrix[1][2] = " + matrix[1][2]);  // → 6（2行目の3列目）
        //
        // // 二重ループで全要素を表示
        // for (int i = 0; i < matrix.length; i++) {              // 行のループ
        //     for (int j = 0; j < matrix[i].length; j++) {       // 列のループ
        //         System.out.print(matrix[i][j] + " ");
        //     }
        //     System.out.println();    // 1行表示したら改行
        // }

        // ===== 練習問題 =====
        // 1. 5人の名前を配列に入れて、全員の名前を表示してみよう
        // 2. int配列の中から最小値を探すプログラムを書いてみよう
        // 3. 二次元配列で3x3の足し算の表を作ってみよう
    }
}
