package com.sing.jvm.gc;

import java.util.LinkedList;
import java.util.List;

/**119
 * 设置日志参数
 * -Xloggc:/opt/xxx/logs/xxx-xxx-gc-%t.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=20M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCCause
 *
 *
 */
public class TestTuning {

    public static void main(String[] args) {

        List list = new LinkedList();
        for (/*int i = 1*/; ; /*i++*/) {
            byte[] b = new byte[1024 * 1024];
            // if(i > 2000){
            //     System.out.println(i);
            // }
            list.add(b);
        }
    }

}
