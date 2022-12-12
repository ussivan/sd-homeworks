import drawingAPI.Awt;
import drawingAPI.DrawingApi;
import drawingAPI.JavaFx;
import model.ListGraph;
import model.Graph;
import model.MatrixGraph;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("args: <javafx|awt> <list|matrix> <path-to-graph>");

        DrawingApi drawingApi = getDrawingApi(args[0]);
        Graph g = getGraph(drawingApi, args[1], args[2]);
        g.drawGraph();
    }

    private static DrawingApi getDrawingApi(String graphicsApi) {
        DrawingApi drawingApi;
        if (graphicsApi.equals("javafx")) {
            drawingApi = new JavaFx();
        } else if (graphicsApi.equals("awt")) {
            drawingApi = new Awt();
        } else {
            throw new IllegalArgumentException("Invalid graphics API: " + graphicsApi);
        }
        return drawingApi;
    }

    private static Graph getGraph(DrawingApi drawingApi, String graphMode, String path) throws IOException {
        Graph graph;
        if (graphMode.equals("list")) {
            graph = new ListGraph(drawingApi, Path.of(path));
        } else if (graphMode.equals("matrix")) {
            graph = new MatrixGraph(drawingApi, Path.of(path));
        } else {
            throw new IllegalArgumentException("Invalid graph mode: " + graphMode);
        }
        return graph;
    }

}
