package com.ppp.springboot.vul.deserialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ppp.FastjsonAttack;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/deserialization")
public class FastjsonController {
    public class KV {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "KV{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }


    @RequestMapping("/fastjson/case1")
    @ResponseBody
    public Object case1(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(requestBody);

        Object result = FastjsonAttack.parseObject(requestBody);

        return result;
    }

    /**
     * value 对象形式可控
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/fastjson/case2")
    @ResponseBody
    public Object case2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String value = request.getParameter("value");
        System.out.println(value);

        KV kv = new KV();
        kv.setName("test");
        kv.setValue(value);

        String json = JSONObject.toJSONString(kv);
        System.out.println(json);

        JSONObject result = JSON.parseObject(json);

        return result;
    }

    /**
     * value 字符替换可控
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/fastjson/case3")
    @ResponseBody
    public Object case3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String value = request.getParameter("value");
        System.out.println(value);

        String json = String.format("{\"name\":\"test\",\"value\":\"%s\"}", value);
        System.out.println(json);

        JSONObject result = JSON.parseObject(json);
        return result;

    }
}
