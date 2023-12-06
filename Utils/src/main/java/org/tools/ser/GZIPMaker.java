package org.tools.ser;

import javassist.ClassPool;
import javassist.CtClass;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Whoopsunix
 */
public class GZIPMaker {

    public static void gzipMaker(String name) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get(name);
        byte[] classBytes = ctClass.toBytecode();
        System.out.println("original");
        System.out.println(classBytes.length);
        System.out.println(Arrays.toString(classBytes));

        System.out.println("gzip");
        byte[] gzipBytes = compress(classBytes);
        System.out.println(gzipBytes.length);
        String gzipB64Str = Base64.getEncoder().encodeToString(gzipBytes);
        System.out.println(gzipB64Str);

    }

    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos)) {
            gzipOutputStream.write(data);
        }
        return baos.toByteArray();
    }

    public static byte[] decompress(byte[] compressedData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(bais)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        }
    }
}
