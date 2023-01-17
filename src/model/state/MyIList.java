package model.state;

public interface MyIList<T> {
    void append(T item);
    boolean isEmpty();
    T get(int index);
    int size();
    String toString();
}
