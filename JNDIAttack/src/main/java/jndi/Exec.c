// gcc -shared -fPIC Exec.c -o Exec.dylib
// gcc -shared -fPIC Exec.c -o Exec.so
// gcc -m64 Exec.c -fPIC --shared -o Exec.dll
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

__attribute__ ((__constructor__)) void preload (void){
    system("open -a Calculator");
}