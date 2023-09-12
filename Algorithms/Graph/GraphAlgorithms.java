import java.util.Set;
import java.util.List;
import java.util.Queue;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Edan Eyal
 * @userid eeyal3
 * @GTID 903754556
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Please enter a non-null start vertex and graph!");
        }
        if (!(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("The start vertex was not found in the provided graph.");
        }
        Set<Vertex<T>> visitedSet = new HashSet();
        Queue<Vertex<T>> queue = new ConcurrentLinkedQueue<>();
        List<Vertex<T>> returnList = new ArrayList(graph.getVertices().size());
        queue.add(start);
        visitedSet.add(start);
        while (queue.size() > 0) {
            Vertex<T> v = queue.remove();
            returnList.add(v);
            for (VertexDistance<T> adjacent : graph.getAdjList().get(v)) {
                if (!(visitedSet.contains(adjacent.getVertex()))) {
                    queue.add(adjacent.getVertex());
                    visitedSet.add(adjacent.getVertex());
                }
            }
        }
        return returnList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Please enter a non-null start vertex and graph!");
        }
        if (!(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("The start vertex was not found in the provided graph.");
        }
        HashSet<Vertex<T>> visitedSet = new HashSet();
        ArrayList<Vertex<T>> returnList = new ArrayList(graph.getVertices().size());
        dfsHelper(start, visitedSet, returnList, graph);
        return returnList;
    }

    /**
     * a private helper method for the depth first search
     * @param s the current vertex in the traversal
     * @param visitedSet a set of vertices that have already been visited
     * @param returnList a list of the vertices in the order they were traversed
     * @param graph the graph to traverse
     * @param <T> a type parameter
     */
    private static <T> void dfsHelper(Vertex<T> s, HashSet visitedSet,
                                      ArrayList<Vertex<T>> returnList, Graph<T> graph) {
        visitedSet.add(s);
        returnList.add(s);
        for (VertexDistance<T> adjacent : graph.getAdjList().get(s)) {
            if (!(visitedSet.contains(adjacent.getVertex()))) {
                dfsHelper(adjacent.getVertex(), visitedSet, returnList, graph);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Please enter a non-null start vertex and graph!");
        }
        if (!(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("The start vertex was not found in the provided graph.");
        }
        HashSet<Vertex<T>> visitedSet = new HashSet<>();
        HashMap<Vertex<T>, Integer> distanceMap = new HashMap<>();
        PriorityQueue<VertexDistance<T>> queue = new PriorityQueue();
        for (Vertex<T> v : graph.getVertices()) {
            distanceMap.put(v, Integer.MAX_VALUE);
        }
        queue.add(new VertexDistance<>(start, 0));
        distanceMap.put(start, 0);
        while (queue.size() > 0 && visitedSet.size() < graph.getVertices().size()) {
            VertexDistance<T> temp = queue.remove();
            Vertex<T> u = temp.getVertex();
            int d = temp.getDistance();
            if (!(visitedSet.contains(u))) {
                visitedSet.add(u);
                for (VertexDistance<T> adjacent : graph.getAdjList().get(u)) {
                    Vertex<T> w = adjacent.getVertex();
                    int d2 = adjacent.getDistance();
                    if (!(visitedSet.contains(w))) {
                        if (distanceMap.get(w) > d + d2) {
                            distanceMap.replace(w, d + d2);
                        }
                        queue.add(new VertexDistance<>(w, d + d2));
                    }
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Please enter a non-null start vertex and graph!");
        }
        if (!(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("The start vertex was not found in the provided graph.");
        }
        HashSet<Vertex<T>> visitedSet = new HashSet<>();
        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();
        HashSet<Edge<T>> edgeSet = new HashSet<>();
        visitedSet.add(start);
        for (VertexDistance<T> dist: graph.getAdjList().get(start)) {
            queue.add(new Edge<>(start, dist.getVertex(), dist.getDistance()));
        }
        while (queue.size() > 0 && visitedSet.size() < graph.getVertices().size()) {
            Edge<T> temp = queue.remove();
            Vertex<T> u = temp.getU();
            Vertex<T> w = temp.getV();
            if (!(visitedSet.contains(w))) {
                edgeSet.add(temp);
                edgeSet.add(new Edge<>(w, u, temp.getWeight()));
                visitedSet.add(w);
                for (VertexDistance<T> edge : graph.getAdjList().get(w)) {
                    if (!(visitedSet.contains(edge.getVertex()))) {
                        queue.add(new Edge<>(w, edge.getVertex(), edge.getDistance()));
                    }
                }
            }
        }
        return edgeSet;
    }
}