package model.expressions;

import exceptions.TypeCheckException;
import model.state.MyIDictionary;
import model.state.MyIHeap;
import model.types.IType;
import model.values.IValue;

public class ValueExpression implements IExpression {
    IValue value;

    public ValueExpression(IValue v) {
        value = v;
    }

    public String toString() {
        return value.toString();
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) {
        return value;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        return value.getType();
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }
}
