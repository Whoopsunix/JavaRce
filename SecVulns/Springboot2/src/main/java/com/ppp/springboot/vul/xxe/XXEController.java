package com.ppp.springboot.vul.xxe;

import org.example.XXEAttack;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Whoopsunix
 * <p>
 * 请求参数获取示例
 */
@Controller
@RequestMapping("/xxe")
public class XXEController {
    @RequestMapping("/case1")
    @ResponseBody
    public Object case1(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.xmlReader(requestBody);
        return result;
    }

    @RequestMapping("/case2")
    @ResponseBody
    public Object case2(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.jdomSAXBuilder(requestBody);
        return result;
    }


    @RequestMapping("/case3")
    @ResponseBody
    public Object case3(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.jdom2SAXBuilder(requestBody);
        return result;
    }

    @RequestMapping("/case4")
    @ResponseBody
    public Object case4(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.javaxSAXParser(requestBody);
        return result;
    }

    @RequestMapping("/case5")
    @ResponseBody
    public Object case5(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.dom4jSAXReader(requestBody);
        return result;
    }

    @RequestMapping("/case6")
    @ResponseBody
    public Object case6(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.jaxpSAXParserFactoryImpl(requestBody);
        return result;
    }

    @RequestMapping("/case7")
    @ResponseBody
    public Object case7(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.xercesSAXParser(requestBody);
        return result;
    }

    @RequestMapping("/case8")
    @ResponseBody
    public Object case8(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.javaxDocumentBuilder(requestBody);
        return result;
    }

    @RequestMapping("/case9")
    @ResponseBody
    public Object case9(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.jaxpDocumentBuilderImpl(requestBody);
        return result;
    }

    @RequestMapping("/case10")
    @ResponseBody
    public Object case10(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.jaxpDocumentBuilderFactoryImpl(requestBody);
        return result;
    }

    @RequestMapping("/case11")
    @ResponseBody
    public Object case11(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.jaxpXercesDocumentBuilderFactoryImpl(requestBody);
        return result;
    }

    @RequestMapping("/case12")
    @ResponseBody
    public Object case12(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.dom4jDocumentHelper(requestBody);
        return result;
    }

    @RequestMapping("/case13")
    @ResponseBody
    public Object case13(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(requestBody);
        Object result = XXEAttack.javaxXMLInputFactory(requestBody);
        return result;
    }


}
