# JavaRceDemo

By. Whoopsunix

# 0x00 do what?

对照实战场景梳理 Java Rce 相关漏洞进一步利用方式

## 目录

- [命令执行](#0x01-command)
    - 命令执行Demo
    - 执行结果输出（InputStream 处理Demo）
- [表达式注入](#0x02-expression-inject)
    - [OGNL](#ognl)
        - get、set执行，sout输出时的回显
    - [SPEL](#spel)
- [JDBC Attack](#0x03-jdbc-attack)
    - mysql

目前涵盖：命令执行及输出、表达式及输出、内存马、JDBC、反序列化、工具类

# 0x01 [Command](Command)

参考 [javaweb-sec](https://github.com/javaweb-sec/javaweb-sec) 有很详细的例子

## 执行Demo

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

# 0x02 [expression inject](Expression)

## OGNL

- [x] 普通执行demo：get、set
- [x] 有sout的回显 (Ps. 通过 Servlet 的回显移到 RceEcho 章节介绍)
    - 明文
    - 套一层base64加密

# 0x03 JDBC Attack

## [mysql](JDBCAttack)

- [x] 文件读取
- [x] 反序列化

# 感谢师傅们的研究 带来了很大的帮助 :)

> https://github.com/javaweb-sec/javaweb-sec
>
> https://github.com/yzddmr6/Java-Js-Engine-Payloads
>
> https://github.com/su18/JDBC-Attack
