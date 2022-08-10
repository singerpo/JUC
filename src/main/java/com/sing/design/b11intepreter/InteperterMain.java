package com.sing.design.b11intepreter;

import java.util.Stack;

/**
 * 解释器模式
 * 就是定义语言的文法，并建立一个解释器来解释该语言中的句子，通过构建解释器，解决某一频繁发生的特定类型问题实例。
 *
 * @author songbo
 * @since 2022-08-10
 */
public class InteperterMain {
    public static void main(String[] args) {
        String statement = "3 * 4 / 2 % 4";
        Calculator calculator = new Calculator();
        calculator.build(statement);
        int result = calculator.compute();
        System.out.println(statement + " = " + result);

    }
}

interface Node {
    int interpret();
}

// 非终结表达式
class ValueNode implements Node {
    private int value;

    public ValueNode(int value) {
        this.value = value;
    }

    @Override
    public int interpret() {
        return this.value;
    }
}

// 终结表达式抽象类，需要多个运算符号
abstract class SymbolNode implements Node {
    protected Node left;
    protected Node right;

    public SymbolNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
}

// 乘以
class MulNode extends SymbolNode {

    public MulNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public int interpret() {
        return left.interpret() * right.interpret();
    }
}

// 除以
class DivNode extends SymbolNode {

    public DivNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public int interpret() {
        return left.interpret() / right.interpret();
    }
}

// 取模
class ModNode extends SymbolNode {

    public ModNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public int interpret() {
        return left.interpret() % right.interpret();
    }
}

class Calculator {
    private Node node;

    public void build(String statement) {
        Node left = null;
        Node right = null;
        Stack<Node> stack = new Stack();

        String[] statementArr = statement.split(" ");
        for (int i = 0; i < statementArr.length; i++) {
            String statementOne = statementArr[i];
            if (statementOne.equalsIgnoreCase("*")) {
                left = stack.pop();
                right = new ValueNode(Integer.parseInt(statementArr[++i]));
                stack.push(new MulNode(left, right));
            } else if (statementOne.equalsIgnoreCase("/")) {
                left = stack.pop();
                right = new ValueNode(Integer.parseInt(statementArr[++i]));
                stack.push(new DivNode(left, right));
            } else if (statementOne.equalsIgnoreCase("%")) {
                left = stack.pop();
                right = new ValueNode(Integer.parseInt(statementArr[++i]));
                stack.push(new ModNode(left, right));
            } else {
                stack.push(new ValueNode(Integer.parseInt(statementOne)));
            }
        }
        this.node = stack.pop();
    }

    public int compute() {
        return node.interpret();
    }
}
