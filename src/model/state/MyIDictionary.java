package model.state;

import java.util.Map;

public interface MyIDictionary<K, V> {
    void add(K key, V value);
    void update(K key, V value);
    void remove(K key);
    V lookUp(K key);
    boolean isDefined(K key);
    String toString();
    Map<K, V> getContent();
    MyIDictionary<K, V> deepCopy();
}
