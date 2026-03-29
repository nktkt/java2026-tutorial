package lesson31_thread;

// java.util.concurrent パッケージ: マルチスレッドプログラミングの高レベルAPIを提供する
// ExecutorService, Executors, Future, Callable など並行処理に必要なクラスが含まれる
// Java 5 で導入され、Thread を直接扱うよりも安全で効率的な並行処理が可能になった
// 「.*」で全クラスを一括インポートしている
import java.util.concurrent.*;

/**
 * ===== レッスン31: マルチスレッド =====
 *
 * 【目標】複数の処理を並行して実行する方法を学ぶ
 *
 * 【スレッドとは？】
 *   プログラムの中で同時に実行される処理の流れ。
 *   例: ファイルダウンロードしながらUIを動かす
 *   1つのプロセス（アプリケーション）の中に複数のスレッドが存在できる。
 *   全スレッドは同じメモリ空間（ヒープ）を共有するが、スタックはスレッドごとに独立。
 *
 * 【スレッドの作り方】
 *   1. Runnable インターフェースを実装 → new Thread(runnable).start()
 *   2. Thread クラスを継承 → new MyThread().start()
 *   3. ExecutorService を使う（推奨）
 *
 * 【重要な注意点】
 *   - start() でスレッドを開始する。run() を直接呼ぶと並行実行されない。
 *   - 複数スレッドから同じ変数を変更すると競合状態(Race Condition)が起きる
 *   - synchronized で排他制御する
 *
 * 【ExecutorService（推奨）】
 *   スレッドプールを管理し、効率的にタスクを実行する。
 *   スレッドの作成・破棄のコストを削減し、同時実行数を制御できる。
 *
 * 【よくあるミス】
 *   1. start() ではなく run() を呼んでしまう（並行にならない）
 *   2. 共有変数のアクセスを同期化し忘れて競合状態が起きる
 *   3. ExecutorService の shutdown() を忘れてプログラムが終了しない
 *   4. デッドロック（2つのスレッドが互いにロック解放を待ち合う状態）
 */
public class ThreadLesson {

    // counter はクラス変数（static フィールド）で、全スレッドから共有される
    // 複数スレッドから同時にアクセスすると競合状態（Race Condition）が発生する可能性がある
    // 【メモリモデル】static変数はヒープ上のメソッド領域に置かれ、全スレッドから見える
    // static int counter = 0;

