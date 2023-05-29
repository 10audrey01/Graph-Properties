package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {
  private Set<T> verticies;
  private Set<Edge<T>> edges;

  /**
   * Creates a graph with sorted set of verticies and edges.
   *
   * @param verticies the set of verticies
   * @param edges the set of edges
   */
  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = sortVerticiesSet(verticies);
    this.edges = sortEdgesSet(edges);
  }

  /**
   * Gets the set of verticies and sorts it in ascending order.
   *
   * @param verticies the set of verticies.
   * @return the sorted set of verticies
   */
  public Set<T> sortVerticiesSet(Set<T> verticies) {
    Set<T> sortedSet = new HashSet<>();
    List<T> sortedList = new ArrayList<>(verticies);
    Collections.sort(sortedList);
    for (T vertex : sortedList) {
      sortedSet.add(vertex);
    }
    return sortedSet;
  }

  /**
   * Gets the set of edges and sorts it in ascending order, depending on source vertex. If the
   * source vertex is the same, then sort by destination vertex.
   *
   * @param edges the set of edges.
   * @return the sorted set of edges.
   */
  public Set<Edge<T>> sortEdgesSet(Set<Edge<T>> edges) {
    Set<Edge<T>> sortedSet = new HashSet<>();
    List<Edge<T>> sortedList = new ArrayList<>(edges);
    Collections.sort(
        sortedList,
        new Comparator<Edge<T>>() {
          @Override
          public int compare(Edge<T> o1, Edge<T> o2) {
            if (o1.getSource().compareTo(o2.getSource()) == 0) {
              return o1.getDestination().compareTo(o2.getDestination());
            } else {
              return o1.getSource().compareTo(o2.getSource());
            }
          }
        });
    for (Edge<T> edge : sortedList) {
      sortedSet.add(edge);
    }
    return sortedSet;
  }

  /**
   * Finds the set of root verticies in the graph and returns as a set.
   *
   * @return The set of root verticies.
   */
  public Set<T> getRoots() {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    } // if the graph is empty, operation is not supported

    Set<T> roots = new HashSet<>(verticies);

    for (T vertex : verticies) {
      for (Edge<T> edge : edges) {
        if (vertex.compareTo(edge.getDestination()) == 0
            && vertex.compareTo(edge.getSource())
                != 0) { // check if every vertex has in-degree of more than 0
          roots.remove(vertex); // if it does, remove it from the set of roots
        }
      }
    }

    // if the graph is an equivalence relation, add the first vertex of each equivalence class to
    // the set of roots
    if (isEquivalence()) {
      for (T vertex : verticies) {
        Set<T> equivalenceClass = getEquivalenceClass(vertex);
        if (!roots.contains(equivalenceClass.iterator().next())) {
          roots.add(equivalenceClass.iterator().next());
        }
      }
    }

    return roots;
  }

  /**
   * Checks if the graph is reflexive, and returns a boolean result.
   *
   * @return true or false.
   */
  public boolean isReflexive() {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    }
    if (verticies.size() > 0 && edges.size() == 0) {
      return false;
    } else if (verticies.size() == 0) {
      return true;
    }

    for (T vertex : verticies) {
      if (!edges.contains(new Edge<>(vertex, vertex))) { // check if vertex has self loop
        return false;
      }
    }

    return true;
  }

  /**
   * Checks if the graph is symmetric, and returns a boolean result.
   *
   * @return true or false.
   */
  public boolean isSymmetric() {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    }
    if (edges.size() == 0) {
      return true;
    }
    for (Edge<T> edge : edges) {
      Edge<T> temp = new Edge<>(edge.getDestination(), edge.getSource());
      if (!edges.contains(temp)) { // check if every edge has a reverse edge
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the graph is transitive, and returns a boolean result.
   *
   * @return true or false.
   */
  public boolean isTransitive() {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    }
    if (edges.size() == 0) {
      return true;
    }

    // check if every edge has a transitive edge
    for (Edge<T> edge1 : edges) {
      T vertexA = edge1.getSource();
      T vertexB = edge1.getDestination();
      for (Edge<T> edge2 : edges) {
        T vertexC = edge2.getSource();
        T vertexD = edge2.getDestination();

        if (vertexB == vertexC) {
          Edge<T> temp = new Edge<>(vertexA, vertexD);
          if (!edges.contains(temp)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Checks if the graph is anti-symmetric, and returns a boolean result.
   *
   * @return true or false.
   */
  public boolean isAntiSymmetric() {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    }

    if (edges.size() == 0) {
      return true;
    }

    // check if every edge has a reverse edge and if it's a self loop
    for (Edge<T> edge : edges) {
      if (edge.getSource() != edge.getDestination()) {
        if (edges.contains(new Edge<>(edge.getDestination(), edge.getSource()))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Checks if the graph is an equivalence relation, and returns a boolean result.
   *
   * @return true or false.
   */
  public boolean isEquivalence() {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    }

    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Finds the equivalence class of a vertex, and returns it as a set.
   *
   * @param vertex The vertex to find the equivalence class of.
   * @return the equivalence class of the vertex.
   */
  public Set<T> getEquivalenceClass(T vertex) {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    }

    if (!isEquivalence()) {
      return new HashSet<T>();
    }

    Set<T> equivalenceClass = new HashSet<T>();
    for (Edge<T> edge : edges) {
      if (vertex.compareTo(edge.getSource()) == 0) {
        // if the vertex is the source of an edge, add the destination to the equivalence class
        equivalenceClass.add(edge.getDestination());
      }
    }

    return equivalenceClass;
  }

  /**
   * Finds the iterativeBreadthFirstSearch path of the graph, and returns it as a list.
   *
   * @return the set of vertices in the iterativeBreadthFirstSearch path.
   */
  public List<T> iterativeBreadthFirstSearch() {
    Set<T> roots = getRoots();
    T rootNode = roots.iterator().next();
    Queue<T> queue = new Queue<>();
    List<T> visited = new LinkedList<T>();
    int countRootsVisited = 0;

    visited.add(rootNode); // add the root node to the visited list
    countRootsVisited++;
    queue.enqueue(rootNode); // add the root node to the queue

    while (!queue.isEmpty()) {
      for (Edge<T> edge : edges) {
        if (edge.getSource().compareTo(queue.peek()) == 0
            && !visited.contains(edge.getDestination())) {
          visited.add(edge.getDestination());
          queue.enqueue(edge.getDestination());
        }
      }

      if (countRootsVisited < roots.size()) { // if there are more roots to visit
        rootNode = nthElementinSet(roots, countRootsVisited); // root node is the next root
        if (!visited.contains(rootNode)) { // if the root node hasn't been visited
          visited.add(rootNode);
          countRootsVisited++;
          queue.enqueue(rootNode);
        }
      }
      queue.dequeue();
    }
    return visited;
  }

  /**
   * Finds the nth element in a set and returns the data in the element.
   *
   * @param data the set.
   * @param n the index of the element.
   * @return the nth element in the set.
   */
  public T nthElementinSet(Iterable<T> data, int n) {
    int index = 0;
    for (T element : data) {
      if (index == n) {
        return element;
      }
      index++;
    }
    return null;
  }

  /**
   * Finds the iterativeDepthFirstSearch path of the graph, and returns it as a list.
   *
   * @return the set of vertices in the iterativeDepthFirstSearch path.
   */
  public List<T> iterativeDepthFirstSearch() {
    Set<T> roots = getRoots();
    T rootNode = roots.iterator().next();
    Stack<T> stack = new Stack<>();
    List<T> visited = new LinkedList<T>();
    int countRootsVisited = 0;
    int numberOfUnvisitedAdjacentVerticies = 0;

    visited.add(rootNode); // add the root node to the visited list
    countRootsVisited++;
    stack.push(rootNode); // add the root node to the stack

    while (!stack.isEmpty()) {
      for (Edge<T> edge : edges) {
        if (edge.getSource().compareTo(stack.peek()) == 0
            && !visited.contains(edge.getDestination())) { // if the edge is adjacent and unvisited
          visited.add(edge.getDestination());
          stack.push(edge.getDestination());
          numberOfUnvisitedAdjacentVerticies++;
        }
      }
      if (numberOfUnvisitedAdjacentVerticies == 0) { // if there are no more adjacent verticies
        stack.pop();
      }
      if (countRootsVisited < roots.size()) { // if there are more roots to visit
        rootNode = nthElementinSet(roots, countRootsVisited); // root node is the next root
        if (!visited.contains(rootNode)) { // if the root node hasn't been visited
          visited.add(rootNode);
          countRootsVisited++;
          stack.push(rootNode);
        }
      }
      numberOfUnvisitedAdjacentVerticies = 0;
    }
    return visited;
  }

  public List<T> recursiveBFS(
      Set<T> rootSet, T rootNode, Queue<T> queue, List<T> visited, int countRootsVisited) {
    if (queue.isEmpty()) {
      return visited;
    }

    for (Edge<T> edge : edges) {
      if (edge.getSource().compareTo(queue.peek()) == 0
          && !visited.contains(edge.getDestination())) {
        visited.add(edge.getDestination());
        queue.enqueue(edge.getDestination());
      }
    }

    if (countRootsVisited < rootSet.size()) { // if there are more roots to visit
      rootNode = nthElementinSet(rootSet, countRootsVisited); // root node is the next root
      if (!visited.contains(rootNode)) { // if the root node hasn't been visited
        visited.add(rootNode);
        countRootsVisited++;
        queue.enqueue(rootNode);
      }
    }
    queue.dequeue();
    return recursiveBFS(rootSet, rootNode, queue, visited, countRootsVisited);
  }

  public List<T> recursiveBreadthFirstSearch() {
    Set<T> roots = getRoots();
    T rootNode = roots.iterator().next();
    Queue<T> queue = new Queue<>();
    List<T> visited = new LinkedList<T>();
    int countRootsVisited = 0;

    visited.add(rootNode); // add the root node to the visited list
    countRootsVisited++;
    queue.enqueue(rootNode); // add the root node to the queue

    return recursiveBFS(roots, rootNode, queue, visited, countRootsVisited);
  }

  public List<T> recursiveDFS(
      Set<T> rootSet,
      T rootNode,
      Stack<T> stack,
      List<T> visited,
      int countRootsVisited,
      int numberOfUnvisitedAdjacentVerticies) {
    if (stack.isEmpty()) {
      return visited;
    }

    for (Edge<T> edge : edges) {
      if (edge.getSource().compareTo(stack.peek()) == 0
          && !visited.contains(edge.getDestination())) { // if the edge is adjacent and unvisited
        visited.add(edge.getDestination());
        stack.push(edge.getDestination());
        numberOfUnvisitedAdjacentVerticies++;
      }
    }
    if (numberOfUnvisitedAdjacentVerticies == 0) { // if there are no more adjacent verticies
      stack.pop();
    }
    if (countRootsVisited < rootSet.size()) { // if there are more roots to visit
      rootNode = nthElementinSet(rootSet, countRootsVisited); // root node is the next root
      if (!visited.contains(rootNode)) { // if the root node hasn't been visited
        visited.add(rootNode);
        countRootsVisited++;
        stack.push(rootNode);
      }
    }
    numberOfUnvisitedAdjacentVerticies = 0;
    return recursiveDFS(
        rootSet, rootNode, stack, visited, countRootsVisited, numberOfUnvisitedAdjacentVerticies);
  }

  public List<T> recursiveDepthFirstSearch() {
    Set<T> roots = getRoots();
    T rootNode = roots.iterator().next();
    Stack<T> stack = new Stack<>();
    List<T> visited = new LinkedList<T>();
    int countRootsVisited = 0;
    int numberOfUnvisitedAdjacentVerticies = 0;

    visited.add(rootNode); // add the root node to the visited list
    countRootsVisited++;
    stack.push(rootNode); // add the root node to the stack

    return recursiveDFS(
        roots, rootNode, stack, visited, countRootsVisited, numberOfUnvisitedAdjacentVerticies);
  }
}
