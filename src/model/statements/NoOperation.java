package model.statements;

import exceptions.TypeCheckException;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.types.IType;

public class NoOperation implements IStatement {
    public String toString() {
        return "no operation";
    }

    @Override
    public ProgramState execute(ProgramState currState) {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new NoOperation();
    }
}
