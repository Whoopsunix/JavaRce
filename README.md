# JavaRceDemo

By. Whoopsunix

# 0x00 do what?

Java 能获取到权限的 Demo，目前涵盖命令执行、表达式、内存马、JDBC、反序列化、工具类

# 命令执行

参考 [javaweb-sec](https://github.com/javaweb-sec/javaweb-sec) 很详细

## 执行方式

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

# 表达式注入

## OGNL

## SPEL



# JDBC相关

# 内存马

# 参考

> https://github.com/javaweb-sec/javaweb-sec
> 
> https://github.com/yzddmr6/Java-Js-Engine-Payloads
> 
