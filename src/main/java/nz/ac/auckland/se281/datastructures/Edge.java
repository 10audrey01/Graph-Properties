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
    return source;
  }

  /**
   * Gets the destination vertex and returns it.
   *
   * @return destination vertex.
   */
  public T getDestination() {
    return destination;
  }

  /**
   * Finds the hashcode, a unique code to hashkey to find the value.
   *
   * @return the hashcode.
   */
  @Override
  public int hashCode() { // Override hashCode()
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
  public boolean equals(
      Object obj) { // Override equals() to compare two edges with source and destination
    if (this == obj) {
      return true; // check if obj is the same as this
    }
    if (obj == null) {
      return false; // check if obj is null
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    Edge other = (Edge) obj; // cast obj to Edge

    if (source == null) {
      if (other.source != null) {
        return false; // compare source and destination
      }
    } else if (!source.equals(other.source)) {
      return false;
    }
    if (destination == null) { // compare source and destination
      if (other.destination != null) {
        return false;
      }
    } else if (!destination.equals(other.destination)) {
      return false; // compare source and destination
    }
    return true;
  }
}
