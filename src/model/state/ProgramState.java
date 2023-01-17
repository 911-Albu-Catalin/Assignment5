package model.state;

import exceptions.ControllerException;
import model.statements.IStatement;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ProgramState {
    private MyIDictionary<String, IValue> symbolTable;
    private MyIList<IValue> output;
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer, IValue> heapTable;
    private IStatement originalProgram;
    private static ArrayList<Integer> ids = new ArrayList<Integer>();
    private int id;
    private static int maxId;

    synchronized public static int getMaxId() {
        return maxId++;
    }

    public ProgramState(MyIStack<IStatement> stk, MyIDictionary<String, IValue> dict, MyIList<IValue> lst, MyIDictionary<StringValue, BufferedReader> file, MyIHeap<Integer, IValue> heap, IStatement stmt) {
        exeStack = stk;
        symbolTable = dict;
        output = lst;
        fileTable = file;
        heapTable = heap;
        originalProgram = stmt.deepCopy();
        exeStack.push(stmt);
        id = getMaxId();
    }

    public ProgramState(MyIStack<IStatement> stk, MyIDictionary<String, IValue> dict, MyIList<IValue> lst, MyIDictionary<StringValue, BufferedReader> file, MyIHeap<Integer, IValue> heap) {
        exeStack = stk;
        symbolTable = dict;
        output = lst;
        fileTable = file;
        heapTable = heap;
    }

    public ProgramState(IStatement originalProgram) {
        exeStack = new MyStack<IStatement>();
        symbolTable = new MyDictionary<String, IValue>();
        output = new MyList<IValue>();
        fileTable = new MyDictionary<StringValue, BufferedReader>();
        heapTable = new MyHeap<Integer, IValue>();
        exeStack.push(originalProgram);
        id = newId();
    }

    public static Integer newId() {
        Integer id = 1;
        do {
            id++;
            synchronized (ids) {
                if (!ids.contains(id))
                    break;
            }
        } while (true);
        synchronized (ids) {
            ids.add(id);
        }
        return id;
    }

    public MyIDictionary<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    public MyIList<IValue> getOutput() {
        return output;
    }

    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Integer, IValue> getHeapTable() {
        return heapTable;
    }

    public int getId() {
        return id;
    }

    synchronized public static void setMaxId(int id) {
        maxId = id;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public ProgramState oneStep() throws ControllerException, FileNotFoundException {
        if (exeStack.isEmpty())
            throw new ControllerException("The execution stack is empty!\n");
        IStatement currStmt = exeStack.pop();
        return currStmt.execute(this);
    }

    public String toString() {
        String returnStr = "";
        returnStr += "Id: " + id + "\n";
        returnStr += "Execution Stack:\n";
        returnStr += exeStack.toString() + "\n";
        returnStr += "Symbol table:\n";
        returnStr += symbolTable.toString() + "\n";
        returnStr += "Output:\n";
        returnStr += output.toString() + "\n";
        returnStr += "File Table:\n";
        returnStr += fileTable.toString() + "\n";
        returnStr += "Heap Table:\n";
        returnStr += heapTable.toString();
        returnStr += "\n";
        return returnStr;
    }
}
