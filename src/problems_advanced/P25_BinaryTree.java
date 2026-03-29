package problems_advanced;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * ===== 上級07: 二分木の走査 (Binary Tree Traversal) =====
 * 出典: LeetCode #94, #144, #145, #102
 *
 * 【問題】
 *   二分木を前順(Preorder)、中順(Inorder)、後順(Postorder)、
 *   レベル順(Level-order)で走査せよ。
 *
 * 【二分木とは？】
 *   各ノードが最大2つの子（左と右）を持つ木構造。
 *         1
 *        / \
 *       2   3
 *      / \   \
 *     4   5   6
 *
 * 【4つの走査順序】
 *   前順 (Preorder):  根→左→右  → 1,2,4,5,3,6
 *   中順 (Inorder):   左→根→右  → 4,2,5,1,3,6
 *   後順 (Postorder): 左→右→根  → 4,5,2,6,3,1
 *   レベル順:         上から下、左から右 → 1,2,3,4,5,6
 *
 * 【学べること】
 *   - 木構造のデータ構造
 *   - 再帰による木の走査
 *   - BFSによるレベル順走査
 */
public class P25_BinaryTree {

    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        //         1
        //        / \
        //       2   3
        //      / \   \
        //     4   5   6
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        // System.out.print("前順:     "); preorder(root); System.out.println();
        // System.out.print("中順:     "); inorder(root); System.out.println();
        // System.out.print("後順:     "); postorder(root); System.out.println();
        // System.out.print("レベル順: "); levelOrder(root); System.out.println();
    }

    // ========== 解答例 ==========

    // --- 前順: 根→左→右 ---
    // static void preorder(TreeNode node) {
    //     if (node == null) return;
    //     System.out.print(node.val + " ");
    //     preorder(node.left);
    //     preorder(node.right);
    // }

    // --- 中順: 左→根→右 ---
    // static void inorder(TreeNode node) {
    //     if (node == null) return;
    //     inorder(node.left);
    //     System.out.print(node.val + " ");
    //     inorder(node.right);
    // }

    // --- 後順: 左→右→根 ---
    // static void postorder(TreeNode node) {
    //     if (node == null) return;
    //     postorder(node.left);
    //     postorder(node.right);
    //     System.out.print(node.val + " ");
    // }

    // --- レベル順: BFS ---
    // static void levelOrder(TreeNode root) {
    //     if (root == null) return;
    //     Queue<TreeNode> queue = new LinkedList<>();
    //     queue.add(root);
    //     while (!queue.isEmpty()) {
    //         TreeNode node = queue.poll();
    //         System.out.print(node.val + " ");
    //         if (node.left != null) queue.add(node.left);
    //         if (node.right != null) queue.add(node.right);
    //     }
    // }
}
