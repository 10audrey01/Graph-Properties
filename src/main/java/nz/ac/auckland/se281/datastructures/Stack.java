package nz.ac.auckland.se281.datastructures;

/**
 * A stack that is composed of nodes and implements the LinkedList class.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each node.
 */
public class Stack<T> {
  private Node<T> top;

  /** Constructor for Stack class with top. */
  public Stack() {
    this.top = null;
  }

  /**
   * Adds data to the stack, making required reference changes.
   *
   * @param data data of the node.
   */
  public void push(T data) {
    Node<T> newNode = new Node<T>(data); // initialise data into new node
    newNode.setNext(top); // put top reference into new node next
    top = newNode; // make new node as top of stack
  }

  /** Removes the top element of the stack. */
  public void pop() {
    if (top == null) { // if stack is empty
      System.out.println("Stack Underflow");
      return;
    }
    top = top.getNext(); // update top reference to point to next node
  }

  /**
   * Finds data of top node, and returns it, also checks if stack is empty.
   *
   * @return top element of the stack.
   */
  public T peek() {
    if (top == null) { // if stack is empty
      System.out.println("Stack Underflow");
      return null;
    }
    return top.getData();
  }

  /**
   * Finds the number of elements of the stack and returns it as an integer.
   *
   * @return size of the stack.
   */
  public int size() {
    int count = 0;
    Node<T> current = top;
    while (current != null) {
      count++;
      current = current.getNext(); // update current to next node
    }
    return count;
  }

  /**
   * Checks if the stack is empty and returns a boolean result.
   *
   * @return true if the stack is empty and false if not.
   */
  public boolean isEmpty() {
    return top == null;
  }
}
