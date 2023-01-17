package model.statements;

import exceptions.TypeCheckException;
import model.state.MyIDictionary;
import model.state.MyIStack;
import model.state.ProgramState;
import model.types.IType;

public class Compound implements IStatement {
    IStatement first;
    IStatement second;

    public Compound(IStatement f, IStatement s) {
        first = f;
        second = s;
    }

    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState currState) {
        MyIStack<IStatement> stk = currState.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public IStatement deepCopy() {
        return new Compound(first, second);
    }
}
