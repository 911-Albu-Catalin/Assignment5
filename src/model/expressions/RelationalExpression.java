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
import model.values.IntegerValue;

public class RelationalExpression implements IExpression {
    String operator;
    IExpression exp1, exp2;

    public RelationalExpression(String o, IExpression e1, IExpression e2) {
        operator = o;
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", exp1.toString(), operator.toString(), exp2.toString());
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) throws InvalidOperandTypeException {
        IValue value1 = exp1.eval(symbolTable, heap);
        IValue value2 = exp2.eval(symbolTable, heap);
        if (value1.getType().equals(new IntegerType()) && value2.getType().equals(new IntegerType())) {
            IntegerValue v1 = (IntegerValue) value1;
            IntegerValue v2 = (IntegerValue) value2;
            return switch (operator) {
                case ">" -> new BooleanValue(v1.getValue() > v2.getValue());
                case ">=" -> new BooleanValue(v1.getValue() >= v2.getValue());
                case "<" -> new BooleanValue(v1.getValue() < v2.getValue());
                case "<=" -> new BooleanValue(v1.getValue() <= v2.getValue());
                case "==" -> new BooleanValue(v1.getValue() == v2.getValue());
                case "!=" -> new BooleanValue(v1.getValue() != v2.getValue());
                default -> {
                    throw new InvalidOperationTypeException("Undefined operator between integers!\n");
                }
            };
        }
        throw new InvalidOperandTypeException("Incompatible types!\n");
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if (type1.equals(new IntegerType())) {
            if (type2.equals(new IntegerType()))
                return new BooleanType();
            else
                throw new TypeCheckException("The type of the operands does not match!\n");
        }
        else
            throw new TypeCheckException("The first operand must be an integer!\n");
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(operator, exp1, exp2);
    }
}
