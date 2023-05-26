package nz.ac.auckland.se281.datastructures;

public class Queue<T> {
  private Node<T> front;
  private Node<T> back;

  public Queue() {
    this.front = null;
    this.back = null;
  }

  public void enqueue(T data) {
    Node<T> newNode = new Node<T>(data);

    if (back == null) {
      front = newNode;
      back = newNode;
    } else {
      newNode.setPrev(back); // set the new node's previous to the current tail
      back.setNext(newNode); // set the current tail's next to the new node
      back = newNode;
    }
  }

  public void dequeue() {
    if (front == null) {
      System.out.println("Underflow");
    } else {
      front = front.getNext(); // update front to next node

      if (front == null) {
        back = null;
      } else {
        front.setPrev(null); // set the new front's previous to null
      }
    }
  }

  public T peek() {
    if (front == null) {
      System.out.println("Underflow");
    }
    return front.getData();
  }

  public boolean isEmpty() {
    return front == null;
  }

  @Override
  public String toString() {
    String result = "[";
    Node<T> current = front;

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
