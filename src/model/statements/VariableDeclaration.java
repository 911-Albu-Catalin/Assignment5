package model.statements;

import exceptions.TypeCheckException;
import exceptions.VariableDeclarationException;
import model.state.MyIDictionary;
import model.state.MyIHeap;
import model.state.ProgramState;
import model.types.*;
import model.values.*;

public class VariableDeclaration implements IStatement {
    private String identifier;
    private IType type;

    public VariableDeclaration(String i, IType t) {
        identifier = i;
        type = t;
    }

    public String toString() {
        return type.toString() + " " + identifier;
    }

    @Override
    public ProgramState execute(ProgramState currState) {
        MyIDictionary<String, IValue> symbolTable = currState.getSymbolTable();
        MyIHeap<Integer, IValue> heap = currState.getHeapTable();
        if (symbolTable.isDefined(identifier)) {
            throw new VariableDeclarationException("Variable is already assigned!\n");
        }
        else {
            symbolTable.add(identifier, type.defaultValue());
//            if (type.equals((new IntegerType()))) {
//                symbolTable.add(identifier, new IntegerValue(0));
//            }
//            else if (type.equals((new BooleanType())))
//            {
//                symbolTable.add(identifier, new BooleanValue(false));
//            }
//            else if (type.equals((new StringType()))) {
//                symbolTable.add(identifier, new StringValue(""));
//            }
//            else if (type.equals((new RefType()))) {
//                Integer i = heap.add(identifier);
//                symbolTable.add(identifier, new RefValue());
//            }
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException {
        typeEnv.update(identifier, type);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclaration(identifier, type);
    }
}
