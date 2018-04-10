import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * DAG Data structure.
 */
public class DAG<T, K> {

    private Map<K, Set<Vertex>> adjList = new HashMap<>();
    private Map<K, Vertex<T, K>> vertices = new LinkedHashMap<>();

    public void addEdge(Vertex<T, K> source, Vertex<T, K> target) {
        K sourceId = source.getId();
        if(adjList.containsKey(sourceId)) {
            adjList.get(sourceId).add(target);
        } else {
            adjList.put(sourceId, new LinkedHashSet<>());
            adjList.get(sourceId).add(target);
        }

        vertices.put(sourceId, source);
    }

    public Vertex<T, K> getVertex(K vertexId) {
        return vertices.get(vertexId);
    }

    public void processDepthFirst(Vertex<T, K> startVertex, Consumer<Vertex> vertexProcessor) {
        processDepthFirstInternal(startVertex, vertexProcessor, new HashSet<K>());
    }

    private void processDepthFirstInternal(Vertex<T, K> startVertex, Consumer<Vertex> vertexProcessor, Set<K> visited) {
        // No need to visit if already visited.
        if(visited.contains(startVertex.getId())) return;

        // Process the current vertex.
        vertexProcessor.accept(startVertex);
        visited.add(startVertex.getId());

        Set<Vertex> connectedVertices = this.adjList.get(startVertex.getId());
        if(connectedVertices == null) return;

        for(Vertex<T, K> vertex : connectedVertices) {
            processDepthFirstInternal(vertex, vertexProcessor, visited);
        }
    }

    public void processLevelOrder(Vertex<T, K> startVertex, Consumer<Vertex> vertexProcessor, Consumer<Integer> levelEndProcessor) {
        final Set<K> visitedVertices = new HashSet<>();
        List<Vertex<T, K>> currentLevel = new ArrayList<>();
        currentLevel.add(startVertex);

        int level = 0;
        while(currentLevel != null && currentLevel.size() > 0) {
            currentLevel = currentLevel.stream().filter(vertex -> !visitedVertices.contains(vertex.getId())).collect(Collectors.toList());
            if(currentLevel.size() == 0) break;

            // Process current level elements, first off.
            for(Vertex<T, K> vertex : currentLevel) {
                if(!visitedVertices.contains(vertex.getId())) {
                    vertexProcessor.accept(vertex);
                }
                visitedVertices.add(vertex.getId());
            }

            currentLevel = populateNextLevel(currentLevel);
            levelEndProcessor.accept(level++);
        }
    }

    private List<Vertex<T, K>> populateNextLevel(List<Vertex<T, K>> currentLevel) {
        List<Vertex<T, K>> nextLevel = new ArrayList<>();

        for(Vertex<T, K> vertex : currentLevel) {
            Set<Vertex> connectedVertices = this.adjList.get(vertex.getId());
            if(connectedVertices == null) continue;
            for(Vertex<T, K> connectedVertex : connectedVertices) {
                nextLevel.add(connectedVertex);
            }
        }
        return nextLevel;
    }

    public static class Vertex <T, K> {
        T value;
        K id;

        public Vertex(T value, K id) {
            this.value = value;
            this.id = id;
        }

        K getId() {
            return id;
        }

        T getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<?, ?> vertex = (Vertex<?, ?>) o;
            return Objects.equals(id, vertex.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return value + " :: " + id;
        }
    }
}



