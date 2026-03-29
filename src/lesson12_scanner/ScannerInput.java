package lesson12_scanner;

import java.util.Scanner;
// ↑ Scanner クラスを使うために import する
//   java.util パッケージに含まれる

/**
 * ===== レッスン12: Scannerと入力処理 =====
 *
 * 【目標】キーボードから様々な型のデータを読み取る方法を学ぶ
 *
 * 【Scannerとは？】
 *   キーボード（標準入力）やファイルからデータを読み取るクラス。
 *   new Scanner(System.in) でキーボード入力用のScannerを作る。
 *
 * 【主要メソッド】
 *   nextInt()     → 整数を1つ読む（空白/改行で区切る）
 *   nextDouble()  → 小数を1つ読む
 *   next()        → 空白までの1単語を読む
 *   nextLine()    → 改行までの1行をまるごと読む
 *   hasNextInt()  → 次の入力が整数かどうか確認（true/false）
 *   hasNext()     → まだ入力があるかどうか確認
 *
 * 【★重要: nextInt()のあとのnextLine()問題★】
 *   nextInt() は「数字」だけ読み、改行文字（Enter）を読み残す。
 *   直後に nextLine() を呼ぶと、残った改行だけを読んで空文字を返してしまう。
 *
 *   対策: nextInt() の直後に sc.nextLine() を1回呼んで改行を消費する。
 *
 *   例:
 *     int n = sc.nextInt();    // 数字を読む（改行が残る）
 *     sc.nextLine();           // 残った改行を消費（空読み）
 *     String s = sc.nextLine(); // これで正しく1行読める
 *
 * 【競技プログラミングでの入力パターン】
 *   よくある形式:
 *     1行目: N（データの個数）
 *     2行目: a1 a2 a3 ... aN（N個の整数が空白区切り）
 *
 *   読み方:
 *     int n = sc.nextInt();
 *     int[] arr = new int[n];
 *     for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
 */
public class ScannerInput {

    public static void main(String[] args) {
        // Scanner のインスタンスを作成
        // System.in = 標準入力（キーボード）
        Scanner sc = new Scanner(System.in);

        // ---------- 整数の入力 ----------
        // // nextInt(): ユーザーが入力した整数を読み取る
        // // 入力待ちになるので、ターミナルに数字を入力して Enter を押す
        // System.out.print("年齢を入力: ");
        // int age = sc.nextInt();
        // System.out.println("あなたは" + age + "歳です。");

        // ---------- 文字列の入力 ----------
        // // 【重要】nextInt() の後に nextLine() を使うとき、
        // // まず空の nextLine() で残った改行を消費する必要がある
        // sc.nextLine();  // ← nextInt() が読み残した改行を消費（これがないと次が空になる）
        //
        // System.out.print("名前を入力: ");
        // String name = sc.nextLine();
        // // nextLine(): 改行(\n)までの文字列をすべて読む（空白を含む）
        // // next() だと「Hello World」の「Hello」だけ読んでしまう
        // System.out.println("こんにちは、" + name + "さん！");

        // ---------- 複数の値を1行で入力 ----------
        // // 「10 20」のように空白区切りで入力すると、nextInt()が順番に読む
        // System.out.print("2つの整数を空白区切りで入力: ");
        // int a = sc.nextInt();   // 最初の数字を読む
        // int b = sc.nextInt();   // 2番目の数字を読む
        // System.out.println("合計: " + (a + b));

        // ---------- 競技プログラミング形式の入力 ----------
        // // 入力例:
        // //   5
        // //   10 20 30 40 50
        // //
        // // 1行目の数値 N を読み、2行目で N 個の整数を配列に格納する
        //
        // System.out.println("個数を入力し、次の行に数値を空白区切りで入力:");
        // int n = sc.nextInt();         // 個数を読む
        // int[] nums = new int[n];      // n要素の配列を作成
        // for (int i = 0; i < n; i++) {
        //     nums[i] = sc.nextInt();   // 空白区切りで1つずつ読む
        // }
        //
        // // 読み取った配列を表示
        // System.out.print("入力された配列: ");
        // for (int num : nums) {
        //     System.out.print(num + " ");
        // }
        // System.out.println();

        // ---------- 入力の検証（型チェック） ----------
        // // hasNextInt(): 次の入力が整数かどうか事前にチェックできる
        // // 整数でない入力（"abc"等）を nextInt() で読むと InputMismatchException
        // // hasNextInt() でチェックすれば安全
        //
        // System.out.print("整数を入力してください: ");
        // if (sc.hasNextInt()) {
        //     int value = sc.nextInt();
        //     System.out.println("入力値: " + value);
        // } else {
        //     System.out.println("整数ではありません！");
        // }

        // ===== 練習問題 =====
        // 1. 名前と年齢を入力してもらい、「○○さんは○歳です」と表示してみよう
        // 2. N個の整数を入力してもらい、合計と平均を求めてみよう
        // 3. 文字列を入力してもらい、文字数を表示してみよう

        // Scanner を閉じる（リソース解放）
        // ※ System.in を閉じると再度開けないので、プログラム終了時のみ
        sc.close();
    }
}
