package com.sing.herostory.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author songbo
 * @since 2022-09-01
 */
public final class PackageUtil {
    private PackageUtil() {

    }

    /**
     * 列表指定包中的所有子类
     *
     * @param packageName 包名称
     * @param recursive   是否递归查找
     * @param superClazz  父类的类型
     * @return 子类集合
     */
    public static Set<Class<?>> listSubClazz(String packageName, boolean recursive, Class<?> superClazz) {
        if (superClazz == null) {
            return Collections.emptySet();
        } else {
            return listClazz(packageName, recursive, superClazz::isAssignableFrom);
        }
    }

    /**
     * 列表指定包中的所有类
     *
     * @param packageName 包名称
     * @param recursive   是否递归查找
     * @param filter      过滤器
     * @return
     */
    private static Set<Class<?>> listClazz(String packageName, boolean recursive, IClazzFilter filter) {
        if (packageName == null || packageName.isEmpty()) {
            return null;
        }
        // 将点转换成斜杠
        String packagePath = packageName.replace(".", "/");
        // 获取类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //结果集合
        Set<Class<?>> resultSet = new HashSet<>();
        try {
            // 获取URL枚举
            Enumeration<URL> urlEnum = classLoader.getResources(packagePath);
            while (urlEnum.hasMoreElements()) {
                // 获取当前URL
                URL currentUrl = urlEnum.nextElement();
                // 获取协议文本
                String protocol = currentUrl.getProtocol();
                // 定义临时集合
                Set<Class<?>> tmpSet = null;
                if ("FILE".equalsIgnoreCase(protocol)) {
                    // 从文件系统中加载类
                    tmpSet = listClazzFromDir(new File(currentUrl.getFile()), packageName, recursive, filter);
                } else if ("JAR".equalsIgnoreCase(protocol)) {
                    tmpSet = listClazzFromJar(new File(currentUrl.getFile()), packageName, recursive, filter);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private static Set<Class<?>> listClazzFromJar(File jarFilePath, String packageName, boolean recursive, IClazzFilter filter) {
        if (!jarFilePath.exists() || !jarFilePath.isDirectory()) {
            return null;
        }

        // 获取子文件列表
        File[] subFileArr = jarFilePath.listFiles();
        if (subFileArr == null || subFileArr.length <= 0) {
            return null;
        }

        Set<Class<?>> resultSet = new HashSet<>();
        JarInputStream jarInputStream = null;
        try {
            jarInputStream = new JarInputStream(new FileInputStream(jarFilePath));
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                if (jarEntry.isDirectory()) {
                    continue;
                }
                // 获取进入点名称
                String entryName = jarEntry.getName();
                // 如果不是以.class结尾，则说明不是Java类文件，则直接跳过
                if (!entryName.endsWith(".class")) {
                    continue;
                }

                // 如果没有开启递归模式，那么就需要判断当前.class文件是否在指定目录下
                if (!recursive) {
                    // 获取目录名称
                    String tmpStr = entryName.substring(0, entryName.lastIndexOf("/"));
                    // 将目录中的"/"全部替换成"."
                    tmpStr = join(tmpStr.split("/"), ".");
                    // 如果包名和目录名不相等，则直接跳过
                    if (!packageName.equals(tmpStr)) {
                        continue;
                    }
                }
                // 清除最后的.class
                String clazzName = entryName.substring(0, entryName.lastIndexOf("."));
                // 将所有的/修改为.
                clazzName = join(clazzName.split("/"), ".");
                // 加载类定义
                Class<?> clazz = Class.forName(clazzName);
                // 如果过滤器不为空，且过滤器不接受当前类，则直接跳过
                if (filter != null && !filter.accept(clazz)) {
                    continue;
                }
                resultSet.add(clazz);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                if (jarInputStream != null) {
                    jarInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return resultSet;
    }

    private static Set<Class<?>> listClazzFromDir(File dirFile, String packageName, boolean recursive, IClazzFilter filter) {
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return null;
        }

        // 获取子文件列表
        File[] subFileArr = dirFile.listFiles();
        if (subFileArr == null || subFileArr.length <= 0) {
            return null;
        }

        //将子文件数组添加到队列
        Queue<File> fileQueue = new LinkedList<>(Arrays.asList(subFileArr));

        Set<Class<?>> resultSet = new HashSet<>();
        while (!fileQueue.isEmpty()) {
            // 从队列中获取文件
            File currentFile = fileQueue.poll();
            // 如果当前文件是目录，且递归，则获取子文件列表
            if (currentFile.isDirectory() && recursive) {
                subFileArr = currentFile.listFiles();
                if (subFileArr != null || subFileArr.length > 0) {
                    // 添加文件到队列
                    fileQueue.addAll(Arrays.asList(subFileArr));
                }
                continue;
            }

            // 如果当前文件不是文件或者不是以.class结尾，则自己跳过
            if (!currentFile.isFile() || !currentFile.getName().endsWith(".class")) {
                continue;
            }

            //类名称
            String clazzName = currentFile.getAbsolutePath();
            // 清除最后的.class
            clazzName = clazzName.substring(dirFile.getAbsolutePath().length(), clazzName.lastIndexOf("."));
            // 转换目录斜杠
            clazzName = clazzName.replace("\\", "/");
            // 清除开头的 /
            clazzName = trimLeft(clazzName, "/");
            // 将所有的/修改为.
            clazzName = join(clazzName.split("/"), ".");
            // 包名+类名
            clazzName = packageName + "." + clazzName;

            try {
                Class<?> clazz = Class.forName(clazzName);
                // 如果过滤器不为空，且过滤器不接受当前类，则直接跳过
                if (filter != null && !filter.accept(clazz)) {
                    continue;
                }
                resultSet.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return resultSet;
    }

    private static String join(String[] strArr, String conn) {
        if (strArr == null || strArr.length <= 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                stringBuilder.append(conn);
            }
            stringBuilder.append(strArr[i]);
        }
        return stringBuilder.toString();
    }

    private static String trimLeft(String src, String trimStr) {
        if (src == null || src.isEmpty()) {
            return "";
        }
        if (src.equals(trimStr)) {
            return "";
        }
        while (src.startsWith(trimStr)) {
            src = src.substring(trimStr.length());
        }
        return src;
    }

    /**
     * 类过滤器
     */
    @FunctionalInterface
    public static interface IClazzFilter {
        /**
         * 是否接收当前类
         *
         * @param clazz 被筛选的类
         * @return 是否符合条件
         */
        boolean accept(Class<?> clazz);
    }
}
