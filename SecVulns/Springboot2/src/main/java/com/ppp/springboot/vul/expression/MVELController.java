package com.ppp.springboot.vul.expression;

import com.ppp.mvel.MVELAttack;
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
@RequestMapping("/expression/MVEL")
public class MVELController {
    @RequestMapping("/case1")
    @ResponseBody
    public Object MVEL(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);

        Object result = MVELAttack.eval(body);

        return result;
    }


}
