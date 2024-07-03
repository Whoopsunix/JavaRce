package com.ppp.springboot.vul.files;

import com.ppp.FileRead;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Whoopsunix
 */
@Controller
@RequestMapping("/file/read")
public class FileReadController {
    @RequestMapping("/case1")
    @ResponseBody
    public String case1(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_InputStreamReader(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case2")
    @ResponseBody
    public String case2(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_InputStreamReader_BufferedReader(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case3")
    @ResponseBody
    public String case3(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_InputStreamReader_CharArrayReader(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case4")
    @ResponseBody
    public String case4(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_FileInputStream(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case5")
    @ResponseBody
    public String case5(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_FileReader_bufferedReader1(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case6")
    @ResponseBody
    public String case6(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_FileReader_bufferedReader2(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case7")
    @ResponseBody
    public String case7(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_FileReader(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case8")
    @ResponseBody
    public String case8(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_FileReader_LineNumberReader(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case9")
    @ResponseBody
    public String case9(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_FileReader_CharArrayReader(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case10")
    @ResponseBody
    public String case10(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_PushbackReader(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case11")
    @ResponseBody
    public String case11(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_Files_readAllBytes(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case12")
    @ResponseBody
    public String case12(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_Files_readAllLines(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case13")
    @ResponseBody
    public String case13(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_Scanner_File(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case14")
    @ResponseBody
    public String case14(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_Scanner_Path(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case15")
    @ResponseBody
    public String case15(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_RandomAccessFile_readLine(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case16")
    @ResponseBody
    public String case16(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_RandomAccessFile_read(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/case17")
    @ResponseBody
    public String case17(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_FileChannel(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/case18")
    @ResponseBody
    public String case18(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_FileChannel_open(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/case19")
    @ResponseBody
    public String case19(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_FileUtils(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/case20")
    @ResponseBody
    public String case20(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_FileInputStream_BufferedInputStream(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/case21")
    @ResponseBody
    public String case21(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            filePath = FileUtils.getResourcePath() + filePath;

            System.out.println(filePath);

            String fileContent = FileRead.read_IOUtils(filePath);
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
