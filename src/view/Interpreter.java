package view;

import controller.Controller;
import exceptions.TypeCheckException;
import model.expressions.*;
import model.state.*;
import model.statements.*;
import model.types.*;
import model.values.BooleanValue;
import model.values.IntegerValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.command.ExitCommand;
import view.command.RunExample;

public class Interpreter {
    public static void main(String[] args) {
        IStatement ex1 = new Compound(new VariableDeclaration("v", new IntegerType()),
                new Compound(new Assignment("v", new ValueExpression(new IntegerValue(2))),
                        new Print(new VariableExpression("v"))));

        IStatement ex2 = new Compound(new VariableDeclaration("a",new IntegerType()),
                new Compound(new VariableDeclaration("b",new IntegerType()),
                        new Compound(new Assignment("a", new ArithmeticExpression(1,new ValueExpression(new IntegerValue(2)),new
                                ArithmeticExpression(3,new ValueExpression(new IntegerValue(3)), new ValueExpression(new IntegerValue(5))))),
                                new Compound(new Assignment("b",new ArithmeticExpression(1,new VariableExpression("a"), new ValueExpression(new
                                        IntegerValue(1)))), new Print(new VariableExpression("b"))))));

        IStatement ex3 = new Compound(new VariableDeclaration("a", new BooleanType()),
                new Compound(new VariableDeclaration("v", new IntegerType()),
                        new Compound(new Assignment("a", new ValueExpression(new BooleanValue(false))),
                                new Compound(new If(new VariableExpression("a"),
                                        new Assignment("v", new ValueExpression(new IntegerValue(2))),
                                        new Assignment("v", new ValueExpression(new IntegerValue(3)))),
                                        new Print(new VariableExpression("v"))))));

        IStatement ex4 = new Compound(
                new VariableDeclaration("file", new StringType()),
                new Compound(
                        new Assignment("file", new ValueExpression(new StringValue("example.txt"))),
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

        IStatement ex5 = new Compound(new VariableDeclaration("a", new IntegerType()),
                new Compound(new VariableDeclaration("b", new IntegerType()),
                        new Compound(new Assignment("a", new ValueExpression(new IntegerValue(5))),
                                new Compound(new Assignment("b", new ValueExpression(new IntegerValue(7))),
                                        new If(new RelationalExpression(">", new VariableExpression("a"),
                                                new VariableExpression("b")),new Print(new VariableExpression("a")),
                                                new Print(new VariableExpression("b")))))));

        IStatement ex6 = new Compound(new VariableDeclaration("v", new RefType(new IntegerType())),
                new Compound(new HeapAllocation("v", new ValueExpression(new IntegerValue(20))),
                        new Compound(new VariableDeclaration("a", new RefType(new RefType(new IntegerType()))),
                                new Compound(new HeapAllocation("a", new VariableExpression("v")),
                                        new Compound(new Print(new VariableExpression("v")), new Print(new VariableExpression("a")))))));

        IStatement ex7 = new Compound(new VariableDeclaration("v", new RefType(new IntegerType())),
                new Compound(new HeapAllocation("v", new ValueExpression(new IntegerValue(20))),
                        new Compound(new VariableDeclaration("a", new RefType(new RefType(new IntegerType()))),
                                new Compound(new HeapAllocation("a", new VariableExpression("v")),
                                        new Compound(new Print(new HeapReading(new VariableExpression("v"))),
                                                new Print(new ArithmeticExpression(1,new HeapReading(new HeapReading(new VariableExpression("a"))), new ValueExpression(new IntegerValue(5)))))))));

        IStatement ex8 = new Compound(new VariableDeclaration("v", new RefType(new IntegerType())),
                new Compound(new HeapAllocation("v", new ValueExpression(new IntegerValue(20))),
                        new Compound(new Print(new HeapReading(new VariableExpression("v"))),
                                new Compound(new HeapWriting("v", new ValueExpression(new IntegerValue(30))),
                                        new Print(new ArithmeticExpression(1, new HeapReading(new VariableExpression("v")), new ValueExpression(new IntegerValue(5))))))));

        IStatement ex9 = new Compound(new VariableDeclaration("v", new RefType(new IntegerType())),
                new Compound(new HeapAllocation("v", new ValueExpression(new IntegerValue(20))),
                        new Compound(new VariableDeclaration("a", new RefType(new RefType(new IntegerType()))),
                                new Compound(new HeapAllocation("a", new VariableExpression("v")),
                                        new Compound(new HeapAllocation("v", new ValueExpression(new IntegerValue(30))),
                                                new Print(new HeapReading(new HeapReading(new VariableExpression("a")))))))));


        IStatement ex10 = new Compound(new VariableDeclaration("v", new IntegerType()),
                new Compound(new Assignment("v", new ValueExpression(new IntegerValue(4))),
                        new Compound(new While(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntegerValue(0))),
                                new Compound(new Print(new VariableExpression("v")), new Assignment("v",new ArithmeticExpression(2, new VariableExpression("v"), new ValueExpression(new IntegerValue(1)))))),
                                new Print(new VariableExpression("v")))));

        IStatement ex11 = new Compound(
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

        try {

        ProgramState prg1 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap(), ex1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller controller1 = new Controller(repo1);

        ProgramState prg2 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap(), ex2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller controller2 = new Controller(repo2);

        ProgramState prg3 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap(), ex3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller controller3 = new Controller(repo3);

        ProgramState prg4 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap(), ex4);
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller controller4 = new Controller(repo4);

        ProgramState prg5 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap(), ex5);
        IRepository repo5 = new Repository(prg5, "log5.txt");
        Controller controller5 = new Controller(repo5);

        ProgramState prg6 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex6);
        IRepository repo6 = new Repository(prg6, "log6.txt");
        Controller controller6 = new Controller(repo6);

