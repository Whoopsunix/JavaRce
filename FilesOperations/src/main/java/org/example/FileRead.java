package org.example;

import java.io.*;
import java.util.Scanner;

/**
 * @author Whoopsunix
 */
public class FileRead {
    public static void main(String[] args) throws Exception {
        String s = read_InputStreamReader_BufferedInputStream("/etc/passwd");
        System.out.println(s);
    }

    /**
     * abstract java.io.Reader
     * java.io.InputStreamReader
     * 自带的 read()
     */
    public String read_InputStreamReader(String filePath) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(filePath);

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        int character;
        StringBuilder content = new StringBuilder();
        while ((character = inputStreamReader.read()) != -1) {
            // 处理读取到的字符
            char charValue = (char) character;
            content.append(charValue);
        }
        inputStreamReader.close();

        return content.toString();
    }

    /**
     * abstract java.io.Reader
     * java.io.InputStreamReader
     * java.io.BufferedReader
     */
    public String read_InputStreamReader_BufferedReader(String filePath) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        bufferedReader.close();
        return content.toString();
    }

    public String read_InputStreamReader_CharArrayReader(String filePath) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        char[] charArray = new char[1024];
        int bytesRead = inputStreamReader.read(charArray);
        CharArrayReader charArrayReader = new CharArrayReader(charArray, 0, bytesRead);

        int character;
        StringBuilder content = new StringBuilder();
        while ((character = charArrayReader.read()) != -1) {
            // 处理读取到的字符
            content.append((char) character);
        }
        charArrayReader.close();
        return content.toString();
    }


    /**
     * 对照组
     */
    public String read_InputStreamReader_text(String str) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        bufferedReader.close();
        return content.toString();
    }

    public static String read_InputStreamReader_BufferedInputStream(String filePath) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        byte[] buf = new byte[1024];
        int len;
        OutputStream outputStream = new ByteArrayOutputStream();
        while ((len = bufferedInputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, len);
        }
        outputStream.close();
        bufferedInputStream.close();
        return outputStream.toString();
    }

    // java.io.FileInputStream
    public String read_FileInputStream(String filePath) {
        String content = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] data = new byte[fileInputStream.available()];
            fileInputStream.read(data);
            fileInputStream.close();
            content = new String(data, "UTF-8");
            System.out.println(content);
        } catch (Exception e) {

        }
        return content;
    }

    /**
     * FileReader
     */
    // java.io.BufferedReader.readLine()
    public String read_FileReader_bufferedReader1(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        FileReader reader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        bufferedReader.close();

        return content.toString();
    }

    // java.io.BufferedReader.read()
    public String read_FileReader_bufferedReader2(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        FileReader reader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(reader);
        int character;
        while ((character = bufferedReader.read()) != -1) {
            // 处理读取到的字符
            char charValue = (char) character;
            content.append(charValue);
        }
        bufferedReader.close();

        return content.toString();
    }

    // java.io.FileReader.read()
    // 不套其他 直接通过 FileReader 读取
    public String read_FileReader(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        FileReader reader = new FileReader(filePath);
        int character;
        while ((character = reader.read()) != -1) {
            // 处理读取到的字符
            char charValue = (char) character;
            content.append(charValue);
        }
        reader.close();

        return content.toString();
    }

    // java.io.LineNumberReader.read()
    public String read_FileReader_LineNumberReader(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        FileReader reader = new FileReader(filePath);
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String line;
        while ((line = lineNumberReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        lineNumberReader.close();

        return content.toString();
    }

    // java.io.CharArrayReader.read()
    public String read_FileReader_CharArrayReader(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();

        FileReader reader = new FileReader(filePath);
        char[] charArray = new char[1024];
        int bytesRead = reader.read(charArray);
        CharArrayReader charArrayReader = new CharArrayReader(charArray, 0, bytesRead);

        int character;
        while ((character = charArrayReader.read()) != -1) {
            // 处理读取到的字符
            char charValue = (char) character;
            content.append(charValue);
        }

        return content.toString();
    }

    // java.io.PushbackReader
    public String read_PushbackReader(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        FileReader reader = new FileReader(filePath);
        PushbackReader pushbackReader = new PushbackReader(reader);
        int character;
        while ((character = pushbackReader.read()) != -1) {
            // 处理读取到的字符
            char charValue = (char) character;
            content.append(charValue);
        }
        pushbackReader.close();

        return content.toString();
    }

    /**
     * Files
     */
    // java.nio.file.Files.readAllBytes()
    public String read_Files_readAllBytes(String filePath) throws Exception {
        byte[] data = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath));
        return new String(data, "UTF-8");
    }

    // java.nio.file.Files.readAllLines()
    public String read_Files_readAllLines(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        java.util.List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(filePath));
        for (String line : lines) {
            content.append(line).append("\n");
        }
        return content.toString();
    }

    /**
     * Scanner
     */
    // java.util.Scanner
    public String read_Scanner_File(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNextLine()) {
            content.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return content.toString();
    }

    // java.util.Scanner
    public String read_Scanner_Path(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(java.nio.file.Paths.get(filePath));
        while (scanner.hasNextLine()) {
            content.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return content.toString();
    }

    /**
     * java.io.RandomAccessFile
     * readLine()
     */
    public String read_RandomAccessFile_readLine(String filePath) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
        StringBuilder content = new StringBuilder();
        String line;
        // read()
        while ((line = randomAccessFile.readLine()) != null) {
            content.append(line).append("\n");
        }

        randomAccessFile.close();
        return content.toString();
    }

    /**
     * java.io.RandomAccessFile
     * read()
     */
    public String read_RandomAccessFile_read(String filePath) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
        StringBuilder content = new StringBuilder();
        int character;
        // read()
        while ((character = randomAccessFile.read()) != -1) {
            // 处理读取到的字符
            char charValue = (char) character;
            content.append(charValue);
        }

        randomAccessFile.close();
        return content.toString();
    }

    // java.nio.channels.FileChannel
    public String read_FileChannel(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        FileInputStream fileInputStream = new FileInputStream(filePath);
        java.nio.channels.FileChannel fileChannel = fileInputStream.getChannel();
        java.nio.ByteBuffer byteBuffer = java.nio.ByteBuffer.allocate(1024);
        while (fileChannel.read(byteBuffer) > 0) {
            byteBuffer.flip();
            content.append(new String(byteBuffer.array(), 0, byteBuffer.limit()));
            byteBuffer.clear();
        }
        fileChannel.close();
        fileInputStream.close();
        return content.toString();
    }

    // java.nio.channels.FileChannel.open
    public String read_FileChannel_open(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        java.nio.channels.FileChannel fileChannel = java.nio.channels.FileChannel.open(java.nio.file.Paths.get(filePath));
        java.nio.ByteBuffer byteBuffer = java.nio.ByteBuffer.allocate(1024);
        while (fileChannel.read(byteBuffer) > 0) {
            byteBuffer.flip();
            content.append(new String(byteBuffer.array(), 0, byteBuffer.limit()));
            byteBuffer.clear();
        }
        fileChannel.close();
        return content.toString();
    }

    /**
     * org.apache.commons.io.FileUtils
     */
    public String read_FileUtils(String filePath) throws Exception {
        return org.apache.commons.io.FileUtils.readFileToString(new File(filePath), "UTF-8");
    }
}
