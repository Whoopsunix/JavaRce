package com.example.undertow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

/**
 * @author Whoopsunix
 */
@Controller
public class BinaryController {
    @RequestMapping("/binary")
    protected void binary(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp) throws Exception{
        InputStream fileContent = file.getInputStream();
        byte[] bytes = new byte[fileContent.available()];
        fileContent.read(bytes);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        objectInputStream.readObject();
    }
}
