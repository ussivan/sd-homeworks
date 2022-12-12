package drawingAPI;

public interface DrawingApi {

    int WIDTH = 800;
    int HEIGHT = 600;

    void drawCircle(Point c, int r);

    void drawLine(Point a, Point b);

    void visualize();

    default int getDrawingAreaWidth() {
        return WIDTH;
    }

    default int getDrawingAreaHeight() {
        return HEIGHT;
    }
}
