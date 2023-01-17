package controller;

import exceptions.ControllerException;
import exceptions.FileException;
import model.state.MyIStack;
import model.state.ProgramState;
import model.statements.IStatement;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    IRepository repo;
    ExecutorService executor;

    public Controller(IRepository r) {
        repo = r;
    }

    public ProgramState oneStep(ProgramState state) throws FileNotFoundException {
        MyIStack<IStatement> stk = state.getExeStack();
        if (stk.isEmpty())
            throw new ControllerException("The execution stack is empty!\n");
        IStatement currStmt = stk.pop();
        return currStmt.execute(state);
    }

    public void allStep() throws FileException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedPrg(repo.getPrgList());

        while(prgList.size() > 0){
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList((ArrayList<ProgramState>) prgList);
    }



    public void add(ProgramState state) {
        repo.add(state);
    }


    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException {
        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg ->
        {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // save the current programs in the repository
        repo.setPrgList((ArrayList<ProgramState>) prgList);
    }


    public void conservativeGarbageCollector(List<ProgramState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymbolTable().getContent().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeapTable().setContent((HashMap<Integer, IValue>) safeGarbageCollector(symTableAddresses, getAddrFromSymTable(p.getHeapTable().getContent().values()), p.getHeapTable().getContent()));
        });
    }

    public Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream().filter(e -> symTableAddr.contains(e.getKey()))
                .collect((Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }


    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<IValue> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
