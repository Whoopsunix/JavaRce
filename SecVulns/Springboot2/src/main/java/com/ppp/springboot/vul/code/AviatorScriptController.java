package com.ppp.springboot.vul.code;

import com.ppp.code.AviatorScriptAttack;
import com.ppp.code.GroovyAttack;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * @author guchangan1
 */

@Controller
@RequestMapping("/code/Aviator")
public class AviatorScriptController {

    // AviatorEvaluator
    @RequestMapping("/case1")
    @ResponseBody
    public Object aviatorEvaluator(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);
        AviatorScriptAttack aviatorScriptAttack = new AviatorScriptAttack();
        Object result = aviatorScriptAttack.aviatorEvaluator(body);

        return result;
    }
}
