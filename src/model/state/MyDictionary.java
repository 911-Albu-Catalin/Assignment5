package model.state;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private HashMap<K, V> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<>();
    }

    public MyDictionary(HashMap<K, V> newD) {
        dictionary = newD;
    }

    @Override
    public String toString() {
        if (dictionary.isEmpty())
            return "{ }" + "\n--------------------------------";
        String returnStr = "{ ";
        int c = -1;
        for (Map.Entry<K, V> entry : dictionary.entrySet()) {
            returnStr += entry.getKey() + ": " + entry.getValue();
            c++;
            if (c < dictionary.size() - 1)
                returnStr += ", ";
            else
                returnStr += " }";
        }
        returnStr += "\n--------------------------------";
        return returnStr;
    }

    @Override
    public void add(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public void update(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public void remove(K key) {
        dictionary.remove(key);
    }

    @Override
    public V lookUp(K key) {
        return dictionary.get(key);
    }

    public Set<K> keySet() {
        return dictionary.keySet();
    }

    public V get(K id) {
        return dictionary.get(id);
    }

    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        HashMap<K, V> copy = new HashMap<K, V>();
        for (Map.Entry<K, V> elem : dictionary.entrySet()) {
            K key = elem.getKey();
            V val = elem.getValue();
            copy.put(key, val);
        }
        return new MyDictionary<K, V>(copy);
    }
}
