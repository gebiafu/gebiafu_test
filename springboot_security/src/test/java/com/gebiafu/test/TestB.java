package com.gebiafu.test;

import java.util.HashMap;
import java.util.Map;

/**
 * title: 统计字符串中字符出现的次数
 * title: 统计字符串中字符出现的次数
 * author: Gebiafu
 * date: 2021/07/10 21:51
 */
public class TestB {
    public static void main(String[] args) {
        String s="asdferdsdfdfsdgfdfsgwewrfdsfvdsghtjhtejtehasa";
        //转成字符数组
        char[] sc = s.toCharArray();
        //遍历数组,存进map中
        Map<Character, Integer> map=new HashMap();
        //数组中字符为key,出现次数为value
        int a=1;//第一次出现次数
        for(char d:sc){
            map.put(d,map.getOrDefault(d,0)+1);
        }
/*        for(int i=0;i<sc.length;i++){
            //字符在map中不存在则添加
            if(map.get(sc[i])==null){
                map.put(sc[i],a);
            }else{
                //字符存在,则value加一
                Integer o = (Integer) map.get(sc[i]);
                map.put(sc[i],o+1);
            }
        }*/
        System.out.println(map);
    }
}
