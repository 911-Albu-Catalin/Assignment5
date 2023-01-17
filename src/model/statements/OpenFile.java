package model.statements;

import exceptions.FileException;
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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenFile implements IStatement {
    private IExpression expression;

    public OpenFile(IExpression e) {
        expression = e;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public ProgramState execute(ProgramState currState) throws FileException, FileNotFoundException {
        MyIDictionary<String, IValue> symbolTable = currState.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = currState.getFileTable();
        IValue value = expression.eval(symbolTable, currState.getHeapTable());
        IType type = new StringType();
        if (value.getType().equals(type)) {
            StringValue stringValue = (StringValue) value;
            if (fileTable.isDefined(stringValue))
                throw new VariableDeclarationException("The file is already defined!\n");
            else {
                try {
                    FileReader fileReader = new FileReader(stringValue.getValue());
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    fileTable.add(stringValue, bufferedReader);
                }
                catch (FileNotFoundException ex) {
                    throw new FileException("There is no file found with this name!\n");
                }
            }
        }
        else
            throw new FileException("The expression must be of string type!\n");
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        if (!expression.typeCheck(typeEnv).equals(new StringType()))
            throw new TypeCheckException("OpenFile requires a string expression!\n");
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        IExpression e = expression.deepCopy();
        return new OpenFile(e);
    }
}
