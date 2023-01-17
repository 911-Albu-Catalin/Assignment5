package model.statements;

import exceptions.FileException;
import exceptions.InvalidOperandTypeException;
import exceptions.TypeCheckException;
import exceptions.VariableDeclarationException;
import model.expressions.IExpression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.types.IType;
import model.types.IntegerType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntegerValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement {
    private IExpression expression;
    private String variableName;

    public ReadFile(IExpression e, String s) {
        expression = e;
        variableName = s;
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + variableName + ")";
    }

    @Override
    public ProgramState execute(ProgramState currState) throws FileException {
        MyIDictionary<String, IValue> symbolTable = currState.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = currState.getFileTable();
        IType type = new IntegerType();
        if (symbolTable.isDefined(variableName)) {
            if (symbolTable.lookUp(variableName).getType() != type) {
                IValue value = expression.eval(symbolTable, currState.getHeapTable());
                IType iType = new StringType();
                if (value.getType().equals(iType)) {
                    StringValue stringValue = (StringValue) value;
                    if (fileTable.isDefined(stringValue)) {
                        BufferedReader bufferedReader = fileTable.lookUp(stringValue);
                        try {
                            String line = bufferedReader.readLine();
                            int n;
                            if (line.length() == 0)
                                n = 0;
                            else
                                n = Integer.parseInt(line);
                            IntegerValue integerValue = new IntegerValue(n);
                            symbolTable.update(variableName, integerValue);
                        }
                        catch (IOException e) {
                            throw new FileException("File exception!\n");
                        }
                    } else
                        throw new VariableDeclarationException("No file with this name is in the table!\n");
                } else
                    throw new InvalidOperandTypeException("The expression must evaluate to a straing!\n");
            } else
                throw new InvalidOperandTypeException("The variable to read a file must be an integer!\n");
        } else
            throw new VariableDeclarationException("This variable is undefined!\n");
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        if (!expression.typeCheck(typeEnv).equals(new StringType()))
            throw new TypeCheckException("ReadFile requires a string expression!\n");
        if (!typeEnv.lookUp(variableName).equals(new IntegerType()))
            throw new TypeCheckException("ReadFile requires an int variable!\n");
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        IExpression e = expression.deepCopy();
        return new ReadFile(e, variableName);
    }
}
