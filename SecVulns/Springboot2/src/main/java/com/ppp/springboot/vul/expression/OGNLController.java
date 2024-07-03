package com.ppp.springboot.vul.expression;

import com.ppp.ognl.OGNLAttack;
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
@RequestMapping("/expression/OGNL")
public class OGNLController {
    @RequestMapping("/case1")
    @ResponseBody
    public Object OGNLGet(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);

        Object result = OGNLAttack.ognlGetValue(body);

        return result;
    }

    @RequestMapping("/case2")
    @ResponseBody
    public Object OGNLSet(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);

        OGNLAttack.ognlSetValue(body);

        return null;
    }

    @RequestMapping("/case3")
    @ResponseBody
    public Object OGNLIbatisGet(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);

        Object result = OGNLAttack.ognlGetValueIbatis(body);

        return result;
    }

    @RequestMapping("/case4")
    @ResponseBody
    public Object OGNLIbatisSet(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = URLDecoder.decode(requestBody);
        System.out.println(body);

        OGNLAttack.ognlSetValueIbatis(body);

        return null;
    }


}
