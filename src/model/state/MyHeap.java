package model.state;

import model.values.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<A, C> implements MyIHeap<Integer, IValue> {
    private HashMap<Integer, IValue> heap;
    private int nextFreeSpace;

    public MyHeap() {
        heap = new HashMap<>();
        nextFreeSpace = 1;
    }

    @Override
    public String toString() {
        if (heap.isEmpty())
            return "{ }" + "\n--------------------------------";
        String returnStr = "{ ";
        int c = -1;
        for (Map.Entry<Integer, IValue> entry : heap.entrySet()) {
            returnStr += entry.getKey() + "->" + entry.getValue();
            c++;
            if (c < heap.size() - 1)
                returnStr += "; ";
            else
                returnStr += " }";
        }
        returnStr += "\n--------------------------------";
        return returnStr;
    }

    @Override
    public Integer add(IValue content) {
        heap.put(nextFreeSpace++, content);
        return nextFreeSpace - 1;
    }

    @Override
    public void update(Integer address, IValue content) {
        heap.put(address, content);
    }

    @Override
    public void remove(Integer address) {
        heap.remove(address);
    }

    @Override
    public IValue lookUp(Integer address) {
        return heap.get(address);
    }

    @Override
    public boolean isDefined(Integer address) {
        return heap.containsKey(address);
    }

    public Set<Integer> keySet() {
        return heap.keySet();
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return heap;
    }

    @Override
    public void setContent(Map<Integer, IValue> newContent) {

    }
}
