package com.nuoxin.virtual.rep.api;

import org.junit.Test;

import java.util.*;

/**
 * Created by fenggang on 12/19/17.
 *
 * @author fenggang
 * @date 12/19/17
 */
public class PushTest {

    @Test
    public void num() {
        ArrayList<String> lists = new ArrayList<>();
        for (int i = 0; i < (10425 + 3585); i++) {
            if (i == 100) {
                lists.add("123456789987654321" + "_" + System.currentTimeMillis());
            } else {
                lists.add(UUID.randomUUID().toString() + "_" + System.currentTimeMillis());
            }
        }

        Set<String> set = new HashSet<>();
        for (int i = 0,leng=lists.size(); i < leng; i++) {
            String str = lists.get(i);
            if(str!=null && str!="" || str.trim().length()>5){
                set.add(str.split("_")[0]);
            }
        }
        List<String> list = new ArrayList<>();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String str = it.next();
            if(str!=null && str!="" || str.trim().length()>5){
                list.add(str);
            }
        }
        int count = 1;
        for (int i = 500; i < list.size() + 500; i = i + 500) {
            if (i > list.size()) {
                int surplus = 500 - (i - list.size());
                List<String> l = list.subList(i - 500, i - 500 + surplus);
                for (String s:l) {
                    System.out.println(count+"==="+s);
                    count=count+1;
                }
            } else {
                List<String> l = list.subList(i - 500, i);

                for (String s:l) {
                    System.out.println(count+"==="+s);
                    count=count+1;
                }
            }
        }
    }
}
