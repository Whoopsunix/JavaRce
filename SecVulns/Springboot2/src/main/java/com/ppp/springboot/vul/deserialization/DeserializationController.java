package com.ppp.springboot.vul.deserialization;

import com.ppp.FastjsonAttack;
import com.ppp.JavaBeanAttack;
import com.ppp.SnakeyamlAttack;
import com.ppp.XStreamAttack;
import com.ppp.code.GroovyAttack;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URLDecoder;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/deserialization")
public class DeserializationController {
    @RequestMapping("/javaBean/case1")
    @ResponseBody
    public Object JavaBean(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);


        Object result = JavaBeanAttack.xmlDecoder(body);
        return result;
    }

    @RequestMapping("/xstream/case1")
    @ResponseBody
    public Object xstream(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);


        Object result = XStreamAttack.deserialize(body);
        return result;
    }

    @RequestMapping("/snakeyaml/case1")
    @ResponseBody
    public Object Snakeyaml(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);


        Object result = SnakeyamlAttack.load(body);
        return result;
    }

    @RequestMapping("/original/case1")
    @ResponseBody
    protected Object original(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        InputStream fileContent = file.getInputStream();
        byte[] bytes = new byte[fileContent.available()];
        fileContent.read(bytes);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object result = objectInputStream.readObject();
        return result;
    }

}
