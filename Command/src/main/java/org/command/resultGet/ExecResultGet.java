package org.command.resultGet;


import com.google.common.io.CharStreams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Whoopsunix
 * 命令执行结果 INputStream 处理
 */
public class ExecResultGet {

    // java.lang.StringBuilder
    public String stringBuilder(InputStream inputStream) throws Exception {
        byte[] bytes = new byte[1024];
        int len = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            stringBuilder.append(new String(bytes, 0, len));
        }
        return stringBuilder.toString();
    }

    // java.io.ByteArrayOutputStream
    public String byteArrayOutputStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }

    // java.util.Scanner
    public String scanner(InputStream inputStream) {
//        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
//        return scanner.hasNext() ? scanner.next() : "";
        return new Scanner(inputStream).useDelimiter("\\A").next();
    }

    // java.io.BufferedReader
    public String bufferedReader(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    // java.io.BufferedReader 2
    public String bufferedReader2(InputStream inputStream) throws Exception {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    // java.io.InputStream.readNBytes > JDK 9
//    public String readNBytes(InputStream inputStream) throws IOException {
//        byte[] bytes = inputStream.readNBytes(1024);
//        return new String(bytes, "UTF-8");
//    }

    // org.apache.commons:commons-io
    public String commons_io(InputStream inputStream) throws Exception {
        return org.apache.commons.io.IOUtils.toString(inputStream);
    }

    // org.springframework:spring-core
    public String spring_core(InputStream inputStream) throws Exception {
        return new String(org.springframework.util.StreamUtils.copyToByteArray(inputStream));
    }

    // com.google.common.io:guava
    public String guava(InputStream inputStream) throws Exception {
        return CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
    }

}
