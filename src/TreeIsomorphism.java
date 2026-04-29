import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeIsomorphism {
    private final Graph graph;

    private boolean validated;
    private boolean isTree;
    private String validationMessage;

    private int[] centers;
    private String canonicalEncoding;

    public TreeIsomorphism(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph nao pode ser nulo");
        }
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public boolean isTree() {
        validate();
        return isTree;
    }

    public String getValidationMessage() {
        validate();
        return validationMessage;
    }

    public int[] getCenters() {
        if (!isTree()) {
            throw new IllegalStateException("a entrada nao representa uma arvore valida");
        }
        if (centers == null) {
            centers = computeCenters();
        }
        return centers;
    }

    public String getCanonicalEncoding() {
        if (!isTree()) {
            throw new IllegalStateException("a entrada nao representa uma arvore valida");
        }
        if (canonicalEncoding == null) {
            canonicalEncoding = computeCanonicalEncoding();
        }
        return canonicalEncoding;
    }

    private void validate() {
        if (validated) return;
        validated = true;

        int V = graph.V();
        int E = graph.E();

        if (V == 0) {
            isTree = false;
            validationMessage = "entrada invalida: arvore deve ter pelo menos 1 vertice (V = 0)";
            return;
        }

        for (int v = 0; v < V; v++) {
            for (int w : graph.adj(v)) {
                if (v == w) {
                    isTree = false;
                    validationMessage = "entrada invalida: existe self-loop no vertice " + v;
                    return;
                }
            }
        }

        if (E != V - 1) {
            isTree = false;
            validationMessage = "entrada invalida: uma arvore com " + V
                    + " vertices deve ter " + (V - 1) + " arestas, mas possui " + E;
            return;
        }

        boolean[] visited = new boolean[V];
        int connectedCount = bfsCount(0, visited);
        if (connectedCount != V) {
            isTree = false;
            validationMessage = "entrada invalida: o grafo nao e conexo (" + connectedCount
                    + " de " + V + " vertices alcancaveis a partir do vertice 0)";
            return;
        }

        isTree = true;
        validationMessage = "entrada valida: o grafo e uma arvore com " + V
                + " vertices e " + E + " arestas";
    }

    private int bfsCount(int source, boolean[] visited) {
        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        while (!queue.isEmpty()) {
            int v = queue.remove();
            count++;
            for (int w : graph.adj(v)) {
                if (!visited[w]) {
                    visited[w] = true;
                    queue.add(w);
                }
            }
        }
        return count;
    }

    private int[] computeCenters() {
        int V = graph.V();

        if (V == 1) {
            return new int[] { 0 };
        }

        int[] degree = new int[V];
        for (int v = 0; v < V; v++) {
            degree[v] = graph.degree(v);
        }

        List<Integer> leaves = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            if (degree[v] <= 1) {
                leaves.add(v);
            }
        }

        int processed = leaves.size();
        while (processed < V) {
            List<Integer> newLeaves = new ArrayList<>();
            for (int u : leaves) {
                for (int v : graph.adj(u)) {
                    degree[v]--;
                    if (degree[v] == 1) {
                        newLeaves.add(v);
                    }
                }
            }
            processed += newLeaves.size();
            leaves = newLeaves;
        }

        int[] result = new int[leaves.size()];
        for (int i = 0; i < leaves.size(); i++) {
            result[i] = leaves.get(i);
        }
        Arrays.sort(result);
        return result;
    }

    private String computeCanonicalEncoding() {
        int[] cs = getCenters();

        if (cs.length == 1) {
            return encode(cs[0], -1);
        }

        String a = encode(cs[0], -1);
        String b = encode(cs[1], -1);
        return (a.compareTo(b) <= 0) ? a : b;
    }

    private String encode(int root, int parent) {
        List<String> childCodes = new ArrayList<>();
        for (int neighbor : graph.adj(root)) {
            if (neighbor != parent) {
                childCodes.add(encode(neighbor, root));
            }
        }
        Collections.sort(childCodes);
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (String c : childCodes) {
            sb.append(c);
        }
        sb.append(')');
        return sb.toString();
    }

    public static boolean areIsomorphic(TreeIsomorphism a, TreeIsomorphism b) {
        if (!a.isTree() || !b.isTree()) {
            throw new IllegalStateException("ambas as entradas precisam ser arvores validas");
        }
        return a.getCanonicalEncoding().equals(b.getCanonicalEncoding());
    }
}
