package com.ppp.springboot.vul.ssti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/ssti")
public class ThymeleafController {
    @RequestMapping("/thymeleaf/case1")
    public String path(@RequestParam String lang) {
        return "user/" + lang + "/welcome"; //template path is tainted
    }






}
