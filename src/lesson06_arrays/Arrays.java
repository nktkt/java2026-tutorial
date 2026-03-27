package lesson06_arrays;

/**
 * ===== レッスン06: 配列 =====
 *
 * 配列は同じ型のデータを複数まとめて管理する仕組み。
 *
 * ポイント:
 * - インデックス（番号）は 0 から始まる
 * - 一度作ったら要素数は変更できない
 * - 拡張for文（for-each）で簡単にループできる
 */
public class Arrays {

    public static void main(String[] args) {

        // ========== 配列の作成 ==========
        System.out.println("--- 配列の基本 ---");

        // 方法1: サイズを指定して作成
        int[] scores = new int[3];
        scores[0] = 85;   // インデックス0（最初の要素）
        scores[1] = 90;
        scores[2] = 78;

        // 方法2: 初期値を指定して作成
        String[] fruits = {"りんご", "バナナ", "みかん", "ぶどう"};

        // ========== 要素へのアクセス ==========
        System.out.println("最初のスコア: " + scores[0]);       // → 85
        System.out.println("2番目のフルーツ: " + fruits[1]);    // → バナナ
        System.out.println("配列の長さ: " + fruits.length);     // → 4

        // ========== for文でループ ==========
        System.out.println("\n--- for文でループ ---");
        for (int i = 0; i < scores.length; i++) {
            System.out.println("  scores[" + i + "] = " + scores[i]);
        }

        // ========== 拡張for文（for-each） ==========
        System.out.println("\n--- 拡張for文 ---");
        for (String fruit : fruits) {
            System.out.println("  フルーツ: " + fruit);
        }

        // ========== 配列の合計・平均 ==========
        System.out.println("\n--- 合計と平均 ---");
        int[] testScores = {70, 85, 92, 68, 95};
        int sum = 0;
        for (int score : testScores) {
            sum += score;
        }
        double average = (double) sum / testScores.length;  // (double)でキャスト
        System.out.println("合計: " + sum);
        System.out.println("平均: " + average);

        // ========== 最大値を探す ==========
        System.out.println("\n--- 最大値 ---");
        int max = testScores[0];  // 最初の要素を仮の最大値にする
        for (int score : testScores) {
            if (score > max) {
                max = score;
            }
        }
        System.out.println("最高点: " + max);

        // ========== 二次元配列 ==========
        System.out.println("\n--- 二次元配列 ---");
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        // 行と列でアクセス
        System.out.println("matrix[1][2] = " + matrix[1][2]);  // → 6（2行目の3列目）

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        // ===== 練習問題 =====
        // 1. 5人の名前を配列に入れて、全員の名前を表示してみよう
        // 2. int配列の中から最小値を探すプログラムを書いてみよう
        // 3. 二次元配列で3x3の足し算の表を作ってみよう
    }
}
