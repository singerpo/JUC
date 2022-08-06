package com.sing.design.b08command;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令模式
 * 将请求封装成对象，将发出命令与执行命令的责任分开，命令的发送者和接收者完全解耦，发送者只需知道如何发送命令，
 * 不需要关心命令是如何实现的，甚至是否执行成功都不需要理会。
 * 1.与composite模式实现宏命令
 * 2.与责任链模式实现多次undo
 * 3.与备忘录模式实现transction回滚
 *
 * @author songbo
 * @since 2022-08-06
 */
public class CommandMain {
    public static void main(String[] args) {
        CommandChain commandChain = new CommandChain();

        Content content = new Content();
        Command insertCommand = new InsertCommand(content);
        insertCommand.execute();
        commandChain.addCommand(insertCommand);

        Command copyCommand = new CopyCommand(content);
        copyCommand.execute();
        commandChain.addCommand(copyCommand);

        Command deleteCommand = new DeleteCommand(content);
        deleteCommand.execute();
        commandChain.addCommand(deleteCommand);

        commandChain.unExecute();
        commandChain.unExecute();
        commandChain.unExecute();


        System.out.println(content.msg);
    }

}

class Content {
    String msg = "hello content";
}

interface Command {
    void execute();

    void unExecute();
}

class InsertCommand implements Command {
    Content content;
    String msg = "http://www.mycontent.cn";

    public InsertCommand(Content content) {
        this.content = content;
    }

    @Override
    public void execute() {
        content.msg = content.msg + msg;
    }

    @Override
    public void unExecute() {
        content.msg = content.msg.substring(0, content.msg.length() - this.msg.length());
    }
}

class CopyCommand implements Command {
    Content content;

    public CopyCommand(Content content) {
        this.content = content;
    }

    @Override
    public void execute() {
        content.msg = content.msg + content.msg;
    }

    @Override
    public void unExecute() {
        content.msg = content.msg.substring(0, content.msg.length() / 2);
    }
}

class DeleteCommand implements Command {
    Content content;
    String deleted;

    public DeleteCommand(Content content) {
        this.content = content;
    }

    @Override
    public void execute() {
        this.deleted = content.msg.substring(0, 5);
        content.msg = content.msg.substring(5, content.msg.length());
    }

    @Override
    public void unExecute() {
        content.msg = this.deleted + content.msg;
    }
}

class CommandChain {
    List<Command> commands = new ArrayList<>();

    public CommandChain addCommand(Command command) {
        commands.add(command);
        return this;
    }

    public void unExecute() {
        if(commands.size() == 0){
            return;
        }
        Command command = commands.get(commands.size() -1);
        command.unExecute();
        commands.remove(command);
    }

}