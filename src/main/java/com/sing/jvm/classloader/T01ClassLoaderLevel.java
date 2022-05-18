package com.sing.jvm.classloader;

import sun.net.spi.nameservice.dns.DNSNameService;

/**
 * 一、类加载过程
 * 1.加载
 * --Java虚拟机将.class文件读入内存（放在运行时区域的方法区内），并根据字节流为之创建一个Class对象（放在堆中）
 * （任何类被使用时系统都会为其创建一个且仅有一个Class对象）
 * （这个Class对象描述了这个类创建出来的对象的所有信息，比如有哪些构造方法、哪些成员方法，哪些成员变量）
 * 2.验证
 * --保证Class文件的字节流包含的信息符合JVM规范。验证过程主要分为四个阶段
 * （文件格式验证：验证字节流文件是否符合Class文件格式的规范，并且能被当前虚拟机正确的处理）
 * （元数据验证：是对字节码描述的信息进行语义分析，以保证其描述的信息符合Java语言的规范要求）
 * （字节码验证：主要是进行数据流和控制流的分析，保证被校验类的方法在运行时不会危害虚拟机）
 * （符号引用验证：符号引用验证发生在虚拟机将符号引用转化为直接引用的时候，这个转化动作将在解析阶段中发生）
 * 3.准备
 * --准备阶段为变量分配内存并设置类变量的初始化。
 * （在这个阶段分配的仅为类的变量，而不包含类的实例变量。对已非final的变量，JVM会将其设置成“零值”，而不是其赋值语句的值：
 * private static int size = 12。那么在这个阶段，size的值为0，而不是12。但final修饰的类变量将会赋值成真实的值）
 * 4.解析
 * --解析过程是将常量池内的符号引用替换成直接引用。
 * （类或接口的解析、字段解析、方法解析、接口方法解析）
 * 5.初始化
 * --初始化则是为标记为常量值的字段赋值的过程。换句话说，只对static修饰的变量或语句块进行初始化
 * （如果初始化一个类的时候，其父类尚未初始化，则优先初始化其父类。）
 * （如果同时包含多个静态变量和静态代码块，则按照自上而下的顺序依次执行）
 * <p>
 * 二、类加载器和双亲委派机制
 * 上面的类加载过程主要是通过类加载器类实现的，Java里面有如下几种类加载器
 * 启动类加载器（BootStrap classLoader） ：负责加载支撑JVM运行的位于%JAVA_HOME%/jre/lib目录下的核心类库，比如rt.jar、charsets.jar等,c++实现
 * 扩展类加载器：（ExtClassLoader）负责加载支撑JVM运行的位于%JAVA_HOME%/jre/lib/ext扩展目录中的JAR类包或由-Djava.ext.dirs指定
 * 应用程序类加载器（AppClassLoader）：负责加载ClassPath路径下的类包，主要就是加载自己写的那些类
 * （自定义加载器（CustomClassLoader）：负责加载用户自定义路径下的类包）
 * <p>
 * 双亲委派机制(避免类的重复加载；保护程序安全防止核心API被随意篡改)
 * 一个类只有被第一次主动使用时，才会被Java虚拟机加载
 * 主动使用的情况（6种）
 * 1.创建类的实例
 * 2.访问类的静态变量(final静态变量除外）
 * 3.调用类的静态方法
 * 4.反射加载
 * 5.初始化一个类的子类
 * 6.Java虚拟机启动时被标记为启动类的类
 *
 * 三、混合执行 解释执行+编译执行
 * 1.检测热点代码：—XX:CompileThreshold=10000
 */
public class T01ClassLoaderLevel {

    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader());
        System.out.println(DNSNameService.class.getClassLoader());
        System.out.println(T01ClassLoaderLevel.class.getClassLoader());
        System.out.println("--------------------------");
        System.out.println(DNSNameService.class.getClassLoader().getClass().getClassLoader());
        System.out.println(T01ClassLoaderLevel.class.getClassLoader().getClass().getClassLoader());
        System.out.println("--------------------------");
        System.out.println(DNSNameService.class.getClassLoader().getParent());
        System.out.println(T01ClassLoaderLevel.class.getClassLoader().getParent());
        System.out.println("----------默认parent----------------");
        System.out.println(ClassLoader.getSystemClassLoader());

        System.out.println("--------------------------");

        System.out.println("--------------------------");
        String pathBoot = System.getProperty("sun.boot.class.path");
        System.out.println(pathBoot.replaceAll(";", System.lineSeparator()));
        System.out.println("--------------------------");
        String pathExt = System.getProperty("java.ext.dirs");
        System.out.println(pathExt.replaceAll(";", System.lineSeparator()));
        System.out.println("--------------------------");
        String pathApp = System.getProperty("java.class.path");
        System.out.println(pathApp.replaceAll(";", System.lineSeparator()));

    }
}
