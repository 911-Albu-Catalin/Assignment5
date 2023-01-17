package repository;

import exceptions.FileException;
import model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Repository implements IRepository {
    private String logFilePath;
    private ArrayList<ProgramState> states;

    public Repository(ProgramState programState, String fileName) {
        states = new ArrayList<ProgramState>();
        states.add(programState);
        logFilePath = fileName;
    }

    public Repository(ArrayList<ProgramState> ps, String fileName) {
        logFilePath = fileName;
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.println("");
            logFile.close();
        }
        catch (IOException e) {
            throw new FileException("Cannot open logFile!\n");
        }
        states = new ArrayList<ProgramState>();
        states.addAll(ps);
    }

    @Override
    public ArrayList<ProgramState> getPrgList() {
        return states;
    }

    @Override
    public void setPrgList(ArrayList<ProgramState> list) {
        states = list;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String filePath) {
        logFilePath = filePath;
    }

//    @Override
//    public ProgramState getCurrProgramState() {
//        if (!states.isEmpty())
//            return states.get(0);
//        else
//            return null;
//    }

    @Override
    public void logPrgStateExec(ProgramState currState) throws FileException, IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println("Id: ");
        logFile.println(currState.getId());
        logFile.println("Execution Stack: ");
        logFile.println(currState.getExeStack().toString());
        logFile.println("Symbol Table: ");
        logFile.println(currState.getSymbolTable().toString());
        logFile.println("Output: ");
        logFile.println(currState.getOutput().toString());
        logFile.println("File Table: ");
        logFile.println(currState.getFileTable().toString());
        logFile.println("Heap Table: ");
        logFile.println(currState.getHeapTable().toString());
        logFile.flush();
        logFile.close();
    }

    @Override
    public int size() {
        return states.size();
    }

    @Override
    public void add(ProgramState prgState) {
        states.add(prgState);
    }

    @Override
    public void clear() {
        states.clear();
    }
}