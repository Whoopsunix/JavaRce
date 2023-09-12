# JavaRceDemo

By. Whoopsunix

# 0x00 do what?

🚀 记录贴 对照实战场景梳理较通用的 Java Rce 相关漏洞的利用方式或知识点

🚩 对于实际环境遇到过的组件如有必要会针对可利用版本进行一个梳理 慢更

🚧 长期项目 不定期学习后更新......

## 目录

- [0x01 命令执行](#0x01-command)
    - 执行Demo，java jsp
    - 执行结果输出（InputStream 处理Demo）
- [0x02 表达式注入](#0x02-expression-inject)
    - [OGNL](#ognl)
    - [EL](#el)
    - [SPEL](#spel)
- [0x03 JDBC Attack](#0x03-jdbc-attack)
    - [Mysql](#mysql)
    - [PostgreSQL](#postgresql)
    - [H2database](#h2database)
    - [IBM DB2](#ibmdb2)
    - [ModeShape](#modeshape)
    - [Apache Derby](#apache-derby)
    - [Sqlite](#sqlite)
- [0x04 Serialization](#0x04-serialization)
    - [BCEL](#bcel)
    - [远程Jar加载](#remotejar)
    - [XMLSerialization](#xmlserialization)
        - [JavaBean](#jarbean)
        - [XStream](#xstream)
- [0x05 RceEcho](#0x05-rceecho)
    - [Tomcat](#tomcatecho)
- [0x06 MemShell](#0x06-memshell)
- [鸣谢](#Thanks)

目前涵盖：命令执行及输出、表达式及输出、JDBC

# 0x01 [Command](Command)

## 执行Demo

参考 [javaweb-sec](https://github.com/javaweb-sec/javaweb-sec) 有很详细的例子

- [x] Runtime
- [x] ProcessBuilder
- [x] ProcessImpl
- [x] ProcessImpl & UnixProcess
- [x] ProcessImpl & UnixProcess by unsafe + Native
- [x] Thread
- [x] ScriptEngineManager
- [x] jni

## 执行结果输出（InputStream 处理Demo）

- [x] java.lang.StringBuilder
- [x] java.io.ByteArrayOutputStream
- [x] java.util.Scanner
- [x] java.io.BufferedReader
- [x] java.io.InputStream.readNBytes > JDK 9
- [x] org.springframework:spring-core
- [x] org.apache.commons:commons-io

# 0x02 [Expression inject](Expression)

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

# 0x03 [JDBC Attack](JDBCAttack)

参考 [JDBC-Attack](https://github.com/su18/JDBC-Attack) 有很详细的例子

结合 [java-object-searcher](https://github.com/c0ny1/java-object-searcher) 工具挖掘

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

# 0x04 [Serialization](Serialization)

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

主要为 CVE 不具体展开

# 0x05 [RceEcho](RceEcho)

参考 [Java-Rce-Echo](https://github.com/feihong-cs/Java-Rce-Echo) 有很详细的例子

慢更版本适配

## [TomcatEcho](RceEcho/TomcatEcho)

Version support

- 6.0.53
- 7.0.59、7.0.109
- 8.0.53、8.5.82
- 9.0.65

# 0x06 [MemShell](MemShell)

## [tomcat](MemShell/tomcat)

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
