package com.orrsrosx.subject.tree;

import org.junit.Assert;
import org.junit.Test;

public class GetNodenTest {

    @Test
    public void treeLevel() {
        /*
           A
          B C
         DE FG
        */
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");
        Node nodeG = new Node("G");

        Node nodeB = new Node("B", nodeD, nodeE);
        Node nodeC = new Node("C", nodeF, nodeG);
        Node node = new Node("A", nodeB, nodeC);

        String[] strings = GetNoden.treeLevel(node, 3);
        for (String i : strings) {
            System.out.print(i + " ");

        }
        //溢出测试
        Assert.assertNull(GetNoden.treeLevel(node, 4));
        //过小测试
        Assert.assertNull(GetNoden.treeLevel(node, 0));

    }

    @Test
    public void Level() {
        /*
           A
          B C
         DE FG
        */
        String str = "381 381 315 270 293 276 257 259 328";
        String[] spi=str.split(" ");
        int[] ints=new int[spi.length];
        for (int i=0;i<spi.length;i++){
            ints[i]= Integer.parseInt(spi[i]);
        }
        sort(ints);
        for(int a:ints)
            System.out.print(a+" ");
    }
    public static void sort(int[] arr) {
        if (arr.length >= 2) {
            for (int i = 1; i < arr.length; i++) {
                int x = arr[i];
                int j = i - 1;
                while (j >= 0 && arr[j] > x) {
                    arr[j + 1] = arr[j];
                    j--;
                }
                arr[j + 1] = x;
            }
        }
    }

}