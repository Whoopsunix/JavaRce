package com.demo.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;

@MultipartConfig
public class BinaryDeSerializerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String cmd = req.getParameter("cmd");
        System.out.println(cmd);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 检查请求是否包含文件上传
            if (ServletFileUpload.isMultipartContent(request)) {
                // 创建文件项目工厂
                FileItemFactory factory = new DiskFileItemFactory();

                // 创建上传处理器
                ServletFileUpload upload = new ServletFileUpload(factory);

                // 解析请求，获取文件项集合
                @SuppressWarnings("unchecked")
                Collection<FileItem> items = upload.parseRequest(request);

                Iterator<FileItem> iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = iterator.next();

                    // 判断是否为普通表单字段还是文件上传字段
                    if (!item.isFormField()) {
                        // 获取上传文件的输入流
                        InputStream inputStream = item.getInputStream();

                        // 使用对象输入流进行反序列化
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        Object deserializedObject = objectInputStream.readObject();
                        objectInputStream.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



