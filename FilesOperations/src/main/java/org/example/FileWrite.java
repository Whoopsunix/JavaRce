package org.example;

import java.io.*;

/**
 * @author Whoopsunix
 */
public class FileWrite {
    /**
     * java.io.FileWriter
     */
    // java.io.OutputStreamWriter
    public void write_OutputStreamWriter(String filePath, String context) throws Exception {
        OutputStream outputStream = new FileOutputStream(filePath);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write(context);
        outputStreamWriter.close();
    }

    // java.io.FileWriter
    public void write_FileWriter(String filePath, String context) throws Exception {
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(context);
        fileWriter.close();
    }

    // java.io.BufferedWriter
    public void write_FileWriter_BufferedWriter(String filePath, String context) throws Exception {
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(context);
        bufferedWriter.close();
        fileWriter.close();
    }

    // java.io.CharArrayWriter
    public void write_FileWriter_CharArrayWriter(String filePath, String context) throws Exception {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        charArrayWriter.write(context);
        FileWriter fileWriter = new FileWriter(filePath);
        charArrayWriter.writeTo(fileWriter);
        charArrayWriter.close();
        fileWriter.close();
    }

    // java.io.PrintWriter
    public void write_FileWriter_PrintWriter(String filePath, String context) throws Exception {
        PrintWriter printWriter = new PrintWriter(new FileWriter(filePath));
        printWriter.println(context);
        printWriter.close();
    }


    /**
     * java.io.PrintWriter
     */
    // java.io.BufferedWriter  java.io.FileWriter
    public void write_PrintWriter_BufferedWriter_FileWriter(String filePath, String context) throws Exception {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
        out.println(context);
        out.close();
    }

    // java.io.FileOutputStream.write()
    public void write_FileOutputStream(String filePath, String context) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(context.getBytes());
        fileOutputStream.close();
    }

    // java.io.PrintStream
    public void write_PrintStream(String filePath, String context) throws Exception {
        PrintStream printStream = new PrintStream(filePath);
        printStream.print(context);
        printStream.close();
    }

    // java.io.RandomAccessFile
    public void write_RandomAccessFile(String filePath, String context) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
        randomAccessFile.write(context.getBytes());
        randomAccessFile.close();
    }

    // java.nio.channels.FileChannel
    public void write_FileChannel(String filePath, String context) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        java.nio.channels.FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.write(java.nio.ByteBuffer.wrap(context.getBytes()));
        fileChannel.close();
        fileOutputStream.close();
    }

    // java.nio.file.Files
    public void write_Files(String filePath, String context) throws Exception {
        java.nio.file.Files.write(java.nio.file.Paths.get(filePath), context.getBytes());
    }

    // org.apache.commons.io.FileUtils
    public void write_FileUtils(String filePath, String context) throws Exception {
        org.apache.commons.io.FileUtils.writeStringToFile(new File(filePath), context, "UTF-8");
    }
}
