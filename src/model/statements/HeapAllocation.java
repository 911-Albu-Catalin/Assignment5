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

public class HeapAllocation implements IStatement {
    private String variable;
    private IExpression expression;

    public HeapAllocation(String v, IExpression e) {
        variable = v;
        expression = e;
    }

    @Override
    public String toString() {
        return "new (" + variable + ", " + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState currState) throws HeapException {
        MyIDictionary<String, IValue> symbolTable = currState.getSymbolTable();
        MyIHeap<Integer, IValue> heap = currState.getHeapTable();
        if (symbolTable.isDefined(variable)) {
            IType varType = symbolTable.lookUp(variable).getType();
            if (varType instanceof RefType) {
                RefValue varValue = (RefValue) symbolTable.lookUp(variable);
                IValue result = expression.eval(symbolTable, heap);
                IType resultType = result.getType();
                if (varValue.getLocationType().equals(resultType)) {
                    Integer newAddress = heap.add(result);
                    symbolTable.update(variable, new RefValue(newAddress, resultType));
                }
                else
                    throw new HeapException("The type does not match!\n");
            }
            else
                throw new HeapException("The variable is not of type Ref!\n");
        }
        else
            throw new HeapException("The variable does not exist in the symbol table!\n");
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeVar = typeEnv.lookUp(variable);
        IType typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new TypeCheckException("HeapAllocation: they must be of the same type!\n");
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocation(variable, expression);
    }
}
