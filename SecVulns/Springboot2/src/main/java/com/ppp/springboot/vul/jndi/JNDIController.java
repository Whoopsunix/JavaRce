package com.ppp.springboot.vul.jndi;


import jndi.JNDIAttack;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/jndi")
public class JNDIController {
    @RequestMapping("/case1")
    @ResponseBody
    public Object case1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = request.getParameter("url");
        System.out.println(url);

        Object result = JNDIAttack.lookup(url);
        System.out.println(result);

        return result;
    }
}
