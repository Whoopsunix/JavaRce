复现流程 m1

在当前目录打开，生成 .class 和 .h 文件

```
arch -x86_64 javac -cp . Calc.java -h com/ppp/command/jni/
```

在生成的 com/ppp/command/jni/ 目录下创建 com_ppp_command_jni_Calc.cpp 文件

```
#include <iostream>
#include <stdlib.h>
#include <cstring>
#include <string>
#include "com_ppp_command_jni_Calc.h"

using namespace std;

JNIEXPORT jstring

JNICALL Java_com_ppp_command_jni_Calc_run
        (JNIEnv *env, jclass jclass, jstring str) {

    if (str != NULL) {
        jboolean jsCopy;
        const char *cmd = env->GetStringUTFChars(str, &jsCopy);
        FILE *fd  = popen(cmd, "r");

        if (fd != NULL) {
            string result;
            char buf[128];

            while (fgets(buf, sizeof(buf), fd) != NULL) {
                result +=buf;
            }

            pclose(fd);
            return env->NewStringUTF(result.c_str());
        }

    }

    return NULL;
}
```

编译

```
// mac编译
g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" -shared -o libcmd.jnilib com_ppp_command_jni_Calc.cpp
// m1
arch -x86_64 g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" -shared -o libcmd.jnilib com_ppp_command_jni_Calc.cpp

// linux
g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o libcmd.so com_ppp_command_jni_Calc.cpp

// windows
x86_64-w64-mingw32-g++ -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o cmd.dll com_ppp_command_jni_Calc.cpp


找不到的话就显式指定一下 include 路径  
arch -x86_64 g++ -fPIC \
    -I/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/include \
    -I/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/include/darwin \
    -shared -o libcmd.jnilib com_ppp_command_jni_Calc.cpp
```

