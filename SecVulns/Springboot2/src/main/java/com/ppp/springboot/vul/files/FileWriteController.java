package com.ppp.springboot.vul.files;

import com.ppp.FileWrite;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/file/write")
public class FileWriteController {

    @RequestMapping("/case1")
    @ResponseBody
    public Object case1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_OutputStreamWriter(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case2")
    @ResponseBody
    public Object case2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_FileWriter(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case3")
    @ResponseBody
    public Object case3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_FileWriter_BufferedWriter(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case4")
    @ResponseBody
    public Object case4(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_FileWriter_CharArrayWriter(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }


    @RequestMapping("/case5")
    @ResponseBody
    public Object case5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_FileWriter_PrintWriter(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case6")
    @ResponseBody
    public Object case6(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_PrintWriter_BufferedWriter_FileWriter(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case7")
    @ResponseBody
    public Object case7(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_FileOutputStream(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case8")
    @ResponseBody
    public Object case8(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_PrintStream(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case9")
    @ResponseBody
    public Object case9(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_RandomAccessFile(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/case10")
    @ResponseBody
    public Object case10(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_FileChannel(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/case11")
    @ResponseBody
    public Object case11(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_Files(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * 随机文件名
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/case111")
    @ResponseBody
    public Object case111(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);
            String fileName = Paths.get(filePath).toFile().getName();
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            filePath = filePath.replaceAll(fileName, String.valueOf(System.currentTimeMillis()));

            FileWrite.write_Files(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/case12")
    @ResponseBody
    public Object case12(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_FileUtils(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/case13")
    @ResponseBody
    public Object case13(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("filePath");
            String fileContent = request.getParameter("fileContent");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(fileContent);
            System.out.println(filePath);

            FileWrite.write_FileOutputStream_file(filePath, fileContent);

            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


}
