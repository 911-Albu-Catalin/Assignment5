package model.expressions;

import exceptions.HeapException;
import exceptions.TypeCheckException;
import model.state.MyIDictionary;
import model.state.MyIHeap;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapReading implements IExpression {
    private IExpression expression;

    public HeapReading(IExpression e) {
        expression = e;
    }

    @Override
    public String toString() {
        return "HeapReading(" + expression.toString() + ")";
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) throws HeapException {
        IValue result = expression.eval(symbolTable, heap);
        if (result instanceof RefValue) {
            Integer address = ((RefValue) result).getAddress();
            if (heap.isDefined(address)) {
                return heap.lookUp(address);
            }
            else
                throw new HeapException("The key does not exist in the heap!\n");
        }
        else
            throw new HeapException("The expression must be evaluated to a RefType!\n");
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType type = expression.typeCheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        }
        else
            throw new TypeCheckException("The rH argument is not a RefType!\n");
    }

    @Override
    public IExpression deepCopy() {
        return new HeapReading(expression);
    }
}
