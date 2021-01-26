package com.java.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Directed Graph
public class Graph {

  Map<String, Node> nodes = new HashMap<>();
  Map<Node, List<Node>> adjacencyList = new HashMap<>();


  private class Node {
    private String label;

    public Node(String label) {
      this.label = label;
    }

    @Override
    public String toString() {
      return label;
    }
  }

  /**
   * Adding a node in the graph
   */
  public void addNode(String label) {
    Node node = new Node(label);
    nodes.putIfAbsent(label, node);
    adjacencyList.putIfAbsent(node, new ArrayList<>());
  }

  /**
   * Adding an Edge to the nodes
   *
   * @param from
   * @param to
   */
  public void addEdge(String from, String to) {
    Node fromNode = nodes.get(from);
    if (fromNode == null) {
      throw new IllegalArgumentException();
    }
    Node toNode = nodes.get(to);
    if (toNode == null) {
      throw new IllegalArgumentException();
    }
    //Adding edge for uni-directed graph
    adjacencyList.get(fromNode).add(toNode);
    //To implement non-directed graph, then add the edge in the other way round as well
    //adjacencyList.get(toNode).add(fromNode);
  }

  public void print() {
    //Iterate over adjacency list i.e. keys from hashmap(nodes)
    for (Node sourceNode : adjacencyList.keySet()) {
      //targets here mean edges
      ArrayList targets = (ArrayList) adjacencyList.get(sourceNode);
      if (!targets.isEmpty()) {
        System.out.println("Node " + sourceNode + " is connected to " + targets);
      }
    }
  }

  public void removeNode(String label) {
    Node node = nodes.get(label);
    if (node == null) {
      return;
    }
    for (Node sourceNode : adjacencyList.keySet())
    //First remove the edges of the node
    {
      adjacencyList.get(sourceNode).remove(node);
    }
    //Secondly, remove the node from the adjacency list
    adjacencyList.remove(node);
    //Finally, remove it from the nodes hashmap
    nodes.remove(node);
  }

  private void removeEdge(String from, String to) {
    Node fromNode = nodes.get(from);
    Node toNode = nodes.get(to);

    if (fromNode == null || toNode == null) {
      return;
    }
    //get the source(fromNode) node to remove the target(toNode) node
    adjacencyList.get(fromNode).remove(toNode);
  }

  //Lets test it
  public static void main(String q[]) {
    Graph graph = new Graph();
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addNode("D");

    graph.addEdge("A", "B");
    graph.addEdge("B", "C");
    graph.addEdge("A", "D");

    graph.print();
  }
}
