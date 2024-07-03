package com.ppp.springboot.vul.jni;

import com.ppp.JNIAttack;
import h2database.H2Attack;
import ibm.IBMAttack;
import modeshape.ModeshapeAttack;
import mysql.MysqlAttack;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import postgresql.PostgresqlAttack;
import teradata.TeradataAttack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/JNI")
public class JNIController {
    @RequestMapping("/case1")
    @ResponseBody
    public void JNIAttack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        JNIAttack.load(param);
    }

}
