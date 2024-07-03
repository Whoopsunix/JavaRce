package com.ppp.springboot.vul.sql;

import com.ppp.XPathInject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/xpath/inject")
public class XPathInjectController {
    @RequestMapping("/case1")
    @ResponseBody
    public Object MysqlCase1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id  = request.getParameter("id");
        System.out.println(id);
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        System.out.println(password);

        List<Object> results = XPathInject.select(Integer.valueOf(id), username, password);
        return results;
    }
}
