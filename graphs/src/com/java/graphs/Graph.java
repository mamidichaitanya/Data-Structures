package com.java.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

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

  public void traverseDepthFirst(String root) {
    HashSet visitedNodes = new HashSet();
    Node node = nodes.get(root);
    if (node == null) {
      return;
    }
    traverseDepthFirstWithRecursion(node, visitedNodes);
  }

  // recursive traversal
  private void traverseDepthFirstWithRecursion(Node root, HashSet visitedNodes) {
    //visited nodes
    System.out.println(root);
    visitedNodes.add(root);

    for (Node node : adjacencyList.get(root)) {
      if (!visitedNodes.contains(node)) {
        traverseDepthFirstWithRecursion(node, visitedNodes);
      }
    }
  }

  // iterative traversal using stack
  private void traverseDepthFirstWithInIteration(String root) {

    Node node = nodes.get(root);
    if (node == null) {
      return;
    }
    Set<Node> visited = new HashSet<>();
    Stack<Node> stack = new Stack<>();
    stack.push(node);

    while ((!stack.empty())) {
      Node currentNode = stack.pop();

      if (visited.contains(currentNode)) {
        continue;
      }
      System.out.println(currentNode);
      visited.add(currentNode);

      for (Node neighbour : adjacencyList.get(currentNode)) {
        if (!visited.contains(neighbour)) {
          stack.push(neighbour);
        }
      }
    }

  }

  //Lets test it
  public static void main(String q[]) {
    Graph graph = new Graph();
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addNode("D");

    graph.addEdge("A", "B");
    graph.addEdge("B", "D");
    graph.addEdge("D", "C");
    graph.addEdge("A", "C");

    graph.print();

    System.out.println("====DFS using recursion=====");
    graph.traverseDepthFirst("A");
    System.out.println("====DFS using iterative(stack)=====");
    graph.traverseDepthFirstWithInIteration("A");

  }
}
