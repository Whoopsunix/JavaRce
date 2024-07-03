package com.ppp.springboot.vul.files;

import com.ppp.FileDelete;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Whoopsunix
 *
 */
@Controller
@RequestMapping("/file/delete")
public class FileDeleteController {

    @RequestMapping("/case1")
    public void case1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = FileUtils.getResourcePath() + request.getParameter("filePath");
        System.out.println(filePath);

        FileDelete.delete(filePath);
    }

}
