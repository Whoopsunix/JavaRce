# tomcat7

报错

```
Caused by: java.lang.NoSuchMethodException: org.apache.catalina.deploy.WebXml addServlet
	at org.apache.tomcat.util.IntrospectionUtils.callMethod1(IntrospectionUtils.java:916)
	at org.apache.tomcat.util.digester.SetNextRule.end(SetNextRule.java:201)
	at org.apache.tomcat.util.digester.Digester.endElement(Digester.java:1046)
```

修改安装目录 conf/context.xml ，添加

```
<Loader delegate="true" />
```

# spring



# jetty

https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-distribution/

idea 插件 https://plugins.jetbrains.com/plugin/18721-jetty

# Resin

下载官方插件



