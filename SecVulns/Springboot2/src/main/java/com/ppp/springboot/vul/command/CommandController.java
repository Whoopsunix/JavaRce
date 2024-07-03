package com.ppp.springboot.vul.command;

import com.ppp.command.*;
import com.ppp.command.jni.JniCmdDemo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/command")
public class CommandController {
    @RequestMapping("/Runtime/case1")
    @ResponseBody
    public Object Runtime(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        InputStream inputStream = RuntimeDemo.exec(param);
        Object result = new ExecResultMod().stringBuilder(inputStream);
        return result;
    }

    @RequestMapping("/ProcessBuilder/case1")
    @ResponseBody
    public Object ProcessBuilder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        InputStream inputStream = ProcessBuilderDemo.exec(param);
        Object result = new ExecResultMod().stringBuilder(inputStream);
        return result;
    }

    @RequestMapping("/ProcessImpl/case1")
    @ResponseBody
    public Object ProcessImpl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        InputStream inputStream = ProcessImplDemo.exec(param);
        Object result = new ExecResultMod().stringBuilder(inputStream);
        return result;
    }

    @RequestMapping("/ProcessImplUnixProcess/case1")
    @ResponseBody
    public Object ProcessImplUnixProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        InputStream inputStream = ProcessImplUnixProcess.exec(param);
        Object result = new ExecResultMod().stringBuilder(inputStream);
        return result;
    }

    @RequestMapping("/ProcessImplUnixProcessByUnsafeNative/case1")
    @ResponseBody
    public Object ProcessImplUnixProcessByUnsafeNative(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        InputStream inputStream = ProcessImplUnixProcessByUnsafeNative.exec(param);
        Object result = new ExecResultMod().stringBuilder(inputStream);
        return result;
    }

    @RequestMapping("/Thread/case1")
    @ResponseBody
    public Object Thread(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        System.out.println(param);

        InputStream inputStream = ThreadDemo.exec(param);
        Object result = new ExecResultMod().stringBuilder(inputStream);
        return result;
    }

}
