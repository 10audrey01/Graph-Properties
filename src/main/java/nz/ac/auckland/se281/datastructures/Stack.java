package nz.ac.auckland.se281.datastructures;

public class Stack<T> {
  private Node<T> top;

  public Stack() {
    this.top = null;
  }

  public void push(T data) {
    Node<T> newNode = new Node<T>(data); // initialise data into new node
    newNode.setNext(top); // put top reference into new node next
    top = newNode; // make new node as top of stack
  }

  public void pop() {
    if (top == null) {
      System.out.println("Stack Underflow");
      return;
    }
    top = top.getNext(); // update top reference to point to next node
  }

  public T peek() {
    if (top == null) {
      System.out.println("Stack Underflow");
      return null;
    }
    return top.getData(); // return data of top node
  }

  public int size() {
    int count = 0;
    Node<T> current = top;
    while (current != null) {
      count++;
      current = current.getNext();
    }
    return count;
  }

  public boolean isEmpty() {
    return top == null;
  }

  @Override
  public String toString() {
    String result = "[";
    Node<T> current = top;

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
