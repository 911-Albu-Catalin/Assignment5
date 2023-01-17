package model.expressions;

import exceptions.DivisionByZeroException;
import exceptions.InvalidOperandTypeException;
import exceptions.InvalidOperationTypeException;
import exceptions.TypeCheckException;
import model.state.MyIDictionary;
import model.state.MyIHeap;
import model.types.BooleanType;
import model.types.IType;
import model.types.IntegerType;
import model.values.IValue;
import model.values.IntegerValue;

public class ArithmeticExpression implements IExpression {
    int operationType;
    IExpression expression1;
    IExpression expression2;

    public ArithmeticExpression(int ot, IExpression e1, IExpression e2) {
        if (ot != 1 && ot != 2 && ot != 3 && ot != 4)
            throw new InvalidOperationTypeException("The arithmetic operation does not exist!\n");
        operationType = ot;
        expression1 = e1;
        expression2 = e2;
    }

    public String toString() {
        String operator = "";
        switch (operationType) {
            case 1 -> {
                operator = "+";
            }
            case 2 -> {
                operator = "-";
            }
            case 3 -> {
                operator = "*";
            }
            case 4 -> {
                operator = "/";
            }
        }
        return  expression1.toString() + " " + operator + expression2.toString();
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) {
        IValue evaluation1, evaluation2;
        evaluation1 = expression1.eval(symbolTable, heap);
        if (evaluation1.getType().equals(new IntegerType())) {
            evaluation2 = expression2.eval(symbolTable, heap);
            if (evaluation2.getType().equals(new IntegerType())) {
                IntegerValue wrappedResult1 = (IntegerValue) evaluation1;
                IntegerValue wrappedResult2 = (IntegerValue) evaluation2;
                int result1 = wrappedResult1.getValue();
                int result2 = wrappedResult2.getValue();

                switch (operationType) {
                    case 1 -> {
                        return new IntegerValue(result1 + result2);
                    }
                    case 2 -> {
                        return new IntegerValue(result1 - result2);
                    }
                    case 3 -> {
                        return new IntegerValue(result1 * result2);
                    }
                    case 4 -> {
                        if (result2 == 0)
                            throw new DivisionByZeroException("Division by zero is not permitted!\n");
                        else
                            return new IntegerValue(result1 / result2);
                    }
                    default -> throw new InvalidOperationTypeException("The arithmetic operation does not exist!\n");
                }
            } else {
                throw new InvalidOperandTypeException("Second operand is not an integer!\n");
            }
        } else {
            throw new InvalidOperandTypeException("First operand is not an integer!\n");
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
        return new ArithmeticExpression(operationType, expression1, expression2);
    }
}
