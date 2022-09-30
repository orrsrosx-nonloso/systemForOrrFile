package com.orrsrosx.subject.tree;

/**
 * @author huyun
 * @ClassName treenode
 * @createTime 2021/1/26 16:19
 * @Version
 **/
public class Node {
    String value;
    Node left;
    Node right;

    public Node(String value) {
        this.value = value;
    }
    public Node(String value, Node left, Node right) {
        super();
        this.value = value;
        this.left = left;
        this.right = right;
    }

}
