package com.ppp.springboot.vul.expression;

import com.ppp.jxpath.JxPathAttack;
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
@RequestMapping("/expression/JxPath")
public class JxPathController {
    @RequestMapping("/case1")
    @ResponseBody
    public Object JxPath(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);

        Object result = JxPathAttack.eval(body);

        return result;
    }


}
