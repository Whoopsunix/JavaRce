package com.ppp.springboot.vul.ssrf;

import com.ppp.SSRFAttack;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/SSRF")
public class SSRFController {
    @RequestMapping("/case1")
    @ResponseBody
    public Object ssrf1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        Object result = SSRFAttack.httpClient(param);
        return result;
    }

    @RequestMapping("/case2")
    @ResponseBody
    public Object ssrf2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        Object result = SSRFAttack.okhttp(param);
        return result;
    }

    @RequestMapping("/case3")
    @ResponseBody
    public Object ssrf3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        Object result = SSRFAttack.URLConnection(param);
        return result;
    }


}
