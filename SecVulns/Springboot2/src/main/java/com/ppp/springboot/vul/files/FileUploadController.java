package com.ppp.springboot.vul.files;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.alibaba.fastjson.util.IOUtils.close;

/**
 * @author Whoopsunix
 *
 */
@Controller
@RequestMapping("/file/upload")
public class FileUploadController {

    @RequestMapping("/case1")
    public void case1(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = file.getOriginalFilename();

        String filePath = FileUtils.getResourcePath() + fileName;
        System.out.println(filePath);
        InputStream fileContent = file.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileContent.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }
    }

    @RequestMapping("/case2")
    public void case2(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = file.getOriginalFilename();

        String filePath = FileUtils.getResourcePath() + fileName;
//        String filePath = FileUtils.getResourcePath() + "2.txt";
        System.out.println(filePath);
        File file1 = new File(filePath);
        file.transferTo(file1);
    }


    public static byte[] getFromInputStream(InputStream in) throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        copy((InputStream)in, (OutputStream)out);
        return out.toByteArray();
    }

    public static int copy(InputStream in, OutputStream out) throws IOException {
        int var2;
        try {
            var2 = copy0(in, out);
        } finally {
            close(in);
            close(out);
        }

        return var2;
    }

    public static int copy0(InputStream in, OutputStream out) throws IOException {
        int byteCount = 0;

        int bytesRead;
        for(byte[] buffer = new byte[4096]; (bytesRead = in.read(buffer)) != -1; byteCount += bytesRead) {
            out.write(buffer, 0, bytesRead);
        }

        out.flush();
        return byteCount;
    }

}
