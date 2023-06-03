package nz.ac.auckland.se281.datastructures;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {
  private T source;
  private T destination;

  /**
   * Creates an edge between two verticies, with a source and destination.
   *
   * @param source the source vertex.
   * @param destination the destination vertex.
   */
  public Edge(T source, T destination) { // constructor with source and destination vertices
    this.source = source;
    this.destination = destination;
  }

  /**
   * Gets the source vertex and returns it.
   *
   * @return source vertex.
   */
  public T getSource() {
    return this.source;
  }

  /**
   * Gets the destination vertex and returns it.
   *
   * @return destination vertex.
   */
  public T getDestination() {
    return this.destination;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((source == null) ? 0 : source.hashCode());
    result = prime * result + ((destination == null) ? 0 : destination.hashCode());
    return result;
  }

  /**
   * Checks if the two edges are equal, and returns a boolean result.
   *
   * @param obj the object to compare.
   * @return true if equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    Edge other = (Edge) obj;

    // if one of the source vertices are null return false
    if (source == null) {
      if (other.source != null) {
        return false;
      }
      // if the source vertices are not equal return false
    } else if (!source.equals(other.source)) {
      return false;
    }
    // if one of the destination vertices are null return false
    if (destination == null) {
      if (other.destination != null) {
        return false;
      }
      // if the destination vertices are not equal return false
    } else if (!destination.equals(other.destination)) {
      return false;
    }
    return true;
  }
}
