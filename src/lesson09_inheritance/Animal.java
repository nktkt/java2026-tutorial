package lesson09_inheritance;

/**
 * ===== レッスン09: 継承 =====
 *
 * 継承 = 既存のクラス（親）の機能を引き継いで新しいクラス（子）を作る。
 * コードの再利用ができ、「〜は〜の一種」という関係を表現できる。
 *
 * キーワード:
 *   extends    → 親クラスを継承する
 *   super      → 親クラスのコンストラクタやメソッドを呼ぶ
 *   @Override  → 親のメソッドを上書き（オーバーライド）する
 *   protected  → 自分と子クラスからアクセスできる
 */

// ========== 親クラス ==========
public class Animal {

    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void eat() {
        System.out.println(name + "は食事中です。");
    }

    public void sleep() {
        System.out.println(name + "は眠っています。");
    }

    public void introduce() {
        System.out.println("動物: " + name + "（" + age + "歳）");
    }
}
