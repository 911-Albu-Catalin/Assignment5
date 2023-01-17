package model.statements;

import exceptions.TypeCheckException;
import model.state.MyIDictionary;
import model.state.MyStack;
import model.state.ProgramState;
import model.types.IType;
import model.values.IValue;

import java.io.FileNotFoundException;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement stmt) {
        statement = stmt;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState currState) throws FileNotFoundException {
        MyIDictionary<String, IValue> newSymTable = currState.getSymbolTable().deepCopy();
        return new ProgramState(new MyStack<IStatement>(), newSymTable, currState.getOutput(), currState.getFileTable(), currState.getHeapTable(), statement);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        IStatement stmt = statement.deepCopy();
        return new ForkStatement(stmt);
    }
}
