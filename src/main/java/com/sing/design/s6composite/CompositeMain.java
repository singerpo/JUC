package com.sing.design.s6composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 * 树形结构专用模式
 *
 * @author songbo
 * @since 2022-06-27
 */
public class CompositeMain {
    public static void main(String[] args) {
        Folder root = new Folder("总文件夹");
        Folder folderOne = new Folder("文件夹1");
        folderOne.add(new LeafFile("文件1"));
        folderOne.add(new LeafFile("文件2"));

        Folder folderTwo = new Folder("文件夹2");
        Folder folderTwoOne = new Folder("文件夹21");
        folderTwoOne.add(new LeafFile("文件1"));
        folderTwoOne.add(new LeafFile("文件2"));
        folderTwo.add(folderTwoOne);

        root.add(folderOne);
        root.add(folderTwo);
        root.display();

    }
}

// 文件类
abstract class File {
    String name;

    public File(String name) {
        this.name = name;
    }

    abstract public void display();

}

// 普通文件
class LeafFile extends File {

    public LeafFile(String name) {
        super(name);
    }

    @Override
    public void display() {
        System.out.println(name);
    }
}

//文件夹可以有子节点
class Folder extends File {
    private List<File> files;

    public Folder(String name) {
        super(name);
        files = new ArrayList<>();
    }

    @Override
    public void display() {
        System.out.println(name);
        for (File file : files) {
            file.display();
        }
    }

    public void add(File file) {
        files.add(file);
    }

    public void remove(File file){
        files.remove(file);
    }
}
