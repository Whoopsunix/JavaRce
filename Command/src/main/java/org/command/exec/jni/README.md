复现流程 m1

1. 生成 .class 和 .h 文件
   arch -x86_64 javac -cp . Calc.java -h com/whoopsunix/vul/exec/jni/
2. 编译

```
// mac编译
g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" -shared -o libcmd.jnilib org_command_exec_jni_Calc.cpp
// m1
g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" -shared -o libcmd.jnilib org_command_exec_jni_Calc.cpp

// linux
g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o libcmd.so org_command_exec_jni_Calc.cpp

// windows
x86_64-w64-mingw32-g++ -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o cmd.dll org_command_exec_jni_Calc.cpp
```


