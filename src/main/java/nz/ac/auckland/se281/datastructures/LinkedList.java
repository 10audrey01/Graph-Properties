package nz.ac.auckland.se281.datastructures;

/**
 * A linked list that is composed of nodes as it uses the Node class.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each node.
 */
public class LinkedList<T> {
  private Node<T> head;
  private Node<T> tail;

  /** Create an empty linked list with head and tail set to null. */
  public LinkedList() {
    this.head = null;
    this.tail = null;
  }

  /**
   * Adds a new node to the end of the list, making required reference changes.
   *
   * @param data The data to be added to the list.
   */
  public void add(T data) {
    Node<T> newNode = new Node<T>(data);

    if (head == null) {
      head = newNode;
      tail = newNode;
    } else {
      newNode.setPrev(tail); // set the new node's previous to the current tail
      tail.setNext(newNode); // set the current tail's next to the new node
      newNode.setNext(null); // set the new node's next to null
      tail = newNode;
    }
  }

  /**
   * Gets the data of the node at the specified index and returns the data.
   *
   * @param index The index of the node to get the data from.
   * @return data of the node at the specified index.
   */
  public T get(int index) {
    Node<T> current = head;
    int count = 0;

    while (current != null) {
      if (count == index) {
        return current.getData();
      }
      count++;
      current = current.getNext(); // update current to next node
    }
    throw new IndexOutOfBoundsException();
  }

  /**
   * Inserts a new node at the specified index, making required reference changes.
   *
   * @param index The index to insert the new node at.
   * @param data The data to be added to the list.
   */
  public void insert(int index, T data) {
    if (index >= size()) {
      throw new IndexOutOfBoundsException();
    }
    Node<T> newNode = new Node<T>(data);
    if (index == 0) {
      newNode.setNext(head); // set new node's next to current head
      head = newNode; // set new node as head
    } else {
      Node<T> current = head;
      for (int i = 0; i < index - 1; i++) {
        current = current.getNext();
      }
      newNode.setNext(current.getNext()); // set new node's next to current's next
      newNode.setPrev(current); // set new node's previous to current
      current.setNext(newNode); // set current's next to new node
      current.getNext().setPrev(newNode); // set the node after the new node's previous to new node
    }
  }

  /**
   * Removes the node at the specified index, making required reference changes.
   *
   * @param index The index of the node to be removed.
   */
  public void remove(int index) {
    if (index == 0) {
      head = head.getNext();
    } else {
      Node<T> current = head;
      for (int i = 0; i < index - 1; i++) { // stop at node before the one to be removed
        current = current.getNext();
      }
      Node<T> temp = current.getNext(); // node to be removed
      current.setNext(temp.getNext()); // set current's next to the node after the one to be removed
      temp.getNext()
          .setPrev(current); // set the node after the one to be removed's previous to current
    }
  }

  /**
   * Returns the number of nodes in the list as an integer.
   *
   * @return number of nodes in the list.
   */
  public int size() {
    Node<T> current = head;
    int count = 0;

    while (current != null) {
      count++;
      current = current.getNext();
    }
    return count;
  }

  /**
   * Checks if the list is empty, and returns a boolean result.
   *
   * @return true if the list is empty and false otherwise.
   */
  public boolean isEmpty() {
    if (head == null) {
      return true;
    }
    return false;
  }

  /**
   * Returns the index of the first occurrence of the specified data.
   *
   * @param data The data to be searched for.
   * @return index of the first occurrence of the specified data.
   */
  public int indexOf(T data) {
    for (int i = 0; i < size(); i++) {
      if (get(i).equals(data)) {
        return i;
      }
    }
    return -1; // data not found
  }
}
