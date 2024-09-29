package com.ppp;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Whoopsunix
 */
public class XPathInject {
    public static void main(String[] args) throws Exception{
//        List<Object> result = select("admin", "123456");
//        System.out.println(result);
        List<Object> result = select(1, "' or 1=1 or '", "pass");
        System.out.println(result);
    }

    public static List<Object> select(Integer id, String username, String password) throws Exception{
        //从本地读取xml文件并解析为dom节点
        SAXReader saxReader = new SAXReader();
        String realPath = Thread.currentThread().getContextClassLoader().getResource("xpath.xml").getPath();
//        System.out.println(realPath);

        FileInputStream fileInputStream = new FileInputStream(realPath);
        Document doc = saxReader.read(fileInputStream);

        String xpath = "/root/users/user[username='" + username + "' and password='" + password + "']";
        System.out.println(xpath);

        List<Object> result = new ArrayList();

        List<Node> selectNodes = doc.selectNodes(xpath);
        if (!selectNodes.isEmpty()) {
            // 用户存在，输出相关信息
            for (Node userNode : selectNodes) {
                result.add(new Users(new Integer(userNode.selectSingleNode("id").getText()), userNode.selectSingleNode("username").getText(), userNode.selectSingleNode("password").getText()));

//                System.out.println("Username: " + userNode.selectSingleNode("username").getText());
//                System.out.println("Password: " + userNode.selectSingleNode("password").getText());
                // 添加其他属性输出...
            }
        }
        return result;
    }
}
