package model.state;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }

    @Override
    public String toString() {
        String returnStr = "";
        for (int i = stack.size() - 1; i >= 0; i--)
            returnStr += stack.get(i).toString() + "\n";
        if (stack.isEmpty())
            returnStr += "empty\n";
        returnStr += "--------------------------------";
        return returnStr;
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T item) {
        stack.push(item);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }
}