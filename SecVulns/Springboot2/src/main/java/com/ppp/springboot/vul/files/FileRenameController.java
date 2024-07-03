package com.ppp.springboot.vul.files;

import com.ppp.FileRename;
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
@RequestMapping("/file/rename")
public class FileRenameController {

    @RequestMapping("/case1")
    public void case1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String oldFile = FileUtils.getResourcePath() + request.getParameter("oldFile");
        String newFile = FileUtils.getResourcePath() + request.getParameter("newFile");

        System.out.println(oldFile);
        System.out.println(newFile);

        FileRename.rename(oldFile, newFile);

    }

}
