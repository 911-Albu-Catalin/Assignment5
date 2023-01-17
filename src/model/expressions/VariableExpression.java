package model.expressions;

import exceptions.TypeCheckException;
import model.state.MyIDictionary;
import model.state.MyIHeap;
import model.types.IType;
import model.values.IValue;

public class VariableExpression implements IExpression {
    String identifier = "";

    public VariableExpression(String i) {
        identifier = i;
    }

    public String toString() {
        return identifier;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) {
        return symbolTable.lookUp(identifier);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        return typeEnv.lookUp(identifier);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(identifier);
    }
}
