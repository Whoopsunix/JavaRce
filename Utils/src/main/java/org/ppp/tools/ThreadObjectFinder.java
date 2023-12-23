//package org.ppp.tools;
//
//import java.lang.reflect.Field;
//import java.util.*;
//
///**
// * @author Whoopsunix
// * 在线程中查找 通过指定类实例的对象
// */
//public class ThreadObjectFinder {
//
//    public static void main(String[] args) throws Exception {
//
//    }
//
//    /**
//     * 获取目标对象
//     */
//    public Object getTargetObject(String className) throws Exception {
//        ThreadObjectFinder threadObjectFinder = new ThreadObjectFinder();
//        List<ClassLoader> activeClassLoaders = threadObjectFinder.getActiveClassLoaders();
//
//        Class cls = threadObjectFinder.getTargetClass(className, activeClassLoaders);
//        System.out.println(cls);
//
//        // 死亡区域 已检查过的类
//        HashSet breakObject = new HashSet();
//        breakObject.add(System.identityHashCode(breakObject));
//
//        // 原始类型和包装类都不递归
//        HashSet<String> breakType = new HashSet<>(Arrays.asList(int.class.getName(), short.class.getName(), long.class.getName(), double.class.getName(), byte.class.getName(), float.class.getName(), char.class.getName(), boolean.class.getName(), Integer.class.getName(), Short.class.getName(), Long.class.getName(), Double.class.getName(), Byte.class.getName(), Float.class.getName(), Character.class.getName(), Boolean.class.getName(), String.class.getName()));
//
//        Object object = Thread.currentThread();
//
////        Object result = threadObjectFinder.getTargetObject(cls, Thread.currentThread(), breakObject, breakType, 30);
//        // 调试用
//        ArrayList stackList = new ArrayList<>();
//        Object result = threadObjectFinder.getTargetObject(cls, Thread.currentThread(), breakObject, breakType, 30, stackList, object.getClass().getName());
//
//
//        return result;
//    }
//
//    /**
//     * 递归查找
//     */
//    public Object getTargetObject(Class targetCls, Object object, HashSet breakObject, HashSet<String> breakType, int maxDepth) {
//        // 最大递归深度
//        maxDepth--;
//        if (maxDepth < 0) {
//            return null;
//        }
//
//        if (object == null) {
//            return null;
//        }
//
//        // 寻找到指定类返回
//        if (targetCls.isInstance(object)) {
//            return object;
//        }
//
//        // 获取内存地址，来标识唯一对象
//        Integer hash = System.identityHashCode(object);
//
//        if (breakObject.contains(hash)) {
//            return null;
//        }
//        breakObject.add(hash);
//
//        // 获取对象所有 Field
//        Field[] fields = object.getClass().getDeclaredFields();
//        ArrayList fieldsArray = new ArrayList();
//        Class objClass = object.getClass();
//        while (objClass != null) {
//            Field[] superFields = objClass.getDeclaredFields();
//            fieldsArray.addAll(Arrays.asList(superFields));
//            objClass = objClass.getSuperclass();
//        }
//        fields = (Field[]) fieldsArray.toArray(new Field[0]);
//
//
//        for (Field field : fields) {
//            try {
//                Class type = field.getType();
//
//                if (breakType.contains(type.getName())) {
//                    continue;
//                }
//
//                // 获取 Field 值
//                field.setAccessible(true);
//                Object value = field.get(object);
//                Object result = null;
//
//                // 递归查找
//                if (value instanceof Map) {
//                    // Map 的 kv 都要遍历
//                    Map map = (Map) value;
//                    for (Object o : map.entrySet()) {
//                        Map.Entry entry = (Map.Entry) o;
//                        result = getTargetObject(targetCls, entry.getKey(), breakObject, breakType, maxDepth);
//                        if (result != null) {
//                            break;
//                        }
//                        result = getTargetObject(targetCls, entry.getValue(), breakObject, breakType, maxDepth);
//                        if (result != null) {
//                            break;
//                        }
//                    }
//                } else if (value instanceof Iterable) {
//                    // 集合的元素都要遍历
//                    Iterable iterable = (Iterable) value;
//                    for (Object o : iterable) {
//                        result = getTargetObject(targetCls, o, breakObject, breakType, maxDepth);
//                        if (result != null) {
//                            break;
//                        }
//                    }
//                } else if (type.isArray()) {
//                    // 数组的元素都要遍历
//                    Object[] array = (Object[]) value;
//                    for (Object o : array) {
//                        result = getTargetObject(targetCls, o, breakObject, breakType, maxDepth);
//                        if (result != null) {
//                            break;
//                        }
//                    }
//                } else {
//                    result = getTargetObject(targetCls, value, breakObject, breakType, maxDepth);
//                }
//
//                if (result != null) {
//                    return result;
//                }
//            } catch (Throwable e) {
//
//            }
//        }
//
//
//        return null;
//    }
//
//    /**
//     * 递归查找, 返回路径，调试用
//     */
//    public Object getTargetObject(Class targetCls, Object object, HashSet breakObject, HashSet<String> breakType, int maxDepth, ArrayList stackList, String stackName) {
//        // 最大递归深度
//        maxDepth--;
//        if (maxDepth < 0) {
//            return null;
//        }
//
//        if (object == null) {
//            return null;
//        }
//
//        // 寻找到指定类返回
//        if (targetCls.isInstance(object)) {
//            return object;
//        }
//
//        // 获取内存地址，来标识唯一对象
//        Integer hash = System.identityHashCode(object);
//
//        if (breakObject.contains(hash)) {
//            return null;
//        }
//        breakObject.add(hash);
//
//        // 获取对象所有 Field
//        Field[] fields = object.getClass().getDeclaredFields();
//        ArrayList fieldsArray = new ArrayList();
//        Class objClass = object.getClass();
//        while (objClass != null) {
//            Field[] superFields = objClass.getDeclaredFields();
//            fieldsArray.addAll(Arrays.asList(superFields));
//            objClass = objClass.getSuperclass();
//        }
//        fields = (Field[]) fieldsArray.toArray(new Field[0]);
//
//
//        for (Field field : fields) {
//            try {
//                Class type = field.getType();
//
//                if (breakType.contains(type.getName())) {
//                    continue;
//                }
//
//                // 获取 Field 值
//                field.setAccessible(true);
//                Object value = field.get(object);
//                Object result = null;
//
//                // 递归查找
//                if (value instanceof Map) {
//                    // Map 的 kv 都要遍历
//                    Map map = (Map) value;
//                    for (Object o : map.entrySet()) {
//                        Map.Entry entry = (Map.Entry) o;
//                        result = getTargetObject(targetCls, entry.getKey(), breakObject, breakType, maxDepth, stackList, field.getName());
//                        if (result != null) {
//                            break;
//                        }
//                        result = getTargetObject(targetCls, entry.getValue(), breakObject, breakType, maxDepth, stackList, field.getName());
//                        if (result != null) {
//                            break;
//                        }
//                    }
//                } else if (value instanceof Iterable) {
//                    // 集合的元素都要遍历
//                    Iterable iterable = (Iterable) value;
//                    for (Object o : iterable) {
//                        result = getTargetObject(targetCls, o, breakObject, breakType, maxDepth, stackList, field.getName());
//                        if (result != null) {
//                            break;
//                        }
//                    }
//                } else if (type.isArray()) {
//                    // 数组的元素都要遍历
//                    Object[] array = (Object[]) value;
//                    for (int i = 0; i < array.length; i++) {
//                        result = getTargetObject(targetCls, array[i], breakObject, breakType, maxDepth, stackList, field.getName() + String.format("[%s]", i));
//                        if (result != null) {
//                            break;
//                        }
//                    }
//                } else {
//                    result = getTargetObject(targetCls, value, breakObject, breakType, maxDepth, stackList, field.getName());
//                }
//
//                if (result != null) {
//                    stackList.add(stackName);
//                    return result;
//                }
//            } catch (Throwable e) {
//
//            }
//        }
//
//
//        return null;
//    }
//
//
//
//    /**
//     * 遍历 ClassLoader 加载目标 Class
//     */
//    public Class getTargetClass(String className, List<ClassLoader> activeClassLoaders) {
//        for (ClassLoader activeClassLoader : activeClassLoaders) {
//            try {
//                return Class.forName(className, true, activeClassLoader);
//            } catch (Throwable e) {
//
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 获取活跃线程
//     *
//     * @return
//     * @throws Exception
//     */
//    public List<ClassLoader> getActiveClassLoaders() throws Exception {
////        List<ClassLoader> activeClassLoaders = new ArrayList<>();
//        Set<ClassLoader> activeClassLoaders = new HashSet<>();
//
//        // 加载当前对象的加载器
//        activeClassLoaders.add(this.getClass().getClassLoader());
//
//        // 当前线程的上下文类加载器
//        activeClassLoaders.add(Thread.currentThread().getContextClassLoader());
//
////        // 应用程序类加载器
////        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
////        activeClassLoaders.add(systemClassLoader);
////
////        // 扩展类加载器
////        activeClassLoaders.add(systemClassLoader.getParent());
//
//        // 获取线程组
//        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
//        Thread[] threads = new Thread[threadGroup.activeCount()];
//        int count = threadGroup.enumerate(threads, true);
//        for (int i = 0; i < count; i++) {
//            activeClassLoaders.add(threads[i].getContextClassLoader());
//        }
//
//        return new ArrayList<>(activeClassLoaders);
//    }
//}
