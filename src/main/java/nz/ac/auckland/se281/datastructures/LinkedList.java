package nz.ac.auckland.se281.datastructures;

public class LinkedList<T> {
  private Node<T> head;
  private Node<T> tail;

  public LinkedList() {
    this.head = null;
    this.tail = null;
  }

  public void add(T data) {
    Node<T> newNode = new Node<T>(data);

    if (head == null) {
      head = newNode;
      tail = newNode;
    } else {
      newNode.setPrev(tail);
      tail.setNext(newNode);
      newNode.setNext(null);
      tail = newNode;
    }
  }

  public T get(int index) {
    Node<T> current = head;
    int count = 0;

    while (current != null) {
      if (count == index) {
        return current.getData();
      }
      count++;
      current = current.getNext();
    }
    throw new IndexOutOfBoundsException();
  }

  public void insert(int index, T data) { // update after implementing size()
    if (index >= size()) {
      throw new IndexOutOfBoundsException();
    }
    Node<T> newNode = new Node<T>(data);
    if (index == 0) {
      newNode.setNext(head);
      head = newNode;
    } else {
      Node<T> current = head;
      for (int i = 0; i < index - 1; i++) {
        current = current.getNext();
      }
      newNode.setNext(current.getNext());
      newNode.setPrev(current);
      current.setNext(newNode);
      current.getNext().setPrev(newNode);
    }
  }

  public void remove(int index) {
    if (index == 0) {
      head = head.getNext();
    } else {
      Node<T> current = head;
      for (int i = 0; i < index - 1; i++) {
        current = current.getNext();
      }
      Node<T> temp = current.getNext();
      current.setNext(temp.getNext());
      temp.getNext().setPrev(current);
    }
  }

  public int size() {
    Node<T> current = head;
    int count = 0;

    while (current != null) {
      count++;
      current = current.getNext();
    }
    return count;
  }

  public boolean isEmpty() {
    if (head == null) {
      return true;
    }
    return false;
  }

  public int indexOf(T data) {
    for (int i = 0; i < size(); i++) {
      if (get(i).equals(data)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public String toString() {
    String result = "[";
    Node<T> current = head;

    while (current != null) {
      result += current.getData();

      if (current.getNext() != null) {
        result += ", ";
      }
      current = current.getNext();
    }
    return result + "]";
  }
}
