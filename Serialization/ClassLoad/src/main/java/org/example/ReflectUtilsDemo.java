package org.example;

/**
 * @author Whoopsunix
 */
public class ReflectUtilsDemo {
    public static void main(String[] args) throws Exception{
//        String b64Str = new B64().encodeJavaClass(Exec.class);
        String b64Str = "yv66vgAAADQAMgoACwAZCQAaABsIABwKAB0AHgoAHwAgCAAhCgAfACIHACMIACQHACUHACYBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAEkxvcmcvZXhhbXBsZS9FeGVjOwEADVN0YWNrTWFwVGFibGUHACUHACMBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAAlFeGVjLmphdmEMAAwADQcAJwwAKAApAQAERXhlYwcAKgwAKwAsBwAtDAAuAC8BABZvcGVuIC1hIENhbGN1bGF0b3IuYXBwDAAwADEBABNqYXZhL2xhbmcvRXhjZXB0aW9uAQALc3RhdGljIEV4ZWMBABBvcmcvZXhhbXBsZS9FeGVjAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsAIQAKAAsAAAAAAAIAAQAMAA0AAQAOAAAAdgACAAIAAAAaKrcAAbIAAhIDtgAEuAAFEga2AAdXpwAETLEAAQAEABUAGAAIAAMADwAAABoABgAAAAcABAAJAAwACgAVAAwAGAALABkADQAQAAAADAABAAAAGgARABIAAAATAAAAEAAC/wAYAAEHABQAAQcAFQAACAAWAA0AAQAOAAAAWwACAAEAAAAWsgACEgm2AAS4AAUSBrYAB1enAARLsQABAAAAEQAUAAgAAwAPAAAAFgAFAAAAEQAIABIAEQAUABQAEwAVABUAEAAAAAIAAAATAAAABwACVAcAFQAAAQAXAAAAAgAY";
        defineClass_cglib(b64Str, "org.example.Exec");
    }

    public static void defineClass_cglib(String calcBase64, String className) throws Exception {
        org.springframework.cglib.core.ReflectUtils.defineClass(className,
                java.util.Base64.getDecoder().decode(calcBase64)
                , ClassLoader.getSystemClassLoader());
    }
}
