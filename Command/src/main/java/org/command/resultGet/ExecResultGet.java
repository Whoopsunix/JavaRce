package org.command.resultGet;


import java.io.InputStream;

/**
 * @author Whoopsunix
 * 命令执行结果 INputStream 处理
 */
public class ExecResultGet {

    // normal
    public String normal(InputStream inputStream) throws Exception {
        // InputStream inputStream = Runtime.getRuntime().exec(payload).getInputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            stringBuilder.append(new String(bytes, 0, len));
        }
        return stringBuilder.toString();
    }

    // org.springframework:spring-core
    public static String exec_result_get_springboot(InputStream inputStream) throws Exception {
        return new String(org.springframework.util.StreamUtils.copyToByteArray(inputStream));
    }

    // org.apache.commons:commons-io
    public static String exec_result_get_commons_io(InputStream inputStream) throws Exception {
        return org.apache.commons.io.IOUtils.toString(inputStream);
    }

    // org.apache.commons:commons-lang3
    public static String exec_result_get_commons_lang3(InputStream inputStream) throws Exception {
        return org.apache.commons.lang3.StringUtils.join(inputStream);
    }

}
