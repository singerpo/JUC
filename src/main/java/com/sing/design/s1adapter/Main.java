package com.sing.design.s1adapter;

import java.io.*;

/**
 * 结构型-适配器模式
 * 类的适配器模式、对象的适配器模式、接口的适配器模式
 *
 *
 */
public class Main {
    public static void main(String[] args) throws IOException {
        //案例一：Spring
        // 案例二：java.io
        FileInputStream fileInputStream = new FileInputStream("");
        // 适配器
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        while (line != null && !line.equals("")){
            System.out.println(line);
        }
        bufferedReader.close();

        // 案例三：jdbc-odbc bridge


    }
}
