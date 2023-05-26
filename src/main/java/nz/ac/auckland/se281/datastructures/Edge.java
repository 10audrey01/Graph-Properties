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
   * Creates an edge between two verticies.
   *
   * @param source
   * @param destination
   */
  public Edge(T source, T destination) { // contructor with source and destination vertices
    this.source = source;
    this.destination = destination;
  }

  /**
   * Gets the source vertex.
   *
   * @return
   */
  public T getSource() {
    return source;
  }

  /**
   * Gets the destination vertex.
   *
   * @return
   */
  public T getDestination() {
    return destination;
  }

  /**
   * Finds the hashcode
   *
   * @return the hashcode
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
   * Checks if two edges are equal
   *
   * @param obj
   * @return true if equal, false otherwise
   */
  @Override
  public boolean equals(
      Object obj) { // Override equals() to compare two edges with source and destination
    if (this == obj) return true; // check if obj is the same as this
    if (obj == null) return false; // check if obj is null
    if (getClass() != obj.getClass()) return false;
    Edge other = (Edge) obj; // cast obj to Edge
    if (source == null) {
      if (other.source != null) return false; // compare source and destination
    } else if (!source.equals(other.source)) return false;
    if (destination == null) { // compare source and destination
      if (other.destination != null) return false;
    } else if (!destination.equals(other.destination))
      return false; // compare source and destination
    return true;
  }
}
