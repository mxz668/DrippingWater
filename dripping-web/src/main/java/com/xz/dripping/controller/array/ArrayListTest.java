package com.xz.dripping.controller.array;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MABIAO on 2018/1/17.
 */
public class ArrayListTest {

    public static void main(String args[]){
//        List list = new ArrayList();
//        list.add("1");
//
//        System.out.println(list.size());
        try {
            String info = "{}";
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(info, new TypeReference<HashMap<String, String>>(){});
            System.out.println(map.get("2"));
        }catch (Exception e){

        }
    }
}
