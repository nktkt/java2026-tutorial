package lesson07_methods;

/**
 * ===== レッスン07: メソッド =====
 *
 * 【目標】処理をメソッドにまとめて再利用する方法を学ぶ
 *
 * 【メソッドとは？】
 *   処理に名前をつけて、何度でも呼び出せるようにしたもの。
 *   「関数」とほぼ同じ意味（Javaではメソッドと呼ぶ）。
 *
 * 【メソッドの構造】
 *   アクセス修飾子 static 戻り値の型 メソッド名(引数の型 引数名) {
 *       処理;
 *       return 戻り値;  ← 戻り値の型がvoidなら不要
 *   }
 *
 * 【用語】
 *   引数（ひきすう）: メソッドに渡すデータ。複数可。なくてもOK。
 *   戻り値（もどりち）: メソッドが返す結果。voidなら「返さない」。
 *   static: 今の段階では「おまじない」でOK（レッスン08で理解する）
 *
 * 【オーバーロード】
 *   同じ名前のメソッドでも、引数の型や数が違えば別メソッドとして定義できる。
 *   例: add(int, int) と add(double, double)
 *
 * 【メリット】
 *   - 同じ処理を何度も書かなくて済む
 *   - コードが読みやすくなる（処理に名前がつく）
 *   - 修正が1か所で済む
 */
public class Methods {

    public static void main(String[] args) {

        // ========== メソッドの呼び出し ==========
        // メソッド名(引数) で呼び出す
        //
        // System.out.println("--- メソッド呼び出し ---");
        // greet();                  // 引数なし、戻り値なし → ただ実行するだけ
        //
        // greetPerson("Naoki");     // 引数あり → "Naoki"がnameに入る
        // greetPerson("Taro");      // 同じメソッドを違う引数で再利用

        // ---------- 戻り値ありのメソッド ----------
        // return で返された値を変数に受け取れる
        //
        // int result = add(10, 20); // add の戻り値(30)が result に入る
        // System.out.println("10 + 20 = " + result);
        //
        // // 変数に入れず、直接使うこともできる
        // System.out.println("3 + 7 = " + add(3, 7));

        // ========== 実用的な例 ==========
        // System.out.println("\n--- 実用例 ---");
        //
        // System.out.println("5の2乗 = " + power(5, 2));    // → 25
        // System.out.println("2の10乗 = " + power(2, 10));  // → 1024
        //
        // System.out.println("最大値: " + max(15, 28));      // → 28
        //
        // System.out.println("偶数? 4 → " + isEven(4));     // → true
        // System.out.println("偶数? 7 → " + isEven(7));     // → false

        // ========== 配列を受け取るメソッド ==========
        // 配列も引数として渡せる
        //
        // System.out.println("\n--- 配列を使うメソッド ---");
        // int[] scores = {80, 95, 70, 88, 92};
        // System.out.println("平均点: " + average(scores));

        // ========== メソッドのオーバーロード ==========
        // 同じ名前 multiply でも引数の型が違う → 別のメソッドとして共存できる
        // Javaが引数の型を見て自動的に正しい方を呼んでくれる
        //
        // System.out.println("\n--- オーバーロード ---");
        // System.out.println(multiply(3, 4));       // int版が呼ばれる → 12
        // System.out.println(multiply(2.5, 3.0));   // double版が呼ばれる → 7.5

        // ===== 練習問題 =====
        // 1. 文字列を受け取り、その文字数を返すメソッドを作ってみよう
        //    ヒント: 文字列.length() で文字数が取れる
        // 2. int配列を受け取り、最大値を返すメソッドを作ってみよう
        // 3. 2つの整数を受け取り、小さい方を返す min メソッドを作ってみよう
    }

    // ========== ここにメソッドを定義する ==========
    // メソッドは main の外、クラスの中に書く

    // --- 引数なし・戻り値なし(void) ---
    // 呼び出すと「こんにちは！」と表示するだけ
    //
    // static void greet() {
    //     System.out.println("こんにちは！");
    // }

    // --- 引数あり・戻り値なし ---
    // String name を受け取って挨拶を表示する
    //
    // static void greetPerson(String name) {
    //     System.out.println("こんにちは、" + name + "さん！");
    // }

    // --- 引数あり・戻り値あり ---
    // int を2つ受け取り、合計を int で返す
    //
    // static int add(int a, int b) {
    //     return a + b;   // ← この値が呼び出し元に返る
    // }

    // --- べき乗を計算 ---
    // base の exponent乗 を返す（例: power(2,3) → 8）
    //
    // static int power(int base, int exponent) {
    //     int result = 1;
    //     for (int i = 0; i < exponent; i++) {
    //         result *= base;   // resultにbaseを繰り返し掛ける
    //     }
    //     return result;
    // }

    // --- 2つの数の大きい方を返す ---
    //
    // static int max(int a, int b) {
    //     if (a > b) {
    //         return a;
    //     } else {
    //         return b;
    //     }
    // }

    // --- 偶数かどうか判定 ---
    // boolean を返す（true = 偶数、false = 奇数）
    //
    // static boolean isEven(int number) {
    //     return number % 2 == 0;   // 2で割った余りが0ならtrue
    // }

    // --- 配列の平均を計算 ---
    // int配列を受け取り、平均値をdoubleで返す
    //
    // static double average(int[] numbers) {
    //     int sum = 0;
    //     for (int n : numbers) {
    //         sum += n;
    //     }
    //     return (double) sum / numbers.length;
    // }

    // --- オーバーロード: int版 ---
    // static int multiply(int a, int b) {
    //     return a * b;
    // }

    // --- オーバーロード: double版 ---
    // 同じ名前だが引数の型が違うので共存できる
    // static double multiply(double a, double b) {
    //     return a * b;
    // }
}
