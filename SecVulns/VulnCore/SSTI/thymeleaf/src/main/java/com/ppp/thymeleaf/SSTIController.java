package com.ppp.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Whoopsunix
 */
@Controller
public class SSTIController {
    @RequestMapping("/ssti")
    public String path(@RequestParam String lang) {
        return "user/" + lang + "/welcome"; //template path is tainted
    }



}
