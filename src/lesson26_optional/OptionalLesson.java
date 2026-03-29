package lesson26_optional;

import java.util.Optional;
// Optional クラスをインポートする。
// Optional は java.util パッケージに属する。Java 8 で追加された。
// 「値が存在するかもしれないし、しないかもしれない」ことを型レベルで表現するためのクラス。
// null を直接扱う代わりに Optional を使うことで、NullPointerException を防ぐ。

/**
 * ===== レッスン26: Optional =====
 *
 * 【目標】NullPointerException を防ぐ安全なコードを書く
 *
 * 【Optional<T> とは？】
 *   「値があるかもしれないし、ないかもしれない」を表すコンテナ。
 *   null を直接扱う代わりに Optional を使い、null チェック忘れを防ぐ。
 *
 * 【作成】
 *   Optional.of(値)          → 値がnullでないことが確実なとき
 *   Optional.ofNullable(値)  → 値がnullかもしれないとき
 *   Optional.empty()         → 空のOptional
 *
 * 【取り出し】
 *   get()                    → 値を取得（空ならNoSuchElementException）
 *   orElse(デフォルト)        → 空ならデフォルト値を返す
 *   orElseGet(Supplier)      → 空なら Supplier で値を生成
 *   orElseThrow()            → 空なら例外を投げる
 *
 * 【変換・判定】
 *   isPresent()              → 値があるか（true/false）
 *   ifPresent(Consumer)      → 値があれば Consumer を実行
 *   map(Function)            → 値を変換した新しいOptionalを返す
 *   filter(Predicate)        → 条件を満たさなければ empty
 *   flatMap(Function)        → Optional を返す Function 用（二重ネスト防止）
 *
 * 【Optional を使うべき場面と使うべきでない場面】
 *   使うべき:
 *   - メソッドの戻り値が「結果がないかもしれない」場合（findById, getFirst 等）
 *   - Stream の終端操作 (findFirst, findAny, reduce 等) の戻り値
 *   使うべきでない:
 *   - フィールドの型: Optional はシリアライズできないため、フィールドには使わない
 *   - メソッドの引数: null チェックは引数では従来通りの方法で行う
 *   - コレクションの戻り値: 空のリストを返す方が良い（Optional<List> は冗長）
 *
 * 【get() を直接使わない理由】
 *   get() は空の Optional に対して NoSuchElementException をスローする。
 *   これは NullPointerException と同じ問題を引き起こす。
 *   orElse(), orElseGet(), orElseThrow() を使う方が安全。
 */
public class OptionalLesson {