    public static void main(String[] args) {

        // ---------- Runnableでスレッド作成 ----------

        // ラムダ式で Runnable インターフェースの run() メソッドを実装する
        // Runnable は関数型インターフェースで、引数なし・戻り値なしの run() を1つだけ持つ
        // このラムダ式の中身が、新しいスレッドで実行される処理内容
        // Runnable task = () -> {

        //     3回ループして、スレッド名とカウンタ値を出力する
        //     for (int i = 0; i < 3; i++) {

        //         Thread.currentThread().getName() で現在実行中のスレッドの名前を取得する
        //         スレッドAとスレッドBが交互に（または同時に）出力される様子が確認できる
        //         出力順は実行するたびに変わる可能性がある（スレッドのスケジューリングはOSが決定）
        //         System.out.println(Thread.currentThread().getName() + ": " + i);

        //         Thread.sleep(100) で現在のスレッドを100ミリ秒（0.1秒）停止させる
        //         sleep() は InterruptedException（検査例外）を投げる可能性があるため、
        //         try-catch で囲む必要がある
        //         sleep中にスレッドが interrupt() されると InterruptedException が発生する
        //         ここでは空の catch で例外を無視している（本番コードでは適切に処理すべき）
        //         try { Thread.sleep(100); } catch (InterruptedException e) {}

        //     }
        // };

        // Thread コンストラクタに Runnable と スレッド名 を渡して新しいスレッドオブジェクトを作る
        // 第2引数 "スレッドA" はデバッグ用のスレッド名。getName() で取得できる
        // 【注意】この時点ではスレッドはまだ開始されていない。start() を呼ぶまで待機状態
        // Thread t1 = new Thread(task, "スレッドA");

        // 同じ Runnable（task）を使って別のスレッドを作成する
        // 1つの Runnable を複数のスレッドで共有できる（ただし共有状態には注意が必要）
        // Thread t2 = new Thread(task, "スレッドB");

        // start() でスレッドの実行を開始する
        // 【超重要】start() は新しいスレッドを作って run() を実行する
        // run() を直接呼ぶと、現在のスレッド（main）でそのまま実行されてしまい並行にならない
        // start() と run() の違いは Java の面接で頻出の質問
        // 【内部動作】start() → OSにスレッド作成を依頼 → OSがスレッドをスケジュール → run() 実行
        // t1.start();  // 並行実行開始（start! run ではない！）

        // t2 も同時に実行開始する
        // t1 と t2 は並行に動くため、出力が混ざり合う
        // 実行例: スレッドA: 0 → スレッドB: 0 → スレッドA: 1 → スレッドB: 1 → ...
        // ただし順序は保証されない（スレッドB: 0 が先に出ることもある）
        // t2.start();

        // ---------- 競合状態（Race Condition）の例 ----------

        // System.out.println("\n--- 競合状態 ---");

        // counter をリセットして実験開始
        // counter = 0;

        // 2つのスレッドがそれぞれ counter を10000回インクリメント（+1）する
        // 【問題】counter++ は一見1つの操作に見えるが、実際には3つの操作に分解される:
        //   ① counter の値を読み取る（Read）
        //   ② 読み取った値に 1 を足す（Modify）
        //   ③ 結果を counter に書き戻す（Write）
        // 2つのスレッドが ① と ③ の間に割り込むと、インクリメントが「消える」
        // 例: counter=100 の時
        //   スレッドA: ① 100を読む → ② 101を計算（まだ書いてない）
        //   スレッドB: ① 100を読む → ② 101を計算 → ③ 101を書く
        //   スレッドA: ③ 101を書く  → 2回インクリメントしたのに101（1回分消えた！）
        // Thread ta = new Thread(() -> { for (int i = 0; i < 10000; i++) counter++; });
        // Thread tb = new Thread(() -> { for (int i = 0; i < 10000; i++) counter++; });

        // ta.start() と tb.start() で両スレッドを開始する
        // ta.start(); tb.start();

        // join() は対象スレッドの終了を待機するメソッド
        // ta.join() は ta が終了するまで main スレッドをブロック（待機）させる
        // 両方の join() が完了した時点で、ta と tb の処理は確実に終了している
        // join() しないと、counter の最終結果を見る前にプログラムが終了する可能性がある
        // InterruptedException は検査例外なので try-catch が必要
        // try { ta.join(); tb.join(); } catch (InterruptedException e) {}

        // 期待値は 10000+10000=20000 だが、競合状態により実際の値は20000未満になることがある
        // 実行するたびに結果が変わる（非決定的な動作）
        // 【解決方法】① synchronized ② AtomicInteger ③ Lock
        // System.out.println("期待: 20000, 実際: " + counter);
        // // ↑ 20000にならないことがある！（競合状態）

        // ---------- synchronized で解決 ----------

        // synchronized ブロック内は同時に1つのスレッドしか実行できない（排他制御）
        // synchronized(lockObject) { ... } でロックオブジェクトを指定して排他制御する
        // 他のスレッドは、ロックが解放されるまでブロックの入り口で待機する
        // 【代替手段】java.util.concurrent.atomic.AtomicInteger を使えば synchronized 不要:
        //   AtomicInteger counter = new AtomicInteger(0);
        //   counter.incrementAndGet(); // スレッドセーフなインクリメント
        // → synchronized ブロック内は同時に1スレッドしか実行できない

        // ---------- ExecutorService（推奨される方法） ----------

        // Executors.newFixedThreadPool(2) で2スレッドのスレッドプールを作成する
        // スレッドプールとは: あらかじめスレッドを作っておいて再利用する仕組み
        // スレッドの作成・破棄にはコストがかかるため、プールで使い回す方が効率的
        // 引数の 2 は同時実行できるスレッドの最大数。タスクが3つあれば2つが同時に実行され、
        // 1つはどちらかが空くのを待つ
        // ExecutorService executor = Executors.newFixedThreadPool(2);

        // submit() でタスク（Runnable）をスレッドプールに送信する
        // プール内の空きスレッドが自動的にタスクを受け取って実行する
        // Thread.currentThread().getName() でスレッド名を表示すると、
        // "pool-1-thread-1" や "pool-1-thread-2" のようなプール管理名が出力される
        // 【利点】new Thread() を直接使うよりスレッド管理が楽で、リソース効率も良い
        // executor.submit(() -> System.out.println("タスク1: " + Thread.currentThread().getName()));

        // 2つ目のタスクをプールに送信する。空きスレッドがあればすぐ実行される
        // executor.submit(() -> System.out.println("タスク2: " + Thread.currentThread().getName()));

        // shutdown() はスレッドプールに「新しいタスクは受け付けない」と伝える
        // 送信済みのタスクは最後まで実行されるが、新規タスクは拒否される
        // 【超重要】shutdown() を呼ばないと、プールのスレッドが生き続けてプログラムが終了しない
        // shutdownNow() は実行中のタスクも中断しようとする（より強制的）
        // executor.shutdown();

        // ---------- Future（非同期結果の取得） ----------

        // Executors.newSingleThreadExecutor() は1スレッドのプールを作成する
        // タスクは順番に1つずつ実行される（同時実行はしない）
        // 結果を受け取りたいタスクに使うことが多い
        // ExecutorService exec = Executors.newSingleThreadExecutor();

        // submit() に Callable<Integer>（ラムダ式）を渡すと、Future<Integer> が返される
        // Callable は Runnable と似ているが、戻り値を返せる点が異なる
        // Runnable: run() → 戻り値なし(void)
        // Callable: call() → 戻り値あり(T)
        // Future は「未来の結果」を表すオブジェクト。結果はまだ計算中かもしれない
        // Future<Integer> future = exec.submit(() -> {

        //     Thread.sleep(1000) で1秒間スリープして、重い処理をシミュレーションする
        //     実際の使用場面: API呼び出し、ファイル処理、データベースクエリなど
        //     Thread.sleep(1000);

        //     計算結果として 42 を返す
        //     この値が Future.get() で取得できる
        //     return 42;

        // });

        // try {

        //     future.get() は、タスクの結果が出るまでブロック（待機）する
        //     タスクが完了していれば即座に結果を返す
        //     まだ完了していなければ、完了するまで main スレッドが待つ
        //     【タイムアウト付き】future.get(5, TimeUnit.SECONDS) で最大5秒待つこともできる
        //     タイムアウトすると TimeoutException が発生する
        //     System.out.println("\n結果: " + future.get());  // ブロックして結果を待つ

        // } catch (Exception e) {
        //     ExecutionException（タスク内で例外が発生）や
        //     InterruptedException（待機中にスレッドが割り込まれた）をキャッチする
        //     e.printStackTrace();
        // }

        // exec のシャットダウンも忘れずに行う
        // ExecutorService は必ず shutdown() で終了させる必要がある
        // 【ベストプラクティス】try-finally または try-with-resources（Java 19+）で確実に閉じる
        // exec.shutdown();

        // ===== 練習問題 =====
        // 1. 2つのスレッドで別々のカウントダウンを並行実行してみよう
        //    ヒント: スレッドAは10から0へ、スレッドBは20から10へカウントダウン
        //    出力が混ざり合うことを確認しよう
        // 2. synchronized を使って競合状態を解消してみよう
        //    ヒント: Object lock = new Object();
        //    synchronized(lock) { counter++; }
        //    これにより counter++ が原子的（アトミック）に実行される
    }
}
