package com.ppp.springboot.vul;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author Whoopsunix
 *
 * 请求参数获取示例
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    /**
     * request.getParameter()
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/case1")
    public void case1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);
        PrintWriter writer = response.getWriter();
        writer.println(param);
    }

    /**
     * 直接获取整个请求 body
     *
     * @param requestBody
     * @param request
     * @param response
     */
    @RequestMapping("/case2")
    @ResponseBody
    public void case2(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(requestBody);
        PrintWriter writer = response.getWriter();
        writer.println(requestBody);
    }

    /**
     * @RequestBody
     *
     * @param map
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/case3")
    public void case3(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String param = (String) map.get("param");
        System.out.println(param);
        PrintWriter writer = response.getWriter();
        writer.println(param);
    }

    /**
     * @RequestParam
     *
     * @param param
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/case4")
    public void case4(@RequestParam String param, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(param);
        PrintWriter writer = response.getWriter();
        writer.println(param);
    }


}
