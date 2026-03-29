package problems_beginner;

/**
 * ===== 初級02: FizzBuzz =====
 * 出典: 定番プログラミング問題（面接でもよく出る）
 *
 * 【問題】
 *   1 から 30 までの数を順に表示せよ。ただし:
 *   - 3の倍数のときは数の代わりに "Fizz" を表示
 *   - 5の倍数のときは数の代わりに "Buzz" を表示
 *   - 3と5の両方の倍数のときは "FizzBuzz" を表示
 *
 * 【出力例】
 *   1
 *   2
 *   Fizz
 *   4
 *   Buzz
 *   Fizz
 *   7
 *   8
 *   Fizz
 *   Buzz
 *   11
 *   Fizz
 *   13
 *   14
 *   FizzBuzz
 *   ...
 *
 * 【ヒント】
 *   - 倍数の判定: n % 3 == 0 なら3の倍数
 *   - 判定の順番が重要！「3かつ5の倍数」を最初に判定すること
 *     → 先に「3の倍数」を判定すると、15で "Fizz" になってしまう
 *
 * 【学べること】
 *   - if / else if / else の条件の順番の重要性
 *   - % 演算子で倍数判定
 */
public class P02_FizzBuzz {

    public static void main(String[] args) {

        // ここにコードを書こう
        // for文で1から30まで、条件に応じて表示を変える


        // ========== 解答例 ==========
        // for (int i = 1; i <= 30; i++) {
        //     if (i % 15 == 0) {           // 3と5の両方の倍数 = 15の倍数（先に判定！）
        //         System.out.println("FizzBuzz");
        //     } else if (i % 3 == 0) {     // 3の倍数
        //         System.out.println("Fizz");
        //     } else if (i % 5 == 0) {     // 5の倍数
        //         System.out.println("Buzz");
        //     } else {                     // それ以外
        //         System.out.println(i);
        //     }
        // }
    }
}
