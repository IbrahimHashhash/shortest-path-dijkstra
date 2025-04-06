package org.example.project3;

class HashEntry<K, V> {
    private K key;
    private V value;

    public HashEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
public class HashTable<K, V> {
    private static final int INITIAL_SIZE = 50;  // Initial table size
    private static final double LOAD_FACTOR = 0.75;  // Threshold to trigger rehash
    private HashEntry<K, V>[] table;
    private int size;

    // Constructor
    public HashTable() {
        table = new HashEntry[INITIAL_SIZE];
        size = 0;
    }

    private int getIndex(K key) {
        int hash = (key.hashCode() % table.length + table.length) % table.length;
        return hash;
    }

    public V get(K key) {
        int hash = getIndex(key);
        int i = 0; // Quadratic probing step counter

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                return table[hash].getValue();
            }
            i++;
            hash = (getIndex(key) + i * i) % table.length; // Quadratic probing
        }
        return null; // Key not found
    }

    public void put(K key, V value) {
        // Check if we need to rehash
        if ((double) size / table.length >= LOAD_FACTOR) {
            rehash();
        }

        int hash = getIndex(key);
        int i = 0; // Quadratic probing step counter

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                table[hash].setValue(value); // Update value if key exists
                return;
            }
            i++;
            hash = (getIndex(key) + i * i) % table.length; // Quadratic probing
        }
        table[hash] = new HashEntry<>(key, value);
        size++;
    }

    private void rehash() {
        HashEntry<K, V>[] oldTable = table;
        table = new HashEntry[table.length * 2]; // Double the table size
        size = 0;

        for (HashEntry<K, V> entry : oldTable) {
            if (entry != null) {
                put(entry.getKey(), entry.getValue()); // Re-insert entries into the new table
            }
        }
    }

    public boolean contains(K key) {
        int hash = getIndex(key);
        int i = 0; // Quadratic probing step counter

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                return true;
            }
            i++;
            hash = (getIndex(key) + i * i) % table.length; // Quadratic probing
        }
        return false;
    }

    public void clear() {
        table = new HashEntry[INITIAL_SIZE]; // Reset to initial size
        size = 0;
    }

    public int getSize() {
        return size;
    }
}
