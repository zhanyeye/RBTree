package cn.zhanyeye;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhanyeye
 * @description 红黑树实现
 * @date 2/21/2021
 */

public class RBTree<K extends Comparable<K>, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Node root;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    static class Node<K extends Comparable<K>, V> {
        private Node parent;
        private Node left;
        private Node right;
        private boolean color;
        private K key;
        private V value;

        public Node(K key, V value, Node parent) {
            this.parent = parent;
            this.color = RED;
            this.key = key;
            this.value = value;
        }

    }

    public void put(K key, V value) {
        Node t = this.root;
        if (t == null) {
            this.root = new Node(key, value, null);
        }

        // 寻找插入位置
        // 定义一个双亲指针
        Node parent;
        int cmp;
        if (key == null) {
            throw new NullPointerException();
        }
        do {
            parent = t;
            cmp = key.compareTo((K) t.key);
            if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            } else {
                t.setValue(value);
                return;
            }
        } while (t != null);

        Node e = new Node(key, value, parent);

        if (cmp < 0) {
            // 如果比较最终落在左子树，则直接将父节点的左指针指向e
            parent.left = e;
        } else {
            // 如果比较最终落在右子树，则直接将父节点的右指针指向e
            parent.right = e;
        }

        // 调整
        fixAfterPut(e);
    }

    /**
     * 1. 2-3-4树：新增元素
     * @param x
     */
    private void fixAfterPut(Node x) {

    }

    /**
     * 围绕着p点左旋
     *         pf                      pf
     *        /                       /
     *       p                       pr(r)
     *      / \         ==>         / \
     *     pl  pr(r)               p   rr
     *        / \                 / \
     *       rl  rr              pl  rl
     * @param p
     */
    private void leftRotate(Node p) {
        // 旋转支点不为空
        if (p != null) {
            // 临时结点指向旋转结点的右子结点
            Node r = p.right;
            // 将右结点的左子节点设置为p的右子树
            p.right = r.left;
            if (r.left != null) {
                r.left.parent = p;
            }

            // 将p的父结点指向pr
            r.parent = p.parent;
            if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }

            // p的右子结点(pr)变为p的父结点
            r.left = p;
            p.parent = r;

        }
    }

    /**
     * 围绕着p点右旋
     *          pf                      pf
     *         /                       /
     *        p                       pl(l)
     *       / \         ==>         / \
     * （l）pl  pr                  rl   p
     *    / \                          / \
     *   rl  rr                      rr   pr
     * @param p
     */
    private void rightRotate(Node p) {
        // 旋转支点不为空
        if (p != null) {
            // 临时结点指向旋转结点的右子结点
            Node l = p.left;
            // 将右结点的左子节点设置为p的右子树
            p.left = l.right;
            if (l.right != null) {
                l.right.parent = p;
            }

            // 将p的父结点指向pr
            l.parent = p.parent;
            if (p.parent.left == p) {
                p.parent.left = l;
            } else {
                p.parent.right = l;
            }

            // p的右子结点(pr)变为p的父结点
            l.right = p;
            p.parent = l;

        }
    }

}
