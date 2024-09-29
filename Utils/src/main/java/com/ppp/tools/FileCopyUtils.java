package com.ppp.tools;



import java.io.*;
import java.nio.file.Files;

/**
 * @author Whoopsunix
 */
public class FileCopyUtils {
    public static final int BUFFER_SIZE = 4096;

    public FileCopyUtils() {
    }

    public static int copy(File in, File out) throws IOException {
        return copy(Files.newInputStream(in.toPath()), Files.newOutputStream(out.toPath()));
    }

    public static void copy(byte[] in, File out) throws IOException {
        copy((InputStream) (new ByteArrayInputStream(in)), (OutputStream) Files.newOutputStream(out.toPath()));
    }

    public static byte[] copyToByteArray(File in) throws IOException {
        return copyToByteArray(Files.newInputStream(in.toPath()));
    }

    public static int copy(InputStream in, OutputStream out) throws IOException {
        int var2;
        try {
            var2 = StreamUtils.copy(in, out);
        } finally {
            close(in);
            close(out);
        }

        return var2;
    }

    public static void copy(byte[] in, OutputStream out) throws IOException {
        try {
            out.write(in);
        } finally {
            close(out);
        }

    }

    public static byte[] copyToByteArray( InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            copy(in, out);
            return out.toByteArray();
        }
    }

    public static int copy(Reader in, Writer out) throws IOException {
        try {
            int charCount = 0;

            int charsRead;
            for (char[] buffer = new char[4096]; (charsRead = in.read(buffer)) != -1; charCount += charsRead) {
                out.write(buffer, 0, charsRead);
            }

            out.flush();
            int var5 = charCount;
            return var5;
        } finally {
            close(in);
            close(out);
        }
    }

    public static void copy(String in, Writer out) throws IOException {
        try {
            out.write(in);
        } finally {
            close(out);
        }

    }

    public static String copyToString( Reader in) throws IOException {
        if (in == null) {
            return "";
        } else {
            StringWriter out = new StringWriter(4096);
            copy((Reader) in, (Writer) out);
            return out.toString();
        }
    }

    private static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException var2) {
        }

    }
}
