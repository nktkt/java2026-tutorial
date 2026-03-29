package problems_intermediate;

/**
 * ===== 中級08: 連結リスト (Linked List) =====
 * 出典: LeetCode #206 / データ構造の基本
 *
 * 【問題】
 *   単方向連結リストを反転させよ。
 *   1 → 2 → 3 → 4 → null  を  4 → 3 → 2 → 1 → null にする
 *
 * 【連結リストとは？】
 *   各要素(ノード)が「値」と「次のノードへの参照」を持つデータ構造。
 *   配列と違い、挿入/削除が O(1)（参照を繋ぎ替えるだけ）。
 *
 *   Node: [値 | next] → [値 | next] → [値 | null]
 *
 * 【配列 vs 連結リスト】
 *   配列: ランダムアクセスO(1)、挿入/削除O(n)
 *   連結リスト: ランダムアクセスO(n)、挿入/削除O(1)
 *
 * 【アプローチ: 3つのポインタ】
 *   prev, current, next を使って矢印の向きを1つずつ逆にする
 *
 * 【学べること】
 *   - 連結リストのデータ構造
 *   - ポインタ（参照）の操作
 *   - ノードクラスの自作
 */
public class P20_LinkedList {

    // ノードの定義
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    public static void main(String[] args) {
        // リスト作成: 1 → 2 → 3 → 4 → 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.print("反転前: ");
        printList(head);

        // head = reverseList(head);

        // System.out.print("反転後: ");
        // printList(head);
    }

    static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println();
    }

    static ListNode reverseList(ListNode head) {
        // ここにコードを書こう
        return head;

        // ========== 解答例 ==========
        // ListNode prev = null;
        // ListNode current = head;
        //
        // while (current != null) {
        //     ListNode next = current.next;   // 次を保存
        //     current.next = prev;            // 矢印を逆向きにする
        //     prev = current;                 // prevを進める
        //     current = next;                 // currentを進める
        // }
        // return prev;   // prevが新しいhead
        //
        // // 例: 1→2→3→null
        // // ステップ1: prev=null,  cur=1, next=2 → null←1  2→3→null
        // // ステップ2: prev=1,     cur=2, next=3 → null←1←2  3→null
        // // ステップ3: prev=2,     cur=3, next=null → null←1←2←3
        // // 結果: 3→2→1→null
    }
}
