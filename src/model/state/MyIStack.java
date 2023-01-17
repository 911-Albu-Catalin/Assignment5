package model.state;

public interface MyIStack<T> {
    T pop();
    void push(T item);
    boolean isEmpty();
    int size();
    String toString();
}
