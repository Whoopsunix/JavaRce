package com.ppp.springboot.vul.jdbc;

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
@RequestMapping("/JDBC")
public class JDBCController {
    @RequestMapping("/H2Attack/case1")
    @ResponseBody
    public void H2Attack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        H2Attack.connect(param);
    }

    @RequestMapping("/IBMAttack/case1")
    @ResponseBody
    public void IBMAttack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        IBMAttack.connect(param);
    }

    @RequestMapping("/ModeshapeAttack/case1")
    @ResponseBody
    public void ModeshapeAttack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        ModeshapeAttack.connect(param);
    }

    @RequestMapping("/MysqlAttack/case1")
    @ResponseBody
    public void MysqlAttack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        MysqlAttack.connect(param);
    }

    @RequestMapping("/PostgresqlAttack/case1")
    @ResponseBody
    public void PostgresqlAttack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        PostgresqlAttack.connect(param);
    }

    @RequestMapping("/TeradataAttack/case1")
    @ResponseBody
    public void TeradataAttack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        TeradataAttack.connect(param);
    }

}
