package model.expressions;

import exceptions.TypeCheckException;
import model.state.MyIDictionary;
import model.state.MyIHeap;
import model.types.IType;
import model.values.IValue;

public interface IExpression {
    IValue eval(MyIDictionary<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap);
    IType typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException;
    IExpression deepCopy();
}
