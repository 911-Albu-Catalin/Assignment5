package repository;
import exceptions.FileException;
import model.state.ProgramState;

import java.io.IOException;
import java.util.ArrayList;

public interface IRepository {
//    ProgramState getCurrProgramState();
    int size();
    void add(ProgramState prgState);
    void clear();
    ArrayList<ProgramState> getPrgList();
    void setPrgList(ArrayList<ProgramState> list);
    public void logPrgStateExec(ProgramState currState) throws FileException, IOException;
}
