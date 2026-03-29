package lesson11_string;
// ↑ パッケージ宣言: このファイルが lesson11_string フォルダに属することを示す

/**
 * ===== レッスン11: Stringのメソッド =====
 *
 * 【目標】文字列を自在に操作するメソッドを学ぶ
 *
 * 【Stringの特徴】
 *   - String は不変（immutable）: 一度作ると中身を変更できない
 *     → "hello".toUpperCase() は "hello" を変えるのではなく、新しい "HELLO" を作って返す
 *   - メソッドを呼んでも元の文字列は変わらず、新しい文字列が返る
 *   - 文字列の比較は == ではなく .equals() を使う
 *     → == は「同じオブジェクトか」を比較（メモリアドレスの比較）
 *     → .equals() は「同じ内容か」を比較（中身の文字列の比較）
 *
 * 【主要メソッド一覧】
 *   length()              → 文字数を返す                   "Hello".length() → 5
 *   charAt(i)             → i番目の文字を返す              "Hello".charAt(1) → 'e'
 *   substring(begin, end) → begin〜end-1の部分文字列       "Hello".substring(1,3) → "el"
 *   indexOf(str)          → strが最初に現れる位置(なければ-1) "Hello".indexOf("ll") → 2
 *   contains(str)         → strを含むか(true/false)        "Hello".contains("ell") → true
 *   equals(str)           → 文字列が等しいか(大文字小文字区別あり)
 *   equalsIgnoreCase(str) → 文字列が等しいか(大文字小文字区別なし)
 *   toUpperCase()         → 全て大文字に変換               "hello" → "HELLO"
 *   toLowerCase()         → 全て小文字に変換               "HELLO" → "hello"
 *   trim()                → 前後の空白を除去               "  hi  " → "hi"
 *   replace(old, new)     → 文字列を置換                   "abc".replace("b","X") → "aXc"
 *   split(区切り)          → 区切り文字で分割して配列にする   "a,b,c".split(",") → ["a","b","c"]
 *   startsWith(str)       → strで始まるか                  "Hello".startsWith("He") → true
 *   endsWith(str)         → strで終わるか                  "Hello".endsWith("lo") → true
 *   isEmpty()             → 空文字列か（長さ0）             "".isEmpty() → true
 *   toCharArray()         → char配列に変換                 "abc" → {'a','b','c'}
 *
 * 【StringBuilder とは？】
 *   文字列を頻繁に結合するときに使うクラス。
 *   String は不変なので + で結合するたびに新しいオブジェクトが作られ遅い。
 *   StringBuilder は内部バッファを持ち、追記が高速。
 *   ループ内で文字列を結合する場合は必ず StringBuilder を使う。
 *
 * 【数値⇔文字列の変換】
 *   int → String:  String.valueOf(123) → "123"
 *   String → int:  Integer.parseInt("123") → 123
 *   String → double: Double.parseDouble("3.14") → 3.14
 *   ※ 数値でない文字列を parseInt すると NumberFormatException が発生
 */
public class StringMethods {

