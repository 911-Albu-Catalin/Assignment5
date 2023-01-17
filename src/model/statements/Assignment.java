package model.statements;

import exceptions.AssignmentException;
import exceptions.TypeCheckException;
import model.expressions.IExpression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.types.IType;
import model.values.IValue;

public class Assignment implements IStatement {
    private String identifier;
    private IExpression expression;

    public Assignment(String i, IExpression e) {
        identifier = i;
        expression = e;
    }

    public String toString() {
        return identifier + " = " + expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState currState) {
        MyIDictionary<String, IValue> symbolTable = currState.getSymbolTable();
        if (symbolTable.isDefined(identifier)) {
            IValue result = expression.eval(symbolTable, currState.getHeapTable());
            IType resultType = result.getType();
            IType identifierType = symbolTable.lookUp(identifier).getType();
//            symbolTable.update(identifier, result);
            if (resultType.equals((identifierType)))
                symbolTable.update(identifier, result);
            else
                throw new AssignmentException("The type of the variable does not match with the type of the expression!\n");
        }
        else {
            throw new AssignmentException("The variable " + identifier + " was not declared before the assignment!\n");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType typeVar = typeEnv.lookUp(identifier);
        IType typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new TypeCheckException("Assignment: right hand side and left hand side have different types!\n");
    }

    @Override
    public IStatement deepCopy() {
        return new Assignment(identifier, expression);
    }
}
