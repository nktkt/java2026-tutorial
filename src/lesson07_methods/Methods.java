package lesson07_methods;

/**
 * ===== レッスン07: メソッド =====
 *
 * メソッドは処理をまとめて名前をつけたもの。
 * 同じ処理を何度も書かなくて済む。
 *
 * 構造:
 *   戻り値の型 メソッド名(引数) {
 *       処理;
 *       return 戻り値;
 *   }
 *
 * - void: 戻り値がないメソッド
 * - return: 結果を呼び出し元に返す
 */
public class Methods {

    public static void main(String[] args) {

        // ========== メソッドの呼び出し ==========
        System.out.println("--- メソッド呼び出し ---");
        greet();  // 引数なし、戻り値なし

        greetPerson("Naoki");  // 引数あり、戻り値なし
        greetPerson("Taro");

        // 戻り値ありのメソッド
        int result = add(10, 20);
        System.out.println("10 + 20 = " + result);

        // 直接使うこともできる
        System.out.println("3 + 7 = " + add(3, 7));

        // ========== 実用的な例 ==========
        System.out.println("\n--- 実用例 ---");

        System.out.println("5の2乗 = " + power(5, 2));
        System.out.println("2の10乗 = " + power(2, 10));

        System.out.println("最大値: " + max(15, 28));

        System.out.println("偶数? 4 → " + isEven(4));
        System.out.println("偶数? 7 → " + isEven(7));

        // ========== 配列を受け取るメソッド ==========
        System.out.println("\n--- 配列を使うメソッド ---");
        int[] scores = {80, 95, 70, 88, 92};
        System.out.println("平均点: " + average(scores));

        // ========== メソッドのオーバーロード ==========
        System.out.println("\n--- オーバーロード ---");
        // 同じ名前でも引数が違えば別のメソッドとして定義できる
        System.out.println(multiply(3, 4));       // int版
        System.out.println(multiply(2.5, 3.0));   // double版

        // ===== 練習問題 =====
        // 1. 文字列を受け取り、その文字数を返すメソッドを作ってみよう
        // 2. int配列を受け取り、最大値を返すメソッドを作ってみよう
        // 3. 2つの整数を受け取り、小さい方を返す min メソッドを作ってみよう
    }

    // --- 引数なし・戻り値なし ---
    static void greet() {
        System.out.println("こんにちは！");
    }

    // --- 引数あり・戻り値なし ---
    static void greetPerson(String name) {
        System.out.println("こんにちは、" + name + "さん！");
    }

    // --- 引数あり・戻り値あり ---
    static int add(int a, int b) {
        return a + b;
    }

    // --- べき乗を計算 ---
    static int power(int base, int exponent) {
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    // --- 2つの数の大きい方を返す ---
    static int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    // --- 偶数かどうか判定 ---
    static boolean isEven(int number) {
        return number % 2 == 0;
    }

    // --- 配列の平均を計算 ---
    static double average(int[] numbers) {
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        return (double) sum / numbers.length;
    }

    // --- オーバーロード: int版 ---
    static int multiply(int a, int b) {
        return a * b;
    }

    // --- オーバーロード: double版 ---
    static double multiply(double a, double b) {
        return a * b;
    }
}