    public static void main(String[] args) {

        // =============================================
        // Optional の基本的な作成方法
        // =============================================

        // Optional<String> opt1 = Optional.of("Hello");
        // Optional.of() は、null でない値を包む Optional を作成する。
        // 引数が null の場合、即座に NullPointerException が発生する。
        // 「この値は絶対に null ではない」と確信がある場合に使う。
        // 内部的には、Optional オブジェクトが "Hello" という文字列への参照を保持する。

        // Optional<String> opt2 = Optional.empty();
        // Optional.empty() は、値を持たない空の Optional を作成する。
        // 「結果が存在しない」ことを明示的に表現する方法。
        // null を返す代わりに Optional.empty() を返すことで、
        // 呼び出し側が「値がないかもしれない」ことを意識できる。
        // 内部的にはシングルトン（同じインスタンスが再利用される）なのでメモリ効率が良い。

        // Optional<String> opt3 = Optional.ofNullable(null);
        // Optional.ofNullable() は、値が null かもしれない場合に使う。
        // 値が null なら Optional.empty() を返し、null でなければ Optional.of(値) を返す。
        // 外部APIやデータベースから取得した値など、null の可能性がある場合に最適。
        // 例: Optional.ofNullable(map.get("key")) → キーが存在しなければ empty になる。

        // System.out.println("--- 基本 ---");
        // セクションの見出し。

        // System.out.println("opt1: " + opt1.orElse("(空)"));  // Hello
        // orElse() は Optional に値があればその値を返し、空なら引数のデフォルト値を返す。
        // opt1 は "Hello" を持っているので "Hello" が返る。
        // orElse() は最も基本的で安全な値の取り出し方法。
        // 【注意】orElse() はOptionalが空でなくても引数の式が評価される。
        //   つまり orElse(expensiveOperation()) と書くと、
        //   値があっても expensiveOperation() が実行されてしまう。
        //   コストの高い処理の場合は orElseGet(() -> expensiveOperation()) を使うべき。

        // System.out.println("opt2: " + opt2.orElse("(空)"));  // (空)
        // opt2 は empty() で作ったので値がない。デフォルト値 "(空)" が返る。

        // System.out.println("opt3: " + opt3.orElse("(空)"));  // (空)
        // opt3 は ofNullable(null) で作ったので、内部的には empty と同じ。デフォルト値が返る。

        // =============================================
        // ifPresent（値がある場合のみ処理を実行）
        // =============================================

        // opt1.ifPresent(v -> System.out.println("値あり: " + v));
        // ifPresent() は、Optional に値がある場合のみ、引数の Consumer を実行する。
        // Consumer<String> を受け取り、v に Optional の値が渡される。
        // opt1 は "Hello" を持っているので、「値あり: Hello」が出力される。
        // 値がない場合は何も実行されない（例外も発生しない）。
        // 【if文との比較】
        //   従来: if (value != null) { System.out.println(value); }
        //   Optional: opt.ifPresent(v -> System.out.println(v));
        //   Optional の方が「null チェック忘れ」を防ぎやすい。
        // 【Java 9 以降】ifPresentOrElse(Consumer, Runnable) が追加された。
        //   値がある場合と無い場合の両方の処理を書ける。
        //   opt.ifPresentOrElse(v -> ..., () -> System.out.println("値なし"));

        // opt2.ifPresent(v -> System.out.println("これは表示されない"));
        // opt2 は空の Optional なので、Consumer は実行されない。
        // この行は何も出力しない。
        // ifPresent() は「値がある時だけ何かする」というパターンに最適。

        // =============================================
        // map（値の変換）
        // =============================================

        // Optional<Integer> length = opt1.map(String::length);
        // map() は Optional の値に Function を適用して、新しい Optional を返す。
        // opt1 は "Hello" を持っているので、String::length が適用されて 5 が得られる。
        // 結果は Optional<Integer> として包まれる（Optional.of(5) と同等）。
        // Optional が空の場合、map() は何も実行せず Optional.empty() を返す。
        // 【チェーンが可能】
        //   opt.map(String::length).map(n -> n * 2) のように、
        //   変換を連鎖させることができる。途中で空になったら以降の処理はスキップされる。
        //   これは null チェックの if 文の連鎖を避ける強力なパターン。
        // 【map vs flatMap の違い】
        //   map:     Function の戻り値を Optional で包む → Optional<Optional<T>> になる可能性
        //   flatMap: Function が Optional を返す場合に使う → 二重ネストを防ぐ
        //   例: map(this::findUserName) → Optional<Optional<String>>（二重ネスト）
        //       flatMap(this::findUserName) → Optional<String>（フラット）

        // System.out.println("\n文字数: " + length.orElse(0));  // 5
        // length は Optional<Integer> で値は 5。orElse(0) で取り出す。
        // もし元の Optional が空だった場合、map() の結果も空なので 0 が返る。

        // =============================================
        // filter（条件によるフィルタリング）
        // =============================================

        // Optional<String> long5 = opt1.filter(s -> s.length() > 3);
        // filter() は Optional の値が Predicate の条件を満たすかチェックする。
        // 条件を満たす → そのまま Optional を返す。
        // 条件を満たさない → Optional.empty() を返す。
        // Optional が空の場合 → そのまま Optional.empty() を返す。
        // "Hello" は5文字で3文字超なので、条件を満たし、Optional.of("Hello") が返る。
        // 【使いどころ】Optional の値に追加条件を付けたい場合。
        //   例: findUser(id).filter(u -> u.isActive()) → IDで見つかり、かつアクティブなユーザー

        // System.out.println("3文字超? " + long5.isPresent());  // true
        // isPresent() は Optional に値があれば true、空なら false を返す。
        // long5 は値を持っているので true。
        // 【注意】isPresent() + get() のパターンは避けるべき。
        //   if (opt.isPresent()) { opt.get() } は null チェックと変わらない。
        //   代わりに orElse(), map(), ifPresent() 等を使うべき。

        // =============================================
        // 実践例: null を返すメソッドを安全に使う
        // =============================================

        // String result = findUserName(1)
        //     .map(String::toUpperCase)
        //     .orElse("UNKNOWN");
        // findUserName(1) は Optional<String> を返すメソッド（下で定義）。
        // id=1 の場合 Optional.of("naoki") が返る。
        //
        // .map(String::toUpperCase)
        //   → "naoki" を "NAOKI" に変換する。Optional.of("NAOKI") が返る。
        //
        // .orElse("UNKNOWN")
        //   → Optional に値があるので "NAOKI" が返る。
        //   もし findUserName が empty を返していたら、"UNKNOWN" が返る。
        //
        // 【このパターンの素晴らしさ】
        //   従来の null チェック:
        //     String name = findUserName(1);
        //     String result;
        //     if (name != null) { result = name.toUpperCase(); }
        //     else { result = "UNKNOWN"; }
        //   Optional を使うと1行で安全に書ける。
        //   null チェック忘れの心配がなく、コードも簡潔で意図が明確。

        // System.out.println("\nユーザー: " + result);
        // 出力: ユーザー: NAOKI

        // ===== 練習問題 =====
        // 1. Optional を使って安全に文字列の最初の文字を取得するメソッドを作ろう
        //    ヒント: Optional<Character> firstChar(String s) {
        //      return Optional.ofNullable(s)
        //        .filter(str -> !str.isEmpty())
        //        .map(str -> str.charAt(0));
        //    }
        //    null チェックと空文字チェックの両方を Optional のチェーンで行える。
        //
        // 2. map と filter をチェーンして「5文字以上の文字列を大文字にする」処理を書こう
        //    ヒント: Optional.of("hello world")
        //      .filter(s -> s.length() >= 5)
        //      .map(String::toUpperCase)
        //      .orElse("(短すぎる)");
        //    filter で条件を絞り、map で変換し、orElse でデフォルト値を設定する。
    }

