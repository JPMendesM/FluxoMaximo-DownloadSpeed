import java.io.*;
import java.util.*;

public class Main {

    static class Edge {
        int to, reverse;
        long capacity;

        Edge(int to, int reverse, long capacity) {
            this.to = to;
            this.reverse = reverse;
            this.capacity = capacity;
        }
    }

    static class Dinic {
        ArrayList<Edge>[] graph;
        int[] level;
        int[] next;

        @SuppressWarnings("unchecked")
        Dinic(int n) {
            graph = new ArrayList[n + 1];
            level = new int[n + 1];
            next = new int[n + 1];

            for (int i = 0; i <= n; i++) {
                graph[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to, long capacity) {
            Edge forward = new Edge(to, graph[to].size(), capacity);
            Edge backward = new Edge(from, graph[from].size(), 0);

            graph[from].add(forward);
            graph[to].add(backward);
        }

        boolean bfs(int source, int sink) {
            Arrays.fill(level, -1);

            ArrayDeque<Integer> queue = new ArrayDeque<>();
            level[source] = 0;
            queue.add(source);

            while (!queue.isEmpty()) {
                int current = queue.poll();

                for (Edge edge : graph[current]) {
                    if (edge.capacity > 0 && level[edge.to] == -1) {
                        level[edge.to] = level[current] + 1;
                        queue.add(edge.to);
                    }
                }
            }

            return level[sink] != -1;
        }

        long dfs(int current, int sink, long flow) {
            if (current == sink) {
                return flow;
            }

            for (; next[current] < graph[current].size(); next[current]++) {
                Edge edge = graph[current].get(next[current]);

                if (edge.capacity > 0 && level[edge.to] == level[current] + 1) {
                    long pushed = dfs(edge.to, sink, Math.min(flow, edge.capacity));

                    if (pushed > 0) {
                        edge.capacity -= pushed;
                        graph[edge.to].get(edge.reverse).capacity += pushed;
                        return pushed;
                    }
                }
            }

            return 0;
        }

        long maxFlow(int source, int sink) {
            long totalFlow = 0;
            long INF = Long.MAX_VALUE;

            while (bfs(source, sink)) {
                Arrays.fill(next, 0);

                while (true) {
                    long pushed = dfs(source, sink, INF);

                    if (pushed == 0) {
                        break;
                    }

                    totalFlow += pushed;
                }
            }

            return totalFlow;
        }
    }

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int pointer = 0;
        private int length = 0;

        private int read() throws IOException {
            if (pointer >= length) {
                length = in.read(buffer);
                pointer = 0;

                if (length <= 0) {
                    return -1;
                }
            }

            return buffer[pointer++];
        }

        long nextLong() throws IOException {
            int c;

            do {
                c = read();
            } while (c <= ' ' && c != -1);

            long value = 0;

            while (c > ' ') {
                value = value * 10 + (c - '0');
                c = read();
            }

            return value;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        int n = fs.nextInt();
        int m = fs.nextInt();

        Dinic dinic = new Dinic(n);

        for (int i = 0; i < m; i++) {
            int a = fs.nextInt();
            int b = fs.nextInt();
            long c = fs.nextLong();

            dinic.addEdge(a, b, c);
        }

        System.out.println(dinic.maxFlow(1, n));
    }
}