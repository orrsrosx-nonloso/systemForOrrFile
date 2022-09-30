package com.orrsrosx.subject.intToHex;

/**
 * @author huyun
 * @ClassName IntToHex
 * @createTime 2021/1/26 15:14
 * @Version
 **/
public class IntToHex {

    public static String intToHex(int i){
        StringBuilder stringBuilder=new StringBuilder();
        char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        int div = 1; //除数
        int res=0; //余数
        while(div!=0){
            res=i%16;
            div=i/16;
            i=res;
            stringBuilder.append(hex[res]);
        }
        String a= String.valueOf(stringBuilder.reverse());
        return a;
    }
}
