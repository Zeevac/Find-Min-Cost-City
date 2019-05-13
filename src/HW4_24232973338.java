import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class HW4_24232973338 {
    private static int[] lengths;
    private int[][] graph;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a file name: ");
        String fileName = scanner.nextLine();
        HW4_24232973338 h = new HW4_24232973338();
        h.readFile(fileName);
    }

    private void readFile(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/" + fileName));
        String line = bufferedReader.readLine();
        graph = new int[Integer.parseInt(line)][Integer.parseInt(line)];
        lengths = new int[graph.length];
        while ((line = bufferedReader.readLine()) != null) {
            String[] pieces = line.split("\\s+");
            int v1 = Integer.parseInt(pieces[0]) - 1;
            int v2 = Integer.parseInt(pieces[1]) - 1;
            int cost = Integer.parseInt(pieces[2]);
            graph[v1][v2] = cost;
            graph[v2][v1] = cost;
        }
        for (int i = 0; i < graph.length; i++) {
            calculateShortestPath(i);
        }

        printOptimalCity();
    }

    private void printOptimalCity() {
        int min = lengths[0];
        int minIndex = 0;
        for (int i = 1; i < graph.length; i++) {
            if (lengths[i] < min) {
                min = lengths[i];
                minIndex = i;
            }
        }
        char result = (char) ('A' + minIndex);
        System.out.println("The optimal city is " + result + " to install headquarters");
    }

    int minDistance(int distances[], boolean knownVertex[]) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int v = 0; v < graph.length; v++)
            if (distances[v] <= min && !knownVertex[v]) {
                min = distances[v];
                minIndex = v;
            }
        return minIndex;
    }


    void calculateShortestPath(int sourceVertex) {
        int distances[] = new int[graph.length];

        boolean knownVertex[] = new boolean[graph.length];

        for (int i = 0; i < graph.length; i++) {
            distances[i] = Integer.MAX_VALUE;
            knownVertex[i] = false;
        }

        distances[sourceVertex] = 0;

        for (int i = 0; i < graph.length - 1; i++) {
            int u = minDistance(distances, knownVertex);
            knownVertex[u] = true;
            for (int v = 0; v < graph.length; v++)
                if (!knownVertex[v] && graph[u][v] != 0 && distances[u] != Integer.MAX_VALUE && distances[u] + graph[u][v] < distances[v]) {
                    distances[v] = distances[u] + graph[u][v];
                }
        }
        calculateDistance(sourceVertex, distances);
    }

    public void calculateDistance(int sourceVertex, int[] distances) {
        for (int i = 0; i < graph.length; i++) {
            lengths[sourceVertex] += distances[i];
        }
    }
}
