package nz.ac.auckland.se281.datastructures;

import java.util.HashSet;
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

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  public Set<T> getRoots() {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    }

    Set<T> roots = new HashSet<>(verticies);

    for (T vertex : verticies) {
      for (Edge<T> edge : edges) {
        if (vertex.compareTo(edge.getDestination()) == 0
            && vertex.compareTo(edge.getSource()) != 0) {
          roots.remove(vertex);
        }
      }
    }

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
      if (!edges.contains(new Edge<>(vertex, vertex))) {
        return false;
      }
    }

    return true;
  }

  public boolean isSymmetric() {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    }
    if (edges.size() == 0) {
      return true;
    }
    for (Edge<T> edge : edges) {
      Edge<T> temp = new Edge<>(edge.getDestination(), edge.getSource());
      if (!edges.contains(temp)) {
        return false;
      }
    }
    return true;
  }

  public boolean isTransitive() {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    }
    if (edges.size() == 0) {
      return true;
    }

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

  public boolean isAntiSymmetric() {
    if (verticies.isEmpty() && edges.isEmpty()) {
      throw new UnsupportedOperationException();
    }

    if (edges.size() == 0) {
      return true;
    }

    for (Edge<T> edge : edges) {
      if (edge.getSource() != edge.getDestination()) {
        if (edges.contains(new Edge<>(edge.getDestination(), edge.getSource()))) {
          return false;
        }
      }
    }
    return true;
  }

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
        equivalenceClass.add(edge.getDestination());
      }
    }

    return equivalenceClass;
  }

  public List<T> iterativeBreadthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeDepthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveBreadthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveDepthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }
}
