package model.statements;

import exceptions.TypeCheckException;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.types.IType;

import java.io.FileNotFoundException;

public interface IStatement {
    ProgramState execute(ProgramState currState) throws FileNotFoundException;
    MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws TypeCheckException;
    IStatement deepCopy();
}
