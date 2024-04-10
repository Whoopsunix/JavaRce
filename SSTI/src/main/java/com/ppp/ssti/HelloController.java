package com.ppp.ssti;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Map;

@Controller
public class HelloController {

    @Autowired
    private  Configuration con;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/hello")
    public String hello(@RequestBody Map<String,Object> body, Model model) {
        model.addAttribute("name", body.get("name"));
        return "hello";
    }

    @RequestMapping(value = "/template", method =  RequestMethod.POST)
    public String template(@RequestBody Map<String,String> templates) throws IOException {
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        for(String templateKey : templates.keySet()){
            stringLoader.putTemplate(templateKey, templates.get(templateKey));
        }
        con.setTemplateLoader(new MultiTemplateLoader(new TemplateLoader[]{stringLoader,
            con.getTemplateLoader()}));
        return "index";
    }
}