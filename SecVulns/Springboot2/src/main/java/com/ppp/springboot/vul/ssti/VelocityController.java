package com.ppp.springboot.vul.ssti;

import com.ppp.VelocityAttack;
import com.ppp.jexl.JEXLAttack;
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
@RequestMapping("/ssti")
public class VelocityController {
    @RequestMapping("/Velocity/case1")
    @ResponseBody
    public Object Velocity(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);

        String result = VelocityAttack.eval(body);

        return result;
    }

}
