package org.example.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

@WebServlet("/base64")
public class Base64DeSerializerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String cmd = req.getParameter("cmd");
        System.out.println(cmd);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 反序列化
            String base64Str = req.getParameter("base64Str");
            byte[] bytes = Base64.getDecoder().decode(base64Str);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



