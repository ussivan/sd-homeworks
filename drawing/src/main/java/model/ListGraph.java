package model;

import drawingAPI.DrawingApi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ListGraph extends Graph {

    private final List<List<Integer>> g;

    public ListGraph(DrawingApi drawingApi, List<List<Integer>> g) {
        super(drawingApi);
        this.g = g;
    }

    public ListGraph(DrawingApi drawingApi, Path path) throws IOException {
        this(drawingApi, Files.readString(path));
    }

    public ListGraph(DrawingApi drawingApi, String def) {
        this(drawingApi, parseList(def));
    }

    private static List<List<Integer>> parseList(String def) {
        var res = new ArrayList<List<Integer>>();
        String firstLine = def.split(System.lineSeparator())[0];
        int size = Integer.parseInt(firstLine);
        for (int i = 0; i < size; i++) {
            res.add(new ArrayList<>());
        }
        def.lines().skip(1).forEach(line -> {
            String[] split = line.split(" +");
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[1]);
            res.get(a).add(b);
        });
        return res;
    }

    protected void doDrawGraph() {
        for (int a = 0; a < g.size(); a++) {
            drawVertex(a);
            for (int b : g.get(a)) {
                drawEdge(a, b);
            }
        }
    }

    @Override
    public int size() {
        return g.size();
    }
}
