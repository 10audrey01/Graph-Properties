package nz.ac.auckland.se281.datastructures;

public class Node<T> {
  private T data;
  private Node<T> prev;
  private Node<T> next;

  public Node(T data) {
    this.data = data;
    this.prev = null;
    this.next = null;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Node<T> getNext() {
    return this.next;
  }

  public void setNext(Node<T> next) {
    this.next = next;
  }

  public Node<T> getPrev() {
    return this.prev;
  }

  public void setPrev(Node<T> prev) {
    this.prev = prev;
  }

  @Override
  public String toString() {
    return data.toString();
  }
}
