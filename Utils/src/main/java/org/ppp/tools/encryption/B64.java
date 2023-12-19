package org.ppp.tools.encryption;

import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author Whoopsunix
 */
public class B64 {
    /**
     * JavaClass 形式 base64加密
     */
    public String encodeJavaClass(Class<?> cls){
        try {
            JavaClass javaClass = Repository.lookupClass(cls);
            System.out.println(Arrays.toString(javaClass.getBytes()));
            String b64Str = encodeStr(javaClass.getBytes());
            System.out.println(b64Str);
            return b64Str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String encodeObj(Object obj) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        String base64str = new String(Base64.getEncoder().encode(baos.toByteArray()));
        System.out.println(base64str);
        return base64str;
    }

    // byte[]
    public String encodeStr(byte[] b) {
        try {
            return Base64.getEncoder().encodeToString(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // file
    public String encodeFile(String filePath) throws Exception {
        InputStream in = new FileInputStream(filePath);
        byte[] data = new byte[in.available()];
        in.read(data);
        return encodeStr(data);
    }

    /**
     * 解密
     */
    // base64解密rt1
    public String decodeStr1(String base64Str) {
        byte[] b = Base64.getDecoder().decode(base64Str);
        return new String(b);
    }

    // base64解密rt2
    public String decodeStr2(String base64Str) {
        try {
            byte[] b = com.sun.org.apache.xml.internal.security.utils.Base64.decode(base64Str);
            return new String(b);
        } catch (Exception e){

        }
        return "";
    }
}
