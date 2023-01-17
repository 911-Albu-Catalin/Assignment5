package view;

import controller.Controller;
import exceptions.HeapException;
import exceptions.InvalidChoiceException;
import exceptions.TypeCheckException;
import model.expressions.ArithmeticExpression;
import model.expressions.HeapReading;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.state.*;
import model.statements.*;
import model.types.*;
import model.values.BooleanValue;
import model.values.IValue;
import model.values.IntegerValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class View {
    Controller controller;

    public View(Controller c) {
        controller = c;
    }

    public void printMenu() {
        String text = "";
        text += "1. int v; v =  2; print(v)\n";
        text += "2. int a; int b; a =  2 + 3 *  5; b = a + 1; print(b)\n";
        text += "3. bool a; int v; a =  true; if (a) then { v =  2 } else { v =  3 }; print(v)\n";
        text += "4. file example\n";
        text += "5. Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))\n";
        text += "5. Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))\n";
        text += "6. int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))\n";

        text += "Press 0 to exit.\n";
        text += "Choose a program: ";
        System.out.println(text);
    }

    public void program1() throws IOException, InterruptedException {
        IStatement program1 = new Compound(new VariableDeclaration("v", new IntegerType()),
                new Compound(new Assignment("v", new ValueExpression(new IntegerValue(2))),
                        new Print(new VariableExpression("v"))));
        MyStack<IStatement> executionStack1 = new MyStack<>();
        executionStack1.push(program1);
        MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
        MyDictionary<String, IValue> symbolsTable1 = new MyDictionary<>();
        MyList<IValue> output1 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable1 = new MyDictionary<>();
        MyHeap<Integer, IValue> heap = new MyHeap<>();
        try {
            program1.typeCheck(typeEnv);
            System.out.println("The type check has been done successfully!\n");
        }
        catch (TypeCheckException e) {
            System.out.println(e.getMessage());
        }
        ProgramState programState1 = new ProgramState(executionStack1, symbolsTable1, output1, fileTable1, heap);
        controller.add(programState1);
        controller.allStep();

    }

    public void program2() throws IOException, InterruptedException {
        IStatement program2 = new Compound(new VariableDeclaration("a", new IntegerType()),
                new Compound(new VariableDeclaration("b", new IntegerType()),
                        new Compound(new Assignment("a",
                                new ArithmeticExpression(1, new ValueExpression(new IntegerValue(2)),
                                        new ArithmeticExpression(3, new ValueExpression(new IntegerValue(3)),
                                                new ValueExpression(new IntegerValue(5))))),
                                new Compound(new Assignment("b",
                                        new ArithmeticExpression(1, new VariableExpression("a"),
                                                new ValueExpression(new IntegerValue(1)))),
                                        new Print(new VariableExpression("b"))))));
        MyStack<IStatement> executionStack2 = new MyStack<>();
        executionStack2.push(program2);
        MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
        MyDictionary<String, IValue> symbolsTable2 = new MyDictionary<>();
        MyList<IValue> output2 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<>();
        MyHeap<Integer, IValue> heap = new MyHeap<>();
        try {
            program2.typeCheck(typeEnv);
            System.out.println("The type check has been done successfully!\n");
        }
        catch (TypeCheckException e) {
            System.out.println(e.getMessage());
        }
        ProgramState programState2 = new ProgramState(executionStack2, symbolsTable2, output2, fileTable2, heap);
        controller.add(programState2);
    }

    public void program3() throws IOException, InterruptedException {
        IStatement program3 = new Compound(new VariableDeclaration("a", new BooleanType()),
                new Compound(new VariableDeclaration("v", new IntegerType()),
                        new Compound(new Assignment("a", new ValueExpression(new BooleanValue(true))),
                                new Compound(new If(new VariableExpression("a"), new Assignment("v",
                                        new ValueExpression(new IntegerValue(2))),
                                        new Assignment("v", new ValueExpression(new IntegerValue(3)))),
                                        new Print(new VariableExpression("v"))))));
        MyStack<IStatement> executionStack3 = new MyStack<>();
        executionStack3.push(program3);
        MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
        MyDictionary<String, IValue> symbolsTable3 = new MyDictionary<>();
        MyList<IValue> output3 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable3 = new MyDictionary<>();
        MyHeap<Integer, IValue> heap = new MyHeap<>();
        try {
            program3.typeCheck(typeEnv);
            System.out.println("The type check has been done successfully!\n");
        }
        catch (TypeCheckException e) {
            System.out.println(e.getMessage());
        }
        ProgramState programState3 = new ProgramState(executionStack3, symbolsTable3, output3, fileTable3, heap);
        controller.add(programState3);
    }

    public void program4() throws IOException, InterruptedException {
        IStatement program4 = new Compound(
                new VariableDeclaration("file", new StringType()),
                new Compound(
                new Assignment("file", new ValueExpression(new StringValue("../example.txt"))),
                new Compound(
                new OpenFile(new VariableExpression("file")),
                new Compound(
                new VariableDeclaration("x", new IntegerType()),
                new Compound(
                new ReadFile(new VariableExpression("file"), "x"),
                new Compound(
                new Print(new VariableExpression("x")),
                new Compound(
                new ReadFile(new VariableExpression("file"), "x"),
                new Compound(
                new Print(new VariableExpression("x")),
                new CloseFile(new VariableExpression("file"))))))))));
        MyStack<IStatement> executionStack4 = new MyStack<>();
        executionStack4.push(program4);
        MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
        MyDictionary<String, IValue> symbolsTable4 = new MyDictionary<>();
        MyList<IValue> output4 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable4 = new MyDictionary<>();
        MyHeap<Integer, IValue> heap = new MyHeap<>();
        try {
            program4.typeCheck(typeEnv);
            System.out.println("The type check has been done successfully!\n");
        }
        catch (TypeCheckException e) {
            System.out.println(e.getMessage());
        }
        ProgramState programState4 = new ProgramState(executionStack4, symbolsTable4, output4, fileTable4, heap);
        controller.add(programState4);
    }

    public void program5() throws IOException, HeapException, InterruptedException {
        IStatement stmt = new Compound(
                new VariableDeclaration("v", new RefType(new IntegerType())),
                new Compound(
                new HeapAllocation("v", new ValueExpression(new IntegerValue(20))),
                new Compound(
                new VariableDeclaration("a", new RefType(new RefType(new IntegerType()))),
                new Compound(
                new HeapAllocation("a", new VariableExpression("v")),
                new Compound(
                new HeapAllocation("v", new ValueExpression(new IntegerValue(30))),
                new Print(new HeapReading(new HeapReading(new VariableExpression("a"))))))))
        );
        MyStack<IStatement> executionStack4 = new MyStack<>();
        executionStack4.push(stmt);
        MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
        MyDictionary<String, IValue> symbolsTable4 = new MyDictionary<>();
        MyList<IValue> output4 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable4 = new MyDictionary<>();
        MyHeap<Integer, IValue> heap = new MyHeap<>();
        try {
            stmt.typeCheck(typeEnv);
            System.out.println("The type check has been done successfully!\n");
        }
        catch (TypeCheckException e) {
            System.out.println(e.getMessage());
        }
        ProgramState programState4 = new ProgramState(executionStack4, symbolsTable4, output4, fileTable4, heap);
        controller.add(programState4);
    }

    public void program6() throws IOException, InterruptedException {
        IStatement stmt = new Compound(
                new VariableDeclaration("v", new IntegerType()),
                new Compound(
                        new VariableDeclaration("a", new RefType(new IntegerType())),
                        new Compound(
                                new Assignment("v", new ValueExpression(new IntegerValue(10))),
                                new Compound(
                                        new HeapAllocation("a", new ValueExpression(new IntegerValue(22))),
                                        new Compound(
                                                new ForkStatement(new Compound(
                                                        new HeapWriting("a", new ValueExpression(new IntegerValue(30))),
                                                        new Compound(
                                                                new Assignment("v", new ValueExpression(new IntegerValue(32))),
                                                                new Compound(
                                                                        new Print(new VariableExpression("v")),
                                                                        new Print(new HeapReading(new VariableExpression("a")))
                                                                )
                                                        )
                                                )),
                                                new Compound(
                                                        new Print(new VariableExpression("v")),
                                                        new Print(new HeapReading(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        MyStack<IStatement> executionStack6 = new MyStack<>();
        MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
        MyDictionary<String, IValue> symbolsTable6 = new MyDictionary<>();
        MyList<IValue> output6 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable6 = new MyDictionary<>();
        MyHeap<Integer, IValue> heap = new MyHeap<>();
        try {
            stmt.typeCheck(typeEnv);
            System.out.println("The type check has been done successfully!\n");
        }
        catch (TypeCheckException e) {
            System.out.println(e.getMessage());
        }
        ProgramState programState4 = new ProgramState(executionStack6, symbolsTable6, output6, fileTable6, heap, stmt);
        controller.add(programState4);
        controller.allStep();

    }

    public void execute() {
        boolean start = false;
        while (!start) {
            printMenu();
            Scanner S = new Scanner(System.in);
            int cmd = S.nextInt();
            try {
                switch (cmd) {
                    case 0 -> {
                        start = true;
                    }
                    case 1 -> {
                        program1();
                    }
                    case 2 -> {
                        program2();
                    }
                    case 3 -> {
                        program3();
                    }
                    case 4 -> {
                        program4();
                    }
                    case 5 -> {
                        program5();
                    }
                    case 6 -> {
                        program6();
                    }
                }
            }
            catch (InvalidChoiceException | IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
