package model.statements;

import exceptions.InvalidOperandTypeException;
import exceptions.TypeCheckException;
import model.expressions.IExpression;
import model.state.MyIDictionary;
import model.state.MyIHeap;
import model.state.MyIStack;
import model.state.ProgramState;
import model.types.BooleanType;
import model.types.IType;
import model.values.BooleanValue;
import model.values.IValue;

public class While implements IStatement {
    private IExpression condition;
    private IStatement execution;

    public While(IExpression c, IStatement e) {
        condition = c;
        execution = e;
    }

    @Override
    public String toString() {
        return "while (" + condition.toString() + ") { " + execution.toString() + " }";
    }

    @Override
    public ProgramState execute(ProgramState currState) {
        MyIStack<IStatement> stk = currState.getExeStack();
        MyIDictionary<String, IValue> symbolTable = currState.getSymbolTable();
        MyIHeap<Integer, IValue> heap = currState.getHeapTable();
        IValue result = condition.eval(symbolTable, heap);
        if (result.getType().equals(new BooleanType())) {
            BooleanValue castResult = (BooleanValue) result;
            if (castResult.getValue()) {
                stk.push(new While(condition, execution));
                stk.push(execution);
            }
        }
        else
            throw new InvalidOperandTypeException("A condition must evaluate to true or false!\n");
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeExp = condition.typeCheck(typeEnv);
        if (typeExp.equals(new BooleanType())) {
            execution.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new TypeCheckException("The condition must be of type bool!\n");
    }

    @Override
    public IStatement deepCopy() {
        return new While(condition, execution);
    }
}