    // =============================================
    // Optional を返すメソッドの定義例
    // =============================================

    // static Optional<String> findUserName(int id) {
    //     // メソッドの戻り値を Optional にすることで、「結果がないかもしれない」ことを
    //     // 型レベルで明示できる。呼び出し側は Optional を受け取るので、
    //     // 値がない場合の処理を書かざるを得なくなる。
    //     // これが null を直接返す場合との最大の違い。
    //     // null を返すと、呼び出し側が null チェックを忘れる可能性がある。
    //     // Optional を返すと、「この結果は空かもしれない」ことが型から分かる。
    //
    //     if (id == 1) return Optional.of("naoki");
    //     // id が 1 の場合、"naoki" を Optional で包んで返す。
    //     // Optional.of() を使っているのは、"naoki" が null でないことが確実だから。
    //     // 文字列リテラルは絶対に null にならないので of() で安全。
    //
    //     return Optional.empty();
    //     // id が 1 以外の場合、空の Optional を返す。
    //     // null を返す代わりに Optional.empty() を返すのがベストプラクティス。
    //     // 呼び出し側は orElse() 等で安全に値を取り出せる。
    //     // 【絶対に避けるべきこと】Optional メソッドで null を返すこと。
    //     //   return null; は Optional の意味を完全に無にする最悪のパターン。
    //     //   必ず Optional.empty() を使うこと。
    // }
}
