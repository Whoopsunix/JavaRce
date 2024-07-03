package com.ppp.springboot.vul.code;

import com.ppp.code.GroovyAttack;
import com.ppp.code.ScriptEngineAttack;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/code/Groovy")
public class GroovyController {
    // groovyShell
    @RequestMapping("/case1")
    @ResponseBody
    public Object groovyShell(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);

        GroovyAttack groovyAttack = new GroovyAttack();
        Object result = groovyAttack.groovyShell(body);

        return result;
    }

}
