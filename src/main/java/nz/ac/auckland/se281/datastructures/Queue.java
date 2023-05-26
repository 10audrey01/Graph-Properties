package nz.ac.auckland.se281.datastructures;

public class Queue<T> {
  private Node<T> front;
  private Node<T> back;

  /** Constructor for Queue class with front and back. */
  public Queue() {
    this.front = null;
    this.back = null;
  }

  /**
   * Adds data to the queue.
   *
   * @param data
   */
  public void enqueue(T data) {
    Node<T> newNode = new Node<T>(data);

    if (back == null) { // if the queue is empty
      front = newNode; // set the new node as the front and back
      back = newNode; // set the new node as the front and back
    } else {
      newNode.setPrev(back); // set the new node's previous to the current tail
      back.setNext(newNode); // set the current tail's next to the new node
      back = newNode; // set the new node as the new tail
    }
  }

  /** Removes the front element of the queue. */
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

  /**
   * Returns the front element of the queue.
   *
   * @return front element of the queue
   */
  public T peek() {
    if (front == null) {
      System.out.println("Underflow");
    }
    return front.getData();
  }

  /**
   * Checks if the queue is empty.
   *
   * @return true if the queue is empty and false otherwise
   */
  public boolean isEmpty() {
    return front == null;
  }
}
