package org.example;

import java.lang.reflect.Constructor;

/**
 * @author Whoopsunix
 */
public class UnsafeDemo {
    public static void main(String[] args) throws Exception {
//        String b64Str = new B64().encodeJavaClass(Exec.class);
        String b64Str = "yv66vgAAADQAMgoACwAZCQAaABsIABwKAB0AHgoAHwAgCAAhCgAfACIHACMIACQHACUHACYBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAEkxvcmcvZXhhbXBsZS9FeGVjOwEADVN0YWNrTWFwVGFibGUHACUHACMBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAAlFeGVjLmphdmEMAAwADQcAJwwAKAApAQAERXhlYwcAKgwAKwAsBwAtDAAuAC8BABZvcGVuIC1hIENhbGN1bGF0b3IuYXBwDAAwADEBABNqYXZhL2xhbmcvRXhjZXB0aW9uAQALc3RhdGljIEV4ZWMBABBvcmcvZXhhbXBsZS9FeGVjAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsAIQAKAAsAAAAAAAIAAQAMAA0AAQAOAAAAdgACAAIAAAAaKrcAAbIAAhIDtgAEuAAFEga2AAdXpwAETLEAAQAEABUAGAAIAAMADwAAABoABgAAAAcABAAJAAwACgAVAAwAGAALABkADQAQAAAADAABAAAAGgARABIAAAATAAAAEAAC/wAYAAEHABQAAQcAFQAACAAWAA0AAQAOAAAAWwACAAEAAAAWsgACEgm2AAS4AAUSBrYAB1enAARLsQABAAAAEQAUAAgAAwAPAAAAFgAFAAAAEQAIABIAEQAUABQAEwAVABUAEAAAAAIAAAATAAAABwACVAcAFQAAAQAXAAAAAgAY";
        defineClass_unsafe(b64Str, "org.example.Exec");
    }

    // sun.misc.Unsafe
    public static void defineClass_unsafe(String calcBase64, String className) throws Exception {
//        sun.misc.Unsafe unsafe = sun.misc.Unsafe.getUnsafe();
        Class cls = Class.forName("sun.misc.Unsafe");
        Constructor constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) constructor.newInstance();

        byte[] bytes = java.util.Base64.getDecoder().decode(calcBase64);
        System.out.println(bytes.length);
        Class cls1 = unsafe.defineClass(className, bytes, 0, bytes.length, null, null);
        cls1.newInstance();
    }
}