        ProgramState prg7 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex7);
        IRepository repo7 = new Repository(prg7, "log7.txt");
        Controller controller7 = new Controller(repo7);

        ProgramState prg8 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex8);
        IRepository repo8 = new Repository(prg8, "log8.txt");
        Controller controller8 = new Controller(repo8);

        ProgramState prg9 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex9);
        IRepository repo9 = new Repository(prg9, "log9.txt");
        Controller controller9 = new Controller(repo9);

        ProgramState prg10 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex10);
        IRepository repo10 = new Repository(prg10, "log10.txt");
        Controller controller10 = new Controller(repo10);

        ProgramState prg11 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex11);
        IRepository repo11 = new Repository(prg11, "log11.txt");
        Controller controller11 = new Controller(repo11);

        MyIDictionary<String, IType> typeEnv = new MyDictionary<>();

            ex1.typeCheck(typeEnv);
            ex2.typeCheck(typeEnv);
            ex3.typeCheck(typeEnv);
            ex4.typeCheck(typeEnv);
            ex5.typeCheck(typeEnv);
            ex6.typeCheck(typeEnv);
            ex7.typeCheck(typeEnv);
            ex8.typeCheck(typeEnv);
            ex9.typeCheck(typeEnv);
            ex10.typeCheck(typeEnv);
            ex11.typeCheck(typeEnv);
            System.out.println("The type check has been done successfully!\n");
            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new RunExample("1", ex1.toString(), controller1));
            menu.addCommand(new RunExample("2", ex2.toString(), controller2));
            menu.addCommand(new RunExample("3", ex3.toString(), controller3));
            menu.addCommand(new RunExample("4", ex4.toString(), controller4));
            menu.addCommand(new RunExample("5", ex5.toString(), controller5));
            menu.addCommand(new RunExample("6", ex6.toString(), controller6));
            menu.addCommand(new RunExample("7", ex7.toString(), controller7));
            menu.addCommand(new RunExample("8", ex8.toString(), controller8));
            menu.addCommand(new RunExample("9", ex9.toString(), controller9));
            menu.addCommand(new RunExample("10", ex10.toString(), controller10));
            menu.addCommand(new RunExample("11", ex11.toString(), controller11));

            menu.show();
        }
        catch (TypeCheckException e) {
            System.out.println(e.getMessage());
        }

    }
}
