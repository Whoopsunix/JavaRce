package org.tools;

import java.io.ObjectStreamClass;

/**
 * @author Whoopsunix
 */
public class SerialVersionUIDDemo {
    public static void main(String[] args) {

    }

    public static long getSerialVersionUID(Class<?> clazz){
        ObjectStreamClass objectStreamClass = ObjectStreamClass.lookup(clazz);

        // 获取默认生成的 serialVersionUID 值
        long serialVersionUID = objectStreamClass.getSerialVersionUID();

        System.out.println("Default serialVersionUID for " + clazz.getName() + ": " + serialVersionUID);
        return serialVersionUID;
    }
}
