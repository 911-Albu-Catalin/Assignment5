package model.statements;

import exceptions.InvalidOperandTypeException;
import exceptions.TypeCheckException;
import model.expressions.IExpression;
import model.state.MyIDictionary;
import model.state.MyIStack;
import model.state.ProgramState;
import model.types.BooleanType;
import model.types.IType;
import model.values.BooleanValue;
import model.values.IValue;

public class If implements IStatement {
    private IExpression condition;
    private IStatement ifBranch;
    private IStatement elseBranch;

    public If(IExpression c, IStatement i, IStatement e) {
        condition = c;
        ifBranch = i;
        elseBranch = e;
    }

    public String toString() {
        return "if (" + condition.toString() + ") then {\n\t" + ifBranch.toString() +
            "\n} else {\n\t" + elseBranch.toString() + "\n}";
    }

    @Override
    public ProgramState execute(ProgramState currState) {
        MyIStack<IStatement> stk = currState.getExeStack();
        MyIDictionary<String, IValue> symbolTable = currState.getSymbolTable();
        IValue result = condition.eval(symbolTable, currState.getHeapTable());
        if (result.getType().equals(new BooleanType())) {
            BooleanValue castResult = (BooleanValue) result;
            if (castResult.getValue())
                stk.push(ifBranch);
            else
                stk.push(elseBranch);
        }
        else
            throw new InvalidOperandTypeException("A condition must evaluate to true or false!\n");
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeExp = condition.typeCheck(typeEnv);
        if (typeExp.equals(new BooleanType())) {
            ifBranch.typeCheck(typeEnv.deepCopy());
            elseBranch.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new TypeCheckException("The condition must be of type bool!\n");
    }

    @Override
    public IStatement deepCopy() {
        return new If(condition, ifBranch, elseBranch);
    }
}
