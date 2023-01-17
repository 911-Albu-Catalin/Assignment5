package model.state;

import java.util.Map;

public interface MyIHeap<A, C> {
    Integer add(C content);
    void update(A address, C content);
    void remove(A address);
    C lookUp(A address);
    boolean isDefined(A address);
    Map<A, C> getContent();
    void setContent(Map<A, C> newContent);
    String toString();
}
