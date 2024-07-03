package com.ppp.springboot.vul.files;


import com.ppp.FileDirectory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Whoopsunix
 *
 */
@Controller
@RequestMapping("/file/directory")
public class FileDirectoryController {

    @RequestMapping("/case1")
    @ResponseBody
    public Object case1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = FileUtils.getResourcePath() + request.getParameter("filePath");
        System.out.println(filePath);

        String[] dirs = FileDirectory.listFiles(filePath);
        return dirs;
    }

    @RequestMapping("/case2")
    @ResponseBody
    public Object case2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = FileUtils.getResourcePath() + request.getParameter("filePath");
        System.out.println(filePath);

        String[] dirs = FileDirectory.list(filePath);
        return dirs;
    }

}
