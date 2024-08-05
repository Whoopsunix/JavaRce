# VulnCore

## [代码执行](Code)

- ScriptEngine
- Groovy

## [命令执行](Command)

> 命令执行方式参考 [javaweb-sec](https://github.com/javaweb-sec/javaweb-sec) 项目复现
>
> - Runtime
> - ProcessBuilder
> - ProcessImpl
> - ProcessImpl & UnixProcess
> - ProcessImpl & UnixProcess by unsafe - Native
> - Thread
> - jni
>
> InputStream 处理
>
> - java.lang.StringBuilder
> - java.io.ByteArrayOutputStream
> - java.util.Scanner
> - java.io.BufferedReader
> - java.io.InputStream.readNBytes > JDK 9
> - org.springframework:spring-core
> - org.apache.commons:commons-io

## [Expression inject](Expression)

> [OGNL](Expression/OGNLAttack)
>
> - 普通执行demo、jsEngine：get、set方式
> - 有sout的回显 (Ps. 通过 Servlet 的回显移到 RceEcho 章节介绍)
>   - 明文
>   - 套一层base64加密
> - 探测用Payload
>   - DNSLOG、HTTPLOG
>   - 延时
>
> 
>
> [EL](Expression/ELAttack)
>
> - Exec 回显
>   - 一句话回显 https://forum.butian.net/share/886
> - jsEngine 回显
> - Scriptlet 标记写法（放在这里对照）
>
> 
>
> [SPEL](Expression/SPELAttack)
>
> - Exec 回显
> - 探测用Payload
>   - DNSLOG、HTTPLOG
>   - 延时
> - 字节码加载
>   - JDK 高版本加载
>
> 
>
> [JxPath](Expression/JxPathAttack)
>
> - Exec
> - js
>
> 
>
> [MVEL](Expression/MVELAttack)
>
> - Exec
>
> 
>
> [JEXL](Expression/JEXLAttack)
>
> - Exec
>
> 
>
> [Aviator](Expression/AviatorAttack)
>
> + Exec
> + BCEL

## [JDBC Attack](JDBCAttack)

JDBC 序列化的知识可以参考这些项目 [JDBC-Attack](https://github.com/su18/JDBC-Attack) 、[pyn3rd blog](https://pyn3rd.github.io/) 、[A New Attack Interface In Java Applications](https://i.blackhat.com/Asia-23/AS-23-Yuanzhen-A-new-attack-interface-in-Java.pdf) 、 [Deserial_Sink_With_JDBC](https://github.com/luelueking/Deserial_Sink_With_JDBC)

> - Mysql
>   - 文件读取
>   - 反序列化
>     - statementInterceptors、detectCustomCollations
> - PostgreSQL
>   - CVE-2022-21724 RCE
>     - AbstractXmlApplicationContext 实现类
>   - 文件写入
>     - loggerLevel / loggerFile
>       - 原始方式写入 EL
>       - 截断方式写入 jsp
> - H2database
>   - RUNSCRIPT 远程sql加载
>   - 代码执行
>     - INIT转义分号
>     - TriggerJS
>     - Groovy
> - IBMDB2
>   - JNDI
> - ModeShape
>   - JNDI
> - Apache Derby
>   - Serialize
> - Sqlite
>   - RCE
> - dameng 达梦
>   - JDNI
> - Oracle
>   - JNDI
> - teradata
>   - JDBC RCE

## [JNDI](JNDIAttack)

> - RMI 分析配置代码
> - JNDI 代码
>   - LocalBeanFactory
>   - LocalJDBC
>   - LocalServiceFactory
>   - LocalXXERCE

## [Serialization](Serialization)

### [Class load](Serialization/ClassLoad)

> - AppClassLoader
> - URLCLassLoader
> - BCEL
> - TransletClassLoader
> - Unsafe
> - ReflectUtils
> - RhinoClassloader
> - ScriptEngineDemo

### [XMLSerialization](Serialization/XMLSerialization)

> [JarBean](Serialization/XMLSerialization/JavaBean)
>
> - 命令执行 Runtime、ProcessBuilder、js
> - 探测用Payload
>   - DNSLOG、SOCKETLOG
>   - 延时
> - JNDI
> - BCEL
> - RemoteJar
>
> 
>
> XStream
>
> 主要为 CVE 不具体展开，<= 1.4.17 的生成集成在 yso 项目中

### [ConstructorEXP](Serialization/ConstructorEXP)

通过构造方法触发RCE

- xml

### [Snakeyaml](Serialization/SnakeyamlDemo)

> - ScriptEngineManager
> - c3p0



## [文件操作](FilesOperations)

可用的文件读写方法，即 Java 数据流的各种操作方法。上传、删除、路径遍历等。



## [XXE](XXE)

测试 JDK 原生的 XXE Demo 时最好将 pom 引入的依赖注释掉，idea 调试时容易出问题进不到想要的 hook 点。提供的 demo 都具备回显



## [SSTI](SSTI)

- freeMarker
- thymeleaf
- Velocity



## [RceEcho & MemShell](MemShellAndRceEcho)

减少重复代码，目前已移到 [PPPYSO](https://github.com/Whoopsunix/PPPYSO) 项目中更新。

命令执行回显目前是通过 [java-object-searcher](https://github.com/c0ny1/java-object-searcher) 工具写的，版本适配还没做，之后再优化，本项目主要给出反序列化 demo，jsp 的例子可以参考 [Java-Rce-Echo](https://github.com/feihong-cs/Java-Rce-Echo)。

对于内存马来说，请求处理接口 Servlet、Filter、Listener、Value 之类的请求处理接口都是通用的，变的其实是获取不同组件上下文的方式，因此可以将代码抽象为 注入器+功能实现 两部分来实现内存马，见工具化的代码。

反序列化的测试可以直接用 Rest Client [MemShell.http](JavaClass.http) 发包，比较方便。

### Tomcat

内存马这部分知识点推荐看 [beichen 师傅的内存马Demo](https://github.com/BeichenDream/GodzillaMemoryShellProject) 写的很好，用到了动态代理的方式实现功能，很好的兼容了 javax 和 jakarta api 规范。

| Tomcat     |        |          |
| ---------- | ------ | -------- |
| 内存马类型 | Loader | 测试版本 |
| Filter     | Thread | 6 7 8 9  |
|            | JMX    | 7 8 9    |
|            | WebApp | 8 9      |
| Servlet    | Thread | 7 8 9    |
|            | JMX    | 7 8 9    |
|            | WebApp | 8 9      |
| Listener   | Thread | 6-11     |
|            | JMX    | 7 8 9    |
|            | WebApp | 8 9      |
| Executor   | Thread | 8        |
| Valve      | Thread | 8        |
|            |        |          |
| RceEcho    | Thread |          |

### Spring

| Springboot2 |                       |                |
| ----------- | --------------------- | -------------- |
| 内存马类型  | Loader                | 测试版本       |
| Controller  | WebApplicationContext | [2.2.x, 2.7.x] |
|             |                       |                |
| RceEcho     | WebApplicationContext | [2.2.x, 2.7.x] |

### Jetty

| Jetty   |        |                                  |
| ------- | ------ | -------------------------------- |
| RceEcho | Thread | 7.x、8.x、9.x、10.x 、11.x全版本 |

### Undertow

WildFly 默认容器用的 Undertow

| Undertow   |        |              |
| ---------- | ------ | ------------ |
| 内存马类型 | Loader | 测试版本     |
| Listener   | Thread | 2.2.25.Final |
| Filter     | Thread | 2.2.25.Final |
| Servlet    | Thread | 2.2.25.Final |
|            |        |              |
| RceEcho    | Thread | 2.2.25.Final |

### Resin

| Resin      |        |                  |
| ---------- | ------ | ---------------- |
| 内存马类型 | Loader | 测试版本         |
| Listener   | Thread | [4.0.52, 4.0.66] |
| Servlet    | Thread | [4.0.52, 4.0.66] |
| Filter     | Thread | [4.0.52, 4.0.66] |
|            |        |                  |
| RceEcho    | Thread | [4.0.52, 4.0.66] |

### OSEcho

- windows
- linux

# Stats

![Alt](https://repobeats.axiom.co/api/embed/818a4d2c0d1562eec751b2637b825b3b0d2cf0e3.svg "Repobeats analytics image")

[//]: # ([![Stargazers over time]&#40;https://starchart.cc/Whoopsunix/JavaRce.svg&#41;]&#40;https://starchart.cc/Whoopsunix/JavaRce&#41;)
