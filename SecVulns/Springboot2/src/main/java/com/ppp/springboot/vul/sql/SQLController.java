package com.ppp.springboot.vul.sql;

import com.ppp.mysql.AliDruidInject;
import com.ppp.mysql.HQLInject;
import com.ppp.mysql.MysqlInject;
import com.ppp.mysql.SpringJDBCInject;
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
@RequestMapping("/sql")
public class SQLController {
    @RequestMapping("/mysql/case1")
    @ResponseBody
    public Object MysqlCase1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id  = request.getParameter("id");
        System.out.println(id);
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        System.out.println(password);

        List<Object> results = MysqlInject.select(Integer.valueOf(id), username, password);
        return results;
    }

    @RequestMapping("/mysql/case2")
    @ResponseBody
    public Object MysqlCase2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id  = request.getParameter("id");
        System.out.println(id);
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        System.out.println(password);

        List<Object> results = MysqlInject.select(Integer.valueOf(id), username, password);
        return results;
    }

    @RequestMapping("/hql/case1")
    @ResponseBody
    public Object HQLCase1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id  = request.getParameter("id");
        System.out.println(id);
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        System.out.println(password);

        List<Object> results = HQLInject.select(Integer.valueOf(id), username, password);
        return results;
    }

    @RequestMapping("/ali/case1")
    @ResponseBody
    public Object AliCase1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id  = request.getParameter("id");
        System.out.println(id);
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        System.out.println(password);

        List<Object> results = AliDruidInject.select(Integer.valueOf(id), username, password);
        return results;
    }

    @RequestMapping("/spring/case1")
    @ResponseBody
    public Object SpringCase1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id  = request.getParameter("id");
        System.out.println(id);
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        System.out.println(password);

        List<Object> results = SpringJDBCInject.select(Integer.valueOf(id), username, password);
        return results;
    }
}
