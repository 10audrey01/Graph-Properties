package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

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
  private HashMap<Integer, ArrayList<Integer>> adjacencyList;
  private TreeMap<Integer, ArrayList<Integer>> sortedAdjacencyList;

  /**
   * Creates a graph with sorted set of verticies and edges.
   *
   * @param verticies the set of verticies.
   * @param edges the set of edges.
   */
  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.adjacencyList = getAdjacencyList(edges);
    this.sortedAdjacencyList = sortedAdjacencyList();
    this.verticies = sortVertices();
    for (T vertex : this.verticies) {
      System.out.println(vertex);
    }
    this.edges = sortEdges();
    for (Edge<T> edge : this.edges) {
      System.out.println(edge.getSource() + " --> " + edge.getDestination());
    }
  }

  /**
   * Creates an adjacency list that represents the graph, with key being the source vertex and value
   * being the list of destination verticies.
   *
   * @param edges the set of edges.
   * @return the adjacency list.
   */
  public HashMap<Integer, ArrayList<Integer>> getAdjacencyList(Set<Edge<T>> edges) {
    HashMap<Integer, ArrayList<Integer>> adjacencyList = new HashMap<>();
    Set<Edge<Integer>> edgesInteger = new HashSet<>();

    for (Edge<T> edge : edges) {
      edgesInteger.add(
          new Edge<Integer>(
              Integer.parseInt(edge.getSource().toString()),
              Integer.parseInt(edge.getDestination().toString()))); // convert T to Integer
    }

    for (Edge<Integer> edge : edgesInteger) {
      // if the source vertex is already in the adjacency list,
      // add the destination vertex to the list
      if (adjacencyList.containsKey(edge.getSource())) {
        adjacencyList.get(edge.getSource()).add(edge.getDestination());
      } else {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(edge.getDestination());
        adjacencyList.put(edge.getSource(), list);
      }
    }

    return adjacencyList;
  }

  /**
   * Sorts the adjacency list by key, in ascending order.
   *
   * @return the sorted adjacency list.
   */
  TreeMap<Integer, ArrayList<Integer>> sortedAdjacencyList() {
    TreeMap<Integer, ArrayList<Integer>> sortedAdjacencyList = new TreeMap<>();

    sortedAdjacencyList.putAll(adjacencyList); // sort the adjacency list by key

    return sortedAdjacencyList;
  }

  /**
   * Sorts the verticies in ascending order.
   *
   * @return the sorted set of verticies.
   */
  public Set<T> sortVertices() {
    Set<T> sortedVerticiesSet = new LinkedHashSet<>();
    Set<Integer> sortedKeySet = sortedAdjacencyList.keySet();

    for (Integer vertex : sortedKeySet) {
      sortedVerticiesSet.add((T) vertex); // convert Integer to T
    }

    return sortedVerticiesSet;
  }

  /**
   * Sorts the edges in ascending order.
   *
   * @return the sorted set of edges.
   */
  public Set<Edge<T>> sortEdges() {
    Set<Edge<T>> sortedEdges = new LinkedHashSet<>();
    List<Edge<Integer>> sortedEdgesList = new ArrayList<>();

    for (Integer vertex : sortedAdjacencyList.keySet()) {
      // sort the list of destination verticies for each source vertex in ascending order
      Collections.sort(sortedAdjacencyList.get(vertex));
      for (Integer destination : sortedAdjacencyList.get(vertex)) {
        sortedEdgesList.add(new Edge<Integer>(vertex, destination));
      }
    }

    for (Edge<Integer> edge : sortedEdgesList) {
      sortedEdges.add((Edge<T>) edge); // convert Integer to T
    }

    return sortedEdges;
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

    Set<T> roots = new LinkedHashSet<>(verticies);

    for (T vertex : verticies) {
      for (Edge<T> edge : edges) {
        // check if every vertex is a destination for an edge, if so in-degree is not 0
        if (vertex.compareTo(edge.getDestination()) == 0) {
          roots.remove(vertex); // if in degree is not zero, remove it from the set of roots
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

        if (vertexB.equals(vertexC) && !edges.contains(new Edge<T>(vertexA, vertexD))) {
          Edge<T> temp = new Edge<T>(vertexA, vertexD);
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
      // if the graph is not an equivalence relation, there is no equivalence class
      return new HashSet<T>();
    }

    Set<T> equivalenceClass = new LinkedHashSet<T>();
    for (Edge<T> edge : edges) {
      // add all vertices that are connected to the vertex to the equivalence class
      if (edge.getSource().toString().compareTo(vertex.toString()) == 0) {
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
      while (!queue.isEmpty()) {
        for (Edge<T> edge : edges) {
          if (edge.getSource().compareTo(queue.peek()) == 0
              && !visited.contains(edge.getDestination())) {
            visited.add(edge.getDestination());
            queue.enqueue(edge.getDestination());
          }
        }
        queue.dequeue();
      }

      if (countRootsVisited < roots.size()) { // if there are more roots to visit
        rootNode = nthElementinSet(roots, countRootsVisited); // root node is the next root
        if (!visited.contains(rootNode)) { // if the root node hasn't been visited
          visited.add(rootNode);
          countRootsVisited++;
          queue.enqueue(rootNode);
        }
      }
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
      while (!stack.isEmpty()) {
        for (Edge<T> edge : edges) {
          if (edge.getSource().compareTo(stack.peek()) == 0
              && !visited.contains(
                  edge.getDestination())) { // if the edge is adjacent and unvisited
            visited.add(edge.getDestination());
            stack.push(edge.getDestination());
            numberOfUnvisitedAdjacentVerticies++;
          }
        }
        if (numberOfUnvisitedAdjacentVerticies == 0) { // if there are no more adjacent verticies
          stack.pop();
        }
        numberOfUnvisitedAdjacentVerticies = 0;
      }
      if (countRootsVisited < roots.size()) { // if there are more roots to visit
        rootNode = nthElementinSet(roots, countRootsVisited); // root node is the next root
        if (!visited.contains(rootNode)) { // if the root node hasn't been visited
          visited.add(rootNode);
          countRootsVisited++;
          stack.push(rootNode);
        }
      }
    }
    return visited;
  }

  /**
   * Finds the recursiveBreadthFirstSearch path of the graph, and returns it as a list, the helper
   * recursive method for recursiveBreadthFirstSearch.
   *
   * @param rootSet the set of root nodes.
   * @param rootNode the current root node being traversed.
   * @param queue the queue of verticies to be visited.
   * @param visited the list of verticies that have been visited.
   * @param countRootsVisited the number of roots that have been visited.
   * @return the intermediate list of vertices in the recursiveBreadthFirstSearch path.
   */
  public List<T> recursiveHelperBreadthFirstSearch(
      Set<T> rootSet, T rootNode, Queue<T> queue, List<T> visited, int countRootsVisited) {
    if (queue.isEmpty()) {
      return visited;
    }
    while (!queue.isEmpty()) {
      for (Edge<T> edge : edges) {
        if (edge.getSource().compareTo(queue.peek()) == 0
            && !visited.contains(edge.getDestination())) {
          visited.add(edge.getDestination());
          queue.enqueue(edge.getDestination());
        }
      }
      queue.dequeue();
    }

    if (countRootsVisited < rootSet.size()) { // if there are more roots to visit
      rootNode = nthElementinSet(rootSet, countRootsVisited); // root node is the next root
      if (!visited.contains(rootNode)) { // if the root node hasn't been visited
        visited.add(rootNode);
        countRootsVisited++;
        queue.enqueue(rootNode);
      }
    }

    return recursiveHelperBreadthFirstSearch(rootSet, rootNode, queue, visited, countRootsVisited);
  }

  /**
   * Finds the recursiveBreadthFirstSearch path of the graph, and returns it as a list, initialises
   * the variables required for helper method recursiveBFS.
   *
   * @return the list of vertices in the recursiveBreadthFirstSearch path.
   */
  public List<T> recursiveBreadthFirstSearch() {
    Set<T> roots = getRoots();
    T rootNode = roots.iterator().next();
    Queue<T> queue = new Queue<>();
    List<T> visited = new LinkedList<T>();
    int countRootsVisited = 0;

    visited.add(rootNode); // add the root node to the visited list
    countRootsVisited++;
    queue.enqueue(rootNode); // add the root node to the queue

    return recursiveHelperBreadthFirstSearch(roots, rootNode, queue, visited, countRootsVisited);
  }

  /**
   * Finds the recursiveDepthFirstSearch path of the graph, and returns it as a list, the helper
   * recursive method for recursiveDepthFirstSearch.
   *
   * @param rootSet the set of root nodes.
   * @param rootNode the current root node being traversed.
   * @param stack the stack of verticies to be visited.
   * @param visited the list of verticies that have been visited.
   * @param countRootsVisited the number of roots that have been visited.
   * @param numberOfUnvisitedAdjacentVerticies the number of unvisited adjacent verticies.
   * @return the intermediate list of vertices in the recursiveDepthFirstSearch path.
   */
  public List<T> recursiveHelperDepthFirstSearch(
      Set<T> rootSet,
      T rootNode,
      Stack<T> stack,
      List<T> visited,
      int countRootsVisited,
      int numberOfUnvisitedAdjacentVerticies) {
    if (stack.isEmpty()) {
      return visited;
    }

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
      numberOfUnvisitedAdjacentVerticies = 0;
    }

    if (countRootsVisited < rootSet.size()) { // if there are more roots to visit
      rootNode = nthElementinSet(rootSet, countRootsVisited); // root node is the next root
      if (!visited.contains(rootNode)) { // if the root node hasn't been visited
        visited.add(rootNode);
        countRootsVisited++;
        stack.push(rootNode);
      }
    }
    return recursiveHelperDepthFirstSearch(
        rootSet, rootNode, stack, visited, countRootsVisited, numberOfUnvisitedAdjacentVerticies);
  }

  /**
   * Finds the recursiveDepthFirstSearch path of the graph, and returns it as a list, initialises
   * the variables required for helper method recursiveDFS.
   *
   * @return the list of vertices in the recursiveDepthFirstSearch path.
   */
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

    return recursiveHelperDepthFirstSearch(
        roots, rootNode, stack, visited, countRootsVisited, numberOfUnvisitedAdjacentVerticies);
  }
}
