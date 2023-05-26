package nz.ac.auckland.se281.datastructures;

/**
 * A queue that is composed of nodes and implements the LinkedList class.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each node.
 */
public class Queue<T> {
  private Node<T> front;
  private Node<T> back;

  /** Constructor for Queue class with front and back. */
  public Queue() {
    this.front = null;
    this.back = null;
  }

  /**
   * Adds data to the queue, making required reference changes.
   *
   * @param data
   */
  public void enqueue(T data) {
    Node<T> newNode = new Node<T>(data);

    if (back == null) { // if the queue is empty
      front = newNode;
      back = newNode;
    } else {
      newNode.setPrev(back);
      back.setNext(newNode);
      back = newNode; // set the new node as the new tail
    }
  }

  /** Removes the front element of the queue, making required reference changes. */
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
   * Returns the front element of the queue, also checks if queue is empty.
   *
   * @return front element of the queue.
   */
  public T peek() {
    if (front == null) {
      System.out.println("Underflow");
    }
    return front.getData();
  }

  /**
   * Checks if the queue is empty returns a boolean result.
   *
   * @return true if the queue is empty and false otherwise.
   */
  public boolean isEmpty() {
    return front == null;
  }
}
