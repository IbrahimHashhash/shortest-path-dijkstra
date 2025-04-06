package org.example.project3;

public class SinglyLinkedList<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    private static class Node<T> {
        Node<T> next;
        T data;

        Node(T data) {
            this.data = data;
            next = null;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public T getHeadData() {
        if (isEmpty()) throw new RuntimeException("ERROR: List empty");
        return head.data;
    }

    public T getTailData() {
        if (isEmpty()) throw new RuntimeException("ERROR: List empty");
        return tail.data;
    }

    public void addLast(T data) {
        Node<T> newest = new Node<>(data);
        if (isEmpty()) {
            head = tail = newest;
        } else {
            tail.next = newest;
            tail = newest;
        }
        size++;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new RuntimeException("ERROR: List empty");
        }

        T tailData = tail.data;

        if (head == tail) { // If there's only one node
            head = tail = null;
        } else {
            Node<T> tailPrev = head;
            while (tailPrev.next != tail) {
                tailPrev = tailPrev.next;
            }
            tailPrev.next = null;
            tail = tailPrev;
        }
        size--;
        return tailData;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new RuntimeException("ERROR: List empty");
        }

        T headData = head.data;
        head = head.next;
        if (head == null) { // If the list is now empty
            tail = null;
        }
        size--;
        return headData;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
    public void clear(){
        head = tail = null;
        size=0;
    }
    public boolean contains(T element) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    public void add(int index, T data) {
        if (index < 0 || index > size) {  // index must be between 0 and size (inclusive)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            // Insert at the beginning (this is similar to addFirst)
            Node<T> newNode = new Node<>(data);
            newNode.next = head;
            head = newNode;
            if (tail == null) {  // If the list was empty, tail should point to the new node
                tail = head;
            }
        } else if (index == size) {
            // Insert at the end (this is similar to addLast)
            addLast(data);
        } else {
            // Insert at a given position
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            Node<T> newNode = new Node<>(data);
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }
    public void addFirst(T data){
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        if (tail == null) {  // If the list was empty, tail should point to the new node
            tail = head;
        }
        size++;
    }
    public void add(T data) {
        addLast(data);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

}
