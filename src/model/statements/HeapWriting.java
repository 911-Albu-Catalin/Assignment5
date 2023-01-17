package model.statements;

import exceptions.HeapException;
import exceptions.TypeCheckException;
import model.expressions.IExpression;
import model.state.MyIDictionary;
import model.state.MyIHeap;
import model.state.ProgramState;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapWriting implements IStatement {
    private String variable;
    private IExpression expression;

    public HeapWriting(String v, IExpression e) {
        variable = v;
        expression = e;
    }

    @Override
    public String toString() {
        return "HeapWriting(" + variable + "->" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState currState) throws HeapException {
        MyIDictionary<String, IValue> symbolTable = currState.getSymbolTable();
        MyIHeap<Integer, IValue> heap = currState.getHeapTable();
        IValue value = symbolTable.lookUp(variable);
        if (symbolTable.isDefined(variable)) {
            if (symbolTable.lookUp(variable).getType() instanceof RefType) {
                RefValue refValue = (RefValue) symbolTable.lookUp(variable);
                Integer address = refValue.getAddress();
                if (heap.isDefined(address)) {
                    IValue newValue = expression.eval(symbolTable, heap);
                    if (symbolTable.lookUp(variable).getType().equals(new RefType(value.getType()))) {
                        heap.update(address, newValue);
                    }
                }
                else
                    throw new HeapException("The key does not exist in the Heap!\n");
            }
            else
                throw new HeapException("The variable is not of type Ref!\n");
        }
        else
            throw new HeapException("This variable is not defined!\n");
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeVar = typeEnv.lookUp(variable);
        IType typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new TypeCheckException("HeapWriting: they must be of the same type!\n");
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWriting(variable, expression);
    }
}
