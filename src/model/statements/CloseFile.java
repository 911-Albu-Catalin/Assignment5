package model.statements;

import exceptions.FileException;
import exceptions.InvalidOperandTypeException;
import exceptions.TypeCheckException;
import exceptions.VariableDeclarationException;
import model.expressions.IExpression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFile implements IStatement {
    private IExpression expression;

    public CloseFile(IExpression e) {
        expression = e;
    }

    @Override
    public String toString() {
        return "closeFile(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState currState) throws FileException {
        MyIDictionary<String, IValue> symbolTable = currState.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = currState.getFileTable();
        IValue value = expression.eval(symbolTable, currState.getHeapTable());
        IType type = new StringType();
        if (value.getType().equals(type)) {
            StringValue stringValue = (StringValue) value;
            if (fileTable.isDefined(stringValue)) {
                BufferedReader bufferedReader = fileTable.lookUp(stringValue);
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    throw new FileException("Error closing the file!\n");
                }
                fileTable.remove(stringValue);
            } else
                throw new VariableDeclarationException("This file does not exist!\n");
        } else
            throw new InvalidOperandTypeException("The expression must be of string type!\n");
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        if (!expression.typeCheck(typeEnv).equals(new StringType()))
            throw new TypeCheckException("CloseFile requires a string expression!\n");
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        IExpression e = expression.deepCopy();
        return new CloseFile(e);
    }
}
