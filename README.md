# JavaRceDemo

By. Whoopsunix

# 0x00 do what?

🚀 记录贴 对照实战场景梳理较通用的 Java Rce 相关漏洞的利用方式或知识点

🚩 对于实际环境遇到过的组件如有必要会针对可利用版本进行一个梳理 慢更 

🚧 长期项目 不定期学习后更新......

🛰️ 部分利用已经集成在二开 [ysoserial](https://github.com/Whoopsunix/ysoserial) 项目中

🪝 [PPPRASP](https://github.com/Whoopsunix/PPPRASP) 项目中对本项目给出的漏洞实现防护（仅实现关键函数的 HOOK，不作进一步处理）

## 目录

- [0x01 RceEcho](#0x01-rceecho)
    - [Tomcat](#tomcatecho)
    - [Spring](#springecho)
    - [Jetty](#jettyecho)
    - [Undertow](#undertowecho)
    - [Resin](#resinecho)
    - [OS](#osecho)
- [0x02 MemShell](#0x02-memshell)
    - [TomcatMemShell](#tomcatmemshell)
- [0x03 命令执行](#0x03-command)
  - [执行Demo，java jsp](#执行demo)
  - [执行结果输出（InputStream 处理Demo）](#执行结果输出inputstream-处理demo)
- [0x04 表达式注入](#0x04-expression-inject)
  - [OGNL](#ognl)
  - [EL](#el)
  - [SPEL](#spel)
- [0x05 JDBC Attack](#0x05-jdbc-attack)
  - [Mysql](#mysql)
  - [PostgreSQL](#postgresql)
  - [H2database](#h2database)
  - [IBM DB2](#ibmdb2)
  - [ModeShape](#modeshape)
  - [Apache Derby](#apache-derby)
  - [Sqlite](#sqlite)
- [0x06 Serialization](#0x06-serialization)
  - [BCEL](#bcel)
  - [远程Jar加载](#remotejar)
  - [XMLSerialization](#xmlserialization)
    - [JavaBean](#jarbean)
    - [XStream](#xstream)
- [0x07 文件读写 Demo](#0x07-文件读写-demo)
- [鸣谢](#Thanks)

# 0x01 [RceEcho](RceEcho)

结合 [java-object-searcher](https://github.com/c0ny1/java-object-searcher) 工具挖掘命令回显 慢更版本适配

本项目主要给出反序列化 demo，jsp 的例子可以参考 [Java-Rce-Echo](https://github.com/feihong-cs/Java-Rce-Echo)

## [TomcatEcho](RceEcho/TomcatEcho)

Version Test

- 6.0.53
- 7.0.59、7.0.109
- 8.0.53、8.5.82
- 9.0.65

## [SpringEcho](RceEcho/SpringEcho)

Version Test

- spring-boot-starter-web
  - [2.2.x, 2.7.x]

## [JettyEcho](RceEcho/JettyEcho)

Version Test

- 7.x、8.x、9.x、10.x 全版本

## [UndertowEcho](RceEcho/UndertowEcho)

WildFly 默认容器用的 Undertow

Version Test

- spring-boot-starter-undertow
  - 2.7.15

## [ResinEcho](RceEcho/ResinEcho)

Version Test

- [4.0.52, 4.0.66]

## [OSEcho](RceEcho/OSEcho)

- windows
- linux

# 0x02 [MemShell](MemShell)

## [TomcatMemShell](MemShell/TomcatMemShell)

- Filter
  - ContextClassLoader support 8 9
  - JMX support 7 8 9
  - Thread support 6 7 8 9
- Servlet
  - ContextClassLoader support 8 9
  - JMX support 7 8 9
  - Thread support 7 8 9
- Listener
  - ContextClassLoader support 8 9
  - JMX support 7 8 9
  - Thread support 7 8 9

# 0x03 [Command](Command)

## [执行Demo](Command)

参考 [javaweb-sec](https://github.com/javaweb-sec/javaweb-sec) 有很详细的例子

- [x] Runtime
- [x] ProcessBuilder
- [x] ProcessImpl
- [x] ProcessImpl & UnixProcess
- [x] ProcessImpl & UnixProcess by unsafe + Native
- [x] Thread
- [x] ScriptEngine
- [x] jni

## [执行结果输出（InputStream 处理Demo）](Command)

- [x] java.lang.StringBuilder
- [x] java.io.ByteArrayOutputStream
- [x] java.util.Scanner
- [x] java.io.BufferedReader
- [x] java.io.InputStream.readNBytes > JDK 9
- [x] org.springframework:spring-core
- [x] org.apache.commons:commons-io

# 0x04 [Expression inject](Expression)

## [OGNL](Expression/OGNLAttack)

- [x] 普通执行demo、jsEngine：get、set方式
- [x] 有sout的回显 (Ps. 通过 Servlet 的回显移到 RceEcho 章节介绍)
  - 明文
  - 套一层base64加密
- [x] 探测用Payload
  - DNSLOG、HTTPLOG
  - 延时

## [EL](Expression/ELAttack)

- [x] runtime 回显
- [x] jsEngine 回显
- [x] Scriptlet 标记写法（放在这里对照）

## [SPEL](Expression/SPELAttack)

- [x] runtime 回显
- [x] 探测用Payload
  - DNSLOG、HTTPLOG
  - 延时

# 0x05 [JDBC Attack](JDBCAttack)

参考 [JDBC-Attack](https://github.com/su18/JDBC-Attack) 、[pyn3rd blog](https://pyn3rd.github.io/) 有很详细的例子

## [Mysql](JDBCAttack/MysqlAttack)

- [x] 文件读取
- [x] 反序列化
    - statementInterceptors
    - detectCustomCollations

## [PostgreSQL](JDBCAttack/PostgreSQLAttack)

- [x] CVE-2022-21724 RCE
    - AbstractXmlApplicationContext 实现类
- [x] 文件写入
    - loggerLevel / loggerFile
        - 原始方式写入 EL
        - 截断方式写入 jsp

## [H2database](JDBCAttack/H2Attack)

- [x] RUNSCRIPT 远程sql加载
- [x] 代码执行
    - INIT转义分号
    - TriggerJS
    - Groovy

## [IBMDB2](JDBCAttack/IBMDB2Attack)

- [x] JNDI

## [ModeShape](JDBCAttack/ModeShapeAttack)

- [x] JNDI

## [Apache Derby](JDBCAttack/DerbyAttack)

- [x] Serialize

## [Sqlite](JDBCAttack/SqliteAttack)

- [x] RCE

# 0x06 [Serialization](Serialization)

## [BCEL](Serialization/BCELAttack)

- [x] static 触发
- [x] 构造方法触发
- [x] 方法触发

## [RemoteJar](Serialization/AttackJar)

- [x] static 触发
- [x] 构造方法触发
- [x] 方法触发

## [XMLSerialization](Serialization/XMLSerialization)

### [JarBean](Serialization/XMLSerialization/JavaBean)

- [x] 命令执行 Runtime、ProcessBuilder、js
- [x] 探测用Payload
    - DNSLOG、SOCKETLOG
    - 延时
- [x] JNDI
- [x] BCEL
- [x] RemoteJar

### XStream

主要为 CVE 不具体展开，<= 1.4.17 的生成集成在 yso 项目中

# 0x07 [文件读写 Demo](FilesOperations)

可用的文件读写函数

# Thanks

感谢师傅们的研究 带来了很大的帮助 :)

> https://github.com/javaweb-sec/javaweb-sec
>
> https://github.com/yzddmr6/Java-Js-Engine-Payloads
>
> https://github.com/su18/JDBC-Attack
>
> https://pyn3rd.github.io/
>
> https://forum.butian.net/share/886
>
> https://github.com/woodpecker-appstore
>
> https://www.yulegeyu.com/archives/
>
> https://github.com/c0ny1/java-object-searcher
>
> https://github.com/feihong-cs/Java-Rce-Echo
> 
> https://flowerwind.github.io/2021/10/11/tomcat6%E3%80%817%E3%80%818%E3%80%819%E5%86%85%E5%AD%98%E9%A9%AC/

[//]: # ([![Stargazers over time]&#40;https://starchart.cc/Whoopsunix/JavaRce.svg&#41;]&#40;https://starchart.cc/Whoopsunix/JavaRce&#41;)

