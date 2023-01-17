package model.state;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {
    private ArrayList<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public String toString() {
        String returnStr = "[";
        for (T item : list)
            returnStr += item.toString() + " ";
        returnStr += "]";
        return returnStr;
    }

    @Override
    public void append(T item) {
        list.add(item);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }
}
