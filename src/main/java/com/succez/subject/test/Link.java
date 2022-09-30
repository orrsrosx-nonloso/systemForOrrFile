package com.orrsrosx.subject.test;

import com.orrsrosx.server.ThreadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * @author huyun
 * @ClassName
 * @createTime 2021/3/17 9:50
 * @Version
 **/
public class Link {
    private static final Logger log = LoggerFactory.getLogger(ThreadTask.class);

    static class Node {
        Node next=null;// 节点的引用，指向下一个节点
        int data;// 节点的对象，即内容

        public Node(int data) {
            this.data = data;
        }
        public Node() {
        }
    }

    public static int numJewelsInStones(String J, String S) {
        char[] Ja = J.toCharArray();
        char[] Sa = S.toCharArray();
        int r = 0;
        for (int i = 0;i < Ja.length ; i ++){
            for(int j = 0; j < Sa.length; j++){
                if(Ja[i] == Sa[j])
                    r ++;
            }
        }
        return r;
    }

    public static void main(String[] args) throws IOException {
        Demo_02();
    }

    private static void Demo_02() {
        System.out.print("请输入一个整数求阶乘:");
        String s = new Scanner(System.in).nextLine();
        System.out.println(Mulptiy(new BigInteger(s)).toString());
    }

    public static BigInteger Mulptiy(BigInteger n) {
        if(n.intValue() == 1) return new BigInteger("1");
        BigInteger sub = new BigInteger("1");
        BigInteger bit = n.subtract(sub);
        return n.multiply(Mulptiy(bit));
    }

    public static void add(int data,Node node){
        if(node.next == null){
            node.next = new Node(data);		//如果当前节点的next为null,直接创建一个新的节点
        }else {
            add(data,node.next);
        }
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

    public static String[] getChange(String str,int i){
        String[] strArray = str.split("");
        for (int j=0;j<i;j++){
            String tip=strArray[strArray.length-1];
            for (int k=strArray.length-1;k>=0;k--){
                if (k==0){
                    strArray[k]=tip;
                }else {
                    strArray[k]=strArray[k-1];
                }
            }
        }
        return strArray;
    }
}
