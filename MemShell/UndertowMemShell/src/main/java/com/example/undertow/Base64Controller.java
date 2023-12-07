package com.example.undertow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

/**
 * @author Whoopsunix
 */
@Controller
public class Base64Controller {
    @RequestMapping("/test")
    @ResponseBody
    protected String test(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 反序列化
        String str = req.getParameter("str");
        return str;
    }


    @RequestMapping("/base64")
    protected void base64De(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        try {
            // 反序列化
            String base64Str = req.getParameter("base64Str");
            System.out.println(base64Str);
            byte[] bytes = Base64.getDecoder().decode(base64Str);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            objectInputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
