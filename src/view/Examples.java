package view;

import model.expressions.ArithmeticExpression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.types.BooleanType;
import model.types.IntegerType;
import model.types.StringType;
import model.values.BooleanValue;
import model.values.IntegerValue;
import model.values.StringValue;

public class Examples {
    public static IStatement buildExample(IStatement... statements) {
        if (statements.length == 0)
            return new NoOperation();
        if (statements.length == 1)
            return statements[0];
        IStatement finalStatement = new Compound(statements[0], statements[1]);
        for (int i = 2; i < statements.length; ++i)
            finalStatement = new Compound(finalStatement, statements[i]);
        return finalStatement;
    }

    public static IStatement[] exampleList() {
        IStatement example0 = buildExample(
                new VariableDeclaration("v",new IntegerType()),
                new Assignment("v",
                        new ValueExpression(new IntegerValue(2))
                ),
                new Print(new VariableExpression("v"))
        );
        IStatement example1 = buildExample(
                new VariableDeclaration("a",new IntegerType()),
                new VariableDeclaration("b",new IntegerType()),
                new Assignment("a",
                        new ArithmeticExpression(1,
                                new ValueExpression(new IntegerValue(2)),
                                new ArithmeticExpression(3,
                                        new ValueExpression(new IntegerValue(3)),
                                        new ValueExpression(new IntegerValue(5))
                                )
                        )
                ),
                new Assignment("b",
                        new ArithmeticExpression(1,
                                new VariableExpression("a"),
                                new ValueExpression(new IntegerValue(1))
                        )
                ),
                new Print(new VariableExpression("b"))
        );
        IStatement example2 = buildExample(
                new VariableDeclaration("a",new BooleanType()),
                new VariableDeclaration("v", new IntegerType()),
                new Assignment("a",
                        new ValueExpression(new BooleanValue(true))
                ),
                new If(
                        new VariableExpression("a"),
                        new Assignment("v",
                                new ValueExpression(new IntegerValue(2))
                        ),
                        new Assignment("v",
                                new ValueExpression(new IntegerValue(3))
                        )
                ),
                new Print(new VariableExpression("v"))
        );
        IStatement example3 = buildExample(
                new VariableDeclaration("a", new BooleanType()),
                new Assignment("a", new ValueExpression(new IntegerValue(2)))
        );
        IStatement example4 = buildExample(
                new VariableDeclaration("file", new StringType()),
                new Assignment("file", new ValueExpression(new StringValue("D:\\ioana\\2nd year\\MAP\\Lab\\Interpretor\\src\\test.txt"))),
                new OpenFile(new VariableExpression("file")),
                new VariableDeclaration("x", new IntegerType()),
                new ReadFile(new VariableExpression("file"), "x"),
                new Print(new VariableExpression("x")),
                new ReadFile(new VariableExpression("file"), "x"),
                new Print(new VariableExpression("x")),
                new CloseFile(new VariableExpression("file"))
        );
        return new IStatement[]{example0, example1, example2, example3, example4};
    }
}
