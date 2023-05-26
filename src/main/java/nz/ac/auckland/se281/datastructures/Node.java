package nz.ac.auckland.se281.datastructures;

/**
 * The node class that is used in the linked list.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each node.
 */
public class Node<T> {
  private T data;
  private Node<T> prev;
  private Node<T> next;

  /**
   * Constructor for Node class with data, prev and next.
   *
   * @param data data of the node.
   */
  public Node(T data) {
    this.data = data;
    this.prev = null;
    this.next = null;
  }

  /**
   * Gets data of the node and returns it.
   *
   * @return data of the node.
   */
  public T getData() {
    return data;
  }

  /**
   * Sets data of the node, does not return anything.
   *
   * @param data data of the node.
   */
  public void setData(T data) {
    this.data = data;
  }

  /**
   * Gets the next node and returns it.
   *
   * @return next node.
   */
  public Node<T> getNext() {
    return this.next;
  }

  /**
   * Sets the next node, does not return anything.
   *
   * @param next next node.
   */
  public void setNext(Node<T> next) {
    this.next = next;
  }

  /**
   * Gets the previous node and returns it.
   *
   * @return previous node.
   */
  public Node<T> getPrev() {
    return this.prev;
  }

  /**
   * Sets the previous node, does not return anything.
   *
   * @param prev previous node.
   */
  public void setPrev(Node<T> prev) {
    this.prev = prev;
  }
}
