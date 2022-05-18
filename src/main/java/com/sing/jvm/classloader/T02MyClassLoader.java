package com.sing.jvm.classloader;

import java.io.*;

/**
 * 二、类加载器和双亲委派机制
 * 上面的类加载过程主要是通过类加载器类实现的，Java里面有如下几种类加载器
 * 启动类加载器（BootStrap classLoader） ：负责加载支撑JVM运行的位于%JAVA_HOME%/jre/lib目录下的核心类库，比如rt.jar、charsets.jar等,c++实现
 * 扩展类加载器：（ExtClassLoader）负责加载支撑JVM运行的位于%JAVA_HOME%/jre/lib/ext扩展目录中的JAR类包或由-Djava.ext.dirs指定
 * 应用程序类加载器（AppClassLoader）：负责加载ClassPath路径下的类包，主要就是加载自己写的那些类
 * （自定义加载器（CustomClassLoader）：负责加载用户自定义路径下的类包）
 * <p>
 * 双亲委派机制(避免类的重复加载；保护程序安全防止核心API被随意篡改)
 * @author songbo
 * @since 2022-05-17
 */
public class T02MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("c:/myJar/",name.replaceAll(".","/").concat(".class"));
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int b = 0;
            while ((b = fileInputStream.read()) != 0){
                byteArrayOutputStream.write(b);
            }
            byteArrayOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            fileInputStream.close();
            return defineClass(name,bytes,0,bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}
