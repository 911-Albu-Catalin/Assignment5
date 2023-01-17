package model.statements;

import exceptions.TypeCheckException;
import model.expressions.IExpression;
import model.state.MyIDictionary;
import model.state.MyIList;
import model.state.ProgramState;
import model.types.IType;
import model.values.IValue;

public class Print implements IStatement {
    IExpression expression;

    public Print(IExpression e) {
        expression = e;
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState currState) {
        MyIList<IValue> output = currState.getOutput();
        output.append(expression.eval(currState.getSymbolTable(), currState.getHeapTable()));
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new Print(expression);
    }
}
