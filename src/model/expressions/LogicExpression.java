package model.expressions;

import exceptions.InvalidOperandTypeException;
import exceptions.InvalidOperationTypeException;
import exceptions.TypeCheckException;
import model.state.MyIDictionary;
import model.state.MyIHeap;
import model.types.BooleanType;
import model.types.IType;
import model.types.IntegerType;
import model.values.BooleanValue;
import model.values.IValue;

public class LogicExpression implements IExpression {
    int operationType;
    IExpression expression1;
    IExpression expression2;

    public LogicExpression(int ot, IExpression e1, IExpression e2) {
        if (ot != 1 && ot != 2)
            throw new InvalidOperationTypeException("The logic operation does not exist!\n");
        operationType = ot;
        expression1 = e1;
        expression2 = e2;
    }

    public String toString() {
        String operator = "";
        if (operationType == 1)
            operator = "and";
        else
            operator = "or";
        return expression1.toString() + " " + operator + " " + expression2.toString();
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) {
        IValue evaluation1, evaluation2;
        evaluation1 = expression1.eval(symbolTable, heap);
        if (evaluation1.getType().equals(new BooleanType())) {
            evaluation2 = expression2.eval(symbolTable, heap);
            if (evaluation2.getType().equals(new BooleanType())) {
                BooleanValue wrappedResult1 = (BooleanValue) evaluation1;
                BooleanValue wrappedResult2 = (BooleanValue) evaluation2;
                boolean result1 = wrappedResult1.getValue();
                boolean result2 = wrappedResult2.getValue();

                switch (operationType) {
                    case 1 -> {
                        return new BooleanValue(result1 && result2);
                    }
                    case 2 -> {
                        return new BooleanValue(result1 || result2);
                    }
                    default -> throw new InvalidOperationTypeException("The logic operation does not exist!\n");
                }
            } else {
                throw new InvalidOperandTypeException("Second operand is not a boolean!\n");
            }
        } else {
            throw new InvalidOperandTypeException("First operand is not a boolean!\n");
        }
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);
        if (type1.equals(new IntegerType())) {
            if (type2.equals(new IntegerType()))
                return new IntegerType();
            else
                throw new TypeCheckException("The type of the operands does not match!\n");
        }
        else {
            if (type1.equals(new BooleanType())) {
                if (type2.equals(new BooleanType()))
                    return new BooleanType();
                else
                    throw new TypeCheckException("The type of the operands does not match!\n");
            }
            else
                throw new TypeCheckException("The operands must be int or bool!\n");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(operationType, expression1, expression2);
    }
}