    public static void main(String[] args) {

        // ---------- 基本メソッド ----------
        // String s = "Hello, Java World!";
        //
        // // length(): 文字列の長さ（文字数）を返す
        // System.out.println("文字数: " + s.length());             // → 18
        //
        // // charAt(index): 指定位置の文字を返す（0始まり）
        // // "Hello, Java World!" の5番目（0から数えて5）はカンマ
        // System.out.println("5番目の文字: " + s.charAt(5));        // → ,
        //
        // // substring(begin, end): begin から end-1 までの部分文字列を返す
        // // s.substring(7, 11) → インデックス7〜10 → "Java"
        // System.out.println("部分文字列: " + s.substring(7, 11)); // → Java
        //
        // // indexOf(str): str が最初に出現する位置を返す（見つからなければ-1）
        // System.out.println("Javaの位置: " + s.indexOf("Java"));  // → 7
        //
        // // contains(str): str を含んでいるか（true/false）
        // // 内部的には indexOf(str) >= 0 と同じ
        // System.out.println("含むか: " + s.contains("Java"));     // → true

        // ---------- 比較 ----------
        // String a = "hello";
        // String b = "Hello";
        // System.out.println("\n--- 比較 ---");
        //
        // // equals(): 内容が完全に一致するか（大文字小文字を区別）
        // System.out.println("equals: " + a.equals(b));                 // → false
        //
        // // equalsIgnoreCase(): 大文字小文字を無視して比較
        // System.out.println("equalsIgnoreCase: " + a.equalsIgnoreCase(b)); // → true
        //
        // // 【重要】== は参照の比較（同じオブジェクトか）
        // //         equals は値の比較（同じ内容か）
        // // String の比較は必ず equals() を使うこと！

        // ---------- 変換 ----------
        // System.out.println("\n--- 変換 ---");
        //
        // // toUpperCase(): 全文字を大文字に変換した新しい文字列を返す
        // // 元の a は変わらない（String は不変）
        // System.out.println("大文字: " + a.toUpperCase());        // → HELLO
        //
        // // toLowerCase(): 全文字を小文字に変換
        // System.out.println("小文字: " + b.toLowerCase());        // → hello
        //
        // // replace(old, new): 一致する部分をすべて置換
        // System.out.println("置換: " + s.replace("Java", "Python"));
        // // → "Hello, Python World!"

        // ---------- トリム ----------
        // String padded = "   hello   ";
        // System.out.println("\n--- trim ---");
        // System.out.println("[" + padded + "]");            // → [   hello   ]
        //
        // // trim(): 先頭と末尾の空白文字（スペース、タブ、改行）を除去
        // // 文字列の途中の空白は除去しない
        // System.out.println("[" + padded.trim() + "]");     // → [hello]

        // ---------- split（分割） ----------
        // // split(区切り文字): 文字列を区切り文字で分割し、String配列で返す
        // // CSV（カンマ区切り）データの解析などで頻繁に使う
        // String csv = "apple,banana,cherry,grape";
        // String[] fruits = csv.split(",");
        // // → fruits = {"apple", "banana", "cherry", "grape"}
        //
        // System.out.println("\n--- split ---");
        // for (String fruit : fruits) {
        //     System.out.println("  " + fruit);
        // }
        //
        // // 空白で分割する例
        // String sentence = "I love Java";
        // String[] words = sentence.split(" ");
        // // → words = {"I", "love", "Java"}

        // ---------- StringBuilder ----------
        // // StringBuilder: 可変の文字列。頻繁な結合操作に使う。
        // StringBuilder sb = new StringBuilder();
        //
        // // append(): 末尾に文字列を追加（String の + と同じだが高速）
        // sb.append("Hello");
        // sb.append(" ");
        // sb.append("World");
        //
        // // insert(位置, 文字列): 指定位置に文字列を挿入
        // sb.insert(5, ",");       // "Hello" の後ろにカンマを挿入
        //
        // // reverse(): 文字列全体を反転
        // sb.reverse();
        //
        // // toString(): StringBuilder → String に変換
        // String result = sb.toString();
        //
        // System.out.println("\n--- StringBuilder ---");
        // System.out.println(result);   // → "dlroW ,olleH"
        //
        // // 【パフォーマンス比較】
        // // NG（遅い）: for ループ内で String を + で結合 → 毎回新しい String オブジェクト生成
        // // OK（速い）: StringBuilder の append() → 内部バッファに追記するだけ

        // ---------- 数値⇔文字列の変換 ----------
        // int num = 123;
        //
        // // int → String: String.valueOf() で数値を文字列に変換
        // String numStr = String.valueOf(num);    // "123"
        //
        // // String → int: Integer.parseInt() で文字列を整数に変換
        // // 数値でない文字列（"abc"等）を渡すと NumberFormatException
        // int parsed = Integer.parseInt("456");   // 456
        //
        // // String → double: Double.parseDouble() で文字列を小数に変換
        // double d = Double.parseDouble("3.14");  // 3.14
        //
        // System.out.println("\n--- 数値⇔文字列変換 ---");
        // System.out.println("int→String: " + numStr);
        // System.out.println("String→int: " + parsed);
        // System.out.println("String→double: " + d);

        // ===== 練習問題 =====
        // 1. 文字列の中の空白の数を数えてみよう
        //    ヒント: charAt() で1文字ずつ見て ' ' と比較
        // 2. "Hello World" の単語を逆順にして "World Hello" にしてみよう
        //    ヒント: split(" ") で分割 → 逆順に結合
        // 3. メールアドレスから@の前（ユーザー名）を取り出してみよう
        //    ヒント: indexOf("@") + substring()
    }
}
