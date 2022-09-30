package com.orrsrosx.subject.tree;

import java.util.LinkedList;

/**
 * @author huyun
 * @ClassName 遍历树
 * @createTime 2021/1/26 16:23
 * @Version
 **/
public class GetNoden {
    //主函数
    /**
    * @Description: 请实现函数treeLevel(TNode tree, int level)，level为整数，表示树的层数。 要求能返回树tree的第level层的所有节点值，并且输出顺序为从左到右。例如：treeLevel(tree, 3)，则返回G-H-C-F
    * @Param: j
    * @return:
    * @Author: huyun
    * @Date: 2021/3/18
    */
    public static String[] treeLevel(Node tree, int level) {
        //溢出或者过小的情况。
        if (level <= 0 || level > maxLenght(tree)) {
            return null;
        }
        LinkedList<String> list = new LinkedList<String>();
        dfs(tree, level, list);
        String[] arr = new String[list.size()];
        list.toArray(arr);
        return arr;
    }



    //遍历这个深度的节点
    private static void dfs(Node node, int level, LinkedList<String> list) {
        //高度为一时直接传入当前值
        if (level == 1) {
            list.add(node.value);
        }
        //左子树不为空是，进行左子树遍历直到传入的level值为1
        if (node.left != null) {
            dfs(node.left, level - 1, list);
        }
        if (node.right != null) {
            dfs(node.right, level - 1, list);
        }
    }


    //获取树最大深度
    public static int maxLenght(Node node) {
        if (node == null) {
            return 0;
        } else {
            int left = maxLenght(node.left);
            int right = maxLenght(node.right);
            return Math.max(left, right) + 1;
        }
    }
}
